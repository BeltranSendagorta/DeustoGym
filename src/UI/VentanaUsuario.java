package UI;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import java.net.URL;

public class VentanaUsuario {
	public JFrame frame;
	private DefaultListModel<String> modeloLista;
	private JList<String> listaActividades;
	private JTable tablaSemanasApuntado;
	private JTable tablaSemanasDisponibles;
	private String[][] actividadesDisponibles = {
			{ "Spinning", "Yoga", "Boxeo", "Aeroyoga", "Pilates", "Spinning", "Boxeo" },
			{ "Yoga", "Pilates", "HIIt", "Entrenamiento funcional", "Spinning", "Yoga", "Pilates" },
			{ "Boxeo", "HIIt", "Entrenamiento funcional", "Spinning", "Yoga", "Boxeo", "HIIt" },
			{ "Aeroyoga", "Entrenamiento funcional", "Spinning", "Yoga", "Boxeo", "Aeroyoga",
					"Entrenamiento funcional" },
			{ "Pilates", "Spinning", "Yoga", "Boxeo", "Aeroyoga", "Pilates", "Spinning" },
			{ "HIIt", "Boxeo", "Aeroyoga", "Pilates", "Spinning", "HIIt", "Boxeo" },
			{ "Entrenamiento funcional", "Aeroyoga", "Pilates", "Spinning", "Yoga", "Entrenamiento funcional",
					"Aeroyoga" },
			{ "Spinning", "Yoga", "Boxeo", "Aeroyoga", "Pilates", "", "" },
			{ "Yoga", "Pilates", "HIIt", "Entrenamiento funcional", "Spinning", "", "" },
			{ "Boxeo", "HIIt", "Entrenamiento funcional", "Spinning", "Yoga", "", "" },
			{ "Aeroyoga", "Entrenamiento funcional", "Spinning", "Yoga", "Boxeo", "", "" },
			{ "Pilates", "Spinning", "Yoga", "Boxeo", "Aeroyoga", "", "" } };

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
		tablaSemanasApuntado = crearTablaClasesApuntadas();
		panelApuntado.add(new JScrollPane(tablaSemanasApuntado), BorderLayout.CENTER);

		JPanel panelDisponibles = new JPanel(new BorderLayout());
		tablaSemanasDisponibles = crearTablaClasesDisponibles();
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

