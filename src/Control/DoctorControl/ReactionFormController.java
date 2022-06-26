package Control.DoctorControl;

import Model.*;
import Model.Utils.DAOImpl.*;
import Model.Utils.Exceptions.NullStringException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class ReactionFormController {
    private final User model;

    //Costruttore
    public ReactionFormController(User model) {
        this.model = model;
    }

    public List<String> getAllPatients() {
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
        return FXCollections.observableArrayList(riskDAO.getAllExistingRisks());
    }

    public void addRisk(String name, String description, int riskLevel) {
        RiskFactorDAOImpl riskDAO = new RiskFactorDAOImpl();

        try {
            riskDAO.createRiskFactor(name, description, riskLevel);
        } catch (NullStringException exc) {
            System.out.println("Error risk: " + exc.getMessage());
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
            System.out.println("Error risk: " + e.getMessage());
        }
        return risk;
    }

    public Reaction getReaction(String name) {
        ReactionDAOImpl reactionDAO = new ReactionDAOImpl();

        Reaction reaction = new Reaction();
        try {
            reaction = reactionDAO.getReaction(name);
        } catch (NullStringException e) {
            System.out.println("Error reaction: " + e.getMessage());
        }
        return reaction;
    }

    public String createPatient(Patient patient) {
        PatientDAOImpl patientDAO = new PatientDAOImpl();
        String idPatient = patient.getIdPatient();
        try {
            idPatient = patientDAO.createPatient(patient.getBirthYear(), patient.getProvince(), patient.getProfession(), patient.getAllRiskFactor());
        } catch (NullStringException e) {
            System.out.println("Error idpatient: " + e.getMessage());
        }
        return idPatient;
    }

    public void createReaction(Reaction reaction) {
        ReactionDAOImpl reactionDAO = new ReactionDAOImpl();
        try {
            reactionDAO.createReaction(reaction.getName(), reaction.getGravity(), reaction.getDescription());
        } catch (NullStringException e) {
            System.out.println("Error reaction: " + e.getMessage());
        }
    }

    public void addVaccination(Patient patient, List<Vaccination> vaccinations) {
        VaccinationDAOImpl vaccinationDAO = new VaccinationDAOImpl();

        for (Vaccination vaccination : vaccinations) {
            try {
                vaccinationDAO.createVaccination(patient.getIdPatient(), vaccination.getVaccine(), vaccination.getTypeSomministration(), vaccination.getVaccinationSite(), vaccination.getVaccinationDate());
            } catch (NullStringException e) {
                System.out.println("Error vaccination: " + e.getMessage());
            }
        }
    }

    public void createReport(Patient patient, Reaction reaction, String reactionDate) {
        ReportDAOImpl reportDAO = new ReportDAOImpl();
        try {
            reportDAO.createReport(patient.getIdPatient(), reaction.getName(), reactionDate, model.getUsername());
        } catch (NullStringException e) {
            System.out.println("Error report: " + e.getMessage());
        }
    }

    public List<String> getVaccinationsDoses(String patient) {
        PatientDAOImpl patientDAO = new PatientDAOImpl();
        List<String> doses = new ArrayList<>();
        List<Vaccination> vaccinations;
        try {
            vaccinations = patientDAO.getPatientVaccinations(patient);
            for (Vaccination vaccination : vaccinations) {
                doses.add(vaccination.getTypeSomministration());
            }
        } catch (NullStringException e) {
            System.out.println("Error idpatient: " + e.getMessage());
        }
        return doses;
    }

}
