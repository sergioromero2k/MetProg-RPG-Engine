package metprog.model;

/**
 * Vampiro: usa Disciplinas como habilidades especiales.
 * Gestiona puntos de sangre (0-10) y tiene una edad.
 *
 * Reglas de combate:
 *  - Potencial de ataque extra: +2 si puntosSangre >= 5.
 *  - Paga el costeSangre de su Disciplina activa antes de atacar.
 *  - Si no puede pagar, no usa el valor de ataque de la disciplina.
 *  - Si el ataque tiene éxito, recupera 4 puntos de sangre.
 *  - Los vampiros NO pueden tener esbirros humanos.
 */
public class Vampiro extends Personaje {
    private static final long serialVersionUID = 1L;

    private int puntosSangre; // 0-10
    private int edad;

    /**
     * @param nombre nombre del vampiro
     * @param salud  salud inicial (0-5)
     * @param poder  poder (1-5)
     * @param oro    oro inicial (>= 0)
     */
    public Vampiro(String nombre, int salud, int poder, int oro) {
        super(nombre, salud, poder, oro);
        this.puntosSangre = 5;
        this.edad = 0;
    }

    // ── Puntos de sangre ─────────────────────────────────────────────────────

    public int getPuntosSangre() { return puntosSangre; }

    public void setPuntosSangre(int puntosSangre) {
        if (puntosSangre >= 0 && puntosSangre <= 10) {
            this.puntosSangre = puntosSangre;
        } else {
            System.out.println("Error: PuntosSangre debe estar entre 0 y 10.");
            this.puntosSangre = Math.max(0, Math.min(10, puntosSangre));
        }
    }

    /**
     * Intenta gastar {@code coste} puntos de sangre.
     * @return true si pudo gastarlos; false si no tenía suficientes
     */
    public boolean gastarSangre(int coste) {
        if (puntosSangre >= coste) {
            puntosSangre -= coste;
            return true;
        }
        return false;
    }

    /**
     * Recupera puntos de sangre al atacar con éxito.
     */
    public void recuperarSangre(int cantidad) {
        setPuntosSangre(Math.min(10, puntosSangre + cantidad));
    }

    // ── Edad ─────────────────────────────────────────────────────────────────

    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }

    // ── Regla: sin esbirros humanos ──────────────────────────────────────────

    /**
     * Los vampiros no pueden tener esbirros humanos.
     * Lanza UnsupportedOperationException si se intenta.
     */
    @Override
    public void añadirEsbirro(Esbirro esbirro) {
        if (esbirro instanceof EsbirroHumano) {
            throw new UnsupportedOperationException(
                "Los vampiros no pueden tener esbirros humanos.");
        }
        super.añadirEsbirro(esbirro);
    }

    // ── Disciplina activa ────────────────────────────────────────────────────

    /**
     * Devuelve la disciplina activa (cast seguro de getHabilidad()).
     * @return la Disciplina o null si no tiene habilidad asignada
     */
    public Disciplina getDisciplina() {
        HabilidadEspecial h = getHabilidad();
        return (h instanceof Disciplina) ? (Disciplina) h : null;
    }

    // ── Reinicio de combate ──────────────────────────────────────────────────

    @Override
    public void reiniciarParaCombate() {
        super.reiniciarParaCombate();
        this.puntosSangre = 5; // valor por defecto al inicio del combate
    }

    // ── Representación ──────────────────────────────────────────────────────

    @Override
    public String toString() {
        return "Vampiro " + getNombre()
                + " [Salud:" + getSalud()
                + " Poder:" + getPoder()
                + " Sangre:" + puntosSangre + "/10"
                + " Oro:" + getOro()
                + " Edad:" + edad + "]";
    }
}