	public JTable crearTablaClasesApuntadas() {
		JTable tabla = new JTable();
		DefaultTableModel modeloTabla = new DefaultTableModel(0, 7) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// Solo permite la edición de celdas que no están en la primera fila
				return row > 0;
			}
		};

		modeloTabla.setColumnIdentifiers(
				new Object[] { "Hora", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo" });

		for (int i = 9; i <= 20; i++) {
			modeloTabla.addRow(new Object[] { i + ":00", null, null, null, null, null, null, null });
		}

		tabla.setModel(modeloTabla);

		tabla.getTableHeader().setReorderingAllowed(false);

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
						if ("Apuntado".equals(claseSeleccionada)) {
							mostrarDialogoDesapuntarse(claseSeleccionada, modeloTabla, row, col);
						} else {
							mostrarDialogoApuntarse(claseSeleccionada, modeloTabla, row, col);
						}
					}
				}
			}
		});

		return tabla;
	}
	
	public JTable getTablaClasesApuntadas() {
	    return tablaSemanasApuntado;
	}
	
	

	public JTable crearTablaClasesDisponibles() {
	    JTable tabla = new JTable();
	    DefaultTableModel modeloTabla = new DefaultTableModel(0, 7) {
	        @Override
	        public boolean isCellEditable(int row, int column) {
	            // Solo permite la edición de celdas que no están en la primera fila
	            return row > 0;
	        }
	    };

	    modeloTabla.setColumnIdentifiers(
	            new Object[]{"Hora", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"});

	    try (BufferedReader br = new BufferedReader(new FileReader("Horario2023.csv"))) {
	        String line;
	        while ((line = br.readLine()) != null) {
	            StringTokenizer tokenizer = new StringTokenizer(line, ",");
	            String hora = tokenizer.nextToken();
	            String[] clases = new String[7];

	            for (int i = 0; i < clases.length && tokenizer.hasMoreTokens(); i++) {
	                clases[i] = tokenizer.nextToken();
	            }

	            modeloTabla.addRow(new Object[]{hora, clases[0], clases[1], clases[2], clases[3], clases[4],
	                    clases[5], clases[6]});
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    tabla.setModel(modeloTabla);

	    tabla.getTableHeader().setReorderingAllowed(false);
	    tabla.setTableHeader(null);

	    tabla.setGridColor(Color.BLACK);
	    tabla.setShowGrid(true);
	    tabla.setCellSelectionEnabled(true);

	    DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
	        private final JLabel label = new JLabel();

	        @Override
	        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
	                                                       boolean hasFocus, int row, int column) {
	            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

	            String actividad = (value != null) ? value.toString() : "";
	            ImageIcon icono = null;

	            // Asignar colores según la actividad
	            switch (actividad) {
	                case "Yoga":
	                    c.setBackground(Color.PINK);
	                    break;
	                
	                case "Spinning":
	                    c.setBackground(Color.GREEN);
	                    break;
	                case "Core":
	                    c.setBackground(Color.YELLOW);
	                    break;
	                case "Boxeo":
	                    c.setBackground(new Color(128, 191, 255));
	                    break;
	                case "Aeroyoga":
	                    c.setBackground(Color.YELLOW);
	                    icono = new ImageIcon("img/aeroyoga.png");
	                    JLabel l = new JLabel();
	                    l.setHorizontalAlignment(JLabel.CENTER);
	                    l.setIcon(icono);
	                    return l;
	                    
	                case "Pilates":
	                    c.setBackground(Color.RED);
	                    break;
	                case "HIIt":
	                    c.setBackground(Color.GRAY);
	                    break;
	                case "Funcional":
	                    c.setBackground(new Color(139, 69, 19)); // Marrón
	                    break;
	                default:
	                    c.setBackground(table.getBackground());
	            }
				return c;

	           
	    }
	};

	    for (int i = 0; i < modeloTabla.getColumnCount(); i++) {
	        tabla.getColumnModel().getColumn(i).setCellRenderer(renderer);
	    }

	    tabla.addMouseListener(new java.awt.event.MouseAdapter() {

	@Override
	public void mouseClicked(java.awt.event.MouseEvent evt) {
		int row = tabla.rowAtPoint(evt.getPoint());
		int col = tabla.columnAtPoint(evt.getPoint());

		if (row > 0 && col > 0) {
			String claseSeleccionada = (String) modeloTabla.getValueAt(row, col);
			if (claseSeleccionada != null) {
				mostrarDialogoApuntarse(claseSeleccionada, modeloTabla, row, col);
			}
		}
	}

	});

	return tabla;
	}
	
	public JTable getTablaClasesDisponibles() {
	    return tablaSemanasDisponibles;
	}
	


	public void agregarClaseAClasesApuntadas(String claseSeleccionada, int row, int col) {
	    String hora = (String) tablaSemanasDisponibles.getValueAt(row, 0);
	    String dia = tablaSemanasDisponibles.getColumnName(col);

	    int filaDisponibles = obtenerFilaHora(hora);

	    if (tablaSemanasApuntado.getValueAt(filaDisponibles, col) != null) {
	        String claseDesapuntada = (String) tablaSemanasApuntado.getValueAt(filaDisponibles, col);
	        mostrarDialogoDesapuntarse(claseDesapuntada, (DefaultTableModel) tablaSemanasApuntado.getModel(),
	                filaDisponibles, col);
	    }

	    modeloLista.addElement(claseSeleccionada);
	    tablaSemanasApuntado.setValueAt(claseSeleccionada, filaDisponibles, col);
	}



	public void mostrarDialogoApuntarse(String claseSeleccionada, DefaultTableModel modeloTabla, int row, int col) {
		String hora = (String) modeloTabla.getValueAt(row, 0);
		String dia = modeloTabla.getColumnName(col);

		String rutaIcono = "ruta/de/tu/carpeta/img/logo.ico";
		ImageIcon icono = new ImageIcon(rutaIcono);

		if (modeloTabla == tablaSemanasApuntado.getModel()) {
			mostrarDialogoDesapuntarse(claseSeleccionada, modeloTabla, row, col);
		} else {
			String[] opciones = { "Sí", "No" };
			int seleccion = JOptionPane.showOptionDialog(frame,
					"¿Quieres apuntarte a la clase de " + claseSeleccionada + "?", "Apuntarse a Clase",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

			if (seleccion == JOptionPane.YES_OPTION) {
				agregarClaseAClasesApuntadas(claseSeleccionada, row, col);
				JOptionPane.showMessageDialog(frame, "Te has apuntado a la clase de " + claseSeleccionada);
			}
		}
	}

	public void mostrarDialogoDesapuntarse(String claseSeleccionada, DefaultTableModel modeloTabla, int row, int col) {
		String[] opciones = { "Sí", "No" };

		String rutaIcono = "ruta/de/tu/carpeta/img/logo.ico";
		ImageIcon icono = new ImageIcon(rutaIcono);

		int seleccion = JOptionPane.showOptionDialog(frame,
				"¿Quieres desapuntarte de la clase de " + claseSeleccionada + "?", "Desapuntarse de Clase",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

		if (seleccion == JOptionPane.YES_OPTION) {

			String hora = (String) modeloTabla.getValueAt(row, 0);
			String dia = modeloTabla.getColumnName(col);

			modeloLista.removeElement(claseSeleccionada);
			modeloTabla.setValueAt(null, row, col);

			int filaDisponibles = obtenerFilaHora(hora);

			int colDisponibles = obtenerIndiceColumna(dia, (DefaultTableModel) tablaSemanasDisponibles.getModel());

			tablaSemanasDisponibles.setValueAt(claseSeleccionada, filaDisponibles, colDisponibles);

			JOptionPane.showMessageDialog(frame, "Te has desapuntado de la clase de " + claseSeleccionada);
		}
	}

	public int obtenerFilaHora(String hora) {
		DefaultTableModel modelo = (DefaultTableModel) tablaSemanasApuntado.getModel();
		for (int fila = 0; fila < modelo.getRowCount(); fila++) {
			String horaTabla = (String) modelo.getValueAt(fila, 0);
			if (hora.equals(horaTabla)) {
				return fila;
			}
		}
		return -1;
	}

	public int obtenerIndiceColumna(String nombreColumna, DefaultTableModel modeloTabla) {
		for (int i = 0; i < modeloTabla.getColumnCount(); i++) {
			if (modeloTabla.getColumnName(i).equals(nombreColumna)) {
				return i;
			}
		}
		return -1;
	}

	public void mostrarDialogoReservaExitosa(String entrenamientoSeleccionado) {
		JOptionPane.showMessageDialog(frame,
				"Tu reserva de " + entrenamientoSeleccionado + " se ha realizado con éxito.");
	}

	public void mostrarVentana() {
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			VentanaUsuario ventanaUsuario = new VentanaUsuario("UsuarioPrueba");
			ventanaUsuario.mostrarVentana();
		});
	}
}
