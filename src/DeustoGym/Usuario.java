package DeustoGym;

import java.util.Calendar;

public class Usuario {
	private String nombre;
	private String apellido;
	private int edad;
	private String contraseña;
	private String id;

	public Usuario(String nombre, String apellido, int edad, String contraseña, String id) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.edad = edad;
		this.contraseña = encriptContrasenia(contraseña); // Encrypting the password during initialization
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public int getEdad() {
		int anioActual = Calendar.getInstance().get(Calendar.YEAR);
		return anioActual - edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void displayInfo() {
		System.out.println("Nombre: " + nombre);
		System.out.println("Apellido: " + apellido);
		System.out.println("Edad: " + getEdad());
		System.out.println("Contraseña: " + contraseña);
		System.out.println("ID: " + id);
	}

	public int calcularEdad() {
	    return 2023 - edad;
	}
	
	
	// Encriptacion de manera Caesar cipher
	// Se sustituye la letra actual por una 3 posicion en adelante
	private String encriptContrasenia(String contraseña) {
		int movimiento = 3;
		StringBuilder encryptedPassword = new StringBuilder();

		for (char c : contraseña.toCharArray()) {
			if (Character.isLetter(c)) {
				char movHecho = (char) (c + movimiento);
				if ((Character.isLowerCase(c) && movHecho > 'z') || (Character.isUpperCase(c) && movHecho > 'Z')) {
					movimiento = (char) (c - (26 - movimiento));
				}
				encryptedPassword.append(movHecho);
			} else {
				encryptedPassword.append(c);
			}
		}

		return encryptedPassword.toString();
	}

	public static void main(String[] args) {

		Usuario client = new Usuario("John", "Doe", 1990, "AAAAAAAA", "Gym123");
		client.displayInfo();
	}
}
