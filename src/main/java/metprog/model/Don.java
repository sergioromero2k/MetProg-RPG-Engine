package metprog.model;

/**
 * Representa una habilidad especial de tipo Don, exclusiva de los Licántropos.
 *
 * <p>Los dones requieren que el personaje posea un nivel de rabia mínimo para
 * poder ser ejecutados durante el combate.
 */
public class Don extends HabilidadEspecial {

  /** Valor mínimo de rabia necesario para activar este don (0-3). */
  private int rabiaMinima;

  /**
   * Construye una nueva instancia de Don.
   *
   * @param nombre nombre del don
   * @param valorAtaque valor de ataque (1-3)
   * @param valorDefensa valor de defensa (1-3)
   * @param rabiaMinima rabia mínima requerida (0-3)
   */
  public Don(String nombre, int valorAtaque, int valorDefensa, int rabiaMinima) {
    super(nombre, valorAtaque, valorDefensa);
    setRabiaMinima(rabiaMinima);
  }

  /**
   * Obtiene el valor de rabia mínima necesaria.
   *
   * @return el valor de rabia mínima
   */
  public int getRabiaMinima() {
    return rabiaMinima;
  }

  /**
   * Establece la rabia mínima validando el rango permitido (0-3).
   *
   * @param rabiaMinima valor entero entre 0 y 3
   */
  public void setRabiaMinima(int rabiaMinima) {
    if (rabiaMinima >= 0 && rabiaMinima <= 3) {
      this.rabiaMinima = rabiaMinima;
    } else {
      System.out.println("Error: rabiaMinima debe estar entre 0 y 3.");
      this.rabiaMinima = 0;
    }
  }

  /**
   * Indica si el licántropo puede usar este don con su rabia actual.
   *
   * @param rabiaActual rabia actual del licántropo
   * @return true si la rabia es suficiente, false en caso contrario
   */
  public boolean puedeUsarse(int rabiaActual) {
    return rabiaActual >= rabiaMinima;
  }

  @Override
  public String toString() {
    return "Don " + getNombre()
        + " [ATK:" + getValorAtaque()
        + " DEF:" + getValorDefensa()
        + " RabiaMin:" + rabiaMinima + "]";
  }
}