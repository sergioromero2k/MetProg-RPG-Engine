package metprog.ui;

import metprog.model.Usuario;
import metprog.service.GestorUsuarios;

import java.util.Scanner;

public class MenuJugador {
	private final GestorUsuarios gestorUsuarios;
	private final Scanner scanner;
	private final Usuario usuario;

	public MenuJugador() {
		this(new GestorUsuarios(), new Scanner(System.in), null);
	}

	public MenuJugador(GestorUsuarios gestorUsuarios, Scanner scanner, Usuario usuario) {
		this.gestorUsuarios = gestorUsuarios;
		this.scanner = scanner;
		this.usuario = usuario;
	}

	public void mostrar() {
		boolean salir = false;

		while (!salir) {
			imprimirCabecera();
			int opcion = leerOpcion();

			switch (opcion) {
				case 1:
					System.out.println("Has seleccionado: Crear personaje");
					break;
				case 2:
					System.out.println("Has seleccionado: Ver datos de mi personaje");
					break;
				case 3:
					System.out.println("Has seleccionado: Gestionar armas");
					break;
				case 4:
					System.out.println("Has seleccionado: Gestionar armaduras");
					break;
				case 5:
					System.out.println("Has seleccionado: Gestionar esbirros");
					break;
				case 6:
					System.out.println("Has seleccionado: Lanzar desafío");
					break;
				case 7:
					System.out.println("Has seleccionado: Ver desafío recibido");
					break;
				case 8:
					System.out.println("Has seleccionado: Aceptar desafío recibido");
					break;
				case 9:
					System.out.println("Has seleccionado: Rechazar desafío recibido");
					break;
				case 10:
					System.out.println("Has seleccionado: Ver ranking global");
					break;
				case 11:
					System.out.println("Has seleccionado: Ver historial de combates");
					break;
				case 12:
					System.out.println("Has seleccionado: Ver historial de oro");
					break;
				case 0:
					System.out.println("Cerrando sesión de jugador...");
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
		System.out.println("=== MENÚ JUGADOR ===");
		if (usuario != null) {
			System.out.println("Jugador conectado: " + usuario.getNick());
		}
		System.out.println("Gestor activo: " + (gestorUsuarios != null));
		System.out.println("Selecciona una opción:");
		System.out.println("1. Crear personaje");
		System.out.println("2. Ver datos de mi personaje");
		System.out.println("3. Gestionar armas");
		System.out.println("4. Gestionar armaduras");
		System.out.println("5. Gestionar esbirros");
		System.out.println("6. Lanzar desafío");
		System.out.println("7. Ver desafío recibido");
		System.out.println("8. Aceptar desafío recibido");
		System.out.println("9. Rechazar desafío recibido");
		System.out.println("10. Ver ranking global");
		System.out.println("11. Ver historial de combates");
		System.out.println("12. Ver historial de oro");
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
