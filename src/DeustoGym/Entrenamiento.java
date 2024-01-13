package DeustoGym;

import java.util.ArrayList;
import java.util.List;

public class Entrenamiento {
    private TiposEntrenamientos tipo;
    private int duracion;
    private String horaInicio;
    private String horaFin;
    private List<String> asistentes = new ArrayList<>();
    private List<String> listaEspera = new ArrayList<>();
    private String monitor;

    public Entrenamiento(TiposEntrenamientos tipo, int duracion, String horaInicio, String horaFin, String monitor) {
        this.tipo = tipo;
        this.duracion = duracion;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.monitor = monitor;
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

    public List<String> getAsistentes() {
        return asistentes;
    }

    public List<String> getListaEspera() {
        return listaEspera;
    }

    public String getMonitor() {
        return monitor;
    }

    public void agregarAsistente(String asistente) {
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
    	return String.join("\n", new String[] {"Tipo de Entrenamiento: " + tipo, "Duración: " + duracion + " minutos",
    			"Hora de Inicio: " + horaInicio, "Hora de Fin: " + horaFin, "Monitor: " + monitor, "Asistentes (" + asistentes.size() + "/5):",
    			String.join("- ", asistentes), "Lista de Espera (" + listaEspera.size() + "/5):",
    			String.join("- ", listaEspera)});
	}
}