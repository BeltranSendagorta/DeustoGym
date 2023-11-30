package DeustoGym;

import java.util.ArrayList;
import java.util.List;

public class Monitor {
    private String dni;
    private String nombre;
    private String apellido;
    private String direccion;
    private List<TiposEntrenamientos> clasesHabilitadas;
    private String contrasena;

    public Monitor(String dni, String nombre, String apellido, String direccion, String contrasena) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
        this.clasesHabilitadas = new ArrayList<>();
        this.contrasena = contrasena;
    }

    public String getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getDireccion() {
        return direccion;
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

    public void mostrarDetalles() {
        System.out.println("DNI: " + dni);
        System.out.println("Nombre del Monitor: " + nombre + " " + apellido);
        System.out.println("Dirección: " + direccion);
        System.out.println("Clases Habilitadas:");
        for (TiposEntrenamientos tipo : clasesHabilitadas) {
            System.out.println("- " + tipo);
        }
        System.out.println("Contraseña: " + contrasena);
    }

    public static void main(String[] args) {
        Monitor monitor1 = new Monitor("12345678A", "Monitor1", "Apellido1", "Calle Principal", "contrasena123");

        monitor1.agregarClaseHabilitada(TiposEntrenamientos.SPINNING);
        monitor1.agregarClaseHabilitada(TiposEntrenamientos.YOGA);

        monitor1.mostrarDetalles();
    }
}
