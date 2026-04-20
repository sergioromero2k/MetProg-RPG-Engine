package metprog.state;

import metprog.model.Desafio;

public interface EstadoDesafio {
  void validar(Desafio desafio);
  void aceptar(Desafio desafio);
  void rechazar(Desafio desafio);
  void cancelar(Desafio desafio);
}