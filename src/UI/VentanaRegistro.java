package UI;


import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import DeustoGym.GestorUsuariosBD;
import DeustoGym.Usuario;

public class VentanaRegistro {
    private JFrame frame;
    private GestorUsuariosBD gestorBD;

    public VentanaRegistro() {
        frame = new JFrame("Ventana de Registro");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setLayout(new BorderLayout());
        
        gestorBD = new GestorUsuariosBD();
        

        // Panel principal con GroupLayout
        JPanel panelRegistro = new JPanel();
        GroupLayout layout = new GroupLayout(panelRegistro);
        panelRegistro.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        JLabel nombreLabel = new JLabel("Nombre:");
        JTextField nombreTextField = new JTextField();
        JLabel apellidoLabel = new JLabel("Apellido:");
        JTextField apellidoTextField = new JTextField();

        JLabel edadLabel = new JLabel("Edad:");
        JTextField  edadTextField = new JTextField();

        JLabel usuarioLabel = new JLabel("Nombre de Usuario:");
        JTextField usuarioTextField = new JTextField();
        JLabel contraseñaLabel = new JLabel("Contraseña:");
        JPasswordField contraseñaPasswordField = new JPasswordField();

        JButton registrarmeButton = new JButton("Registrarme");
        registrarmeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = nombreTextField.getText();
                String apellido = apellidoTextField.getText();
                String edad = edadTextField.getText();
                String usuario = usuarioTextField.getText();
                String contraseña = new String(contraseñaPasswordField.getPassword());
                Usuario nuevoUsuario = new Usuario(nombre, apellido, Integer.parseInt(edad), contraseña, usuario);

                gestorBD.agregarUsuario(nuevoUsuario);

                JOptionPane.showMessageDialog(frame, "Ha sido registrado de manera correcta");
                VentanaInicioSesionUsuario ventanaInicioSesionUsuario = new VentanaInicioSesionUsuario();
                ventanaInicioSesionUsuario.mostrarVentana();
                frame.dispose();
            }
        });
        
        
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(nombreLabel)
                        .addComponent(apellidoLabel)
                        .addComponent(edadLabel)  
                        .addComponent(usuarioLabel)
                        .addComponent(contraseñaLabel)
                        .addComponent(registrarmeButton))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(nombreTextField)
                        .addComponent(apellidoTextField)
                        .addComponent(edadTextField)  
                        .addComponent(usuarioTextField)
                        .addComponent(contraseñaPasswordField)));

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(nombreLabel)
                        .addComponent(nombreTextField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(apellidoLabel)
                        .addComponent(apellidoTextField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(edadLabel)
                        .addComponent(edadTextField))  
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(usuarioLabel)
                        .addComponent(usuarioTextField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(contraseñaLabel)
                        .addComponent(contraseñaPasswordField))
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
