package metprog;

import metprog.model.*;
import metprog.service.Persistencia;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Tests de persistencia: verifica que los datos se guardan y cargan
 * correctamente entre ejecuciones.
 */
class PersistenciaTest {

    @BeforeEach
    void setUp() {
        Persistencia.inicializar();
    }

    @AfterEach
    void tearDown() {
        // Limpiar archivos de test para no contaminar ejecuciones futuras
        borrarSiExiste("datos/usuarios.ser");
        borrarSiExiste("datos/operadores.ser");
        borrarSiExiste("datos/desafios.ser");
        borrarSiExiste("datos/combates.ser");
    }

    private void borrarSiExiste(String ruta) {
        File f = new File(ruta);
        if (f.exists()) f.delete();
    }

    // ── Usuarios ─────────────────────────────────────────────────────────────

    @Test
    void guardarYCargarUsuariosMantieneLosDatos() {
        Usuario u = new Usuario("Pedro", "pedro1", "Password1");
        u.setPersonaje(new Vampiro("Nosferatu", 5, 3, 200));

        List<Usuario> lista = new ArrayList<>();
        lista.add(u);

        Persistencia.guardarUsuarios(lista);
        List<Usuario> cargados = Persistencia.cargarUsuarios();

        assertEquals(1, cargados.size());
        assertEquals("pedro1", cargados.get(0).getNick());
        assertEquals("Pedro", cargados.get(0).getNombre());
    }

    @Test
    void cargarUsuariosSinArchivoDevuelveListaVacia() {
        borrarSiExiste("datos/usuarios.ser");
        List<Usuario> resultado = Persistencia.cargarUsuarios();
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    @Test
    void usuarioConPersonajePersisteBien() {
        Usuario u = new Usuario("Laura", "laura1", "Password1");
        Vampiro v = new Vampiro("Conde", 5, 4, 300);
        Arma arma = new Arma("Espada", 2, 0, false);
        v.equiparArma(arma);
        v.setArmasActivas(List.of(arma));
        u.setPersonaje(v);

        Persistencia.guardarUsuarios(List.of(u));
        List<Usuario> cargados = Persistencia.cargarUsuarios();

        assertNotNull(cargados.get(0).getPersonaje());
        assertInstanceOf(Vampiro.class, cargados.get(0).getPersonaje());
        assertEquals("Conde", cargados.get(0).getPersonaje().getNombre());
    }

    @Test
    void historialOroSePersiste() {
        Usuario u = new Usuario("Marcos", "marcos1", "Password1");
        u.registrarGananciaOro(100, "Victoria test");
        u.registrarPerdidaOro(20, "Derrota test");

        Persistencia.guardarUsuarios(List.of(u));
        List<Usuario> cargados = Persistencia.cargarUsuarios();

        assertEquals(2, cargados.get(0).getHistorialOro().size());
        assertEquals(100, cargados.get(0).getHistorialOro().get(0).getCantidad());
        assertEquals(-20, cargados.get(0).getHistorialOro().get(1).getCantidad());
    }

    // ── Operadores ────────────────────────────────────────────────────────────

    @Test
    void guardarYCargarOperadoresMantieneLosDatos() {
        Operador op = new Operador("Admin", "admin1", "AdminPass1");
        Persistencia.guardarOperadores(List.of(op));
        List<Operador> cargados = Persistencia.cargarOperadores();
        assertEquals(1, cargados.size());
        assertEquals("admin1", cargados.get(0).getNick());
    }

    // ── Combates ─────────────────────────────────────────────────────────────

    @Test
    void guardarYCargarCombatesMantieneLosDatos() {
        Usuario u1 = new Usuario("U1", "u1", "Password1");
        Usuario u2 = new Usuario("U2", "u2", "Password2");
        Combate c = new Combate(u1, u2);
        c.setVencedor(u1);
        c.setRondasEmpleadas(4);
        c.setOroGanado(150);

        Persistencia.guardarCombates(List.of(c));
        List<Combate> cargados = Persistencia.cargarCombates();

        assertEquals(1, cargados.size());
        assertEquals("u1", cargados.get(0).getVencedor().getNick());
        assertEquals(4,   cargados.get(0).getRondasEmpleadas());
        assertEquals(150, cargados.get(0).getOroGanado());
    }

    @Test
    void combateConRondasLogPersisteBien() {
        Usuario u1 = new Usuario("A", "a1", "Password1");
        Usuario u2 = new Usuario("B", "b1", "Password2");
        Combate c  = new Combate(u1, u2);
        c.añadirRonda(new RondaCombate(1,
                3, 2, 2, 1, 2, 1, 3, 2,
                false, true, "Ronda 1: B recibe daño"));

        Persistencia.guardarCombates(List.of(c));
        List<Combate> cargados = Persistencia.cargarCombates();

        assertEquals(1, cargados.get(0).getRondas().size());
        assertEquals("Ronda 1: B recibe daño",
                cargados.get(0).getRondas().get(0).getDescripcion());
    }

    // ── Guardar todo de una vez ───────────────────────────────────────────────

    @Test
    void guardarTodoPersisteTodosLosArchivos() {
        List<Usuario>  usuarios   = List.of(new Usuario("X", "xx1", "Password1"));
        List<Operador> operadores = List.of(new Operador("Op", "op1", "AdminPass1"));
        List<Desafio>  desafios   = new ArrayList<>();
        List<Combate>  combates   = new ArrayList<>();

        assertDoesNotThrow(() ->
            Persistencia.guardarTodo(usuarios, operadores, desafios, combates));

        assertTrue(Persistencia.existenDatosGuardados());
        assertEquals(1, Persistencia.cargarUsuarios().size());
        assertEquals(1, Persistencia.cargarOperadores().size());
    }

    // ── Esbirros con estructura recursiva ─────────────────────────────────────

    @Test
    void esbirrosRecursivosSePersitenCorrectamente() {
        Usuario u = new Usuario("Amo", "amo1", "Password1");
        Vampiro v = new Vampiro("Conde", 5, 4, 100);

        EsbirroDemonio demonio = new EsbirroDemonio("Asmo", 3, "Pacto eterno");
        EsbirroGhoul   sub     = new EsbirroGhoul("Ghoulito", 2, 3);
        demonio.añadirSubEsbirro(sub);
        v.añadirEsbirro(demonio);
        u.setPersonaje(v);

        Persistencia.guardarUsuarios(List.of(u));
        List<Usuario> cargados = Persistencia.cargarUsuarios();

        Personaje pCargado = cargados.get(0).getPersonaje();
        assertEquals(1, pCargado.getEsbirros().size());
        EsbirroDemonio demCargado = (EsbirroDemonio) pCargado.getEsbirros().get(0);
        assertEquals(1, demCargado.getSubEsbirros().size());
        assertEquals("Ghoulito", demCargado.getSubEsbirros().get(0).getNombre());
    }
}