package metprog;

import java.util.Scanner;
import metprog.service.GestorDesafios;
import metprog.service.GestorUsuarios;
import metprog.service.Persistencia;
import metprog.ui.MenuPrincipal;

public class Main {
  public static void main(String[] args) {

    // Inicializar carpeta de datos
    Persistencia.inicializar();

    // Crear gestores
    GestorUsuarios gestorUsuarios = new GestorUsuarios();
    GestorDesafios gestorDesafios = new GestorDesafios();

    // Cargar datos si existen
    if (Persistencia.existenDatosGuardados()) {
      gestorUsuarios.setUsuarios(Persistencia.cargarUsuarios());
      gestorUsuarios.setOperadores(Persistencia.cargarOperadores());
      gestorDesafios.setDesafios(Persistencia.cargarDesafios());
      gestorDesafios.setHistorialCombates(Persistencia.cargarCombates());
    }

    // Lanzar menú principal
    MenuPrincipal menuPrincipal = new MenuPrincipal(gestorUsuarios, gestorDesafios, new Scanner(System.in));

    menuPrincipal.mostrar();

    // Guardar datos al salir
    Persistencia.guardarTodo(
        gestorUsuarios.getUsuarios(),
        gestorUsuarios.getOperadores(),
        gestorDesafios.getDesafios(),
        gestorDesafios.getHistorialCombates()
    );
  }
}