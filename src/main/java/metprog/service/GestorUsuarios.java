package metprog.service;

import metprog.model.Operador;
import metprog.model.Personaje;
import metprog.model.Usuario;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Gestiona el ciclo de vida de los usuarios y operadores del sistema.
 *
 * <p>Cubre registro, baja, autenticación, bloqueo/desbloqueo,
 * asignación de personaje y consulta del ranking global.
 */
public class GestorUsuarios {

    private final List<Usuario>  usuarios   = new ArrayList<>();
    private final List<Operador> operadores = new ArrayList<>();

    // ── Registro ─────────────────────────────────────────────────────────────

    /**
     * Registra un nuevo jugador en el sistema.
     *
     * @param nombre   nombre real del usuario.
     * @param nick     alias único (no puede coincidir con ningún usuario u operador).
     * @param password contraseña (8-12 caracteres).
     * @return el usuario creado, o {@code null} si los datos no son válidos.
     */
    public Usuario registrarUsuario(String nombre, String nick, String password) {
        if (!passwordValida(password)) {
            return null;
        }
        if (nickEnUso(nick)) {
            return null;
        }
        try {
            Usuario u = new Usuario(nombre, nick, password);
            // Garantiza unicidad del número de registro
            while (numeroRegistroEnUso(u.getNumeroRegistro())) {
                u = new Usuario(nombre, nick, password);
            }
            usuarios.add(u);
            return u;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    /**
     * Registra un nuevo operador en el sistema.
     *
     * @param nombre   nombre del operador.
     * @param nick     alias único.
     * @param password contraseña (8-12 caracteres).
     * @return el operador creado, o {@code null} si los datos no son válidos.
     */
    public Operador registrarOperador(String nombre, String nick, String password) {
        if (!passwordValida(password)) {
            return null;
        }
        if (nickEnUso(nick)) {
            return null;
        }
        try {
            Operador op = new Operador(nombre, nick, password);
            operadores.add(op);
            return op;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    // ── Baja ─────────────────────────────────────────────────────────────────

    /**
     * Da de baja a un usuario verificando la contraseña.
     *
     * @param nick     nick del usuario a eliminar.
     * @param password contraseña del usuario.
     * @return {@code true} si la baja se realizó correctamente.
     */
    public boolean darDeBajaUsuario(String nick, String password) {
        Usuario u = buscarUsuarioPorNick(nick);
        if (u == null || !u.getPassword().equals(password)) {
            return false;
        }
        usuarios.remove(u);
        return true;
    }

    /**
     * Da de baja a un operador verificando la contraseña.
     *
     * @param nick     nick del operador.
     * @param password contraseña del operador.
     * @return {@code true} si la baja se realizó correctamente.
     */
    public boolean darDeBajaOperador(String nick, String password) {
        Operador op = buscarOperadorPorNick(nick);
        if (op == null || !op.getPassword().equals(password)) {
            return false;
        }
        operadores.remove(op);
        return true;
    }

    // ── Login ─────────────────────────────────────────────────────────────────

    /**
     * Autentica a un jugador en el sistema.
     *
     * @param nick     nick del usuario.
     * @param password contraseña proporcionada.
     * @return el usuario autenticado, o {@code null} si las credenciales son
     *         incorrectas o el usuario está bloqueado.
     */
    public Usuario loginUsuario(String nick, String password) {
        Usuario u = buscarUsuarioPorNick(nick);
        if (u == null || u.isBloqueado() || !u.getPassword().equals(password)) {
            return null;
        }
        return u;
    }

    /**
     * Autentica a un operador en el sistema.
     *
     * @param nick     nick del operador.
     * @param password contraseña proporcionada.
     * @return el operador autenticado, o {@code null} si las credenciales son incorrectas.
     */
    public Operador loginOperador(String nick, String password) {
        Operador op = buscarOperadorPorNick(nick);
        if (op == null || !op.getPassword().equals(password)) {
            return null;
        }
        return op;
    }

    // ── Bloqueo ───────────────────────────────────────────────────────────────

    /**
     * Bloquea a un usuario impidiéndole autenticarse.
     *
     * @param nick nick del usuario a bloquear.
     */
    public void bloquearUsuario(String nick) {
        Usuario u = buscarUsuarioPorNick(nick);
        if (u != null) {
            u.setBloqueado(true);
        }
    }

    /**
     * Desbloquea a un usuario permitiéndole volver a autenticarse.
     *
     * @param nick nick del usuario a desbloquear.
     */
    public void desbloquearUsuario(String nick) {
        Usuario u = buscarUsuarioPorNick(nick);
        if (u != null) {
            u.setBloqueado(false);
        }
    }

    // ── Personaje ─────────────────────────────────────────────────────────────

    /**
     * Asigna un personaje al usuario identificado por su nick.
     *
     * @param nick      nick del usuario.
     * @param personaje personaje a asignar.
     * @return {@code true} si el usuario existe y se realizó la asignación.
     */
    public boolean registrarPersonaje(String nick, Personaje personaje) {
        Usuario u = buscarUsuarioPorNick(nick);
        if (u == null) {
            return false;
        }
        u.setPersonaje(personaje);
        return true;
    }

    /**
     * Da de baja el personaje de un usuario.
     *
     * @param nick nick del usuario.
     * @return {@code true} si el usuario existe y tenía personaje.
     */
    public boolean darDeBajaPersonaje(String nick) {
        Usuario u = buscarUsuarioPorNick(nick);
        if (u == null || u.getPersonaje() == null) {
            return false;
        }
        u.setPersonaje(null);
        return true;
    }

    // ── Ranking ───────────────────────────────────────────────────────────────

    /**
     * Devuelve el ranking global de jugadores ordenado por oro descendente.
     * Solo incluye usuarios que tienen personaje registrado.
     *
     * @return lista ordenada de usuarios.
     */
    public List<Usuario> getRankingGlobal() {
        List<Usuario> conPersonaje = new ArrayList<>();
        for (Usuario u : usuarios) {
            if (u.getPersonaje() != null) {
                conPersonaje.add(u);
            }
        }
        conPersonaje.sort(Comparator.comparingInt(
                (Usuario u) -> u.getPersonaje().getOro()).reversed());
        return conPersonaje;
    }

    // ── Consultas ─────────────────────────────────────────────────────────────

    /**
     * Busca un usuario por su nick.
     *
     * @param nick nick a buscar.
     * @return el usuario encontrado, o {@code null} si no existe.
     */
    public Usuario buscarUsuarioPorNick(String nick) {
        for (Usuario u : usuarios) {
            if (u.getNick().equals(nick)) {
                return u;
            }
        }
        return null;
    }

    /**
     * Busca un operador por su nick.
     *
     * @param nick nick a buscar.
     * @return el operador encontrado, o {@code null} si no existe.
     */
    public Operador buscarOperadorPorNick(String nick) {
        for (Operador op : operadores) {
            if (op.getNick().equals(nick)) {
                return op;
            }
        }
        return null;
    }

    /**
     * Devuelve una copia de la lista de todos los usuarios registrados.
     *
     * @return lista de usuarios.
     */
    public List<Usuario> getUsuarios() {
        return new ArrayList<>(usuarios);
    }

    /**
     * Devuelve una copia de la lista de todos los operadores registrados.
     *
     * @return lista de operadores.
     */
    public List<Operador> getOperadores() {
        return new ArrayList<>(operadores);
    }

    /**
     * Reemplaza la lista interna de usuarios (usado al cargar desde persistencia).
     *
     * @param lista nueva lista de usuarios.
     */
    public void setUsuarios(List<Usuario> lista) {
        usuarios.clear();
        usuarios.addAll(lista);
    }

    /**
     * Reemplaza la lista interna de operadores (usado al cargar desde persistencia).
     *
     * @param lista nueva lista de operadores.
     */
    public void setOperadores(List<Operador> lista) {
        operadores.clear();
        operadores.addAll(lista);
    }

    // ── Utilidades privadas ───────────────────────────────────────────────────

    private boolean passwordValida(String password) {
        return password != null
                && password.length() >= 8
                && password.length() <= 12;
    }

    private boolean nickEnUso(String nick) {
        return buscarUsuarioPorNick(nick) != null
                || buscarOperadorPorNick(nick) != null;
    }

    private boolean numeroRegistroEnUso(String numero) {
        for (Usuario u : usuarios) {
            if (u.getNumeroRegistro().equals(numero)) {
                return true;
            }
        }
        return false;
    }
}