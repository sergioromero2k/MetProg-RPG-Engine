package metprog.model;

/**
 * Represents an armor equipment item.
 *
 * <p>Armor primarily provides defense modifiers, though some specialized
 * pieces may also grant attack bonuses.
 */
public class Armadura extends Equipo {

  /**
   * Constructs a new Armadura instance.
   *
   * @param nombre the name of the armor
   * @param modAtaque the attack modifier (0-3)
   * @param modDefensa the defense modifier (0-3)
   */
  public Armadura(String nombre, int modAtaque, int modDefensa) {
    super(nombre, modAtaque, modDefensa);
  }

  @Override
  public String toString() {
    String atkPart = getModAtaque() > 0 ? " ATK+" + getModAtaque() : "";
    return getNombre() + " [DEF+" + getModDefensa() + atkPart + "]";
  }
}