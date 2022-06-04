package Control.DoctorControl;

import Model.User;
import Model.Utils.DAOImpl.PatientDAOImpl;
import Model.Utils.DAOImpl.ReactionDAOImpl;
import Model.Utils.DAOImpl.RiskFactorDAOImpl;
import Model.Utils.Exceptions.NullStringException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class ReactionFormController {
    private User model;

    //Costruttore
    public ReactionFormController(User model) {
        this.model = model;
    }

    public List<String> getAllPatients() throws NullStringException {
        PatientDAOImpl patientDAO = new PatientDAOImpl();
        ObservableList<String> patients;

        try {
            patients = FXCollections.observableArrayList(patientDAO.getAllPatients(model.getUsername()));
        } catch (NullStringException e) {
            throw new NullStringException();
        }

        return patients;
    }

    public List<String> getAllExistingRisks() {
        RiskFactorDAOImpl riskDAO = new RiskFactorDAOImpl();
        ObservableList<String> risks;

        risks = FXCollections.observableArrayList(riskDAO.getAllExistingRisks());

        return risks;
    }

    public void addRisk(String name, String description, int riskLevel) {
        RiskFactorDAOImpl riskDAO = new RiskFactorDAOImpl();

        try {
            riskDAO.createRiskFactor(name, description, riskLevel);
        } catch (NullStringException exc) {
            System.out.println("Error: " + exc.getMessage());
        }

    }


    public List<String> getAllExistingReactions() {
        ReactionDAOImpl reactionDAO = new ReactionDAOImpl();
        ObservableList<String> reactions;

        reactions = FXCollections.observableArrayList(reactionDAO.getAllReactions());

        return reactions;
    }
}
