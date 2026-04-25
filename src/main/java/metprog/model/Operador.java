package metprog.model;

import java.io.Serializable;

/**
 * Operador del sistema (administrador).
 * Tiene nombre, nick y password (sin número de registro).
 * No gestiona personaje propio.
 */
public class Operador implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nombre;
    private String nick;
    private String password;

    /**
     * @param nombre   nombre del operador
     * @param nick     alias único en el sistema
     * @param password contraseña (8-12 caracteres)
     */
    public Operador(String nombre, String nick, String password) {
        setNombre(nombre);
        setNick(nick);
        setPassword(password);
    }

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

    @Override
    public String toString() {
        return "Operador[" + nick + "]";
    }
}
