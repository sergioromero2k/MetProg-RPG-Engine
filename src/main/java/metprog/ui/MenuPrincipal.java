package metprog.ui;

import java.util.Scanner;
import metprog.model.Desafio;
import metprog.model.Operador;
import metprog.model.Usuario;
import metprog.service.GestorDesafios;
import metprog.service.GestorUsuarios;

/**
 * Punto de entrada principal de la interfaz de usuario por consola.
 *
 * <p>Gestiona el flujo inicial de la aplicación, permitiendo el acceso
 * diferenciado entre jugadores y operadores, así como el registro de nuevos usuarios.
 */
public class MenuPrincipal {

  private final GestorUsuarios gestorUsuarios;
  private final GestorDesafios gestorDesafios;
  private final Scanner scanner;

  /**
   * Constructor por defecto que inicializa los gestores y el escáner.
   */
  public MenuPrincipal() {
    this(new GestorUsuarios(), new GestorDesafios(), new Scanner(System.in));
  }

  /**
   * Constructor que permite inyectar un gestor de usuarios existente.
   *
   * @param gestorUsuarios instancia del gestor de usuarios.
   */
  public MenuPrincipal(GestorUsuarios gestorUsuarios) {
    this(gestorUsuarios, new GestorDesafios(), new Scanner(System.in));
  }

  /**
   * Constructor completo para inyección de dependencias.
   *
   * @param gestorUsuarios instancia del gestor de usuarios.
   * @param gestorDesafios instancia del gestor de desafíos.
   * @param scanner instancia de la utilidad de lectura.
   */
  public MenuPrincipal(GestorUsuarios gestorUsuarios, GestorDesafios gestorDesafios,
                       Scanner scanner) {
    this.gestorUsuarios = gestorUsuarios;
    this.gestorDesafios = gestorDesafios;
    this.scanner = scanner;
  }

