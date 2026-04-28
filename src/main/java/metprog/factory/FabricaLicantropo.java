package metprog.factory;

import metprog.model.Arma;
import metprog.model.Armadura;
import metprog.model.Don;
import metprog.model.HabilidadEspecial;
import metprog.model.Licantropo;
import metprog.model.Personaje;

/**
 * Implementacion de la fabrica para la creacion de personajes tipo Licantropo.
 *
 * <p>Esta clase define los valores iniciales especificos para un licantropo,
 * incluyendo su habilidad base (Don), su arma de garras y su armadura natural.
 */
public class FabricaLicantropo implements FabricaPersonaje {

  /**
   * Crea un nuevo personaje de tipo Licantropo con estadisticas base.
   *
   * @param nombre el nombre que se le asignara al licantropo.
   * @return una instancia de Licantropo totalmente equipada.
   */
  @Override
  public Personaje crearPersonaje(String nombre) {
    Licantropo l = new Licantropo(nombre, 5, 3, 100);
    l.setHabilidad(crearHabilidadBase());
    l.equiparArma(crearArmaInicial());
    l.agregarArmadura(crearArmaduraInicial());
    return l;
  }

  /**
   * Genera el Don inicial para el licantropo.
   *
   * @return un nuevo objeto Don con valores de ataque y defensa base.
   */
  @Override
  public HabilidadEspecial crearHabilidadBase() {
    return new Don("Morder duro", 2, 1, 1);
  }

  /**
   * Crea el arma inicial por defecto del licantropo.
   *
   * @return un objeto Arma que representa las garras.
   */
  @Override
  public Arma crearArmaInicial() {
    return new Arma("Garras", 2, 0, false);
  }

  /**
   * Crea la armadura inicial por defecto del licantropo.
   *
   * @return un objeto Armadura que representa la piel gruesa.
   */
  @Override
  public Armadura crearArmaduraInicial() {
    return new Armadura("Piel Gruesa", 2, 0);
  }

}
