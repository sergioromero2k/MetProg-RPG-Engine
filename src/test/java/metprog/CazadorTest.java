package metprog;

import metprog.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

/**
 * Tests unitarios para la clase Cazador.
 */
class CazadorTest {

    private Cazador cazador;
    private Talento talento;
    private Arma ballesta;
    private Armadura chaleco;

    @BeforeEach
    void setUp() {
        cazador  = new Cazador("Van Helsing", 5, 3, 60);
        talento  = new Talento("Ojo Certero", 2, 1);
        ballesta = new Arma("Ballesta Sagrada", 3, 0, false);
        chaleco  = new Armadura("Chaleco Blindado", 0, 2);
        cazador.setHabilidad(talento);
        cazador.equiparArma(ballesta);
        cazador.agregarArmadura(chaleco);
    }

    // ── Voluntad ─────────────────────────────────────────────────────────────

    @Test
    void voluntadInicialEsTres() {
        assertEquals(3, cazador.getVoluntad());
    }

    @Test
    void recibirDañoDisminuyeVoluntad() {
        cazador.recibirDano(1);
        assertEquals(2, cazador.getVoluntad());
        assertEquals(4, cazador.getSalud());
    }

    @Test
    void voluntadNoBajaDeCero() {
        cazador.recibirDano(10); // más daño del que aguanta
        assertEquals(0, cazador.getVoluntad());
    }

    @Test
    void setVoluntadInvalidaSeCorrige() {
        cazador.setVoluntad(4);
        assertEquals(3, cazador.getVoluntad());
        cazador.setVoluntad(-1);
        assertEquals(0, cazador.getVoluntad());
    }

    @Test
    void reinicioRestaurarVoluntadYSalud() {
        cazador.recibirDano(2);
        cazador.reiniciarParaCombate();
        assertEquals(3, cazador.getVoluntad());
        assertEquals(5, cazador.getSalud());
    }

    // ── Talento ──────────────────────────────────────────────────────────────

    @Test
    void getTalentoDevuelveElAsignado() {
        assertNotNull(cazador.getTalento());
        assertEquals("Ojo Certero", cazador.getTalento().getNombre());
    }

    @Test
    void talentoNoTieneRestriccionesAdicionales() {
        // Un talento siempre se puede usar (no tiene coste ni rabia mínima)
        Talento t = new Talento("Instinto", 1, 3);
        assertEquals(1, t.getValorAtaque());
        assertEquals(3, t.getValorDefensa());
    }

    // ── Equipo activo ─────────────────────────────────────────────────────────

    @Test
    void dosArmasDeUnaManoActivasPermitido() {
        Arma espada = new Arma("Espada", 2, 0, false);
        cazador.equiparArma(espada);
        assertTrue(cazador.setArmasActivas(List.of(ballesta, espada)));
        assertEquals(2, cazador.getArmasActivas().size());
    }

    @Test
    void armaDosManosNoPermiteOtraActiva() {
        Arma hacha = new Arma("Hacha Doble", 3, 1, true);
        cazador.equiparArma(hacha);
        cazador.setArmasActivas(List.of(hacha));
        // Solo debe quedar esa arma activa
        assertEquals(1, cazador.getArmasActivas().size());
        assertTrue(cazador.getArmasActivas().get(0).isDosManos());
    }

    @Test
    void noSePuedenActivarMasDeDosArmas() {
        Arma e1 = new Arma("Espada", 1, 0, false);
        Arma e2 = new Arma("Daga", 1, 0, false);
        cazador.equiparArma(e1);
        cazador.equiparArma(e2);
        assertFalse(cazador.setArmasActivas(List.of(ballesta, e1, e2)));
    }

    @Test
    void tieneEquipoActivoCorrecto() {
        cazador.setArmasActivas(List.of(ballesta));
        cazador.setArmaduraActiva(chaleco);
        assertTrue(cazador.tieneEquipoActivo());
    }
}
