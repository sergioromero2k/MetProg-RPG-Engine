package metprog;

import metprog.model.*;
import metprog.service.GestorDesafios;
import metprog.service.GestorUsuarios;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

/**
 * Tests del GestorDesafios: crear, validar, aceptar, rechazar desafíos
 * y todas las restricciones de negocio.
 */
class GestorDesafiosTest {

    private GestorUsuarios gestorUsuarios;
    private GestorDesafios gestorDesafios;
    private Usuario desafiante;
    private Usuario desafiado;

    @BeforeEach
    void setUp() {
        gestorUsuarios = new GestorUsuarios();
        gestorDesafios = new GestorDesafios();

        desafiante = gestorUsuarios.registrarUsuario("Alpha", "alpha1", "Password1");
        desafiado  = gestorUsuarios.registrarUsuario("Beta",  "beta1",  "Password2");

        // Equipar personajes con equipo activo
        configurarPersonajeCompleto(desafiante, new Vampiro("VampA", 5, 4, 500));
        configurarPersonajeCompleto(desafiado,  new Cazador("CazB",  5, 3, 300));
    }

    /** Helper: asigna personaje con arma y armadura activas */
    private void configurarPersonajeCompleto(Usuario u, Personaje p) {
        Arma    arma    = new Arma("Espada", 2, 0, false);
        Armadura armadura = new Armadura("Escudo", 0, 2);
        p.equiparArma(arma);
        p.añadirArmadura(armadura);
        p.setArmasActivas(List.of(arma));
        p.setArmaduraActiva(armadura);
        u.setPersonaje(p);
    }

    // ── Crear desafío ─────────────────────────────────────────────────────────

    @Test
    void crearDesafioValidoDevuelveDesafio() {
        Desafio d = gestorDesafios.crearDesafio(desafiante, desafiado, 100);
        assertNotNull(d);
        assertEquals(desafiante, d.getDesafiante());
        assertEquals(desafiado,  d.getDesafiado());
        assertEquals(100,        d.getOroApostado());
    }

    @Test
    void crearDesafioConOroNegativoFalla() {
        Desafio d = gestorDesafios.crearDesafio(desafiante, desafiado, -10);
        assertNull(d);
    }

    @Test
    void crearDesafioConMasOroDelQueSeTimeneFalla() {
        Desafio d = gestorDesafios.crearDesafio(desafiante, desafiado, 9999);
        assertNull(d);
    }

    @Test
    void crearDesafioSinEquipoActivoFalla() {
        Usuario sinEquipo = gestorUsuarios.registrarUsuario("C", "cc1", "Password3");
        sinEquipo.setPersonaje(new Vampiro("SinEquipo", 5, 3, 100));
        Desafio d = gestorDesafios.crearDesafio(desafiante, sinEquipo, 10);
        assertNull(d);
    }

    @Test
    void crearDesafioConDesafianteBloquedoFalla() {
        gestorUsuarios.bloquearUsuario("alpha1");
        Desafio d = gestorDesafios.crearDesafio(desafiante, desafiado, 10);
        assertNull(d);
    }

    @Test
    void noSePuedeDesafiarAUnMismo() {
        Desafio d = gestorDesafios.crearDesafio(desafiante, desafiante, 10);
        assertNull(d);
    }

    @Test
    void noSePuedeDesafiarSiDesafiadoTienePendiente() {
        gestorDesafios.crearDesafio(desafiante, desafiado, 50);
        // Crear un tercer usuario que intente desafiar a beta
        Usuario tercero = gestorUsuarios.registrarUsuario("Gamma", "gamma1", "Password3");
        configurarPersonajeCompleto(tercero, new Licantropo("Lobo", 5, 3, 200));
        Desafio d2 = gestorDesafios.crearDesafio(tercero, desafiado, 20);
        assertNull(d2);
    }

    // ── Validar desafío (operador) ────────────────────────────────────────────

    @Test
    void validarDesafioTransicionaAPublicado() {
        Desafio d = gestorDesafios.crearDesafio(desafiante, desafiado, 100);
        boolean ok = gestorDesafios.validarDesafio(d,
                List.of(), List.of(), List.of(), List.of());
        assertTrue(ok);
        assertEquals("Publicado", d.getEstado().getClass().getSimpleName());
    }