  /**
   * Muestra el menú raíz y gestiona el bucle principal de ejecución.
   */
  public void mostrar() {
    boolean salir = false;

    while (!salir) {
      mostrarBienvenida();
      int opcion = leerOpcion();

      switch (opcion) {
        case 1:
          gestionarJugador();
          break;
        case 2:
          gestionarOperador();
          break;
        case 0:
          System.out.println("Hasta pronto.");
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

  /**
   * Gestiona el submenú de acceso para jugadores.
   */
  private void gestionarJugador() {
    boolean volver = false;

    while (!volver) {
      System.out.println("=== ACCESO JUGADOR ===");
      System.out.println("1. Iniciar sesión");
      System.out.println("2. Registrar usuario");
      System.out.println("0. Volver");
      System.out.print("Selecciona una opción: ");

      switch (leerOpcion()) {
        case 1:
          Usuario usuario = iniciarSesionUsuario();
          if (usuario != null) {
            Desafio pendiente = gestorDesafios.getDesafioPublicadoParaUsuario(usuario);
            if (pendiente != null) {
              System.out.println("AVISO: Tienes un desafío pendiente de "
                  + pendiente.getDesafiante().getNick());
            }
            new MenuJugador(gestorUsuarios, gestorDesafios, scanner, usuario).mostrar();
          }
          break;
        case 2:
          Usuario registrado = registrarUsuario();
          if (registrado != null) {
            new MenuJugador(gestorUsuarios, gestorDesafios, scanner, registrado).mostrar();
          }
          break;
        case 0:
          volver = true;
          break;
        default:
          System.out.println("Opción no válida.");
      }

      if (!volver) {
        System.out.println();
      }
    }
  }

  /**
   * Gestiona el submenú de acceso para operadores.
   */
  private void gestionarOperador() {
    boolean volver = false;

    while (!volver) {
      System.out.println("=== ACCESO OPERADOR ===");
      System.out.println("1. Iniciar sesión");
      System.out.println("2. Registrar operador");
      System.out.println("0. Volver");
      System.out.print("Selecciona una opción: ");

      switch (leerOpcion()) {
        case 1:
          Operador operador = iniciarSesionOperador();
          if (operador != null) {
            new MenuOperador(gestorUsuarios, gestorDesafios, scanner, operador).mostrar();
          }
          break;
        case 2:
          Operador registrado = registrarOperador();
          if (registrado != null) {
            new MenuOperador(gestorUsuarios, gestorDesafios, scanner, registrado).mostrar();
          }
          break;
        case 0:
          volver = true;
          break;
        default:
          System.out.println("Opción no válida.");
      }

      if (!volver) {
        System.out.println();
      }
    }
  }

  private Usuario iniciarSesionUsuario() {
    System.out.print("Nick: ");
    String nick = scanner.nextLine();
    System.out.print("Contraseña: ");
    String password = scanner.nextLine();

    Usuario u = gestorUsuarios.buscarUsuarioPorNick(nick);
    if (u == null) {
      System.out.println("Error: No existe ningún usuario con ese nick.");
      return null;
    }
    if (u.isBloqueado()) {
      System.out.println("Error: Tu cuenta está bloqueada. Contacta con el operador.");
      return null;
    }
    if (!u.getPassword().equals(password)) {
      System.out.println("Error: Contraseña incorrecta.");
      return null;
    }
    System.out.println("Bienvenido, " + u.getNick() + ".");
    return u;
  }

  private Usuario registrarUsuario() {
    System.out.print("Nombre: ");
    String nombre = scanner.nextLine();
    System.out.print("Nick: ");
    String nick = scanner.nextLine();
    System.out.print("Contraseña (entre 8 y 12 caracteres): ");
    String password = scanner.nextLine();

    if(password.length() < 8 || password.length() > 12) {
      System.out.println("Error: La contraseña debe tener entre 8 y 12 caracteres.");
      return null;
    }
    if (gestorUsuarios.buscarUsuarioPorNick(nick) != null) {
      System.out.println("Error: El nick ya está en uso.");
      return null;
    }

    Usuario usuario = gestorUsuarios.registrarUsuario(nombre, nick, password);
    if (usuario == null) {
      System.out.println("Error: No se ha podido registrar el usuario.");
    } else {
      System.out.println("Usuario registrado: " + usuario.getNick()
          + " | Número de registro: " + usuario.getNumeroRegistro());
    }
    return usuario;
  }

  private Operador iniciarSesionOperador() {
    System.out.print("Nick: ");
    String nick = scanner.nextLine();
    System.out.print("Contraseña: ");
    String password = scanner.nextLine();

    Operador op = gestorUsuarios.buscarOperadorPorNick(nick);
    if (op == null) {
      System.out.println("Error: No existe ningún operador con ese nick.");
      return null;
    }
    if (!op.getPassword().equals(password)) {
      System.out.println("Error: Contraseña incorrecta.");
      return null;
    }
    System.out.println("Bienvenido, operador " + op.getNick() + ".");
    return op;
  }

  private Operador registrarOperador() {
    System.out.print("Nombre: ");
    String nombre = scanner.nextLine();
    System.out.print("Nick: ");
    String nick = scanner.nextLine();
    System.out.print("Contraseña (entre 8 y 12 caracteres): ");
    String password = scanner.nextLine();

    if (password.length() < 8 || password.length() > 12) {
      System.out.println("Error: La contraseña debe tener entre 8 y 12 caracteres.");
      return null;
    }
    if (gestorUsuarios.buscarOperadorPorNick(nick) != null) {
      System.out.println("Error: El nick ya está en uso.");
      return null;
    }

    Operador operador = gestorUsuarios.registrarOperador(nombre, nick, password);
    if (operador == null) {
      System.out.println("Error: No se ha podido registrar el operador.");
    } else {
      System.out.println("Operador registrado: " + operador.getNick());
    }
    return operador;
  }

  private void mostrarBienvenida() {
    System.out.println("=== Bienvenido a MetProg RPG Engine ===");
    System.out.println("1. Jugar");
    System.out.println("2. Operador");
    System.out.println("0. Salir");
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