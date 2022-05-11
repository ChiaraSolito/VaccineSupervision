package Model.DataBase;

import java.sql.*;


public class DataBaseConnection {

    //Connessione al database
    private Connection connection;

    //Nome della tabella del database
    private String table_name = "expenses";

    private void manageSQLException(Exception e){
        e.printStackTrace();
        System.err.println(e.getClass().getName()+": "+e.getMessage());
        System.exit(0);
    }

    /*
        Costruttore che apre la connessione
        COMPLETAMENTE DA RIVEDERE
     */
    public DataBaseConnection(){
        connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql:src/DataBase/Users.db",
                    "postgres", "ingdelsw");
        } catch (Exception e) {
            manageSQLException(e);
        }
        System.out.println("Opened database successfully");
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch(SQLException sqle) {
            System.out.println("Error: " + sqle.getMessage());
        }
    }
}
