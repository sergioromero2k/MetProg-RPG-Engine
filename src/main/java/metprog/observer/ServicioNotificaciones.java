package metprog.observer;

import java.util.ArrayList;
import java.util.List;
import metprog.model.Combate;
import metprog.model.Desafio;
import metprog.model.Evento;
import metprog.model.enums.TipoEvento;

/**
 * Servicio encargado de gestionar la lista de suscriptores y distribuir notificaciones.
 *
 * <p>Implementa el patron Observer permitiendo que distintos componentes del sistema
 * reaccionen a eventos como resultados de combates o recepcion de desafios.
 */
public class ServicioNotificaciones {

  private final List<INotificador> suscriptores = new ArrayList<>();

  /**
   * Agrega un nuevo suscriptor a la lista de notificaciones.
   *
   * @param n el notificador que desea recibir eventos.
   */
  public void suscribir(INotificador n) {
    suscriptores.add(n);
  }

  /**
   * Elimina un suscriptor de la lista.
   *
   * @param n el notificador que dejara de recibir eventos.
   */
  public void desuscribir(INotificador n) {
    suscriptores.remove(n);
  }

  /**
   * Distribuye un evento a todos los suscriptores activos.
   *
   * @param evento el objeto de evento a procesar.
   */
  public void notificar(Evento evento) {
    for (INotificador suscriptor : suscriptores) {
      suscriptor.actualizar(evento);
    }
  }

  /**
   * Genera y envia una notificacion cuando un combate ha terminado.
   *
   * @param c la instancia del combate finalizado.
   */
  public void notificarResultadoCombate(Combate c) {
    Evento e = new Evento(TipoEvento.COMBATE_FINALIZADO);
    e.agregarDato("combate", c);
    notificar(e);
  }

  /**
   * Genera y envia una notificacion al recibir un nuevo desafio.
   *
   * @param d la instancia del desafio recibido.
   */
  public void notificarDesafioRecibido(Desafio d) {
    Evento e = new Evento(TipoEvento.DESAFIO_RECIBIDO);
    e.agregarDato("desafio", d);
    notificar(e);
  }
}