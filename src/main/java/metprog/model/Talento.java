package metprog.model;

/**
 * Representa un Talento, una habilidad especial característica de los Cazadores.
 *
 * <p>A diferencia de otras habilidades especiales, el talento no impone
 * restricciones adicionales para su uso durante el combate.
 */
public class Talento extends HabilidadEspecial {

  /**
   * Construye una nueva instancia de Talento.
   *
   * @param nombre nombre identificativo del talento.
   * @param valorAtaque valor de ataque (rango 1-3).
   * @param valorDefensa valor de defensa (rango 1-3).
   */
  public Talento(String nombre, int valorAtaque, int valorDefensa) {
    super(nombre, valorAtaque, valorDefensa);
  }

  @Override
  public String toString() {
    return "Talento " + getNombre()
        + " [ATK:" + getValorAtaque()
        + " DEF:" + getValorDefensa() + "]";
  }
}