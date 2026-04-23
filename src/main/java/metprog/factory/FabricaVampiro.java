package metprog.factory;

import metprog.model.*;

public class FabricaVampiro {
  Personaje crearPersonaje(String nombre) {
    Vampiro v = new Vampiro(nombre, 5, 3, 100);
    return v;
  }
  Arma crearArmaInicial();
  Armadura crearArmaduraInicial();
  HabilidadEspecial crearHabilidadBase();
}
