package metprog.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import metprog.state.EstadoDesafio;
import metprog.state.Pendiente;

/**
 * Representa un desafío entre dos usuarios en el sistema.
 *
 * <p>Gestiona el ciclo de vida de un duelo mediante el patrón State y aplica
 * las restricciones de negocio relacionadas con el oro y los personajes.
 */
public class Desafio implements Serializable {

  private static final long serialVersionUID = 1L;

  private Usuario desafiante;
  private Usuario desafiado;
  private int oroApostado;
  private LocalDateTime fechaCreacion;

  private List<Fortaleza> fortalezasDesafiante = new ArrayList<>();
  private List<Debilidad> debilidadesDesafiante = new ArrayList<>();
  private List<Fortaleza> fortalezasDesafiado = new ArrayList<>();
  private List<Debilidad> debilidadesDesafiado = new ArrayList<>();

  private Combate combate;
  private EstadoDesafio estado;

  /**
   * Construye un nuevo desafío validando las restricciones iniciales.
   *
   * @param desafiante usuario que lanza el desafío
   * @param desafiado usuario al que se desafía
   * @param oroApostado cantidad de oro apostada
   * @throws IllegalArgumentException si los parámetros no cumplen las reglas de negocio
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
    this.desafiante = desafiante;
    this.desafiado = desafiado;
    this.oroApostado = oroApostado;
    this.fechaCreacion = LocalDateTime.now();
    this.estado = new Pendiente();
  }

  /** Valida el desafío mediante el estado actual. */
  public void validar() {
    estado.validar(this);
  }

  /** Acepta el desafío mediante el estado actual. */
  public void aceptar() {
    estado.aceptar(this);
  }

  /** Rechaza el desafío mediante el estado actual. */
  public void rechazar() {
    estado.rechazar(this);
  }

  /** Cancela el desafío mediante el estado actual. */
  public void cancelar() {
    estado.cancelar(this);
  }

  /** Finaliza el desafío mediante el estado actual. */
  public void finalizar() {
    estado.finalizar(this);
  }

  public EstadoDesafio getEstado() {
    return estado;
  }

  public void setEstado(EstadoDesafio estado) {
    this.estado = estado;
  }

  public Usuario getDesafiante() {
    return desafiante;
  }

  public Usuario getDesafiado() {
    return desafiado;
  }

  public int getOroApostado() {
    return oroApostado;
  }

  public void setOroApostado(int oroApostado) {
    this.oroApostado = oroApostado;
  }

  public LocalDateTime getFechaCreacion() {
    return fechaCreacion;
  }

  public Combate getCombate() {
    return combate;
  }

  public void setCombate(Combate combate) {
    this.combate = combate;
  }

  public List<Fortaleza> getFortalezasDesafiante() {
    return fortalezasDesafiante;
  }

  public void setFortalezasDesafiante(List<Fortaleza> f) {
    this.fortalezasDesafiante = f;
  }

  public List<Debilidad> getDebilidadesDesafiante() {
    return debilidadesDesafiante;
  }

  public void setDebilidadesDesafiante(List<Debilidad> d) {
    this.debilidadesDesafiante = d;
  }

  public List<Fortaleza> getFortalezasDesafiado() {
    return fortalezasDesafiado;
  }

  public void setFortalezasDesafiado(List<Fortaleza> f) {
    this.fortalezasDesafiado = f;
  }

  public List<Debilidad> getDebilidadesDesafiado() {
    return debilidadesDesafiado;
  }

  public void setDebilidadesDesafiado(List<Debilidad> d) {
    this.debilidadesDesafiado = d;
  }

  /**
   * Aplica la penalización por rechazo al desafiado (10% del oro apostado).
   *
   * <p>El oro restado al desafiado se transfiere al desafiante como compensación.
   */
  public void aplicarPenalizacionRechazo() {
    int penalizacion = oroApostado / 10;
    Personaje personajeDesafiado = desafiado.getPersonaje();
    if (personajeDesafiado != null) {
      int oroActual = personajeDesafiado.getOro();
      personajeDesafiado.setOro(Math.max(0, oroActual - penalizacion));
      desafiado.registrarPerdidaOro(penalizacion,
          "Penalización por rechazar desafío de " + desafiante.getNick());

      Personaje personajeDesafiante = desafiante.getPersonaje();
      if (personajeDesafiante != null) {
        personajeDesafiante.setOro(personajeDesafiante.getOro() + penalizacion);
        desafiante.registrarGananciaOro(penalizacion,
            "Compensación por rechazo de " + desafiado.getNick());
      }
    }
  }

  @Override
  public String toString() {
    return "Desafio["
        + desafiante.getNick()
        + " vs " + desafiado.getNick()
        + " oro:" + oroApostado
        + " estado:" + estado.getClass().getSimpleName()
        + " fecha:" + fechaCreacion + "]";
  }
}