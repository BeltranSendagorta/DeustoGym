package domain;

public class Usuario extends Persona{
	private Suscripcion s = new Suscripcion(TipoSuscripcion.Basica, 0);
	private String contrasenia;
	public Usuario(String nombre, String apellido, int id, int edad, String contrasenia, Suscripcion s) {
		super(nombre, apellido, id, edad);
		this.s = s;
		this.contrasenia = contrasenia;
	}
	
	public Suscripcion getS() {
		return s;
	}

	public void setS(Suscripcion s) {
		this.s = s;
	}

	public String getContraseña() {
		return contrasenia;
	}

	public void setContraseña(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	@Override
	public String toString() {
		return "Usuario [s=" + s + ", contrasenia=" + contrasenia + ", getNombre()=" + getNombre() + ", getApellido()="
				+ getApellido() + ", getId()=" + getId() + ", getEdad()=" + getEdad() + ", toString()="
				+ super.toString() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}
	
}
