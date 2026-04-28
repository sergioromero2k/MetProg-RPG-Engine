package metprog.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import metprog.model.Debilidad;
import metprog.model.Desafio;
import metprog.model.Fortaleza;
import metprog.model.Operador;
import metprog.model.Usuario;
import metprog.service.GestorDesafios;
import metprog.service.GestorUsuarios;
import metprog.service.Persistencia;

/**
 * Interfaz de usuario para el rol de Operador.
 *
 * <p>Proporciona herramientas para la gestión de usuarios, validación de desafíos,
 * control de bloqueos y administración de la persistencia del sistema.
 */
public class MenuOperador {

  private final GestorUsuarios gestorUsuarios;
  private final GestorDesafios gestorDesafios;
  private final Scanner scanner;
  private final Operador operador;

  /**
   * Constructor obsoleto. Se recomienda usar el constructor con parámetros.
   */
  @Deprecated
  public MenuOperador() {
    this(new GestorUsuarios(), new GestorDesafios(), new Scanner(System.in), null);
  }

  /**
   * Constructor principal para el menú de operador.
   *
   * @param gestorUsuarios el gestor para la administración de cuentas.
   * @param gestorDesafios el gestor para la lógica de desafíos.
   * @param scanner la utilidad para lectura de consola.
   * @param operador el usuario con privilegios que ha iniciado sesión.
   */
  public MenuOperador(GestorUsuarios gestorUsuarios,
                      GestorDesafios gestorDesafios,
                      Scanner scanner,
                      Operador operador) {
    this.gestorUsuarios = gestorUsuarios;
    this.gestorDesafios = gestorDesafios;
    this.scanner = scanner;
    this.operador = operador;
  }

  /**
   * Inicia el bucle de ejecución del menú de operador.
   */
  public void mostrar() {
    boolean salir = false;

    while (!salir) {
      imprimirCabecera();
      int opcion = leerOpcion();

      switch (opcion) {
        case 1:
          registrarOperador();
          break;
        case 2:
          registrarUsuario();
          break;
        case 3:
          darDeBajaUsuario();
          break;
        case 4:
          darDeBajaOperador();
          break;
        case 5:
          bloquearUsuario();
          break;
        case 6:
          desbloquearUsuario();
          break;
        case 7:
          validarDesafio();
          break;
        case 8:
          verDesafiosPendientes();
          break;
        case 9:
          verDesafiosRegistrados();
          break;
        case 10:
          verHistorialCombates();
          break;
        case 11:
          guardarDatos();
          break;
        case 12:
          cargarDatos();
          break;
        case 0:
          System.out.println("Cerrando sesión de operador...");
          salir = true;
          break;
        default:
          System.out.println("Opción no válida.");
      }

      if (!salir) {
        System.out.println();
      }
    }
  }

  private void registrarOperador() {
    System.out.print("Nombre: ");
    String nombre = scanner.nextLine();
    System.out.print("Nick: ");
    String nick = scanner.nextLine();
    System.out.print("Contraseña (8-12 caracteres): ");
    String password = scanner.nextLine();
    Operador op = gestorUsuarios.registrarOperador(nombre, nick, password);
    if (op == null) {
      System.out.println("No se ha podido registrar el operador.");
    } else {
      System.out.println("Operador registrado: " + op.getNick());
    }
  }

  private void registrarUsuario() {
    System.out.print("Nombre: ");
    String nombre = scanner.nextLine();
    System.out.print("Nick: ");
    String nick = scanner.nextLine();
    System.out.print("Contraseña (8-12 caracteres): ");
    String password = scanner.nextLine();
    Usuario u = gestorUsuarios.registrarUsuario(nombre, nick, password);
    if (u == null) {
      System.out.println("No se ha podido registrar el usuario.");
    } else {
      System.out.println("Usuario registrado: " + u.getNick()
          + " | Número de registro: " + u.getNumeroRegistro());
    }
  }

  private void darDeBajaUsuario() {
    System.out.print("Nick del usuario: ");
    String nick = scanner.nextLine();
    System.out.print("Contraseña: ");
    String password = scanner.nextLine();
    if (gestorUsuarios.darDeBajaUsuario(nick, password)) {
      System.out.println("Usuario dado de baja correctamente.");
    } else {
      System.out.println("No se ha podido dar de baja al usuario.");
    }
  }

  private void darDeBajaOperador() {
    System.out.print("Nick del operador: ");
    String nick = scanner.nextLine();
    System.out.print("Contraseña: ");
    String password = scanner.nextLine();
    if (gestorUsuarios.darDeBajaOperador(nick, password)) {
      System.out.println("Operador dado de baja correctamente.");
    } else {
      System.out.println("No se ha podido dar de baja al operador.");
    }
  }

  private void bloquearUsuario() {
    System.out.print("Nick del usuario a bloquear: ");
    String nick = scanner.nextLine();
    gestorUsuarios.bloquearUsuario(nick);
    System.out.println("Usuario bloqueado.");
  }

  private void desbloquearUsuario() {
    System.out.print("Nick del usuario a desbloquear: ");
    String nick = scanner.nextLine();
    gestorUsuarios.desbloquearUsuario(nick);
    System.out.println("Usuario desbloqueado.");
  }

