package metprog.model;

/**
 * Esbirro de tipo humano.
 * Tiene un nivel de lealtad (ALTA / NORMAL / BAJA).
 * Los vampiros NO pueden tener esbirros humanos.
 */
public class EsbirroHumano extends Esbirro {
    private static final long serialVersionUID = 1L;

    private Lealtad lealtad;

    /**
     * @param nombre  nombre del esbirro
     * @param salud   salud (1-3)
     * @param lealtad nivel de lealtad
     */
    public EsbirroHumano(String nombre, int salud, Lealtad lealtad) {
        super(nombre, salud);
        this.lealtad = lealtad;
    }

    public Lealtad getLealtad() { return lealtad; }
    public void setLealtad(Lealtad lealtad) { this.lealtad = lealtad; }

    @Override
    public String toString() {
        return "EsbirroHumano " + getNombre()
                + " [Salud:" + getSalud() + " Lealtad:" + lealtad + "]";
    }
}
