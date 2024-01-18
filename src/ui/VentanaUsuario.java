package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import bd.BaseDatos;
import domain.Entrenamiento;
import domain.Factura;
import domain.Usuario;
import io.DeustoGym;

public class VentanaUsuario {
    public JFrame frame;
    private Usuario u = null;
    private DefaultListModel<Factura> modeloLista;
    private JList<Factura> listaActividades;
    private JTable tablaSemanasApuntado;
    private JTable tablaSemanasDisponibles;
    private Factura f= null;
    JPanel panelApuntado = new JPanel(new BorderLayout());
    @SuppressWarnings("unused")
	private Entrenamiento entr = null;

    public VentanaUsuario(Usuario u) {
    	this.u = u;
        frame = new JFrame("Ventana de Usuario");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setLayout(new BorderLayout());

        JLabel perfilLabel = new JLabel("Perfil del Usuario: " + this.u.getNombre());
        perfilLabel.setHorizontalAlignment(JLabel.RIGHT);
        perfilLabel.setForeground(Color.BLACK);
        frame.add(perfilLabel, BorderLayout.NORTH);

        JTabbedPane tabbedPane = new JTabbedPane();

        panelApuntado = new JPanel(new BorderLayout());
        tablaSemanasApuntado = crearTablaClasesApuntadas();
        panelApuntado.add(new JScrollPane(tablaSemanasApuntado), BorderLayout.CENTER);

        JPanel panelDisponibles = new JPanel(new BorderLayout());
        
        
        tablaSemanasDisponibles = crearTablaClasesDisponibles();
        
        panelDisponibles.add(new JScrollPane(tablaSemanasDisponibles), BorderLayout.CENTER);

        tabbedPane.addTab("Clases Apuntadas", panelApuntado);
        tabbedPane.addTab("Clases Disponibles", panelDisponibles);

        frame.add(tabbedPane, BorderLayout.CENTER);

        modeloLista = new DefaultListModel<Factura>();
        boolean hay = false;
        modeloLista.addElement(f);
        if(!hay) modeloLista.addElement(new Factura(u));
        listaActividades = new JList<>(modeloLista);
        listaActividades.setBackground(new Color(240, 240, 240));
        JScrollPane scrollLista = new JScrollPane(listaActividades);
        frame.add(scrollLista, BorderLayout.EAST);
        listaActividades.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                int index = listaActividades.locationToIndex(evt.getPoint());
                f = modeloLista.getElementAt(index);
                DeustoGym.listF.get(DeustoGym.listF.indexOf(f)).setPagado(true);
                frame.repaint();
            }
        });
        JButton botonC = new JButton("Cerrar Sesion");
        botonC.addActionListener(e ->{
            File f = new File("resources/.properties");
            if(f.exists()) {
                f.delete();
            }
            frame.dispose();
            VentanaSesion ventanaSesion = new VentanaSesion();
            ventanaSesion.mostrarVentana();
        });

        // Agregar el botón al sur de la ventana
        frame.add(botonC, BorderLayout.SOUTH);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
        	@Override
			public void windowClosed(WindowEvent e) {
				BaseDatos.actualizarBD();
				DeustoGym.guardarFacturas();
			};
        });
    }

	public JTable crearTablaClasesApuntadas() {
		JTable tabla = new JTable() {
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component comp = super.prepareRenderer(renderer, row, column);
				TableColumn tableColumn = getColumnModel().getColumn(column);
				tableColumn.setPreferredWidth(100);
				setRowHeight(25);
				return comp;
			}
		};
		DefaultTableModel modeloTabla = new DefaultTableModel(0, 7) {
			private static final long serialVersionUID = 1L;

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
				if(e.getAsistentes().contains(this.u)) {
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
			public void mouseClicked(MouseEvent evt) {
				int row = tabla.rowAtPoint(evt.getPoint());
				int col = tabla.columnAtPoint(evt.getPoint());
				if (row >= 0 && col > 0) {
					Miclase claseSeleccionada = (Miclase) modeloTabla.getValueAt(row, col);
					if (claseSeleccionada != null) {
						frame.setVisible(false);
						getEntrenamientoLista(claseSeleccionada, 0);
					}
					panelApuntado.removeAll();
					panelApuntado.add(crearTablaClasesApuntadas());
					modeloTabla.setColumnIdentifiers(
							new Object[] { "Hora", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo" }
					);
					panelApuntado.repaint();
					panelApuntado.revalidate();
				}
			}
		});
		return tabla;
	}

	public JTable crearTablaClasesDisponibles() {
		JTable tabla = new JTable() {
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component comp = super.prepareRenderer(renderer, row, column);
				TableColumn tableColumn = getColumnModel().getColumn(column);
				tableColumn.setPreferredWidth(100);
				setRowHeight(25);
				return comp;
			}
		};
		DefaultTableModel modeloTabla = new DefaultTableModel(0, 7) {
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int column) {
				
				return row > 0;
			}
		};
		modeloTabla.setColumnIdentifiers(
				new Object[] { "Hora", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo" }
		);
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

		tabla.setModel(modeloTabla);

		tabla.getTableHeader().setReorderingAllowed(false);

		tabla.setGridColor(Color.BLACK);
		tabla.setShowGrid(true);
		tabla.setCellSelectionEnabled(true);
		tabla.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent evt) {
				int row = tabla.rowAtPoint(evt.getPoint());
				int col = tabla.columnAtPoint(evt.getPoint());
				if (row >= 0 && col > 0) {
					Miclase claseSeleccionada = (Miclase) modeloTabla.getValueAt(row, col);
					if (claseSeleccionada != null) {
						frame.setVisible(false);
						getEntrenamientoLista(claseSeleccionada, 1);						
					}
					panelApuntado.removeAll();
					panelApuntado.add(crearTablaClasesApuntadas());
					modeloTabla.setColumnIdentifiers(
							new Object[] { "Hora", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo" }
					);
					panelApuntado.repaint();
					panelApuntado.revalidate();
				}
			}
		});
		return tabla;
	}
	
	public void getEntrenamientoLista(Miclase clase, int tipo) {
		this.entr = null;
		JFrame ventanaSele = new JFrame();
		ventanaSele.setSize(400,400);
		JButton boton = new JButton("Atras");
		JPanel lista = new JPanel();
		JPanel botonera = new JPanel();
		botonera.setLayout(new GridLayout(1,2));
		botonera.add(boton);
		ventanaSele.setLayout(new FlowLayout());
		DefaultListModel<Entrenamiento> modeloListaEntr = new DefaultListModel<Entrenamiento>();
		for(Entrenamiento e : clase.getLista()) {
			modeloListaEntr.addElement(e);
		}
	    JList<Entrenamiento> listaActividadesEntr = new JList<Entrenamiento>(modeloListaEntr);
	    listaActividadesEntr.addMouseListener(new MouseAdapter() {
	        @Override
	        public void mouseClicked(MouseEvent evt) {
	            int index = listaActividadesEntr.locationToIndex(evt.getPoint());  // Cambio aquí
	            if (index >= 0 && index < modeloListaEntr.getSize()) {  // Cambio aquí
	                entr = modeloListaEntr.getElementAt(index);
	                if (tipo == 1) {
	                    if (mostrarDialogoApuntarse(entr)) {
	                        entr.agregarAsistente(u);
	                        frame.revalidate();
	                        frame.setVisible(true);
	                        ventanaSele.dispose();
	                    }
	                } else {
	                    if (mostrarDialogoDesapuntarse(entr)) {
	                        if (entr.getAsistentes().contains(u)) {
	                            entr.getAsistentes().remove(u);
	                            frame.revalidate();
	                            frame.setVisible(true);
	                            ventanaSele.dispose();
	                        }
	                    }
	                }
	            }
	        }
	    });

	    lista.add(listaActividadesEntr);
	    boton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				entr = null;
				ventanaSele.dispose();
				frame.revalidate();
				frame.setVisible(true);
			}
		});
	    ventanaSele.getContentPane().add(listaActividadesEntr);
	    ventanaSele.getContentPane().add(botonera);
	    ventanaSele.setVisible(true);
	}
	
	public boolean mostrarDialogoApuntarse(Entrenamiento claseSeleccionada) {
		String[] opciones = { "Sí", "No" };
		int seleccion = JOptionPane.showOptionDialog(frame,
				"¿Quieres apuntarte a la clase de " + claseSeleccionada+" El "+ claseSeleccionada.getdiaSe() +
				", a las: "+ claseSeleccionada.getHoraInicio() + "?", "Apuntarse a Clase",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

		if (seleccion == JOptionPane.YES_OPTION) {
			JOptionPane.showMessageDialog(frame, "Te has apuntado a la clase de " + claseSeleccionada);
			return true;
		}
		return false;
	}

	public boolean mostrarDialogoDesapuntarse(Entrenamiento claseSeleccionada) {
		String[] opciones = { "Sí", "No" };
		int seleccion = JOptionPane.showOptionDialog(frame,
				"¿Quieres desapuntarte de la clase de " + claseSeleccionada+" El "+ claseSeleccionada.getdiaSe() +
				", a las: "+ claseSeleccionada.getHoraInicio()+ "?", "Desapuntarse de Clase",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
		if (seleccion == JOptionPane.YES_OPTION) {
			JOptionPane.showMessageDialog(frame, "Te has desapuntado de la clase de " + claseSeleccionada);
			return true;
		}
		return false;
	}

	public void mostrarVentana() {
		frame.setVisible(true);
	}
}

class Miclase{
	ArrayList<Entrenamiento> entr = new ArrayList<Entrenamiento>();
	public Miclase() {
	}
	public ArrayList<Entrenamiento> getLista(){
		return this.entr;
	}
	@Override
	public String toString() {
		String r = "";
		for(Entrenamiento e: this.entr)
			r+=e.getNombre()+",";
		return r;
	}
}
