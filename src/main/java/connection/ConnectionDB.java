package connection;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionDB {
    private static final Logger LOGGER = Logger.getLogger(ConnectionDB.class.getName());

    private String urlDatabase;
    private String usernameDatabase;
    private String passwordDatabase;
    private static volatile ConnectionDB instance;


    public ConnectionDB() {
        loadProperties();
    }

    public static ConnectionDB getInstance() {
        if (instance == null){
            synchronized (ConnectionDB.class){
                instance = new ConnectionDB();
            }
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(urlDatabase, usernameDatabase, passwordDatabase);
    }

    private void loadProperties() {
        try(InputStream input = new FileInputStream("src/main/resources/config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            urlDatabase = prop.getProperty("url");
            usernameDatabase = prop.getProperty("user");
            passwordDatabase = prop.getProperty("password");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error loading properties file -> ", e.getMessage());
        }
    }

}
