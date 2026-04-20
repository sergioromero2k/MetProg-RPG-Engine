package metprog.state;
import metprog.model.Desafio;

public class Rechazado implements EstadoDesafio {
  @Override public void validar(Desafio d) {
    System.out.println("Aviso: No se puede validar un desafío que ya ha sido rechazado.");
  };
  @Override public void aceptar(Desafio d) {
    System.out.println("Aviso: El desafío no está disponible para ser aceptado.");

  };
  @Override public void rechazar(Desafio d) {};
  @Override public void cancelar(Desafio d) {
    System.out.println("Aviso: El desafío ya estaba cancelado/rechazado.");
  };
}