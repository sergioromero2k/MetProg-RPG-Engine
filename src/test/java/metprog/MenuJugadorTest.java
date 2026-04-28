package metprog;

import metprog.model.Desafio;
import metprog.model.Usuario;
import metprog.ui.MenuOperador;
import metprog.ui.MenuJugador;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MenuJugadorTest extends MenuTestSupport {

    @Test
    @Timeout(2)
    void menuJugadorPuedeCrearAceptarYRechazarDesafiosConEntradasInvalidas() {
        EscenarioDesafio escenario = prepararEscenarioDesafio();

        String salidaCreacion = ejecutarConSalida(() -> {
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

        assertTrue(salidaCreacion.contains("Opción no válida."));
        assertTrue(salidaCreacion.contains("Desafío creado correctamente"));

        String salidaValidacion = ejecutarConSalida(() -> {
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

        assertTrue(salidaValidacion.contains("Desafío validado y publicado."));

        String salidaAceptacion = ejecutarConSalida(() -> {
            MenuJugador menuJugador = new MenuJugador(
                    escenario.gestorUsuarios,
                    escenario.gestorDesafios,
                    crearScanner(String.join("\n",
                            "abc",
                            "8",
                            "0") + "\n"),
                    escenario.desafiado);
            menuJugador.mostrar();
        });

        assertTrue(salidaAceptacion.contains("Opción no válida."));
        assertTrue(salidaAceptacion.contains("Desafío aceptado."));
        assertEquals("EnCombate", escenario.gestorDesafios.getDesafios().get(0).getEstado().getClass().getSimpleName());

        EscenarioDesafio escenarioRechazo = prepararEscenarioDesafio();

        ejecutarConSalida(() -> {
            MenuJugador menuJugador = new MenuJugador(
                    escenarioRechazo.gestorUsuarios,
                    escenarioRechazo.gestorDesafios,
                    crearScanner(String.join("\n",
                            "6",
                            "beta1",
                            "100",
                            "0") + "\n"),
                    escenarioRechazo.desafiante);
            menuJugador.mostrar();
        });

        escenarioRechazo.gestorDesafios.validarDesafio(
                escenarioRechazo.gestorDesafios.getDesafios().get(0),
                java.util.List.of(), java.util.List.of(), java.util.List.of(), java.util.List.of());

        String salidaRechazo = ejecutarConSalida(() -> {
            MenuJugador menuJugador = new MenuJugador(
                    escenarioRechazo.gestorUsuarios,
                    escenarioRechazo.gestorDesafios,
                    crearScanner(String.join("\n",
                            "9",
                            "0") + "\n"),
                    escenarioRechazo.desafiado);
            menuJugador.mostrar();
        });

        assertTrue(salidaRechazo.contains("Desafío rechazado."));
        assertEquals("Rechazado", escenarioRechazo.gestorDesafios.getDesafios().get(0).getEstado().getClass().getSimpleName());
    }
}