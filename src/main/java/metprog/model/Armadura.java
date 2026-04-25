package metprog.model;

public class Armadura extends Equipo {

    /**
     * @param nombre     nombre de la armadura
     * @param modAtaque  modificador de ataque (0-3); puede ser 0
     * @param modDefensa modificador de defensa (0-3)
     */
    public Armadura(String nombre, int modAtaque, int modDefensa) {
        super(nombre, modAtaque, modDefensa);
    }

    @Override
    public String toString() {
        return nombre + " [DEF+" + modDefensa + (modAtaque > 0 ? " ATK+" + modAtaque : "") + "]";
    }
}
