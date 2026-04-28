package metprog.observer;

import metprog.model.Evento;

/**
 * Representa la interfaz de usuario que reacciona a los eventos del sistema.
 *
 * <p>Esta implementacion de {@link INotificador} se encarga de mostrar por
 * consola los mensajes correspondientes a los cambios de estado que afectan
 * directamente al jugador.
 */
public class InterfazJugador implements INotificador {

  /**
   * Procesa las notificaciones recibidas segun su tipo de evento.
   *
   * @param e el objeto evento que contiene la informacion de la notificacion.
   */
  @Override
  public void actualizar(Evento e) {
    switch (e.getTipo()) {
      case DESAFIO_RECIBIDO:
        System.out.println("Tienes un desafio pendiente.");
        break;
      case COMBATE_FINALIZADO:
        System.out.println("El combate ha finalizado.");
        break;
      case USUARIO_BLOQUEADO:
        System.out.println("Tu cuenta ha sido bloqueada.");
        break;
      default:
        break;
    }
  }
}