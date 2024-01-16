package main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import domain.Entrenamiento;
import domain.Factura;
import domain.Monitor;
import domain.Suscripcion;
import domain.TipoSuscripcion;
import domain.TiposEntrenamientos;
import domain.Usuario;

public class DeustoGymTest {

	public Suscripcion suscripcion1, suscripcion2;
	public Usuario usuario1, usuario2, usuario3, usuario4, usuario5,usuarioE;
	public Entrenamiento entrenamiento1, entrenamiento2, entrenamiento3, entrenamiento4, entrenamiento5;
	public Monitor monitor1, monitor2;
	public Factura factura1, factura2;
	public File f = new File("ficheroPrueba.dat");
	
	@Before
	public void setUp() throws Exception {monitor1 = new Monitor("123456789", "Juan", "Pérez", 30, "contraseña123");
		monitor1.agregarClaseHabilitada(TiposEntrenamientos.SPINNING);
    	monitor1.agregarClaseHabilitada(TiposEntrenamientos.YOGA);

    	monitor2 = new Monitor("987654321", "María", "Gómez", 25, "claveSecreta");
    	monitor2.agregarClaseHabilitada(TiposEntrenamientos.BOXEO);

        suscripcion2 = new Suscripcion(TipoSuscripcion.Basica, 5);
    	suscripcion1 = new Suscripcion(TipoSuscripcion.Basica, 2);
        usuario2 = new Usuario("222222222", "Juan", "Pérez", 30, "usuario456", suscripcion2);
        usuario1 = new Usuario("111111111", "Ana", "García", 28, "clave123", suscripcion1);
        
     // Instancias de Entrenamiento
        entrenamiento1 = new Entrenamiento("Spinning Masterclass", TiposEntrenamientos.SPINNING, 60, "08:00", "09:00", monitor1, 10);
        entrenamiento2 = new Entrenamiento("Yoga Relax", TiposEntrenamientos.YOGA, 45, "15:00", "15:45", monitor1, 8);
        entrenamiento3 = new Entrenamiento("Boxeo Intenso", TiposEntrenamientos.BOXEO, 75, "18:30", "19:45", monitor2, 12);
        entrenamiento4 = new Entrenamiento("Aeroyoga Fusion", TiposEntrenamientos.AEROYOGA, 60, "10:00", "11:00", monitor1, 15);
        entrenamiento5 = new Entrenamiento("Pilates Core", TiposEntrenamientos.PILATES, 50, "16:30", "17:20", monitor2, 10);

     // Asigna usuarios a los entrenamientos
        entrenamiento1.agregarAsistente(usuario1);
        entrenamiento2.agregarAsistente(usuario1);
        entrenamiento3.agregarAsistente(usuario1);
        entrenamiento4.agregarAsistente(usuario1);
        entrenamiento4.agregarAsistente(usuario2);
        entrenamiento5.agregarAsistente(usuario2);

        // Crea dos facturas
        factura1 = new Factura(usuario1);
        factura2 = new Factura(usuario2);
        if(f.exists()) {
        	f.delete();
        }
	}

	@Test
	public void test() {
		assertEquals(2, factura1.getEntrenamientosExtra().size());
		entrenamiento5.agregarAsistente(usuario1);
		assertEquals(3, factura1.getEntrenamientosExtra().size());
		assertFalse(factura1.isPagado());
		factura1.pagar();
		assertTrue(factura1.isPagado());
		factura2.calcularPago();
		assertEquals((usuario2.getS().getTp().getPrecio() - (usuario2.getS().getTp().getPrecio()* (usuario2.getS().getDescuento()/100))), factura2.getPrecioFinal());
		
		DeustoGym.listF.add(factura1);
		DeustoGym.listF.add(factura2);
		assertFalse(f.exists());
		
		DeustoGym.guardarProductos("ficheroPrueba.dat");
		DeustoGym.listF.clear();
		assertTrue(f.exists());
		DeustoGym.cargarProductos("ficheroPrueba.dat");
		assertFalse(DeustoGym.listF.isEmpty());
	}

}
