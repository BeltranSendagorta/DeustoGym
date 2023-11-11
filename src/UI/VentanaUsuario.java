package UI;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
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

		JTabbedPane tabbedPane = new JTabbedPane();

		JPanel panelApuntado = new JPanel(new BorderLayout());
		tablaSemanasApuntado = crearTabla();
		panelApuntado.add(new JScrollPane(tablaSemanasApuntado), BorderLayout.CENTER);

		JPanel panelDisponibles = new JPanel(new BorderLayout());
		tablaSemanasDisponibles = crearTablaConBotones();
		panelDisponibles.add(new JScrollPane(tablaSemanasDisponibles), BorderLayout.CENTER);

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

		for (int i = 9; i <= 20; i++) {
			modeloTabla.addRow(new Object[] { i + ":00", null, null, null, null, null, null, null });
		}

		modeloTabla.setColumnIdentifiers(
				new Object[] { "Hora", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo" });

		tabla.setModel(modeloTabla);
		tabla.setGridColor(Color.BLACK);
		tabla.setShowGrid(true);
		tabla.setCellSelectionEnabled(true);

		tabla.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int row = tabla.rowAtPoint(evt.getPoint());
				int col = tabla.columnAtPoint(evt.getPoint());

				if (row >= 0 && col > 0) {
					String claseSeleccionada = (String) modeloTabla.getValueAt(row, col);
					if (claseSeleccionada != null) {
						mostrarDialogoApuntarse(claseSeleccionada, modeloTabla, row, col);
					}
				}
			}
		});

		return tabla;
		
	}
	
	private String[][] actividadesDisponibles = {
	        {"Spinning", "Yoga", "Boxeo", "Aeroyoga", "Pilates", "Spinning", "Boxeo"},
	        {"Yoga", "Pilates", "HIIt", "Entrenamiento funcional", "Spinning", "Yoga", "Pilates"},
	        {"Boxeo", "HIIt", "Entrenamiento funcional", "Spinning", "Yoga", "Boxeo", "HIIt"},
	        {"Aeroyoga", "Entrenamiento funcional", "Spinning", "Yoga", "Boxeo", "Aeroyoga", "Entrenamiento funcional"},
	        {"Pilates", "Spinning", "Yoga", "Boxeo", "Aeroyoga", "Pilates", "Spinning"},
	        {"HIIt", "Boxeo", "Aeroyoga", "Pilates", "Spinning", "HIIt", "Boxeo"},
	        {"Entrenamiento funcional", "Aeroyoga", "Pilates", "Spinning", "Yoga", "Entrenamiento funcional", "Aeroyoga"},
	        {"Spinning", "Yoga", "Boxeo", "Aeroyoga", "Pilates", "", ""},
	        {"Yoga", "Pilates", "HIIt", "Entrenamiento funcional", "Spinning", "", ""},
	        {"Boxeo", "HIIt", "Entrenamiento funcional", "Spinning", "Yoga", "", ""},
	        {"Aeroyoga", "Entrenamiento funcional", "Spinning", "Yoga", "Boxeo", "", ""},
	        {"Pilates", "Spinning", "Yoga", "Boxeo", "Aeroyoga", "", ""}
	};

	
	
	

	private JTable crearTablaConBotones() {
	    JTable tabla = new JTable();
	    DefaultTableModel modeloTabla = new DefaultTableModel(0, 8) {
	        @Override
	        public boolean isCellEditable(int row, int column) {
	            return false;
	        }
	    };

	    modeloTabla.setColumnIdentifiers(new Object[]{"Hora", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"});

	    // Llenar la tabla con las actividades disponibles
	    for (int i = 0; i < actividadesDisponibles.length; i++) {
	        Object[] fila = new Object[8];
	        fila[0] = i + 9 + ":00";
	        System.arraycopy(actividadesDisponibles[i], 0, fila, 1, actividadesDisponibles[i].length);
	        modeloTabla.addRow(fila);
	    }

	    tabla.setModel(modeloTabla);
	    tabla.setGridColor(Color.BLACK);
	    tabla.setShowGrid(true);
	    tabla.setCellSelectionEnabled(true);

	    return tabla;
	}


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
		String[] opciones = { "Sí", "No" };
		int seleccion = JOptionPane.showOptionDialog(frame,
				"¿Quieres apuntarte a la clase de " + claseSeleccionada + "?", "Apuntarse a Clase",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

		if (seleccion == JOptionPane.YES_OPTION) {
			modeloLista.addElement(claseSeleccionada);

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

		String hora = (String) tablaSemanasDisponibles.getValueAt(row, 0);
		String dia = tablaSemanasDisponibles.getColumnName(col);

		int filaApuntadas = obtenerFilaHora(hora);

		int colApuntadas = obtenerIndiceColumna(dia, (DefaultTableModel) tablaSemanasApuntado.getModel());

		tablaSemanasApuntado.setValueAt("Spinning", filaApuntadas, colApuntadas);
	}

	private int obtenerFilaHora(String hora) {
		DefaultTableModel modelo = (DefaultTableModel) tablaSemanasApuntado.getModel();
		for (int fila = 0; fila < modelo.getRowCount(); fila++) {
			String horaTabla = (String) modelo.getValueAt(fila, 0);
			if (hora.equals(horaTabla)) {
				return fila;
			}
		}
		return -1;
	}

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