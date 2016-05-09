package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by synerzip on 29/4/16.
 */
public class DBUtil {
    private static Connection connection;

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        if (connection == null) {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/my_database", "root", "shubham5252");
        }
        return connection;
    }
}