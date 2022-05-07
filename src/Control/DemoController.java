package Control;

import Model.DemoModel;
import View.DemoViewBuilder;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class DemoController {
    private Stage stage;
    private DemoModel model = new DemoModel();

    private DemoViewBuilder viewBuilder;

    //Costruttore
    public DemoController(Stage stage) {
        this.stage = stage;
        viewBuilder = new DemoViewBuilder(model, () -> checkAccess());
    }

    public Parent getView()  {
        return viewBuilder.getView();
    }

    /*
    metodo privato che verifica l'accesso
     */
    private void checkAccess() {
        if (model.getFarmacologo() && model.getUser().equals("fred") && model.getPassword().equals("password")) {
            loadFarmacologistApplication();
        } else if(model.getMedico() && model.getUser().equals("fred") && model.getPassword().equals("password")) {
            loadDoctorApplication();
        } else if(model.getMedico() && model.getFarmacologo()){
            displayDualMessage();
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

    private void displayDualMessage() {
        Alert dialog = new Alert(Alert.AlertType.ERROR);
        dialog.setTitle("Not able to Login");
        dialog.setHeaderText("Incorrect check of boxes");
        dialog.setContentText("Please try Again and check if you chose the right user type.");
        dialog.showAndWait();
    }

    private void loadDoctorApplication() {
        Alert dialog = new Alert(Alert.AlertType.ERROR);
        dialog.setTitle("Main Page Doctor");
        dialog.setHeaderText("You have successfully login as Doctor!");
        dialog.setContentText("This will be the doctor view.");
        dialog.showAndWait();
    }

    private void loadFarmacologistApplication() {
        Alert dialog = new Alert(Alert.AlertType.ERROR);
        dialog.setTitle("Not able to Login");
        dialog.setHeaderText("You have successfully login as Farmacologist!");
        dialog.setContentText("This will be the farmacologist view");
        dialog.showAndWait();
    }
}
