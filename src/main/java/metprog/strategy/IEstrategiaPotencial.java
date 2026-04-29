package metprog.strategy;

import metprog.model.HabilidadEspecial;
import metprog.model.Personaje;

public interface IEstrategiaPotencial {
  int calcularPotencial(Personaje p, HabilidadEspecial h, boolean esAtaque);
  int getModificadorEspecial(Personaje p);
}
