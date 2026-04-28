package metprog;

import metprog.model.Arma;
import metprog.model.Armadura;
import metprog.model.Desafio;
import metprog.model.Operador;
import metprog.model.Personaje;
import metprog.model.Usuario;
import metprog.model.Vampiro;
import metprog.model.Cazador;
import metprog.service.GestorDesafios;
import metprog.service.GestorUsuarios;
import metprog.ui.MenuJugador;
import metprog.ui.MenuOperador;
import metprog.ui.MenuPrincipal;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Pruebas de los flujos de menú por consola.
 *
 * <p>Verifica que los menús principales y secundarios toleran entradas
 * inesperadas y recorren los flujos de jugador, operador y desafíos sin lanzar
 * excepciones.
 */
class MenuFlujosTest {

    private PrintStream originalOut;

    @BeforeEach
    void guardarSalidaOriginal() {
        originalOut = System.out;
    }

    @AfterEach
    void restaurarSalidaOriginal() {
        System.setOut(originalOut);
    }

    @Test
    @Timeout(2)
    void menuPrincipalNoCrasheaConEntradasInesperadasYAccesos() {
        GestorUsuarios gestorUsuarios = new GestorUsuarios();
        GestorDesafios gestorDesafios = new GestorDesafios();

        Usuario usuario = gestorUsuarios.registrarUsuario("Ana", "ana1", "Password1");
        configurarPersonajeCompleto(usuario, new Vampiro("VampA", 5, 4, 500));
        Operador operador = gestorUsuarios.registrarOperador("Admin", "admin1", "AdminPass1");

        String entrada = String.join("\n",
                "abc",
                "1",
                "1",
                "ana1",
                "Password1",
                "0",
                "0",
                "2",
                "1",
                "admin1",
                "AdminPass1",
                "0",
                "0",
                "0") + "\n";

        String salida = ejecutarConSalida(() -> {
            MenuPrincipal menuPrincipal = new MenuPrincipal(
                    gestorUsuarios,
                    gestorDesafios,
                    crearScanner(entrada));
            menuPrincipal.mostrar();
        });

        assertTrue(salida.contains("Opción no válida."));
        assertTrue(salida.contains("Bienvenido, ana1."));
        assertTrue(salida.contains("Bienvenido, operador admin1."));
        assertTrue(salida.contains("Hasta pronto."));
        assertNotNull(usuario);
        assertNotNull(operador);
    }

    @Test
    @Timeout(2)
    void flujoCompletoDeDesafioAceptadoDesdeMenus() {
        EscenarioDesafio escenario = prepararEscenarioDesafio();

        String salidaJugador = ejecutarConSalida(() -> {
            MenuJugador menuJugador = new MenuJugador(
                    escenario.gestorUsuarios,
                    escenario.gestorDesafios,
                    crearScanner(String.join("\n",
                            "xyz",
                            "6",
                            "beta1",
                            "100",
                            "0") + "\n"),
                    escenario.desafiante);
            menuJugador.mostrar();
        });

        assertTrue(salidaJugador.contains("Opción no válida."));
        assertTrue(salidaJugador.contains("Desafío creado correctamente"));

        String salidaOperador = ejecutarConSalida(() -> {
            MenuOperador menuOperador = new MenuOperador(
                    escenario.gestorUsuarios,
                    escenario.gestorDesafios,
                    crearScanner(String.join("\n",
                            "7",
                            "1",
                            "noche:3,ruido:dos",
                            "sol:1",
                            "frio:2",
                            "vacio:x",
                            "0") + "\n"),
                    escenario.operador);
            menuOperador.mostrar();
        });

        assertTrue(salidaOperador.contains("Desafío validado y publicado."));

        String salidaDesafiado = ejecutarConSalida(() -> {
            MenuJugador menuJugador = new MenuJugador(
                    escenario.gestorUsuarios,
                    escenario.gestorDesafios,
                    crearScanner(String.join("\n",
                            "abc",
                            "7",
                            "8",
                            "0") + "\n"),
                    escenario.desafiado);
            menuJugador.mostrar();
        });

        assertTrue(salidaDesafiado.contains("Opción no válida."));
        assertTrue(salidaDesafiado.contains("Desafío aceptado."));
        assertEquals("EnCombate", escenario.gestorDesafios.getDesafios().get(0).getEstado().getClass().getSimpleName());
    }

