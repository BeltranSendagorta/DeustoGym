package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import bd.BaseDatos;
import domain.Administrador;

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
        agregarComponente(panelInicioSesion, new JLabel("Dni de Administrador:"), 0, 0, gbc);
        JTextField nombreAdministradorTextField = new JTextField(15);
        agregarComponente(panelInicioSesion, nombreAdministradorTextField, 1, 0, gbc);

        agregarComponente(panelInicioSesion, new JLabel("Contraseña:"), 0, 1, gbc);
        JPasswordField contraseñaPasswordField = new JPasswordField(15);
        agregarComponente(panelInicioSesion, contraseñaPasswordField, 1, 1, gbc);

        JButton registrarseButton = new JButton("Salir");
        registrarseButton.addActionListener(e -> abrirVentanaSesion());
        agregarComponente(panelInicioSesion, registrarseButton, 1, 2, gbc);

        JButton iniciarSesionButton = new JButton("Iniciar Sesión");
        iniciarSesionButton.addActionListener(e ->{
        	if(nombreAdministradorTextField.getText() != null && contraseñaPasswordField.getPassword() != null) {
        		Administrador a = (Administrador) BaseDatos.getPersona(nombreAdministradorTextField.getText(), 0);
        		if(a.getContrasenia().equals(new String(contraseñaPasswordField.getPassword())));
        			iniciarSesion(a);
        	}
        });
        agregarComponente(panelInicioSesion, iniciarSesionButton, 1, 3, gbc);

        // Agregar el panel al centro
        frame.add(panelInicioSesion, BorderLayout.CENTER);
    }

    private void agregarComponente(JPanel panel, JComponent componente, int x, int y, GridBagConstraints gbc) {
        gbc.gridx = x;
        gbc.gridy = y;
        panel.add(componente, gbc);
    }

    private void abrirVentanaSesion() {
        VentanaSesion ventanaSesion = new VentanaSesion();
        ventanaSesion.mostrarVentana();
        frame.dispose();
    }

    private void iniciarSesion(Administrador a) {
    	VentanaAdministrador ventanaAdministrador = new VentanaAdministrador(a);
    	ventanaAdministrador.mostrarVentana();
        frame.dispose();
    }


    public void mostrarVentana() {
        frame.setVisible(true);
    }

}
