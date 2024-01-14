package domain;

import java.util.ArrayList;
import java.util.List;

import bd.BaseDatos;

public class Entrenamiento {
	private int id;
    private TiposEntrenamientos tipo;
    private int duracion;
    private String horaInicio;
    private String horaFin;
    private List<Usuario> asistentes = new ArrayList<>();
    private List<Usuario> listaEspera = new ArrayList<>();
    private Monitor monitor;
    private int precio;
    
    public Entrenamiento(TiposEntrenamientos tipo, int duracion, String horaInicio, String horaFin, Monitor monitor, int precio) {
        this.tipo = tipo;
        this.duracion = duracion;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.monitor = monitor;
        this.precio = precio;
        BaseDatos.posibleID(this);
    }
    
    public Entrenamiento() {}

    public int getId() {
		return id;
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

	public int getDuracion() {
		return duracion;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
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
        if (asistentes.size() < 5) {
            asistentes.add(asistente);
            System.out.println("El asistente ha sido agregado al entrenamiento.");
        } else {
            if (listaEspera.size() < 5) {
                listaEspera.add(asistente);
                System.out.println("El asistente ha sido agregado a la lista de espera.");
            } else {
                System.out.println("Error: El entrenamiento ya tiene el máximo de asistentes y la lista de espera está llena.");
            }
        }
    }

	@Override
	public String toString() {
		return "Entrenamiento [tipo=" + tipo + ", duracion=" + duracion + ", horaInicio=" + horaInicio + ", horaFin="
				+ horaFin + ", asistentes=" + asistentes + ", listaEspera=" + listaEspera + ", monitor=" + monitor
				+ ", precio=" + precio + "]";
	}
	
//	Entrenamiento (id INTEGER PRIMARY KEY AUTOINCREMENT,
//	tipEntr int,
//	horaI varchar(100), 
//	horaF varchar(100), precio int,
//	idMonitor varchar(9) REFERENCES Persona (dni));"
	
//	EntrUsu (id INTEGER PRIMARY KEY AUTOINCREMENT, 
//	idEntr REFERENCES Entrenamiento (id), espera int, 
//	idPersona REFERENCES Persona (dni));"
}