package metprog.model;

/**
 * Represents a weapon item in the game equipment system.
 *
 * <p>Weapons can be either one-handed or two-handed, which modifies the
 * character's ability to equip other items simultaneously.
 */
public class Arma extends Equipo {

  /** Whether the weapon requires both hands to be used. */
  private boolean dosManos;

  /**
   * Constructs a new Arma instance.
   *
   * @param nombre the name of the weapon
   * @param modAtaque the attack modifier (0-3)
   * @param modDefensa the defense modifier (0-3)
   * @param dosManos true if it is a two-handed weapon, false otherwise
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
    String handSuffix = dosManos ? " 2M" : " 1M";
    return getNombre()
        + " [ATK+" + getModAtaque()
        + " DEF+" + getModDefensa()
        + handSuffix + "]";
  }
}