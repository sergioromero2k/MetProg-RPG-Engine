package metprog.model;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Personaje implements Serializable {
  private String nombre;
  private int salud;
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

  public Personaje(String nombre, int salud, int poder, int oro) {
    setNombre(nombre);
    setSalud(salud);
    setPoder(poder);
    setOro(oro);
    this.armas = new ArrayList<>();
    this.armasActivas = new ArrayList<>();
    this.armaduras = new ArrayList<>();
    this.esbirros = new ArrayList<>();
    this.fortalezas = new ArrayList<>();
    this.debilidades = new ArrayList<>();
  }

  public String getNombre() {
    return nombre;
  }
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }
  public int getSalud() {
    return salud;
  }
  public void setSalud(int salud) {
    if (salud >= 0 && salud <= 5) {
      this.salud = salud;
    } else {
      System.out.println("Error: Salud debe estar entre 0 y 5.");
      this.salud = 0;
    }
  }

  public int getPoder() {
    return poder;
  }
  public void setPoder(int poder) {
    if (poder >= 1 && poder <= 5) {
      this.poder = poder;
    } else {
      System.out.println("Error: Poder debe estar entre 1 y 5.");
      this.poder = 0;
    }
  }
  public int getOro() {
    return oro;
  }
  public void setOro(int oro) {
    if (oro >= 0) {
      this.oro = oro;
    } else {
      System.out.println("Error: Oro debe ser positivo o 0.");
      this.oro = 0;
    }
  }

  public void equiparArma(Arma arma) {
    armas.add(arma);
  }

  public void añadirArmadura(Armadura armadura) {
    armaduras.add(armadura);
  }

  public void añadirEsbirro(Esbirro esbirro) {
    esbirros.add(esbirro);
  }

  public void añadirFortaleza(Fortaleza fortaleza) {
    fortalezas.add(fortaleza);
  }

  public void añadirDebilidad(Debilidad debilidad) {
    debilidades.add(debilidad);
  }

  public HabilidadEspecial getHabilidad () {
    return habilidad;
  }

  public void setHabilidad(HabilidadEspecial habilidad) {
    this.habilidad = habilidad;
  }

  public ArrayList<Arma> getArmas() {
    return armas;
  }

  public ArrayList<Arma> getArmasActivas() {
    return armasActivas;
  }

  public ArrayList<Armadura> getArmaduras() {
    return armaduras;
  }

  public Armadura getArmaduraActiva() {
    return armaduraActiva;
  }

  public void setArmaduraActiva(Armadura armaduraActiva) {
    this.armaduraActiva = armaduraActiva;
  }

  public ArrayList<Esbirro> getEsbirros() {
    return esbirros;
  }

  public ArrayList<Fortaleza> getFortalezas() {
    return fortalezas;
  }

  public ArrayList<Debilidad> getDebilidades() {
    return debilidades;
  }

  public void recibirDaño(int cantidad) {
    setSalud(Math.max(0, getSalud() - cantidad));
  }

  public void reiniciarParaCombate() {

  }
}

