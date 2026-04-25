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
    sb.append("=======================================\n")
        .append("  RESULTADO DEL COMBATE\n")
        .append("  ")
        .append(desafiante.getNick())
        .append(" vs ")
        .append(desafiado.getNick())
        .append("\n")
        .append("  Fecha: ")
        .append(fechaCombate)
        .append("\n")
        .append("  Rondas: ")
        .append(rondasEmpleadas)
        .append("\n")
        .append("---------------------------------------\n");

    for (RondaCombate r : rondas) {
      sb.append("  ").append(r).append("\n");
    }

    sb.append("---------------------------------------\n");
    if (esEmpate()) {
      sb.append("  RESULTADO: EMPATE. Nadie pierde oro.\n");
    } else {
      sb.append("  VENCEDOR: ").append(vencedor.getNick()).append("\n");
      sb.append("  ORO GANADO: ").append(oroGanado).append("\n");
    }

    if (!conEsbirrosSupervivientes.isEmpty()) {
      sb.append("  Con esbirros supervivientes: ");
      for (Usuario u : conEsbirrosSupervivientes) {
        sb.append(u.getNick()).append(" ");
      }
      sb.append("\n");
    }
    sb.append("=======================================\n");
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
