package metprog.state;

import java.io.Serializable;
import metprog.model.Desafio;

/**
 * Interfaz que define el comportamiento de los diferentes estados de un desafío.
 *
 * <p>Esta interfaz es parte de la implementación del patrón State, permitiendo
 * que el objeto Desafio cambie su comportamiento según su estado interno actual.
 * Al extender Serializable, asegura que el estado pueda persistirse junto con el
 * objeto Desafio.
 */
public interface EstadoDesafio extends Serializable {

  /**
   * Intenta validar el desafío, normalmente acción reservada para un operador.
   *
   * @param desafio el contexto del desafío sobre el que se actúa.
   */
  void validar(Desafio desafio);

  /**
   * Intenta aceptar el desafío por parte del usuario desafiado.
   *
   * @param desafio el contexto del desafío sobre el que se actúa.
   */
  void aceptar(Desafio desafio);

  /**
   * Intenta rechazar el desafío por parte del usuario desafiado.
   *
   * @param desafio el contexto del desafío sobre el que se actúa.
   */
  void rechazar(Desafio desafio);

  /**
   * Intenta cancelar el desafío, normalmente por el desafiante o por reglas de sistema.
   *
   * @param desafio el contexto del desafío sobre el que se actúa.
   */
  void cancelar(Desafio desafio);

  /**
   * Intenta finalizar el proceso de combate y cerrar el ciclo de vida del desafío.
   *
   * @param desafio el contexto del desafío sobre el que se actúa.
   */
  void finalizar(Desafio desafio);
}