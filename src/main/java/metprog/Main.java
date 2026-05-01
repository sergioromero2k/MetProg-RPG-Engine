package metprog;

import java.util.Scanner;
import metprog.service.GestorDesafios;
import metprog.service.GestorUsuarios;
import metprog.service.Persistencia;
import metprog.observer.HistorialCombates;
import metprog.observer.InterfazJugador;
import metprog.observer.LoggerSistema;
import metprog.observer.ServicioNotificaciones;
import metprog.ui.MenuPrincipal;

public class Main {
  public static void main(String[] args) {

    // Inicializar carpeta de datos
    Persistencia.inicializar();

    // Crear gestores
    GestorUsuarios gestorUsuarios = new GestorUsuarios();
    GestorDesafios gestorDesafios = new GestorDesafios();
    ServicioNotificaciones servicioNotificaciones = new ServicioNotificaciones();

    servicioNotificaciones.suscribir(new LoggerSistema());
    servicioNotificaciones.suscribir(new HistorialCombates());
    servicioNotificaciones.suscribir(new InterfazJugador());

    gestorUsuarios.setServicioNotificaciones(servicioNotificaciones);
    gestorDesafios.setServicioNotificaciones(servicioNotificaciones);
    gestorDesafios.setGestorUsuarios(gestorUsuarios);

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