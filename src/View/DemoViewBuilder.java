package View;

import Model.DemoModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class DemoViewBuilder {
    private final DemoModel model;
    private Runnable actionRunnable;

    //Aggiungere un collegamento alla determinata base di dati?

    public DemoViewBuilder(DemoModel model, Runnable actionRunnable) {
        this.model = model;
        this.actionRunnable = actionRunnable;
    }

    public Region getView() {
        HBox userHBox = new HBox(10, new Text("User ID: "), createBoundTextField(model.userProperty()));
        HBox passwordHBox = new HBox(10, new Text("Password: "), createBoundPasswordField(model.passwordProperty()));
        HBox medicoBox = new HBox(10, new CheckBox("Medico"), createBoundMedicoField(model.medicoProperty()));
        HBox farmacologoBox = new HBox(10, new CheckBox("Farmacologo"), createBoundFarmacologoField(model.farmacologoProperty()));
        Button loginButton = new Button("Login");
        loginButton.setOnAction(evt -> actionRunnable.run());
        VBox results = new VBox(10, userHBox, passwordHBox, medicoBox, farmacologoBox, loginButton);
        results.setAlignment(Pos.CENTER);
        return results;
    }

    private Node createBoundPasswordField(StringProperty boundProperty) {
        PasswordField results = new PasswordField();
        results.textProperty().bindBidirectional(boundProperty);
        return results;
    }

    private Node createBoundTextField(StringProperty boundProperty) {
        TextField results = new TextField("");
        results.textProperty().bindBidirectional(boundProperty);
        return results;
    }

    private Node createBoundMedicoField(BooleanProperty boundProperty){
        CheckBox results = new CheckBox();
        results.selectedProperty().bindBidirectional(boundProperty);
        return results;
    }
    private Node createBoundFarmacologoField(BooleanProperty boundProperty) {
        CheckBox results = new CheckBox();
        results.selectedProperty().bindBidirectional(boundProperty);
        return results;
    }

}
