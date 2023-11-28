package Test;

import static org.junit.Assert.*;

import java.util.concurrent.CountDownLatch;

import javax.swing.SwingUtilities;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import UI.VentanaAdministrador;

public class VentanaAdministradorTest {

    private static VentanaAdministrador ventanaAdministrador;

    @Before
    public void setUp() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);

        SwingUtilities.invokeLater(() -> {
            ventanaAdministrador = new VentanaAdministrador("TestAdmin");
            ventanaAdministrador.mostrarVentana();
            latch.countDown();
        });

        latch.await(); // Esperar a que la inicialización se complete
    }

    @After
    public void tearDown() throws Exception {
        ventanaAdministrador = null;
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
