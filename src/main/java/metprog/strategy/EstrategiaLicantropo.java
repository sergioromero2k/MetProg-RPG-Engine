package metprog.strategy;

import metprog.model.Arma;
import metprog.model.HabilidadEspecial;
import metprog.model.Personaje;
import metprog.model.Licantropo;

public class EstrategiaLicantropo implements IEstrategiaPotencial {

  public int calcularPotencial(Personaje p, HabilidadEspecial h) {
    int poder = p.getPoder();
    int valorHabilidad = h.getValorAtaque();
    int modEquipo = 0;

    for (Arma arma : p.getArmasActivas()) {
      modEquipo += arma.getModAtaque();
    }
    if (p.getArmaduraActiva() != null) {
      modEquipo += p.getArmaduraActiva().getModAtaque();
    }

    int modEspecial = getModificadorEspecial(p);
    return poder + valorHabilidad + modEquipo + modEspecial;
  }

  public int getModificadorEspecial(Personaje p) {
    Licantropo l = (Licantropo) p;
    return l.getRabia();
  }
}