package Control.DoctorControl;

import Model.User;
import View.DoctorView.MainPage;
import View.LoginViewBuilder;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.io.FileNotFoundException;
import java.sql.SQLException;


public class MainControllerDoc {
    private User model = new User();

    private LoginViewBuilder viewBuilder;
    private MainPage mainPage;

    private Stage stage;

    public MainControllerDoc(Stage stage) {
        this.stage = stage;
        mainPage = new MainPage(model, () -> {
            try {
                selectTask();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
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

    private void loadList(Stage stage) {
        this.stage = stage;
        Alert dialog = new Alert(Alert.AlertType.ERROR);
        dialog.setTitle("Oh ma guarda pazienti");
        dialog.showAndWait();
    }

    private void loadReactionForm(Stage stage) {
        this.stage = stage;
        Alert dialog = new Alert(Alert.AlertType.ERROR);
        dialog.setTitle("Oh ma guarda cose");
        dialog.showAndWait();
    }
}
