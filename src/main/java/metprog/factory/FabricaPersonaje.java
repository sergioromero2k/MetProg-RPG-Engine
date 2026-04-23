package metprog.factory;

import metprog.model.Personaje;
import metprog.model.Arma;
import metprog.model.HabilidadEspecial;
import metprog.model.Armadura;

public interface FabricaPersonaje {
  Personaje crearPersonaje(String nombre);
  Arma crearArmaInicial();
  Armadura crearArmaduraInicial();
  HabilidadEspecial crearHabilidadBase();
}
