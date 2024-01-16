package io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import bd.BaseDatos;
import domain.Entrenamiento;
import domain.Factura;
import domain.TipoSuscripcion;
import domain.Usuario;

public class DeustoGym {
	
	public static List<Factura> listF = new ArrayList<>();
	private static Logger logger = Logger.getLogger( "DeustoGym" );
	private static final String CSV_FILE = "resources/data/facturas.csv";
	private static List<String> combinaciones = new ArrayList<String>(); 
	
	 public static void guardarFacturas() {
	        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE, true))) {
	            for (Factura factura : listF) {
	            	factura.getPrecioFinal();
	                StringBuilder record = new StringBuilder();
	                record.append(factura.getUsuario().getDni()).append(",");
	                record.append(factura.getPrecioFinal()).append(",");
	                record.append(String.join("|||", factura.getPagos())).append(",");
	                record.append(factura.isPagado());
	                writer.write(record.toString());
	                writer.newLine();
	    			logger.log(Level.INFO, "Guardando CSV");
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

    public static void leerFacturas() {
        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String dni = parts[0];
                    int precioFinal = Integer.parseInt(parts[1]);
                    String pagos = parts[2];
                    boolean pagado = Boolean.parseBoolean(parts[3]);
                    Usuario usuario = (Usuario) BaseDatos.personas.get(dni);;
                    Factura factura = new Factura(usuario);
                    factura.setPrecioFinal(precioFinal);
                    factura.setPagos(Arrays.asList(pagos.split("|||")));
                    factura.setPagado(pagado);
                    listF.add(factura);
        			logger.log(Level.INFO, "CargadoCSV");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void guardarProductos(String nombreFic) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nombreFic));
			oos.writeObject(listF);
			oos.close();
			logger.log(Level.INFO, "Guardando .dat");
		}catch(IOException e){
			System.out.println("ERROR EN ESCRITURA de fichero: " + nombreFic);
			System.out.println(e);
		}
	}
    
    public static void cargarProductos(String nombreFic) {
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nombreFic));
			@SuppressWarnings("unchecked")
			List<Factura> cCargado = (List<Factura>) ois.readObject();
			listF=cCargado;
			logger.log(Level.INFO, "Cargado .dat");
			ois.close();
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("ERROR EN LA CARGA de fichero: " + nombreFic);
			System.out.println(e);
		}
	}
    
// 	  RECURSIVIDAD: CUANTOS ENTRENAMIENTOS SE PUEDEN HACER CON X DINERO
//    T3
//    Calcular las compras posibles con el dinero disponible sobrando 100 euros como mucho
 	public static void calcularComprasPosibles( double disponible, double sobra, Usuario u ) {
 		if(!u.getS().getTp().equals(TipoSuscripcion.Vip)) {
 			Factura f = new Factura(u);
 			ArrayList<Entrenamiento> entrs = (ArrayList<Entrenamiento>) BaseDatos.entrenamientos;
 			combinaciones.clear();
 			for(Entrenamiento e: entrs) {
 				if(e.getAsistentes().contains(u)) e.getAsistentes().remove(u);
 			}
 			calcularComprasPosiblesRecursivo(f, disponible, sobra ,"", entrs);
 			System.out.println(String.join("\n", combinaciones));
 		}else System.out.println("Al ser vip no tienes gastos extra");
 	}
 	
 	public static void calcularComprasPosiblesRecursivo(Factura f, double disponible, double sobra ,String combinaciones, List<Entrenamiento> entrs) {
 		//Caso Base
 		if(disponible<sobra) {
 			DeustoGym.combinaciones.add(combinaciones + "dinero Restante: " + disponible);
 			return ;
 		}
 		else {
 			//Caso Recursivo
 			for(Entrenamiento e:entrs) {
 				if(e.getAsistentes().size()<5 && !e.getAsistentes().contains(f.getUsuario())) {
 					e.agregarAsistente(f.getUsuario());
 					if(disponible-((double) f.getPrecioFinal()) > 0) {
 						calcularComprasPosiblesRecursivo(f, disponible-((double)f.getPrecioFinal()), sobra, combinaciones + e.getNombre() + ", ", entrs);
 	 					e.getAsistentes().remove(f.getUsuario());
 	 				}
 				}
 			}	
 		}
 	}
}