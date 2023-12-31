package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaInicioSesionAdministrador {
    private JFrame frame;

    public VentanaInicioSesionAdministrador() {
        frame = new JFrame("Ventana de Inicio de Sesión Administrador");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setLayout(new BorderLayout());

        // Panel para los componentes de inicio de sesión
        JPanel panelInicioSesion = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Componentes de inicio de sesión
        agregarComponente(panelInicioSesion, new JLabel("Nombre de Administrador:"), 0, 0, gbc);
        JTextField nombreAdministradorTextField = new JTextField(15);
        agregarComponente(panelInicioSesion, nombreAdministradorTextField, 1, 0, gbc);

        agregarComponente(panelInicioSesion, new JLabel("Contraseña:"), 0, 1, gbc);
        JPasswordField contraseñaPasswordField = new JPasswordField(15);
        agregarComponente(panelInicioSesion, contraseñaPasswordField, 1, 1, gbc);

        JButton registrarseButton = new JButton("¿ Aun no estás registrado ?");
        registrarseButton.addActionListener(e -> abrirVentanaRegistro());
        agregarComponente(panelInicioSesion, registrarseButton, 1, 2, gbc);

        JButton iniciarSesionButton = new JButton("Iniciar Sesión");
        iniciarSesionButton.addActionListener(e -> iniciarSesion(nombreAdministradorTextField.getText()));
        agregarComponente(panelInicioSesion, iniciarSesionButton, 1, 3, gbc);

        // Agregar el panel al centro
        frame.add(panelInicioSesion, BorderLayout.CENTER);
    }

    private void agregarComponente(JPanel panel, JComponent componente, int x, int y, GridBagConstraints gbc) {
        gbc.gridx = x;
        gbc.gridy = y;
        panel.add(componente, gbc);
    }

    private void abrirVentanaRegistro() {
        VentanaRegistro ventanaRegistro = new VentanaRegistro();
        ventanaRegistro.mostrarVentana();
        frame.dispose();
    }

    private void iniciarSesion(String nombreAdministrador) {
    	VentanaAdministrador ventanaAdministrador = new VentanaAdministrador(nombreAdministrador);
    	ventanaAdministrador.mostrarVentana();
        frame.dispose();
    }


    public void mostrarVentana() {
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        VentanaInicioSesionAdministrador VentanaInicioSesionAdministrador = new VentanaInicioSesionAdministrador();
        VentanaInicioSesionAdministrador.mostrarVentana();

    }
}
