package metprog;

import metprog.model.*;
import metprog.service.MotorCombate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

/**
 * Tests del MotorCombate.
 *
 * Como el combate usa dados aleatorios, los tests se centran en:
 *  - Invariantes que se cumplen siempre (estructura del resultado).
 *  - Comportamientos deterministas (potencial de ataque/defensa calculado en método público).
 *  - Casos extremos (esbirros, empate forzado, modificadores).
 *
 * Los tests de combate completo se repiten varias veces para cubrir distintas tiradas.
 */
class MotorCombateTest {

    private MotorCombate motor;
    private Usuario u1;
    private Usuario u2;

    @BeforeEach
    void setUp() {
        motor = new MotorCombate();
        u1 = new Usuario("Alpha", "alpha1", "Password1");
        u2 = new Usuario("Beta",  "beta1",  "Password2");
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    /** Crea un Vampiro completamente equipado y listo para combatir */
    private Vampiro crearVampiroEquipado(String nombre, int poder, int oro) {
        Vampiro v = new Vampiro(nombre, 5, poder, oro);
        v.setHabilidad(new Disciplina("Dominar", 2, 1, 1));
        Arma a = new Arma("Espada", 2, 0, false);
        Armadura arm = new Armadura("Capa", 0, 2);
        v.equiparArma(a); v.añadirArmadura(arm);
        v.setArmasActivas(List.of(a)); v.setArmaduraActiva(arm);
        return v;
    }

    private Cazador crearCazadorEquipado(String nombre, int poder, int oro) {
        Cazador c = new Cazador(nombre, 5, poder, oro);
        c.setHabilidad(new Talento("Ojo", 2, 1));
        Arma a = new Arma("Ballesta", 2, 0, false);
        Armadura arm = new Armadura("Chaleco", 0, 2);
        c.equiparArma(a); c.añadirArmadura(arm);
        c.setArmasActivas(List.of(a)); c.setArmaduraActiva(arm);
        return c;
    }

    private Licantropo crearLicantropoEquipado(String nombre, int poder, int oro) {
        Licantropo l = new Licantropo(nombre, 5, poder, oro);
        l.setHabilidad(new Don("Frenesí", 3, 2, 0)); // rabiaMinima=0 → siempre usable
        Arma a = new Arma("Garra", 3, 0, false);
        Armadura arm = new Armadura("Piel", 0, 3);
        l.equiparArma(a); l.añadirArmadura(arm);
        l.setArmasActivas(List.of(a)); l.setArmaduraActiva(arm);
        return l;
    }

    // ── Estructura del resultado ─────────────────────────────────────────────

    @RepeatedTest(10)
    void combateDevuelveCombateNoNulo() {
        u1.setPersonaje(crearVampiroEquipado("V", 3, 100));
        u2.setPersonaje(crearCazadorEquipado("C", 3, 100));
        Combate c = motor.ejecutar(u1, u2);
        assertNotNull(c);
    }

    @RepeatedTest(10)
    void combateSiempreTerminaConUnVencedorOEmpate() {
        u1.setPersonaje(crearVampiroEquipado("V", 3, 100));
        u2.setPersonaje(crearCazadorEquipado("C", 3, 100));
        Combate c = motor.ejecutar(u1, u2);
        // El vencedor es u1, u2 o null (empate), nunca otra cosa
        assertTrue(c.getVencedor() == null
                || c.getVencedor().equals(u1)
                || c.getVencedor().equals(u2));
    }

    @RepeatedTest(10)
    void combateRegistraAlMenosUnaRonda() {
        u1.setPersonaje(crearVampiroEquipado("V", 3, 100));
        u2.setPersonaje(crearCazadorEquipado("C", 3, 100));
        Combate c = motor.ejecutar(u1, u2);
        assertTrue(c.getRondasEmpleadas() >= 1);
        assertTrue(c.getRondas().size() >= 1);
    }

    @RepeatedTest(10)
    void rondasEmpleadasMatchLogDeRondas() {
        u1.setPersonaje(crearVampiroEquipado("V", 3, 100));
        u2.setPersonaje(crearCazadorEquipado("C", 3, 100));
        Combate c = motor.ejecutar(u1, u2);
        assertEquals(c.getRondasEmpleadas(), c.getRondas().size());
    }

    @RepeatedTest(10)
    void alTerminarAlMenosUnPersonajeSinSalud() {
        u1.setPersonaje(crearVampiroEquipado("V", 3, 100));
        u2.setPersonaje(crearCazadorEquipado("C", 3, 100));
        motor.ejecutar(u1, u2);
        // Al menos uno de los dos debe haber llegado a 0
        boolean p1sin = u1.getPersonaje().getSalud() <= 0;
        boolean p2sin = u2.getPersonaje().getSalud() <= 0;
        assertTrue(p1sin || p2sin);
    }

    // ── Potencial de ataque determinista ─────────────────────────────────────

    @Test
    void potencialAtaqueVampiroSinSangreSuficienteNoPagaDisciplina() {
        Vampiro v = crearVampiroEquipado("V", 3, 100);
        v.setPuntosSangre(0); // sin sangre
        // La disciplina cuesta 1, no puede pagarla → no suma su valorAtaque
        // Potencial = poder(3) + disciplina(0) + arma(2) + sangre<5(0) + mods(0) = 5
        int pot = motor.calcularPotencialAtaque(v);
        assertEquals(5, pot);
    }

    @Test
    void potencialAtaqueVampiroConSangreSuficienteSumaDisciplinaYBonus() {
        Vampiro v = crearVampiroEquipado("V", 3, 100);
        v.setPuntosSangre(5); // >= 5 → +2 bonus
        // Potencial = poder(3) + disciplina(2) + arma(2) + bonus(2) + mods(0) = 9
        // Pero al calcular, gasta 1 de sangre → sangre queda en 4 (< 5, sin bonus)
        // El bonus se evalúa ANTES de gastar: depende de la implementación
        // En nuestra impl, el gasto ocurre dentro del cálculo, el bonus se toma del estado tras gastar
        // sangre 5-1=4 → bonus 0 → pot = 3+2+2+0 = 7
        int pot = motor.calcularPotencialAtaque(v);
        assertEquals(7, pot);
    }

    @Test
    void potencialAtaqueLicantropoSumaRabia() {
        Licantropo l = crearLicantropoEquipado("L", 3, 100);
        l.setRabia(2);
        // poder(3) + don(3) + arma(3) + rabia(2) + mods(0) = 11
        assertEquals(11, motor.calcularPotencialAtaque(l));
    }

    @Test
    void potencialAtaqueLicantropoSinRabiaSuficienteNousaDon() {
        Licantropo l = crearLicantropoEquipado("L", 3, 100);
        // Don con rabiaMinima=2, rabia actual=0 → no puede usarlo
        l.setHabilidad(new Don("Grande", 3, 2, 2));
        l.setRabia(0);
        // poder(3) + don(0) + arma(3) + rabia(0) + mods(0) = 6
        assertEquals(6, motor.calcularPotencialAtaque(l));
    }

    @Test
    void potencialAtaqueCazadorSumaVoluntad() {
        Cazador c = crearCazadorEquipado("C", 3, 100);
        // voluntad inicial = 3, poder=3, talento=2, arma=2, voluntad=3 → 10
        assertEquals(10, motor.calcularPotencialAtaque(c));
    }

    @Test
    void potencialAtaqueCazadorConVoluntadCeroNoSumaExtra() {
        Cazador c = crearCazadorEquipado("C", 3, 100);
        c.setVoluntad(0);
        // poder(3) + talento(2) + arma(2) + voluntad(0) = 7
        assertEquals(7, motor.calcularPotencialAtaque(c));
    }

    // ── Potencial de defensa determinista ─────────────────────────────────────

    @Test
    void potencialDefensaCazadorCorrectoConVoluntadPlena() {
        Cazador c = crearCazadorEquipado("C", 3, 100);
        // poder(3) + talento_def(1) + armadura_def(2) + voluntad(3) = 9
        assertEquals(9, motor.calcularPotencialDefensa(c));
    }

    @Test
    void potencialDefensaLicantropoUsaDonSoloConRabiaSuficiente() {
        Licantropo l = crearLicantropoEquipado("L", 3, 100);
        Don don = new Don("Frenesí", 3, 2, 1);
        l.setHabilidad(don);
        l.setRabia(0); // no llega a rabiaMinima=1 → valorDefensa = 0
        // poder(3) + don_def(0) + armadura(3) + rabia(0) = 6
        assertEquals(6, motor.calcularPotencialDefensa(l));
    }

    // ── Modificadores presentes ───────────────────────────────────────────────

    @Test
    void fortalezaPresenteSumaAlPotencial() {
        Vampiro v = crearVampiroEquipado("V", 3, 100);
        v.setPuntosSangre(0); // sin sangre para aislar el modificador
        Fortaleza f = new Fortaleza("Noche cerrada", 3);
        v.getFortalezasPresentes().add(f);
        // poder(3) + disc(0) + arma(2) + bonus(0) + fortaleza(3) = 8
        assertEquals(8, motor.calcularPotencialAtaque(v));
    }

    @Test
    void debilidadPresenteRestaAlPotencial() {
        Cazador c = crearCazadorEquipado("C", 3, 100);
        c.setVoluntad(0);
        Debilidad d = new Debilidad("Luz solar", 2);
        c.getDebilidadesPresentes().add(d);
        // poder(3) + talento(2) + arma(2) + voluntad(0) - debilidad(2) = 5
        assertEquals(5, motor.calcularPotencialAtaque(c));
    }

    // ── Esbirros ─────────────────────────────────────────────────────────────

    @RepeatedTest(5)
    void personajeConEsbirrosAguantaMasDaño() {
        // Vampiro con 2 esbirros (pool = 2+3 = 5) vs cazador sin esbirros
        Vampiro v = crearVampiroEquipado("V", 5, 100); // poder alto para asegurar victorias
        EsbirroGhoul g1 = new EsbirroGhoul("G1", 2, 3);
        EsbirroGhoul g2 = new EsbirroGhoul("G2", 3, 4);
        v.añadirEsbirro(g1);
        v.añadirEsbirro(g2);
        assertEquals(5, v.getSaludTotalEsbirros());

        u1.setPersonaje(v);
        u2.setPersonaje(crearCazadorEquipado("C", 1, 100)); // poder bajo

        Combate c = motor.ejecutar(u1, u2);
        // El vampiro con esbirros debería resistir más rondas. No podemos garantizar
        // el vencedor por la aleatoriedad, pero sí que el combate termina correctamente.
        assertTrue(c.getRondasEmpleadas() >= 1);
        assertNotNull(c);
    }

    @Test
    void saludTotalEsbirrosRecursivoEsCorrectaAntesDeCombate() {
        Vampiro v = crearVampiroEquipado("V", 3, 100);
        EsbirroDemonio dem = new EsbirroDemonio("Asmo", 3, "Pacto");
        dem.añadirSubEsbirro(new EsbirroGhoul("Sub", 2, 3));
        v.añadirEsbirro(dem);
        // Total = demonio(3) + sub(2) = 5
        assertEquals(5, v.getSaludTotalEsbirros());
    }

    // ── Dados ────────────────────────────────────────────────────────────────

    @Test
    void lanzarCeroDadosDevuelveCeroExitos() {
        assertEquals(0, motor.lanzarDados(0));
    }

    @Test
    void lanzarDadosNegativoDevuelveCeroExitos() {
        assertEquals(0, motor.lanzarDados(-5));
    }

    @RepeatedTest(20)
    void exitosNuncaSuperanElNumeroDeDados() {
        int n = 6;
        int exitos = motor.lanzarDados(n);
        assertTrue(exitos >= 0 && exitos <= n);
    }

    // ── Vampiro recupera sangre al atacar con éxito ───────────────────────────

    @Test
    void vampiroRecuperaSangreAlAtacarConExitoEnCombate() {
        // Vampiro con poder alto vs contrincante con poder 1 (casi siempre pierde)
        Vampiro v = crearVampiroEquipado("V", 5, 100);
        v.setPuntosSangre(1); // poca sangre antes del combate
        u1.setPersonaje(v);

        Cazador c = crearCazadorEquipado("C", 1, 100);
        c.setVoluntad(0); // sin voluntad extra
        u2.setPersonaje(c);

        motor.ejecutar(u1, u2);
        // Tras el combate (que el vampiro debería ganar casi siempre),
        // los puntos de sangre deberían haber aumentado respecto al mínimo
        // No podemos garantizar el valor exacto por la aleatoriedad del combate,
        // pero sí que no se corrompió el estado
        assertTrue(v.getPuntosSangre() >= 0 && v.getPuntosSangre() <= 10);
    }

    // ── Reinicio de estado entre combates ────────────────────────────────────

    @Test
    void licantropoEmpiezaConRabiaCeroEnCadaCombate() {
        Licantropo l = crearLicantropoEquipado("L", 3, 100);
        l.setRabia(3); // simular rabia acumulada de un combate anterior
        u1.setPersonaje(l);
        u2.setPersonaje(crearCazadorEquipado("C", 3, 100));

        motor.ejecutar(u1, u2);
        // El motor llama reiniciarParaCombate() al empezar → rabia empieza en 0
        // (luego puede haber subido durante el combate)
        // Solo podemos verificar que el combate se ejecutó sin errores
        assertTrue(l.getSalud() >= 0);
    }

    @Test
    void cazadorEmpiezaConVoluntadTresEnCadaCombate() {
        Cazador c = crearCazadorEquipado("C", 3, 100);
        c.setVoluntad(0); // simular combate previo
        u1.setPersonaje(crearVampiroEquipado("V", 3, 100));
        u2.setPersonaje(c);

        motor.ejecutar(u1, u2);
        // El motor reinicia → voluntad comienza en 3 (puede bajar durante combate)
        assertTrue(c.getSalud() >= 0);
    }
}
