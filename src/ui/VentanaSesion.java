package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class VentanaSesion {
    private JFrame frame;

    public VentanaSesion() {
        frame = new JFrame("Ventana de Sesión");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setLayout(new BorderLayout());
        
        // Configuración del nombre
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
                VentanaInicioSesionMonitor ventanaInicioSesionMonitor = new VentanaInicioSesionMonitor();
                ventanaInicioSesionMonitor.mostrarVentana();
                frame.dispose(); // Cerrar la ventana actual
            }
        });
        panelBotones.add(iniciarSesionMonitorButton);

        // Botón para iniciar sesión como administrador
        JButton iniciarSesionAdminButton = new JButton("Iniciar Sesión Administrador");
        iniciarSesionAdminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VentanaInicioSesionAdministrador ventanaInicioSesionAdministrador = new VentanaInicioSesionAdministrador();
				ventanaInicioSesionAdministrador.mostrarVentana();
                frame.dispose();
            }
        });
        panelBotones.add(iniciarSesionAdminButton);

        frame.add(panelBotones, BorderLayout.CENTER);
    }

    public void mostrarVentana() {
        frame.setVisible(true);
    }

    // Método para cargar la imagen del archivo en la carpeta "img"
    protected static ImageIcon createImageIcon(String path) {
        URL imgURL = VentanaSesion.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("No se pudo encontrar el archivo: " + path);
            return null;
        }
    }
    
    public void setIconImage(Image image) {
        frame.setIconImage(image);
    }
}