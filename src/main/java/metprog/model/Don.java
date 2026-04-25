package metprog.model;

/**
 * Don: habilidad especial exclusiva de los Licántropos.
 * Requiere que el licántropo tenga al menos un valor mínimo de rabia para poder usarse.
 */
public class Don extends HabilidadEspecial {

    /** Valor mínimo de rabia necesario para activar este don (0-3) */
    private int rabiaMinima;

    /**
     * @param nombre       nombre del don
     * @param valorAtaque  valor de ataque (1-3)
     * @param valorDefensa valor de defensa (1-3)
     * @param rabiaMinima  rabia mínima requerida (0-3)
     */
    public Don(String nombre, int valorAtaque, int valorDefensa, int rabiaMinima) {
        super(nombre, valorAtaque, valorDefensa);
        setRabiaMinima(rabiaMinima);
    }

    public int getRabiaMinima() {
        return rabiaMinima;
    }

    public void setRabiaMinima(int rabiaMinima) {
        if (rabiaMinima >= 0 && rabiaMinima <= 3) {
            this.rabiaMinima = rabiaMinima;
        } else {
            System.out.println("Error: rabiaMinima debe estar entre 0 y 3.");
            this.rabiaMinima = 0;
        }
    }

    /**
     * Indica si el licántropo puede usar este don con su rabia actual.
     * @param rabiaActual rabia actual del licántropo
     */
    public boolean puedeUsarse(int rabiaActual) {
        return rabiaActual >= rabiaMinima;
    }

    @Override
    public String toString() {
        return "Don " + getNombre()
                + " [ATK:" + getValorAtaque()
                + " DEF:" + getValorDefensa()
                + " RabiaMin:" + rabiaMinima + "]";
    }
}
