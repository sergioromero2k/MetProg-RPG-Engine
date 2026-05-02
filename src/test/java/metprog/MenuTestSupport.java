package metprog;

import metprog.model.Arma;
import metprog.model.Armadura;
import metprog.model.Operador;
import metprog.model.Personaje;
import metprog.model.Usuario;
import metprog.service.GestorDesafios;
import metprog.service.GestorUsuarios;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

abstract class MenuTestSupport {

    protected String ejecutarConSalida(Runnable accion) {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream captura = new PrintStream(baos, true, StandardCharsets.UTF_8);
        System.setOut(captura);
        try {
            accion.run();
        } finally {
            captura.flush();
            System.setOut(originalOut);
        }
        return baos.toString(StandardCharsets.UTF_8);
    }

    protected Scanner crearScanner(String entrada) {
        return new Scanner(new ByteArrayInputStream(entrada.getBytes(StandardCharsets.UTF_8)));
    }

    protected void configurarPersonajeCompleto(Usuario usuario, Personaje personaje) {
        Arma arma = new Arma("Espada", 2, 0, false);
        Armadura armadura = new Armadura("Escudo", 0, 2);
        personaje.equiparArma(arma);
        personaje.agregarArmadura(armadura);
        personaje.setArmasActivas(List.of(arma));
        personaje.setArmaduraActiva(armadura);
        usuario.setPersonaje(personaje);
    }

    protected EscenarioDesafio prepararEscenarioDesafio() {
      GestorUsuarios gestorUsuarios = new GestorUsuarios();
      GestorDesafios gestorDesafios = new GestorDesafios();

      Usuario desafiante = gestorUsuarios.registrarUsuario("Alpha", "alpha1", "Password1");
      Usuario desafiado = gestorUsuarios.registrarUsuario("Beta", "beta1", "Password2");
      Operador operador = gestorUsuarios.registrarOperador("Admin", "admin1", "AdminPass1");

      configurarPersonajeCompleto(desafiante, new metprog.model.Vampiro("VampA", 5, 4, 500));
      configurarPersonajeCompleto(desafiado, new metprog.model.Cazador("CazB", 5, 3, 300));


      return new EscenarioDesafio(gestorUsuarios, gestorDesafios, desafiante, desafiado, operador);
    }

    protected static class EscenarioDesafio {
        protected final GestorUsuarios gestorUsuarios;
        protected final GestorDesafios gestorDesafios;
        protected final Usuario desafiante;
        protected final Usuario desafiado;
        protected final Operador operador;

        protected EscenarioDesafio(GestorUsuarios gestorUsuarios,
                                   GestorDesafios gestorDesafios,
                                   Usuario desafiante,
                                   Usuario desafiado,
                                   Operador operador) {
            this.gestorUsuarios = gestorUsuarios;
            this.gestorDesafios = gestorDesafios;
            this.desafiante = desafiante;
            this.desafiado = desafiado;
            this.operador = operador;
        }
    }
}