package domain;

public abstract class Persona {
	private String nombre;
	private String apellido;
	private int id;
	private int edad;
	
	public Persona(String nombre, String apellido, int id, int edad) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.id = id;
		this.edad = edad;
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getEdad() {
		return edad;
	}
	public void setEdad(int edad) {
		this.edad = edad;
	}
	@Override
	public String toString() {
		return "Persona [nombre=" + nombre + ", apellido=" + apellido + ", id=" + id + ", edad=" + edad + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		
		return this.id == ((Persona) obj).getId();
	}
}
