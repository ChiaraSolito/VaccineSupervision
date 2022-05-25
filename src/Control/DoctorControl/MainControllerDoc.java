package Control.DoctorControl;

import Model.User;
import View.DoctorView.MainPageDoc;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.io.FileNotFoundException;


public class MainControllerDoc {
    private User model;
    private MainPageDoc mainPageDoc;


    public MainControllerDoc() {

    }

    private void selectTask() {


    }

    private void displayErrorMessage() {
        Alert dialog = new Alert(Alert.AlertType.ERROR);
        dialog.setTitle("What the hell!");
        dialog.setHeaderText("Non dovresti essere qui.");
        dialog.setContentText("You have entered hell.");
        dialog.showAndWait();
    }

    private void loadList(Stage stage) {
        Alert dialog = new Alert(Alert.AlertType.ERROR);
        dialog.setTitle("Oh ma guarda pazienti");
        dialog.showAndWait();
    }

    private void loadReactionForm(Stage stage) {
        Alert dialog = new Alert(Alert.AlertType.ERROR);
        dialog.setTitle("Oh ma guarda cose");
        dialog.showAndWait();
    }
}
