package metprog.model;

import java.io.Serializable;

/**
 * Representa a un operador del sistema con privilegios de administrador.
 *
 * <p>A diferencia de los usuarios, el operador no posee un personaje propio
 * y se encarga de la gestion del sistema.
 */
public class Operador implements Serializable {

  private static final long serialVersionUID = 1L;

  private String nombre;
  private String nick;
  private String password;

  /**
   * Construye una nueva instancia de Operador.
   *
   * @param nombre nombre completo del operador
   * @param nick alias unico en el sistema
   * @param password contraseña (entre 8 y 12 caracteres)
   */
  public Operador(String nombre, String nick, String password) {
    setNombre(nombre);
    setNick(nick);
    setPassword(password);
  }

  /**
   * Obtiene el nombre del operador.
   *
   * @return el nombre actual
   */
  public String getNombre() {
    return nombre;
  }

  /**
   * Establece el nombre del operador validando que no sea nulo o vacio.
   *
   * @param nombre el nuevo nombre
   */
  public void setNombre(String nombre) {
    if (nombre != null && !nombre.trim().isEmpty()) {
      this.nombre = nombre;
    } else {
      System.out.println("Error: El nombre no puede estar vacio.");
      this.nombre = "Sin nombre";
    }
  }

  /**
   * Obtiene el nick del operador.
   *
   * @return el alias unico
   */
  public String getNick() {
    return nick;
  }

  /**
   * Establece el nick del operador validando que no sea nulo o vacio.
   *
   * @param nick el nuevo alias
   */
  public void setNick(String nick) {
    if (nick != null && !nick.trim().isEmpty()) {
      this.nick = nick;
    } else {
      System.out.println("Error: El nick no puede estar vacio.");
      this.nick = "Sin nick";
    }
  }

  /**
   * Obtiene la contraseña del operador.
   *
   * @return la contraseña actual
   */
  public String getPassword() {
    return password;
  }

  /**
   * Establece la contraseña validando la longitud permitida.
   *
   * @param password cadena de entre 8 y 12 caracteres
   * @throws IllegalArgumentException si la longitud no es valida
   */
  public void setPassword(String password) {
    if (password != null && password.length() >= 8 && password.length() <= 12) {
      this.password = password;
    } else {
      throw new IllegalArgumentException("La contraseña debe tener entre 8 y 12 caracteres.");
    }
  }

  @Override
  public String toString() {
    return "Operador[" + nick + "]";
  }
}