package metprog.model;

/**
 * Representa a un personaje de tipo Vampiro en el sistema.
 *
 * <p>Los vampiros gestionan puntos de sangre y poseen una edad. Tienen la
 * restriccion de no poder reclutar esbirros de tipo humano.
 */
public class Vampiro extends Personaje {

  private static final long serialVersionUID = 1L;

  private int puntosSangre;
  private int edad;

  /**
   * Construye una nueva instancia de Vampiro con valores iniciales.
   *
   * @param nombre nombre del vampiro.
   * @param salud salud inicial (rango 0-5).
   * @param poder poder base (rango 1-5).
   * @param oro cantidad de oro inicial.
   */
  public Vampiro(String nombre, int salud, int poder, int oro) {
    super(nombre, salud, poder, oro);
    this.puntosSangre = 5;
    this.edad = 0;
  }

  /**
   * Obtiene la cantidad actual de puntos de sangre.
   *
   * @return puntos de sangre en el rango 0-10.
   */
  public int getPuntosSangre() {
    return puntosSangre;
  }

  /**
   * Establece los puntos de sangre validando el rango permitido.
   *
   * @param puntosSangre valor entero a asignar.
   */
  public void setPuntosSangre(int puntosSangre) {
    if (puntosSangre >= 0 && puntosSangre <= 10) {
      this.puntosSangre = puntosSangre;
    } else {
      System.out.println("Error: PuntosSangre debe estar entre 0 y 10.");
      this.puntosSangre = 0;
    }
  }

  /**
   * Intenta gastar una cantidad especifica de puntos de sangre.
   *
   * @param coste cantidad de puntos a deducir.
   * @return true si el vampiro tenia sangre suficiente, false en caso contrario.
   */
  public boolean gastarSangre(int coste) {
    if (puntosSangre >= coste) {
      puntosSangre -= coste;
      return true;
    }
    return false;
  }

  /**
   * Incrementa los puntos de sangre sin superar el maximo de diez.
   *
   * @param cantidad puntos a recuperar tras un ataque exitoso.
   */
  public void recuperarSangre(int cantidad) {
    setPuntosSangre(Math.min(10, puntosSangre + cantidad));
  }

  /**
   * Obtiene la edad del vampiro.
   *
   * @return edad en años.
   */
  public int getEdad() {
    return edad;
  }

  /**
   * Establece la edad del vampiro.
   *
   * @param edad valor entero de la edad.
   */
  public void setEdad(int edad) {
    this.edad = edad;
  }

  /**
   * Agrega un esbirro validando que no sea de tipo humano.
   *
   * @param esbirro el esbirro que se desea añadir.
   * @throws UnsupportedOperationException si el esbirro es una instancia de EsbirroHumano.
   */
  @Override
  public void agregarEsbirro(Esbirro esbirro) {
    if (esbirro instanceof EsbirroHumano) {
      throw new UnsupportedOperationException(
          "Los vampiros no pueden tener esbirros humanos.");
    }
    super.agregarEsbirro(esbirro);
  }

  /**
   * Devuelve la Disciplina activa realizando un cast de la habilidad especial.
   *
   * @return la Disciplina asignada o null si no posee una.
   */
  public Disciplina getDisciplina() {
    HabilidadEspecial h = getHabilidad();
    return (h instanceof Disciplina) ? (Disciplina) h : null;
  }

  /**
   * Restablece los puntos de sangre y la salud para el inicio de un combate.
   */
  @Override
  public void reiniciarParaCombate() {
    super.reiniciarParaCombate();
    this.puntosSangre = 5;
  }

  @Override
  public String toString() {
    return "Vampiro " + getNombre()
        + " [Salud:" + getSalud()
        + " Poder:" + getPoder()
        + " Sangre:" + puntosSangre + "/10"
        + " Oro:" + getOro()
        + " Edad:" + edad + "]";
  }
}