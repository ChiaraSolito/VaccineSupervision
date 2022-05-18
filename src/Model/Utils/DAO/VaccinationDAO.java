package Model.Utils.DAO;

import Model.Report;
import Model.Vaccination;

import java.util.Date;
import java.util.List;

public interface VaccinationDAO {
    void createVaccination(String idPatient, String vaccine,
                           String typeSomministration, String vaccinationSite,
                           Date vaccinationDate, List<Report> vaccinationReport);
    List<Vaccination> getAllVaccination(String idPatient);
    List<Vaccination> getTwoMonthsVaccination(String idPatient);
}
