package metprog.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Jugador registrado en el sistema.
 *
 * Número de registro: formato LNNLL (1 letra, 2 dígitos, 2 letras).
 * Se genera automáticamente y es único por usuario.
 * Password: entre 8 y 12 caracteres.
 */
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nombre;
    private String nick;
    private String password;
    private String numeroRegistro; // Formato LNNLL

    private Personaje personaje;
    private boolean bloqueado;
    private LocalDateTime ultimaDerrota; // para la restricción de 24h

    /** Historial de combates del usuario (pares oro ganado/perdido) */
    private List<RegistroOro> historialOro = new ArrayList<>();

    // ── Constructor ──────────────────────────────────────────────────────────

    /**
     * @param nombre   nombre real del usuario
     * @param nick     alias único en el sistema
     * @param password contraseña (8-12 caracteres)
     */
    public Usuario(String nombre, String nick, String password) {
        setNombre(nombre);
        setNick(nick);
        setPassword(password);
        this.numeroRegistro = generarNumeroRegistro();
        this.bloqueado = false;
    }

    // ── Getters / Setters ────────────────────────────────────────────────────

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getNick() { return nick; }
    public void setNick(String nick) { this.nick = nick; }

    public String getPassword() { return password; }
    public void setPassword(String password) {
        if (password != null && password.length() >= 8 && password.length() <= 12) {
            this.password = password;
        } else {
            throw new IllegalArgumentException("La contraseña debe tener entre 8 y 12 caracteres.");
        }
    }

    public String getNumeroRegistro() { return numeroRegistro; }

    public Personaje getPersonaje() { return personaje; }
    public void setPersonaje(Personaje personaje) { this.personaje = personaje; }

    public boolean isBloqueado() { return bloqueado; }
    public void setBloqueado(boolean bloqueado) { this.bloqueado = bloqueado; }

    public LocalDateTime getUltimaDerrota() { return ultimaDerrota; }
    public void setUltimaDerrota(LocalDateTime ultimaDerrota) { this.ultimaDerrota = ultimaDerrota; }

    // ── Historial de oro ─────────────────────────────────────────────────────

    public List<RegistroOro> getHistorialOro() { return historialOro; }

    public void registrarGananciaOro(int cantidad, String descripcion) {
        historialOro.add(new RegistroOro(cantidad, descripcion, LocalDateTime.now()));
    }

    public void registrarPerdidaOro(int cantidad, String descripcion) {
        historialOro.add(new RegistroOro(-cantidad, descripcion, LocalDateTime.now()));
    }

    // ── Validaciones de negocio ───────────────────────────────────────────────

    /**
     * @return true si el usuario tiene personaje con equipo activo y no está bloqueado
     */
    public boolean puedeDesafiar() {
        return !bloqueado && personaje != null && personaje.tieneEquipoActivo();
    }

    /**
     * Comprueba si este usuario ha perdido un combate en las últimas 24 horas.
     */
    public boolean haPerdidoEnUltimas24h() {
        if (ultimaDerrota == null) return false;
        return ultimaDerrota.isAfter(LocalDateTime.now().minusHours(24));
    }

    // ── Generación del número de registro ────────────────────────────────────

    /**
     * Genera un número de registro con formato LNNLL.
     * L = letra mayúscula aleatoria, N = dígito aleatorio 0-9.
     */
    public static String generarNumeroRegistro() {
        Random rnd = new Random();
        char l1 = (char) ('A' + rnd.nextInt(26));
        int  n1  = rnd.nextInt(10);
        int  n2  = rnd.nextInt(10);
        char l2 = (char) ('A' + rnd.nextInt(26));
        char l3 = (char) ('A' + rnd.nextInt(26));
        return "" + l1 + n1 + n2 + l2 + l3;
    }

    // ── Representación ──────────────────────────────────────────────────────

    @Override
    public String toString() {
        return "Usuario[" + nick + " #" + numeroRegistro
                + (bloqueado ? " BLOQUEADO" : "") + "]";
    }

    // ── Clase interna: RegistroOro ────────────────────────────────────────────

    /**
     * Representa una entrada en el historial de oro del usuario.
     */
    public static class RegistroOro implements Serializable {
        private static final long serialVersionUID = 1L;

        private final int           cantidad;    // positivo = ganancia, negativo = pérdida
        private final String        descripcion;
        private final LocalDateTime fecha;

        public RegistroOro(int cantidad, String descripcion, LocalDateTime fecha) {
            this.cantidad    = cantidad;
            this.descripcion = descripcion;
            this.fecha       = fecha;
        }

        public int           getCantidad()    { return cantidad; }
        public String        getDescripcion() { return descripcion; }
        public LocalDateTime getFecha()       { return fecha; }

        @Override
        public String toString() {
            return fecha + " | " + (cantidad >= 0 ? "+" : "") + cantidad + " oro | " + descripcion;
        }
    }
}
