package metprog.model;

public class Vampiro extends Personaje{
  private int puntosSangre;
  private int edad;

  public Vampiro(String nombre, int salud, int poder, int oro) {
    super(nombre, salud, poder, oro);
    this.puntosSangre = 5;
    this.edad = 0;
  }
  public int getPuntosSangre() { return puntosSangre; }
  public void setPuntosSangre(int puntosSangre) {
    if (puntosSangre >= 0 && puntosSangre <= 10) {
      this.puntosSangre = puntosSangre;
    } else {
      System.out.println("Error: PuntosSangre debe estar entre 0 y 10.");
      this.puntosSangre = 0;
    }
  }
  public int getEdad() {return edad;}
  public void setEdad(int edad) { this.edad = edad; }
}