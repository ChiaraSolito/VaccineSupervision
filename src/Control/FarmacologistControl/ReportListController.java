package Control.FarmacologistControl;

import Model.Report;
import Model.Utils.DAOImpl.ReportDAOImpl;
import Model.Utils.DAOImpl.VaccinationDAOImpl;
import Model.Utils.Exceptions.NullStringException;
import Model.Vaccination;

import java.util.ArrayList;
import java.util.List;

public class ReportListController {

    public ReportListController() {
    }

    public List<Report> getReportList(){
        ReportDAOImpl reportDAO = new ReportDAOImpl();
        List<Report> reports = new ArrayList<>();

        try{
            reports = reportDAO.getAllReports();
        } catch (NullStringException nse){
            System.err.println("String Error: ");
            nse.printStackTrace();
        }
        return reports;
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

}
