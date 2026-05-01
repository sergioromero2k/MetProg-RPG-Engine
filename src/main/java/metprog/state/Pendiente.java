package metprog.state;

import metprog.model.Desafio;

/**
 * Representa el estado inicial de un desafío tras ser creado.
 *
 * <p>En este estado, el desafío está a la espera de que un operador lo valide
 * para pasar al estado Publicado. Las acciones de aceptar, rechazar o cancelar
 * no están permitidas hasta que la validación se complete.
 */
public class Pendiente implements EstadoDesafio {

  @Override
  public void validar(Desafio d) {
    d.setEstado(new Publicado());
  }

  @Override
  public void aceptar(Desafio d) {
    System.out.println("Aviso: El desafío aún está pendiente de validación.");
  }

  @Override
  public void rechazar(Desafio d) {
    System.out.println("Aviso: El desafío aún está pendiente de validación.");
  }

  @Override
  public void cancelar(Desafio d) {
    System.out.println("Aviso: El desafío aún está pendiente de validación.");
  }

  @Override
  public void finalizar(Desafio d) {
    System.out.println("No se puede finalizar en este estado.");
  }
}