package ui;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import domain.Suscripcion;
import domain.TipoSuscripcion;
import domain.Usuario;

public class VentanaRegistro {
    private JFrame frame;
  
    public VentanaRegistro() {
        frame = new JFrame("Ventana de Registro");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setLayout(new BorderLayout());

        // Panel principal con GroupLayout
        JPanel panelRegistro = new JPanel();
        GroupLayout layout = new GroupLayout(panelRegistro);
        panelRegistro.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        // Componentes de registro
        JLabel nombreLabel = new JLabel("Nombre:");
        JTextField nombreTextField = new JTextField();
        JLabel apellidoLabel = new JLabel("Apellido:");
        JTextField apellidoTextField = new JTextField();
        JLabel usuarioLabel = new JLabel("Dni:");
        JTextField usuarioTextField = new JTextField();
        JLabel edadLabel = new JLabel("Edad:");
        JTextField edadTextField = new JTextField("18");
        edadTextField.setText("18");
        JLabel contraseñaLabel = new JLabel("Contraseña:");
        JPasswordField contraseñaPasswordField = new JPasswordField();

        // JComboBox para el tipo de suscripción
        JLabel tipoSuscripcionLabel = new JLabel("Tipo de Suscripción:");
        JComboBox<TipoSuscripcion> tipoSuscripcionComboBox = new JComboBox<>(TipoSuscripcion.values());

        JButton registrarmeButton = new JButton("Registrarme");
        registrarmeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try {
                TipoSuscripcion tipoSuscripcionSeleccionado = (TipoSuscripcion) tipoSuscripcionComboBox.getSelectedItem();
                if(tipoSuscripcionSeleccionado!=null && !usuarioTextField.getText().equals("") && !nombreTextField.getText().equals("")
                		&&!apellidoTextField.getText().equals("")&&!edadTextField.getText().equals("")&& !(new String(contraseñaPasswordField.getPassword()).equals("")) ) {
                	if(!Arrays.asList(TipoSuscripcion.values()).contains(tipoSuscripcionSeleccionado))
                		new Usuario(usuarioTextField.getText(), nombreTextField.getText(), apellidoTextField.getText(),
                			Integer.parseInt(edadTextField.getText()), new String(contraseñaPasswordField.getPassword()), new Suscripcion(TipoSuscripcion.Basica, 0));
                	else new Usuario(usuarioTextField.getText(), nombreTextField.getText(), apellidoTextField.getText(),
                			Integer.parseInt(edadTextField.getText()), new String(contraseñaPasswordField.getPassword()), new Suscripcion(tipoSuscripcionSeleccionado, 0));
                }
                VentanaInicioSesionUsuario ventanaInicioSesionUsuario = new VentanaInicioSesionUsuario();
                ventanaInicioSesionUsuario.mostrarVentana();
                frame.dispose();
            	}catch (Exception e1) {
            		System.out.println(e1.getStackTrace());
				}
            }
        });

        // Configuración del GroupLayout
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(nombreLabel)
                        .addComponent(apellidoLabel)
                        .addComponent(usuarioLabel)
                        .addComponent(contraseñaLabel)
                        .addComponent(edadLabel)
                        .addComponent(tipoSuscripcionLabel)
                        .addComponent(registrarmeButton))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(nombreTextField)
                        .addComponent(apellidoTextField)
                        .addComponent(usuarioTextField)
                        .addComponent(contraseñaPasswordField)
                        .addComponent(edadTextField)
                        .addComponent(tipoSuscripcionComboBox)
                        .addComponent(registrarmeButton)));

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(nombreLabel)
                        .addComponent(nombreTextField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(apellidoLabel)
                        .addComponent(apellidoTextField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(usuarioLabel)
                        .addComponent(usuarioTextField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(contraseñaLabel)
                        .addComponent(contraseñaPasswordField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(edadLabel)
                        .addComponent(edadTextField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(tipoSuscripcionLabel)
                        .addComponent(tipoSuscripcionComboBox))
                .addComponent(registrarmeButton));

        // Agregar el panel al centro
        frame.add(panelRegistro, BorderLayout.CENTER);
    }
    public void mostrarVentana() {
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        VentanaRegistro ventanaRegistro = new VentanaRegistro();
        ventanaRegistro.mostrarVentana();
    }
}
