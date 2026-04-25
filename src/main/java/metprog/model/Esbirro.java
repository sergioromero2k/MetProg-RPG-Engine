package metprog.model;

import java.io.Serializable;

/**
 * Clase abstracta que representa a un esbirro en el sistema.
 *
 * <p>Los esbirros son aliados o súbditos que poseen nombre y un valor de
 * salud limitado que influye en el combate.
 */
public abstract class Esbirro implements Serializable {

  private static final long serialVersionUID = 1L;

  private String nombre;
  private int salud;

  /**
   * Construye una nueva instancia de Esbirro.
   *
   * @param nombre el nombre del esbirro
   * @param salud la salud inicial (1-3)
   */
  public Esbirro(String nombre, int salud) {
    setNombre(nombre);
    setSalud(salud);
  }

  /**
   * Obtiene el nombre del esbirro.
   *
   * @return el nombre actual
   */
  public String getNombre() {
    return nombre;
  }

  /**
   * Establece el nombre del esbirro.
   *
   * @param nombre el nuevo nombre
   */
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  /**
   * Obtiene el valor de salud actual del esbirro.
   *
   * @return la salud del esbirro
   */
  public int getSalud() {
    return salud;
  }

  /**
   * Establece la salud del esbirro validando que este en el rango permitido.
   *
   * @param salud valor entero entre 1 y 3
   */
  public void setSalud(int salud) {
    if (salud >= 1 && salud <= 3) {
      this.salud = salud;
    } else {
      System.out.println("Error: Salud debe estar entre 1 y 3.");
      this.salud = 1;
    }
  }
}