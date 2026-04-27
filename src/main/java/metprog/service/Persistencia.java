package metprog.service;

import metprog.model.Combate;
import metprog.model.Desafio;
import metprog.model.Operador;
import metprog.model.Usuario;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Gestiona la persistencia de datos entre ejecuciones mediante serialización Java.
 *
 * <p>Guarda y carga cuatro colecciones independientes:
 * <ul>
 *   <li>Usuarios ({@code datos/usuarios.dat})</li>
 *   <li>Operadores ({@code datos/operadores.dat})</li>
 *   <li>Desafíos ({@code datos/desafios.dat})</li>
 *   <li>Combates ({@code datos/combates.dat})</li>
 * </ul>
 *
 * <p>Todos los métodos son estáticos para facilitar su uso sin instanciar la clase.
 */
public class Persistencia {

    private static final String DIR        = "datos";
    private static final String USUARIOS   = DIR + "/usuarios.dat";
    private static final String OPERADORES = DIR + "/operadores.dat";
    private static final String DESAFIOS   = DIR + "/desafios.dat";
    private static final String COMBATES   = DIR + "/combates.dat";

    // Constructor privado: clase de utilidades, no se instancia.
    private Persistencia() {}

    // ── Inicialización ────────────────────────────────────────────────────────

    /**
     * Crea el directorio de datos si no existe.
     * Debe llamarse una vez al arrancar la aplicación.
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

    // ── Guardar todo ─────────────────────────────────────────────────────────

    /**
     * Guarda todas las colecciones en una sola operación.
     *
     * @param usuarios   lista de usuarios.
     * @param operadores lista de operadores.
     * @param desafios   lista de desafíos.
     * @param combates   lista de combates.
     */
    public static void guardarTodo(List<Usuario>  usuarios,
                                   List<Operador> operadores,
                                   List<Desafio>  desafios,
                                   List<Combate>  combates) {
        guardarUsuarios(usuarios);
        guardarOperadores(operadores);
        guardarDesafios(desafios);
        guardarCombates(combates);
    }

    // ── Usuarios ─────────────────────────────────────────────────────────────

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

    // ── Operadores ────────────────────────────────────────────────────────────

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

    // ── Desafíos ──────────────────────────────────────────────────────────────

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

    // ── Combates ──────────────────────────────────────────────────────────────

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

    // ── Comprobaciones ────────────────────────────────────────────────────────

    /**
     * Indica si existe al menos el archivo de usuarios (señal de que ya se
     * realizó una ejecución anterior).
     *
     * @return {@code true} si existen datos guardados.
     */
    public static boolean existenDatosGuardados() {
        return new File(USUARIOS).exists()
                || new File(OPERADORES).exists();
    }

    // ── Métodos genéricos internos ────────────────────────────────────────────

    /**
     * Serializa cualquier lista al archivo indicado.
     *
     * @param lista lista a guardar.
     * @param ruta  ruta del archivo de destino.
     */
    private static void guardar(List<?> lista, String ruta) {
        inicializar(); // Asegura que el directorio existe
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(ruta))) {
            oos.writeObject(lista);
        } catch (IOException e) {
            System.err.println("Error al guardar " + ruta + ": " + e.getMessage());
        }
    }

    /**
     * Deserializa una lista desde el archivo indicado.
     *
     * @param ruta ruta del archivo fuente.
     * @param <T>  tipo de los elementos de la lista.
     * @return la lista deserializada, o una lista vacía si no existe o hay error.
     */
    @SuppressWarnings("unchecked")
    private static <T> List<T> cargar(String ruta) {
        File archivo = new File(ruta);
        if (!archivo.exists()) {
            return new ArrayList<>();
        }
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(archivo))) {
            return (List<T>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al cargar " + ruta + ": " + e.getMessage());
            return new ArrayList<>();
        }
    }
}