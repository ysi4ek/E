package Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
public abstract class AbstractRepository {
	
	private static final Logger LOGGER = Logger.getLogger(AbstractRepository.class);
	
	private static ResourceBundle dbConnectionConfig = ResourceBundle.getBundle("dbconnection");
	private String url;
	private String user;
	private String password;
	
	public AbstractRepository() {
		super();
		this.url = dbConnectionConfig.getString("url");
		this.user = dbConnectionConfig.getString("user");
		this.password = dbConnectionConfig.getString("password");
	}

	protected Connection getConnection(){
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			LOGGER.error("Impossible to create connection with next values: URL: " + url + ", USER: " + user + ", PASSWORD: " + password);
		}
		return connection;
	}
	
	protected void closeConnection(Connection connection){
		if(connection != null){
			try {
				connection.close();
			} catch (SQLException e) {
				LOGGER.error("Impossible to close connection: " + connection);

			}
		}
	}
	
}