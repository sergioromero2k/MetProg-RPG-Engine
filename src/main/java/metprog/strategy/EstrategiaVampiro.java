package metprog.strategy;

import metprog.model.Arma;
import metprog.model.HabilidadEspecial;
import metprog.model.Personaje;
import metprog.model.Vampiro;

public class EstrategiaVampiro implements IEstrategiaPotencial {

  public int calcularPotencial(Personaje p, HabilidadEspecial h, boolean esAtaque) {
    int poder = p.getPoder();
    int valorHabilidad = esAtaque ? h.getValorAtaque() : h.getValorDefensa();
    int modEquipo = 0;

    for (Arma arma : p.getArmasActivas()) {
      modEquipo += esAtaque ? arma.getModAtaque() : arma.getModDefensa();
    }
    if (p.getArmaduraActiva() != null) {
      modEquipo += esAtaque ? p.getArmaduraActiva().getModAtaque() : p.getArmaduraActiva().getModDefensa();
    }

    int modEspecial = getModificadorEspecial(p);
    return poder + valorHabilidad + modEquipo + modEspecial;
  }

  public int getModificadorEspecial(Personaje p) {
    Vampiro v = (Vampiro) p;
    if (v.getPuntosSangre() >= 5) {
      return 2;
    }
    return 0;
  }
}
