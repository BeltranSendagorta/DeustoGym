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
				sent = "CREATE TABLE Entrenamiento (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre varchar(100), dia String, tipEntr int, horaI varchar(100), horaF varchar(100), precio int, idMonitor varchar(9) REFERENCES Persona (dni));";
				logger.log( Level.INFO, "Statement: " + sent );
				statement.executeUpdate( sent );
				sent = "DROP TABLE IF EXISTS EntrUsu";
				logger.log( Level.INFO, "Statement: " + sent );
				statement.executeUpdate( sent );
				sent = "CREATE TABLE EntrUsu (id INTEGER PRIMARY KEY AUTOINCREMENT, idEntr REFERENCES Entrenamiento (id), espera int, idPersona REFERENCES Persona (dni));";
				logger.log( Level.INFO, "Statement: " + sent );
				statement.executeUpdate( sent );
				sent = "DROP TABLE IF EXISTS Suscripcion";
				logger.log( Level.INFO, "Statement: " + sent );
				statement.executeUpdate( sent );
				sent = "CREATE TABLE Suscripcion (idU INTEGER PRIMARY KEY, tipoSus int, descuento int, FOREIGN KEY (idU) References Persona (dni));";
				logger.log( Level.INFO, "Statement: " + sent );
				statement.executeUpdate( sent );
			}
			return true;
		} catch(Exception e) {
			logger.log( Level.SEVERE, "Excepción", e );
			return false;
		}
	}
	
	
	/** Busca a una persona por dni y por su tipo
	 * @param dni Dni de la persona a buscar
	 * @param tipo Tipo de Persona (0 Admin, 1 Usuario, 2 Monitor)
	 * @return la Persona
	 */
	public static Persona getPersona(String dni, int tipo) {
		Persona p = null;
		try (Statement statement = conexion.createStatement(); Statement st = conexion.createStatement()){			
			String sent = "select * from Persona where dni = '" + dni+"' ;";
			logger.log( Level.INFO, "Statement: " + sent );
			ResultSet rs = statement.executeQuery( sent );
			int dato = 0;
			String dat = "";
			while( rs.next() ) { // Leer el resultset
				dato = rs.getInt("tipo");
				if(dato == tipo) {
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
							sent = "select * from Suscripcion where idU = "+ rs.getString("dni") +";";
							logger.log( Level.INFO, "Statement: " + sent );
							ResultSet rs2 = st.executeQuery( sent );
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
							ResultSet rs2 = st.executeQuery( sent );
							while(rs2.next()) {
								((Monitor) p).getClasesHabilitadas().add(TiposEntrenamientos.values()[rs2.getInt("tipoEntr")]);
							}
							break;
						}
						default: p= new Usuario();
					}
					dat = rs.getString("dni");
					p.setDni(dat);
					dat = rs.getString("nombre");
					p.setNombre(dat);
					dat = rs.getString("apellido");
					p.setApellido(dat);
					dato = rs.getInt("edad");
					p.setEdad(dato);
					return p;
				}else {
					break;
				}
			}
		} catch (Exception e) {
			logger.log( Level.SEVERE, "Excepción", e );
		}	
		return p;
	}
	/**
	 * Coge a las personas de la base de datos (las divide entre, Usuario, Administrador, Monitor)  
	 */
	public static void getPersonas(){
		try (Statement statement = conexion.createStatement(); Statement st = conexion.createStatement()){			
			String sent = "select * from Persona;";
			logger.log( Level.INFO, "Statement: " + sent );
			ResultSet rs = statement.executeQuery( sent );
			int dato = 0;
			String dat = "";
			Persona p;
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
						sent = "select * from Suscripcion where idU = "+ rs.getString("dni") +";";
						logger.log( Level.INFO, "Statement: " + sent );
						ResultSet rs2 = st.executeQuery( sent );
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
						ResultSet rs2 = st.executeQuery( sent );
						while(rs2.next()) {
							((Monitor) p).getClasesHabilitadas().add(TiposEntrenamientos.values()[rs2.getInt("tipoEntr")]);
						}
						break;
					}
					default: p= new Usuario();
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
	
	/** Recibe todos los entrenamientos
	 * @param id
	 * @return
	 */
	public static int getEntrenamiento() {
		try (Statement statement = conexion.createStatement(); Statement st = conexion.createStatement()){		
			String sent = "select * from Entrenamiento;";
			logger.log( Level.INFO, "Statement: " + sent );
			ResultSet rs = statement.executeQuery( sent );
			int dato = 0;
			String dat = "";
			Entrenamiento entr = new Entrenamiento();
			while(rs.next()) {
				dato = rs.getInt("id");
				entr.setId(dato);
				dat=rs.getString("nombre");
				entr.setNombre(dat);
				dato = rs.getInt("tipEntr");
				entr.setTipo(TiposEntrenamientos.values()[dato]);
				dat = rs.getString("horaI");
				entr.setHoraInicio(dat);
				dat = rs.getString("horaF");
				entr.setHoraFin(dat);
				dato = rs.getInt("precio");
				entr.setPrecio(dato);
				dat = rs.getString("dia");
				entr.setdiaSe(dat);
				if(!personas.isEmpty()) {
						getPersonas();
				}
				entr.setMonitor((Monitor) personas.get(rs.getString("idMonitor")));
			
				sent = "select * from EntrUsu where idEntr = " + entr.getId() + ";";
				logger.log( Level.INFO, "Statement: " + sent );
				ResultSet rs2 = st.executeQuery( sent );
				
				while(rs2.next()) {
					if(rs2.getInt("espera") == 1) {
						entr.getListaEspera().add((Usuario) personas.get(rs2.getString("idPersona")));
					}else {
						entr.getAsistentes().add((Usuario) personas.get(rs2.getString("idPersona")));
					}
				}
				entrenamientos.add(entr);
			}
		} catch (Exception e) {
			logger.log( Level.SEVERE, "Excepción", e );
		}
		return 0;
	}
	
	/** Inserta los entrenamientos a la tabla de Entrenamiento, tambien añade los usuarios a la tabla de EntrUsu
	 * @param entrenamiento Entrenamiento a insertar
	 * @return
	 */
	public static int insertarEntrena(Entrenamiento entrenamiento) {
		abrirConexion("resources/db/BaseDatos.db", false);
		try (Statement statement = conexion.createStatement(); Statement st = conexion.createStatement()) {
			String sent = "INSERT INTO Entrenamiento (tipEntr, nombre,dia ,horaI, horaF, precio, idMonitor) VALUES ('" +
		               entrenamiento.getTipo().ordinal() + "', '"+ entrenamiento.getNombre() + "', '"+entrenamiento.getdiaSe() + "', '" + entrenamiento.getHoraInicio() + "', '" + entrenamiento.getHoraFin() + "', " +
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
			entrenamientos.add(entrenamiento);
			for(Usuario u: entrenamiento.getAsistentes()) {
				sent = "INSERT INTO EntrUsu (idEntr, espera, idPersona) VALUES (" +
			               entrenamiento.getId() + ", 0, '" + u.getDni() + "');";
				logger.log(Level.INFO, "Statement: " + sent);
				insertados = st.executeUpdate(sent);
			}
			
			for(Usuario u: entrenamiento.getListaEspera()) {
				sent = "INSERT INTO EntrUsu (idEntr, espera, idPersona) VALUES (" +
			               entrenamiento.getId() + ", 1, '" + u.getDni() + "');";
				logger.log(Level.INFO, "Statement: " + sent);
				insertados = st.executeUpdate(sent);
			}
			
			return 1;
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Excepción", e);
			return 0;
		}
	}
	
	/** Inserta una persona nueva en la BD
	 * @param p Persona a insertar
	 * @return true si ha ido bien, false si no.
	 */
	public static boolean insertarPersona(Persona p) {
		abrirConexion("resources/db/BaseDatos.db", false);
		try (Statement statement = conexion.createStatement()) {
			String sent = "";
			String sent2 = "";
			if(p instanceof Monitor) {
				sent = "INSERT INTO Persona (dni, nombre, apellido, edad, contrasena, tipo) VALUES ('" +
		        p.getDni() + "', '" + p.getNombre() + "', '" + p.getApellido() + "', " +
		        p.getEdad() + ", '" + ((Monitor) p).getContrasena() + "', 2);";
			}else if(p instanceof Usuario) {
				sent = "INSERT INTO Persona (dni, nombre, apellido, edad, contrasena, tipo) VALUES ('" +
				p.getDni() + "', '" + p.getNombre() + "', '" + p.getApellido() + "', " +
				p.getEdad() + ", '" + ((Usuario) p).getContrasenia() + "', 1);";
				
			}else if(p instanceof Administrador){
				sent = "INSERT INTO Persona (dni, nombre, apellido, edad, contrasena, tipo) VALUES ('" +
				p.getDni() + "', '" + p.getNombre() + "', '" + p.getApellido() + "', " +
				p.getEdad() + ", '" + ((Administrador) p).getContrasenia() + "', 0);";
			}
			logger.log(Level.INFO, "Statement: " + sent);
			int insertados = statement.executeUpdate(sent);
			if (insertados != 1) return false; // Error en inserción
			if(p instanceof Monitor) {
				Monitor m = (Monitor) p;
				for(TiposEntrenamientos te: m.getClasesHabilitadas()) {
					sent2 = "INSERT INTO MonitorTipoEntr (tipoEntr, idP) VALUES (" +
			               te.ordinal() + ", '" + m.getDni() + "');";
					insertados = statement.executeUpdate(sent2);
				}
			}else if(p instanceof Usuario) {
				Usuario u = (Usuario) p;
				sent2 = "INSERT INTO Suscripcion (tipoSus, descuento, idU) VALUES (" +
				        u.getS().getTp().ordinal() + ", " + u.getS().getDescuento() + ", '" + u.getDni() + "');";
					insertados = statement.executeUpdate(sent2);
			}
			personas.put(p.getDni(), p);
			return true;
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Excepción", e);
			return false;
		}
	}
	
	public static void actualizarBD() {
		try {
			abrirConexion("resources/db/BaseDatos.db", true);
			logger.log(Level.INFO, "Se ha borrado la base de datos");
		}catch (Exception e) {
			logger.log(Level.SEVERE, "Excepción", e);
			logger.log(Level.INFO, "No se ha borrado la base de datos");
		}
		List<Entrenamiento> entrs = entrenamientos;
		Map<String, Persona> pers = personas;
		entrenamientos.clear();
		personas.clear();
		for (Entrenamiento entrenamiento : entrs) {
			insertarEntrena(entrenamiento);
		}
		for(Persona p: pers.values()) {
			insertarPersona(p);
		}
		logger.log(Level.INFO, "datos reinsertados en la base de datos");
	}
	
	public static void posibleID(Entrenamiento entrena) {
		insertarEntrena(entrena);
	}
	
}
