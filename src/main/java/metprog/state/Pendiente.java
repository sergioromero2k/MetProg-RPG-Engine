package  metprog.state;
import metprog.model.Desafio;

public class Pendiente implements EstadoDesafio {
  @Override
  public void validar(Desafio d) {
    d.setEstado(new Publicado());
  }
  @Override public void aceptar(Desafio d) {
    System.out.println("Aviso: El desafío aún está pendiente de validación.");
  }
  @Override public void rechazar(Desafio d){
    System.out.println("Aviso: El desafío aún está pendiente de validación.");
  }
  @Override public void cancelar(Desafio d){
    System.out.println("Aviso: El desafío aún está pendiente de validación.");
  }
  @Override
  public void finalizar(Desafio d) {
    System.out.println("No se puede finalizar en este estado.");
  }
}