package metprog.ui;

import java.util.List;
import java.util.Scanner;
import metprog.factory.FabricaCazador;
import metprog.factory.FabricaLicantropo;
import metprog.factory.FabricaPersonaje;
import metprog.factory.FabricaVampiro;
import metprog.model.Combate;
import metprog.model.Desafio;
import metprog.model.Personaje;
import metprog.model.Usuario;
import metprog.service.GestorDesafios;
import metprog.service.GestorUsuarios;
import metprog.service.MotorCombate;
import metprog.service.Persistencia;

/**
 * Interfaz de usuario para el rol de Jugador.
 *
 * <p>Permite a los usuarios gestionar sus personajes, inventarios, lanzar
 * y responder a desafíos, y consultar estadísticas globales.
 */
public class MenuJugador {

  private final GestorUsuarios gestorUsuarios;
  private final GestorDesafios gestorDesafios;
  private final Scanner scanner;
  private final Usuario usuario;
  private final MotorCombate motorCombate;

  /**
   * Constructor obsoleto. Se recomienda usar el constructor con parámetros.
   */
  @Deprecated
  public MenuJugador() {
    this(new GestorUsuarios(), new GestorDesafios(), new Scanner(System.in), null);
  }

  /**
   * Constructor principal para el menú de jugador.
   *
   * @param gestorUsuarios instancia del gestor de usuarios.
   * @param gestorDesafios instancia del gestor de desafíos.
   * @param scanner utilidad para lectura de consola.
   * @param usuario usuario que ha iniciado la sesión.
   */
  public MenuJugador(GestorUsuarios gestorUsuarios,
                     GestorDesafios gestorDesafios,
                     Scanner scanner,
                     Usuario usuario) {
    this.gestorUsuarios = gestorUsuarios;
    this.gestorDesafios = gestorDesafios;
    this.scanner = scanner;
    this.usuario = usuario;
    this.motorCombate = new MotorCombate();
  }

