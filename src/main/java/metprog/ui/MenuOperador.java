package metprog.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import metprog.factory.FabricaCazador;
import metprog.factory.FabricaLicantropo;
import metprog.factory.FabricaPersonaje;
import metprog.factory.FabricaVampiro;
import metprog.model.Arma;
import metprog.model.Armadura;
import metprog.model.Debilidad;
import metprog.model.Desafio;
import metprog.model.Disciplina;
import metprog.model.Don;
import metprog.model.Esbirro;
import metprog.model.EsbirroDemonio;
import metprog.model.EsbirroGhoul;
import metprog.model.EsbirroHumano;
import metprog.model.HabilidadEspecial;
import metprog.model.Fortaleza;
import metprog.model.Licantropo;
import metprog.model.Personaje;
import metprog.model.Operador;
import metprog.model.Talento;
import metprog.model.Usuario;
import metprog.model.enums.Lealtad;
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
          gestionarPersonaje();
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

  private void gestionarPersonaje() {
    Usuario usuario = seleccionarUsuario();
    if (usuario == null) {
      System.out.println("No se ha seleccionado ningún usuario válido.");
      return;
    }
    if (usuario.getPersonaje() == null) {
      System.out.println("El usuario no tiene personaje.");
      System.out.println("¿Deseas crear uno ahora? (s/n)");
      if (!scanner.nextLine().trim().equalsIgnoreCase("s")) {
        return;
      }
      usuario.setPersonaje(crearPersonajeInteractivo());
      System.out.println("Personaje creado: "+ usuario.getPersonaje());
      return;
    }

    boolean volver = false;
    while (!volver) {
      Personaje personaje = usuario.getPersonaje();
      System.out.println("=== EDITAR PERSONAJE ===");
      System.out.println("Usuario: " + usuario.getNick());
      System.out.println(personaje);
      System.out.println("1. Cambiar nombre");
      System.out.println("2. Cambiar salud");
      System.out.println("3. Cambiar poder");
      System.out.println("4. Cambiar oro");
      System.out.println("5. Cambiar habilidad especial");
      System.out.println("6. Añadir arma");
      System.out.println("7. Añadir armadura");
      System.out.println("8. Activar armas");
      System.out.println("9. Activar armadura");
      System.out.println("10. Añadir fortaleza");
      System.out.println("11. Añadir debilidad");
      System.out.println("12. Añadir esbirro");
      System.out.println("13. Eliminar esbirro");
      System.out.println("14. Eliminar fortaleza");
      System.out.println("15. Eliminar debilidad");
      System.out.println("0. Volver");
      System.out.print("Selecciona una opción: ");

      switch (leerOpcion()) {
        case 1:
          System.out.print("Nuevo nombre: ");
          personaje.setNombre(scanner.nextLine());
          System.out.println("Nombre actualizado.");
          break;
        case 2:
          System.out.print("Nueva salud (0-5): ");
          personaje.setSalud(leerEntero());
          System.out.println("Salud actualizada.");
          break;
        case 3:
          System.out.print("Nuevo poder (1-5): ");
          personaje.setPoder(leerEntero());
          System.out.println("Poder actualizado.");
          break;
        case 4:
          System.out.print("Nuevo oro: ");
          personaje.setOro(leerEntero());
          System.out.println("Oro actualizado.");
          break;
        case 5:
          cambiarHabilidad(personaje);
          break;
        case 6:
          agregarArma(personaje);
          break;
        case 7:
          agregarArmadura(personaje);
          break;
        case 8:
          activarArmas(personaje);
          break;
        case 9:
          activarArmadura(personaje);
          break;
        case 10:
          agregarFortaleza(personaje);
          break;
        case 11:
          agregarDebilidad(personaje);
          break;
        case 12:
          agregarEsbirro(personaje);
          break;
        case 13:
          eliminarEsbirro(personaje);
          break;
        case 14:
          eliminarFortaleza(personaje);
          break;
        case 15:
          eliminarDebilidad(personaje);
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

  private Usuario seleccionarUsuario() {
    List<Usuario> usuarios = gestorUsuarios.getUsuarios();
    if (usuarios.isEmpty()) {
      return null;
    }
    for (int i = 0; i < usuarios.size(); i++) {
      Usuario u = usuarios.get(i);
      System.out.println((i + 1) + ". " + u.getNick() + " -> "
          + (u.getPersonaje() != null ? u.getPersonaje().getNombre() : "sin personaje"));
    }
    System.out.print("Selecciona un usuario: ");
    int indice = leerEntero() - 1;
    if (indice < 0 || indice >= usuarios.size()) {
      return null;
    }
    return usuarios.get(indice);
  }

  private Personaje crearPersonajeInteractivo() {
    System.out.println("Tipo de personaje:");
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
        System.out.println("Tipo no válido, se creará un cazador por defecto.");
        fabrica = new FabricaCazador();
        break;
    }
    return fabrica.crearPersonaje(nombre);
  }

  private void cambiarHabilidad(Personaje personaje) {
    System.out.println("Tipo de habilidad:");
    System.out.println("1. Talento");
    System.out.println("2. Don");
    System.out.println("3. Disciplina");
    System.out.print("Opción: ");
    int tipo = leerEntero();
    System.out.print("Nombre: ");
    String nombre = scanner.nextLine();
    System.out.print("Valor ataque (1-3): ");
    int valorAtaque = leerEntero();
    System.out.print("Valor defensa (1-3): ");
    int valorDefensa = leerEntero();

    HabilidadEspecial habilidad;
    switch (tipo) {
      case 1:
        habilidad = new Talento(nombre, valorAtaque, valorDefensa);
        break;
      case 2:
        System.out.print("Rabia mínima (0-3): ");
        habilidad = new Don(nombre, valorAtaque, valorDefensa, leerEntero());
        break;
      case 3:
        System.out.print("Coste de sangre (1-3): ");
        habilidad = new Disciplina(nombre, valorAtaque, valorDefensa, leerEntero());
        break;
      default:
        System.out.println("Tipo no válido.");
        return;
    }
    personaje.setHabilidad(habilidad);
    System.out.println("Habilidad actualizada.");
    Persistencia.guardarUsuarios(gestorUsuarios.getUsuarios());
  }

  private void agregarArma(Personaje personaje) {
    System.out.print("Nombre del arma: ");
    String nombre = scanner.nextLine();
    System.out.print("Mod. ataque (0-3): ");
    int modAtaque = leerEntero();
    System.out.print("Mod. defensa (0-3): ");
    int modDefensa = leerEntero();
    System.out.print("¿Es de dos manos? (s/n): ");
    boolean dosManos = scanner.nextLine().trim().equalsIgnoreCase("s");
    personaje.equiparArma(new Arma(nombre, modAtaque, modDefensa, dosManos));
    System.out.println("Arma añadida.");
    Persistencia.guardarUsuarios(gestorUsuarios.getUsuarios());
  }

  private void agregarArmadura(Personaje personaje) {
    System.out.print("Nombre de la armadura: ");
    String nombre = scanner.nextLine();
    System.out.print("Mod. ataque (0-3): ");
    int modAtaque = leerEntero();
    System.out.print("Mod. defensa (0-3): ");
    int modDefensa = leerEntero();
    personaje.agregarArmadura(new Armadura(nombre, modAtaque, modDefensa));
    System.out.println("Armadura añadida.");
    Persistencia.guardarUsuarios(gestorUsuarios.getUsuarios());
  }

  private void activarArmas(Personaje personaje) {
    List<Arma> armas = personaje.getArmas();
    if (armas.isEmpty()) {
      System.out.println("No hay armas para activar.");
      return;
    }
    for (int i = 0; i < armas.size(); i++) {
      System.out.println((i + 1) + ". " + armas.get(i));
    }
    System.out.print("Introduce índices separados por coma: ");
    List<Arma> seleccion = new ArrayList<>();
    for (Integer indice : leerIndices()) {
      if (indice >= 1 && indice <= armas.size()) {
        Arma arma = armas.get(indice - 1);
        if (!seleccion.contains(arma)) {
          seleccion.add(arma);
        }
      }
    }
    if (personaje.setArmasActivas(seleccion)) {
      System.out.println("Armas activas actualizadas.");
      Persistencia.guardarUsuarios(gestorUsuarios.getUsuarios());
    }
  }

  private void activarArmadura(Personaje personaje) {
    List<Armadura> armaduras = personaje.getArmaduras();
    if (armaduras.isEmpty()) {
      System.out.println("No hay armaduras para activar.");
      return;
    }
    for (int i = 0; i < armaduras.size(); i++) {
      System.out.println((i + 1) + ". " + armaduras.get(i));
    }
    System.out.print("Introduce el índice de la armadura: ");
    int indice = leerEntero();
    if (indice >= 1 && indice <= armaduras.size()) {
      if (personaje.setArmaduraActiva(armaduras.get(indice - 1))) {
        System.out.println("Armadura activa actualizada.");
        Persistencia.guardarUsuarios(gestorUsuarios.getUsuarios());
      }
    } else {
      System.out.println("Índice no válido.");
    }
  }

  private void agregarFortaleza(Personaje personaje) {
    personaje.agregarFortaleza(leerFortaleza());
    System.out.println("Fortaleza añadida.");
    Persistencia.guardarUsuarios(gestorUsuarios.getUsuarios());
  }

  private void agregarDebilidad(Personaje personaje) {
    personaje.agregarDebilidad(leerDebilidad());
    System.out.println("Debilidad añadida.");
    Persistencia.guardarUsuarios(gestorUsuarios.getUsuarios());
  }

  private void eliminarFortaleza(Personaje personaje) {
    if (personaje.getFortalezas().isEmpty()) {
      System.out.println("No hay fortalezas para eliminar.");
      return;
    }
    mostrarFortalezas(personaje);
    System.out.print("Índice de fortaleza a eliminar: ");
    int indice = leerEntero();
    if (indice >= 1 && indice <= personaje.getFortalezas().size()) {
      personaje.eliminarFortaleza(personaje.getFortalezas().get(indice - 1));
      System.out.println("Fortaleza eliminada.");
      Persistencia.guardarUsuarios(gestorUsuarios.getUsuarios());
    } else {
      System.out.println("Índice no válido.");
    }
  }

  private void eliminarDebilidad(Personaje personaje) {
    if (personaje.getDebilidades().isEmpty()) {
      System.out.println("No hay debilidades para eliminar.");
      return;
    }
    mostrarDebilidades(personaje);
    System.out.print("Índice de debilidad a eliminar: ");
    int indice = leerEntero();
    if (indice >= 1 && indice <= personaje.getDebilidades().size()) {
      personaje.eliminarDebilidad(personaje.getDebilidades().get(indice - 1));
      System.out.println("Debilidad eliminada.");
      Persistencia.guardarUsuarios(gestorUsuarios.getUsuarios());
    } else {
      System.out.println("Índice no válido.");
    }
  }

  private void agregarEsbirro(Personaje personaje) {
    System.out.println("Tipo de esbirro:");
    System.out.println("1. Humano");
    System.out.println("2. Ghoul");
    System.out.println("3. Demonio");
    System.out.print("Opción: ");
    int tipo = leerEntero();
    System.out.print("Nombre: ");
    String nombre = scanner.nextLine();
    System.out.print("Salud (1-3): ");
    int salud = leerEntero();

    Esbirro esbirro;
    switch (tipo) {
      case 1:
        System.out.println("Lealtad (ALTA, NORMAL, BAJA): ");
        Lealtad lealtad;
        try {
          lealtad = Lealtad.valueOf(scanner.nextLine().trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
          lealtad = Lealtad.NORMAL;
        }
        esbirro = new EsbirroHumano(nombre, salud, lealtad);
        break;
      case 2:
        System.out.print("Dependencia (1-5): ");
        esbirro = new EsbirroGhoul(nombre, salud, leerEntero());
        break;
      case 3:
        System.out.print("Descripción del pacto: ");
        esbirro = new EsbirroDemonio(nombre, salud, scanner.nextLine());
        break;
      default:
        System.out.println("Tipo no válido.");
        return;
    }

    try {
      personaje.agregarEsbirro(esbirro);
      System.out.println("Esbirro añadido.");
      Persistencia.guardarUsuarios(gestorUsuarios.getUsuarios());
    } catch (UnsupportedOperationException ex) {
      System.out.println("No se pudo añadir el esbirro: " + ex.getMessage());
    }
  }

  private void eliminarEsbirro(Personaje personaje) {
    if (personaje.getEsbirros().isEmpty()) {
      System.out.println("No hay esbirros para eliminar.");
      return;
    }
    for (int i = 0; i < personaje.getEsbirros().size(); i++) {
      System.out.println((i + 1) + ". " + personaje.getEsbirros().get(i));
    }
    System.out.print("Índice de esbirro a eliminar: ");
    int indice = leerEntero();
    if (indice >= 1 && indice <= personaje.getEsbirros().size()) {
      personaje.eliminarEsbirro(personaje.getEsbirros().get(indice - 1));
      System.out.println("Esbirro eliminado.");
      Persistencia.guardarUsuarios(gestorUsuarios.getUsuarios());
    } else {
      System.out.println("Índice no válido.");
    }
  }

  private List<Integer> leerIndices() {
    String entrada = scanner.nextLine();
    List<Integer> indices = new ArrayList<>();
    if (entrada == null || entrada.trim().isEmpty()) {
      return indices;
    }
    for (String parte : entrada.split(",")) {
      try {
        indices.add(Integer.parseInt(parte.trim()));
      } catch (NumberFormatException e) {
        // ignorar
      }
    }
    return indices;
  }

  private Fortaleza leerFortaleza() {
    System.out.print("Nombre de la fortaleza: ");
    String nombre = scanner.nextLine();
    System.out.print("Valor (1-5): ");
    return new Fortaleza(nombre, leerEntero());
  }

  private Debilidad leerDebilidad() {
    System.out.print("Nombre de la debilidad: ");
    String nombre = scanner.nextLine();
    System.out.print("Valor (1-5): ");
    return new Debilidad(nombre, leerEntero());
  }

  private void mostrarFortalezas(Personaje personaje) {
    for (int i = 0; i < personaje.getFortalezas().size(); i++) {
      System.out.println((i + 1) + ". " + personaje.getFortalezas().get(i));
    }
  }

  private void mostrarDebilidades(Personaje personaje) {
    for (int i = 0; i < personaje.getDebilidades().size(); i++) {
      System.out.println((i + 1) + ". " + personaje.getDebilidades().get(i));
    }
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
    System.out.println("11. Gestionar personaje");
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