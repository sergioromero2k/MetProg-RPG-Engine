package metprog.model;

import java.io.Serializable;

public abstract class Equipo implements Serializable {
  private String nombre;
  private int modAtaque;
  private int modDefensa;

  public Equipo(String nombre, int modAtaque, int modDefensa) {
    setNombre(nombre);
    setModAtaque(modAtaque);
    setModDefensa(modDefensa);
  }

  public String getNombre() {
    return nombre;
  }
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }
  public int getModAtaque() {
    return modAtaque;
  }
  public void setModAtaque(int modAtaque) {
    if (modAtaque >= 0 && modAtaque <= 3) {
      this.modAtaque = modAtaque;
    } else {
      System.out.println("Error: modAtaque debe estar entre 0 y 3.");
      this.modAtaque = 0;
    }
  }
  public int getModDefensa() {
    return modDefensa;
  }
  public void setModDefensa(int modDefensa) {
    if (modDefensa >= 0 && modDefensa <= 3) {
      this.modDefensa = modDefensa;
    } else {
      System.out.println("Error: modDefensa debe estar entre 0 y 3.");
      this.modDefensa = 0;
    }
  }
}