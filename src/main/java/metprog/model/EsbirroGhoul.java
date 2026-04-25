package metprog.model;

/**
 * Esbirro de tipo Ghoul.
 * Tiene un valor de dependencia con su amo (1-5).
 */
public class EsbirroGhoul extends Esbirro {
    private static final long serialVersionUID = 1L;

    private int dependencia; // 1-5

    /**
     * @param nombre      nombre del esbirro
     * @param salud       salud (1-3)
     * @param dependencia nivel de dependencia con el amo (1-5)
     */
    public EsbirroGhoul(String nombre, int salud, int dependencia) {
        super(nombre, salud);
        setDependencia(dependencia);
    }

    public int getDependencia() { return dependencia; }

    public void setDependencia(int dependencia) {
        if (dependencia >= 1 && dependencia <= 5) {
            this.dependencia = dependencia;
        } else {
            System.out.println("Error: Dependencia debe estar entre 1 y 5.");
            this.dependencia = Math.max(1, Math.min(5, dependencia));
        }
    }

    @Override
    public String toString() {
        return "EsbirroGhoul " + getNombre()
                + " [Salud:" + getSalud() + " Dependencia:" + dependencia + "]";
    }
}
