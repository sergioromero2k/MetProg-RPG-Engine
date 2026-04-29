package metprog.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import metprog.model.enums.TipoEvento;

/**
 * Representa un evento ocurrido dentro del sistema.
 *
 * <p>Se utiliza para el registro de auditoría y la comunicación de cambios
 * de estado, almacenando información estructurada en un mapa de datos.
 */
public class Evento implements Serializable {

  private static final long serialVersionUID = 1L;

  private TipoEvento tipo;
  private Map<String, Object> datos;
  private LocalDateTime fecha;

  /**
   * Construye un nuevo Evento con el tipo especificado.
   *
   * @param tipo el tipo de evento que define la naturaleza del suceso.
   */
  public Evento(TipoEvento tipo) {
    this.tipo = tipo;
    this.datos = new HashMap<>();
    this.fecha = LocalDateTime.now();
  }

  /**
   * Obtiene el tipo de evento.
   *
   * @return el enumerado que identifica el tipo de evento.
   */
  public TipoEvento getTipo() {
    return tipo;
  }

  /**
   * Obtiene el mapa de datos adicionales del evento.
   *
   * @return mapa con claves y valores de información variable.
   */
  public Map<String, Object> getDatos() {
    return datos;
  }

  /**
   * Obtiene la fecha y hora en la que se creó el evento.
   *
   * @return el objeto LocalDateTime de creación.
   */
  public LocalDateTime getFecha() {
    return fecha;
  }

  /**
   * Agrega un dato adicional al contexto del evento.
   *
   * @param clave identificador único del dato.
   * @param valor objeto con la información a almacenar.
   */
  public void agregarDato(String clave, Object valor) {
    datos.put(clave, valor);
  }
}