package metprog.model;

import java.io.Serializable;

/**
 * Clase abstracta que representa un modificador de estadísticas.
 *
 * <p>Los modificadores pueden ser fortalezas o debilidades que alteran el
 * potencial de combate de un personaje basándose en un valor numérico.
 */
public abstract class Modificador implements Serializable {

  private static final long serialVersionUID = 1L;

  private String nombre;
  private int valor;

  /**
   * Construye una nueva instancia de Modificador.
   *
   * @param nombre el nombre del modificador
   * @param valor el valor numérico del modificador (1-5)
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
   * Obtiene el valor numérico del modificador.
   *
   * @return el valor del modificador
   */
  public int getValor() {
    return valor;
  }

  /**
   * Establece el valor del modificador validando que esté en el rango permitido.
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
}