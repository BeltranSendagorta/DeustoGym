package UI;

import DeustoGym.DeustoGym;
import DeustoGym.TablaClasesDisponibles;

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
    private DeustoGym deustoGym;
    private TablaClasesDisponibles tablaClasesDisponibles;

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
        tablaClasesDisponibles = new TablaClasesDisponibles();
        tablaSemanasDisponibles = tablaClasesDisponibles.crearClasesDisponibles(new String[0][0]);
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

        tablaSemanasDisponibles.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = tablaSemanasDisponibles.rowAtPoint(evt.getPoint());
                int col = tablaSemanasDisponibles.columnAtPoint(evt.getPoint());

                if (row > 0 && col > 0) {
                    String claseSeleccionada = (String) tablaSemanasDisponibles.getValueAt(row, col);
                    if (claseSeleccionada != null) {
                        mostrarDialogoApuntarse(claseSeleccionada,
                                (DefaultTableModel) tablaSemanasDisponibles.getModel(), row, col);
                    }
                }
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
