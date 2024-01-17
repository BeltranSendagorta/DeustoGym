package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import bd.BaseDatos;

public class Entrenamiento implements Serializable{
	private static final long serialVersionUID = 1L;
	private int id;
    private TiposEntrenamientos tipo;
    private String nombre;
    private String diaSe;
    private String horaInicio;
    private String horaFin;    
    private List<Usuario> asistentes = new ArrayList<>();
    private List<Usuario> listaEspera = new ArrayList<>();
    private Monitor monitor;
    private int precio;
    private static Logger logger = Logger.getLogger( "Entrenamiento" );
    
    public Entrenamiento(String nombre, TiposEntrenamientos tipo, String diaSe, String horaInicio, String horaFin, Monitor monitor, int precio) {
        this.tipo = tipo;
        this.diaSe = diaSe;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.monitor = monitor;
        this.precio = precio;
        this.nombre = nombre;
        BaseDatos.posibleID(this);
    }
    
    public Entrenamiento() {}

    public int getId() {
		return id;
	}
    
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TiposEntrenamientos getTipo() {
		return tipo;
	}

	public void setTipo(TiposEntrenamientos tipo) {
		this.tipo = tipo;
	}

	public String getdiaSe() {
		return diaSe;
	}

	public void setdiaSe(String diaSe) {
		this.diaSe = diaSe;
	}

	public String getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

	public String getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(String horaFin) {
		this.horaFin = horaFin;
	}

	public List<Usuario> getAsistentes() {
		return asistentes;
	}

	public void setAsistentes(List<Usuario> asistentes) {
		this.asistentes = asistentes;
	}

	public List<Usuario> getListaEspera() {
		return listaEspera;
	}

	public void setListaEspera(List<Usuario> listaEspera) {
		this.listaEspera = listaEspera;
	}

	public Monitor getMonitor() {
		return monitor;
	}

	public void setMonitor(Monitor monitor) {
		this.monitor = monitor;
	}

	public int getPrecio() {
		return precio;
	}

	public void setPrecio(int precio) {
		this.precio = precio;
	}

	public void agregarAsistente(Usuario asistente) {
        if (this.asistentes.size() < 5) {
            this.asistentes.add(asistente);
            logger.log( Level.INFO, "El asistente:" + asistente +" ha sido agregado al "+this+ "entrenamiento. ");
        } else {
            if (this.listaEspera.size() < 5) {
                this.listaEspera.add(asistente);
                logger.log( Level.INFO, "El asistente:" + asistente +" ha sido agregado la lista de espera.");
            } else {
                logger.log( Level.SEVERE, "Error: El entrenamiento ya tiene el máximo de asistentes y la lista de espera está llena.");
            }
        }
    }

	@Override
	public String toString() {
		return this.getNombre();
	}
}