package UI;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class VentanaAdministrador {
	private JFrame frame;
	private DefaultListModel<String> modeloLista;
	private JTable tablaSemanasApuntado;
	public Map<String, Integer> gananciasProfesores;

	public VentanaAdministrador(String nombreAdministrador) {
		frame = new JFrame("Ventana de Administrador");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setLayout(new BorderLayout());

        // Crear botones para inscribir y desapuntar
        JButton inscribirButton = new JButton("Inscribir");
        JButton desapuntarButton = new JButton("Desapuntar");

        // Agregar listeners a los botones
        inscribirButton.addActionListener(e -> mostrarDialogoInscribir());
        desapuntarButton.addActionListener(e -> mostrarDialogoDesapuntar());

        // Crear panel para botones
        JPanel panelBotones = new JPanel();
        panelBotones.add(inscribirButton);
        panelBotones.add(desapuntarButton);

        // Panel superior con el perfil y los botones
        JPanel panelSuperior = new JPanel(new BorderLayout());
        JLabel perfilLabel = new JLabel("Perfil del Administrador: " + nombreAdministrador);
        perfilLabel.setHorizontalAlignment(JLabel.RIGHT);
        perfilLabel.setForeground(Color.BLACK);
        panelSuperior.add(perfilLabel, BorderLayout.NORTH);
        panelSuperior.add(panelBotones, BorderLayout.SOUTH);

        // Panel central con la tabla
        JPanel panelCentral = new JPanel(new BorderLayout());
        tablaSemanasApuntado = crearTabla();
        panelCentral.add(new JScrollPane(tablaSemanasApuntado), BorderLayout.CENTER);

        // Panel inferior con las ganancias
        JPanel panelInferior = new JPanel(new BorderLayout());
        JTextArea gananciasTextArea = new JTextArea();
        gananciasTextArea.setEditable(false);
        panelInferior.add(new JScrollPane(gananciasTextArea), BorderLayout.CENTER);

        // Panel lateral con la lista de actividades
        modeloLista = new DefaultListModel<>();
        JList<String> listaActividades = new JList<>(modeloLista);
        listaActividades.setBackground(new Color(240, 240, 240));
        JScrollPane scrollLista = new JScrollPane(listaActividades);
        panelInferior.add(scrollLista, BorderLayout.EAST);

        // Agregar paneles al frame
        frame.add(panelSuperior, BorderLayout.NORTH);
        frame.add(panelCentral, BorderLayout.CENTER);
        frame.add(panelInferior, BorderLayout.SOUTH);

        gananciasProfesores = new HashMap<>();
        calcularGananciasIniciales();

        tablaSemanasApuntado.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                int row = tablaSemanasApuntado.rowAtPoint(evt.getPoint());
                int col = tablaSemanasApuntado.columnAtPoint(evt.getPoint());

                if (row >= 0 && col > 0) {
                    Object cellValue = tablaSemanasApuntado.getValueAt(row, col);
                    if (cellValue != null) {
                        String claseSeleccionada = cellValue.toString();
                        mostrarDialogoClase(claseSeleccionada);
                    }
                }
            }
        });

        frame.setVisible(true);
    }

	public JTable crearTabla() {
		JTable tabla = new JTable() {
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component comp = super.prepareRenderer(renderer, row, column);
				int rendererWidth = comp.getPreferredSize().width;
				TableColumn tableColumn = getColumnModel().getColumn(column);
				tableColumn.setPreferredWidth(100);
				setRowHeight(25);
				return comp;
			}
		};

		DefaultTableModel modeloTabla = new DefaultTableModel(0, 8) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		try (BufferedReader br = new BufferedReader(new FileReader("Horario2023.csv"))) {
			br.readLine();
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

		modeloTabla.setColumnIdentifiers(
				new Object[] { "Hora", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo" });

		tabla.setModel(modeloTabla);
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

		for (int i = 1; i < modeloTabla.getColumnCount(); i++) {
			tabla.getColumnModel().getColumn(i).setCellRenderer(renderer);
		}

		return tabla;
	}

	public void calcularGananciasIniciales() {
		DefaultTableModel modeloTabla = (DefaultTableModel) tablaSemanasApuntado.getModel();

		for (int row = 0; row < modeloTabla.getRowCount(); row++) {
			for (int col = 1; col < modeloTabla.getColumnCount(); col++) {
				Object cellValue = modeloTabla.getValueAt(row, col);
				if (cellValue != null) {
					String profesor = obtenerProfesorDeClase(cellValue.toString());
					int costoClase = 10;
					actualizarGananciasProfesor(profesor, costoClase);
				}
			}
		}

		Set<String> profesoresAsignados = new HashSet<>(gananciasProfesores.keySet());
		Set<String> profesoresEsperados = new HashSet<>(
				Arrays.asList("Koldo", "Peio", "Kepa", "Jose", "Nerea", "Alex", "Beltran", "Maider", "Malen"));
		profesoresEsperados.removeAll(profesoresAsignados);
		for (String profesorFaltante : profesoresEsperados) {
			gananciasProfesores.put(profesorFaltante, 0);
		}

		actualizarLabelGanancias();
	}
	
	

	public String obtenerProfesorDeClase(String clase) {

		Map<String, String> asignacionClasesProfesores = new HashMap<>();
		asignacionClasesProfesores.put("Yoga", "Koldo");
		asignacionClasesProfesores.put("Spinning", "Peio");
		asignacionClasesProfesores.put("Core", "Kepa");
		asignacionClasesProfesores.put("Boxeo", "Jose");
		asignacionClasesProfesores.put("Aeroyoga", "Nerea");
		asignacionClasesProfesores.put("Pilates", "Alex");
		asignacionClasesProfesores.put("HIIt", "Beltran");
		asignacionClasesProfesores.put("Funcional", "Maider");

		return asignacionClasesProfesores.get(clase);
	}

	public void actualizarGananciasProfesor(String profesor, int costoClase) {
		int gananciaProfesor = gananciasProfesores.getOrDefault(profesor, 0);
		gananciaProfesor += costoClase;
		gananciasProfesores.put(profesor, gananciaProfesor);
		actualizarLabelGanancias();
	}

	public void actualizarLabelGanancias() {
		StringBuilder gananciasTexto = new StringBuilder("Ganancias por Profesor:\n");

		for (String profesor : gananciasProfesores.keySet()) {
			int ganancia = gananciasProfesores.get(profesor);
			gananciasTexto.append(profesor).append(": ").append(ganancia).append(" euros\n");
		}

		JTextArea gananciasTextArea = new JTextArea(gananciasTexto.toString());
		gananciasTextArea.setEditable(false);
		frame.add(new JScrollPane(gananciasTextArea), BorderLayout.SOUTH);
		frame.revalidate();
	}

	public String[] generarClasesAleatorias(String[] tiposClase, int numClases) {
		Random random = new Random();
		String[] clasesAleatorias = new String[numClases];
		for (int i = 0; i < numClases; i++) {
			clasesAleatorias[i] = tiposClase[random.nextInt(tiposClase.length)];
		}
		return clasesAleatorias;
	}

	public void mostrarDialogoClase(String claseSeleccionada) {
		String profesor = obtenerProfesorDeClase(claseSeleccionada);
		String usuariosApuntados = generarNombresUsuariosAleatorios();
		JOptionPane.showMessageDialog(frame, "Clase: " + claseSeleccionada + "\nProfesor: " + profesor
				+ "\nUsuarios Apuntados: " + usuariosApuntados);
	}

	public String generarNombresUsuariosAleatorios() {
		String[] nombres = { "Iker", "Aitor", "Ander", "Eneko", "Kerman", "Iñigo", "Javier", "Alejandro", "Beñat",
				"Oscar", "Xabier", "Roberto" };
		int numUsuarios = new Random().nextInt(5) + 1;
		StringBuilder usuarios = new StringBuilder();
		for (int i = 0; i < numUsuarios; i++) {
			usuarios.append(nombres[new Random().nextInt(nombres.length)]);
			if (i < numUsuarios - 1) {
				usuarios.append(", ");
			}
		}
		return usuarios.toString();
	}
	
	private void mostrarDialogoInscribir() {
        String claseSeleccionada = obtenerClaseSeleccionada();
        if (claseSeleccionada != null) {
            String nombreAlumno = JOptionPane.showInputDialog(frame, "Ingrese el nombre del alumno:");
            if (nombreAlumno != null && !nombreAlumno.isEmpty()) {
                inscribirAlumno(claseSeleccionada, nombreAlumno);
            }
        }
    }
	
	private void mostrarDialogoDesapuntar() {
        String claseSeleccionada = obtenerClaseSeleccionada();
        if (claseSeleccionada != null) {
            String nombreAlumno = JOptionPane.showInputDialog(frame, "Ingrese el nombre del alumno a desapuntar:");
            if (nombreAlumno != null && !nombreAlumno.isEmpty()) {
                desapuntarAlumno(claseSeleccionada, nombreAlumno);
            }
        }
    }
	
	private String obtenerClaseSeleccionada() {
        int row = tablaSemanasApuntado.getSelectedRow();
        int col = tablaSemanasApuntado.getSelectedColumn();

        if (row >= 0 && col > 0) {
            Object cellValue = tablaSemanasApuntado.getValueAt(row, col);
            if (cellValue != null) {
                return cellValue.toString();
            }
        }

        return null;
    }
	
	 private void inscribirAlumno(String claseSeleccionada, String nombreAlumno) {
	        // Aquí puedes implementar la lógica para inscribir al alumno en la clase seleccionada.
	        // Puedes actualizar la tabla, el modelo de lista, etc.

	        JOptionPane.showMessageDialog(frame, "Alumno " + nombreAlumno + " inscrito en " + claseSeleccionada);
	    }

	    private void desapuntarAlumno(String claseSeleccionada, String nombreAlumno) {
	        // Aquí puedes implementar la lógica para desapuntar al alumno de la clase seleccionada.
	        // Puedes actualizar la tabla, el modelo de lista, etc.

	        JOptionPane.showMessageDialog(frame, "Alumno " + nombreAlumno + " desapuntado de " + claseSeleccionada);
	    }

	public void mostrarVentana() {
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			VentanaAdministrador ventanaAdministrador = new VentanaAdministrador("AdministradorPrueba");
			ventanaAdministrador.mostrarVentana();
		});
	}
}
