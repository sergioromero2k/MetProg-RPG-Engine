package metprog.ui;

public class MenuJugador {

	public void mostrar() {
		imprimirCabecera();
		imprimirOpciones();
	}

	private void imprimirCabecera() {
		System.out.println("=== MENÚ JUGADOR ===");
		System.out.println("Selecciona una opción:");
	}

	private void imprimirOpciones() {
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
	}
}
