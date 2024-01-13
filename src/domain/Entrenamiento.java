package domain;

import java.util.ArrayList;
import java.util.List;

public class Entrenamiento {
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
    }
    
    public int getPrecio() {
		return precio;
	}

	public void setPrecio(int precio) {
		this.precio = precio;
	}

	public TiposEntrenamientos getTipo() {
        return tipo;
    }

    public int getDuracion() {
        return duracion;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public List<Usuario> getAsistentes() {
        return asistentes;
    }

    public List<Usuario> getListaEspera() {
        return listaEspera;
    }

    public Monitor getMonitor() {
        return monitor;
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
}