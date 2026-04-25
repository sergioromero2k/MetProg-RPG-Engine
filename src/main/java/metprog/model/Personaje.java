package metprog.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase abstracta base para todos los personajes del juego.
 * Gestiona el inventario de armas/armaduras, esbirros,
 * modificadores y las reglas de equipamiento activo.
 */
public abstract class Personaje implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nombre;
    private int salud;         // 0-5
    private int poder;         // 1-5
    private int oro;           // >= 0

    private HabilidadEspecial habilidad;

    private ArrayList<Arma>     armas         = new ArrayList<>();
    private ArrayList<Arma>     armasActivas  = new ArrayList<>();
    private ArrayList<Armadura> armaduras     = new ArrayList<>();
    private Armadura            armaduraActiva;

    private ArrayList<Esbirro>   esbirros    = new ArrayList<>();
    private ArrayList<Fortaleza> fortalezas  = new ArrayList<>();
    private ArrayList<Debilidad> debilidades = new ArrayList<>();

    // Fortalezas/debilidades que el operador declaró "presentes" para el combate actual
    private ArrayList<Fortaleza> fortalezasPresentes  = new ArrayList<>();
    private ArrayList<Debilidad> debilidadesPresentes = new ArrayList<>();

    // ── Salud al inicio del combate (para restaurarla) ──────────────────────
    private int saludInicial;

    public Personaje(String nombre, int salud, int poder, int oro) {
        setNombre(nombre);
        setSalud(salud);
        setPoder(poder);
        setOro(oro);
        this.saludInicial = salud;
    }

    // ── Getters / Setters básicos ────────────────────────────────────────────

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getSalud() { return salud; }
    public void setSalud(int salud) {
        if (salud >= 0 && salud <= 5) {
            this.salud = salud;
        } else {
            System.out.println("Error: Salud debe estar entre 0 y 5.");
            this.salud = Math.max(0, Math.min(5, salud));
        }
    }

    public int getPoder() { return poder; }
    public void setPoder(int poder) {
        if (poder >= 1 && poder <= 5) {
            this.poder = poder;
        } else {
            System.out.println("Error: Poder debe estar entre 1 y 5.");
            this.poder = Math.max(1, Math.min(5, poder));
        }
    }

    public int getOro() { return oro; }
    public void setOro(int oro) {
        if (oro >= 0) {
            this.oro = oro;
        } else {
            System.out.println("Error: Oro no puede ser negativo.");
            this.oro = 0;
        }
    }

    public HabilidadEspecial getHabilidad() { return habilidad; }
    public void setHabilidad(HabilidadEspecial habilidad) { this.habilidad = habilidad; }

    // ── Gestión de armas ─────────────────────────────────────────────────────

    public ArrayList<Arma> getArmas() { return armas; }
    public ArrayList<Arma> getArmasActivas() { return armasActivas; }

    /**
     * Añade un arma al inventario del personaje.
     */
    public void equiparArma(Arma arma) {
        armas.add(arma);
    }

    /**
     * Establece las armas activas respetando las reglas:
     * - Un arma de 2 manos: solo esa puede estar activa.
     * - Armas de 1 mano: máximo 2 simultáneamente.
     *
     * @param seleccion lista de armas a activar (deben pertenecer al inventario)
     * @return true si la selección es válida y se ha aplicado
     */
    public boolean setArmasActivas(List<Arma> seleccion) {
        if (seleccion == null || seleccion.isEmpty()) {
            System.out.println("Error: debe seleccionar al menos un arma.");
            return false;
        }
        // Todas deben estar en el inventario
        for (Arma a : seleccion) {
            if (!armas.contains(a)) {
                System.out.println("Error: el arma '" + a.getNombre() + "' no está en el inventario.");
                return false;
            }
        }
        if (seleccion.size() == 1) {
            armasActivas = new ArrayList<>(seleccion);
            return true;
        }
        // Más de 1: ninguna puede ser de 2 manos y máximo 2
        if (seleccion.size() > 2) {
            System.out.println("Error: máximo 2 armas de 1 mano activas.");
            return false;
        }
        for (Arma a : seleccion) {
            if (a.isDosManos()) {
                System.out.println("Error: un arma de 2 manos solo puede estar activa sola.");
                return false;
            }
        }
        armasActivas = new ArrayList<>(seleccion);
        return true;
    }

    // ── Gestión de armaduras ─────────────────────────────────────────────────

    public ArrayList<Armadura> getArmaduras() { return armaduras; }
    public Armadura getArmaduraActiva() { return armaduraActiva; }

    public void añadirArmadura(Armadura armadura) {
        armaduras.add(armadura);
    }

    /**
     * Establece la armadura activa (debe pertenecer al inventario).
     */
    public boolean setArmaduraActiva(Armadura armadura) {
        if (!armaduras.contains(armadura)) {
            System.out.println("Error: la armadura '" + armadura.getNombre() + "' no está en el inventario.");
            return false;
        }
        this.armaduraActiva = armadura;
        return true;
    }

    // ── Equipo activo completo ────────────────────────────────────────────────

    /**
     * Devuelve true si el personaje tiene al menos un arma activa y una armadura activa.
     */
    public boolean tieneEquipoActivo() {
        return !armasActivas.isEmpty() && armaduraActiva != null;
    }

    // ── Esbirros ─────────────────────────────────────────────────────────────

    public ArrayList<Esbirro> getEsbirros() { return esbirros; }

    public void añadirEsbirro(Esbirro esbirro) {
        esbirros.add(esbirro);
    }

    public void eliminarEsbirro(Esbirro esbirro) {
        esbirros.remove(esbirro);
    }

    /**
     * Calcula la salud total de todos los esbirros (incluidos esbirros de esbirros demonios).
     */
    public int getSaludTotalEsbirros() {
        int total = 0;
        for (Esbirro e : esbirros) {
            total += e.getSalud();
            if (e instanceof EsbirroDemonio) {
                total += ((EsbirroDemonio) e).getSaludTotalSubesbirros();
            }
        }
        return total;
    }

    // ── Modificadores ────────────────────────────────────────────────────────

    public ArrayList<Fortaleza> getFortalezas() { return fortalezas; }
    public ArrayList<Debilidad> getDebilidades() { return debilidades; }

    public void añadirFortaleza(Fortaleza fortaleza) { fortalezas.add(fortaleza); }
    public void eliminarFortaleza(Fortaleza fortaleza) { fortalezas.remove(fortaleza); }
    public void añadirDebilidad(Debilidad debilidad) { debilidades.add(debilidad); }
    public void eliminarDebilidad(Debilidad debilidad) { debilidades.remove(debilidad); }

    // ── Modificadores presentes (fijados por el operador) ────────────────────

    public ArrayList<Fortaleza> getFortalezasPresentes() { return fortalezasPresentes; }
    public ArrayList<Debilidad> getDebilidadesPresentes() { return debilidadesPresentes; }

    public void setFortalezasPresentes(ArrayList<Fortaleza> fp) { this.fortalezasPresentes = fp; }
    public void setDebilidadesPresentes(ArrayList<Debilidad> dp) { this.debilidadesPresentes = dp; }

    /**
     * Calcula la suma neta de modificadores presentes (fortalezas - debilidades).
     */
    public int getModificadorNeto() {
        int suma = 0;
        for (Fortaleza f : fortalezasPresentes) suma += f.getValor();
        for (Debilidad d : debilidadesPresentes) suma -= d.getValor();
        return suma;
    }

    // ── Estado de salud ──────────────────────────────────────────────────────

    public boolean estaVivo() { return salud > 0; }

    public void recibirDaño(int cantidad) {
        setSalud(Math.max(0, salud - cantidad));
    }

    // ── Reinicio de combate ──────────────────────────────────────────────────

    /**
     * Restaura el estado del personaje al inicio de cada combate.
     * Las subclases deben sobrescribir esto para reiniciar sus atributos propios.
     */
    public void reiniciarParaCombate() {
        setSalud(saludInicial);
        fortalezasPresentes.clear();
        debilidadesPresentes.clear();
    }

    // ── Representación ──────────────────────────────────────────────────────

    @Override
    public String toString() {
        return getClass().getSimpleName() + " " + nombre
                + " [Salud:" + salud + "/5 Poder:" + poder + " Oro:" + oro + "]";
    }
}
