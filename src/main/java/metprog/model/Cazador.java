package metprog.model;

public class Cazador extends Personaje {
    private static final long serialVersionUID = 1L;

    private int voluntad; // 0-3

    /**
     * @param nombre nombre del cazador
     * @param salud  salud inicial (0-5)
     * @param poder  poder (1-5)
     * @param oro    oro inicial (>= 0)
     */
    public Cazador(String nombre, int salud, int poder, int oro) {
        super(nombre, salud, poder, oro);
        this.voluntad = 3;
    }

    // ── Voluntad ─────────────────────────────────────────────────────────────

    public int getVoluntad() { return voluntad; }

    public void setVoluntad(int voluntad) {
        if (voluntad >= 0 && voluntad <= 3) {
            this.voluntad = voluntad;
        } else {
            System.out.println("Error: Voluntad debe estar entre 0 y 3.");
            this.voluntad = Math.max(0, Math.min(3, voluntad));
        }
    }

    /**
     * Decrementa la voluntad en 1 (al recibir daño). No baja de 0.
     */
    public void decrementarVoluntad() {
        setVoluntad(Math.max(0, voluntad - 1));
    }

    // ── Recibir daño sobrescrito: -voluntad ──────────────────────────────────

    @Override
    public void recibirDaño(int cantidad) {
        for (int i = 0; i < cantidad; i++) {
            if (getSalud() > 0) {
                super.recibirDaño(1);
                decrementarVoluntad();
            }
        }
    }

    // ── Talento activo ────────────────────────────────────────────────────────

    /**
     * Devuelve el Talento activo (cast seguro de getHabilidad()).
     * @return el Talento o null si no tiene habilidad asignada
     */
    public Talento getTalento() {
        HabilidadEspecial h = getHabilidad();
        return (h instanceof Talento) ? (Talento) h : null;
    }

    // ── Reinicio de combate ──────────────────────────────────────────────────

    @Override
    public void reiniciarParaCombate() {
        super.reiniciarParaCombate();
        this.voluntad = 3;
    }

    // ── Representación ──────────────────────────────────────────────────────

    @Override
    public String toString() {
        return "Cazador " + getNombre()
                + " [Salud:" + getSalud()
                + " Poder:" + getPoder()
                + " Voluntad:" + voluntad + "/3"
                + " Oro:" + getOro() + "]";
    }
}
