package metprog.strategy;

import metprog.model.HabilidadEspecial;
import metprog.model.Personaje;

/**
 * Define el contrato para las diferentes estrategias de cálculo de potencial.
 *
 * <p>Esta interfaz permite implementar algoritmos específicos para determinar el valor
 * de ataque o defensa según el tipo de personaje (Vampiro, Licántropo, Cazador).
 */
public interface IEstrategiaPotencial {

  /**
   * Calcula el valor total del potencial basado en los atributos del personaje.
   *
   * @param p el personaje que realiza la acción.
   * @param h la habilidad especial que se está aplicando.
   * @param esAtaque indica si se debe calcular el potencial de ataque o de defensa.
   * @return el valor entero resultante del cálculo.
   */
  int calcularPotencial(Personaje p, HabilidadEspecial h, boolean esAtaque);

  /**
   * Obtiene el modificador único y específico de la clase del personaje.
   *
   * @param p el personaje del cual extraer el modificador especial.
   * @return el valor entero del modificador (ej: Rabia, Voluntad o Sangre).
   */
  int getModificadorEspecial(Personaje p);
}