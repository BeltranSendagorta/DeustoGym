package DeustoGym;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.StringTokenizer;

import javax.swing.table.DefaultTableModel;

public class DeustoGym {


	public String[][] leerHorarioDesdeCSV(String filePath) {
	    String[][] horario = null;

	    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
	        // Salta la primera línea (encabezados) al calcular la cantidad de filas
	        long rowCount = br.lines().skip(1).count();
	        horario = new String[(int) rowCount][8]; // Ajusta el tamaño a 8 para incluir la columna de horas

	        // Reinicia el lector para volver a leer desde el principio
	        br.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
	        // Salta la primera línea (encabezados)
	        br.readLine();

	        String line;
	        int i = 0;
	        while ((line = br.readLine()) != null) {
	            StringTokenizer tokenizer = new StringTokenizer(line, ",");
	            int j = 0;
	            while (tokenizer.hasMoreTokens() && j < 8) { // Ajusta el tamaño a 8
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


	
	public void cargarHorarioDesdeCSV(DefaultTableModel modeloTabla, String[][] horario) {
	    // Limpiar la tabla antes de cargar nuevos datos
	    modeloTabla.setRowCount(0);

	    // Iterar sobre la matriz bidimensional de horarios
	    for (String[] row : horario) {
	        // Obtener la hora y las clases para cada fila
	        String hora = row[0];
	        String[] clases = Arrays.copyOfRange(row, 1, row.length); // Excluir la columna de horas

	        // Agregar una nueva fila al modelo de tabla
	        if (hora != null && !hora.isEmpty()) { // Asegúrate de que la hora no sea nula ni esté vacía
	            modeloTabla.addRow(new Object[]{hora, clases[0], clases[1], clases[2], clases[3], clases[4], clases[5], clases[6]});
	        } else {
	            // Tratar el caso en que no hay suficientes elementos en el array
	            System.out.println("Error: No hay suficientes elementos en el array 'clases'");
	        }
	    }
	}



}
