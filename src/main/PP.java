package main;

import bd.BaseDatos;
import domain.Administrador;
import domain.Entrenamiento;
import domain.Monitor;
import domain.Suscripcion;
import domain.TipoSuscripcion;
import domain.TiposEntrenamientos;
import domain.Usuario;
import ui.VentanaSesion;

public class PP {
	public static Monitor monitor1, monitor2;
	public static Suscripcion suscripcion1, suscripcion2, suscripcion3, suscripcion4, suscripcion5;
	public static Usuario usuario1, usuario2, usuario3, usuario4, usuario5;
	public static Administrador admin1, admin2;
	public static Entrenamiento entrenamiento1, entrenamiento2, entrenamiento3, entrenamiento4, entrenamiento5, entrenamiento6, entrenamiento7, entrenamiento8, entrenamiento9, entrenamiento10,
    entrenamiento11, entrenamiento12, entrenamiento13, entrenamiento14, entrenamiento15, entrenamiento16, entrenamiento17, entrenamiento18, entrenamiento19, entrenamiento20;;
	
	public static void main(String[] args) {
		//INICIAR DATOS (SOLO SI SE HA REINICIADO LA BD)
		BaseDatos.abrirConexion("resources/db/BaseDatos.db", false);
		BaseDatos.abrirConexion("resources/db/BaseDatos.db", true);
        
		// Instancias de Monitor
		monitor1 = new Monitor("123456789", "Juan", "Perez", 30, "contraseña123");
        monitor1.agregarClaseHabilitada(TiposEntrenamientos.SPINNING);
        monitor1.agregarClaseHabilitada(TiposEntrenamientos.YOGA);
        monitor2 = new Monitor("987654321", "María", "Gómez", 25, "claveSecreta");
        monitor2.agregarClaseHabilitada(TiposEntrenamientos.BOXEO);
        
     // Instancias de Suscripcion
	    suscripcion1 = new Suscripcion(TipoSuscripcion.Basica, 2);
	    suscripcion2 = new Suscripcion(TipoSuscripcion.Premium, 5);
	    suscripcion3 = new Suscripcion(TipoSuscripcion.Vip, 0);
	    suscripcion4 = new Suscripcion(TipoSuscripcion.Basica, 3);
	    suscripcion5 = new Suscripcion(TipoSuscripcion.Premium, 8);
	        
	    // Instancias de Usuario
	    usuario1 = new Usuario("111111111", "Ana", "García", 28, "clave123", suscripcion1);
	    usuario2 = new Usuario("222222222", "Luis", "Martínez", 35, "usuario123", suscripcion2);
	    usuario3 = new Usuario("333333333", "Elena", "Rodríguez", 40, "contraseña456", suscripcion3);
	    usuario4 = new Usuario("444444444", "Carlos", "Fernández", 32, "clave789", suscripcion4);
        usuario5 = new Usuario("555555555", "Laura", "Gómez", 28, "usuario456", suscripcion5);
	        
	     // Instancias de Administrador
        admin1 = new Administrador("admin1DNI", "Admin1Nombre", "Admin1Apellido", 35, "admin1Password");
        admin2 = new Administrador("admin2DNI", "Admin2Nombre", "Admin2Apellido", 40, "admin2Password");
	        
	     // Instancias de Entrenamiento
        entrenamiento1 = new Entrenamiento("Spinning Masterclass", TiposEntrenamientos.SPINNING, "Lunes", "08:00", "09:00", monitor1, 10);
        entrenamiento2 = new Entrenamiento("Yoga Relax", TiposEntrenamientos.YOGA, "Martes", "15:00", "16:00", monitor1, 8);
        entrenamiento3 = new Entrenamiento("Boxeo Intenso", TiposEntrenamientos.BOXEO, "Miercoles", "18:00", "19:00", monitor2, 12);
        entrenamiento3 = new Entrenamiento("Boxeo Intenso", TiposEntrenamientos.BOXEO, "Miercoles", "18:00", "19:00", monitor2, 12);
        entrenamiento4 = new Entrenamiento("Entrenamiento 4", TiposEntrenamientos.AEROYOGA, "Jueves", "10:00", "11:00", monitor1, 15);
        entrenamiento6 = new Entrenamiento("Entrenamiento 6", TiposEntrenamientos.HIIT, "Viernes", "12:00", "13:00", monitor1, 12);
        entrenamiento7 = new Entrenamiento("Entrenamiento 7", TiposEntrenamientos.SPINNING, "Sabado", "18:00", "19:00", monitor2, 8);
        entrenamiento5 = new Entrenamiento("Entrenamiento 5", TiposEntrenamientos.PILATES, "Domingo", "16:00", "17:00", monitor2, 10);
        entrenamiento8 = new Entrenamiento("Entrenamiento 8", TiposEntrenamientos.YOGA, "Lunes", "08:00", "09:00", monitor1, 10);
        entrenamiento9 = new Entrenamiento("Entrenamiento 9", TiposEntrenamientos.BOXEO, "Martes", "14:00", "15:00", monitor2, 15);
        entrenamiento10 = new Entrenamiento("Entrenamiento 10", TiposEntrenamientos.AEROYOGA, "Miercoles", "16:00", "17:00", monitor1, 14);
        entrenamiento11 = new Entrenamiento("Entrenamiento 11", TiposEntrenamientos.PILATES, "Jueves", "11:00", "12:00", monitor2, 9);
        entrenamiento12 = new Entrenamiento("Entrenamiento 12", TiposEntrenamientos.HIIT, "Viernes", "19:00", "20:00", monitor1, 11);
        entrenamiento13 = new Entrenamiento("Entrenamiento 13", TiposEntrenamientos.SPINNING, "Sabado", "15:00", "16:00", monitor2, 8);
        entrenamiento14 = new Entrenamiento("Entrenamiento 14", TiposEntrenamientos.YOGA, "Domingo", "09:00", "10:00", monitor1, 11);
        entrenamiento15 = new Entrenamiento("Entrenamiento 15", TiposEntrenamientos.BOXEO, "Lunes", "17:00", "18:00", monitor2, 13);
        entrenamiento16 = new Entrenamiento("Entrenamiento 16", TiposEntrenamientos.AEROYOGA, "Martes", "13:00", "14:00", monitor1, 12);
        entrenamiento17 = new Entrenamiento("Entrenamiento 17", TiposEntrenamientos.PILATES, "Miercoles", "10:00", "11:00", monitor2, 8);
        entrenamiento18 = new Entrenamiento("Entrenamiento 18", TiposEntrenamientos.HIIT, "Jueves", "20:00", "21:00", monitor1, 10);
        entrenamiento19 = new Entrenamiento("Entrenamiento 19", TiposEntrenamientos.SPINNING, "Viernes", "14:00", "15:00", monitor2, 9);
        entrenamiento20 = new Entrenamiento("Entrenamiento 20", TiposEntrenamientos.YOGA, "Lunes", "08:00", "09:00", monitor1, 12);
        
     // Asigna usuarios a los entrenamientos
        entrenamiento1.agregarAsistente(usuario1);
        entrenamiento2.agregarAsistente(usuario1);
        entrenamiento1.agregarAsistente(usuario2);
        entrenamiento2.agregarAsistente(usuario3);
        entrenamiento2.agregarAsistente(usuario4);
        entrenamiento2.agregarAsistente(usuario5);
        entrenamiento2.agregarAsistente(usuario2);
      
        VentanaSesion ventanaSesion = new VentanaSesion();
	    ventanaSesion.mostrarVentana();
	}
}
