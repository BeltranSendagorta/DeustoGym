package UI;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
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

        JLabel perfilLabel = new JLabel("Perfil del Administrador: " + nombreAdministrador);
        perfilLabel.setHorizontalAlignment(JLabel.RIGHT);
        perfilLabel.setForeground(Color.BLACK);
        frame.add(perfilLabel, BorderLayout.NORTH);

        JPanel panelApuntado = new JPanel(new BorderLayout());
        tablaSemanasApuntado = crearTabla();
        panelApuntado.add(new JScrollPane(tablaSemanasApuntado), BorderLayout.CENTER);
        frame.add(panelApuntado, BorderLayout.CENTER);

      
        JTextArea gananciasTextArea = new JTextArea();
        gananciasTextArea.setEditable(false);
        frame.add(new JScrollPane(gananciasTextArea), BorderLayout.SOUTH);

        modeloLista = new DefaultListModel<>();
        JList<String> listaActividades = new JList<>(modeloLista);
        listaActividades.setBackground(new Color(240, 240, 240));
        JScrollPane scrollLista = new JScrollPane(listaActividades);
        frame.add(scrollLista, BorderLayout.EAST);

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

    private JTable crearTabla() {
        JTable tabla = new JTable();
        DefaultTableModel modeloTabla = new DefaultTableModel(0, 8) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        try (BufferedReader br = new BufferedReader(new FileReader("Horario2023.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                StringTokenizer tokenizer = new StringTokenizer(line, ",");
                String hora = tokenizer.nextToken();
                String[] clases = new String[7];

                // Verificar si hay suficientes tokens antes de acceder a ellos
                for (int i = 0; i < clases.length && tokenizer.hasMoreTokens(); i++) {
                    clases[i] = tokenizer.nextToken();
                }

                modeloTabla.addRow(new Object[]{hora, clases[0], clases[1], clases[2], clases[3], clases[4], clases[5], clases[6]});
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        modeloTabla.setColumnIdentifiers(new Object[]{"Hora", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"});

        tabla.setModel(modeloTabla);
        tabla.setGridColor(Color.BLACK);
        tabla.setShowGrid(true);
        tabla.setCellSelectionEnabled(true);

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                String actividad = (value != null) ? value.toString() : "";

                 // Asignar colores según la actividad
	            switch (actividad) {
	                case "Yoga":
	                    c.setBackground(Color.PINK);
	                    icono = new ImageIcon("img/yoga.png");
	                    JLabel a = new JLabel();
	                    a.setHorizontalAlignment(JLabel.CENTER);
	                    a.setIcon(icono);
	                    return a;
	                
	                case "Spinning":
	                    c.setBackground(Color.GREEN);
	                    icono = new ImageIcon("img/spinning.png");
	                    JLabel b = new JLabel();
	                    b.setHorizontalAlignment(JLabel.CENTER);
	                    b.setIcon(icono);
	                    return b;

	                case "Core":
	                    c.setBackground(Color.YELLOW);
	                    icono = new ImageIcon("img/core.png");
	                    JLabel d = new JLabel();
	                    d.setHorizontalAlignment(JLabel.CENTER);
	                    d.setIcon(icono);
	                    return d;

	                case "Boxeo":
	                    c.setBackground(new Color(128, 191, 255));
	                    icono = new ImageIcon("img/boxeo.png");
	                    JLabel e = new JLabel();
	                    e.setHorizontalAlignment(JLabel.CENTER);
	                    e.setIcon(icono);
	                    return e;

	                case "Aeroyoga":
	                    c.setBackground(Color.YELLOW);
	                    icono = new ImageIcon("img/aeroyoga.png");
	                    JLabel f = new JLabel();
	                    f.setHorizontalAlignment(JLabel.CENTER);
	                    f.setIcon(icono);
	                    return f;
	                    
	                case "Pilates":
	                    c.setBackground(Color.RED);
	                    icono = new ImageIcon("img/pilates.png");
	                    JLabel g = new JLabel();
	                    g.setHorizontalAlignment(JLabel.CENTER);
	                    g.setIcon(icono);
	                    return g;

	                case "HIIt":
	                    c.setBackground(Color.GRAY);
	                    icono = new ImageIcon("img/hiit.png");
	                    JLabel h = new JLabel();
	                    h.setHorizontalAlignment(JLabel.CENTER);
	                    h.setIcon(icono);
	                    return h;

	                case "Funcional":
	                    c.setBackground(new Color(139, 69, 19)); // Marrón
	                    icono = new ImageIcon("img/funcional.png");
	                    JLabel i = new JLabel();
	                    i.setHorizontalAlignment(JLabel.CENTER);
	                    i.setIcon(icono);
	                    return i;

	                default:
	                    c.setBackground(table.getBackground());
                }

                return c;
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

        // Añadir las claves que no estén en el mapa gananciasProfesores con valor 0.
        Set<String> profesoresAsignados = new HashSet<>(gananciasProfesores.keySet());
        Set<String> profesoresEsperados = new HashSet<>(Arrays.asList("Koldo", "Peio", "Kepa", "Jose", "Nerea", "Alex", "Beltran", "Maider", "Malen"));
        profesoresEsperados.removeAll(profesoresAsignados);
        for (String profesorFaltante : profesoresEsperados) {
            gananciasProfesores.put(profesorFaltante, 0);
        }

        actualizarLabelGanancias();
    }

    public String obtenerProfesorDeClase(String clase) {
     
        Map<String, String> asignacionClasesProfesores = new HashMap<>();
        asignacionClasesProfesores.put("Yoga", "Koldo");
        asignacionClasesProfesores.put("Yoga", "Peio");
        asignacionClasesProfesores.put("Yoga", "Kepa");
        asignacionClasesProfesores.put("Spinning", "Jose");
        asignacionClasesProfesores.put("Spinning", "Nerea");
        asignacionClasesProfesores.put("Spinning", "Alex");
        asignacionClasesProfesores.put("Core", "Beltran");
        asignacionClasesProfesores.put("Core", "Maider");
        asignacionClasesProfesores.put("Core", "Malen");

    
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
        JOptionPane.showMessageDialog(frame, "Clase: " + claseSeleccionada + "\nProfesor: " + profesor + "\nUsuarios Apuntados: " + usuariosApuntados);
    }

    public String generarNombresUsuariosAleatorios() {
        String[] nombres = { "Iker", "Aitor", "Ander", "Eneko", "Kerman", "Iñigo", "Javier", "Alejandro", "Beñat", "Oscar", "Xabier", "Roberto" };
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
