package Test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import UI.VentanaMonitor;

import javax.swing.table.DefaultTableModel;

public class VentanaMonitorTest {

    private VentanaMonitor ventanaMonitor;

    @Before
    public void setUp() throws Exception {
        ventanaMonitor = new VentanaMonitor("MonitorPrueba");
    }

    @Test
    public void testCrearTablaClasesApuntadas() {
        assertNotNull(ventanaMonitor.crearTablaClasesApuntadas());
    }

    @Test
    public void testCrearTablaClasesDisponibles() {
        assertNotNull(ventanaMonitor.crearTablaClasesDisponibles());
    }



    @Test
    public void testMostrarMaterialNecesario() {

        String clase = "Spinning";

        ventanaMonitor.mostrarMaterialNecesario(clase);

    }





    @Test
    public void testObtenerFilaHora() {
        String hora = "10:00";

        int fila = ventanaMonitor.obtenerFilaHora(hora);

        assertEquals(1, fila);
    }

    @Test
    public void testMostrarDialogoReservaExitosa() {

        String claseSeleccionada = "Yoga";

        ventanaMonitor.mostrarDialogoReservaExitosa(claseSeleccionada);

    }

   

}
