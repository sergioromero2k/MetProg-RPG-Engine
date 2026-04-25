package metprog.model;

/**
 * Representa una debilidad que actúa como un modificador negativo para un personaje.
 *
 * <p>El operador del sistema decide qué debilidades están presentes en un combate.
 * El valor de la debilidad se resta al potencial de ataque y defensa del personaje.
 */
public class Debilidad extends Modificador {

  /**
   * Construye una nueva instancia de Debilidad.
   *
   * @param nombre el nombre identificativo de la debilidad
   * @param valor el valor numérico que se restará en los cálculos de combate
   */
  public Debilidad(String nombre, int valor) {
    super(nombre, valor);
  }
}