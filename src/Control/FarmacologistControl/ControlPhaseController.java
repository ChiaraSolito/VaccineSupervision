package Control.FarmacologistControl;

import Model.ControlPhase;
import Model.Utils.DAOImpl.ControlPhaseDAOImpl;
import Model.Utils.DAOImpl.VaccinationDAOImpl;
import Model.Utils.Exceptions.NullStringException;
import javafx.collections.FXCollections;

import java.util.List;

public class ControlPhaseController {

    public ControlPhaseController() {
    }

    public List<String> getAllVaccines() {

        VaccinationDAOImpl vaccinationDAO = new VaccinationDAOImpl();

        return FXCollections.observableArrayList(vaccinationDAO.getAllCovidVaccines());
    }

    public void addControlPhase(ControlPhase controlPhase) {
        ControlPhaseDAOImpl controlPhaseDAO = new ControlPhaseDAOImpl();

        try {
            controlPhaseDAO.proposeControlPhase(controlPhase.getVaccine(), controlPhase.getPharmacologist());
        } catch (NullStringException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public int getTotalNumberControlPhase(String vaccine) {
        ControlPhaseDAOImpl controlPhaseDAO = new ControlPhaseDAOImpl();
        int totalNumber = 0;

        try {
            totalNumber = controlPhaseDAO.getTotalNumberControlPhase(vaccine);
        } catch (NullStringException e) {
            System.err.println("Error: " + e.getMessage());
        }

        return totalNumber;
    }

    public int getSixMonthsNumberControlPhase(String vaccine) {
        ControlPhaseDAOImpl controlPhaseDAO = new ControlPhaseDAOImpl();
        int sixMonthsNumber = 0;

        try {
            sixMonthsNumber = controlPhaseDAO.getSixMonthsNumberControlPhase(vaccine);
        } catch (NullStringException e) {
            System.err.println("Error: " + e.getMessage());
        }

        return sixMonthsNumber;
    }
}
