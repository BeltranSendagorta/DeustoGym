package UI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Calendar;

public class VentanaUsuario {
    private JFrame frame;
    private DefaultListModel<String> modeloLista;
    private JList<String> listaActividades;

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

        JPanel panelTabla = new JPanel(new BorderLayout());

        // Crear la tabla
        JTable tablaSemanas = new JTable();
        DefaultTableModel modeloTabla = new DefaultTableModel(0, 8) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Añadir las horas en la primera columna
        for (int i = 9; i <= 20; i++) {
            modeloTabla.addRow(new Object[]{i + ":00", null, null, null, null, null, null, null});
        }

        // Añadir los días de la semana como encabezados de columna
        modeloTabla.setColumnIdentifiers(new Object[]{"Hora", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"});

        tablaSemanas.setModel(modeloTabla);
        tablaSemanas.setGridColor(Color.BLACK);
        tablaSemanas.setShowGrid(true);

        // Activar la selección de celdas en lugar de filas
        tablaSemanas.setCellSelectionEnabled(true);

        // Agregar un escucha de eventos de clic para manejar la selección de celdas
        tablaSemanas.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = tablaSemanas.rowAtPoint(evt.getPoint());
                int col = tablaSemanas.columnAtPoint(evt.getPoint());
                if (row >= 0 && col > 0) {  // Evitar la primera columna (horas)
                    // Aquí puedes manejar la lógica para la celda seleccionada
                    System.out.println("Celda seleccionada: " + modeloTabla.getValueAt(row, col));
                }
            }
        });

        panelTabla.add(new JScrollPane(tablaSemanas), BorderLayout.CENTER);

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

        frame.add(panelTabla, BorderLayout.CENTER);
    }

    private void mostrarDialogoReservaExitosa(String entrenamientoSeleccionado) {
        JOptionPane.showMessageDialog(frame, "Tu reserva de " + entrenamientoSeleccionado + " se ha realizado con éxito.");
    }

    public void mostrarVentana() {
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        VentanaUsuario ventanaUsuario = new VentanaUsuario("UsuarioPrueba");
        ventanaUsuario.mostrarVentana();
    }
}
