package DeustoGym;

import javax.swing.ImageIcon;

import UI.VentanaSesion;

public class Main {
    public static void main(String[] args) {
        // Cargar el ícono desde el archivo .ico en la carpeta img
        ImageIcon icon = new ImageIcon("img/logo.ico");

        VentanaSesion ventanaSesion = new VentanaSesion();
        //ventanaSesion.setIconImage(icon.getImage());  // Establecer el ícono en la ventana
        ventanaSesion.mostrarVentana();
    }
}