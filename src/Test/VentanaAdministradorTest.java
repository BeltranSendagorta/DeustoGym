package Test;

import static org.junit.Assert.*;

import java.util.Arrays;

import javax.swing.JTable;
import javax.swing.SwingUtilities;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import UI.VentanaAdministrador;

public class VentanaAdministradorTest {

    private static VentanaAdministrador ventanaAdministrador;

    @Before
    public void setUp() throws Exception {
        SwingUtilities.invokeLater(() -> {
            ventanaAdministrador = new VentanaAdministrador("TestAdmin");
            ventanaAdministrador.mostrarVentana();
        });
        Thread.sleep(1000);
    }

    @After
    public void tearDown() throws Exception {
    	ventanaAdministrador = null;
    }

    @Test
    public void testCrearTabla() {
        JTable tabla = ventanaAdministrador.crearTabla();
        assertNotNull(tabla);

    }

    @Test
    public void testCalcularGananciasIniciales() {
        assertNotNull(ventanaAdministrador.gananciasProfesores);
        assertEquals(9, ventanaAdministrador.gananciasProfesores.size());

    }

    @Test
    public void testObtenerProfesorDeClase() {
        String profesor = ventanaAdministrador.obtenerProfesorDeClase("Yoga");
        assertEquals("Koldo", profesor);

    }

    @Test
    public void testActualizarGananciasProfesor() {
        String profesorPrueba = "Koldo";
        int gananciaInicial = ventanaAdministrador.gananciasProfesores.get(profesorPrueba);
        ventanaAdministrador.actualizarGananciasProfesor(profesorPrueba, 20);
        int gananciaFinal = ventanaAdministrador.gananciasProfesores.get(profesorPrueba);
        assertEquals(gananciaInicial + 20, gananciaFinal);
    }

    @Test
    public void testActualizarLabelGanancias() {
        ventanaAdministrador.actualizarLabelGanancias();

    }

    @Test
    public void testGenerarClasesAleatorias() {
        String[] tiposClase = {"Yoga", "Spinning", "Core", "Boxeo", "Aeroyoga", "Pilates", "HIIt", "Funcional"};
        int numClases = 5;
        String[] clasesAleatorias = ventanaAdministrador.generarClasesAleatorias(tiposClase, numClases);
        assertNotNull(clasesAleatorias);
        assertEquals(numClases, clasesAleatorias.length);

    }

    @Test
    public void testMostrarDialogoClase() {
        String clasePrueba = "Yoga";
        ventanaAdministrador.mostrarDialogoClase(clasePrueba);

    }

    @Test
    public void testGenerarNombresUsuariosAleatorios() {
        String nombres = ventanaAdministrador.generarNombresUsuariosAleatorios();
        assertNotNull(nombres);

    }
}