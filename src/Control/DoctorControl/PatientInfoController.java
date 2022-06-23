package Control.DoctorControl;

import Model.Patient;
import Model.Report;
import Model.User;
import Model.Utils.DAOImpl.PatientDAOImpl;
import Model.Utils.DAOImpl.VaccinationDAOImpl;
import Model.Utils.Exceptions.NullStringException;
import Model.Vaccination;

import java.util.List;

public class PatientInfoController {

    private static User model;

    /*
        Costruttore
     */
    public PatientInfoController(User model) {
        PatientInfoController.model = model;
    }

    public Patient getPatient(String patient) {

        PatientDAOImpl patientDAO = new PatientDAOImpl();
        Patient p = null;

        try {
            p = patientDAO.getPatient(patient);
        } catch (NullStringException e) {
            System.out.println("Error patient: " + e.getMessage());
        }

        return p;
    }

    public List<Report> getPatientReports(String patient) {
        PatientDAOImpl patientDAO = new PatientDAOImpl();
        List<Report> reports = null;

        try {
            reports = patientDAO.getPatientReports(model.getUsername(), patient);
        } catch (NullStringException e) {
            System.out.println("Error patient: " + e.getMessage());
        }

        return reports;
    }

    public List<Vaccination> getPatientVaccinations(String patient) {
        PatientDAOImpl patientDAO = new PatientDAOImpl();
        List<Vaccination> vaccinations = null;

        try {
            vaccinations = patientDAO.getPatientVaccinations(patient);
        } catch (NullStringException e) {
            System.out.println("Error patient: " + e.getMessage());
        }

        return vaccinations;
    }

    public List<Vaccination> getPatientTwoMonthsVaccination(String patient, String reactionDate) {
        VaccinationDAOImpl vaccinationDAO = new VaccinationDAOImpl();
        List<Vaccination> vaccinations = null;

        try {
            vaccinations = vaccinationDAO.getTwoMonthsVaccination(patient, reactionDate);
        } catch (NullStringException e) {
            System.out.println("Error patient or reaction: " + e.getMessage());
        }

        return vaccinations;
    }

    public int getNumberVaccination(String patient) {
        PatientDAOImpl patientDAO = new PatientDAOImpl();
        int numberVaccinations = 0;

        try {
            numberVaccinations = patientDAO.vaccinationsNumber(patient);
        } catch (NullStringException e) {
            System.out.println("Error patient: " + e.getMessage());
        }

        return numberVaccinations;
    }

    public int getNumberReport(String patient) {
        PatientDAOImpl patientDAO = new PatientDAOImpl();
        int numberReports = 0;

        try {
            numberReports = patientDAO.reactionsNumber(patient);
        } catch (NullStringException e) {
            System.out.println("Error patient: " + e.getMessage());
        }

        return numberReports;
    }
}
