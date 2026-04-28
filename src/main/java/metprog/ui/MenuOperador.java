package metprog.ui;

import java.util.Scanner;

public class MenuOperador {
	private final Scanner scanner;

	public MenuOperador() {
		this.scanner = new Scanner(System.in);
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
					System.out.println("Has seleccionado: Validar desafío");
					break;
				case 8:
					System.out.println("Has seleccionado: Ver desafíos pendientes");
					break;
				case 9:
					System.out.println("Has seleccionado: Ver desafíos registrados");
					break;
				case 10:
					System.out.println("Has seleccionado: Ver historial de combates");
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

	private void imprimirCabecera() {
		System.out.println("=== MENÚ OPERADOR ===");
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
