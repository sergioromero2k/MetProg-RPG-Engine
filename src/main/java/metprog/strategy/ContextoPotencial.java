package metprog.strategy;

import metprog.model.HabilidadEspecial;
import metprog.model.Personaje;

/**
 * Clase de contexto para la aplicación del patrón Strategy en el cálculo de potencial.
 *
 * <p>Esta clase permite alternar dinámicamente entre diferentes algoritmos de cálculo
 * de potencial (ataque o defensa) dependiendo de la estrategia configurada.
 */
public class ContextoPotencial {

  private IEstrategiaPotencial estrategia;

  /**
   * Configura la estrategia concreta a utilizar.
   *
   * @param estrategia implementación de la estrategia de cálculo de potencial.
   */
  public void setEstrategia(IEstrategiaPotencial estrategia) {
    this.estrategia = estrategia;
  }

  /**
   * Ejecuta el cálculo del potencial delegando en la estrategia actual.
   *
   * @param personaje el personaje que realiza la acción.
   * @param habilidad la habilidad especial aplicada.
   * @param esAtaque indica si el cálculo es para una acción ofensiva o defensiva.
   * @return el valor entero del potencial calculado.
   */
  public int calcular(Personaje personaje, HabilidadEspecial habilidad, boolean esAtaque) {
    return estrategia.calcularPotencial(personaje, habilidad, esAtaque);
  }
}