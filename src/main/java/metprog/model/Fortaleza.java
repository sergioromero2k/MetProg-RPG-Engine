package metprog.model;

/**
 * Fortaleza: modificador positivo de un Personaje.
 * El operador decide qué fortalezas están "presentes" en un combate,
 * y su valor se suma al potencial de ataque y defensa.
 */
public class Fortaleza extends Modificador {

    public Fortaleza(String nombre, int valor) {
        super(nombre, valor);
    }
}
