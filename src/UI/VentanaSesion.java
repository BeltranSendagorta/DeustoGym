package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VentanaSesion {
    private JFrame frame;

    public VentanaSesion() {
        frame = new JFrame("Ventana de Sesión");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setLayout(new BorderLayout());

        // Configuración del nombre de la empresa
        JLabel nombreEmpresaLabel = new JLabel("DeustoGym");
        nombreEmpresaLabel.setForeground(Color.WHITE);
        nombreEmpresaLabel.setFont(new Font("Arial", Font.BOLD, 24));
        nombreEmpresaLabel.setHorizontalAlignment(JLabel.CENTER);
        frame.add(nombreEmpresaLabel, BorderLayout.NORTH);

        // Panel para los botones
        JPanel panelBotones = new JPanel(new GridLayout(3, 1, 0, 10));

        // Botón para iniciar sesión como usuario
        JButton iniciarSesionUsuarioButton = new JButton("Iniciar Sesión Usuario");
        iniciarSesionUsuarioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VentanaInicioSesionUsuario ventanaInicioSesionUsuario = new VentanaInicioSesionUsuario();
                ventanaInicioSesionUsuario.mostrarVentana();
                frame.dispose(); // Cerrar la ventana actual
            }
        });
        panelBotones.add(iniciarSesionUsuarioButton);

        // Botón para iniciar sesión como monitor
        JButton iniciarSesionMonitorButton = new JButton("Iniciar Sesión Monitor");
        iniciarSesionMonitorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para la ventana de inicio de sesión del monitor
            }
        });
        panelBotones.add(iniciarSesionMonitorButton);

        // Botón para iniciar sesión como administrador
        JButton iniciarSesionAdminButton = new JButton("Iniciar Sesión Administrador");
        iniciarSesionAdminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para la ventana de inicio de sesión del administrador
            }
        });
        panelBotones.add(iniciarSesionAdminButton);

        frame.add(panelBotones, BorderLayout.CENTER);
    }

    public void mostrarVentana() {
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        VentanaSesion ventanaSesion = new VentanaSesion();
        ventanaSesion.mostrarVentana();
    }
}
