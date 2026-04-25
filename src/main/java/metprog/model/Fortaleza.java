package metprog.model;

/**
 * Representa una fortaleza que actúa como un modificador positivo para un personaje.
 *
 * <p>El operador del sistema decide qué fortalezas están presentes en un combate.
 * El valor de la fortaleza se suma al potencial de ataque y defensa del personaje.
 */
public class Fortaleza extends Modificador {

  /**
   * Construye una nueva instancia de Fortaleza.
   *
   * @param nombre el nombre identificativo de la fortaleza
   * @param valor el valor numérico que se sumará en los cálculos de combate
   */
  public Fortaleza(String nombre, int valor) {
    super(nombre, valor);
  }
}