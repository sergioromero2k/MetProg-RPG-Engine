package metprog.strategy;

import metprog.model.Arma;
import metprog.model.HabilidadEspecial;
import metprog.model.Personaje;
import metprog.model.Vampiro;

public class EstrategiaVampiro implements IEstrategiaPotencial {

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
    Vampiro v = (Vampiro) p;
    if (v.getPuntosSangre() >= 5) {
      return 2;
    }
    return 0;
  }
}
