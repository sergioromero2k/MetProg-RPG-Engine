package metprog.strategy;

import metprog.model.Arma;
import metprog.model.HabilidadEspecial;
import metprog.model.Personaje;
import metprog.model.Vampiro;

/**
 * Implementación de la estrategia de cálculo de potencial para personajes de tipo Vampiro.
 *
 * <p>El potencial del vampiro incluye su poder base, el equipo activo, su habilidad especial
 * y un modificador adicional basado en sus puntos de sangre acumulados.
 */
public class EstrategiaVampiro implements IEstrategiaPotencial {

  /**
   * Calcula el potencial total (ataque o defensa) para un vampiro.
   *
   * @param p el personaje que realiza la acción.
   * @param h la habilidad especial utilizada.
   * @param esAtaque verdadero si se calcula ataque, falso para defensa.
   * @return el valor total del potencial.
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
      modEquipo += esAtaque
          ? p.getArmaduraActiva().getModAtaque()
          : p.getArmaduraActiva().getModDefensa();
    }

    int modEspecial = getModificadorEspecial(p);
    return poder + valorHabilidad + modEquipo + modEspecial;
  }

  /**
   * Obtiene el modificador específico del vampiro basado en su reserva de sangre.
   *
   * @param p el personaje (debe ser instancia de Vampiro).
   * @return 2 si tiene 5 o más puntos de sangre, 0 en caso contrario.
   */
  public int getModificadorEspecial(Personaje p) {
    Vampiro v = (Vampiro) p;
    if (v.getPuntosSangre() >= 5) {
      return 2;
    }
    return 0;
  }
}