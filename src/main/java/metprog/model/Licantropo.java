package metprog.model;

/**
 * Licántropo: usa Dones como habilidades especiales.
 * Gestiona un valor de Rabia (0-3) que sube al recibir daño y empieza en 0 cada combate.
 * También representa la forma bestia con incremento de estatura y peso.
 *
 * Reglas de combate:
 *  - Rabia empieza en 0 al inicio del combate.
 *  - +1 rabia cada vez que pierde 1 punto de salud.
 *  - La rabia se suma al potencial de ataque y defensa.
 *  - Si rabia < rabiaMinima del Don, no usa su valor de ataque/defensa en esa ronda.
 */
public class Licantropo extends Personaje {
    private static final long serialVersionUID = 1L;

    private int rabia; // 0-3

    // Incrementos en forma bestia (generados aleatoriamente en el rango indicado)
    private double incrementoAltura; // 0.5 - 1.0 metros
    private double incrementoPeso;   // 90 - 110 kilos

    /**
     * @param nombre nombre del licántropo
     * @param salud  salud inicial (0-5)
     * @param poder  poder (1-5)
     * @param oro    oro inicial (>= 0)
     */
    public Licantropo(String nombre, int salud, int poder, int oro) {
        super(nombre, salud, poder, oro);
        this.rabia = 0;
        // Valores por defecto del rango de la bestia
        this.incrementoAltura = 0.5 + Math.random() * 0.5;          // [0.5, 1.0]
        this.incrementoPeso   = 90  + Math.random() * 20;            // [90, 110]
    }

    // ── Rabia ─────────────────────────────────────────────────────────────────

    public int getRabia() { return rabia; }

    public void setRabia(int rabia) {
        if (rabia >= 0 && rabia <= 3) {
            this.rabia = rabia;
        } else {
            System.out.println("Error: Rabia debe estar entre 0 y 3.");
            this.rabia = Math.max(0, Math.min(3, rabia));
        }
    }

    /**
     * Incrementa la rabia en 1 (al recibir daño). No supera 3.
     */
    public void incrementarRabia() {
        setRabia(Math.min(3, rabia + 1));
    }

    // ── Recibir daño sobrescrito: +rabia ─────────────────────────────────────

    @Override
    public void recibirDaño(int cantidad) {
        for (int i = 0; i < cantidad; i++) {
            if (getSalud() > 0) {
                super.recibirDaño(1);
                incrementarRabia();
            }
        }
    }

    // ── Forma bestia ──────────────────────────────────────────────────────────

    public double getIncrementoAltura() { return incrementoAltura; }
    public void setIncrementoAltura(double incrementoAltura) {
        if (incrementoAltura >= 0.5 && incrementoAltura <= 1.0) {
            this.incrementoAltura = incrementoAltura;
        }
    }

    public double getIncrementoPeso() { return incrementoPeso; }
    public void setIncrementoPeso(double incrementoPeso) {
        if (incrementoPeso >= 90 && incrementoPeso <= 110) {
            this.incrementoPeso = incrementoPeso;
        }
    }

    // ── Don activo ────────────────────────────────────────────────────────────

    /**
     * Devuelve el Don activo (cast seguro de getHabilidad()).
     * @return el Don o null si no tiene habilidad asignada
     */
    public Don getDon() {
        HabilidadEspecial h = getHabilidad();
        return (h instanceof Don) ? (Don) h : null;
    }

    /**
     * Indica si puede usar su Don activo con la rabia actual.
     */
    public boolean puedeUsarDon() {
        Don d = getDon();
        return d != null && d.puedeUsarse(rabia);
    }

    // ── Reinicio de combate ──────────────────────────────────────────────────

    @Override
    public void reiniciarParaCombate() {
        super.reiniciarParaCombate();
        this.rabia = 0;
    }

    // ── Representación ──────────────────────────────────────────────────────

    @Override
    public String toString() {
        return "Licántropo " + getNombre()
                + " [Salud:" + getSalud()
                + " Poder:" + getPoder()
                + " Rabia:" + rabia + "/3"
                + " Oro:" + getOro() + "]";
    }
}
