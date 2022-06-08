package Model.Utils.DAO;
import Model.Patient;
import Model.Report;
import Model.RiskFactor;
import Model.Utils.Exceptions.NullStringException;
import Model.Vaccination;

import java.util.List;

public interface PatientDAO {
    List<String> getAllPatients(String username) throws NullStringException; //, IllegalRiskValueException, NullStringException;

    List<Report> getPatientReports(String username, String idPatient) throws NullStringException; //, IllegalRiskValueException, NullStringException;

    Patient getPatient(String idPatient) throws NullStringException;

    List<Vaccination> getPatientVaccinations(String idPatient) throws NullStringException;

    String createPatient(String birthYear, String province, String profession, List<RiskFactor> risk_factor) throws NullStringException;

    int reactionsNumber(String idPatient) throws NullStringException;

    int vaccinationsNumber(String idPatient) throws NullStringException;
}
