package metprog.state;

import metprog.model.Desafio;

/**
 * Representa el estado de un desafío que ha sido declinado o cancelado.
 *
 * <p>Este es un estado terminal donde no se permiten más transiciones de lógica
 * de negocio, como validaciones o aceptaciones.
 */
public class Rechazado implements EstadoDesafio {

  @Override
  public void validar(Desafio d) {
    System.out.println("Aviso: No se puede validar un desafío que ya ha sido rechazado.");
  }

  @Override
  public void aceptar(Desafio d) {
    System.out.println("Aviso: El desafío no está disponible para ser aceptado.");
  }

  @Override
  public void rechazar(Desafio d) {
    // El desafío ya se encuentra en este estado.
  }

  @Override
  public void cancelar(Desafio d) {
    System.out.println("Aviso: El desafío ya estaba cancelado/rechazado.");
  }

  @Override
  public void finalizar(Desafio d) {
    System.out.println("No se puede finalizar en este estado.");
  }
}