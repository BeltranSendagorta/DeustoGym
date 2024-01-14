package domain;

public class Usuario extends Persona{
	private Suscripcion s = new Suscripcion(TipoSuscripcion.Basica, 0);
	private String contrasenia;
	public Usuario(String dni, String nombre, String apellido, int edad, String contrasenia, Suscripcion s) {
		super(dni, nombre, apellido, edad);
		this.s = s;
		this.contrasenia = contrasenia;
	}
	
	public Usuario() {
		// TODO Auto-generated constructor stub
	}

	public Suscripcion getS() {
		return s;
	}

	public void setS(Suscripcion s) {
		this.s = s;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	@Override
	public String toString() {
		return "Usuario [s=" + s + ", contrasenia=" + contrasenia + ", getNombre()=" + getNombre() + ", getApellido()="
				+ getApellido() + ", getDni()=" + getDni() + ", getEdad()=" + getEdad() + ", toString()="
				+ super.toString() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}
	
}
