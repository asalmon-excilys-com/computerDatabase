package excilys.main.orm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class GestionConnection {

	public static final String URL = "jdbc:mysql://localhost:3306/computerDatabase";
	public static final String USER = "root";
	public static final String PASSWORD = "root";

	final static Logger logger = LoggerFactory
			.getLogger(GestionConnection.class);

	private GestionConnection() {
		try {
			//Utile sous Tomcat, inutile sur glassfish ou autres.
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			logger.error("Impossible de charger le driver MySQL"
					+ e.getMessage());
			System.exit(1);
		}
		this.threadConnection = new ThreadLocal<Connection>() {
			@Override
			public Connection initialValue() {
				Connection conn;
				try {
					conn = DriverManager.getConnection(URL, USER, PASSWORD);
					conn.setAutoCommit(false);
					threadConnection.set(conn);
				} catch (SQLException e) {
					logger.error("Erreur de recuperation de la connexion"
							+ e.getMessage());
					return null;
				}
				return conn;
			}
		};
	}
	
	


	public ThreadLocal<Connection> threadConnection;

	public Connection getConnection() {

		 return threadConnection.get();

	}

	public void closeConnection() {

		try {
			if (threadConnection.get() != null) {
				threadConnection.get().close();
			}
		} catch (SQLException e) {
			logger.error("Erreur lors de la fermeture de la connexion"
					+ e.getMessage());
		} finally {
			threadConnection.remove();
		}
	}
}
