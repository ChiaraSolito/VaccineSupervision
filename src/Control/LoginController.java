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
        userConnection = new DataBaseConnection();
        userConnection.openConnection();

        userConnection.statement = userConnection.connection.createStatement();

        String username = model.getUsername();
        if(model.getPassword().equals(userConnection.statement.executeQuery("SELECT password FROM users WHERE username = '" + username + "'"))){
            if (model.getType() ) {
                loadFarmacologistApplication();
            } else {
                loadDoctorApplication(stage);
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
        System.out.println("Sono un dottore");
        stage.setScene(new Scene(new MainPageController(stage).getView()));
        stage.setTitle("Doctor Menù");
        stage.show();
    }

    private void loadFarmacologistApplication() {
        Alert dialog = new Alert(Alert.AlertType.ERROR);
        dialog.setTitle("Not able to Login");
        dialog.setHeaderText("You have successfully login as Farmacologist!");
        dialog.setContentText("This will be the farmacologist view");
        dialog.showAndWait();
    }
}
