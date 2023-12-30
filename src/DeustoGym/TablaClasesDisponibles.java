package DeustoGym;

import javax.swing.JTable;
import DeustoGym.DeustoGym;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class TablaClasesDisponibles {




    public JTable crearClasesDisponibles(String[][] horario) {
        JTable tabla = new JTable() {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component comp = super.prepareRenderer(renderer, row, column);
                int rendererWidth = comp.getPreferredSize().width;
                TableColumn tableColumn = getColumnModel().getColumn(column);
                tableColumn.setPreferredWidth(100);
                setRowHeight(25);
                return comp;
            }
        };

        tabla.getTableHeader().setReorderingAllowed(false);
        tabla.setGridColor(Color.BLACK);
        tabla.setShowGrid(true);
        tabla.setCellSelectionEnabled(true);

        // Modelo de tabla
        DefaultTableModel modeloTabla = new DefaultTableModel(0, 7) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return row > 0;
            }
        };

        modeloTabla.setColumnIdentifiers(
                new Object[]{"Hora", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"});

        // Crear una instancia de DeustoGym
        DeustoGym deustoGym = new DeustoGym();

        // Llamar al método cargarHorarioDesdeCSV con la instancia de DeustoGym
        deustoGym.cargarHorarioDesdeCSV(modeloTabla);

        // Verificar si hay datos en el modelo antes de asignarlo a la tabla
        if (modeloTabla.getRowCount() == 0) {
            // Imprime algún mensaje de depuración o lanza una excepción para identificar el problema.
            System.out.println("El modelo de la tabla está vacío. ¡Verifica la carga de datos desde el CSV!");
        }

        tabla.setModel(modeloTabla);

        tabla.getTableHeader().setReorderingAllowed(false);
        JTableHeader tableHeader = tabla.getTableHeader();
        if (tableHeader != null) {
            tableHeader.setReorderingAllowed(false);
        }

        tabla.setGridColor(Color.BLACK);
        tabla.setShowGrid(true);
        tabla.setCellSelectionEnabled(true);

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                String actividad = (value != null) ? value.toString() : "";

                ImageIcon icono;
            	switch (actividad) {
				case "Yoga":
					c.setBackground(Color.PINK);
					icono = resizeImage("img/yoga.png");
					break;
				case "Spinning":
					c.setBackground(Color.GREEN);
					icono = resizeImage("img/spinning.png");
					break;
				case "Core":
					c.setBackground(Color.YELLOW);
					icono = resizeImage("img/core.png");
					break;
				case "Boxeo":
					c.setBackground(new Color(128, 191, 255));
					icono = resizeImage("img/boxeo.png");
					break;
				case "Aeroyoga":
					c.setBackground(Color.YELLOW);
					icono = resizeImage("img/aeroyoga.png");
					break;
				case "Pilates":
					c.setBackground(Color.RED);
					icono = resizeImage("img/pilates.png");
					break;
				case "HIIt":
					c.setBackground(Color.GRAY);
					icono = resizeImage("img/hiit.png");
					break;
				case "Funcional":
					c.setBackground(new Color(139, 69, 19)); // Marrón
					icono = resizeImage("img/funcional.png");
					break;

				default:
					c.setBackground(table.getBackground());
					icono = null;
				}

				if (icono != null) {
					JLabel label = new JLabel();
					label.setHorizontalAlignment(JLabel.CENTER);
					label.setIcon(icono);
					label.setOpaque(true);
					label.setBackground(table.getBackground());
					return label;
				}

				return c;
			}

            private ImageIcon resizeImage(String imagePath) {
                ImageIcon originalIcon = new ImageIcon(imagePath);
                Image image = originalIcon.getImage();
                Image resizedImage = image.getScaledInstance(35, 20, java.awt.Image.SCALE_SMOOTH);
                return new ImageIcon(resizedImage);
            }
        };

        for (int i = 0; i < modeloTabla.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }

        return tabla;
    }


}

