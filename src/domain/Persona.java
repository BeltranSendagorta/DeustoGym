package domain;

import java.io.Serializable;
import java.util.Comparator;

public abstract class Persona implements Serializable, Comparator<Persona>{
	private static final long serialVersionUID = 1L;
	private String nombre;
	private String apellido;
	private int edad;
	private String dni;
	
	public Persona(String dni, String nombre, String apellido, int edad) {
		super();
		this.dni = dni;
		this.nombre = nombre;
		this.apellido = apellido;
		this.edad = edad;
	}
	
	public Persona() {}
	
	public String getDni() {
		return this.dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
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
		return edad;
	}
	public void setEdad(int edad) {
		this.edad = edad;
	}
	@Override
	public String toString() {
		return "Persona [nombre=" + nombre + ", apellido=" + apellido + ", dni=" + dni + ", edad=" + edad + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.dni.equals(((Persona) obj).getDni());
	}
	
	@Override
    public int compare(Persona persona1, Persona persona2) {
        // Comparar por DNI (en este ejemplo, asumiendo que el DNI es un String)
        return persona1.getDni().compareTo(persona2.getDni());
    }
}