    @Test
    @Timeout(2)
    void flujoCompletoDeDesafioRechazadoDesdeMenus() {
        EscenarioDesafio escenario = prepararEscenarioDesafio();

        String salidaJugador = ejecutarConSalida(() -> {
            MenuJugador menuJugador = new MenuJugador(
                    escenario.gestorUsuarios,
                    escenario.gestorDesafios,
                    crearScanner(String.join("\n",
                            "6",
                            "beta1",
                            "100",
                            "0") + "\n"),
                    escenario.desafiante);
            menuJugador.mostrar();
        });

        assertTrue(salidaJugador.contains("Desafío creado correctamente"));

        String salidaOperador = ejecutarConSalida(() -> {
            MenuOperador menuOperador = new MenuOperador(
                    escenario.gestorUsuarios,
                    escenario.gestorDesafios,
                    crearScanner(String.join("\n",
                            "7",
                            "1",
                            "","","","",
                            "0") + "\n"),
                    escenario.operador);
            menuOperador.mostrar();
        });

        assertTrue(salidaOperador.contains("Desafío validado y publicado."));

        String salidaDesafiado = ejecutarConSalida(() -> {
            MenuJugador menuJugador = new MenuJugador(
                    escenario.gestorUsuarios,
                    escenario.gestorDesafios,
                    crearScanner(String.join("\n",
                            "9",
                            "0") + "\n"),
                    escenario.desafiado);
            menuJugador.mostrar();
        });

        assertTrue(salidaDesafiado.contains("Desafío rechazado."));
        assertEquals("Rechazado", escenario.gestorDesafios.getDesafios().get(0).getEstado().getClass().getSimpleName());
    }

    private String ejecutarConSalida(Runnable accion) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream captura = new PrintStream(baos, true, StandardCharsets.UTF_8);
        System.setOut(captura);
        accion.run();
        captura.flush();
        return baos.toString(StandardCharsets.UTF_8);
    }

    private Scanner crearScanner(String entrada) {
        return new Scanner(new ByteArrayInputStream(entrada.getBytes(StandardCharsets.UTF_8)));
    }

    private void configurarPersonajeCompleto(Usuario usuario, Personaje personaje) {
        Arma arma = new Arma("Espada", 2, 0, false);
        Armadura armadura = new Armadura("Escudo", 0, 2);
        personaje.equiparArma(arma);
        personaje.añadirArmadura(armadura);
        personaje.setArmasActivas(List.of(arma));
        personaje.setArmaduraActiva(armadura);
        usuario.setPersonaje(personaje);
    }

    private EscenarioDesafio prepararEscenarioDesafio() {
        GestorUsuarios gestorUsuarios = new GestorUsuarios();
        GestorDesafios gestorDesafios = new GestorDesafios();

        Usuario desafiante = gestorUsuarios.registrarUsuario("Alpha", "alpha1", "Password1");
        Usuario desafiado = gestorUsuarios.registrarUsuario("Beta", "beta1", "Password2");
        Operador operador = gestorUsuarios.registrarOperador("Admin", "admin1", "AdminPass1");

        configurarPersonajeCompleto(desafiante, new Vampiro("VampA", 5, 4, 500));
        configurarPersonajeCompleto(desafiado, new Cazador("CazB", 5, 3, 300));

        return new EscenarioDesafio(gestorUsuarios, gestorDesafios, desafiante, desafiado, operador);
    }

    private static class EscenarioDesafio {
        private final GestorUsuarios gestorUsuarios;
        private final GestorDesafios gestorDesafios;
        private final Usuario desafiante;
        private final Usuario desafiado;
        private final Operador operador;

        private EscenarioDesafio(GestorUsuarios gestorUsuarios,
                                 GestorDesafios gestorDesafios,
                                 Usuario desafiante,
                                 Usuario desafiado,
                                 Operador operador) {
            this.gestorUsuarios = gestorUsuarios;
            this.gestorDesafios = gestorDesafios;
            this.desafiante = desafiante;
            this.desafiado = desafiado;
            this.operador = operador;
        }
    }
}