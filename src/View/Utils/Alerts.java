package View.Utils;

import Model.User;
import javafx.scene.control.Alert;

public class Alerts {

    public static Alert displayConfMessage(User model) {
        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
        dialog.setTitle("Sei sicuro di voler continuare?");
        dialog.setHeaderText("Utente: " + model.getUsername());
        dialog.setContentText("Stai effettuando un'azione non reversibile.");
        dialog.showAndWait();

        return dialog;
    }

    public static void displayErrorMessage(User model) {
        Alert dialog = new Alert(Alert.AlertType.ERROR);
        dialog.setTitle("Errore!");
        dialog.setHeaderText("Utente: " + model.getUsername());
        dialog.setContentText("Hai dimenticato qualcosa. Inserisci tutti i dati necessari prima di andare avanti.");
        dialog.showAndWait();
    }

    public static void displayLoginError() {
        Alert dialog = new Alert(Alert.AlertType.ERROR);
        dialog.setTitle("Not able to Login");
        dialog.setHeaderText("Username o Password errati");
        dialog.setContentText("Hai inserito username o password errati.\nRiprovare e controllare di aver inserito correttamente i dati.");
        dialog.showAndWait();
    }

    public static void displayNotAcceptedPatient(User model) {
        Alert dialog = new Alert(Alert.AlertType.ERROR);
        dialog.setTitle("Errore! Input non accettato.");
        dialog.setHeaderText("Utente: " + model.getUsername());
        dialog.setContentText("Parte delle informazioni inserite per il paziente sono errate, non possibili o non accettate.");
        dialog.showAndWait();
    }
}
