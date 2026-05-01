package metprog.state;

import java.io.Serializable;
import metprog.model.Desafio;

public interface EstadoDesafio extends Serializable {
  void validar(Desafio desafio);
  void aceptar(Desafio desafio);
  void rechazar(Desafio desafio);
  void cancelar(Desafio desafio);
  void finalizar(Desafio desafio);
}