package main;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.table.DefaultTableModel;

import domain.Entrenamiento;
import domain.Factura;

public class DeustoGym {
	
	public static List<Entrenamiento> listEntrenamientos = new ArrayList<>();
	public static List<Factura> listF = new ArrayList<>();
	
	
    public String[][] leerHorarioDesdeCSV(String filePath) {
        String[][] horario = null;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            long rowCount = br.lines().skip(1).count();
            horario = new String[(int) rowCount][8];
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine();
            String line;
            int i = 0;
            while ((line = br.readLine()) != null) {
                StringTokenizer tokenizer = new StringTokenizer(line, ",");
                int j = 0;
                while (tokenizer.hasMoreTokens() && j < 8) {
                    horario[i][j] = tokenizer.nextToken();
                    j++;
                }
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return horario;
    }

    public void cargarHorarioDesdeCSV(DefaultTableModel modeloTabla, String[] actividadesDisponibles) {
        modeloTabla.setRowCount(0);
        for (String actividad : actividadesDisponibles) {
            modeloTabla.addRow(new Object[]{actividad, "", "", "", "", "", "", ""});
        }
    }
    
    public void guardarProductos(String nombreFic) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nombreFic));
			oos.writeObject(listF);
			System.out.println("guardar");
			oos.close();
		}catch(IOException e){
			System.out.println("ERROR EN ESCRITURA de fichero: " + nombreFic);
			System.out.println(e);
		}
	}
    
    public void cargarProductos(String nombreFic) {
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nombreFic));
			@SuppressWarnings("unchecked")
			List<Factura> cCargado = (List<Factura>) ois.readObject();
			System.out.println(cCargado);
			System.out.println("cargado");
			listF=cCargado;
			ois.close();
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("ERROR EN LA CARGA de fichero: " + nombreFic);
			System.out.println(e);
		}
	}
}
