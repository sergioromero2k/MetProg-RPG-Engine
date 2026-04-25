package metprog.model;

/**
 * Representa una habilidad especial de tipo Talento.
 *
 * <p>Los talentos proporcionan bonificadores específicos de ataque y defensa
 * que se suman a las capacidades base del personaje que los posee.
 */
public class Talento extends HabilidadEspecial {

  /**
   * Construye una nueva instancia de Talento.
   *
   * @param nombre el nombre del talento
   * @param valorAtaque el bonificador de ataque que otorga
   * @param valorDefensa el bonificador de defensa que otorga
   */
  public Talento(String nombre, int valorAtaque, int valorDefensa) {
    super(nombre, valorAtaque, valorDefensa);
  }

  @Override
  public String toString() {
    return "Talento: " + getNombre()
        + " [ATK+" + getValorAtaque()
        + " DEF+" + getValorDefensa() + "]";
  }
}