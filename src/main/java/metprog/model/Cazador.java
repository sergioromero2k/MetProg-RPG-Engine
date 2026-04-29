package metprog.model;

/**
 * Representa a un personaje de tipo Cazador en el juego.
 *
 * <p>El cazador posee un atributo especial de voluntad que afecta a su desempeño
 * y se ve reducido al recibir daño durante el combate.
 */
public class Cazador extends Personaje {

  private static final long serialVersionUID = 1L;

  /** Puntos de voluntad del cazador, con un rango de 0 a 3. */
  private int voluntad;

  /**
   * Construye una nueva instancia de Cazador con voluntad inicial máxima.
   *
   * @param nombre nombre del cazador
   * @param salud salud inicial (0-5)
   * @param poder poder (1-5)
   * @param oro oro inicial (mayor o igual a 0)
   */
  public Cazador(String nombre, int salud, int poder, int oro) {
    super(nombre, salud, poder, oro);
    this.voluntad = 3;
  }

  public int getVoluntad() {
    return voluntad;
  }

  /**
   * Actualiza el valor de voluntad asegurando que esté en el rango permitido.
   *
   * @param voluntad nuevo valor de voluntad (0-3)
   */
  public void setVoluntad(int voluntad) {
    if (voluntad >= 0 && voluntad <= 3) {
      this.voluntad = voluntad;
    } else {
      System.out.println("Error: Voluntad debe estar entre 0 y 3.");
      this.voluntad = 0;
    }
  }

  /**
   * Decrementa la voluntad en 1 unidad al recibir daño, sin bajar de 0.
   */
  public void decrementarVoluntad() {
    setVoluntad(Math.max(0, voluntad - 1));
  }

  @Override
  public void recibirDano(int cantidad) {
    for (int i = 0; i < cantidad; i++) {
      if (getSalud() > 0) {
        super.recibirDano(1);
        decrementarVoluntad();
      }
    }
  }

  /**
   * Devuelve el talento activo realizando una conversión segura.
   *
   * @return el Talento o null si la habilidad no es de tipo Talento
   */
  public Talento getTalento() {
    HabilidadEspecial h = getHabilidad();
    return (h instanceof Talento) ? (Talento) h : null;
  }

  @Override
  public void reiniciarParaCombate() {
    super.reiniciarParaCombate();
    this.voluntad = 3;
  }

  @Override
  public String toString() {
    return "Cazador " + getNombre()
        + " [Salud:" + getSalud()
        + " Poder:" + getPoder()
        + " Voluntad:" + voluntad + "/3"
        + " Oro:" + getOro() + "]";
  }
}
