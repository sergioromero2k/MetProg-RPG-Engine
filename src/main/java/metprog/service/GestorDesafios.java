package metprog.service;

import java.util.ArrayList;
import java.util.List;
import metprog.model.Combate;
import metprog.model.Debilidad;
import metprog.model.Desafio;
import metprog.model.Fortaleza;
import metprog.model.Personaje;
import metprog.model.Usuario;
import metprog.observer.ServicioNotificaciones;
import metprog.state.EnCombate;
import metprog.state.Pendiente;
import metprog.state.Publicado;

/**
 * Gestiona el ciclo de vida completo de los desafíos entre jugadores.
 *
 * <p>Controla la creación, validación por parte del operador, aceptación,
 * rechazo y finalización de desafíos, aplicando todas las reglas de negocio
 * definidas en el enunciado.
 */
public class GestorDesafios {

  private final List<Desafio> desafios = new ArrayList<>();
  private final List<Combate> historialCombates = new ArrayList<>();
  private ServicioNotificaciones servicioNotificaciones;
  private GestorUsuarios gestorUsuarios;

  /**
   * Crea un nuevo desafío entre dos usuarios validando todas las reglas.
   *
   * <p>Restricciones comprobadas: El desafiante no puede desafiar a sí mismo,
   * no puede estar bloqueado, ambos deben tener equipo activo, el oro apostado
   * debe ser positivo y no superar el del desafiante, y el desafiado no puede
   * tener desafíos activos ni haber perdido en las últimas 24h.
   *
   * @param desafiante usuario que lanza el reto.
   * @param desafiado usuario al que se desafía.
   * @param oroApostado cantidad de oro apostada.
   * @return el desafío creado, o {@code null} si alguna regla se incumple.
   */
  public Desafio crearDesafio(Usuario desafiante, Usuario desafiado, int oroApostado) {
    if (desafiante == null || desafiado == null) {
      return null;
    }
    if (desafiante == desafiado || desafiante.getNick().equals(desafiado.getNick())) {
      return null;
    }
    if (desafiante.isBloqueado()) {
      return null;
    }
    if (!desafiante.puedeDesafiar()) {
      return null;
    }
    if (desafiado.getPersonaje() == null || !desafiado.getPersonaje().tieneEquipoActivo()) {
      return null;
    }
    if (oroApostado < 0) {
      return null;
    }
    if (desafiante.getPersonaje() != null
        && oroApostado > desafiante.getPersonaje().getOro()) {
      return null;
    }
    if (tieneDesafioActivo(desafiado)) {
      return null;
    }
    if (desafiado.haPerdidoEnUltimas24h()) {
      return null;
    }

    try {
      Desafio d = new Desafio(desafiante, desafiado, oroApostado);
      desafios.add(d);
      guardarDatos();
      if (servicioNotificaciones != null) {
        servicioNotificaciones.notificarDesafioRecibido(d);
      }
      return d;
    } catch (IllegalArgumentException e) {
      return null;
    }
  }

  /**
   * El operador valida el desafío y decide qué modificadores están presentes.
   *
   * <p>Tras la validación el desafío pasa al estado {@code Publicado} y el
   * desafiado recibe la notificación la próxima vez que entre al sistema.
   *
   * @param desafio desafío a validar.
   * @param fortDesafiante fortalezas presentes del desafiante.
   * @param debDesafiante debilidades presentes del desafiante.
   * @param fortDesafiado fortalezas presentes del desafiado.
   * @param debDesafiado debilidades presentes del desafiado.
   * @return {@code true} si la transición se realizó correctamente.
   */
  public boolean validarDesafio(Desafio desafio,
                                List<Fortaleza> fortDesafiante,
                                List<Debilidad> debDesafiante,
                                List<Fortaleza> fortDesafiado,
                                List<Debilidad> debDesafiado) {
    if (desafio == null) {
      return false;
    }
    desafio.setFortalezasDesafiante(fortDesafiante != null ? fortDesafiante : new ArrayList<>());
    desafio.setDebilidadesDesafiante(debDesafiante != null ? debDesafiante : new ArrayList<>());
    desafio.setFortalezasDesafiado(fortDesafiado != null ? fortDesafiado : new ArrayList<>());
    desafio.setDebilidadesDesafiado(debDesafiado != null ? debDesafiado : new ArrayList<>());

    desafio.validar();
    guardarDatos();
    return desafio.getEstado() instanceof Publicado;
  }

