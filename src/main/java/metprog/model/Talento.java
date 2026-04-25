package metprog.model;

/**
 * Talento: habilidad especial de los Cazadores.
 * No añade ninguna restricción especial más allá de las de HabilidadEspecial.
 */
public class Talento extends HabilidadEspecial {

    /**
     * @param nombre       nombre del talento
     * @param valorAtaque  valor de ataque (1-3)
     * @param valorDefensa valor de defensa (1-3)
     */
    public Talento(String nombre, int valorAtaque, int valorDefensa) {
        super(nombre, valorAtaque, valorDefensa);
    }

    @Override
    public String toString() {
        return "Talento " + getNombre()
                + " [ATK:" + getValorAtaque()
                + " DEF:" + getValorDefensa() + "]";
    }
}
