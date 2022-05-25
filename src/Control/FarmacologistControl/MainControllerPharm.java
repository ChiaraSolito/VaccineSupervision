package Control.FarmacologistControl;

import Model.User;
import View.PharmaView.MainPagePharm;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.io.FileNotFoundException;


public class MainControllerPharm {
    private User model;
    private MainPagePharm mainPagePharm;


    public MainControllerPharm() {
    }

    private void selectTask() {

    }

    public Parent getView() throws FileNotFoundException {
        return mainPagePharm.getView();
    }

    private void displayErrorMessage() {
    }

    private void loadPatientList(Stage stage) {
        Alert dialog = new Alert(Alert.AlertType.ERROR);
        dialog.setTitle("Oh ma guarda pazienti");
        dialog.showAndWait();
    }

    private void loadInsertView() {
        Alert dialog = new Alert(Alert.AlertType.ERROR);
        dialog.setTitle("Oh ma guarda cose");
        dialog.showAndWait();
    }
}
