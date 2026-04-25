package metprog.model;

/**
 * Representa una habilidad especial de tipo Disciplina, exclusiva de los Vampiros.
 *
 * <p>Cada disciplina tiene un coste asociado en puntos de sangre que se debe
 * descontar al ser utilizada en combate.
 */
public class Disciplina extends HabilidadEspecial {

  /** Puntos de sangre que cuesta activar esta disciplina (1-3). */
  private int costeSangre;

  /**
   * Construye una nueva instancia de Disciplina.
   *
   * @param nombre nombre de la disciplina
   * @param valorAtaque valor de ataque (1-3)
   * @param valorDefensa valor de defensa (1-3)
   * @param costeSangre coste en puntos de sangre (1-3)
   */
  public Disciplina(String nombre, int valorAtaque, int valorDefensa, int costeSangre) {
    super(nombre, valorAtaque, valorDefensa);
    setCosteSangre(costeSangre);
  }

  /**
   * Obtiene los puntos de sangre que cuesta la disciplina.
   *
   * @return los puntos de sangre que cuesta la disciplina.
   */
  public int getCosteSangre() {
    return costeSangre;
  }

  /**
   * Establece el coste de sangre validando que esté en el rango permitido.
   *
   * @param costeSangre valor entre 1 y 3
   */
  public void setCosteSangre(int costeSangre) {
    if (costeSangre >= 1 && costeSangre <= 3) {
      this.costeSangre = costeSangre;
    } else {
      System.out.println("Error: costeSangre debe estar entre 1 y 3.");
      this.costeSangre = 1;
    }
  }

  @Override
  public String toString() {
    return "Disciplina " + getNombre()
        + " [ATK:" + getValorAtaque()
        + " DEF:" + getValorDefensa()
        + " Coste:" + costeSangre + " sangre]";
  }
}