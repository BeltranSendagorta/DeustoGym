package domain;

public class Administrador extends Persona{
	private String contrasenia;
	
	public Administrador(String dni, String nombre, String apellido, int edad, String contrasenia) {
		super(dni, nombre, apellido, edad);
		this.contrasenia = contrasenia;
	}

	public Administrador() {
	}

	public String getContrasenia() {
		return contrasenia;
	}
	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	@Override
	public String toString() {
		
		return String.join("\n", new String[] {"Nombre: " + this.getNombre(), 
				"Apellido: " + this.getApellido(), "Dni: " + this.getDni()
				, "Contrasenia: " + contrasenia});
	}
}