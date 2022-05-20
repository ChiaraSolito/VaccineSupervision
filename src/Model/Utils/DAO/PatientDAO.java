package Model.Utils.DAO;
import Model.Patient;
import Model.RiskFactor;
import java.sql.SQLException;
import java.util.List;

public interface PatientDAO {
    List<Patient> getAllPatients(String username) throws SQLException; //, IllegalRiskValueException, NullStringException;
    List<Patient> getPatientReports(String username, String idPatient) throws SQLException; //, IllegalRiskValueException, NullStringException;
    Patient getPatient(String idPatient) throws SQLException;
    void createPatient(String idPatient, String birthYear, String province, String profession, List<RiskFactor> risk_factor);
    int reactionsNumber(String idPatient);
    int vaccinationsNumber(String idPatient);
}