package Model.DataBase;
import java.sql.*;

public class DataBaseConnection {
    public Connection connection = null;
    public Statement statement = null;
    public ResultSet rs = null;

    public void openConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/VaccineSupervisionDB", "postgres", "postgres");
        } catch (SQLException psql) {
            System.out.println("Error: " + psql.getMessage());
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Error: " + cnfe.getMessage());
        }
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch(SQLException psql) {
            System.out.println("Error: " + psql.getMessage());
        }
    }
}
