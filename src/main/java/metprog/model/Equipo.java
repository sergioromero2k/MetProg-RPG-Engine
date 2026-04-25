package metprog.model;

import java.io.Serializable;

/**
 * Clase base abstracta que representa el equipo de un personaje.
 *
 * <p>Define los atributos comunes como nombre y modificadores de combate
 * para armas, armaduras y otros objetos.
 */
public abstract class Equipo implements Serializable {

  private static final long serialVersionUID = 1L;

  private String nombre;
  private int modAtaque;
  private int modDefensa;

  /**
   * Construye una nueva instancia de Equipo.
   *
   * @param nombre el nombre del equipo
   * @param modAtaque el modificador de ataque (0-3)
   * @param modDefensa el modificador de defensa (0-3)
   */
  public Equipo(String nombre, int modAtaque, int modDefensa) {
    setNombre(nombre);
    setModAtaque(modAtaque);
    setModDefensa(modDefensa);
  }

  /**
   * Obtiene el nombre del equipo.
   *
   * @return el nombre del equipo
   */
  public String getNombre() {
    return nombre;
  }

  /**
   * Establece el nombre del equipo.
   *
   * @param nombre el nuevo nombre
   */
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  /**
   * Obtiene el modificador de ataque.
   *
   * @return el modificador de ataque
   */
  public int getModAtaque() {
    return modAtaque;
  }

  /**
   * Establece el modificador de ataque validando el rango permitido.
   *
   * @param modAtaque valor entre 0 y 3
   */
  public void setModAtaque(int modAtaque) {
    if (modAtaque >= 0 && modAtaque <= 3) {
      this.modAtaque = modAtaque;
    } else {
      System.out.println("Error: modAtaque debe estar entre 0 y 3.");
      this.modAtaque = 0;
    }
  }

  /**
   * Obtiene el modificador de defensa.
   *
   * @return el modificador de defensa
   */
  public int getModDefensa() {
    return modDefensa;
  }

  /**
   * Establece el modificador de defensa validando el rango permitido.
   *
   * @param modDefensa valor entre 0 y 3
   */
  public void setModDefensa(int modDefensa) {
    if (modDefensa >= 0 && modDefensa <= 3) {
      this.modDefensa = modDefensa;
    } else {
      System.out.println("Error: modDefensa debe estar entre 0 y 3.");
      this.modDefensa = 0;
    }
  }
}