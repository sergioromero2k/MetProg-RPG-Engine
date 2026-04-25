package metprog.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase base abstracta que define el comportamiento y atributos de un personaje.
 *
 * <p>Gestiona la salud, poder, inventario de armas, armaduras y esbirros,
 * además de los modificadores activos durante el combate.
 */
public abstract class Personaje implements Serializable {

  private static final long serialVersionUID = 1L;

  private String nombre;
  private int salud;
  private int saludInicial;
  private int poder;
  private int oro;
  private HabilidadEspecial habilidad;
  private ArrayList<Arma> armas;
  private ArrayList<Arma> armasActivas;
  private ArrayList<Armadura> armaduras;
  private Armadura armaduraActiva;
  private ArrayList<Esbirro> esbirros;
  private ArrayList<Fortaleza> fortalezas;
  private ArrayList<Debilidad> debilidades;
  private ArrayList<Fortaleza> fortalezasPresentes;
  private ArrayList<Debilidad> debilidadesPresentes;

  /**
   * Constructor de la clase Personaje.
   *
   * @param nombre nombre del personaje.
   * @param salud salud inicial (0-5).
   * @param poder poder base (1-5).
   * @param oro cantidad de oro inicial.
   */
  public Personaje(String nombre, int salud, int poder, int oro) {
    setNombre(nombre);
    setSalud(salud);
    setPoder(poder);
    setOro(oro);
    this.saludInicial = salud;
    this.armas = new ArrayList<>();
    this.armasActivas = new ArrayList<>();
    this.armaduras = new ArrayList<>();
    this.esbirros = new ArrayList<>();
    this.fortalezas = new ArrayList<>();
    this.debilidades = new ArrayList<>();
    this.fortalezasPresentes = new ArrayList<>();
    this.debilidadesPresentes = new ArrayList<>();
  }

  /**
   * Obtiene el nombre del personaje.
   *
   * @return el nombre del personaje.
   */
  public String getNombre() {
    return nombre;
  }

  /**
   * Establece el nombre del personaje.
   *
   * @param nombre el nuevo nombre del personaje.
   */
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  /**
   * Obtiene la salud actual del personaje.
   *
   * @return la salud actual.
   */
  public int getSalud() {
    return salud;
  }

  /**
   * Establece la salud del personaje validando el rango.
   *
   * @param salud valor de salud entre 0 y 5.
   */
  public void setSalud(int salud) {
    if (salud >= 0 && salud <= 5) {
      this.salud = salud;
    } else {
      System.out.println("Error: Salud debe estar entre 0 y 5.");
      this.salud = 0;
    }
  }

  /**
   * Obtiene el poder base del personaje.
   *
   * @return el poder base.
   */
  public int getPoder() {
    return poder;
  }

  /**
   * Establece el poder del personaje validando el rango.
   *
   * @param poder valor de poder entre 1 y 5.
   */
  public void setPoder(int poder) {
    if (poder >= 1 && poder <= 5) {
      this.poder = poder;
    } else {
      System.out.println("Error: Poder debe estar entre 1 y 5.");
      this.poder = 1;
    }
  }

  /**
   * Obtiene la cantidad de oro actual.
   *
   * @return el oro disponible.
   */
  public int getOro() {
    return oro;
  }

  /**
   * Establece la cantidad de oro validando que sea positivo.
   *
   * @param oro valor de oro (no puede ser negativo).
   */
  public void setOro(int oro) {
    if (oro >= 0) {
      this.oro = oro;
    } else {
      System.out.println("Error: Oro debe ser positivo o 0.");
      this.oro = 0;
    }
  }

  /**
   * Obtiene la habilidad especial asignada.
   *
   * @return la habilidad especial.
   */
  public HabilidadEspecial getHabilidad() {
    return habilidad;
  }

  /**
   * Establece la habilidad especial del personaje.
   *
   * @param habilidad la nueva habilidad especial.
   */
  public void setHabilidad(HabilidadEspecial habilidad) {
    this.habilidad = habilidad;
  }

  /**
   * Obtiene la lista completa de armas en el inventario.
   *
   * @return lista de armas totales.
   */
  public ArrayList<Arma> getArmas() {
    return armas;
  }

  /**
   * Obtiene la lista de armas equipadas actualmente.
   *
   * @return lista de armas activas.
   */
  public ArrayList<Arma> getArmasActivas() {
    return armasActivas;
  }

  /**
   * Añade un arma al inventario general.
   *
   * @param arma el arma a añadir.
   */
  public void equiparArma(Arma arma) {
    armas.add(arma);
  }

  /**
   * Establece las armas activas validando las restricciones de equipo.
   *
   * @param seleccion lista de armas elegidas.
   * @return true si la selección cumple las reglas, false en caso contrario.
   */
  public boolean setArmasActivas(List<Arma> seleccion) {
    if (seleccion == null || seleccion.isEmpty()) {
      System.out.println("Error: debe seleccionar al menos un arma.");
      return false;
    }
    for (Arma a : seleccion) {
      if (!armas.contains(a)) {
        System.out.println("Error: el arma no está en el inventario.");
        return false;
      }
    }
    if (seleccion.size() == 1) {
      armasActivas = new ArrayList<>(seleccion);
      return true;
    }
    if (seleccion.size() > 2) {
      System.out.println("Error: máximo 2 armas de 1 mano activas.");
      return false;
    }
    for (Arma a : seleccion) {
      if (a.isDosManos()) {
        System.out.println("Error: un arma de 2 manos solo puede estar activa sola.");
        return false;
      }
    }
    armasActivas = new ArrayList<>(seleccion);
    return true;
  }

  /**
   * Obtiene la lista de armaduras en el inventario.
   *
   * @return lista de armaduras.
   */
  public ArrayList<Armadura> getArmaduras() {
    return armaduras;
  }

