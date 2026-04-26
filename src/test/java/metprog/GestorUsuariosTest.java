package metprog;

import metprog.model.*;
import metprog.service.GestorUsuarios;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests del GestorUsuarios: registro, login, bloqueo,
 * unicidad de nick y número de registro LNNLL.
 */
class GestorUsuariosTest {

    private GestorUsuarios gestor;

    @BeforeEach
    void setUp() {
        gestor = new GestorUsuarios();
    }

    // ── Registro de usuario ──────────────────────────────────────────────────

    @Test
    void registrarUsuarioCorrectamente() {
        Usuario u = gestor.registrarUsuario("Ana García", "ana99", "Password1");
        assertNotNull(u);
        assertEquals("ana99", u.getNick());
        assertEquals("Ana García", u.getNombre());
    }

    @Test
    void registrarUsuarioConNickDuplicadoFalla() {
        gestor.registrarUsuario("Ana", "ana99", "Password1");
        Usuario u2 = gestor.registrarUsuario("Ana2", "ana99", "Password2");
        assertNull(u2);
    }

    @Test
    void registrarUsuarioConPasswordCortaFalla() {
        Usuario u = gestor.registrarUsuario("Bob", "bob01", "corta");
        assertNull(u);
    }

    @Test
    void registrarUsuarioConPasswordLargaFalla() {
        Usuario u = gestor.registrarUsuario("Bob", "bob01", "estapasswordeslargaaaaaa");
        assertNull(u);
    }

    @Test
    void numeroRegistroTieneFormatoLNNLL() {
        Usuario u = gestor.registrarUsuario("Carlos", "carlos1", "Segura123");
        assertNotNull(u);
        String num = u.getNumeroRegistro();
        assertEquals(5, num.length());
        assertTrue(Character.isLetter(num.charAt(0)));
        assertTrue(Character.isDigit(num.charAt(1)));
        assertTrue(Character.isDigit(num.charAt(2)));
        assertTrue(Character.isLetter(num.charAt(3)));
        assertTrue(Character.isLetter(num.charAt(4)));
    }

    @Test
    void dosUsuariosTienenNumerosDeRegistroDistintos() {
        Usuario u1 = gestor.registrarUsuario("U1", "nick1", "Password1");
        Usuario u2 = gestor.registrarUsuario("U2", "nick2", "Password2");
        assertNotNull(u1);
        assertNotNull(u2);
        // En la práctica colisión es casi imposible, pero el gestor lo garantiza
        assertNotEquals(u1.getNumeroRegistro(), u2.getNumeroRegistro(),
                "Los números de registro deben ser únicos");
    }

    // ── Dar de baja ──────────────────────────────────────────────────────────

    @Test
    void darDeBajaUsuarioExistente() {
        gestor.registrarUsuario("Dani", "dani1", "Clave1234");
        assertTrue(gestor.darDeBajaUsuario("dani1", "Clave1234"));
        assertNull(gestor.buscarUsuarioPorNick("dani1"));
    }

    @Test
    void darDeBajaConPasswordIncorrectaFalla() {
        gestor.registrarUsuario("Dani", "dani1", "Clave1234");
        assertFalse(gestor.darDeBajaUsuario("dani1", "equivocada"));
    }

    // ── Login ────────────────────────────────────────────────────────────────

    @Test
    void loginCorrecto() {
        gestor.registrarUsuario("Eva", "eva01", "Password1");
        Usuario u = gestor.loginUsuario("eva01", "Password1");
        assertNotNull(u);
        assertEquals("eva01", u.getNick());
    }

    @Test
    void loginConPasswordIncorrectaFalla() {
        gestor.registrarUsuario("Eva", "eva01", "Password1");
        assertNull(gestor.loginUsuario("eva01", "mala"));
    }

    @Test
    void loginConNickInexistenteFalla() {
        assertNull(gestor.loginUsuario("fantasma", "Password1"));
    }

    // ── Bloqueo ──────────────────────────────────────────────────────────────

    @Test
    void bloquearUsuarioImposibilitaLogin() {
        gestor.registrarUsuario("Fer", "fer1", "Password1");
        gestor.bloquearUsuario("fer1");
        assertNull(gestor.loginUsuario("fer1", "Password1"));
    }

    @Test
    void desbloquearUsuarioPermiteLogin() {
        gestor.registrarUsuario("Fer", "fer1", "Password1");
        gestor.bloquearUsuario("fer1");
        gestor.desbloquearUsuario("fer1");
        assertNotNull(gestor.loginUsuario("fer1", "Password1"));
    }

    // ── Operadores ───────────────────────────────────────────────────────────

    @Test
    void registrarOperadorCorrectamente() {
        var op = gestor.registrarOperador("Admin", "admin1", "AdminPass1");
        assertNotNull(op);
        assertEquals("admin1", op.getNick());
    }

    @Test
    void loginOperadorCorrecto() {
        gestor.registrarOperador("Admin", "admin1", "AdminPass1");
        var op = gestor.loginOperador("admin1", "AdminPass1");
        assertNotNull(op);
    }

    @Test
    void nickCompartidoEntreUsuarioYOperadorFalla() {
        gestor.registrarUsuario("Juan", "juan1", "Password1");
        var op = gestor.registrarOperador("Juan Op", "juan1", "AdminPass1");
        assertNull(op); // mismo nick ya en uso
    }

    // ── Ranking ──────────────────────────────────────────────────────────────

    @Test
    void rankingOrdenadoPorOroDescendente() {
        Usuario u1 = gestor.registrarUsuario("Rico", "rico1", "Password1");
        Usuario u2 = gestor.registrarUsuario("Pobre", "pobre1", "Password2");

        u1.setPersonaje(new Vampiro("V1", 5, 3, 500));
        u2.setPersonaje(new Cazador("C1", 5, 3, 100));

        var ranking = gestor.getRankingGlobal();
        assertEquals("rico1", ranking.get(0).getNick());
        assertEquals("pobre1", ranking.get(1).getNick());
    }

    // ── Asignar personaje ─────────────────────────────────────────────────────

    @Test
    void registrarPersonajeAsignaCorrectamente() {
        gestor.registrarUsuario("Greta", "greta1", "Password1");
        Vampiro v = new Vampiro("Vlad", 5, 4, 200);
        assertTrue(gestor.registrarPersonaje("greta1", v));
        assertEquals("Vlad", gestor.buscarUsuarioPorNick("greta1").getPersonaje().getNombre());
    }
}
