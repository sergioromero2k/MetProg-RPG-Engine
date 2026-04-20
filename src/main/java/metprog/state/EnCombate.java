package metprog.state;

import metprog.model.Desafio;

public class EnCombate implements EstadoDesafio {
  @Override
  public void validar(Desafio d) {}
  @Override public void aceptar(Desafio d) { /* Bloqueado */ }
  @Override public void rechazar(Desafio d){}
  @Override public void cancelar(Desafio d){}
}