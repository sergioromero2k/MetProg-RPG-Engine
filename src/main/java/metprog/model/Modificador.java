package metprog.model;

import java.io.Serializable;

/**
 * Clase abstracta que representa un modificador de estadisticas para un personaje.
 *
 * <p>Los modificadores pueden representar fortalezas o debilidades que afectan
 * el desempeño de los personajes en combate.
 */
public abstract class Modificador implements Serializable {

  private static final long serialVersionUID = 1L;

  private String nombre;
  private int valor;

  /**
   * Construye una nueva instancia de Modificador.
   *
   * @param nombre el nombre descriptivo del modificador
   * @param valor el valor numerico asociado (1-5)
   */
  public Modificador(String nombre, int valor) {
    setNombre(nombre);
    setValor(valor);
  }

  /**
   * Obtiene el nombre del modificador.
   *
   * @return el nombre actual
   */
  public String getNombre() {
    return nombre;
  }

  /**
   * Establece el nombre del modificador.
   *
   * @param nombre el nuevo nombre
   */
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  /**
   * Obtiene el valor numerico del modificador.
   *
   * @return el valor del modificador
   */
  public int getValor() {
    return valor;
  }

  /**
   * Establece el valor del modificador validando el rango permitido.
   *
   * @param valor valor entero entre 1 y 5
   */
  public void setValor(int valor) {
    if (valor >= 1 && valor <= 5) {
      this.valor = valor;
    } else {
      System.out.println("Error: Valor debe estar entre 1 y 5.");
      this.valor = 1;
    }
  }

  /**
   * Retorna el nombre del modificador.
   *
   * @return el nombre del modificador
   */
  @Override
  public String toString() {
    return nombre;
  }
}