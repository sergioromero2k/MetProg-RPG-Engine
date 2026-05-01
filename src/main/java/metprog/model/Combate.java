package metprog.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Registra el resultado de un combate entre dos usuarios.
 *
 * <p>Almacena datos sobre los participantes, las rondas transcurridas, el vencedor,
 * la fecha del encuentro y el estado de los esbirros al finalizar.
 */
public class Combate implements Serializable {

  private static final long serialVersionUID = 1L;

  private final Usuario desafiante;
  private final Usuario desafiado;

  private Usuario vencedor; // null representa un empate
  private int rondasEmpleadas;
  private int oroGanado;
  private final LocalDateTime fechaCombate;

  private final List<RondaCombate> rondas = new ArrayList<>();

  private final List<Usuario> conEsbirrosSupervivientes = new ArrayList<>();

  /**
   * Crea un nuevo registro de combate con la fecha y hora actuales.
   *
   * @param desafiante el usuario que inicia el reto
   * @param desafiado el usuario que recibe el reto
   */
  public Combate(Usuario desafiante, Usuario desafiado) {
    this.desafiante = desafiante;
    this.desafiado = desafiado;
    this.fechaCombate = LocalDateTime.now();
  }

  public Usuario getDesafiante() {
    return desafiante;
  }

  public Usuario getDesafiado() {
    return desafiado;
  }

  public Usuario getVencedor() {
    return vencedor;
  }

  public void setVencedor(Usuario vencedor) {
    this.vencedor = vencedor;
  }

  public int getRondasEmpleadas() {
    return rondasEmpleadas;
  }

  public void setRondasEmpleadas(int rondasEmpleadas) {
    this.rondasEmpleadas = rondasEmpleadas;
  }

  public int getOroGanado() {
    return oroGanado;
  }

  public void setOroGanado(int oroGanado) {
    this.oroGanado = oroGanado;
  }

  public LocalDateTime getFechaCombate() {
    return fechaCombate;
  }

  public List<RondaCombate> getRondas() {
    return rondas;
  }

  /**
   * Agrega una nueva ronda al registro del combate.
   *
   * @param ronda la instancia de RondaCombate a añadir
   */
  public void agregarRonda(RondaCombate ronda) {
    rondas.add(ronda);
  }

  public List<Usuario> getConEsbirrosSupervivientes() {
    return conEsbirrosSupervivientes;
  }

  /**
   * Registra si un usuario terminó el combate con esbirros vivos.
   *
   * @param u el usuario a registrar
   */
  public void registrarEsbirrosSupervivientes(Usuario u) {
    if (!conEsbirrosSupervivientes.contains(u)) {
      conEsbirrosSupervivientes.add(u);
    }
  }

  /**
   * Indica si el combate ha terminado en tablas.
   *
   * @return true si no hay un vencedor asignado, false en caso contrario
   */
  public boolean esEmpate() {
    return vencedor == null;
  }

  /**
   * Genera un resumen detallado en formato texto del combate.
   *
   * @return una cadena con el reporte del combate para el usuario
   */
  public String generarResumen() {
    StringBuilder sb = new StringBuilder();
    sb.append("Combate ")
        .append(desafiante.getNick())
        .append(" vs ")
        .append(desafiado.getNick())
        .append(" (")
        .append(fechaCombate)
        .append(")\n");
    sb.append("Rondas: ").append(rondasEmpleadas).append("\n");
    sb.append("Vencedor: ")
        .append(esEmpate() ? "EMPATE" : vencedor.getNick())
        .append("\n");

    if (!conEsbirrosSupervivientes.isEmpty()) {
      sb.append("Esbirros supervivientes:\n");
      if (conEsbirrosSupervivientes.contains(desafiante)) {
        sb.append("- ").append(desafiante.getNick()).append("\n");
      }
      if (conEsbirrosSupervivientes.contains(desafiado)) {
        sb.append("- ").append(desafiado.getNick()).append("\n");
      }
    }

    sb.append("Oro apostado: ").append(oroGanado).append("\n");
    return sb.toString();
  }

  /**
   * Genera una vista detallada del combate, mostrando cada ronda por separado.
   *
   * @return una cadena con el detalle completo del combate ronda a ronda.
   */
  public String generarDetalleRondas() {
    StringBuilder sb = new StringBuilder();
    sb.append("=======================================\n")
        .append("  COMBATE ")
        .append(desafiante.getNick())
        .append(" vs ")
        .append(desafiado.getNick())
        .append(" (")
        .append(fechaCombate)
        .append(")\n")
        .append("  Rondas: ")
        .append(rondasEmpleadas)
        .append("\n")
        .append("---------------------------------------\n");

    for (RondaCombate ronda : rondas) {
      sb.append(ronda).append("\n")
          .append("---------------------------------------\n");
    }

    if (esEmpate()) {
      sb.append("  VENCEDOR: EMPATE\n");
    } else {
      sb.append("  VENCEDOR: ").append(vencedor.getNick()).append("\n");
    }

    if (!conEsbirrosSupervivientes.isEmpty()) {
      sb.append("  Esbirros supervivientes:\n");
      if (conEsbirrosSupervivientes.contains(desafiante)) {
        sb.append("  - ").append(desafiante.getNick()).append("\n");
      }
      if (conEsbirrosSupervivientes.contains(desafiado)) {
        sb.append("  - ").append(desafiado.getNick()).append("\n");
      }
    }

    sb.append("  Oro apostado: ").append(oroGanado).append("\n")
        .append("=======================================\n");
    return sb.toString();
  }

  @Override
  public String toString() {
    String resultadoStr = esEmpate() ? "EMPATE" : "Vencedor: " + vencedor.getNick();
    return "Combate["
        + desafiante.getNick()
        + " vs "
        + desafiado.getNick()
        + " | "
        + resultadoStr
        + " | Rondas:"
        + rondasEmpleadas
        + " | "
        + fechaCombate
        + "]";
  }
}
