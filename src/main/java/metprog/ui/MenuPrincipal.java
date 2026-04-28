package metprog.ui;

import java.util.Scanner;

public class MenuPrincipal {
	private final Scanner scanner;

	public MenuPrincipal() {
		this.scanner = new Scanner(System.in);
	}

	public void mostrar() {
		boolean salir = false;

		while (!salir) {
			mostrarBienvenida();
			int opcion = leerOpcion();

			switch (opcion) {
				case 1:
					System.out.println("Has seleccionado: Jugar");
					break;
				case 2:
					System.out.println("Has seleccionado: Operador");
					break;
				case 0:
					System.out.println("Hasta pronto.");
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

	private void mostrarBienvenida() {
		System.out.println("=== Bienvenido a MetProg RPG Engine ===");
		System.out.println("1. Jugar");
		System.out.println("2. Operador");
		System.out.println("0. Salir");
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
