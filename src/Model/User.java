package Model;

import Model.DataBase.DataBaseConnection;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.BooleanPropertyBase;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.SQLException;

public class User {
    //Stringa per lo username
    private StringProperty username;

    //Stringa per la password
    private StringProperty password = new SimpleStringProperty("");

    DataBaseConnection userConnection;

    public User() {
        this.username = new SimpleStringProperty("");
        this.password = new SimpleStringProperty("");
    }

    public User(String username, String password, boolean type) {
    }

    public User getUser(String username) throws SQLException {
        userConnection = new DataBaseConnection();
        userConnection.openConnection();

        userConnection.statement = userConnection.connection.createStatement();
        userConnection.rs = userConnection.statement.executeQuery("SELECT username, password, type FROM Users WHERE username = '" + username + "'");

        User user = new User(userConnection.rs.getString("username"), userConnection.rs.getString("password"),
                userConnection.rs.getBoolean("type"));

        userConnection.closeConnection();

        return user;
    }

    //Booleano che mi dice se lo user è un medico
    private BooleanProperty type = new BooleanPropertyBase() {
        @Override
        public Object getBean() {
            return null;
        }

        @Override
        public String getName() {
            return null;
        }
    };

    /*
        Metodo per il binding della BooleanProperty
     */
    public BooleanProperty typeProperty(){
        return type;
    }
    public boolean getType(){
        return type.get();
    }

    public String getUsername() {
        return username.get();
    }

    /*
        Metodo per il binding della StringProperty
     */
    public StringProperty userProperty() {
        return username;
    }

    public String getPassword() {
        return password.get();
    }

    /*
        Metodo per il binding della StringProperty
     */
    public StringProperty passwordProperty() {
        return password;
    }
}