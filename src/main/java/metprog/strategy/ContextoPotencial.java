package metprog.strategy;

import metprog.model.Personaje;
import metprog.model.HabilidadEspecial;

public class ContextoPotencial {
  private IEstrategiaPotencial estrategia;

  public void setEstrategia(IEstrategiaPotencial estrategia) {
    this.estrategia = estrategia;
  }
  public int calcular(Personaje personaje, HabilidadEspecial habilidad, boolean esAtaque) {
    return estrategia.calcularPotencial(personaje, habilidad, esAtaque);
  }
}