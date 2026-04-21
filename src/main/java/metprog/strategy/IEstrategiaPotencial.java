package metprog.strategy;

import metprog.model.HabilidadEspecial;
import metprog.model.Personaje;

public interface IEstrategiaPotencial {
  int calcularPotencial(Personaje p, HabilidadEspecial h);
  int getModificadorEspecial(Personaje p);
}
