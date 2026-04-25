package metprog.model;

import java.io.Serializable;

/**
 * Clase base abstracta que define una habilidad especial para los personajes.
 *
 * <p>Las habilidades especiales proporcionan bonificadores de ataque y defensa
 * que se activan bajo ciertas condiciones durante el combate.
 */
public abstract class HabilidadEspecial implements Serializable {

  private static final long serialVersionUID = 1L;

  private String nombre;
  private int valorAtaque;
  private int valorDefensa;

  /**
   * Construye una nueva instancia de HabilidadEspecial.
   *
   * @param nombre el nombre de la habilidad
   * @param valorAtaque el bonificador de ataque (1-3)
   * @param valorDefensa el bonificador de defensa (1-3)
   */
  public HabilidadEspecial(String nombre, int valorAtaque, int valorDefensa) {
    setNombre(nombre);
    setValorAtaque(valorAtaque);
    setValorDefensa(valorDefensa);
  }

  /**
   * Obtiene el nombre de la habilidad.
   *
   * @return el nombre actual
   */
  public String getNombre() {
    return nombre;
  }

  /**
   * Establece el nombre de la habilidad.
   *
   * @param nombre el nuevo nombre
   */
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  /**
   * Obtiene el valor de ataque de la habilidad.
   *
   * @return el valor de ataque
   */
  public int getValorAtaque() {
    return valorAtaque;
  }

  /**
   * Establece el valor de ataque validando el rango permitido.
   *
   * @param valorAtaque valor entero entre 1 y 3
   */
  public void setValorAtaque(int valorAtaque) {
    if (valorAtaque >= 1 && valorAtaque <= 3) {
      this.valorAtaque = valorAtaque;
    } else {
      System.out.println("Error: Valor de ataque debe estar entre 1 y 3.");
      this.valorAtaque = 1;
    }
  }

  /**
   * Obtiene el valor de defensa de la habilidad.
   *
   * @return el valor de defensa
   */
  public int getValorDefensa() {
    return valorDefensa;
  }

  /**
   * Establece el valor de defensa validando el rango permitido.
   *
   * @param valorDefensa valor entero entre 1 y 3
   */
  public void setValorDefensa(int valorDefensa) {
    if (valorDefensa >= 1 && valorDefensa <= 3) {
      this.valorDefensa = valorDefensa;
    } else {
      System.out.println("Error: Valor de defensa debe estar entre 1 y 3.");
      this.valorDefensa = 1;
    }
  }
}
