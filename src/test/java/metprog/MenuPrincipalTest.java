package metprog;

import metprog.model.Operador;
import metprog.model.Usuario;
import metprog.model.Vampiro;
import metprog.service.GestorDesafios;
import metprog.service.GestorUsuarios;
import metprog.ui.MenuPrincipal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MenuPrincipalTest extends MenuTestSupport {

    @Test
    @Timeout(2)
    void menuPrincipalToleraEntradasInesperadasYAbreAccesos() {
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
}