package metprog.observer;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import metprog.model.Evento;

/**
 * Observador encargado de registrar la actividad del sistema en un archivo fisico.
 *
 * <p>Implementa la interfaz {@link INotificador} para persistir de forma
 * secuencial cada evento generado, incluyendo su marca de tiempo y tipo.
 */
public class LoggerSistema implements INotificador {

  private final String nombreFichero = "log.txt";

  /**
   * Registra el evento recibido en el archivo de log.
   *
   * @param e el evento que contiene la informacion a persistir.
   */
  @Override
  public void actualizar(Evento e) {
    try (FileWriter fw = new FileWriter(nombreFichero, true)) {
      fw.write(e.getFecha() + " | " + e.getTipo());
      for (Map.Entry<String, Object> entry : e.getDatos().entrySet()) {
        fw.write(" | " + entry.getKey() + "=" + entry.getValue());
      }
      fw.write("\n");
    } catch (IOException ex) {
      System.out.println("Error al escribir log: " + ex.getMessage());
    }
  }
}