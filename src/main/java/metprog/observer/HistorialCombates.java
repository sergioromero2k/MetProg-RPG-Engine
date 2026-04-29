package metprog.observer;

import java.util.ArrayList;
import java.util.List;
import metprog.model.Combate;
import metprog.model.Evento;
import metprog.model.enums.TipoEvento;

/**
 * Observador que mantiene un registro historico de todos los combates finalizados.
 *
 * <p>Esta clase permite consultar posteriormente los resultados de las contiendas
 * que han tenido lugar durante la ejecucion del sistema.
 */
public class HistorialCombates implements INotificador {

  private final List<Combate> historial = new ArrayList<>();

  /**
   * Procesa los eventos y almacena el combate si este ha finalizado.
   *
   * @param e el evento recibido del notificador.
   */
  @Override
  public void actualizar(Evento e) {
    if (e.getTipo() == TipoEvento.COMBATE_FINALIZADO) {
      Combate c = (Combate) e.getDatos().get("combate");
      if (c != null) {
        historial.add(c);
      }
    }
  }

  /**
   * Obtiene la lista de combates registrados hasta el momento.
   *
   * @return una lista con todos los objetos Combate finalizados.
   */
  public List<Combate> getHistorial() {
    return historial;
  }
}