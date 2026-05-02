package metprog.model;

/**
 * Representa a un personaje de tipo Licantropo en el sistema.
 *
 * <p>Los licantropos gestionan un valor de Rabia que aumenta al recibir daño.
 * Poseen además atributos de forma bestia como el incremento de altura y peso.
 */
public class Licantropo extends Personaje {

  private static final long serialVersionUID = 1L;

  private int rabia;
  private double incrementoAltura;
  private double incrementoPeso;

  /**
   * Construye una nueva instancia de Licantropo con valores iniciales.
   *
   * @param nombre el nombre del licantropo
   * @param salud la salud inicial (0-5)
   * @param poder el poder base (1-5)
   * @param oro la cantidad de oro inicial
   */
  public Licantropo(String nombre, int salud, int poder, int oro) {
    super(nombre, salud, poder, oro);
    this.rabia = 0;
    this.incrementoAltura = 0.5 + Math.random() * 0.5;
    this.incrementoPeso = 90 + Math.random() * 20;
  }

  /**
   * Obtiene el valor actual de rabia.
   *
   * @return valor de rabia entre 0 y 3.
   */
  public int getRabia() {
    return rabia;
  }

  /**
   * Establece la rabia validando el rango permitido (0-3).
   *
   * @param rabia valor entero a asignar.
   */
  public void setRabia(int rabia) {
    if (rabia >= 0 && rabia <= 3) {
      this.rabia = rabia;
    } else {
      System.out.println("Error: Rabia debe estar entre 0 y 3.");
      this.rabia = Math.max(0, Math.min(3, rabia));
    }
  }

  /** Incrementa la rabia en una unidad hasta un maximo de 3. */
  public void incrementarRabia() {
    setRabia(Math.min(3, rabia + 1));
  }

  /**
   * Sobrescrito para aumentar la rabia por cada punto de salud perdido.
   *
   * @param cantidad cantidad de danio total recibida.
   */
  @Override
  public void recibirDano(int cantidad) {
    for (int i = 0; i < cantidad; i++) {
      if (getSalud() > 0) {
        super.recibirDano(1);
        incrementarRabia();
      }
    }
  }

  /**
   * Obtiene el incremento de altura.
   *
   * @return el incremento de altura en metros.
   */
  public double getIncrementoAltura() {
    return incrementoAltura;
  }

  /**
   * Establece el incremento de altura.
   *
   * @param incrementoAltura valor entre 0.5 y 1.0.
   */
  public void setIncrementoAltura(double incrementoAltura) {
    if (incrementoAltura >= 0.5 && incrementoAltura <= 1.0) {
      this.incrementoAltura = incrementoAltura;
    }
  }

  /**
   * Obtiene el incremento de peso.
   *
   * @return el incremento de peso en kilos.
   */
  public double getIncrementoPeso() {
    return incrementoPeso;
  }

  /**
   * Establece el incremento de peso.
   *
   * @param incrementoPeso valor entre 90 y 110.
   */
  public void setIncrementoPeso(double incrementoPeso) {
    if (incrementoPeso >= 90 && incrementoPeso <= 110) {
      this.incrementoPeso = incrementoPeso;
    }
  }

  /**
   * Devuelve el Don activo realizando un cast de la habilidad.
   *
   * @return el Don asignado o null si no es de tipo Don.
   */
  public Don getDon() {
    HabilidadEspecial h = getHabilidad();
    return (h instanceof Don) ? (Don) h : null;
  }

  /**
   * Comprueba si el nivel de rabia es suficiente para usar el Don.
   *
   * @return true si puede usar el don actual.
   */
  public boolean puedeUsarDon() {
    Don d = getDon();
    return d != null && d.puedeUsarse(rabia);
  }

  /** Reinicia los valores del licantropo para un nuevo combate. */
  @Override
  public void reiniciarParaCombate() {
    super.reiniciarParaCombate();
    this.rabia = 0;
  }

  @Override
  public String toString() {
    return "Licantropo " + getNombre()
        + " [Salud:" + getSalud()
        + " Poder:" + getPoder()
        + " Rabia:" + rabia + "/3"
        + " Oro:" + getOro() + "]";
  }
}
