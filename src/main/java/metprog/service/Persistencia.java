package metprog.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import metprog.model.Combate;
import metprog.model.Desafio;
import metprog.model.Operador;
import metprog.model.Usuario;

/**
 * Gestiona la persistencia de datos entre ejecuciones mediante serialización Java.
 *
 * <p>Guarda y carga cuatro colecciones independientes: Usuarios, Operadores,
 * Desafíos y Combates en archivos con extensión .ser.
 */
public class Persistencia {

  private static final String DIR = "datos";
  private static final String USUARIOS = DIR + "/usuarios.ser";
  private static final String OPERADORES = DIR + "/operadores.ser";
  private static final String DESAFIOS = DIR + "/desafios.ser";
  private static final String COMBATES = DIR + "/combates.ser";

  /**
   * Constructor privado para evitar la instanciación de esta clase de utilidades.
   */
  private Persistencia() {}

  /**
   * Crea el directorio de datos si no existe.
   *
   * <p>Debe llamarse una vez al arrancar la aplicación para asegurar que las
   * rutas de guardado son válidas.
   */
  public static void inicializar() {
    try {
      Path dir = Paths.get(DIR);
      if (!Files.exists(dir)) {
        Files.createDirectories(dir);
      }
    } catch (IOException e) {
      System.err.println("Error al crear el directorio de datos: " + e.getMessage());
    }
  }

  /**
   * Guarda todas las colecciones en una sola operación.
   *
   * @param usuarios lista de usuarios.
   * @param operadores lista de operadores.
   * @param desafios lista de desafíos.
   * @param combates lista de combates.
   */
  public static void guardarTodo(List<Usuario> usuarios,
                                 List<Operador> operadores,
                                 List<Desafio> desafios,
                                 List<Combate> combates) {
    guardarUsuarios(usuarios);
    guardarOperadores(operadores);
    guardarDesafios(desafios);
    guardarCombates(combates);
  }

  /**
   * Serializa la lista de usuarios al disco.
   *
   * @param usuarios lista a guardar.
   */
  public static void guardarUsuarios(List<Usuario> usuarios) {
    guardar(usuarios, USUARIOS);
  }

  /**
   * Carga la lista de usuarios desde el disco.
   *
   * @return lista de usuarios, o lista vacía si el archivo no existe.
   */
  public static List<Usuario> cargarUsuarios() {
    return cargar(USUARIOS);
  }

  /**
   * Serializa la lista de operadores al disco.
   *
   * @param operadores lista a guardar.
   */
  public static void guardarOperadores(List<Operador> operadores) {
    guardar(operadores, OPERADORES);
  }

  /**
   * Carga la lista de operadores desde el disco.
   *
   * @return lista de operadores, o lista vacía si el archivo no existe.
   */
  public static List<Operador> cargarOperadores() {
    return cargar(OPERADORES);
  }

  /**
   * Serializa la lista de desafíos al disco.
   *
   * @param desafios lista a guardar.
   */
  public static void guardarDesafios(List<Desafio> desafios) {
    guardar(desafios, DESAFIOS);
  }

  /**
   * Carga la lista de desafíos desde el disco.
   *
   * @return lista de desafíos, o lista vacía si el archivo no existe.
   */
  public static List<Desafio> cargarDesafios() {
    return cargar(DESAFIOS);
  }

  /**
   * Serializa la lista de combates al disco.
   *
   * @param combates lista a guardar.
   */
  public static void guardarCombates(List<Combate> combates) {
    guardar(combates, COMBATES);
  }

  /**
   * Carga la lista de combates desde el disco.
   *
   * @return lista de combates, o lista vacía si el archivo no existe.
   */
  public static List<Combate> cargarCombates() {
    return cargar(COMBATES);
  }

  /**
   * Indica si existen datos de usuarios u operadores guardados.
   *
   * @return true si existen datos guardados previos.
   */
  public static boolean existenDatosGuardados() {
    return new File(USUARIOS).exists() || new File(OPERADORES).exists();
  }

  /**
   * Serializa cualquier lista al archivo indicado.
   *
   * @param lista lista a guardar.
   * @param ruta ruta del archivo de destino.
   */
  private static void guardar(List<?> lista, String ruta) {
    inicializar();
    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ruta))) {
      oos.writeObject(lista);
    } catch (IOException e) {
      System.err.println("Error al guardar " + ruta + ": " + e.getMessage());
    }
  }

  /**
   * Deserializa una lista desde el archivo indicado.
   *
   * @param <T> tipo de los elementos de la lista.
   * @param ruta ruta del archivo fuente.
   * @return la lista deserializada, o una lista vacía si no existe o hay error.
   */
  @SuppressWarnings("unchecked")
  private static <T> List<T> cargar(String ruta) {
    File archivo = new File(ruta);
    if (!archivo.exists()) {
      return new ArrayList<>();
    }
    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
      return (List<T>) ois.readObject();
    } catch (IOException | ClassNotFoundException e) {
      System.err.println("Error al cargar " + ruta + ": " + e.getMessage());
      return new ArrayList<>();
    }
  }
}