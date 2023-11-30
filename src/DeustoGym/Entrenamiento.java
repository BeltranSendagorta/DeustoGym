package DeustoGym;

import java.util.ArrayList;
import java.util.List;

public class Entrenamiento {
    private TiposEntrenamientos tipo;
    private int duracion;
    private String horaInicio;
    private String horaFin;
    private List<String> asistentes;
    private List<String> listaEspera; // Nueva lista de espera
    private String monitor;

    public Entrenamiento(TiposEntrenamientos tipo, int duracion, String horaInicio, String horaFin, String monitor) {
        this.tipo = tipo;
        this.duracion = duracion;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.monitor = monitor;
        this.asistentes = new ArrayList<>();
        this.listaEspera = new ArrayList<>();
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

    public void mostrarDetalles() {
        System.out.println("Tipo de Entrenamiento: " + tipo);
        System.out.println("Duración: " + duracion + " minutos");
        System.out.println("Hora de Inicio: " + horaInicio);
        System.out.println("Hora de Fin: " + horaFin);
        System.out.println("Monitor: " + monitor);
        System.out.println("Asistentes (" + asistentes.size() + "/5):");
        for (String asistente : asistentes) {
            System.out.println("- " + asistente);
        }
        System.out.println("Lista de Espera (" + listaEspera.size() + "/5):");
        for (String espera : listaEspera) {
            System.out.println("- " + espera);
        }
    }

    public static void main(String[] args) {
        // Crear un entrenamiento de tipo SPINNING con una duración de 60 minutos
        Entrenamiento spinning = new Entrenamiento(TiposEntrenamientos.SPINNING, 60, "10:00", "11:00", "Monitor1");

        // Agregar asistentes al entrenamiento
        spinning.agregarAsistente("Asistente1");
        spinning.agregarAsistente("Asistente2");
        spinning.agregarAsistente("Asistente3");
        spinning.agregarAsistente("Asistente4");
        spinning.agregarAsistente("Asistente5"); // Llega al máximo permitido
        spinning.agregarAsistente("Asistente6"); // Va a la lista de espera

        // Mostrar detalles del entrenamiento
        spinning.mostrarDetalles();
    }
}