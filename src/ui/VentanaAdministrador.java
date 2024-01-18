package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import bd.BaseDatos;
import domain.Administrador;
import domain.Entrenamiento;
import domain.Factura;
import domain.Monitor;
import domain.Persona;
import domain.Usuario;
import io.DeustoGym;

public class VentanaAdministrador {
	private JFrame frame;
	private DefaultListModel<Factura> modeloLista;
	private JTable tablaSemanasApuntado;
	public Map<String, Integer> gananciasProfesores;
	
	public VentanaAdministrador(Administrador a) {
		frame = new JFrame("Ventana de Administrador");
		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setLayout(new BorderLayout());

		JLabel perfilLabel = new JLabel("Perfil del Administrador: " + a.getNombre());
		perfilLabel.setHorizontalAlignment(JLabel.RIGHT);
		perfilLabel.setForeground(Color.BLACK);
		frame.add(perfilLabel, BorderLayout.NORTH);

		JPanel panelApuntado = new JPanel(new BorderLayout());
		tablaSemanasApuntado = crearTabla();
		panelApuntado.add(new JScrollPane(tablaSemanasApuntado), BorderLayout.CENTER);
		frame.add(panelApuntado, BorderLayout.CENTER);
		for(Persona u: BaseDatos.personas.values()) {
			if(u instanceof Usuario)
				((Usuario)u).crearFactura();
		}
		modeloLista = new DefaultListModel<>();
		for(Factura f : DeustoGym.listF) {
			modeloLista.addElement(f);
		}
		JList<Factura> listaActividades = new JList<>(modeloLista);
		listaActividades.setBackground(new Color(240, 240, 240));
		JScrollPane scrollLista = new JScrollPane(listaActividades);
		frame.add(scrollLista, BorderLayout.EAST);


		tablaSemanasApuntado.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				int row = tablaSemanasApuntado.rowAtPoint(evt.getPoint());
				int col = tablaSemanasApuntado.columnAtPoint(evt.getPoint());

				if (row >= 0 && col > 0) {
					Miclase cellValue = (Miclase) tablaSemanasApuntado.getValueAt(row, col);
					if (cellValue != null) {
						for(Entrenamiento e:cellValue.getLista()) {
							mostrarDialogoClase(e);
						}
					}
				}
			}
		});
		JButton botonC = new JButton("Cerrar Sesion");
        botonC.addActionListener(e ->{
            frame.dispose();
            VentanaSesion ventanaSesion = new VentanaSesion();
            ventanaSesion.mostrarVentana();
        });

        // Agregar el botón al sur de la ventana
        frame.add(botonC, BorderLayout.SOUTH);
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

		DefaultTableModel modeloTabla = new DefaultTableModel(0, 8){
			@Override
			public boolean isCellEditable(int row, int column) {
				return row > 0;
			}
		};
		Miclase lunes=new Miclase();
		Miclase martes=new Miclase();
		Miclase miercoles=new Miclase();
		Miclase jueves=new Miclase();
		Miclase viernes=new Miclase();
		Miclase sabado=new Miclase();
		Miclase domingo=new Miclase();
		for (int i = 8; i <= 21; i++) {
			lunes=new Miclase();
			martes=new Miclase();
			miercoles=new Miclase();
			jueves=new Miclase();
			viernes=new Miclase();
			sabado=new Miclase();
			domingo=new Miclase();
			for(Entrenamiento e: BaseDatos.entrenamientos) {
				if(e.getHoraInicio().equals((i< 10 ? "0" : "")+i+":00")) {
					switch (e.getdiaSe()) {
					case "Lunes": {
						lunes.getLista().add(e);
						break;
					}case "Martes": {
						martes.getLista().add(e);
						break;
					}case "Miercoles": {
						miercoles.getLista().add(e);
						break;
					}case "Jueves": {
						jueves.getLista().add(e);
						break;
					}case "Viernes": {
						viernes.getLista().add(e);
						break;
					}case "Sabado": {
						sabado.getLista().add(e);
						break;
					}case "Domingo": {
						domingo.getLista().add(e);
						break;
					}
					default:
						throw new IllegalArgumentException("Unexpected value: " + e.getdiaSe());
					}
				}
			}
			modeloTabla.addRow(new Object[] { i + ":00", lunes, martes, miercoles, jueves, viernes, sabado, domingo});
		}
		modeloTabla.setColumnIdentifiers(
				new Object[] { "Hora", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo" });

		tabla.setModel(modeloTabla);
		tabla.setGridColor(Color.BLACK);
		tabla.setShowGrid(true);
		tabla.setCellSelectionEnabled(true);
		return tabla;
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

	public void mostrarDialogoClase(Entrenamiento e) {
		Monitor profesor = e.getMonitor();
		String usuariosApuntados = "";
		for(Usuario u : e.getAsistentes()) {
			usuariosApuntados+=u.getNombre()+", ";
		}
		JOptionPane.showMessageDialog(frame, "Clase: " + e + "\nProfesor: " + profesor
				+ "\nUsuarios Apuntados: " + usuariosApuntados);
	}

	public void mostrarVentana() {
		frame.setVisible(true);
	}
}