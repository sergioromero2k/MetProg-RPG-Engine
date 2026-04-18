package metprog.model;

import java.io.Serializable;

public abstract class HabilidadEspecial  implements Serializable {
  private String nombre;
  private int valorAtaque;
  private int valorDefensa;

  public HabilidadEspecial (String nombre, int valorAtaque, int valorDefensa) {
    setNombre(nombre);
    setValorAtaque(valorAtaque);
    setValorDefensa(valorDefensa);
  }

  public String getNombre() {
    return nombre;
  }
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }
  public int getValorAtaque() {
    return valorAtaque;
  }
  public void setValorAtaque(int valorAtaque) {
    if (valorAtaque >= 1 && valorAtaque <= 3) {
      this.valorAtaque = valorAtaque;
    } else {
      System.out.println("Error: Valor de ataque debe estar entre 1 y 3.");
      this.valorAtaque = 1;
    }
  }

  public int getValorDefensa() {
    return valorDefensa;
  }
  public void setValorDefensa(int valorDefensa) {
    if (valorDefensa >= 1 && valorDefensa <= 3) {
      this.valorDefensa = valorDefensa;
    } else {
      System.out.println("Error: Valor de defensa debe estar entre 1 y 3.");
      this.valorDefensa = 1;
    }
  }
}

