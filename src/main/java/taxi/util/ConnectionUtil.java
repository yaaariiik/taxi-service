package taxi.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectionUtil {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/taxi_service_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "password";
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final Logger logger = LogManager.getLogger(ConnectionUtil.class);

    static {
        logger.info("Static block was called");
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            logger.error("Can't find SQL Driver, reason: {}", e.getMessage());
            throw new RuntimeException("Can't find SQL Driver", e);
        }
    }

    public static Connection getConnection() {
        logger.info("Method getConnection was called");
        Properties dbProperties = new Properties();
        dbProperties.setProperty("user", USERNAME);
        dbProperties.setProperty("password", PASSWORD);
        try {
            return DriverManager.getConnection(URL, dbProperties);
        } catch (SQLException e) {
            logger.error("Can't create connection to DB, reason: {} ",
                            e.getMessage());
            throw new RuntimeException("Can't create connection to DB ", e);
        }
    }
}