    @Test
    void validarDesafioConModificadoresPresentes() {
        Desafio d = gestorDesafios.crearDesafio(desafiante, desafiado, 50);
        Fortaleza fort = new Fortaleza("Noche cerrada", 3);
        Debilidad deb  = new Debilidad("Luz solar", 2);
        gestorDesafios.validarDesafio(d,
                List.of(fort), List.of(deb), List.of(), List.of());
        assertEquals(1, d.getFortalezasDesafiante().size());
        assertEquals(1, d.getDebilidadesDesafiante().size());
    }

    // ── Rechazar desafío ─────────────────────────────────────────────────────

    @Test
    void rechazarDesafioAplicaPenalizacionDelDiezPorciento() {
        Desafio d = gestorDesafios.crearDesafio(desafiante, desafiado, 100);
        gestorDesafios.validarDesafio(d, List.of(), List.of(), List.of(), List.of());

        int oroDesafiadoAntes   = desafiado.getPersonaje().getOro();   // 300
        int oroDesafianteAntes  = desafiante.getPersonaje().getOro();  // 500

        gestorDesafios.rechazarDesafio(d);

        assertEquals(oroDesafiadoAntes  - 10, desafiado.getPersonaje().getOro());
        assertEquals(oroDesafianteAntes + 10, desafiante.getPersonaje().getOro());
    }

    @Test
    void rechazarDesafioConOroInsuficienteNoEsNegativo() {
        // Desafiado con poco oro
        desafiado.getPersonaje().setOro(5);
        Desafio d = gestorDesafios.crearDesafio(desafiante, desafiado, 100);
        gestorDesafios.validarDesafio(d, List.of(), List.of(), List.of(), List.of());
        gestorDesafios.rechazarDesafio(d);
        assertTrue(desafiado.getPersonaje().getOro() >= 0);
    }

    // ── Aceptar y finalizar ───────────────────────────────────────────────────

    @Test
    void aceptarDesafioTransicionaAEnCombate() {
        Desafio d = gestorDesafios.crearDesafio(desafiante, desafiado, 50);
        gestorDesafios.validarDesafio(d, List.of(), List.of(), List.of(), List.of());
        assertTrue(gestorDesafios.aceptarDesafio(d));
        assertEquals("EnCombate", d.getEstado().getClass().getSimpleName());
    }

    @Test
    void finalizarDesafioTransfiereOroAlVencedor() {
        Desafio d = gestorDesafios.crearDesafio(desafiante, desafiado, 100);
        gestorDesafios.validarDesafio(d, List.of(), List.of(), List.of(), List.of());
        gestorDesafios.aceptarDesafio(d);

        Combate combate = new Combate(desafiante, desafiado);
        combate.setVencedor(desafiante); // desafiante gana
        combate.setRondasEmpleadas(3);

        int oroAntes = desafiante.getPersonaje().getOro();
        gestorDesafios.finalizarDesafio(d, combate);

        assertEquals(oroAntes + 100, desafiante.getPersonaje().getOro());
        assertEquals(200, desafiado.getPersonaje().getOro()); // 300 - 100
    }

    @Test
    void finalizarDesafioEnEmpateNadieChangePierde() {
        Desafio d = gestorDesafios.crearDesafio(desafiante, desafiado, 100);
        gestorDesafios.validarDesafio(d, List.of(), List.of(), List.of(), List.of());
        gestorDesafios.aceptarDesafio(d);

        Combate combate = new Combate(desafiante, desafiado);
        // vencedor = null → empate
        combate.setRondasEmpleadas(5);

        int oroDesafianteBefore = desafiante.getPersonaje().getOro();
        int oroDesafiadoBefore  = desafiado.getPersonaje().getOro();

        gestorDesafios.finalizarDesafio(d, combate);

        assertEquals(oroDesafianteBefore, desafiante.getPersonaje().getOro());
        assertEquals(oroDesafiadoBefore,  desafiado.getPersonaje().getOro());
    }

    // ── Consultas ─────────────────────────────────────────────────────────────

    @Test
    void getDesafiosPendientesDevuelveSoloPendientes() {
        gestorDesafios.crearDesafio(desafiante, desafiado, 50);
        assertEquals(1, gestorDesafios.getDesafiosPendientes().size());
    }

    @Test
    void getDesafioPublicadoParaUsuarioDevuelveCorrectamente() {
        Desafio d = gestorDesafios.crearDesafio(desafiante, desafiado, 50);
        gestorDesafios.validarDesafio(d, List.of(), List.of(), List.of(), List.of());
        Desafio encontrado = gestorDesafios.getDesafioPublicadoParaUsuario(desafiado);
        assertEquals(d, encontrado);
    }
}
