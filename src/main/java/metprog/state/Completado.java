package metprog.state;

import metprog.model.Desafio;

/**
 * Representa el estado final de un desafío que ha concluido con éxito.
 *
 * <p>En este estado, todas las acciones de transición son inválidas dado que
 * el ciclo de vida del desafío ha terminado.
 */
public class Completado implements EstadoDesafio {

  @Override
  public void validar(Desafio d) {
    System.out.println("El desafío ya está completado.");
  }

  @Override
  public void aceptar(Desafio d) {
    System.out.println("El desafío ya está completado.");
  }

  @Override
  public void rechazar(Desafio d) {
    System.out.println("El desafío ya está completado.");
  }

  @Override
  public void cancelar(Desafio d) {
    System.out.println("El desafío ya está completado.");
  }

  @Override
  public void finalizar(Desafio d) {
    System.out.println("No se puede finalizar en este estado.");
  }
}