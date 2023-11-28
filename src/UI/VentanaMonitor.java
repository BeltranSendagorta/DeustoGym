package UI;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class VentanaMonitor {
    private JFrame frame;
    private DefaultListModel<String> modeloLista;
    private JList<String> listaActividades;
    private JTable tablaSemanasApuntado;
    private JTable tablaSemanasDisponibles;
    private Map<JButton, String> spinningDias = new HashMap<>();
    private Map<String, List<String>> clasesPorDia = new HashMap<>();
    private Map<JLabel, String> clasesLabels = new HashMap<>();
    
    
    public VentanaMonitor(String nombreMonitor) {
        frame = new JFrame("Ventana de Monitor");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setLayout(new BorderLayout());

        JLabel perfilLabel = new JLabel("Perfil del Monitor: " + nombreMonitor);
        perfilLabel.setHorizontalAlignment(JLabel.LEFT);
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
        frame.add(scrollLista, BorderLayout.WEST);
        


		JPanel panelClases = new JPanel();
		panelClases.setLayout(new BoxLayout(panelClases, BoxLayout.Y_AXIS));
		String[] clases = {"Spinning", "Yoga", "Core","Boxeo", "Aeroyoga", "Pilates","Hit","Funcional"};
		for (String clase : clases) {
		    JLabel labelClase = new JLabel(clase);
		    labelClase.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		    labelClase.addMouseListener(new MouseAdapter() {
		        @Override
		        public void mouseClicked(MouseEvent e) {
		            mostrarMaterialNecesario(clase);
		        }
		    });
		    panelClases.add(labelClase);
		    clasesLabels.put(labelClase, clase);
		}

		// Ajuste de ancho y altura
		panelClases.setPreferredSize(new Dimension(200, frame.getHeight())); // Ajusta el ancho a 250 (o el valor que desees)

		frame.add(panelClases, BorderLayout.WEST);

        frame.setVisible(true);
        
    }

    public JTable crearTablaClasesApuntadas() {
		JTable tabla = new JTable();
		DefaultTableModel modeloTabla = new DefaultTableModel(0, 7) {
			@Override
			public boolean isCellEditable(int row, int column) {
				
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
				new Object[] { "Hora", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo" });

		try (BufferedReader br = new BufferedReader(new FileReader("Horario2023.csv"))) {
			String line;
			while ((line = br.readLine()) != null) {
				StringTokenizer tokenizer = new StringTokenizer(line, ",");
				String hora = tokenizer.nextToken();
				String[] clases = new String[7];

				for (int i = 0; i < clases.length && tokenizer.hasMoreTokens(); i++) {
					clases[i] = tokenizer.nextToken();
				}

				modeloTabla.addRow(new Object[] { hora, clases[0], clases[1], clases[2], clases[3], clases[4],
						clases[5], clases[6] });
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
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

				String actividad = (value != null) ? value.toString() : "";

				ImageIcon icono;

				switch (actividad) {
				case "Yoga":
					c.setBackground(Color.PINK);
					icono = resizeImage("img/yoga.png");
					break;
				case "Spinning":
					c.setBackground(Color.GREEN);
					icono = resizeImage("img/spinning.png");
					break;
				case "Core":
					c.setBackground(Color.YELLOW);
					icono = resizeImage("img/core.png");
					break;
				case "Boxeo":
					c.setBackground(new Color(128, 191, 255));
					icono = resizeImage("img/boxeo.png");
					break;
				case "Aeroyoga":
					c.setBackground(Color.YELLOW);
					icono = resizeImage("img/aeroyoga.png");
					break;
				case "Pilates":
					c.setBackground(Color.RED);
					icono = resizeImage("img/pilates.png");
					break;
				case "HIIt":
					c.setBackground(Color.GRAY);
					icono = resizeImage("img/hiit.png");
					break;
				case "Funcional":
					c.setBackground(new Color(139, 69, 19)); // Marrón
					icono = resizeImage("img/funcional.png");
					break;

				default:
					c.setBackground(table.getBackground());
					icono = null;
				}

				if (icono != null) {
					JLabel label = new JLabel();
					label.setHorizontalAlignment(JLabel.CENTER);
					label.setIcon(icono);
					label.setOpaque(true);
					label.setBackground(table.getBackground());
					return label;
				}

				return c;
			}

			private ImageIcon resizeImage(String imagePath) {
				ImageIcon originalIcon = new ImageIcon(imagePath);
				Image image = originalIcon.getImage();
				Image resizedImage = image.getScaledInstance(35, 20, java.awt.Image.SCALE_SMOOTH);
				return new ImageIcon(resizedImage);
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

    private void agregarClaseAClasesApuntadas(String claseSeleccionada, int row, int col) {
		String hora = (String) tablaSemanasDisponibles.getValueAt(row, 0);
		String dia = tablaSemanasDisponibles.getColumnName(col);

		int filaDisponibles = obtenerFilaHora(hora);

		if (tablaSemanasApuntado.getValueAt(filaDisponibles, col) != null) {
			String claseDesapuntada = (String) tablaSemanasApuntado.getValueAt(filaDisponibles, col);
			mostrarDialogoDesapuntarse(claseDesapuntada, (DefaultTableModel) tablaSemanasApuntado.getModel(),
					filaDisponibles, col);
		}

		
		tablaSemanasApuntado.setValueAt(claseSeleccionada, filaDisponibles, col);
	}
    
    public void mostrarMaterialNecesario(String clase) {
	    String materialMonitor = "Material necesario para " + clase + ":\n\n";

	    switch (clase) {
	        case "Spinning":
	            materialMonitor += "Bicicletas estáticas\nToallas\nBotellas de agua";
	            break;
	        case "Yoga":
	            materialMonitor += "Tapetes de yoga\nBloques\nCinturones";
	            break;
	        case "Core":
	            materialMonitor += "Colchonetas\nPesas ligeras\nBandas elásticas";
	            break;
	        case "Boxeo":
	            materialMonitor += "Guantes de boxeo\nSacos de boxeo\nVendas";
	            break;
	        case "Aeroyoga":
	            materialMonitor += "Hamacas de yoga aéreo\nAlmohadas\nGanchos de montaje";
	            break;
	        case "Pilates":
	            materialMonitor += "Pelotas de pilates\nBandas elásticas\nRodillos";
	            break;
	        case "Hit":
	            materialMonitor += "Conos de entrenamiento\nCuerdas para saltar\nCronómetros";
	            break;
	        case "Funcional":
	            materialMonitor += "Kettlebells\nBarras\nPlataformas de salto";
	            break;
	        // Puedes añadir más casos para otras clases si es necesario
	        default:
	            materialMonitor += "Información no disponible";
	            break;
	    }

	    JOptionPane.showMessageDialog(frame, materialMonitor, "Material Necesario para el Monitor", JOptionPane.INFORMATION_MESSAGE);
	}
 
    public void mostrarDialogoApuntarse(String claseSeleccionada, DefaultTableModel modeloTabla, int row, int col) {
	    String hora = (String) modeloTabla.getValueAt(row, 0);
	    String dia = modeloTabla.getColumnName(col);

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
    private void mostrarDialogoDesapuntarse(String claseSeleccionada, DefaultTableModel modeloTabla, int row, int col) {
		String[] opciones = { "Sí", "No" };
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

     public void mostrarDialogoReservaExitosa(String entrenamientoSeleccionado) {
		JOptionPane.showMessageDialog(frame,
				"Tu reserva de " + entrenamientoSeleccionado + " se ha realizado con éxito.");
	}
    public int obtenerIndiceColumna(String nombreColumna, DefaultTableModel modeloTabla) {
        for (int i = 0; i < modeloTabla.getColumnCount(); i++) {
            if (modeloTabla.getColumnName(i).equals(nombreColumna)) {
                return i;
            }
        }
        return -1;
    }
    public void mostrarVentana() {
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        VentanaMonitor ventanaMonitor = new VentanaMonitor("MonitorPrueba");
        ventanaMonitor.mostrarVentana();
    }
}