  private void validarDesafio() {
    Desafio desafio = seleccionarDesafioPendiente();
    if (desafio == null) {
      System.out.println("No hay desafíos pendientes para validar.");
      return;
    }
    System.out.println("Introduce modificadores en formato nombre:valor separados por coma.");
    System.out.print("Fortalezas del desafiante: ");
    List<Fortaleza> fortDesafiante = leerFortalezas();
    System.out.print("Debilidades del desafiante: ");
    List<Debilidad> debDesafiante = leerDebilidades();
    System.out.print("Fortalezas del desafiado: ");
    List<Fortaleza> fortDesafiado = leerFortalezas();
    System.out.print("Debilidades del desafiado: ");
    List<Debilidad> debDesafiado = leerDebilidades();

    if (gestorDesafios.validarDesafio(desafio, fortDesafiante, debDesafiante,
        fortDesafiado, debDesafiado)) {
      System.out.println("Desafío validado y publicado.");
    } else {
      System.out.println("No se ha podido validar el desafío.");
    }
  }

  private void verDesafiosPendientes() {
    List<Desafio> lista = gestorDesafios.getDesafiosPendientes();
    if (lista.isEmpty()) {
      System.out.println("No hay desafíos pendientes.");
      return;
    }
    for (int i = 0; i < lista.size(); i++) {
      System.out.println((i + 1) + ". " + lista.get(i));
    }
  }

  private void verDesafiosRegistrados() {
    List<Desafio> lista = gestorDesafios.getDesafios();
    if (lista.isEmpty()) {
      System.out.println("No hay desafíos registrados.");
      return;
    }
    for (int i = 0; i < lista.size(); i++) {
      System.out.println((i + 1) + ". " + lista.get(i));
    }
  }

  private void verHistorialCombates() {
    List<metprog.model.Combate> combates = gestorDesafios.getHistorialCombates();
    if (combates.isEmpty()) {
      System.out.println("No hay combates registrados.");
      return;
    }
    for (int i = 0; i < combates.size(); i++) {
      System.out.println((i + 1) + ". " + combates.get(i));
    }
  }

  private void guardarDatos() {
    Persistencia.guardarTodo(
        gestorUsuarios.getUsuarios(),
        gestorUsuarios.getOperadores(),
        gestorDesafios.getDesafios(),
        gestorDesafios.getHistorialCombates()
    );
    System.out.println("Datos guardados correctamente.");
  }

  private void cargarDatos() {
    gestorUsuarios.setUsuarios(Persistencia.cargarUsuarios());
    gestorUsuarios.setOperadores(Persistencia.cargarOperadores());
    gestorDesafios.setDesafios(Persistencia.cargarDesafios());
    gestorDesafios.setHistorialCombates(Persistencia.cargarCombates());
    System.out.println("Datos cargados correctamente.");
  }

  private Desafio seleccionarDesafioPendiente() {
    List<Desafio> lista = gestorDesafios.getDesafiosPendientes();
    if (lista.isEmpty()) {
      return null;
    }
    for (int i = 0; i < lista.size(); i++) {
      System.out.println((i + 1) + ". " + lista.get(i));
    }
    System.out.print("Selecciona un desafío: ");
    int indice = leerEntero() - 1;
    if (indice < 0 || indice >= lista.size()) {
      return null;
    }
    return lista.get(indice);
  }

  private List<Fortaleza> leerFortalezas() {
    String entrada = scanner.nextLine().trim();
    List<Fortaleza> resultado = new ArrayList<>();
    if (entrada.isEmpty()) {
      return resultado;
    }
    for (String parte : entrada.split(",")) {
      String[] trozos = parte.trim().split(":");
      if (trozos.length == 2) {
        Integer valor = parseEnteroSeguro(trozos[1].trim());
        if (valor != null) {
          resultado.add(new Fortaleza(trozos[0].trim(), valor));
        }
      }
    }
    return resultado;
  }

  private List<Debilidad> leerDebilidades() {
    String entrada = scanner.nextLine().trim();
    List<Debilidad> resultado = new ArrayList<>();
    if (entrada.isEmpty()) {
      return resultado;
    }
    for (String parte : entrada.split(",")) {
      String[] trozos = parte.trim().split(":");
      if (trozos.length == 2) {
        Integer valor = parseEnteroSeguro(trozos[1].trim());
        if (valor != null) {
          resultado.add(new Debilidad(trozos[0].trim(), valor));
        }
      }
    }
    return resultado;
  }

  private Integer parseEnteroSeguro(String texto) {
    try {
      return Integer.parseInt(texto);
    } catch (NumberFormatException e) {
      return null;
    }
  }

  private int leerEntero() {
    String entrada = scanner.nextLine();
    Integer valor = parseEnteroSeguro(entrada);
    return valor != null ? valor : -1;
  }

  private void imprimirCabecera() {
    System.out.println("=== MENÚ OPERADOR ===");
    if (operador != null) {
      System.out.println("Operador conectado: " + operador.getNick());
    }
    System.out.println("1. Registrar operador");
    System.out.println("2. Registrar usuario");
    System.out.println("3. Dar de baja usuario");
    System.out.println("4. Dar de baja operador");
    System.out.println("5. Bloquear usuario");
    System.out.println("6. Desbloquear usuario");
    System.out.println("7. Validar desafío");
    System.out.println("8. Ver desafíos pendientes");
    System.out.println("9. Ver desafíos registrados");
    System.out.println("10. Ver historial de combates");
    System.out.println("11. Guardar datos");
    System.out.println("12. Cargar datos");
    System.out.println("0. Cerrar sesión");
    System.out.print("Selecciona una opción: ");
  }

  private int leerOpcion() {
    String entrada = scanner.nextLine();
    try {
      return Integer.parseInt(entrada);
    } catch (NumberFormatException e) {
      return -1;
    }
  }
}