package DeustoGym;

public class Administrador {
	private String nombre;
	private String apellido;
	private String id;
	private String contrasenia;

	public Administrador(String nombre, String apellido, String id, String contrasenia) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.id = id;
		this.contrasenia = contrasenia;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public String getId() {
		return id;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void mostrarInformacion() {
		System.out.println("Nombre: " + nombre);
		System.out.println("Apellido: " + apellido);
		System.out.println("ID: " + id);
		System.out.println("Contrasenia: " + contrasenia);
	}

	public static void main(String[] args) {

		Administrador admin = new Administrador("John", "Doe", "admin123", "contrasenia123");

		admin.mostrarInformacion();
	}
}
