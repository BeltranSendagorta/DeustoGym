package DeustoGym;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.StringTokenizer;

import javax.swing.table.DefaultTableModel;

public class DeustoGym {

    // Método para leer el horario desde el archivo CSV
    public String[][] leerHorarioDesdeCSV() {
        String filePath = "Horario2023.csv";  // Cambiar la ruta según tu estructura de carpetas
        return leerHorarioDesdeCSV(filePath);
    }

    // Método para cargar el horario desde el archivo CSV en el modelo de tabla
    public void cargarHorarioDesdeCSV(DefaultTableModel modeloTabla) {
        String[][] horario = leerHorarioDesdeCSV();  // Lee el horario desde el archivo
        if (horario != null) {
            imprimirDatosHorario(horario);  // Imprime los datos leídos
            cargarHorarioEnModelo(modeloTabla, horario);  // Carga el horario en el modelo de tabla
        } else {
            System.out.println("Error: No se pudo leer el horario desde el archivo CSV.");
        }
    }

    // Método para leer el horario desde un archivo CSV dado su ruta
    private String[][] leerHorarioDesdeCSV(String filePath) {
        String[][] horario = null;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // Salta la primera línea (encabezados) al calcular la cantidad de filas
            long rowCount = br.lines().skip(1).count();
            horario = new String[(int) rowCount][8]; // Ajusta el tamaño a 8 para incluir la columna de horas

            // Reinicia el lector para volver a leer desde el principio
            br.close();

            // Vuelve a abrir el archivo para leer los datos reales
            BufferedReader brData = new BufferedReader(new FileReader(filePath));
            // Salta la primera línea (encabezados)
            brData.readLine();

            // Lee los datos y carga en la matriz
            int i = 0;
            String line;
            while ((line = brData.readLine()) != null) {
                StringTokenizer tokens = new StringTokenizer(line, ",");
                // Asegúrate de que hay suficientes tokens antes de intentar acceder a ellos
                if (tokens.countTokens() >= 8) {
                    horario[i][0] = tokens.nextToken(); // Hora
                    for (int j = 1; j < 8; j++) {
                        horario[i][j] = tokens.nextToken(); // Clases
                    }
                    i++;
                } else {
                    System.out.println("Advertencia: No hay suficientes tokens en la línea " + (i + 2) + " del archivo CSV. Se omitirá esta línea.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return horario;
    }

    // Método para imprimir los datos del horario
    private void imprimirDatosHorario(String[][] horario) {
        System.out.println("Datos leídos desde el archivo CSV:");
        for (int i = 0; i < horario.length; i++) {
            for (int j = 0; j < horario[i].length; j++) {
                System.out.print((horario[i][j] != null ? horario[i][j] : "null") + " ");
            }
            System.out.println();
        }
    }

    // Método para cargar el horario en el modelo de tabla
    private void cargarHorarioEnModelo(DefaultTableModel modeloTabla, String[][] horario) {
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

    // Método principal
    public static void main(String[] args) {
        DeustoGym deustoGym = new DeustoGym();
        DefaultTableModel modeloTabla = new DefaultTableModel();
        // ... Resto de tu código ...
        deustoGym.cargarHorarioDesdeCSV(modeloTabla);
    }
}
