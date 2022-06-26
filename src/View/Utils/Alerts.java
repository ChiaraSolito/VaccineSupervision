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

    public static void displayCPhaseError() {
        Alert dialog = new Alert(Alert.AlertType.ERROR);
        dialog.setTitle("Proposta fase di controllo già inserita");
        dialog.setHeaderText("In data odierna hai già proposto una fase di controllo su questo vaccino.");
        dialog.setContentText("Per inserire una nuova proposta per questo vaccino riprova in una data diversa.");
        dialog.showAndWait();
    }

    public static void displayNotAcceptedDose(User model) {
        Alert dialog = new Alert(Alert.AlertType.ERROR);
        dialog.setTitle("Errore! Input non accettato.");
        dialog.setHeaderText("Utente: " + model.getUsername());
        dialog.setContentText("Parte delle informazioni inserite per la vaccinazione sono errate, non possibili o non accettate.\n Inserire da capo la lista.");
        dialog.showAndWait();
    }

    public static void displayErrorVaccineHistory(User model) {
        Alert dialog = new Alert(Alert.AlertType.ERROR);
        dialog.setTitle("Errore! Vaccinazione già presente.");
        dialog.setHeaderText("Utente: " + model.getUsername());
        dialog.setContentText("Vaccinazione già presente. La lista delle vaccinazioni inserite verrà cancellata. Inserire nuovamente");
        dialog.showAndWait();
    }

    public static void displayNotAcceptedVacc(User model) {
        Alert dialog = new Alert(Alert.AlertType.ERROR);
        dialog.setTitle("Errore! Vaccinazione non consentita.");
        dialog.setHeaderText("Utente: " + model.getUsername());
        dialog.setContentText("Vaccinazione già presente o con errori. Reinserire nuovamente l'ultima vaccinazione.");
        dialog.showAndWait();
    }

    public static void displayRiskError(User model) {
        Alert dialog = new Alert(Alert.AlertType.ERROR);
        dialog.setTitle("Errore! Inserimento non consentito.");
        dialog.setHeaderText("Utente: " + model.getUsername());
        dialog.setContentText("L'inserimento non è andato a termine. Ricorda che il livello del rischio deve essere tra 1 e 5 e di non aver inserito apici nella descrizione.");
        dialog.showAndWait();
    }
}
