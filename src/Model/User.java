package Model;

import Model.DataBase.DataBaseConnection;
import javafx.beans.property.*;

import java.sql.SQLException;

public class User {
    //Stringa per lo username
    private StringProperty username = new SimpleStringProperty("");

    //Stringa per la password
    private StringProperty password = new SimpleStringProperty("");

    DataBaseConnection userConnection;

    public User() {
    }

    public User(String username, String password, boolean type) {
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.type = new SimpleBooleanProperty(type);
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