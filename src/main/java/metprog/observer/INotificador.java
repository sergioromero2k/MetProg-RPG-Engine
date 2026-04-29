package metprog.observer;

import metprog.model.Evento;

/**
 * Interfaz que define el comportamiento de un observador en el sistema.
 *
 * <p>Cualquier clase que desee reaccionar a los eventos generados por el
 * sujeto (Notificador) debe implementar este metodo para procesar la informacion.
 */
public interface INotificador {

  /**
   * Metodo llamado cuando se produce un cambio de estado o suceso relevante.
   *
   * @param evento objeto que contiene los detalles de lo ocurrido.
   */
  void actualizar(Evento evento);
}