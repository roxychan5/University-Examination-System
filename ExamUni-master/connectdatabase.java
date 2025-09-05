import java.sql.*;

public class connectdatabase {
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        String url = "jdbc:mysql://localhost:3306/unisystem"; // url
        String user = "root"; // default username
        String password = ""; // no password

        // Load the MySQL JDBC driver
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Establish and return the connection
        return DriverManager.getConnection(url, user, password);
    }
}

/*  Establish a connection
Connection connection = connectdatabase.getConnection();
*/