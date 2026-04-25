package metprog.model;

public class Arma extends Equipo {

    /** true = arma de 2 manos; false = arma de 1 mano */
    private boolean dosManos;

    /**
     * @param nombre    nombre del arma
     * @param modAtaque modificador de ataque (0-3)
     * @param modDefensa modificador de defensa (0-3); las armas pueden tener 0
     * @param dosManos  true si requiere las dos manos
     */
    public Arma(String nombre, int modAtaque, int modDefensa, boolean dosManos) {
        super(nombre, modAtaque, modDefensa);
        this.dosManos = dosManos;
    }

    public boolean isDosManos() {
        return dosManos;
    }

    public void setDosManos(boolean dosManos) {
        this.dosManos = dosManos;
    }

    @Override
    public String toString() {
        return nombre + " [ATK+" + modAtaque + " DEF+" + modDefensa + (dosManos ? " 2M" : " 1M") + "]";
    }
}