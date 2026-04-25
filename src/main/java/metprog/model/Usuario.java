package metprog.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Representa a un jugador registrado en el sistema.
 *
 * <p>Cada usuario posee un número de registro único generado automáticamente,
 * un personaje asociado y un historial de transacciones de oro.
 */
public class Usuario implements Serializable {

  private static final long serialVersionUID = 1L;

  private String nombre;
  private String nick;
  private String password;
  private String numeroRegistro;
  private Personaje personaje;
  private boolean bloqueado;
  private LocalDateTime ultimaDerrota;

  /** Historial de combates del usuario (pares oro ganado/perdido). */
  private List<RegistroOro> historialOro = new ArrayList<>();

  /**
   * Construye un nuevo usuario con los datos proporcionados.
   *
   * @param nombre nombre real del usuario.
   * @param nick alias único en el sistema.
   * @param password contraseña (8-12 caracteres).
   */
  public Usuario(String nombre, String nick, String password) {
    setNombre(nombre);
    setNick(nick);
    setPassword(password);
    this.numeroRegistro = generarNumeroRegistro();
    this.bloqueado = false;
  }

  /**
   * Obtiene el nombre real del usuario.
   *
   * @return el nombre almacenado.
   */
  public String getNombre() {
    return nombre;
  }

  /**
   * Establece el nombre real validando que no esté vacío.
   *
   * @param nombre el nombre a asignar.
   */
  public void setNombre(String nombre) {
    if (nombre != null && !nombre.trim().isEmpty()) {
      this.nombre = nombre;
    } else {
      System.out.println("Error: El nombre no puede estar vacío.");
      this.nombre = "Sin nombre";
    }
  }

  /**
   * Obtiene el nick del usuario.
   *
   * @return el alias único.
   */
  public String getNick() {
    return nick;
  }

  /**
   * Establece el nick del usuario.
   *
   * @param nick el nuevo nick.
   */
  public void setNick(String nick) {
    if (nick != null && !nick.trim().isEmpty()) {
      this.nick = nick;
    } else {
      System.out.println("Error: El nick no puede estar vacío.");
      this.nick = "Sin nick";
    }
  }

  /**
   * Obtiene la contraseña del usuario.
   *
   * @return la contraseña actual.
   */
  public String getPassword() {
    return password;
  }

  /**
   * Establece la contraseña validando la longitud permitida.
   *
   * @param password cadena de entre 8 y 12 caracteres.
   * @throws IllegalArgumentException si la longitud no es válida.
   */
  public void setPassword(String password) {
    if (password != null && password.length() >= 8 && password.length() <= 12) {
      this.password = password;
    } else {
      throw new IllegalArgumentException("La contraseña debe tener entre 8 y 12 caracteres.");
    }
  }

  /**
   * Obtiene el número de registro automático.
   *
   * @return el código LNNLL.
   */
  public String getNumeroRegistro() {
    return numeroRegistro;
  }

  /**
   * Obtiene el personaje asociado al usuario.
   *
   * @return la instancia del personaje.
   */
  public Personaje getPersonaje() {
    return personaje;
  }

  /**
   * Asigna un personaje al usuario.
   *
   * @param personaje el personaje creado.
   */
  public void setPersonaje(Personaje personaje) {
    this.personaje = personaje;
  }

  /**
   * Indica si el usuario está bloqueado por el administrador.
   *
   * @return true si está bloqueado.
   */
  public boolean isBloqueado() {
    return bloqueado;
  }

  /**
   * Cambia el estado de bloqueo del usuario.
   *
   * @param bloqueado nuevo estado de bloqueo.
   */
  public void setBloqueado(boolean bloqueado) {
    this.bloqueado = bloqueado;
  }

  /**
   * Obtiene la fecha y hora de la última derrota registrada.
   *
   * @return objeto LocalDateTime de la derrota.
   */
  public LocalDateTime getUltimaDerrota() {
    return ultimaDerrota;
  }

