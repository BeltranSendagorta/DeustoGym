package domain;

import java.util.ArrayList;
import java.util.List;

public class Monitor extends Persona{
	
	private String contrasena;
	private List<TiposEntrenamientos> clasesHabilitadas = new ArrayList<TiposEntrenamientos>();

	public Monitor(String nombre, String apellido, int id, int edad, String contrasena) {
		super(nombre, apellido, id, edad);
		this.contrasena = contrasena;
	}
	public List<TiposEntrenamientos> getClasesHabilitadas() {
		return clasesHabilitadas;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void agregarClaseHabilitada(TiposEntrenamientos tipo) {
		clasesHabilitadas.add(tipo);
		System.out.println("El monitor ahora está habilitado para la clase de " + tipo);
	}
	
	@Override
	public String toString() {
		return "Monitor [contrasena=" + contrasena + ", clasesHabilitadas=" + clasesHabilitadas + ", getNombre()="
				+ getNombre() + ", getApellido()=" + getApellido() + ", getId()=" + getId() + ", getEdad()=" + getEdad()
				+ ", toString()=" + super.toString() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ "]";
	}
}
