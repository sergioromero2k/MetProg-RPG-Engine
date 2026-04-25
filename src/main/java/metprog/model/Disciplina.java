package metprog.model;

/**
 * Disciplina: habilidad especial exclusiva de los Vampiros.
 * Tiene un coste en puntos de sangre (1-3) que se descuenta cada vez que se usa.
 */
public class Disciplina extends HabilidadEspecial {

    /** Puntos de sangre que cuesta activar esta disciplina (1-3) */
    private int costeSangre;

    /**
     * @param nombre      nombre de la disciplina
     * @param valorAtaque valor de ataque (1-3)
     * @param valorDefensa valor de defensa (1-3)
     * @param costeSangre coste en puntos de sangre (1-3)
     */
    public Disciplina(String nombre, int valorAtaque, int valorDefensa, int costeSangre) {
        super(nombre, valorAtaque, valorDefensa);
        setCosteSangre(costeSangre);
    }

    public int getCosteSangre() {
        return costeSangre;
    }

    public void setCosteSangre(int costeSangre) {
        if (costeSangre >= 1 && costeSangre <= 3) {
            this.costeSangre = costeSangre;
        } else {
            System.out.println("Error: costeSangre debe estar entre 1 y 3.");
            this.costeSangre = 1;
        }
    }

    @Override
    public String toString() {
        return "Disciplina " + getNombre()
                + " [ATK:" + getValorAtaque()
                + " DEF:" + getValorDefensa()
                + " Coste:" + costeSangre + " sangre]";
    }
}
