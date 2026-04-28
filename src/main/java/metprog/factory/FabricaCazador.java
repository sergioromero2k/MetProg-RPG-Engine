package metprog.factory;

import metprog.model.*;

public class FabricaCazador implements FabricaPersonaje {

  @Override
  public Personaje crearPersonaje(String nombre) {
    Cazador c = new Cazador(nombre, 5, 3, 100);
    c.setHabilidad(crearHabilidadBase());
    c.equiparArma(crearArmaInicial());
    c.agregarArmadura(crearArmaduraInicial());
    return c;
  }

  @Override
  public Arma crearArmaInicial() {
    return new Arma("Pistola", 2, 0, false);
  }

  @Override
  public Armadura crearArmaduraInicial() {
    return new Armadura("Chaleco de cuero", 0, 2);
  }

  @Override
  public HabilidadEspecial crearHabilidadBase() {
    return new Talento("Vision mejorada", 2, 1);
  }
}
