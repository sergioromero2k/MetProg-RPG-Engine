package metprog.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Registra el resultado de un combate.
 *
 * Datos persistidos según el enunciado:
 *  - Usuario desafiante y desafiado.
 *  - Rondas empleadas.
 *  - Fecha del combate.
 *  - Usuario vencedor (null = empate).
 *  - Los contendientes que mantuvieron algún esbirro sin derrotar.
 *  - Oro ganado.
 */
public class Combate implements Serializable {
    private static final long serialVersionUID = 1L;

    // ── Participantes ────────────────────────────────────────────────────────
    private final Usuario desafiante;
    private final Usuario desafiado;

    // ── Resultado ────────────────────────────────────────────────────────────
    private Usuario vencedor;           // null = empate
    private int     rondasEmpleadas;
    private int     oroGanado;
    private final LocalDateTime fechaCombate;

    // ── Log de rondas ────────────────────────────────────────────────────────
    private final List<RondaCombate> rondas = new ArrayList<>();

    // ── Esbirros supervivientes ───────────────────────────────────────────────
    // Contendientes que terminaron el combate con al menos un esbirro vivo
    private final List<Usuario> conEsbirrosSupervivientes = new ArrayList<>();

    // ── Constructor ──────────────────────────────────────────────────────────

    public Combate(Usuario desafiante, Usuario desafiado) {
        this.desafiante  = desafiante;
        this.desafiado   = desafiado;
        this.fechaCombate = LocalDateTime.now();
    }

    // ── Getters / Setters ────────────────────────────────────────────────────

    public Usuario getDesafiante() { return desafiante; }
    public Usuario getDesafiado()  { return desafiado; }

    public Usuario getVencedor() { return vencedor; }
    public void setVencedor(Usuario vencedor) { this.vencedor = vencedor; }

    public int getRondasEmpleadas() { return rondasEmpleadas; }
    public void setRondasEmpleadas(int rondasEmpleadas) { this.rondasEmpleadas = rondasEmpleadas; }

    public int getOroGanado() { return oroGanado; }
    public void setOroGanado(int oroGanado) { this.oroGanado = oroGanado; }

    public LocalDateTime getFechaCombate() { return fechaCombate; }

    // ── Log de rondas ────────────────────────────────────────────────────────

    public List<RondaCombate> getRondas() { return rondas; }

    public void añadirRonda(RondaCombate ronda) { rondas.add(ronda); }

    // ── Esbirros supervivientes ───────────────────────────────────────────────

    public List<Usuario> getConEsbirrosSupervivientes() { return conEsbirrosSupervivientes; }

    public void registrarEsbirrosSupervivientes(Usuario u) {
        if (!conEsbirrosSupervivientes.contains(u)) {
            conEsbirrosSupervivientes.add(u);
        }
    }

    // ── Utilidades ───────────────────────────────────────────────────────────

    public boolean esEmpate() { return vencedor == null; }

    /**
     * Genera un resumen en texto del combate para mostrar al jugador.
     */
    public String generarResumen() {
        StringBuilder sb = new StringBuilder();
        sb.append("═══════════════════════════════════════\n");
        sb.append("  RESULTADO DEL COMBATE\n");
        sb.append("  ").append(desafiante.getNick())
          .append(" vs ").append(desafiado.getNick()).append("\n");
        sb.append("  Fecha: ").append(fechaCombate).append("\n");
        sb.append("  Rondas: ").append(rondasEmpleadas).append("\n");
        sb.append("───────────────────────────────────────\n");
        for (RondaCombate r : rondas) {
            sb.append("  ").append(r).append("\n");
        }
        sb.append("───────────────────────────────────────\n");
        if (esEmpate()) {
            sb.append("  RESULTADO: EMPATE. Nadie pierde oro.\n");
        } else {
            sb.append("  VENCEDOR: ").append(vencedor.getNick()).append("\n");
            sb.append("  ORO GANADO: ").append(oroGanado).append("\n");
        }
        if (!conEsbirrosSupervivientes.isEmpty()) {
            sb.append("  Con esbirros supervivientes: ");
            for (Usuario u : conEsbirrosSupervivientes) sb.append(u.getNick()).append(" ");
            sb.append("\n");
        }
        sb.append("═══════════════════════════════════════\n");
        return sb.toString();
    }

    @Override
    public String toString() {
        return "Combate[" + desafiante.getNick()
                + " vs " + desafiado.getNick()
                + " | " + (esEmpate() ? "EMPATE" : "Vencedor: " + vencedor.getNick())
                + " | Rondas:" + rondasEmpleadas
                + " | " + fechaCombate + "]";
    }
}
