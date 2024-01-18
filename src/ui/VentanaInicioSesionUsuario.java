package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import bd.BaseDatos;
import domain.Usuario;

public class VentanaInicioSesionUsuario {
    private JFrame frame;
	private static Logger logger = Logger.getLogger( "iNICIAR SESION" );
	
    public VentanaInicioSesionUsuario() {
    	JCheckBox jcb = new JCheckBox("Mantener Iniciada sesion");
        frame = new JFrame("Ventana de Inicio de Sesión Usuario");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setLayout(new BorderLayout());

        // Panel para los componentes de inicio de sesión
        JPanel panelInicioSesion = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Componentes de inicio de sesión
        agregarComponente(panelInicioSesion, new JLabel("Dni de Usuario:"), 0, 0, gbc);
        JTextField nombreUsuarioTextField = new JTextField(15);
        agregarComponente(panelInicioSesion, nombreUsuarioTextField, 1, 0, gbc);

        agregarComponente(panelInicioSesion, new JLabel("Contraseña:"), 0, 1, gbc);
        JPasswordField contraseñaPasswordField = new JPasswordField(15);
        agregarComponente(panelInicioSesion, contraseñaPasswordField, 1, 1, gbc);

        JButton registrarseButton = new JButton("¿ Aun no estás registrado ?");
        registrarseButton.addActionListener(e -> abrirVentanaRegistro());
        agregarComponente(panelInicioSesion, registrarseButton, 1, 2, gbc);

        JButton iniciarSesionButton = new JButton("Iniciar Sesión");
        iniciarSesionButton.addActionListener(e -> {
        	if(nombreUsuarioTextField.getText() != null && contraseñaPasswordField.getPassword() != null) {
        		Usuario u = (Usuario) BaseDatos.getPersona(nombreUsuarioTextField.getText(), 1);
        		if(u.getContrasenia().equals(new String(contraseñaPasswordField.getPassword())));
        			iniciarSesion(jcb.isSelected(), u);
        	}
        });
        agregarComponente(panelInicioSesion, iniciarSesionButton, 1, 3, gbc);
        
        agregarComponente(panelInicioSesion, jcb, 1, 4, gbc);

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

    private void iniciarSesion(boolean guardar, Usuario u) {
    	if(guardar) {
    		File f = new File("resources/.properties");
    		Properties pro = new Properties();
    		pro.setProperty("Dni", u.getDni());
    		pro.setProperty("Pw", u.getContrasenia());
    		pro.setProperty("Tipo", "1");
    		
    		try {
				pro.storeToXML(new FileOutputStream(f), "Nombre del usuario");
				logger.log(Level.INFO, "Fichero .properties Hecho");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
        new VentanaUsuario(u);
        frame.dispose();
    }

    public void mostrarVentana() {
    	Properties pro = new Properties();
 		try {
			pro.loadFromXML(new FileInputStream("resources/.properties"));
			if(pro.getProperty("Pw") != null) {
				String user = pro.getProperty("Dni");
				int tipo = Integer.parseInt(pro.getProperty("Tipo"));
				String pas = pro.getProperty("Pw");
				switch (tipo) {
				//En caso de querer hacerse para Adminsitrador y Monitor (No se hace por moitivos de seguridad)
				case 1:{
					Usuario u = ((Usuario) BaseDatos.getPersona(user, tipo));
					if(pas.equals(u.getContrasenia())) {
						new VentanaUsuario(u);
					}
					break;
				}
				default:
			        frame.setVisible(true);
				}
			}else {
		        frame.setVisible(true);
			}
		} catch (FileNotFoundException e) {
	        frame.setVisible(true);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
