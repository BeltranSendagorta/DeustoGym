package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import bd.BaseDatos;

public class Monitor extends Persona{
	
	private static final long serialVersionUID = 1L;
	private String contrasena;
	private List<TiposEntrenamientos> clasesHabilitadas = new ArrayList<TiposEntrenamientos>();
    private static Logger logger = Logger.getLogger( "Entrenamiento" );
    
	public Monitor(String dni, String nombre, String apellido, int edad, String contrasena) {
		super(dni, nombre, apellido, edad);
		this.contrasena = contrasena;
		BaseDatos.insertarPersona(this);
	}
	public Monitor() {}
	
	public List<TiposEntrenamientos> getClasesHabilitadas() {
		return this.clasesHabilitadas;
	}

	public String getContrasena() {
		return this.contrasena;
	}
	
	public void setContrasenia(String contrasena) {
		this.contrasena = contrasena;
	}
	public void agregarClaseHabilitada(TiposEntrenamientos tipo) {
		this.clasesHabilitadas.add(tipo);
		logger.log( Level.INFO, "El monitor ahora está habilitado para la clase de" + tipo);
	}
	
	@Override
	public String toString() {
		return this.getNombre();
	}
}