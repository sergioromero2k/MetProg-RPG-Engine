package metprog.observer;

import metprog.model.Evento;
import metprog.model.Combate;
import metprog.model.Desafio;
import metprog.model.Usuario;

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
        Desafio desafioRecibido = (Desafio) e.getDatos().get("desafio");
        if (desafioRecibido != null) {
          System.out.println("Tienes un desafío pendiente de "
              + desafioRecibido.getDesafiante().getNick() + ".");
        } else {
          System.out.println("Tienes un desafio pendiente.");
        }
        break;
      case DESAFIO_ACEPTADO:
        Desafio desafioAceptado = (Desafio) e.getDatos().get("desafio");
        if (desafioAceptado != null) {
          System.out.println("Has aceptado el desafío de "
              + desafioAceptado.getDesafiante().getNick() + ".");
        }
        break;
      case DESAFIO_RECHAZADO:
        Desafio desafioRechazado = (Desafio) e.getDatos().get("desafio");
        if (desafioRechazado != null) {
          System.out.println("El desafío de "
              + desafioRechazado.getDesafiante().getNick() + " ha sido rechazado.");
        }
        break;
      case COMBATE_FINALIZADO:
        Combate combate = (Combate) e.getDatos().get("combate");
        if (combate != null) {
          if (combate.esEmpate()) {
            System.out.println("El combate ha finalizado en empate.");
          } else {
            System.out.println("El combate ha finalizado. Vencedor: "
                + combate.getVencedor().getNick() + ".");
          }
        } else {
          System.out.println("El combate ha finalizado.");
        }
        break;
      case USUARIO_BLOQUEADO:
        Usuario usuario = (Usuario) e.getDatos().get("usuario");
        if (usuario != null) {
          System.out.println("La cuenta de " + usuario.getNick() + " ha sido bloqueada.");
        } else {
          System.out.println("Tu cuenta ha sido bloqueada.");
        }
        break;
      default:
        break;
    }
  }
}