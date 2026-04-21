package metprog.state;
import metprog.model.Desafio;

public class Publicado implements EstadoDesafio {
  @Override public void validar(Desafio d){}
  @Override
  public void aceptar(Desafio d) {
    d.setEstado(new EnCombate());
  }
  @Override
  public void rechazar(Desafio d) {
    d.setEstado(new Rechazado());
  }
  @Override
  public void cancelar(Desafio d) {
    d.setEstado(new Rechazado());
  }
  @Override
  public void finalizar(Desafio d) {
    System.out.println("No se puede finalizar en este estado.");
  }
}