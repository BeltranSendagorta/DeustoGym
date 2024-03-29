/**
 * 
 */
package bd;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import domain.Administrador;
import domain.Entrenamiento;
import domain.Monitor;
import domain.Suscripcion;
import domain.TipoSuscripcion;
import domain.TiposEntrenamientos;
import domain.Usuario;

public class BaseDatosTest {
	
	public Monitor monitor1, monitor2;
	public Suscripcion suscripcion1, suscripcion2, suscripcion3, suscripcion4, suscripcion5;
	public Usuario usuario1, usuario2, usuario3, usuario4, usuario5;
	public Administrador admin1, admin2;
	public Entrenamiento entrenamiento1, entrenamiento2, entrenamiento3;
	
	@Before
	public void setUp() throws Exception {
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

     // Asigna usuarios a los entrenamientos
        entrenamiento1.agregarAsistente(usuario1);
        entrenamiento1.agregarAsistente(usuario2);
        entrenamiento2.agregarAsistente(usuario3);
        entrenamiento2.agregarAsistente(usuario4);
        entrenamiento2.agregarAsistente(usuario5);
        entrenamiento2.agregarAsistente(usuario1);
        entrenamiento2.agregarAsistente(usuario2);
	}

	@Test
	public void test() {
		BaseDatos.personas.clear();
		BaseDatos.entrenamientos.clear();
		
		BaseDatos.getEntrenamiento();
		BaseDatos.getPersonas();
		
		assertEquals("Admin1Apellido",BaseDatos.personas.get("admin1DNI").getApellido());
		
		assertEquals(3, BaseDatos.entrenamientos.size());
		assertEquals(9, BaseDatos.personas.keySet().size());
		
		BaseDatos.personas.get("admin1DNI").setApellido("Gonzalez");
		
		BaseDatos.actualizarBD();  
		
		BaseDatos.personas.clear();
		BaseDatos.entrenamientos.clear();
		
		BaseDatos.getEntrenamiento();
		BaseDatos.getPersonas();
		
		assertEquals("Gonzalez", BaseDatos.personas.get("admin1DNI").getApellido());
	}

}
