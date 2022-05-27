package Model.Utils.DAO;

import Model.Report;
import Model.Vaccination;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface VaccinationDAO {
    void createVaccination(String idPatient, String vaccine,
                           String typeSomministration, String vaccinationSite,
                           Date vaccinationDate) throws SQLException;
    List<Vaccination> getAllVaccination(String idPatient) throws SQLException;
    List<Vaccination> getTwoMonthsVaccination(String idPatient) throws SQLException;
}
