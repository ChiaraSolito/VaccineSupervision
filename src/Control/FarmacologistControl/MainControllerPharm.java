package Control.FarmacologistControl;

import Model.User;
import View.PharmaView.MainPage;
import View.LoginViewBuilder;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.io.FileNotFoundException;


public class MainControllerPharm {
    private User model = new User();

    private LoginViewBuilder viewBuilder;
    private MainPage mainPage;

    private Stage stage;

    public MainControllerPharm(Stage stage) {
        this.stage = stage;
        mainPage = new MainPage(model, () -> selectTask());
    }

    private void selectTask() {

    }

    public Parent getView() throws FileNotFoundException {
        return mainPage.getView();
    }

    private void displayErrorMessage() {
        Alert dialog = new Alert(Alert.AlertType.ERROR);
        dialog.setTitle("What the hell!");
        dialog.setHeaderText("Non dovresti essere qui.");
        dialog.setContentText("You have entered hell.");
        dialog.showAndWait();
    }

    private void loadPatientList(Stage stage) {
        this.stage = stage;
        Alert dialog = new Alert(Alert.AlertType.ERROR);
        dialog.setTitle("Oh ma guarda pazienti");
        dialog.showAndWait();
    }

    private void loadInsertView(Stage stage) {
        this.stage = stage;
        Alert dialog = new Alert(Alert.AlertType.ERROR);
        dialog.setTitle("Oh ma guarda cose");
        dialog.showAndWait();
    }
}