  /**
   * El desafiado acepta el desafío publicado.
   *
   * <p>Aplica los modificadores acordados al personaje de cada jugador y
   * transiciona el estado a {@code EnCombate}.
   *
   * @param desafio desafío a aceptar.
   * @return {@code true} si la transición se realizó correctamente.
   */
  public boolean aceptarDesafio(Desafio desafio) {
    if (desafio == null) {
      return false;
    }

    desafio.aceptar();

    Personaje pdesafiante = desafio.getDesafiante().getPersonaje();
    Personaje pdesafiado = desafio.getDesafiado().getPersonaje();

    if (pdesafiante != null) {
      pdesafiante.setFortalezasPresentes(
          new ArrayList<>(desafio.getFortalezasDesafiante()));
      pdesafiante.setDebilidadesPresentes(
          new ArrayList<>(desafio.getDebilidadesDesafiante()));
    }
    if (pdesafiado != null) {
      pdesafiado.setFortalezasPresentes(
          new ArrayList<>(desafio.getFortalezasDesafiado()));
      pdesafiado.setDebilidadesPresentes(
          new ArrayList<>(desafio.getDebilidadesDesafiado()));
    }

    if (desafio.getEstado() instanceof EnCombate && servicioNotificaciones != null) {
      servicioNotificaciones.notificarDesafioAceptado(desafio);
    }

    guardarDatos();

    return desafio.getEstado() instanceof EnCombate;
  }

  /**
   * El desafiado rechaza el desafío publicado.
   *
   * <p>Se aplica automáticamente la penalización del 10 por ciento del oro apostado.
   *
   * @param desafio desafío a rechazar.
   */
  public void rechazarDesafio(Desafio desafio) {
    if (desafio == null) {
      return;
    }
    desafio.rechazar();
    desafio.aplicarPenalizacionRechazo();
    guardarDatos();
    if (servicioNotificaciones != null) {
      servicioNotificaciones.notificarDesafioRechazado(desafio);
    }
  }

  /**
   * Registra el resultado del combate y transfiere el oro al vencedor.
   *
   * <p>Si el combate es un empate, nadie pierde oro. Si hay vencedor, recibe
   * el oro apostado y el perdedor registra la fecha de su derrota.
   *
   * @param desafio desafío que se finaliza.
   * @param combate resultado del combate calculado por el MotorCombate.
   */
  public void finalizarDesafio(Desafio desafio, Combate combate) {
    if (desafio == null || combate == null) {
      return;
    }
    desafio.setCombate(combate);
    combate.setOroGanado(desafio.getOroApostado());

    if (!combate.esEmpate()) {
      Usuario vencedor = combate.getVencedor();
      Usuario perdedor = vencedor == desafio.getDesafiante()
          ? desafio.getDesafiado()
          : desafio.getDesafiante();

      Personaje pvencedor = vencedor.getPersonaje();
      Personaje pperdedor = perdedor.getPersonaje();

      if (pvencedor != null && pperdedor != null) {
        int oro = desafio.getOroApostado();
        pvencedor.setOro(pvencedor.getOro() + oro);
        pperdedor.setOro(Math.max(0, pperdedor.getOro() - oro));

        vencedor.registrarGananciaOro(oro, "Victoria vs " + perdedor.getNick());
        perdedor.registrarPerdidaOro(oro, "Derrota vs " + vencedor.getNick());
      }
      perdedor.setUltimaDerrota(combate.getFechaCombate());
    }
    // Registrar qué contendientes mantuvieron esbirros vivos al finalizar
    try {
      Personaje pDesafiante = desafio.getDesafiante().getPersonaje();
      Personaje pDesafiado = desafio.getDesafiado().getPersonaje();
      if (pDesafiante != null && !pDesafiante.getEsbirros().isEmpty()) {
        for (var e : pDesafiante.getEsbirros()) {
          if (e.getSalud() > 0) {
            combate.registrarEsbirrosSupervivientes(desafio.getDesafiante());
            break;
          }
        }
      }
      if (pDesafiado != null && !pDesafiado.getEsbirros().isEmpty()) {
        for (var e : pDesafiado.getEsbirros()) {
          if (e.getSalud() > 0) {
            combate.registrarEsbirrosSupervivientes(desafio.getDesafiado());
            break;
          }
        }
      }
    } catch (Exception ignored) {
      // Seguridad: no impedir finalizar por problemas al inspeccionar esbirros
    }

    desafio.finalizar();
    historialCombates.add(combate);
    guardarDatos();
    if (servicioNotificaciones != null) {
      servicioNotificaciones.notificarResultadoCombate(combate);
    }
  }

