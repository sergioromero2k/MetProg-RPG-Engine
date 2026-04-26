package metprog;

import metprog.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

/**
 * Tests unitarios para la clase Licantropo.
 */
class LicantropoTest {

    private Licantropo lican;
    private Don don;
    private Arma garra;
    private Armadura piel;

    @BeforeEach
    void setUp() {
        lican = new Licantropo("Fenrir", 5, 3, 80);
        don   = new Don("Frenesí", 3, 2, 2);
        garra = new Arma("Garra", 3, 0, false);
        piel  = new Armadura("Piel de Bestia", 0, 3);
        lican.setHabilidad(don);
        lican.equiparArma(garra);
        lican.añadirArmadura(piel);
    }

    // ── Rabia ─────────────────────────────────────────────────────────────────

    @Test
    void rabiaInicialEsCero() {
        assertEquals(0, lican.getRabia());
    }

    @Test
    void recibirDañoIncrementaRabia() {
        lican.recibirDaño(1);
        assertEquals(1, lican.getRabia());
        assertEquals(4, lican.getSalud());
    }

    @Test
    void rabiaNoPasaDeTres() {
        lican.recibirDaño(5); // 5 golpes pero rabia máx 3
        assertEquals(3, lican.getRabia());
    }

    @Test
    void setRabiaInvalidaSeCorrige() {
        lican.setRabia(4);
        assertEquals(3, lican.getRabia());
        lican.setRabia(-1);
        assertEquals(0, lican.getRabia());
    }

    @Test
    void reinicioRestaurarRabiaYSalud() {
        lican.recibirDaño(3);
        lican.reiniciarParaCombate();
        assertEquals(0, lican.getRabia());
        assertEquals(5, lican.getSalud());
    }

    // ── Don ──────────────────────────────────────────────────────────────────

    @Test
    void getDonDevuelveElAsignado() {
        assertNotNull(lican.getDon());
        assertEquals("Frenesí", lican.getDon().getNombre());
        assertEquals(2, lican.getDon().getRabiaMinima());
    }

    @Test
    void puedeUsarDonConRabiaSuficiente() {
        lican.setRabia(2);
        assertTrue(lican.puedeUsarDon());
    }

    @Test
    void noPuedeUsarDonConRabiaInsuficiente() {
        lican.setRabia(1);
        assertFalse(lican.puedeUsarDon());
    }

    @Test
    void donConRabiaMinimaCero() {
        Don d = new Don("Básico", 1, 1, 0);
        assertEquals(0, d.getRabiaMinima());
        assertTrue(d.puedeUsarse(0));
    }

    // ── Esbirros ─────────────────────────────────────────────────────────────

    @Test
    void licantropoPuedeAñadirEsbirroHumano() {
        EsbirroHumano h = new EsbirroHumano("Aldeano", 1, Lealtad.NORMAL);
        assertDoesNotThrow(() -> lican.añadirEsbirro(h));
        assertEquals(1, lican.getEsbirros().size());
    }

    // ── Equipo activo ─────────────────────────────────────────────────────────

    @Test
    void tieneEquipoActivoCorrecto() {
        lican.setArmasActivas(List.of(garra));
        lican.setArmaduraActiva(piel);
        assertTrue(lican.tieneEquipoActivo());
    }
}
