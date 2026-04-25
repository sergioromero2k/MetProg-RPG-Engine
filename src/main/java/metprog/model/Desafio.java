package metprog.model;

import metprog.state.EstadoDesafio;
import metprog.state.Pendiente;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa un desafío entre dos usuarios.
 *
 * Ciclo de vida (patrón State):
 *   PENDIENTE → (validado por operador) → PUBLICADO
 *   PUBLICADO → (aceptado por desafiado) → EN_COMBATE → COMPLETADO
 *   PUBLICADO → (rechazado por desafiado o cancelado) → RECHAZADO
 *
 * Restricciones de negocio:
 *  - Ambos personajes deben tener equipo activo.
 *  - El desafiante no puede apostar oro negativo ni más del que tiene.
 *  - No se puede desafiar a un usuario que perdió hace < 24h.
 *  - El desafiado no puede hacer otra acción hasta aceptar o rechazar.
 *  - Si rechaza: paga el 10% del oro apostado por el desafiante.
 *  - Si empate: nadie pierde oro.
 */
public class Desafio implements Serializable {
    private static final long serialVersionUID = 1L;

    private Usuario desafiante;
    private Usuario desafiado;
    private int     oroApostado;       // cantidad apostada por el desafiante
    private LocalDateTime fechaCreacion;

    // Modificadores presentes fijados por el operador
    private List<Fortaleza> fortalezasDesafiante = new ArrayList<>();
    private List<Debilidad> debilidadesDesafiante = new ArrayList<>();
    private List<Fortaleza> fortalezasDesafiado   = new ArrayList<>();
    private List<Debilidad> debilidadesDesafiado  = new ArrayList<>();

    // Combate resultante (relleno al completarse)
    private Combate combate;

    // Patrón State
    private EstadoDesafio estado;

    // ── Constructor ──────────────────────────────────────────────────────────

    /**
     * @param desafiante  usuario que lanza el desafío
     * @param desafiado   usuario al que se desafía
     * @param oroApostado cantidad de oro apostada (>= 0 y <= oro del desafiante)
     */
    public Desafio(Usuario desafiante, Usuario desafiado, int oroApostado) {
        if (desafiante == null || desafiado == null) {
            throw new IllegalArgumentException("Los usuarios no pueden ser nulos.");
        }
        if (oroApostado < 0) {
            throw new IllegalArgumentException("El oro apostado no puede ser negativo.");
        }
        if (desafiante.getPersonaje() != null
                && oroApostado > desafiante.getPersonaje().getOro()) {
            throw new IllegalArgumentException(
                "El desafiante no tiene suficiente oro para apostar esa cantidad.");
        }
        this.desafiante    = desafiante;
        this.desafiado     = desafiado;
        this.oroApostado   = oroApostado;
        this.fechaCreacion = LocalDateTime.now();
        this.estado        = new Pendiente();
    }

    // ── Delegación al estado (patrón State) ─────────────────────────────────

    public void validar()   { estado.validar(this); }
    public void aceptar()   { estado.aceptar(this); }
    public void rechazar()  { estado.rechazar(this); }
    public void cancelar()  { estado.cancelar(this); }
    public void finalizar() { estado.finalizar(this); }

    public EstadoDesafio getEstado() { return estado; }
    public void setEstado(EstadoDesafio estado) { this.estado = estado; }

    // ── Getters / Setters ────────────────────────────────────────────────────

    public Usuario getDesafiante() { return desafiante; }
    public Usuario getDesafiado()  { return desafiado; }

    public int getOroApostado() { return oroApostado; }
    public void setOroApostado(int oroApostado) { this.oroApostado = oroApostado; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }

    public Combate getCombate() { return combate; }
    public void setCombate(Combate combate) { this.combate = combate; }

    // ── Modificadores presentes ──────────────────────────────────────────────

    public List<Fortaleza> getFortalezasDesafiante() { return fortalezasDesafiante; }
    public void setFortalezasDesafiante(List<Fortaleza> f) { this.fortalezasDesafiante = f; }

    public List<Debilidad> getDebilidadesDesafiante() { return debilidadesDesafiante; }
    public void setDebilidadesDesafiante(List<Debilidad> d) { this.debilidadesDesafiante = d; }

    public List<Fortaleza> getFortalezasDesafiado() { return fortalezasDesafiado; }
    public void setFortalezasDesafiado(List<Fortaleza> f) { this.fortalezasDesafiado = f; }

    public List<Debilidad> getDebilidadesDesafiado() { return debilidadesDesafiado; }
    public void setDebilidadesDesafiado(List<Debilidad> d) { this.debilidadesDesafiado = d; }

    // ── Lógica de negocio: penalización por rechazo ──────────────────────────

    /**
     * Aplica la penalización por rechazo al desafiado:
     * paga el 10% del oro apostado (redondeado hacia abajo).
     */
    public void aplicarPenalizacionRechazo() {
        int penalizacion = oroApostado / 10;
        Personaje pDesafiado = desafiado.getPersonaje();
        if (pDesafiado != null) {
            int oroActual = pDesafiado.getOro();
            pDesafiado.setOro(Math.max(0, oroActual - penalizacion));
            desafiado.registrarPerdidaOro(penalizacion,
                "Penalización por rechazar desafío de " + desafiante.getNick());

            // El oro va al desafiante
            Personaje pDesafiante = desafiante.getPersonaje();
            if (pDesafiante != null) {
                pDesafiante.setOro(pDesafiante.getOro() + penalizacion);
                desafiante.registrarGananciaOro(penalizacion,
                    "Compensación por rechazo de " + desafiado.getNick());
            }
        }
    }

    // ── Representación ──────────────────────────────────────────────────────

    @Override
    public String toString() {
        return "Desafio[" + desafiante.getNick()
                + " vs " + desafiado.getNick()
                + " oro:" + oroApostado
                + " estado:" + estado.getClass().getSimpleName()
                + " fecha:" + fechaCreacion + "]";
    }
}
