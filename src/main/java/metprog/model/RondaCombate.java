package metprog.model;

import java.io.Serializable;

/**
 * Registra los detalles y el resultado de una ronda de combate individual.
 *
 * <p>Esta clase es serializable para permitir el almacenamiento del historial
 * de combates entre personajes.
 */
public class RondaCombate implements Serializable {

  private static final long serialVersionUID = 1L;

  private int numeroRonda;
  private int exitosAtaqueJ1;
  private int exitosDefensaJ1;
  private int exitosAtaqueJ2;
  private int exitosDefensaJ2;
  private String resultado;

  /**
   * Construye un registro detallado de la ronda de combate.
   *
   * @param numeroRonda el número secuencial de la ronda.
   * @param exitosAtaqueJ1 éxitos de ataque del jugador 1.
   * @param exitosDefensaJ1 éxitos de defensa del jugador 1.
   * @param exitosAtaqueJ2 éxitos de ataque del jugador 2.
   * @param exitosDefensaJ2 éxitos de defensa del jugador 2.
   * @param resultado descripción textual del desenlace de la ronda.
   */
  public RondaCombate(
      int numeroRonda,
      int exitosAtaqueJ1,
      int exitosDefensaJ1,
      int exitosAtaqueJ2,
      int exitosDefensaJ2,
      String resultado) {
    this.numeroRonda = numeroRonda;
    this.exitosAtaqueJ1 = exitosAtaqueJ1;
    this.exitosDefensaJ1 = exitosDefensaJ1;
    this.exitosAtaqueJ2 = exitosAtaqueJ2;
    this.exitosDefensaJ2 = exitosDefensaJ2;
    this.resultado = resultado;
  }

  public int getNumeroRonda() {
    return numeroRonda;
  }

  public int getExitosAtaqueJ1() {
    return exitosAtaqueJ1;
  }

  public int getExitosDefensaJ1() {
    return exitosDefensaJ1;
  }

  public int getExitosAtaqueJ2() {
    return exitosAtaqueJ2;
  }

  public int getExitosDefensaJ2() {
    return exitosDefensaJ2;
  }

  public String getResultado() {
    return resultado;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("--- Ronda ").append(numeroRonda).append(" ---\n");
    sb.append("Ataques: J1=").append(exitosAtaqueJ1).append(" éxitos | J2=").append(exitosAtaqueJ2).append(" éxitos\n");
    sb.append("Defensas: J1=").append(exitosDefensaJ1).append(" éxitos | J2=").append(exitosDefensaJ2).append(" éxitos\n");
    sb.append("Resultado: ").append(resultado);
    return sb.toString();
  }
}