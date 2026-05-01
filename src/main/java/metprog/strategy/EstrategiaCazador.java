package metprog.strategy;

import metprog.model.Arma;
import metprog.model.Cazador;
import metprog.model.HabilidadEspecial;
import metprog.model.Personaje;

/**
 * Implementación de la estrategia de cálculo de potencial para personajes de tipo Cazador.
 *
 * <p>El potencial del cazador se basa en su poder base, modificadores de equipo,
 * su habilidad especial y su valor de Voluntad como modificador único.
 */
public class EstrategiaCazador implements IEstrategiaPotencial {

  /**
   * Calcula el potencial total (ataque o defensa) para un cazador.
   *
   * @param p El personaje que realiza la acción.
   * @param h La habilidad especial utilizada.
   * @param esAtaque Verdadero si se calcula el ataque, falso para la defensa.
   * @return El valor total del potencial calculado.
   */
  @Override
  public int calcularPotencial(Personaje p, HabilidadEspecial h, boolean esAtaque) {
    int poder = p.getPoder();
    int valorHabilidad = esAtaque ? h.getValorAtaque() : h.getValorDefensa();
    int modEquipo = 0;

    for (Arma arma : p.getArmasActivas()) {
      modEquipo += esAtaque ? arma.getModAtaque() : arma.getModDefensa();
    }

    if (p.getArmaduraActiva() != null) {
      modEquipo +=
          esAtaque ? p.getArmaduraActiva().getModAtaque() : p.getArmaduraActiva().getModDefensa();
    }

    int modEspecial = getModificadorEspecial(p);
    return poder + valorHabilidad + modEquipo + modEspecial;
  }

  /**
   * Obtiene el modificador específico del cazador basado en su Voluntad.
   *
   * @param p El personaje (debe ser una instancia de Cazador).
   * @return El valor de voluntad del cazador.
   */
  public int getModificadorEspecial(Personaje p) {
    Cazador cazador = (Cazador) p;
    return cazador.getVoluntad();
  }
}