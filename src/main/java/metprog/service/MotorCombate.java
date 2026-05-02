package metprog.service;

import java.util.Random;
import metprog.model.Cazador;
import metprog.model.Combate;
import metprog.model.Desafio;
import metprog.model.Licantropo;
import metprog.model.Personaje;
import metprog.model.RondaCombate;
import metprog.model.Vampiro;
import metprog.strategy.ContextoPotencial;
import metprog.strategy.EstrategiaCazador;
import metprog.strategy.EstrategiaLicantropo;
import metprog.strategy.EstrategiaVampiro;
import metprog.strategy.IEstrategiaPotencial;

/**
 * Motor encargado de gestionar la lógica de resolución de combates.
 *
 * <p>Calcula el potencial de ataque y defensa de cada contendiente basándose en
 * sus estrategias específicas, ejecuta el lanzamiento de dados y gestiona las
 * rondas hasta que uno o ambos personajes caen.
 */
public class MotorCombate {

  private final Random random = new Random();

  /**
   * Ejecuta el proceso de combate completo entre el desafiante y el desafiado.
   *
   * @param desafio el objeto desafío que contiene a los dos contendientes.
   * @return un objeto Combate con el historial de rondas y el vencedor.
   */
  public Combate ejecutarCombate(Desafio desafio) {
    Personaje p1 = desafio.getDesafiante().getPersonaje();
    Personaje p2 = desafio.getDesafiado().getPersonaje();

    p1.reiniciarParaCombate();
    p2.reiniciarParaCombate();

    Combate combate = new Combate(desafio.getDesafiante(), desafio.getDesafiado());
    int numeroRonda = 0;

    while (p1.estaVivo() && p2.estaVivo()) {
      numeroRonda++;

      ContextoPotencial ctx = new ContextoPotencial();

      // Turno del personaje 1
      ctx.setEstrategia(determinarEstrategia(p1));
      int ataqueP1 = ctx.calcular(p1, p1.getHabilidad(), true);
      int defensaP1 = ctx.calcular(p1, p1.getHabilidad(), false);

      // Turno del personaje 2
      ctx.setEstrategia(determinarEstrategia(p2));
      int ataqueP2 = ctx.calcular(p2, p2.getHabilidad(), true);
      int defensaP2 = ctx.calcular(p2, p2.getHabilidad(), false);

      // Resolución de éxitos
      int exitosAtaqueP1 = lanzarDados(ataqueP1);
      int exitosDefensaP2 = lanzarDados(defensaP2);
      int exitosAtaqueP2 = lanzarDados(ataqueP2);
      int exitosDefensaP1 = lanzarDados(defensaP1);

      // Aplicación de daño
      String resultadoRonda = "Empate";
      if (exitosAtaqueP1 >= exitosDefensaP2) {
        p2.recibirDano(1);
        resultadoRonda = desafio.getDesafiante().getNick();
      }

      if (exitosAtaqueP2 >= exitosDefensaP1) {
        p1.recibirDano(1);
        if (!resultadoRonda.equals("Empate")) {
          resultadoRonda = "Empate";
        } else {
          resultadoRonda = desafio.getDesafiado().getNick();
        }
      }

      RondaCombate ronda = new RondaCombate(
          numeroRonda,
          exitosAtaqueP1,
          exitosDefensaP1,
          exitosAtaqueP2,
          exitosDefensaP2,
          desafio.getDesafiante().getNick(),
          desafio.getDesafiado().getNick(),
          resultadoRonda
      );
      combate.agregarRonda(ronda);
    }

    // Determinación del vencedor
    actualizarResultadoCombate(combate, desafio, p1, p2, numeroRonda);

    return combate;
  }

  /**
   * Realiza el lanzamiento de dados basado en el potencial calculado.
   *
   * @param potencial número de dados a lanzar.
   * @return cantidad de éxitos obtenidos (dados con valor mayor o igual a 5).
   */
  public int lanzarDados(int potencial) {
    int exitos = 0;
    for (int i = 0; i < potencial; i++) {
      int dado = random.nextInt(6) + 1;
      if (dado >= 5) {
        exitos++;
      }
    }
    return exitos;
  }

  /**
   * Determina la estrategia de cálculo de potencial según el tipo de personaje.
   *
   * @param p el personaje a evaluar.
   * @return la instancia de la estrategia correspondiente o null si no aplica.
   */
  private IEstrategiaPotencial determinarEstrategia(Personaje p) {
    if (p instanceof Vampiro) {
      return new EstrategiaVampiro();
    } else if (p instanceof Licantropo) {
      return new EstrategiaLicantropo();
    } else if (p instanceof Cazador) {
      return new EstrategiaCazador();
    }
    return null;
  }

  /**
   * Actualiza el estado final del objeto combate tras la simulación.
   */
  private void actualizarResultadoCombate(Combate c, Desafio d,
                                          Personaje p1, Personaje p2, int rondas) {
    if (!p1.estaVivo() && !p2.estaVivo()) {
      c.setVencedor(null);
    } else if (p1.estaVivo() && !p2.estaVivo()) {
      c.setVencedor(d.getDesafiante());
    } else if (p2.estaVivo() && !p1.estaVivo()) {
      c.setVencedor(d.getDesafiado());
    }
    c.setRondasEmpleadas(rondas);
  }
}