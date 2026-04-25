package metprog.model;

import metprog.model.enums.Lealtad;

/**
 * Representa a un esbirro de tipo humano en el sistema.
 *
 * <p>Los humanos poseen un nivel de lealtad específico. Según las reglas de
 * negocio, los personajes de tipo Vampiro no pueden poseer este tipo de esbirros.
 */
public class EsbirroHumano extends Esbirro {

  private static final long serialVersionUID = 1L;

  private Lealtad lealtad;

  /**
   * Construye una nueva instancia de EsbirroHumano.
   *
   * @param nombre nombre del esbirro
   * @param salud salud inicial (1-3)
   * @param lealtad nivel de lealtad (ALTA, NORMAL, BAJA)
   */
  public EsbirroHumano(String nombre, int salud, Lealtad lealtad) {
    super(nombre, salud);
    setLealtad(lealtad);
  }

  /**
   * Obtiene el nivel de lealtad del humano.
   *
   * @return el nivel de lealtad actual
   */
  public Lealtad getLealtad() {
    return lealtad;
  }

  /**
   * Establece la lealtad del esbirro validando que no sea nula.
   *
   * @param lealtad el nuevo nivel de lealtad
   */
  public void setLealtad(Lealtad lealtad) {
    if (lealtad != null) {
      this.lealtad = lealtad;
    } else {
      System.out.println("Error: La lealtad no puede ser nula.");
      this.lealtad = Lealtad.NORMAL;
    }
  }
}