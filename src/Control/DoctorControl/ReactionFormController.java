package Control.DoctorControl;

import Model.*;
import Model.Utils.DAOImpl.*;
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
        ObservableList<String> patients = null;

        try {
            patients = FXCollections.observableArrayList(patientDAO.getAllPatients(model.getUsername()));
        } catch (NullStringException e) {
            System.out.println("Error patient: " + e.getMessage());
        }

        return patients;
    }

    public List<String> getAllExistingRisks() {
        RiskFactorDAOImpl riskDAO = new RiskFactorDAOImpl();
        ObservableList<String> risks = FXCollections.observableArrayList(riskDAO.getAllExistingRisks());

        return risks;
    }

    public void addRisk(String name, String description, int riskLevel) {
        RiskFactorDAOImpl riskDAO = new RiskFactorDAOImpl();

        try {
            riskDAO.createRiskFactor(name, description, riskLevel);
        } catch (NullStringException exc) {
            System.out.println("Error risk null: " + exc.getMessage());
        }

    }

    public List<String> getAllExistingReactions() {
        ReactionDAOImpl reactionDAO = new ReactionDAOImpl();
        ObservableList<String> reactions;

        reactions = FXCollections.observableArrayList(reactionDAO.getAllReactions());

        return reactions;
    }

    public Patient getPatient(String id) {
        PatientDAOImpl patientDAO = new PatientDAOImpl();

        Patient patient = new Patient();
        try {
            patient = patientDAO.getPatient(id);
        } catch (NullStringException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return patient;
    }

    public RiskFactor getRisk(String name) {
        RiskFactorDAOImpl riskDAO = new RiskFactorDAOImpl();

        RiskFactor risk = new RiskFactor();
        try {
            risk = riskDAO.getRisk(name);
        } catch (NullStringException e) {
            System.out.println("Error risk null: " + e.getMessage());
        }
        return risk;
    }

    public Reaction getReaction(String name) {
        ReactionDAOImpl reactionDAO = new ReactionDAOImpl();

        Reaction reaction = new Reaction();
        try {
            reaction = reactionDAO.getReaction(name);
        } catch (NullStringException e) {
            System.out.println("Error reaction null: " + e.getMessage());
        }
        return reaction;
    }

    public void createReport(Patient patient, Reaction reaction, List<Vaccination> vaccinations, String reactionDate, boolean flagPatient, boolean flagReaction) {
        ReportDAOImpl reportDAO = new ReportDAOImpl();
        PatientDAOImpl patientDAO = new PatientDAOImpl();
        ReactionDAOImpl reactionDAO = new ReactionDAOImpl();
        VaccinationDAOImpl vaccinationDAO = new VaccinationDAOImpl();
        String idPatient = patient.getIdPatient();

        if (flagPatient) {
            try {
                idPatient = patientDAO.createPatient(patient.getBirthYear(), patient.getProvince(), patient.getProfession(), patient.getAllRiskFactor());
            } catch (NullStringException e) {
                System.out.println("Error idpatient null: " + e.getMessage());
            }
        }
        if (flagReaction) {
            try {
                reactionDAO.createReaction(reaction.getName(), reaction.getGravity(), reaction.getDescription());
            } catch (NullStringException e) {
                System.out.println("Error reaction null: " + e.getMessage());
            }
        }
        for (Vaccination vaccination : vaccinations) {
            try {
                vaccinationDAO.createVaccination(idPatient, vaccination.getVaccine(), vaccination.getTypeSomministration(), vaccination.getVaccinationSite(), vaccination.getVaccinationDate());
            } catch (NullStringException e) {
                System.out.println("Error vaccination null: " + e.getMessage());
            }
        }
        try {
            reportDAO.createReport(idPatient, reaction.getName(), reactionDate, model.getUsername());
        } catch (NullStringException e) {
            System.out.println("Error report null: " + e.getMessage());
        }
    }
}
