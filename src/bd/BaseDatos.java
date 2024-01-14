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
	public static Map<String, Persona> personas = new HashMap<>();
	public static List<Entrenamiento> entrenamientos = new ArrayList<>();
	
	
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
				sent = "CREATE TABLE Entrenamiento (id INTEGER PRIMARY KEY AUTOINCREMENT, duracion int, tipEntr int, horaI varchar(100), horaF varchar(100), precio int, idMonitor varchar(9) REFERENCES Persona (dni));";
				logger.log( Level.INFO, "Statement: " + sent );
				statement.executeUpdate( sent );
				sent = "DROP TABLE IF EXISTS EntrUsu";
				logger.log( Level.INFO, "Statement: " + sent );
				statement.executeUpdate( sent );
				sent = "CREATE TABLE EntrUsu (id INTEGER PRIMARY KEY AUTOINCREMENT, idEntr REFERENCES Entrenamiento (id), espera int, idPersona REFERENCES Persona (dni));";
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
			logger.log( Level.SEVERE, "Excepción", e );
		}
	}
	
	public static int getEntrenamiento(boolean id) {
		try (Statement statement = conexion.createStatement()){		
			if(id) {	
				String sent = "select * from Entremaniento;";
				logger.log( Level.INFO, "Statement: " + sent );
				ResultSet rs = statement.executeQuery( sent );
				int dato = 0;
				String dat = "";
				Entrenamiento entr = new Entrenamiento();
				while(rs.next()) {
					dato = rs.getInt("id");
					entr.setId(dato);
					dato = rs.getInt("tipEntr");
					entr.setTipo(TiposEntrenamientos.values()[dato]);
					dat = rs.getString("horaI");
					entr.setHoraInicio(dat);
					dat = rs.getString("horaF");
					entr.setHoraFin(dat);
					dato = rs.getInt("precio");
					entr.setPrecio(dato);
					dato = rs.getInt("duracion");
					entr.setDuracion(dato);
					if(!personas.isEmpty()) {
							getPersonas();
					}
					entr.setMonitor((Monitor) personas.get(rs.getString("idMonitor")));
					
					sent = "select * from EntrUsu where idEntr = " + entr.getId() + ";";
					logger.log( Level.INFO, "Statement: " + sent );
					ResultSet rs2 = statement.executeQuery( sent );
					
					while(rs2.next()) {
						if(rs2.getInt("espera") == 1) {
							entr.getListaEspera().add((Usuario) personas.get(rs2.getString("idPersona")));
						}else {
							entr.getAsistentes().add((Usuario) personas.get(rs2.getString("idPersona")));
						}
					}
					entrenamientos.add(entr);
				}
			}
		} catch (Exception e) {
			logger.log( Level.SEVERE, "Excepción", e );
		}
		return 0;
	}
	
	/** Inserta los entrenamientos a la tabla de Entrenamiento, tambien añade los usuarios a la tabla de EntrUsu
	 * @param entrenamiento
	 * @return
	 */
	public static int insertarEntrena(Entrenamiento entrenamiento) {
		int tipo=0;
		for(;tipo < TiposEntrenamientos.values().length; tipo++) {
			if(TiposEntrenamientos.values()[tipo].equals(entrenamiento.getTipo()));
		}
		try (Statement statement = conexion.createStatement()) {
			abrirConexion("BaseDatos.db", false);
			String sent = "INSERT INTO Entrenamiento (tipEntr, horaI, horaF, precio, idMonitor) VALUES ('" +
		               tipo + "', '" + entrenamiento.getHoraInicio() + "', '" + entrenamiento.getHoraFin() + "', " +
		               entrenamiento.getPrecio() + ", '" + entrenamiento.getMonitor().getDni() + "');";
			
			logger.log(Level.INFO, "Statement: " + sent);
			int insertados = statement.executeUpdate(sent);

			if (insertados != 1)
				return 0; // Error en inserción
			
			ResultSet rrss = statement.getGeneratedKeys(); // Genera un resultset ficticio con las claves generadas del
															// último comando
			rrss.next(); // Avanza a la única fila
			int pk = rrss.getInt(1); // Coge la única columna (la primary key autogenerada)
			entrenamiento.setId(pk);
			
			for(Usuario u: entrenamiento.getAsistentes()) {
				sent = "INSERT INTO EntrUsu (idEntr, espera, idPersona) VALUES (" +
			               entrenamiento.getId() + ", 0, '" + u.getDni() + "');";
				logger.log(Level.INFO, "Statement: " + sent);
				insertados = statement.executeUpdate(sent);
			}
			
			for(Usuario u: entrenamiento.getListaEspera()) {
				sent = "INSERT INTO EntrUsu (idEntr, espera, idPersona) VALUES (" +
			               entrenamiento.getId() + ", 1, '" + u.getDni() + "');";
				logger.log(Level.INFO, "Statement: " + sent);
				insertados = statement.executeUpdate(sent);
			}
			
			return 1;
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Excepción", e);
			return 0;
		}
	}
	
	public static int insertarPersona() {
		
		return 0;
	}
	public static void posibleID(Entrenamiento entrena) {
		insertarEntrena(entrena);
	}
	
}