  /**
   * Inicia el bucle de ejecución del menú de jugador.
   */
  public void mostrar() {
    boolean salir = false;

    while (!salir) {
      imprimirCabecera();
      int opcion = leerOpcion();

      switch (opcion) {
        case 1:
          crearPersonaje();
          break;
        case 2:
          verDatosPersonaje();
          break;
        case 3:
          gestionarArmas();
          break;
        case 4:
          gestionarArmaduras();
          break;
        case 5:
          gestionarEsbirros();
          break;
        case 6:
          lanzarDesafio();
          break;
        case 7:
          verDesafioRecibido();
          break;
        case 8:
          aceptarDesafioRecibido();
          break;
        case 9:
          rechazarDesafioRecibido();
          break;
        case 10:
          verRankingGlobal();
          break;
        case 11:
          verHistorialCombates();
          break;
        case 12:
          verHistorialOro();
          break;
        case 13:
          darDeBajaPersonaje();
          break;
        case 0:
          System.out.println("Cerrando sesión...");
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

  private void crearPersonaje() {
    if (usuario == null) {
      return;
    }
    if (usuario.getPersonaje() != null) {
      System.out.println("Ya tienes un personaje. Elimina el actual para crear uno nuevo.");
      return;
    }
    System.out.println("Elige tipo de personaje:");
    System.out.println("1. Vampiro");
    System.out.println("2. Licántropo");
    System.out.println("3. Cazador");
    System.out.print("Opción: ");
    int tipo = leerEntero();

    System.out.print("Nombre del personaje: ");
    String nombre = scanner.nextLine();

    FabricaPersonaje fabrica;
    switch (tipo) {
      case 1:
        fabrica = new FabricaVampiro();
        break;
      case 2:
        fabrica = new FabricaLicantropo();
        break;
      case 3:
        fabrica = new FabricaCazador();
        break;
      default:
        System.out.println("Tipo no válido.");
        return;
    }

    Personaje personaje = fabrica.crearPersonaje(nombre);
    if (gestorUsuarios.registrarPersonaje(usuario.getNick(), personaje)) {
      System.out.println("Personaje creado: " + personaje);
      Persistencia.guardarUsuarios(gestorUsuarios.getUsuarios());
    } else {
      System.out.println("No se ha podido crear el personaje.");
    }
  }

  private void verDatosPersonaje() {
    if (usuario == null || usuario.getPersonaje() == null) {
      System.out.println("No tienes personaje registrado.");
      return;
    }
    System.out.println(usuario.getPersonaje());
  }

  private void gestionarArmas() {
    if (usuario == null || usuario.getPersonaje() == null) {
      System.out.println("No tienes personaje registrado.");
      return;
    }
    Personaje p = usuario.getPersonaje();
    System.out.println("Armas en inventario:");
    if (p.getArmas().isEmpty()) {
      System.out.println("No tienes armas.");
      return;
    }
    for (int i = 0; i < p.getArmas().size(); i++) {
      System.out.println((i + 1) + ". " + p.getArmas().get(i));
    }
    System.out.println("Armas activas: " + p.getArmasActivas());
    System.out.println("\n¿Deseas cambiar las armas activas? (s/n)");
    String respuesta = scanner.nextLine().trim();
    if (!respuesta.equalsIgnoreCase("s")) {
      return;
    }

    System.out.println("Introduce los números de arma separados por coma (máximo 2): ");
    String entrada = scanner.nextLine().trim();
    List<Integer> indices = parsearIndices(entrada);
    if (indices.isEmpty()) {
      System.out.println("No se ha seleccionado ninguna arma válida.");
      return;
    }

    List<metprog.model.Arma> seleccion = new java.util.ArrayList<>();
    for (Integer indice : indices) {
      if (indice >= 1 && indice <= p.getArmas().size()) {
        metprog.model.Arma arma = p.getArmas().get(indice - 1);
        if (!seleccion.contains(arma)) {
          seleccion.add(arma);
        }
      }
    }

    if (p.setArmasActivas(seleccion)) {
      System.out.println("Armas activas actualizadas correctamente.");
      Persistencia.guardarUsuarios(gestorUsuarios.getUsuarios());
    }
  }

  private void gestionarArmaduras() {
    if (usuario == null || usuario.getPersonaje() == null) {
      System.out.println("No tienes personaje registrado.");
      return;
    }
    Personaje p = usuario.getPersonaje();
    System.out.println("Armaduras en inventario:");
    if (p.getArmaduras().isEmpty()) {
      System.out.println("No tienes armaduras.");
      return;
    }
    for (int i = 0; i < p.getArmaduras().size(); i++) {
      System.out.println((i + 1) + ". " + p.getArmaduras().get(i));
    }
    System.out.println("Armadura activa: " + p.getArmaduraActiva());
    System.out.println("\n¿Deseas cambiar la armadura activa? (s/n)");
    String respuesta = scanner.nextLine().trim();
    if (!respuesta.equalsIgnoreCase("s")) {
      return;
    }

    System.out.print("Introduce el número de armadura a activar: ");
    int indice = leerEntero();
    if (indice < 1 || indice > p.getArmaduras().size()) {
      System.out.println("Índice no válido.");
      return;
    }

    if (p.setArmaduraActiva(p.getArmaduras().get(indice - 1))) {
      System.out.println("Armadura activa actualizada correctamente.");
      Persistencia.guardarUsuarios(gestorUsuarios.getUsuarios());
    }
  }

  private void gestionarEsbirros() {
    if (usuario == null || usuario.getPersonaje() == null) {
      System.out.println("No tienes personaje registrado.");
      return;
    }
    Personaje p = usuario.getPersonaje();
    System.out.println("Esbirros:");
    if (p.getEsbirros().isEmpty()) {
      System.out.println("No tienes esbirros.");
      return;
    }
    for (int i = 0; i < p.getEsbirros().size(); i++) {
      System.out.println((i + 1) + ". " + p.getEsbirros().get(i));
    }
  }

  private void lanzarDesafio() {
    if (usuario == null) {
      System.out.println("No hay jugador conectado.");
      return;
    }
    System.out.print("Nick del desafiado: ");
    String nickDesafiado = scanner.nextLine();
    Usuario desafiado = gestorUsuarios.buscarUsuarioPorNick(nickDesafiado);
    if (desafiado == null) {
      System.out.println("No existe un usuario con ese nick.");
      return;
    }
    System.out.print("Oro apostado: ");
    int oroApostado = leerEntero();
    Desafio desafio = gestorDesafios.crearDesafio(usuario, desafiado, oroApostado);
    if (desafio == null) {
      System.out.println("No se ha podido crear el desafío.");
    } else {
      System.out.println("Desafío creado correctamente: " + desafio);
    }
  }

  private void verDesafioRecibido() {
    Desafio desafio = desafioPendienteDelUsuario();
    if (desafio == null) {
      System.out.println("No tienes ningún desafío pendiente.");
      return;
    }
    System.out.println(desafio);
  }

  private void aceptarDesafioRecibido() {
    Desafio desafio = desafioPendienteDelUsuario();
    if (desafio == null) {
      System.out.println("No tienes ningún desafío pendiente.");
      return;
    }
    if (gestorDesafios.aceptarDesafio(desafio)) {
      System.out.println("Desafío aceptado. Iniciando combate...");
      Combate combate = motorCombate.ejecutarCombate(desafio);
      gestorDesafios.finalizarDesafio(desafio, combate);
      System.out.println(combate.generarDetalleRondas());
    } else {
      System.out.println("No se ha podido aceptar el desafío.");
    }
  }

  private void rechazarDesafioRecibido() {
    Desafio desafio = desafioPendienteDelUsuario();
    if (desafio == null) {
      System.out.println("No tienes ningún desafío pendiente.");
      return;
    }
    int penalizacion = desafio.getOroApostado() / 10;
    System.out.println("Si rechazas el desafío se aplicará una penalización de "
        + penalizacion + " de oro (10%).");
    System.out.print("¿Confirmas que deseas rechazar el desafío? (s/n): ");
    String respuesta = scanner.nextLine().trim().toLowerCase();
    if (!respuesta.equals("s") && !respuesta.equals("si")) {
      System.out.println("Operación cancelada.");
      return;
    }

    gestorDesafios.rechazarDesafio(desafio);
    // Evitar mensaje duplicado: la notificación al InterfazJugador informará al usuario
  }

  private void verRankingGlobal() {
    List<Usuario> ranking = gestorUsuarios.getRankingGlobal();
    if (ranking.isEmpty()) {
      System.out.println("No hay jugadores en el ranking.");
      return;
    }
    System.out.println("=== RANKING GLOBAL ===");
    for (int i = 0; i < ranking.size(); i++) {
      Usuario u = ranking.get(i);
      System.out.println((i + 1) + ". " + u.getNick()
          + " | Oro: " + u.getPersonaje().getOro());
    }
  }

  private void verHistorialCombates() {
    // Preferir la fuente persistida cuando el usuario solicita ver el historial
    List<Combate> combates = Persistencia.cargarCombates();
    if (combates.isEmpty()) {
      System.out.println("No hay combates registrados.");
      return;
    }
    for (Combate c : combates) {
      System.out.println(c.generarResumen());
    }
  }

  private void verHistorialOro() {
    if (usuario == null) {
      return;
    }
    List<Usuario.RegistroOro> historial = usuario.getHistorialOro();
    if (historial.isEmpty()) {
      System.out.println("No hay registros de oro.");
      return;
    }
    System.out.println("=== HISTORIAL DE ORO ===");
    for (Usuario.RegistroOro r : historial) {
      System.out.println(r);
    }
  }

  private void darDeBajaPersonaje() {
    if (usuario == null || usuario.getPersonaje() == null) {
      System.out.println("No tienes personaje registrado.");
      return;
    }

    System.out.print("¿Seguro que quieres dar de baja tu personaje? (s/n): ");
    String confirmacion = scanner.nextLine().trim();
    if (!confirmacion.equalsIgnoreCase("s")) {
      System.out.println("Operación cancelada.");
      return;
    }

    if (gestorUsuarios.darDeBajaPersonaje(usuario.getNick())) {
      System.out.println("Personaje dado de baja correctamente.");
      Persistencia.guardarUsuarios(gestorUsuarios.getUsuarios());
    } else {
      System.out.println("No se ha podido dar de baja el personaje.");
    }
  }

  private Desafio desafioPendienteDelUsuario() {
    if (usuario == null) {
      return null;
    }
    return gestorDesafios.getDesafioPublicadoParaUsuario(usuario);
  }

  private void imprimirCabecera() {
    System.out.println("=== MENÚ JUGADOR ===");
    if (usuario != null) {
      System.out.println("Jugador: " + usuario.getNick());
    }
    System.out.println("1. Crear personaje");
    System.out.println("2. Ver datos de mi personaje");
    System.out.println("3. Gestionar armas");
    System.out.println("4. Gestionar armaduras");
    System.out.println("5. Gestionar esbirros");
    System.out.println("6. Lanzar desafío");
    System.out.println("7. Ver desafío recibido");
    System.out.println("8. Aceptar desafío recibido");
    System.out.println("9. Rechazar desafío recibido");
    System.out.println("10. Ver ranking global");
    System.out.println("11. Ver historial de combates");
    System.out.println("12. Ver historial de oro");
    System.out.println("13. Dar de baja mi personaje");
    System.out.println("0. Cerrar sesión");
    System.out.print("Selecciona una opción: ");
  }

  private List<Integer> parsearIndices(String entrada) {
    List<Integer> indices = new java.util.ArrayList<>();
    if (entrada == null || entrada.trim().isEmpty()) {
      return indices;
    }
    String[] partes = entrada.split(",");
    for (String parte : partes) {
      try {
        indices.add(Integer.parseInt(parte.trim()));
      } catch (NumberFormatException e) {
        // ignorar elementos no válidos
      }
    }
    return indices;
  }

  private int leerOpcion() {
    String entrada = scanner.nextLine();
    try {
      return Integer.parseInt(entrada);
    } catch (NumberFormatException e) {
      return -1;
    }
  }

  private int leerEntero() {
    String entrada = scanner.nextLine();
    try {
      return Integer.parseInt(entrada);
    } catch (NumberFormatException e) {
      return -1;
    }
  }
}