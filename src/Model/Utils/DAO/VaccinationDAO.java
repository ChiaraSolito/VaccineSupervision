package Model.Utils.DAO;

import Model.Utils.Exceptions.NullStringException;
import Model.Vaccination;

import java.util.List;

public interface VaccinationDAO {
    void createVaccination(String idPatient, String vaccine,
                           String typeSomministration, String vaccinationSite,
                           String vaccinationDate) throws NullStringException;

    List<Vaccination> getAllVaccination(String idPatient) throws NullStringException;

    List<Vaccination> getTwoMonthsVaccination(String idPatient, String reactionDate) throws NullStringException;

    List<String> getAllVaccines();
    List<String> getAllCovidVaccines();
}
