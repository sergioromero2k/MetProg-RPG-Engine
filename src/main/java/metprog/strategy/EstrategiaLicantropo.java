package metprog.strategy;

import metprog.model.Arma;
import metprog.model.HabilidadEspecial;
import metprog.model.Licantropo;
import metprog.model.Personaje;
import metprog.model.Don;

/**
 * Implementación de la estrategia de cálculo de potencial para personajes de tipo Licántropo.
 *
 * <p>El potencial del licántropo suma su poder base, equipo, habilidad especial y
 * el valor acumulado de Rabia como modificador especial.
 */
public class EstrategiaLicantropo implements IEstrategiaPotencial {

  /**
   * Calcula el potencial total para un licántropo según el tipo de acción.
   *
   * @param p el personaje que realiza la acción.
   * @param h la habilidad especial utilizada.
   * @param esAtaque verdadero si se calcula ataque, falso para defensa.
   * @return el valor total del potencial.
   */

  @Override
  public int calcularPotencial(Personaje p, HabilidadEspecial h, boolean esAtaque) {
    Licantropo l = (Licantropo) p;
    int poder = p.getPoder();
    int valorHabilidad = 0;

    Don don = l.getDon();
    if (don != null && don.puedeUsarse(l.getRabia())) {
      valorHabilidad = esAtaque ? don.getValorAtaque() : don.getValorDefensa();
    }

    int modEquipo = 0;
    for (Arma arma : p.getArmasActivas()) {
      modEquipo += esAtaque ? arma.getModAtaque() : arma.getModDefensa();
    }
    if (p.getArmaduraActiva() != null) {
      modEquipo += esAtaque
          ? p.getArmaduraActiva().getModAtaque()
          : p.getArmaduraActiva().getModDefensa();
    }

    int modEspecial = getModificadorEspecial(p);
    int modNeto = p.getModificadorNeto();
    return poder + valorHabilidad + modEquipo + modEspecial + modNeto;
  }

  /**
   * Obtiene el modificador específico basado en la Rabia del licántropo.
   *
   * @param p el personaje (debe ser instancia de Licantropo).
   * @return el valor de rabia actual.
   */
  public int getModificadorEspecial(Personaje p) {
    Licantropo l = (Licantropo) p;
    return l.getRabia();
  }
}