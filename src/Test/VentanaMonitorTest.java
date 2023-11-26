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
    public void testMostrarDialogoApuntarse() {
        // Given
        String claseSeleccionada = "Yoga";
        int row = 1; 
        int col = 2; 

        ventanaMonitor.mostrarDialogoApuntarse(claseSeleccionada,
                (DefaultTableModel) ventanaMonitor.getTablaSemanasDisponibles().getModel(), row, col);

        assertEquals(claseSeleccionada, ventanaMonitor.getTablaSemanasApuntado().getValueAt(row, col));
    }

    @Test
    public void testMostrarDialogoDesapuntarse() {
 
        String claseSeleccionada = "Yoga";
        int row = 1; 
        int col = 2; 

        ventanaMonitor.mostrarDialogoApuntarse(claseSeleccionada,
                (DefaultTableModel) ventanaMonitor.getTablaSemanasDisponibles().getModel(), row, col);

        assertEquals(claseSeleccionada, ventanaMonitor.getTablaSemanasApuntado().getValueAt(row, col));

        ventanaMonitor.mostrarDialogoDesapuntarse(claseSeleccionada,
                (DefaultTableModel) ventanaMonitor.getTablaSemanasApuntado().getModel(), row, col);

        assertNull(ventanaMonitor.getTablaSemanasApuntado().getValueAt(row, col));
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

    @Test
    public void testObtenerIndiceColumna() {

        String nombreColumna = "Martes";


        int indice = ventanaMonitor.obtenerIndiceColumna(nombreColumna,
                (DefaultTableModel) ventanaMonitor.getTablaSemanasDisponibles().getModel());

        assertEquals(2, indice);
    }
}
