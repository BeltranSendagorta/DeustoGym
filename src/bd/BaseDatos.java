package bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import domain.Administrador;
import domain.Entrenamiento;
import domain.Monitor;
import domain.Persona;
import domain.Suscripcion;
import domain.TipoSuscripcion;
import domain.TiposEntrenamientos;
import domain.Usuario;



public class BaseDatos {
	private static Connection conexion;
	private static Logger logger = Logger.getLogger( "BaseDeDatos" );
	private static Map<String, Persona> personas = new HashMap<>();
	private static List<Entrenamiento> listEntre = new ArrayList<>();
	
	
	/** Abre conexión con la base de datos
	 * @param nombreBD	Nombre del fichero de base de datos
	 * @param reiniciaBD	true si se quiere reiniciar la base de datos (se borran sus contenidos si los tuviera y se crean datos por defecto)
	 * @return	true si la conexión ha sido correcta, false en caso contrario
	 */
	public static boolean abrirConexion( String nombreBD, boolean reiniciaBD ) {
		try {
			logger.log( Level.INFO, "Carga de librería org.sqlite.JDBC" );
			Class.forName("org.sqlite.JDBC");  // Carga la clase de BD para sqlite
			logger.log( Level.INFO, "Abriendo conexión con " + nombreBD );
			conexion = DriverManager.getConnection("jdbc:sqlite:" + nombreBD );
			if (reiniciaBD) {
				Statement statement = conexion.createStatement();
				String sent = "DROP TABLE IF EXISTS Persona";
				logger.log( Level.INFO, "Statement: " + sent );
				statement.executeUpdate( sent );
				sent = "CREATE TABLE Persona (dni varchar(9) PRIMARY KEY, nombre varchar(100), apellido varchar(100), edad int, contrasena varchar(100), tipo int);";
				logger.log( Level.INFO, "Statement: " + sent );
				statement.executeUpdate( sent );
				sent = "DROP TABLE IF EXISTS MonitorTipoEntr";
				logger.log( Level.INFO, "Statement: " + sent );
				statement.executeUpdate( sent );
				sent = "CREATE TABLE MonitorTipoEntr (id INTEGER PRIMARY KEY AUTOINCREMENT, tipoEntr int, idP Key references Persona (dni));";
				logger.log( Level.INFO, "Statement: " + sent );
				statement.executeUpdate( sent );
				sent = "DROP TABLE IF EXISTS Entrenamiento";
				logger.log( Level.INFO, "Statement: " + sent );
				statement.executeUpdate( sent );
				sent = "CREATE TABLE Entrenamiento (id INTEGER PRIMARY KEY AUTOINCREMENT, tipEntr int, horaI varchar(100), horaF varchar(100), precio int, idMonitor INTEGER REFERENCES Persona (dni));";
				logger.log( Level.INFO, "Statement: " + sent );
				statement.executeUpdate( sent );
				sent = "DROP TABLE IF EXISTS EntrUsu";
				logger.log( Level.INFO, "Statement: " + sent );
				statement.executeUpdate( sent );
				sent = "CREATE TABLE EntrUsu (id INTEGER PRIMARY KEY AUTOINCREMENT, idEntr KEY REFERENCES Entrenamiento (id), espera int, idPersona KEY REFERENCES Persona (dni));";
				logger.log( Level.INFO, "Statement: " + sent );
				statement.executeUpdate( sent );
				sent = "DROP TABLE IF EXISTS Suscripccion";
				logger.log( Level.INFO, "Statement: " + sent );
				statement.executeUpdate( sent );
				sent = "CREATE TABLE Suscripccion (idU INTEGER PRIMARY KEY, tipoSus int, descuento int, FOREIGN KEY (idU) References Persona (dni));";
				logger.log( Level.INFO, "Statement: " + sent );
				statement.executeUpdate( sent );
			}
			return true;
		} catch(Exception e) {
			logger.log( Level.SEVERE, "Excepción", e );
			return false;
		}
	}
	public static void getPersonas(){
		try (Statement statement = conexion.createStatement()){			
			String sent = "select * from Persona;";
			logger.log( Level.INFO, "Statement: " + sent );
			ResultSet rs = statement.executeQuery( sent );
			int dato = 0;
			String dat = "";
			Persona p = new Persona() {};
			while( rs.next() ) { // Leer el resultset
				dato = rs.getInt("tipo");
				switch (dato) {
					case 0: { //Admin
						p = new Administrador();
						dat = rs.getString("contrasena");
						((Administrador) p).setContrasenia(dat);
						break;
					}
					case 1: { //Usuario
						p = new Usuario();
						dat = rs.getString("contrasena");
						((Usuario) p).setContrasenia(dat);
						sent = "select * from Suscripccion where idU = "+ rs.getString("dni") +";";
						logger.log( Level.INFO, "Statement: " + sent );
						ResultSet rs2 = statement.executeQuery( sent );
						while(rs2.next()) {
							((Usuario) p).setS(new Suscripcion(TipoSuscripcion.values()[rs2.getInt("tipoSus")], rs2.getInt("descuento")));
						}
						break;
					}
					
					case 2: { //Monitor
						p = new Monitor();
						dat = rs.getString("contrasena");
						((Monitor) p).setContrasenia(dat);
						sent = "select * from MonitorTipoEntr where idP = "+ rs.getString("dni") +";";
						logger.log( Level.INFO, "Statement: " + sent );
						ResultSet rs2 = statement.executeQuery( sent );
						while(rs2.next()) {
							((Monitor) p).getClasesHabilitadas().add(TiposEntrenamientos.values()[rs2.getInt("tipoEntr")]);
						}
						break;
					}
					default: p= new Persona() {};
				}
				dat = rs.getString("dni");
				p.setDni(dat);
				dat = rs.getString("nombre");
				p.setNombre(dat);
				dat = rs.getString("apellido");
				p.setApellido(dat);
				dato = rs.getInt("edad");
				p.setEdad(dato);
				personas.put(p.getDni(), p);
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.log( Level.SEVERE, "Excepción", e );
		}
	}
}
