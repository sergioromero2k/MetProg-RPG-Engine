package metprog.model;

import java.io.Serializable;

/**
 * Datos de una ronda del combate: útil para mostrar el log al jugador.
 */
public class RondaCombate implements Serializable {
    private static final long serialVersionUID = 1L;

    private final int numero;
    private final int potencialAtaque1;
    private final int exitosAtaque1;
    private final int potencialDefensa2;
    private final int exitosDefensa2;
    private final int potencialAtaque2;
    private final int exitosAtaque2;
    private final int potencialDefensa1;
    private final int exitosDefensa1;
    private final boolean daño1Recibido;   // personaje 1 recibió daño
    private final boolean daño2Recibido;   // personaje 2 recibió daño
    private final String descripcion;

    public RondaCombate(int numero,
                        int potencialAtaque1, int exitosAtaque1,
                        int potencialDefensa2, int exitosDefensa2,
                        int potencialAtaque2, int exitosAtaque2,
                        int potencialDefensa1, int exitosDefensa1,
                        boolean daño1Recibido, boolean daño2Recibido,
                        String descripcion) {
        this.numero           = numero;
        this.potencialAtaque1 = potencialAtaque1;
        this.exitosAtaque1    = exitosAtaque1;
        this.potencialDefensa2= potencialDefensa2;
        this.exitosDefensa2   = exitosDefensa2;
        this.potencialAtaque2 = potencialAtaque2;
        this.exitosAtaque2    = exitosAtaque2;
        this.potencialDefensa1= potencialDefensa1;
        this.exitosDefensa1   = exitosDefensa1;
        this.daño1Recibido    = daño1Recibido;
        this.daño2Recibido    = daño2Recibido;
        this.descripcion      = descripcion;
    }

    public int     getNumero()            { return numero; }
    public boolean isDaño1Recibido()      { return daño1Recibido; }
    public boolean isDaño2Recibido()      { return daño2Recibido; }
    public String  getDescripcion()       { return descripcion; }
    public int     getPotencialAtaque1()  { return potencialAtaque1; }
    public int     getExitosAtaque1()     { return exitosAtaque1; }
    public int     getPotencialAtaque2()  { return potencialAtaque2; }
    public int     getExitosAtaque2()     { return exitosAtaque2; }

    @Override
    public String toString() {
        return "Ronda " + numero + ": " + descripcion;
    }
}
