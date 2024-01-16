package domain;

import bd.BaseDatos;

public class Usuario extends Persona{
	private static final long serialVersionUID = 1L;
	private Suscripcion s = new Suscripcion(TipoSuscripcion.Basica, 0);
	private String contrasenia;
	
	public Usuario(String dni, String nombre, String apellido, int edad, String contrasenia, Suscripcion s) {
		super(dni, nombre, apellido, edad);
		this.s = s;
		this.contrasenia = contrasenia;
		this.s=s;
		BaseDatos.insertarPersona(this);
	}
	
	public Usuario() {}

	public Suscripcion getS() {
		return this.s;
	}

	public void setS(Suscripcion s) {
		this.s = s;
	}

	public String getContrasenia() {
		return this.contrasenia;
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