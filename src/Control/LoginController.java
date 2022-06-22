package Control;

import Model.DataBase.DataBaseConnection;
import Model.User;

import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {
    private final User model;

    DataBaseConnection userConnection;

    //Costruttore
    public LoginController(User model) {
        this.model = model;
    }

    /*
    metodo privato che verifica l'accesso
     */
    public int checkAccess() throws FileNotFoundException, SQLException {
        //get username
        String username = model.getUsername();

        //open connection
        userConnection = new DataBaseConnection();
        userConnection.openConnection();

        //query to the database
        userConnection.statement = userConnection.connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        userConnection.rs = userConnection.statement.executeQuery("SELECT password, type FROM users WHERE username = '" + username + "'");

        //check login
        int flag = 2;
        while(userConnection.rs.next()) {
            if (model.getPassword().equals(userConnection.rs.getString("password"))) {
                if (userConnection.rs.getBoolean("type")) {
                    flag = 1; //farmacologo
                } else {
                    flag = 0; //medico
                }
            } else {
                flag = 2;
            }
        }
        return flag;
    }
}
