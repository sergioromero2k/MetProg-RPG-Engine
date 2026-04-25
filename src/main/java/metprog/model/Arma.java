package metprog.model;

/**
 * Representa un objeto de tipo arma en el sistema de equipo.
 *
 * <p>Las armas pueden ser de una o dos manos, lo que afecta a la capacidad
 * del personaje para equipar otros objetos simultáneamente.
 */
public class Arma extends Equipo {

  /** Indica si el arma requiere el uso de ambas manos. */
  private boolean dosManos;

  /**
   * Construye una nueva instancia de Arma.
   *
   * @param nombre el nombre del arma
   * @param modAtaque el modificador de ataque (0-3)
   * @param modDefensa el modificador de defensa (0-3)
   * @param dosManos true si es un arma de dos manos, false si es de una
   */
  public Arma(String nombre, int modAtaque, int modDefensa, boolean dosManos) {
    super(nombre, modAtaque, modDefensa);
    this.dosManos = dosManos;
  }

  public boolean isDosManos() {
    return dosManos;
  }

  public void setDosManos(boolean dosManos) {
    this.dosManos = dosManos;
  }

  @Override
  public String toString() {
    String sufijoManos = dosManos ? " 2M" : " 1M";
    return getNombre()
        + " [ATK+" + getModAtaque()
        + " DEF+" + getModDefensa()
        + sufijoManos + "]";
  }
}