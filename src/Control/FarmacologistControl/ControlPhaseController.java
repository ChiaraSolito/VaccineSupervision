package Control.FarmacologistControl;

import Model.ControlPhase;
import Model.User;
import Model.Utils.DAOImpl.ControlPhaseDAOImpl;
import Model.Utils.DAOImpl.VaccinationDAOImpl;
import Model.Utils.Exceptions.NullStringException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class ControlPhaseController {
    private User model;

    public ControlPhaseController(User model) {
        this.model = model;
    }

    public List<String> getAllVaccines() {

        VaccinationDAOImpl vaccinationDAO = new VaccinationDAOImpl();
        ObservableList<String> vaccines = FXCollections.observableArrayList(vaccinationDAO.getAllVaccines());

        return vaccines;
    }

    public void addControlPhase(ControlPhase controlPhase) {
        ControlPhaseDAOImpl controlPhaseDAO = new ControlPhaseDAOImpl();

        try {
            controlPhaseDAO.proposeControlPhase(controlPhase.getReportDate(), controlPhase.getVaccine(), controlPhase.getPharmacologist());
        } catch (NullStringException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
