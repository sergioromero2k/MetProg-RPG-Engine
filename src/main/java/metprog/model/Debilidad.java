package metprog.model;

/**
 * Debilidad: modificador negativo de un Personaje.
 * El operador decide qué debilidades están "presentes" en un combate,
 * y su valor se resta al potencial de ataque y defensa.
 */
public class Debilidad extends Modificador {

    public Debilidad(String nombre, int valor) {
        super(nombre, valor);
    }
}
