package Control;

import Control.DoctorControl.MainPageController;
import Model.DataBase.DataBaseConnection;
import Model.User;
import View.DoctorView.MainPage;
import View.LoginViewBuilder;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {
    private Stage stage;
    private User model = new User();

    private LoginViewBuilder viewBuilder;
    private MainPage mainPage;

    DataBaseConnection userConnection;

    //Costruttore
    public LoginController(Stage stage) {
        this.stage = stage;
        viewBuilder = new LoginViewBuilder(model, () -> {
            try {
                checkAccess();
            } catch (FileNotFoundException | SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public Parent getView() throws FileNotFoundException {
        return viewBuilder.getView();
    }

    /*
    metodo privato che verifica l'accesso
     */
    private void checkAccess() throws FileNotFoundException, SQLException {
        //get username
        String username = model.getUsername();

        //open connection
        userConnection = new DataBaseConnection();
        userConnection.openConnection();

        //query to the database
        userConnection.statement = userConnection.connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        userConnection.rs = userConnection.statement.executeQuery("SELECT password, type FROM users WHERE username = '" + username + "'");

        //check login
        if(userConnection.rs.next()) {
            if (model.getPassword().equals(userConnection.rs.getString("password"))) {
                if (userConnection.rs.getBoolean("type")) {
                    loadPharmacologistApplication();
                } else {
                    loadDoctorApplication(stage);
                }
            } else {
                displayErrorMessage();
            }
        } else {
            displayErrorMessage();
        }
    }

    private void displayErrorMessage() {
        Alert dialog = new Alert(Alert.AlertType.ERROR);
        dialog.setTitle("Not able to Login");
        dialog.setHeaderText("Incorrect Username or Password");
        dialog.setContentText("You have entered incorrect username or Password.\nPlease try Again and check if you chose the right user type.");
        dialog.showAndWait();
    }

    private void loadDoctorApplication(Stage stage) throws FileNotFoundException {
        stage.setScene(new Scene(new MainPageController(stage).getView()));
        stage.setTitle("Doctor Menù");
        stage.show();
    }

    private void loadPharmacologistApplication() {
        Alert dialog = new Alert(Alert.AlertType.ERROR);
        dialog.setTitle("Not able to Login");
        dialog.setHeaderText("You have successfully login as Pharmacologist!");
        dialog.setContentText("This will be the pharmacologist view");
        dialog.showAndWait();
    }
}
