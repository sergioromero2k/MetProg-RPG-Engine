package metprog.model;

/**
 * Representa una pieza de armadura.
 *
 * <p>Las armaduras proporcionan principalmente modificadores de defensa,
 * aunque algunas piezas especiales pueden otorgar bonificadores de ataque.
 */
public class Armadura extends Equipo {

  /**
   * Construye una nueva instancia de Armadura.
   *
   * @param nombre el nombre de la armadura
   * @param modAtaque el modificador de ataque (0-3)
   * @param modDefensa el modificador de defensa (0-3)
   */
  public Armadura(String nombre, int modAtaque, int modDefensa) {
    super(nombre, modAtaque, modDefensa);
  }

  @Override
  public String toString() {
    String parteAtk = getModAtaque() > 0 ? " ATK+" + getModAtaque() : "";
    return getNombre() + " [DEF+" + getModDefensa() + parteAtk + "]";
  }
}