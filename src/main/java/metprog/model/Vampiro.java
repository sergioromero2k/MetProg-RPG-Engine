package metprog.model;

/**
 * Representa a un personaje de tipo Vampiro en el sistema.
 *
 * <p>Los vampiros poseen atributos únicos como los puntos de sangre,
 * los cuales consumen para activar disciplinas, y la edad.
 */
public class Vampiro extends Personaje {

  private int puntosSangre;
  private int edad;

  /**
   * Construye una nueva instancia de Vampiro con valores iniciales.
   *
   * @param nombre el nombre del vampiro
   * @param salud la salud inicial del personaje
   * @param poder el poder base del personaje
   * @param oro la cantidad de oro inicial
   */
  public Vampiro(String nombre, int salud, int poder, int oro) {
    super(nombre, salud, poder, oro);
    this.puntosSangre = 5;
    this.edad = 0;
  }

  /**
   * Obtiene los puntos de sangre actuales.
   *
   * @return la cantidad de puntos de sangre
   */
  public int getPuntosSangre() {
    return puntosSangre;
  }

  /**
   * Establece los puntos de sangre validando el rango permitido (0-10).
   *
   * @param puntosSangre valor entre 0 y 10
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
   * Obtiene la edad del vampiro.
   *
   * @return la edad en años
   */
  public int getEdad() {
    return edad;
  }

  /**
   * Establece la edad del vampiro.
   *
   * @param edad la nueva edad
   */
  public void setEdad(int edad) {
    this.edad = edad;
  }
}