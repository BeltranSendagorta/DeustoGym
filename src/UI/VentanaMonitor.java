package UI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        JPanel panelClases = new JPanel();
        panelClases.setLayout(new BoxLayout(panelClases, BoxLayout.Y_AXIS));
        String[] clases = {"Spinning", "Yoga", "Core"};
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
        frame.add(panelClases, BorderLayout.EAST);
        
        // Ajuste de ancho y altura
        panelClases.setPreferredSize(new Dimension(200, frame.getHeight())); // Ancho de 150

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

        for (int i = 9; i <= 20; i++) {
            modeloTabla.addRow(new Object[] { i + ":00", null, null, null, null, null, null, null });
        }

        modeloTabla.setColumnIdentifiers(new Object[] { "Hora", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo" });

        agregarBoton(modeloTabla, "Martes", 10, "Martes");
        agregarBoton(modeloTabla, "Jueves", 10, "Jueves");

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
                    Object contenidoCelda = modeloTabla.getValueAt(row, col);
                    if (contenidoCelda instanceof JButton) {
                        ((JButton) contenidoCelda).doClick();
                    }
                }
            }
        });

        return tabla;
    }

    private void agregarBoton(DefaultTableModel modeloTabla, String nombreColumna, int fila, String dia) {
        int indiceColumna = obtenerIndiceColumna(nombreColumna, modeloTabla);
        if (indiceColumna != -1) {
            JButton boton = new JButton("Spinning");
            
            boton.addActionListener(e -> mostrarDialogoApuntarse("Spinning", modeloTabla, fila, indiceColumna));
            modeloTabla.setValueAt(boton, fila, indiceColumna);
            spinningDias.put(boton, dia);
        } else {
            System.err.println("La columna '" + nombreColumna + "' no se encontró.");
        }
    }
    
    private void mostrarMaterialNecesario(String clase) {

        String materialNecesario = "Material necesario para " + clase + ":\n\n";
        if ("Spinning".equals(clase)) {
            materialNecesario += "Bicicleta estática\nToalla\nBotella de agua";
        } else if ("Yoga".equals(clase)) {

        } else if ("Core".equals(clase)) {
        }
        JOptionPane.showMessageDialog(frame, materialNecesario, "Material Necesario", JOptionPane.INFORMATION_MESSAGE);
    }
 
    private void mostrarDialogoApuntarse(String claseSeleccionada, DefaultTableModel modeloTabla, int row, int col) {
        String[] opciones = { "Sí", "No" };
        int seleccion = JOptionPane.showOptionDialog(frame,
                "¿Quieres apuntarte a la clase de " + claseSeleccionada + "?", "Apuntarse a Clase",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

        if (seleccion == JOptionPane.YES_OPTION) {
            modeloLista.addElement(claseSeleccionada);
            if ("Spinning".equals(claseSeleccionada)) {
                modeloTabla.setValueAt("Spinning", row, col);
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

        // Actualiza las clases por día
        if (!clasesPorDia.containsKey(dia)) {
            clasesPorDia.put(dia, new ArrayList<>());
        }
        clasesPorDia.get(dia).add("Spinning");
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
