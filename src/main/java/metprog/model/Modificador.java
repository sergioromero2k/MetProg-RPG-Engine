package metprog.model;

import java.io.Serializable;

/**
 * Clase abstracta base para Fortaleza y Debilidad.
 * Ambas tienen nombre y un valor entre 1 y 5.
 */
public abstract class Modificador implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nombre;
    private int valor;

    public Modificador(String nombre, int valor) {
        setNombre(nombre);
        setValor(valor);
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getValor() { return valor; }
    public void setValor(int valor) {
        if (valor >= 1 && valor <= 5) {
            this.valor = valor;
        } else {
            System.out.println("Error: Valor debe estar entre 1 y 5.");
            this.valor = 1;
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " " + nombre + " (valor:" + valor + ")";
    }
}
