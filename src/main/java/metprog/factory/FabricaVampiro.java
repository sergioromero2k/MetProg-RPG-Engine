package metprog.factory;

import java.util.Arrays;
import metprog.model.*;

public class FabricaVampiro implements FabricaPersonaje {

  @Override
  public Personaje crearPersonaje(String nombre) {
    Vampiro v = new Vampiro(nombre, 5, 3, 100);
    v.setPuntosSangre(5);
    v.setHabilidad(crearHabilidadBase());

    Arma arma = crearArmaInicial();
    v.equiparArma(arma);
    v.setArmasActivas(Arrays.asList(arma));   

    Armadura armadura = crearArmaduraInicial();
    v.agregarArmadura(armadura);
    v.setArmaduraActiva(armadura);            

    return v;
  }

  @Override
  public Arma crearArmaInicial() {
    return new Arma("Colmillos", 2, 0, false);
  }

  @Override
  public Armadura crearArmaduraInicial() {
    return new Armadura("Capa Oscura", 0, 2);
  }

  @Override
  public HabilidadEspecial crearHabilidadBase() {
    return new Disciplina("Dominio Mental", 2, 1, 1);
  }
}