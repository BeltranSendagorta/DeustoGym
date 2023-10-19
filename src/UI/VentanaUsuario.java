package UI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaUsuario {
	private JFrame frame;
	private DefaultListModel<String> modeloLista;
	private JList<String> listaActividades;
	private JTable tablaSemanasApuntado;
	private JTable tablaSemanasDisponibles;

	public VentanaUsuario(String nombreUsuario) {
		frame = new JFrame("Ventana de Usuario");
		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setLayout(new BorderLayout());

		JLabel perfilLabel = new JLabel("Perfil del Usuario: " + nombreUsuario);
		perfilLabel.setHorizontalAlignment(JLabel.RIGHT);
		perfilLabel.setForeground(Color.BLACK);
		frame.add(perfilLabel, BorderLayout.NORTH);

		// Crear el panel de pestañas
		JTabbedPane tabbedPane = new JTabbedPane();

		// Pestaña de clases apuntadas
		JPanel panelApuntado = new JPanel(new BorderLayout());
		tablaSemanasApuntado = crearTabla();
		panelApuntado.add(new JScrollPane(tablaSemanasApuntado), BorderLayout.CENTER);

		// Pestaña de clases disponibles
		JPanel panelDisponibles = new JPanel(new BorderLayout());
		tablaSemanasDisponibles = crearTablaConBotones();
		panelDisponibles.add(new JScrollPane(tablaSemanasDisponibles), BorderLayout.CENTER);

		// Agregar pestañas al panel de pestañas
		tabbedPane.addTab("Clases Apuntadas", panelApuntado);
		tabbedPane.addTab("Clases Disponibles", panelDisponibles);

		frame.add(tabbedPane, BorderLayout.CENTER);

		modeloLista = new DefaultListModel<>();
		listaActividades = new JList<>(modeloLista);
		listaActividades.setBackground(new Color(240, 240, 240));
		JScrollPane scrollLista = new JScrollPane(listaActividades);
		frame.add(scrollLista, BorderLayout.EAST);

		listaActividades.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int index = listaActividades.locationToIndex(evt.getPoint());
				String entrenamientoSeleccionado = modeloLista.getElementAt(index);
				mostrarDialogoReservaExitosa(entrenamientoSeleccionado);
			}
		});

		frame.setVisible(true);
	}

	private JTable crearTabla() {
		JTable tabla = new JTable();
		DefaultTableModel modeloTabla = new DefaultTableModel(0, 8) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		// Añadir las horas en la primera columna
		for (int i = 9; i <= 20; i++) {
			modeloTabla.addRow(new Object[] { i + ":00", null, null, null, null, null, null, null });
		}

		// Añadir los días de la semana como encabezados de columna
		modeloTabla.setColumnIdentifiers(
				new Object[] { "Hora", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo" });

		tabla.setModel(modeloTabla);
		tabla.setGridColor(Color.BLACK);
		tabla.setShowGrid(true);
		tabla.setCellSelectionEnabled(true);

		// Agregar un escucha de eventos de clic para manejar la selección de celdas
		tabla.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int row = tabla.rowAtPoint(evt.getPoint());
				int col = tabla.columnAtPoint(evt.getPoint());

				if (row >= 0 && col > 0) { // Evitar la primera columna (horas)
					// Aquí puedes manejar la lógica para la celda seleccionada
					String claseSeleccionada = (String) modeloTabla.getValueAt(row, col);
					if (claseSeleccionada != null) {
						mostrarDialogoApuntarse(claseSeleccionada, modeloTabla, row, col);
					}
				}
			}
		});

		return tabla;
	}

	private JTable crearTablaConBotones() {
	    JTable tabla = new JTable();
	    DefaultTableModel modeloTabla = new DefaultTableModel(0, 8) {
	        @Override
	        public boolean isCellEditable(int row, int column) {
	            return false;
	        }
	    };

	    // Añadir las horas en la primera columna
	    for (int i = 9; i <= 20; i++) {
	        modeloTabla.addRow(new Object[]{i + ":00", null, null, null, null, null, null, null});
	    }

	    // Añadir los días de la semana como encabezados de columna
	    modeloTabla.setColumnIdentifiers(new Object[]{"Hora", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"});

	    // Añadir botones de Spinning los martes y jueves a las 7 pm
	    agregarBoton(modeloTabla, "Martes", 10);
	    agregarBoton(modeloTabla, "Jueves", 10);

	    tabla.setModel(modeloTabla);
	    tabla.setGridColor(Color.BLACK);
	    tabla.setShowGrid(true);
	    tabla.setCellSelectionEnabled(true);

	    // Agregar un escucha de eventos de clic para manejar la selección de celdas
	    tabla.addMouseListener(new java.awt.event.MouseAdapter() {
	        @Override
	        public void mouseClicked(java.awt.event.MouseEvent evt) {
	            int row = tabla.rowAtPoint(evt.getPoint());
	            int col = tabla.columnAtPoint(evt.getPoint());

	            if (row >= 0 && col > 0) {  // Evitar la primera columna (horas)
	                // Aquí puedes manejar la lógica para la celda seleccionada
	                Object contenidoCelda = modeloTabla.getValueAt(row, col);
	                if (contenidoCelda instanceof JButton) {
	                    ((JButton) contenidoCelda).doClick();
	                }
	            }
	        }
	    });

	    return tabla;
	}

	// Método para agregar un botón a la tabla en la posición especificada
	private void agregarBoton(DefaultTableModel modeloTabla, String nombreColumna, int fila) {
	    int indiceColumna = obtenerIndiceColumna(nombreColumna, modeloTabla);
	    if (indiceColumna != -1) {
	        JButton boton = new JButton("Spinning");
	        boton.addActionListener(e -> mostrarDialogoApuntarse("Spinning", modeloTabla, fila, indiceColumna));
	        modeloTabla.setValueAt(boton, fila, indiceColumna);
	    } else {
	        System.err.println("La columna '" + nombreColumna + "' no se encontró.");
	    }
	}


	private void mostrarDialogoApuntarse(String claseSeleccionada, DefaultTableModel modeloTabla, int row, int col) {
		// Crear un cuadro de diálogo con las opciones de reserva
		String[] opciones = { "Sí", "No" };
		int seleccion = JOptionPane.showOptionDialog(frame,
				"¿Quieres apuntarte a la clase de " + claseSeleccionada + "?", "Apuntarse a Clase",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

		// Si se selecciona "Sí", añadir la clase a la lista de actividades y cambiar el
		// texto a "Apuntado"
		if (seleccion == JOptionPane.YES_OPTION) {
			modeloLista.addElement(claseSeleccionada);

			// Verificar si la clase seleccionada es "Spinning" y, en ese caso, agregarla a
			// la tabla de clases apuntadas
			if ("Spinning".equals(claseSeleccionada)) {
				modeloTabla.setValueAt("Spinning", row, col); // Mantener el texto como "Spinning"
				agregarClaseSpinningAClasesApuntadas(row, col);
			} else {
				modeloTabla.setValueAt("Apuntado", row, col);
			}

			JOptionPane.showMessageDialog(frame, "Te has apuntado a la clase de " + claseSeleccionada);
		}
	}

	private void agregarClaseSpinningAClasesApuntadas(int row, int col) {
		// Obtener la hora y día desde la tabla de clases disponibles
		String hora = (String) tablaSemanasDisponibles.getValueAt(row, 0);
		String dia = tablaSemanasDisponibles.getColumnName(col);

		// Encontrar la fila correspondiente en la tabla de clases apuntadas
		int filaApuntadas = obtenerFilaHora(hora);

		// Encontrar la columna correspondiente en la tabla de clases apuntadas
		int colApuntadas = obtenerIndiceColumna(dia, (DefaultTableModel) tablaSemanasApuntado.getModel());

		// Añadir la clase "Spinning" a la tabla de clases apuntadas en la misma celda
		tablaSemanasApuntado.setValueAt("Spinning", filaApuntadas, colApuntadas);
	}

	// Método para obtener la fila correspondiente a una hora en la tabla de clases
	// apuntadas
	private int obtenerFilaHora(String hora) {
		DefaultTableModel modelo = (DefaultTableModel) tablaSemanasApuntado.getModel();
		for (int fila = 0; fila < modelo.getRowCount(); fila++) {
			String horaTabla = (String) modelo.getValueAt(fila, 0);
			if (hora.equals(horaTabla)) {
				return fila;
			}
		}
		return -1; // Devolver -1 si no se encuentra la fila
	}

	// Método para obtener el índice de la columna dado el nombre
	private int obtenerIndiceColumna(String nombreColumna, DefaultTableModel modeloTabla) {
		for (int i = 0; i < modeloTabla.getColumnCount(); i++) {
			if (modeloTabla.getColumnName(i).equals(nombreColumna)) {
				return i;
			}
		}
		return -1; // Devolver -1 si no se encuentra la columna
	}

	private void mostrarDialogoReservaExitosa(String entrenamientoSeleccionado) {
		JOptionPane.showMessageDialog(frame,
				"Tu reserva de " + entrenamientoSeleccionado + " se ha realizado con éxito.");
	}

	public void mostrarVentana() {
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		VentanaUsuario ventanaUsuario = new VentanaUsuario("UsuarioPrueba");
		ventanaUsuario.mostrarVentana();
	}
}
