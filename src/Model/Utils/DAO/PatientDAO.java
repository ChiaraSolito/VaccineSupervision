package Model.Utils.DAO;
import Model.Patient;
import Model.Report;
import Model.RiskFactor;
import Model.Vaccination;

import java.sql.SQLException;
import java.util.List;

public interface PatientDAO {
    List<String> getAllPatients(String username) throws SQLException; //, IllegalRiskValueException, NullStringException;
    List<Report> getPatientReports(String username, String idPatient) throws SQLException; //, IllegalRiskValueException, NullStringException;
    Patient getPatient(String idPatient) throws SQLException;
    List<Vaccination> getPatientVaccinations(String idPatient) throws SQLException;
    void createPatient(String birthYear, String province, String profession, List<RiskFactor> risk_factor) throws SQLException;
    int reactionsNumber(String idPatient) throws SQLException;
    int vaccinationsNumber(String idPatient) throws SQLException;
}
