package Control.FarmacologistControl;

import Model.Report;
import Model.Utils.DAOImpl.ReportDAOImpl;
import Model.Utils.DAOImpl.VaccinationDAOImpl;
import Model.Utils.Exceptions.NullStringException;
import Model.Vaccination;

import java.util.List;

public class ReportListController {

    public ReportListController() {
    }

    public List<Report> getReportList(){
        ReportDAOImpl reportDAO = new ReportDAOImpl();

        return reportDAO.getAllReports();
    }

    public List<Vaccination> getPatientTwoMonthsVaccination(String patient, String reactionDate) {
        VaccinationDAOImpl vaccinationDAO = new VaccinationDAOImpl();
        List<Vaccination> vaccinations = null;

        try {
            vaccinations = vaccinationDAO.getTwoMonthsVaccination(patient, reactionDate);
        } catch (NullStringException e) {
            System.err.println("Error patient or reactionDate: " + e.getMessage());
        }

        return vaccinations;
    }

}