  /**
   * Establece la fecha de la última derrota.
   *
   * @param ultimaDerrota instante de la derrota.
   */
  public void setUltimaDerrota(LocalDateTime ultimaDerrota) {
    this.ultimaDerrota = ultimaDerrota;
  }

  /**
   * Obtiene el historial completo de transacciones de oro.
   *
   * @return lista de registros de oro.
   */
  public List<RegistroOro> getHistorialOro() {
    return historialOro;
  }

  /**
   * Registra una entrada positiva en el historial de oro.
   *
   * @param cantidad cantidad de oro ganado.
   * @param descripcion motivo de la ganancia.
   */
  public void registrarGananciaOro(int cantidad, String descripcion) {
    historialOro.add(new RegistroOro(cantidad, descripcion, LocalDateTime.now()));
  }

  /**
   * Registra una entrada negativa en el historial de oro.
   *
   * @param cantidad cantidad de oro perdido.
   * @param descripcion motivo de la pérdida.
   */
  public void registrarPerdidaOro(int cantidad, String descripcion) {
    historialOro.add(new RegistroOro(-cantidad, descripcion, LocalDateTime.now()));
  }

  /**
   * Verifica si el usuario cumple las condiciones para iniciar un desafío.
   *
   * @return true si el usuario tiene personaje con equipo activo y no está bloqueado.
   */
  public boolean puedeDesafiar() {
    return !bloqueado && personaje != null && personaje.tieneEquipoActivo();
  }

  /**
   * Comprueba si este usuario ha perdido un combate en las últimas 24 horas.
   *
   * @return true si existe una derrota reciente.
   */
  public boolean haPerdidoEnUltimas24h() {
    if (ultimaDerrota == null) {
      return false;
    }
    return ultimaDerrota.isAfter(LocalDateTime.now().minusHours(24));
  }

  /**
   * Genera un número de registro con formato LNNLL.
   *
   * @return cadena con formato de un carácter, dos números y dos caracteres.
   */
  public static String generarNumeroRegistro() {
    Random rnd = new Random();
    char l1 = (char) ('A' + rnd.nextInt(26));
    int n1 = rnd.nextInt(10);
    int n2 = rnd.nextInt(10);
    char l2 = (char) ('A' + rnd.nextInt(26));
    char l3 = (char) ('A' + rnd.nextInt(26));
    return "" + l1 + n1 + n2 + l2 + l3;
  }

  @Override
  public String toString() {
    return "Usuario[" + nick + " #" + numeroRegistro
        + (bloqueado ? " BLOQUEADO" : "") + "]";
  }

  /**
   * Representa una entrada en el historial de oro del usuario.
   */
  public static class RegistroOro implements Serializable {

    private static final long serialVersionUID = 1L;

    private final int cantidad;
    private final String descripcion;
    private final LocalDateTime fecha;

    /**
     * Construye un registro de transacción de oro.
     *
     * @param cantidad valor de la transacción.
     * @param descripcion detalle del movimiento.
     * @param fecha instante de la transacción.
     */
    public RegistroOro(int cantidad, String descripcion, LocalDateTime fecha) {
      this.cantidad = cantidad;
      this.descripcion = descripcion;
      this.fecha = fecha;
    }

    /**
     * Obtiene la cantidad de oro de este registro.
     *
     * @return el valor numérico del oro.
     */
    public int getCantidad() {
      return cantidad;
    }

    /**
     * Obtiene la descripción del movimiento de oro.
     *
     * @return el texto descriptivo.
     */
    public String getDescripcion() {
      return descripcion;
    }

    /**
     * Obtiene la fecha en la que se realizó el registro.
     *
     * @return el objeto LocalDateTime correspondiente.
     */
    public LocalDateTime getFecha() {
      return fecha;
    }

    @Override
    public String toString() {
      return fecha + " | " + (cantidad >= 0 ? "+" : "") + cantidad + " oro | " + descripcion;
    }
  }
}