package metprog.model;

import java.io.Serializable;

public abstract class Esbirro implements Serializable {
  private String nombre;
  private int salud;


  public Esbirro (String nombre, int salud) {
    setNombre(nombre);
    setSalud(salud);
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
    if (salud >= 1 && salud <= 3) {
      this.salud = salud;
    } else {
      System.out.println("Error: Salud debe estar entre 1 y 3.");
      this.salud = 1;
    }
  }
}