package metprog.state;

import metprog.model.Desafio;

/**
 * Representa el estado de un desafío que ha sido validado por un operador.
 *
 * <p>En este estado, el desafío es visible para el usuario desafiado, quien puede
 * optar por aceptarlo (pasando a combate) o rechazarlo. El desafiante también
 * podría cancelarlo en este punto.
 */
public class Publicado implements EstadoDesafio {

  @Override
  public void validar(Desafio d) {
    // El desafío ya ha sido validado. No se requiere acción adicional.
  }

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