package domain;

import java.util.ArrayList;
import java.util.List;

public class Monitor extends Persona{
	
	private String contrasena;
	private List<TiposEntrenamientos> clasesHabilitadas = new ArrayList<TiposEntrenamientos>();

	public Monitor(String dni, String nombre, String apellido, int edad, String contrasena) {
		super(dni, nombre, apellido, edad);
		this.contrasena = contrasena;
	}
	public Monitor() {
		// TODO Auto-generated constructor stub
	}
	public List<TiposEntrenamientos> getClasesHabilitadas() {
		return clasesHabilitadas;
	}

	public String getContrasena() {
		return contrasena;
	}
	
	public void setContrasenia(String contrasena) {
		this.contrasena = contrasena;
	}
	public void agregarClaseHabilitada(TiposEntrenamientos tipo) {
		clasesHabilitadas.add(tipo);
		System.out.println("El monitor ahora está habilitado para la clase de " + tipo);
	}
	
	@Override
	public String toString() {
		return "Monitor [contrasena=" + contrasena + ", clasesHabilitadas=" + clasesHabilitadas + ", getNombre()="
				+ getNombre() + ", getApellido()=" + getApellido() + ", getDni()=" + getDni() + ", getEdad()=" + getEdad()
				+ ", toString()=" + super.toString() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ "]";
	}
}