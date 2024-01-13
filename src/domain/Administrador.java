package domain;

public class Administrador extends Persona{
	private String contrasenia;
	
	public Administrador(String nombre, String apellido, int id, int edad, String contrasenia) {
		super(nombre, apellido, id, edad);
		this.contrasenia = contrasenia;
	}

	public String getContrasenia() {
		return contrasenia;
	}
	@Override
	public String toString() {
		
		return String.join("\n", new String[] {"Nombre: " + this.getNombre(), 
				"Apellido: " + this.getApellido(), "ID: " + this.getId()
				, "Contrasenia: " + contrasenia});
	}
}