  /**
   * Obtiene la armadura equipada actualmente.
   *
   * @return la armadura activa.
   */
  public Armadura getArmaduraActiva() {
    return armaduraActiva;
  }

  /**
   * Añade una armadura al inventario del personaje.
   *
   * @param armadura la armadura a añadir.
   */
  public void agregarArmadura(Armadura armadura) {
    armaduras.add(armadura);
  }

  /**
   * Activa una armadura del inventario.
   *
   * @param armadura la armadura a equipar.
   * @return true si la armadura pertenece al personaje.
   */
  public boolean setArmaduraActiva(Armadura armadura) {
    if (!armaduras.contains(armadura)) {
      System.out.println("Error: la armadura no está en el inventario.");
      return false;
    }
    this.armaduraActiva = armadura;
    return true;
  }

  /**
   * Verifica si el personaje tiene equipo ofensivo y defensivo.
   *
   * @return true si tiene equipo activo.
   */
  public boolean tieneEquipoActivo() {
    return !armasActivas.isEmpty() && armaduraActiva != null;
  }

  /**
   * Obtiene la lista de esbirros del personaje.
   *
   * @return lista de esbirros.
   */
  public ArrayList<Esbirro> getEsbirros() {
    return esbirros;
  }

  /**
   * Añade un esbirro a la lista de aliados.
   *
   * @param esbirro el esbirro a añadir.
   */
  public void agregarEsbirro(Esbirro esbirro) {
    esbirros.add(esbirro);
  }

  /**
   * Elimina un esbirro de la lista de aliados.
   *
   * @param esbirro el esbirro a eliminar.
   */
  public void eliminarEsbirro(Esbirro esbirro) {
    esbirros.remove(esbirro);
  }

  /**
   * Calcula la salud total sumando la de todos los esbirros.
   *
   * @return la suma de salud de la jerarquía de esbirros.
   */
  public int getSaludTotalEsbirros() {
    int total = 0;
    for (Esbirro e : esbirros) {
      total += e.getSalud();
      if (e instanceof EsbirroDemonio) {
        total += ((EsbirroDemonio) e).getSaludTotalSubesbirros();
      }
    }
    return total;
  }

  /**
   * Obtiene la lista de fortalezas del personaje.
   *
   * @return lista de fortalezas.
   */
  public ArrayList<Fortaleza> getFortalezas() {
    return fortalezas;
  }

  /**
   * Obtiene la lista de debilidades del personaje.
   *
   * @return lista de debilidades.
   */
  public ArrayList<Debilidad> getDebilidades() {
    return debilidades;
  }

  /**
   * Añade una fortaleza al catálogo del personaje.
   *
   * @param fortaleza la fortaleza a añadir.
   */
  public void agregarFortaleza(Fortaleza fortaleza) {
    fortalezas.add(fortaleza);
  }

  /**
   * Elimina una fortaleza del catálogo.
   *
   * @param fortaleza la fortaleza a eliminar.
   */
  public void eliminarFortaleza(Fortaleza fortaleza) {
    fortalezas.remove(fortaleza);
  }

  /**
   * Añade una debilidad al catálogo del personaje.
   *
   * @param debilidad la debilidad a añadir.
   */
  public void agregarDebilidad(Debilidad debilidad) {
    debilidades.add(debilidad);
  }

  /**
   * Elimina una debilidad del catálogo.
   *
   * @param debilidad la debilidad a eliminar.
   */
  public void eliminarDebilidad(Debilidad debilidad) {
    debilidades.remove(debilidad);
  }

  /**
   * Obtiene las fortalezas activas en la ronda actual.
   *
   * @return lista de fortalezas presentes.
   */
  public ArrayList<Fortaleza> getFortalezasPresentes() {
    return fortalezasPresentes;
  }

  /**
   * Obtiene las debilidades activas en la ronda actual.
   *
   * @return lista de debilidades presentes.
   */
  public ArrayList<Debilidad> getDebilidadesPresentes() {
    return debilidadesPresentes;
  }

  /**
   * Establece las fortalezas presentes para el cálculo de combate.
   *
   * @param fp lista de fortalezas para la ronda.
   */
  public void setFortalezasPresentes(ArrayList<Fortaleza> fp) {
    this.fortalezasPresentes = fp;
  }

  /**
   * Establece las debilidades presentes para el cálculo de combate.
   *
   * @param dp lista de debilidades para la ronda.
   */
  public void setDebilidadesPresentes(ArrayList<Debilidad> dp) {
    this.debilidadesPresentes = dp;
  }

  /**
   * Calcula el modificador neto de la ronda.
   *
   * @return valor resultante del balance de modificadores.
   */
  public int getModificadorNeto() {
    int suma = 0;
    for (Fortaleza f : fortalezasPresentes) {
      suma += f.getValor();
    }
    for (Debilidad d : debilidadesPresentes) {
      suma -= d.getValor();
    }
    return suma;
  }

  /**
   * Verifica si el personaje sigue en pie.
   *
   * @return true si la salud es mayor a cero.
   */
  public boolean estaVivo() {
    return salud > 0;
  }

  /**
   * Aplica daño a la salud del personaje.
   *
   * @param cantidad puntos de daño a restar.
   */
  public void recibirDano(int cantidad) {
    setSalud(Math.max(0, salud - cantidad));
  }

  /**
   * Restablece el estado del personaje para un nuevo combate.
   */
  public void reiniciarParaCombate() {
    setSalud(saludInicial);
    fortalezasPresentes.clear();
    debilidadesPresentes.clear();
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + " " + nombre
        + " [Salud:" + salud + "/5 Poder:" + poder + " Oro:" + oro + "]";
  }
}