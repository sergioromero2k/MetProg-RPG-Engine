package metprog.factory;

import metprog.model.Arma;
import metprog.model.Armadura;
import metprog.model.Cazador;
import metprog.model.HabilidadEspecial;
import metprog.model.Personaje;
import metprog.model.Talento;

/**
 * Implementacion de la fabrica para la creacion de personajes tipo Cazador.
 *
 * <p>Define los valores iniciales especificos para un cazador, incluyendo
 * su talento base, su arma de fuego inicial y su armadura de cuero.
 */
public class FabricaCazador implements FabricaPersonaje {

  /**
   * Crea un nuevo personaje de tipo Cazador con estadisticas base.
   *
   * @param nombre el nombre que se le asignara al cazador.
   * @return una instancia de Cazador totalmente equipada.
   */
  @Override
  public Personaje crearPersonaje(String nombre) {
    Cazador c = new Cazador(nombre, 5, 3, 100);
    c.setHabilidad(crearHabilidadBase());
    c.equiparArma(crearArmaInicial());
    c.agregarArmadura(crearArmaduraInicial());
    return c;
  }

  /**
   * Crea el arma inicial por defecto del cazador.
   *
   * @return un objeto Arma que representa una pistola.
   */
  @Override
  public Arma crearArmaInicial() {
    return new Arma("Pistola", 2, 0, false);
  }

  /**
   * Crea la armadura inicial por defecto del cazador.
   *
   * @return un objeto Armadura que representa un chaleco de cuero.
   */
  @Override
  public Armadura crearArmaduraInicial() {
    return new Armadura("Chaleco de cuero", 0, 2);
  }

  /**
   * Genera el Talento inicial para el cazador.
   *
   * @return un nuevo objeto Talento con valores de ataque y defensa base.
   */
  @Override
  public HabilidadEspecial crearHabilidadBase() {
    return new Talento("Vision mejorada", 2, 1);
  }
}
