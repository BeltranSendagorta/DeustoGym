package Test;

import static org.junit.Assert.*;

import java.util.Arrays;

import javax.swing.SwingUtilities;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import UI.VentanaAdministrador;

public class VentanaAdministradorTest {

    private static VentanaAdministrador ventana;

    @Before
    public void setUp() throws Exception {
        SwingUtilities.invokeLater(() -> {
            ventana = new VentanaAdministrador("TestAdmin");
            ventana.mostrarVentana();
        });
        Thread.sleep(1000);
    }

    @After
    public void tearDown() throws Exception {
        ventana = null;
    }

    @Test
    public void testObtenerProfesorDeClase() {
        assertEquals("Koldo", ventana.obtenerProfesorDeClase("Yoga"));
        assertEquals("Jose", ventana.obtenerProfesorDeClase("Spinning"));
        assertEquals("Beltran", ventana.obtenerProfesorDeClase("Core"));
        assertNull(ventana.obtenerProfesorDeClase("ClaseInvalida"));
    }

    @Test
    public void testActualizarGananciasProfesor() {
        ventana.actualizarGananciasProfesor("Koldo", 20);
        assertEquals(20, ventana.gananciasProfesores.get("Koldo").intValue());

        ventana.actualizarGananciasProfesor("Koldo", 30);
        assertEquals(50, ventana.gananciasProfesores.get("Koldo").intValue());

        ventana.actualizarGananciasProfesor("NuevoProfesor", 15);
        assertEquals(15, ventana.gananciasProfesores.get("NuevoProfesor").intValue());
    }

  

    @Test
    public void testCalcularGananciasIniciales() {
        ventana.calcularGananciasIniciales();

        assertNotNull(ventana.gananciasProfesores);
        assertFalse(ventana.gananciasProfesores.isEmpty());

        for (String profesor : ventana.gananciasProfesores.keySet()) {
            assertEquals(0, ventana.gananciasProfesores.get(profesor).intValue());
        }
    }

    @Test
    public void testGenerarNombresUsuariosAleatorios() {
        String nombres = ventana.generarNombresUsuariosAleatorios();
        assertNotNull(nombres);
    }
}
