package metprog.model;

import java.util.ArrayList;

/**
 * Representa un esbirro de tipo Demonio con capacidades recursivas.
 *
 * <p>Los demonios tienen un pacto con su amo y pueden poseer sus propios
 * sub-esbirros, permitiendo una estructura jerárquica de aliados.
 */
public class EsbirroDemonio extends Esbirro {

  private static final long serialVersionUID = 1L;

  /** Descripción del pacto entre el demonio y su amo. */
  private String descripcionPacto;

  /** Sub-esbirros propios de este demonio. */
  private ArrayList<Esbirro> subEsbirros = new ArrayList<>();

  /**
   * Construye un nuevo esbirro demonio.
   *
   * @param nombre nombre del demonio
   * @param salud salud inicial (1-3)
   * @param descripcionPacto descripción del pacto
   */
  public EsbirroDemonio(String nombre, int salud, String descripcionPacto) {
    super(nombre, salud);
    setDescripcionPacto(descripcionPacto);
  }

  /**
   * Obtiene la descripción del pacto.
   *
   * @return el texto del pacto
   */
  public String getDescripcionPacto() {
    return descripcionPacto;
  }

  /**
   * Establece la descripción del pacto validando que no sea nula o vacía.
   *
   * @param descripcionPacto el nuevo texto del pacto
   */
  public void setDescripcionPacto(String descripcionPacto) {
    if (descripcionPacto != null && !descripcionPacto.trim().isEmpty()) {
      this.descripcionPacto = descripcionPacto;
    } else {
      System.out.println("Error: La descripción del pacto no puede estar vacía.");
      this.descripcionPacto = "Pacto sin descripción";
    }
  }

  /**
   * Obtiene la lista de sub-esbirros asociados.
   *
   * @return lista de esbirros dependientes
   */
  public ArrayList<Esbirro> getSubEsbirros() {
    return subEsbirros;
  }

  /**
   * Agrega un nuevo sub-esbirro a la jerarquía.
   *
   * @param esbirro el esbirro a agregar
   */
  public void agregarSubEsbirro(Esbirro esbirro) {
    subEsbirros.add(esbirro);
  }

  /**
   * Elimina un sub-esbirro de la jerarquía.
   *
   * @param esbirro el esbirro a eliminar
   */
  public void eliminarSubEsbirro(Esbirro esbirro) {
    subEsbirros.remove(esbirro);
  }

  /**
   * Calcula la salud total de todos los sub-esbirros de manera recursiva.
   *
   * @return la suma de salud de toda la descendencia de esbirros
   */
  public int getSaludTotalSubesbirros() {
    int total = 0;
    for (Esbirro e : subEsbirros) {
      total += e.getSalud();
      if (e instanceof EsbirroDemonio) {
        total += ((EsbirroDemonio) e).getSaludTotalSubesbirros();
      }
    }
    return total;
  }

  @Override
  public String toString() {
    return "EsbirroDemonio " + getNombre()
        + " [Salud:" + getSalud()
        + " Pacto:'" + descripcionPacto + "'"
        + " SubEsbirros:" + subEsbirros.size() + "]";
  }
}