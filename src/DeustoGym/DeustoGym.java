package DeustoGym;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import javax.swing.table.DefaultTableModel;

public class DeustoGym {

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

}
