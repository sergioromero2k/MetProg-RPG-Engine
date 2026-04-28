package metprog.ui;

import metprog.model.Operador;
import metprog.model.Usuario;
import metprog.service.GestorUsuarios;

import java.util.Scanner;

public class MenuPrincipal {
	private final GestorUsuarios gestorUsuarios;
	private final Scanner scanner;

	public MenuPrincipal() {
		this(new GestorUsuarios(), new Scanner(System.in));
	}

	public MenuPrincipal(GestorUsuarios gestorUsuarios) {
		this(gestorUsuarios, new Scanner(System.in));
	}

	public MenuPrincipal(GestorUsuarios gestorUsuarios, Scanner scanner) {
		this.gestorUsuarios = gestorUsuarios;
		this.scanner = scanner;
	}

	public void mostrar() {
		boolean salir = false;

		while (!salir) {
			mostrarBienvenida();
			int opcion = leerOpcion();

			switch (opcion) {
				case 1:
					gestionarJugador();
					break;
				case 2:
					gestionarOperador();
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

	private void gestionarJugador() {
		boolean volver = false;

		while (!volver) {
			System.out.println("=== ACCESO JUGADOR ===");
			System.out.println("1. Iniciar sesión");
			System.out.println("2. Registrar usuario");
			System.out.println("0. Volver");
			System.out.print("Selecciona una opción: ");

			switch (leerOpcion()) {
				case 1:
					Usuario usuario = iniciarSesionUsuario();
					if (usuario != null) {
						new MenuJugador(gestorUsuarios, scanner, usuario).mostrar();
					}
					break;
				case 2:
					Usuario registrado = registrarUsuario();
					if (registrado != null) {
						new MenuJugador(gestorUsuarios, scanner, registrado).mostrar();
					}
					break;
				case 0:
					volver = true;
					break;
				default:
					System.out.println("Opción no válida.");
			}

			if (!volver) {
				System.out.println();
			}
		}
	}

	private void gestionarOperador() {
		boolean volver = false;

		while (!volver) {
			System.out.println("=== ACCESO OPERADOR ===");
			System.out.println("1. Iniciar sesión");
			System.out.println("2. Registrar operador");
			System.out.println("0. Volver");
			System.out.print("Selecciona una opción: ");

			switch (leerOpcion()) {
				case 1:
					Operador operador = iniciarSesionOperador();
					if (operador != null) {
						new MenuOperador(gestorUsuarios, scanner, operador).mostrar();
					}
					break;
				case 2:
					Operador registrado = registrarOperador();
					if (registrado != null) {
						new MenuOperador(gestorUsuarios, scanner, registrado).mostrar();
					}
					break;
				case 0:
					volver = true;
					break;
				default:
					System.out.println("Opción no válida.");
			}

			if (!volver) {
				System.out.println();
			}
		}
	}

	private Usuario iniciarSesionUsuario() {
		System.out.print("Nick: ");
		String nick = scanner.nextLine();
		System.out.print("Contraseña: ");
		String password = scanner.nextLine();
		Usuario usuario = gestorUsuarios.loginUsuario(nick, password);

		if (usuario == null) {
			System.out.println("Credenciales inválidas o usuario bloqueado.");
		} else {
			System.out.println("Bienvenido, " + usuario.getNick() + ".");
		}
		return usuario;
	}

	private Usuario registrarUsuario() {
		System.out.print("Nombre: ");
		String nombre = scanner.nextLine();
		System.out.print("Nick: ");
		String nick = scanner.nextLine();
		System.out.print("Contraseña: ");
		String password = scanner.nextLine();
		Usuario usuario = gestorUsuarios.registrarUsuario(nombre, nick, password);

		if (usuario == null) {
			System.out.println("No se ha podido registrar el usuario.");
		} else {
			System.out.println("Usuario registrado correctamente: " + usuario.getNick());
		}
		return usuario;
	}

	private Operador iniciarSesionOperador() {
		System.out.print("Nick: ");
		String nick = scanner.nextLine();
		System.out.print("Contraseña: ");
		String password = scanner.nextLine();
		Operador operador = gestorUsuarios.loginOperador(nick, password);

		if (operador == null) {
			System.out.println("Credenciales inválidas.");
		} else {
			System.out.println("Bienvenido, operador " + operador.getNick() + ".");
		}
		return operador;
	}

	private Operador registrarOperador() {
		System.out.print("Nombre: ");
		String nombre = scanner.nextLine();
		System.out.print("Nick: ");
		String nick = scanner.nextLine();
		System.out.print("Contraseña: ");
		String password = scanner.nextLine();
		Operador operador = gestorUsuarios.registrarOperador(nombre, nick, password);

		if (operador == null) {
			System.out.println("No se ha podido registrar el operador.");
		} else {
			System.out.println("Operador registrado correctamente: " + operador.getNick());
		}
		return operador;
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
