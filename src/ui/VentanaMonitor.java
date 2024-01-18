package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import bd.BaseDatos;
import domain.Entrenamiento;
import domain.Monitor;
import domain.Usuario;

public class VentanaMonitor {
    private JFrame frame;
    private Monitor m;
    private DefaultListModel<Usuario> modeloLista;
    private JList<Usuario> listaActividades;
    private JTable tablaSemanasApuntado;
    private JLabel perfilLabel;
    private JTabbedPane tabbedPane;
    private JScrollPane scrollLista;
    private JPanel lista;
    public VentanaMonitor(Monitor m) {
    	this.m = m;
        frame = new JFrame("Ventana de Monitor");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setLayout(new BorderLayout());

        perfilLabel = new JLabel("Perfil del Monitor: " + m.getNombre());
        perfilLabel.setHorizontalAlignment(JLabel.LEFT);
        perfilLabel.setForeground(Color.BLACK);
        frame.add(perfilLabel, BorderLayout.NORTH);

        tabbedPane = new JTabbedPane();

        JPanel panelApuntado = new JPanel(new BorderLayout());
		tablaSemanasApuntado = crearTablaClasesApuntadas();
		panelApuntado.add(new JScrollPane(tablaSemanasApuntado), BorderLayout.CENTER);

        tabbedPane.addTab("Clases Apuntadas", panelApuntado);

        frame.add(tabbedPane, BorderLayout.CENTER);

        modeloLista = new DefaultListModel<>();
        listaActividades = new JList<>(modeloLista);
        listaActividades.setBackground(new Color(240, 240, 240));
        scrollLista = new JScrollPane(listaActividades);
        lista = new JPanel();
        lista.add(scrollLista);
        frame.add(lista, BorderLayout.WEST);
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

    public JTable crearTablaClasesApuntadas() {
		JTable tabla = new JTable() {@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
			Component comp = super.prepareRenderer(renderer, row, column);
			int rendererWidth = comp.getPreferredSize().width;
			TableColumn tableColumn = getColumnModel().getColumn(column);
			tableColumn.setPreferredWidth(100);
			setRowHeight(25);
			return comp;
		}};
		DefaultTableModel modeloTabla = new DefaultTableModel(0, 7) {
			@Override
			public boolean isCellEditable(int row, int column) {
				
				return row > 0;
			}
		};
		modeloTabla.setColumnIdentifiers(
				new Object[] { "Hora", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo" });
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
				if(e.getMonitor().equals(this.m)) {
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
			}
			modeloTabla.addRow(new Object[] { i + ":00", lunes, martes, miercoles, jueves, viernes, sabado, domingo});
		}
		tabla.setModel(modeloTabla);
		tabla.getTableHeader().setReorderingAllowed(false);
		tabla.setGridColor(Color.BLACK);
		tabla.setShowGrid(true);
		tabla.setCellSelectionEnabled(true);

		tabla.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int row = tabla.rowAtPoint(evt.getPoint());
				int col = tabla.columnAtPoint(evt.getPoint());

				if (row >= 0 && col > 0) {
					Miclase claseSeleccionada = (Miclase) modeloTabla.getValueAt(row, col);
					modeloLista.clear();
					for(Usuario u: claseSeleccionada.getLista().get(0).getAsistentes()) {
						modeloLista.addElement(u);
					}
					listaActividades = new JList<>(modeloLista);
				    listaActividades.setBackground(new Color(240, 240, 240));
				    scrollLista = new JScrollPane(listaActividades);
				    lista.removeAll();
				    lista.add(scrollLista);
					lista.repaint();
					lista.revalidate();
				}
			}
		});
		return tabla;
	}
    public void mostrarVentana() {
        frame.setVisible(true);
    }
}