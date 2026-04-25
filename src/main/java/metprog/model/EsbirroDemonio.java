package metprog.model;

import java.util.ArrayList;

/**
 * Esbirro de tipo Demonio.
 * Tiene un Pacto con su amo (descripción textual).
 * Puede tener sub-esbirros de cualquier tipo (incluidos otros demonios),
 * formando una estructura recursiva.
 */
public class EsbirroDemonio extends Esbirro {
    private static final long serialVersionUID = 1L;

    /** Descripción del pacto entre el demonio y su amo */
    private String descripcionPacto;

    /** Sub-esbirros propios de este demonio (puede haber 0 o más) */
    private ArrayList<Esbirro> subEsbirros = new ArrayList<>();

    /**
     * @param nombre            nombre del demonio
     * @param salud             salud (1-3)
     * @param descripcionPacto  descripción del pacto
     */
    public EsbirroDemonio(String nombre, int salud, String descripcionPacto) {
        super(nombre, salud);
        this.descripcionPacto = descripcionPacto;
    }

    // ── Pacto ────────────────────────────────────────────────────────────────

    public String getDescripcionPacto() { return descripcionPacto; }
    public void setDescripcionPacto(String descripcionPacto) {
        this.descripcionPacto = descripcionPacto;
    }

    // ── Sub-esbirros ─────────────────────────────────────────────────────────

    public ArrayList<Esbirro> getSubEsbirros() { return subEsbirros; }

    public void añadirSubEsbirro(Esbirro esbirro) {
        subEsbirros.add(esbirro);
    }

    public void eliminarSubEsbirro(Esbirro esbirro) {
        subEsbirros.remove(esbirro);
    }

    /**
     * Calcula la salud total de todos los sub-esbirros de manera recursiva.
     */
    public int getSaludTotalSubesbirros() {
        int total = 0;
        for (Esbirro e : subEsbirros) {
            total += e.getSalud();
            if (e instanceof EsbirroDemonio) {
                total += ((EsbirroDemonio) e).getSaludTotalSubesbirros();
            }
        }
        return total;
    }

    @Override
    public String toString() {
        return "EsbirroDemonio " + getNombre()
                + " [Salud:" + getSalud()
                + " Pacto:'" + descripcionPacto + "'"
                + " SubEsbirros:" + subEsbirros.size() + "]";
    }
}
