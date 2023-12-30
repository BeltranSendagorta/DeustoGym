package DeustoGym;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import UI.VentanaSesion;

public class main {
    public static void main(String[] args) {
        ImageIcon icon = new ImageIcon("img/logo.ico");
        VentanaSesion ventanaSesion = new VentanaSesion();
        ventanaSesion.mostrarVentana();
    }
}