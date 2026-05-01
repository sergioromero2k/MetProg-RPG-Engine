package metprog.state;

import metprog.model.Desafio;

/**
 * Representa el estado en el que se está ejecutando el combate de un desafío.
 *
 * <p>En este estado, las acciones de validación, aceptación o rechazo no son
 * permitidas. La única transición válida es la finalización del combate.
 */
public class EnCombate implements EstadoDesafio {

  @Override
  public void validar(Desafio d) {
    System.out.println("El desafío ya está en combate.");
  }

  @Override
  public void aceptar(Desafio d) {
    System.out.println("El desafío ya está en combate.");
  }

  @Override
  public void rechazar(Desafio d) {
    System.out.println("El desafío ya está en combate.");
  }

  @Override
  public void cancelar(Desafio d) {
    System.out.println("El desafío ya está en combate.");
  }

  @Override
  public void finalizar(Desafio d) {
    d.setEstado(new Completado());
  }
}