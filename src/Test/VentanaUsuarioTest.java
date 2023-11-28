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

import javax.swing.ImageIcon;
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
		Assert.assertEquals(8, tablaClasesApuntadas.getColumnCount()); // Update to the actual number of columns
		Assert.assertEquals(12, tablaClasesApuntadas.getRowCount());
	}

	  @Test
	    public void testGetTablaClasesApuntadas() {
	        JTable tablaEsperada = ventanaUsuario.crearTablaClasesApuntadas();
	        JTable tablaObtenida = ventanaUsuario.getTablaClasesApuntadas();
	        Assert.assertEquals(tablaEsperada, tablaObtenida);
	    }

	@Test
	public void testCrearTablaClasesDisponibles() {
		JTable tablaClasesDisponibles = ventanaUsuario.crearTablaClasesDisponibles();
		Assert.assertNotNull(tablaClasesDisponibles);
		Assert.assertEquals(8, tablaClasesDisponibles.getColumnCount());
		Assert.assertEquals(13, tablaClasesDisponibles.getRowCount()); // Update to the actual number of rows
	}

	@Test
	public void testGetTablaClasesDisponibles() {
		JTable tabla = ventanaUsuario.getTablaClasesDisponibles();
		Assert.assertNotNull(tabla);
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

	public class JOptionPaneTestUtil {
		private static List<String> messages = new ArrayList<>();

		public static void reset() {
			messages.clear();
		}

		public static void showMessageDialog(Object message) {
			messages.add(message.toString());
		}

		public static List<String> getMessages() {
			return new ArrayList<>(messages);
		}
	}

	@Test
	public void testMostrarDialogoApuntarse_Apuntarse() {
		String claseSeleccionada = "Spinning";
		DefaultTableModel modeloTabla = new DefaultTableModel();
		int row = 1;
		int col = 2;
		JOptionPaneTestUtil.showMessageDialog("Sí");

		ventanaUsuario.mostrarDialogoApuntarse(claseSeleccionada, modeloTabla, row, col);
		List<String> messages = JOptionPaneTestUtil.getMessages();
		Assert.assertEquals(2, messages.size());
		Assert.assertEquals("¿Quieres apuntarte a la clase de Spinning?", messages.get(0));
		Assert.assertEquals("Te has apuntado a la clase de Spinning", messages.get(1));

	}

	@Test
	public void testMostrarDialogoDesapuntarse_Desapuntarse() {
	    String claseSeleccionada = "Spinning";
	    DefaultTableModel modeloTabla = new DefaultTableModel();
	    int row = 1;
	    int col = 2;

	    // Configura la simulación del diálogo
	    JOptionPaneTestUtil.showMessageDialog("Sí");

	    // Ejecuta el método a probar
	    try {
	        ventanaUsuario.mostrarDialogoDesapuntarse(claseSeleccionada, modeloTabla, row, col);

	        // Obtiene los mensajes mostrados en el diálogo
	        List<String> messages = JOptionPaneTestUtil.getMessages();

	        // Imprime los mensajes para debugging
	        System.out.println("Mensajes obtenidos: " + messages);

	        // Imprime información adicional para depurar
	        System.out.println("Número de filas en modeloTabla: " + modeloTabla.getRowCount());
	        System.out.println("Número de columnas en modeloTabla: " + modeloTabla.getColumnCount());
	        System.out.println("Value at (" + row + ", " + col + "): " + modeloTabla.getValueAt(row, col));

	        // Realiza las aserciones
	        Assert.assertEquals(1, messages.size());
	        Assert.assertEquals("¿Quieres desapuntarte de la clase de Spinning?", messages.get(0));

	        // Aquí puedes agregar más aserciones si es necesario

	    } catch (Exception e) {
	        // Captura cualquier excepción para obtener más detalles en la consola
	        e.printStackTrace();
	        Assert.fail("Excepción inesperada: " + e.getMessage());
	    }
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
