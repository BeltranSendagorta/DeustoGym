package main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import bd.BaseDatos;
import domain.Entrenamiento;
import domain.Factura;
import domain.Monitor;
import domain.Suscripcion;
import domain.TipoSuscripcion;
import domain.TiposEntrenamientos;
import domain.Usuario;
import io.DeustoGym;

public class DeustoGymTest {

	public static Suscripcion suscripcion1, suscripcion2;
	public static Usuario usuario1, usuario2, usuario3, usuario4, usuario5,usuarioE;
	public static Entrenamiento entrenamiento1, entrenamiento2, entrenamiento3, entrenamiento4, entrenamiento5, entrenamiento6, entrenamiento7, entrenamiento8, entrenamiento9, entrenamiento10,
    entrenamiento11, entrenamiento12, entrenamiento13, entrenamiento14, entrenamiento15, entrenamiento16, entrenamiento17, entrenamiento18, entrenamiento19, entrenamiento20;
	public static Monitor monitor1, monitor2;
	public static Factura factura1, factura2;
	public static File f = new File("resources/data/ficheroPrueba.dat");
	public static File f2 = new File("resources/data/facturas.csv");
	
	@BeforeClass
	public static void setUp() throws Exception {
        BaseDatos.abrirConexion("resources/db/BaseDatos.db", true);
		monitor1 = new Monitor("123456789", "Juan", "Perez", 30, "contraseña123");
		monitor1.agregarClaseHabilitada(TiposEntrenamientos.SPINNING);
    	monitor1.agregarClaseHabilitada(TiposEntrenamientos.YOGA);

    	monitor2 = new Monitor("987654321", "María", "Gómez", 25, "claveSecreta");
    	monitor2.agregarClaseHabilitada(TiposEntrenamientos.BOXEO);

        suscripcion2 = new Suscripcion(TipoSuscripcion.Basica, 5);
    	suscripcion1 = new Suscripcion(TipoSuscripcion.Basica, 2);
    	
        usuario2 = new Usuario("222222222", "Juan", "Perez", 30, "usuario456", suscripcion2);
        usuario1 = new Usuario("111111111", "Ana", "García", 28, "clave123", suscripcion1);
        
     // Instancias de Entrenamiento
        entrenamiento1 = new Entrenamiento("Spinning Masterclass", TiposEntrenamientos.SPINNING, "Lunes", "08:00", "09:00", monitor1, 10);
        entrenamiento2 = new Entrenamiento("Yoga Relax", TiposEntrenamientos.YOGA, "Martes", "15:00", "16:00", monitor1, 8);
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
        entrenamiento3.agregarAsistente(usuario1);
        entrenamiento4.agregarAsistente(usuario1);
        entrenamiento5.agregarAsistente(usuario1);
        entrenamiento6.agregarAsistente(usuario1);
        entrenamiento7.agregarAsistente(usuario1);
        entrenamiento8.agregarAsistente(usuario1);
        entrenamiento4.agregarAsistente(usuario2);
        entrenamiento5.agregarAsistente(usuario2);

     // Crea dos facturas
        factura1 = new Factura(usuario1);
        factura2 = new Factura(usuario2);
        if(f.exists() || f2.exists()) {
        	f.delete();
        	f2.delete();
        }
	}

	@Test
	public void test() {
		assertEquals(6, factura1.getEntrenamientosExtra().size());
		entrenamiento9.agregarAsistente(usuario1);
		assertEquals(7, factura1.getEntrenamientosExtra().size());
		assertFalse(factura1.isPagado());
		factura1.pagar();
		assertTrue(factura1.isPagado());
		factura2.calcularPago();
		assertEquals((usuario2.getS().getTp().getPrecio() - (usuario2.getS().getTp().getPrecio()* (usuario2.getS().getDescuento()/100))), factura2.getPrecioFinal());

		DeustoGym.listF.clear();
		DeustoGym.listF.add(factura1);
		DeustoGym.listF.add(factura2);
		assertFalse(f.exists());
		
		DeustoGym.guardarProductos("resources/data/ficheroPrueba.dat");
		DeustoGym.listF.clear();
		assertTrue(f.exists());
		DeustoGym.cargarProductos("resources/data/ficheroPrueba.dat");
		assertFalse(DeustoGym.listF.isEmpty());

		DeustoGym.listF.clear();
		DeustoGym.listF.add(factura1);
		DeustoGym.listF.add(factura2);
		
		assertFalse(f2.exists());	
		DeustoGym.guardarFacturas();
		DeustoGym.listF.clear();
		assertTrue(f2.exists());
		DeustoGym.leerFacturas();
		assertFalse(DeustoGym.listF.isEmpty());
		System.out.println(DeustoGym.listF);
	}
	
	@Test
	public void recursiva() {
		DeustoGym.calcularComprasPosibles(1000,100, usuario1);
	}
}
