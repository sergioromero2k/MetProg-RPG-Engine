package metprog;

import metprog.model.Desafio;
import metprog.ui.MenuOperador;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MenuOperadorTest extends MenuTestSupport {

    @Test
    @Timeout(2)
    void menuOperadorValidaYListaDesafiosConEntradasInvalidas() {
        EscenarioDesafio escenario = prepararEscenarioDesafio();

        ejecutarConSalida(() -> {
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

        assertEquals("Publicado", escenario.gestorDesafios.getDesafios().get(0).getEstado().getClass().getSimpleName());

        String salidaListados = ejecutarConSalida(() -> {
            MenuOperador menuOperador = new MenuOperador(
                    escenario.gestorUsuarios,
                    escenario.gestorDesafios,
                    crearScanner(String.join("\n",
                            "8",
                            "9",
                            "10",
                            "0") + "\n"),
                    escenario.operador);
            menuOperador.mostrar();
        });

        assertTrue(salidaListados.contains("Desafío["));
        assertTrue(salidaListados.contains("Combate") || salidaListados.contains("No hay combates registrados."));

        Desafio desafio = escenario.gestorDesafios.getDesafios().get(0);
        assertEquals("Publicado", desafio.getEstado().getClass().getSimpleName());
    }
}