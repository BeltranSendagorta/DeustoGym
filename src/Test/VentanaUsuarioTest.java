package Test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import UI.VentanaUsuario;

import java.awt.Component;
import java.awt.Container;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;



public class VentanaUsuarioTest {

	private VentanaUsuario ventanaUsuario;

	@Before
	public void setUp() {
		ventanaUsuario = new VentanaUsuario("nombreUsuario");

	}

	@Test
	public void testCrearTablaClasesApuntadas() {
		JTable tablaClasesApuntadas = ventanaUsuario.crearTablaClasesApuntadas();
		Assert.assertNotNull(tablaClasesApuntadas);
		Assert.assertEquals(8, tablaClasesApuntadas.getColumnCount()); 
		Assert.assertEquals(12, tablaClasesApuntadas.getRowCount());
	}

	@Test
	public void testAgregarClaseAClasesApuntadas() {
	    String claseSeleccionada = null;
	    int row = 1;
	    int col = 2;

	    System.out.println("Before agregarClaseAClasesApuntadas - Value at (" + row + ", " + col + "): "
	            + ventanaUsuario.getTablaClasesApuntadas().getValueAt(row, col));

	    ventanaUsuario.agregarClaseAClasesApuntadas(claseSeleccionada, row, col);

	    JTable tablaClasesApuntadas = ventanaUsuario.getTablaClasesApuntadas();
	    DefaultTableModel model = (DefaultTableModel) tablaClasesApuntadas.getModel();
	    String claseApuntada = (String) model.getValueAt(row, col);

	    System.out.println("After agregarClaseAClasesApuntadas - Value at (" + row + ", " + col + "): "
	            + tablaClasesApuntadas.getValueAt(row, col));

	    Assert.assertEquals(claseSeleccionada, claseApuntada);
	}

	@Test
	public void testCrearTablaClasesDisponibles() {
		JTable tablaClasesDisponibles = ventanaUsuario.crearTablaClasesDisponibles();
		Assert.assertNotNull(tablaClasesDisponibles);
		Assert.assertEquals(8, tablaClasesDisponibles.getColumnCount());
		Assert.assertEquals(13, tablaClasesDisponibles.getRowCount()); // Update to the actual number of rows
	}
	

	@Test
	public void testObtenerFilaHora() {
		String hora = "10:00";
		int filaDisponibles = ventanaUsuario.obtenerFilaHora(hora);
		Assert.assertEquals(1, filaDisponibles);
	}

	@Test
	public void testObtenerIndiceColumna_ColumnaExistente() {
		DefaultTableModel modeloTabla = new DefaultTableModel(new Object[][] { { "1", "2", "3" } },
				new String[] { "Columna 1", "Columna 2", "Columna 3" });

		int indiceColumna = ventanaUsuario.obtenerIndiceColumna("Columna 2", modeloTabla);
		Assert.assertEquals(1, indiceColumna);
	}

	@Test
	public void testObtenerIndiceColumna() {
		DefaultTableModel modeloTabla = new DefaultTableModel(new Object[][] { { "1", "2", "3" } },
				new String[] { "Columna 1", "Columna 2", "Columna 3" });

		int indiceColumna = ventanaUsuario.obtenerIndiceColumna("Columna 4", modeloTabla);
		Assert.assertEquals(-1, indiceColumna);
	}

	 @Test
	    public void testMostrarDialogoReservaExitosa() {
	        String entrenamientoSeleccionado = "Spinning";

	        ventanaUsuario.mostrarDialogoReservaExitosa(entrenamientoSeleccionado);
	    }

	@Test
	public void testMostrarVentana() {
		ventanaUsuario.mostrarVentana();

		Assert.assertTrue(ventanaUsuario.frame.isVisible());
	}
	
	
	

}
