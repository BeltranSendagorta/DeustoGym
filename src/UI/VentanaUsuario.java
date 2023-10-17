package UI;

import com.toedter.calendar.JCalendar;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Calendar;
import java.util.Date;

public class VentanaUsuario {
    private JFrame frame;
    private DefaultListModel<String> modeloLista;
    private JList<String> listaActividades;

    public VentanaUsuario(String nombreUsuario) {
        frame = new JFrame("Ventana de Usuario");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.WHITE); // Fondo blanco para mayor contraste
        frame.setLayout(new BorderLayout());

        // Componente para mostrar el perfil del usuario
        JLabel perfilLabel = new JLabel("Perfil del Usuario: " + nombreUsuario);
        perfilLabel.setHorizontalAlignment(JLabel.RIGHT);
        perfilLabel.setForeground(Color.BLACK); // Texto en negro para mayor contraste
        frame.add(perfilLabel, BorderLayout.NORTH);

        // Panel para el calendario
        JPanel panelCalendario = new JPanel(new BorderLayout());

        // Agregar el JCalendar
        JCalendar jCalendar = new JCalendar();
        panelCalendario.add(jCalendar, BorderLayout.CENTER);

        // Inicializar el modelo de lista y la lista de actividades
        modeloLista = new DefaultListModel<>();
        listaActividades = new JList<>(modeloLista);
        listaActividades.setBackground(new Color(240, 240, 240)); // Fondo gris claro para la lista
        JScrollPane scrollLista = new JScrollPane(listaActividades);
        frame.add(scrollLista, BorderLayout.EAST);

        // Agregar un listener para manejar la selección de días
        jCalendar.getDayChooser().addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("day".equals(evt.getPropertyName())) {
                    Date selectedDate = jCalendar.getDate();
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(selectedDate);

                    int diaSemana = calendar.get(Calendar.DAY_OF_WEEK);

                    if (diaSemana == Calendar.TUESDAY || diaSemana == Calendar.THURSDAY) {
                        mostrarDialogoOpcionesReserva();
                    } else {
                        ocultarOpcionesReserva();
                    }
                }
            }
        });

        // Agregar un listener para manejar la selección de entrenamientos
        listaActividades.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int index = listaActividades.locationToIndex(evt.getPoint());
                String entrenamientoSeleccionado = modeloLista.getElementAt(index);
                mostrarDialogoReservaExitosa(entrenamientoSeleccionado);
            }
        });

        frame.add(panelCalendario, BorderLayout.CENTER);
    }

    private void mostrarDialogoOpcionesReserva() {
        // Crear un cuadro de diálogo con las opciones de reserva
        String[] opciones = {"Crossfit 22:00", "Spinning 16:00"};
        String seleccion = (String) JOptionPane.showInputDialog(frame, "Selecciona una opción:",
                "Opciones de Reserva", JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

        // Si se selecciona una opción, añadirla a la lista de actividades
        if (seleccion != null) {
            modeloLista.addElement(seleccion);
            mostrarDialogoReservaExitosa(seleccion);
        }
    }

    private void ocultarOpcionesReserva() {
        // Limpiar la lista de actividades
        modeloLista.clear();
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
