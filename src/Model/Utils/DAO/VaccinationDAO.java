package Model.Utils.DAO;

import Model.Utils.Exceptions.NullStringException;
import Model.Vaccination;
import java.util.Date;
import java.util.List;

public interface VaccinationDAO {
    void createVaccination(String idPatient, String vaccine,
                           String typeSomministration, String vaccinationSite,
                           Date vaccinationDate) throws NullStringException;
    List<Vaccination> getAllVaccination(String idPatient) throws NullStringException;
    List<Vaccination> getTwoMonthsVaccination(String idPatient) throws NullStringException;
}
