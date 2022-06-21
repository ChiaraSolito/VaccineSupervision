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

    public Patient getPatient(String patient) throws NullStringException {
        PatientDAOImpl patientDAO = new PatientDAOImpl();
        Patient p;

        try {
            p = patientDAO.getPatient(patient);
        } catch (NullStringException e) {
            throw new NullStringException();
        }

        return p;
    }

    public List<Report> getPatientReports(String patient) throws NullStringException {
        PatientDAOImpl patientDAO = new PatientDAOImpl();
        List<Report> reports;

        try {
            reports = patientDAO.getPatientReports(model.getUsername(), patient);
        } catch (NullStringException e) {
            throw new NullStringException();
        }

        return reports;
    }

    public List<Vaccination> getPatientVaccinations(String patient) throws NullStringException {
        PatientDAOImpl patientDAO = new PatientDAOImpl();
        List<Vaccination> vaccinations;

        try {
            vaccinations = patientDAO.getPatientVaccinations(patient);
        } catch (NullStringException e) {
            throw new NullStringException();
        }

        return vaccinations;
    }

    public List<Vaccination> getPatientTwoMonthsVaccination(String patient, String reactionDate) throws NullStringException {
        VaccinationDAOImpl vaccinationDAO = new VaccinationDAOImpl();
        List<Vaccination> vaccinations;

        try {
            vaccinations = vaccinationDAO.getTwoMonthsVaccination(patient, reactionDate);
        } catch (NullStringException e) {
            throw new NullStringException();
        }

        return vaccinations;
    }

    public int getNumberVaccination(String patient) throws NullStringException {
        PatientDAOImpl patientDAO = new PatientDAOImpl();
        int numberVaccinations;

        try {
            numberVaccinations = patientDAO.vaccinationsNumber(patient);
        } catch (NullStringException e) {
            throw new NullStringException();
        }

        return numberVaccinations;
    }

    public int getNumberReport(String patient) throws NullStringException {
        PatientDAOImpl patientDAO = new PatientDAOImpl();
        int numberReports;

        try {
            numberReports = patientDAO.reactionsNumber(patient);
        } catch (NullStringException e) {
            throw new NullStringException();
        }

        return numberReports;
    }
}