  /**
   * Devuelve todos los desafíos en estado Pendiente.
   *
   * @return lista de desafíos pendientes de validación.
   */
  public List<Desafio> getDesafiosPendientes() {
    List<Desafio> resultado = new ArrayList<>();
    for (Desafio d : desafios) {
      if (d.getEstado() instanceof Pendiente) {
        resultado.add(d);
      }
    }
    return resultado;
  }

  /**
   * Devuelve el desafío publicado para un destinatario concreto.
   *
   * @param desafiado usuario desafiado.
   * @return el desafío publicado, o {@code null} si no hay ninguno.
   */
  public Desafio getDesafioPublicadoParaUsuario(Usuario desafiado) {
    for (Desafio d : desafios) {
      if ("Publicado".equals(d.getEstado().getClass().getSimpleName())
          && d.getDesafiado() == desafiado) {
        return d;
      }
    }
    return null;
  }

  /**
   * Devuelve todos los desafíos registrados en el sistema.
   *
   * @return lista completa de desafíos.
   */
  public List<Desafio> getDesafios() {
    return new ArrayList<>(desafios);
  }

  /**
   * Devuelve el historial completo de combates finalizados.
   *
   * @return lista de combates realizados.
   */
  public List<Combate> getHistorialCombates() {
    return new ArrayList<>(historialCombates);
  }

  /**
   * Reemplaza la lista interna de desafíos.
   *
   * @param lista nueva lista de desafíos a cargar.
   */
  public void setDesafios(List<Desafio> lista) {
    desafios.clear();
    desafios.addAll(lista);
  }

  /**
   * Reemplaza el historial de combates.
   *
   * @param lista nueva lista de combates a cargar.
   */
  public void setHistorialCombates(List<Combate> lista) {
    historialCombates.clear();
    historialCombates.addAll(lista);
  }

  /**
   * Asigna el servicio de notificaciones para emitir eventos de desafíos.
   *
   * @param servicioNotificaciones servicio compartido de notificaciones.
   */
  public void setServicioNotificaciones(ServicioNotificaciones servicioNotificaciones) {
    this.servicioNotificaciones = servicioNotificaciones;
  }

  /**
   * Asigna el gestor de usuarios para poder persistir el estado completo.
   *
   * @param gestorUsuarios gestor compartido de usuarios.
   */
  public void setGestorUsuarios(GestorUsuarios gestorUsuarios) {
    this.gestorUsuarios = gestorUsuarios;
  }

  /**
   * Comprueba si el usuario ya es destinatario de un desafío activo.
   *
   * @param usuario el usuario a consultar.
   * @return true si tiene un desafio pendiente o publicado.
   */
  private boolean tieneDesafioActivo(Usuario usuario) {
    for (Desafio d : desafios) {
      String estado = d.getEstado().getClass().getSimpleName();
      if (d.getDesafiado() == usuario
          && ("Pendiente".equals(estado) || "Publicado".equals(estado))) {
        return true;
      }
    }
    return false;
  }

  private void guardarDatos() {
    if (gestorUsuarios != null) {
      Persistencia.guardarTodo(
          gestorUsuarios.getUsuarios(),
          gestorUsuarios.getOperadores(),
          desafios,
          historialCombates);
    } else {
      Persistencia.guardarDesafios(desafios);
      Persistencia.guardarCombates(historialCombates);
    }
  }
}