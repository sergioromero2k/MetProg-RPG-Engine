package metprog.ui;

import metprog.model.Debilidad;
import metprog.model.Desafio;
import metprog.model.Fortaleza;
import metprog.model.Operador;
import metprog.model.Usuario;
import metprog.service.GestorDesafios;
import metprog.service.GestorUsuarios;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuOperador {
	private final GestorUsuarios gestorUsuarios;
	private final GestorDesafios gestorDesafios;
	private final Scanner scanner;
	private final Operador operador;

	public MenuOperador() {
		this(new GestorUsuarios(), new GestorDesafios(), new Scanner(System.in), null);
	}

	public MenuOperador(GestorUsuarios gestorUsuarios,
					   GestorDesafios gestorDesafios,
					   Scanner scanner,
					   Operador operador) {
		this.gestorUsuarios = gestorUsuarios;
		this.gestorDesafios = gestorDesafios;
		this.scanner = scanner;
		this.operador = operador;
	}

	public void mostrar() {
		boolean salir = false;

		while (!salir) {
			imprimirCabecera();
			int opcion = leerOpcion();

			switch (opcion) {
				case 1:
					System.out.println("Has seleccionado: Registrar operador");
					break;
				case 2:
					System.out.println("Has seleccionado: Registrar usuario");
					break;
				case 3:
					System.out.println("Has seleccionado: Dar de baja usuario");
					break;
				case 4:
					System.out.println("Has seleccionado: Dar de baja operador");
					break;
				case 5:
					System.out.println("Has seleccionado: Bloquear usuario");
					break;
				case 6:
					System.out.println("Has seleccionado: Desbloquear usuario");
					break;
				case 7:
					validarDesafio();
					break;
				case 8:
					verDesafiosPendientes();
					break;
				case 9:
					verDesafiosRegistrados();
					break;
				case 10:
					verHistorialCombates();
					break;
				case 11:
					System.out.println("Has seleccionado: Guardar datos");
					break;
				case 12:
					System.out.println("Has seleccionado: Cargar datos");
					break;
				case 0:
					System.out.println("Saliendo del menú de operador...");
					salir = true;
					break;
				default:
					System.out.println("Opción no válida.");
			}

			if (!salir) {
				System.out.println();
			}
		}
	}

	private void validarDesafio() {
		Desafio desafio = seleccionarDesafioPendiente();
		if (desafio == null) {
			System.out.println("No hay desafíos pendientes para validar.");
			return;
		}

		System.out.println("Introduce modificadores en formato nombre:valor separados por coma.");
		System.out.print("Fortalezas del desafiante: ");
		List<Fortaleza> fortDesafiante = leerFortalezas();
		System.out.print("Debilidades del desafiante: ");
		List<Debilidad> debDesafiante = leerDebilidades();
		System.out.print("Fortalezas del desafiado: ");
		List<Fortaleza> fortDesafiado = leerFortalezas();
		System.out.print("Debilidades del desafiado: ");
		List<Debilidad> debDesafiado = leerDebilidades();

		if (gestorDesafios.validarDesafio(desafio, fortDesafiante, debDesafiante, fortDesafiado, debDesafiado)) {
			System.out.println("Desafío validado y publicado.");
		} else {
			System.out.println("No se ha podido validar el desafío.");
		}
	}

	private void verDesafiosPendientes() {
		List<Desafio> desafios = gestorDesafios.getDesafiosPendientes();
		if (desafios.isEmpty()) {
			System.out.println("No hay desafíos pendientes.");
			return;
		}
		for (int i = 0; i < desafios.size(); i++) {
			System.out.println((i + 1) + ". " + desafios.get(i));
		}
	}

	private void verDesafiosRegistrados() {
		List<Desafio> desafios = gestorDesafios.getDesafios();
		if (desafios.isEmpty()) {
			System.out.println("No hay desafíos registrados.");
			return;
		}
		for (int i = 0; i < desafios.size(); i++) {
			System.out.println((i + 1) + ". " + desafios.get(i));
		}
	}

	private void verHistorialCombates() {
		List<metprog.model.Combate> combates = gestorDesafios.getHistorialCombates();
		if (combates.isEmpty()) {
			System.out.println("No hay combates registrados.");
			return;
		}
		for (int i = 0; i < combates.size(); i++) {
			System.out.println((i + 1) + ". " + combates.get(i));
		}
	}

	private Desafio seleccionarDesafioPendiente() {
		List<Desafio> desafios = gestorDesafios.getDesafiosPendientes();
		if (desafios.isEmpty()) {
			return null;
		}
		for (int i = 0; i < desafios.size(); i++) {
			System.out.println((i + 1) + ". " + desafios.get(i));
		}
		System.out.print("Selecciona un desafío: ");
		int indice = leerEntero() - 1;
		if (indice < 0 || indice >= desafios.size()) {
			return null;
		}
		return desafios.get(indice);
	}

	private List<Fortaleza> leerFortalezas() {
		return leerFortalezasDesdeLinea();
	}

	private List<Debilidad> leerDebilidades() {
		return leerDebilidadesDesdeLinea();
	}

	private List<Fortaleza> leerFortalezasDesdeLinea() {
		String entrada = scanner.nextLine().trim();
		List<Fortaleza> resultado = new ArrayList<>();
		if (entrada.isEmpty()) {
			return resultado;
		}
		for (String parte : entrada.split(",")) {
			String[] trozos = parte.trim().split(":");
			if (trozos.length == 2) {
				Integer valor = parseEnteroSeguro(trozos[1].trim());
				if (valor != null) {
					resultado.add(new Fortaleza(trozos[0].trim(), valor));
				}
			}
		}
		return resultado;
	}

	private List<Debilidad> leerDebilidadesDesdeLinea() {
		String entrada = scanner.nextLine().trim();
		List<Debilidad> resultado = new ArrayList<>();
		if (entrada.isEmpty()) {
			return resultado;
		}
		for (String parte : entrada.split(",")) {
			String[] trozos = parte.trim().split(":");
			if (trozos.length == 2) {
				Integer valor = parseEnteroSeguro(trozos[1].trim());
				if (valor != null) {
					resultado.add(new Debilidad(trozos[0].trim(), valor));
				}
			}
		}
		return resultado;
	}

	private Integer parseEnteroSeguro(String texto) {
		try {
			return Integer.parseInt(texto);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	private int leerEntero() {
		String entrada = scanner.nextLine();
		Integer valor = parseEnteroSeguro(entrada);
		return valor != null ? valor : -1;
	}

	private void imprimirCabecera() {
		System.out.println("=== MENÚ OPERADOR ===");
		if (operador != null) {
			System.out.println("Operador conectado: " + operador.getNick());
		}
		System.out.println("Gestor de desafíos activo: " + (gestorDesafios != null));
		System.out.println("Selecciona una opción:");
		System.out.println("1. Registrar operador");
		System.out.println("2. Registrar usuario");
		System.out.println("3. Dar de baja usuario");
		System.out.println("4. Dar de baja operador");
		System.out.println("5. Bloquear usuario");
		System.out.println("6. Desbloquear usuario");
		System.out.println("7. Validar desafío");
		System.out.println("8. Ver desafíos pendientes");
		System.out.println("9. Ver desafíos registrados");
		System.out.println("10. Ver historial de combates");
		System.out.println("11. Guardar datos");
		System.out.println("12. Cargar datos");
		System.out.println("0. Cerrar sesión");
		System.out.print("Selecciona una opción: ");
	}

	private int leerOpcion() {
		String entrada = scanner.nextLine();

		try {
			return Integer.parseInt(entrada);
		} catch (NumberFormatException e) {
			return -1;
		}
	}
}
