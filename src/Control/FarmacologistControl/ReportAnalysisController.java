package Control.FarmacologistControl;

import Model.Utils.DAOImpl.ReportDAOImpl;

import java.util.Map;

public class ReportAnalysisController {

    public ReportAnalysisController() {
    }

    public Map<String, Integer> getReactionProvince() {
        ReportDAOImpl reportDAO = new ReportDAOImpl();

        return reportDAO.getReactionProvince();
    }

    public Map<String, Integer> getReactionSite() {
        ReportDAOImpl reportDAO = new ReportDAOImpl();

        return reportDAO.getReactionSite();
    }

    public Map<String, Integer> countVaccineSevereReaction() {
        ReportDAOImpl reportDAO = new ReportDAOImpl();

        return reportDAO.countVaccineSevereReaction();
    }

    public Map<String, Integer> getReaction6Months() {
        ReportDAOImpl reportDAO = new ReportDAOImpl();

        return reportDAO.getReaction6Months();
    }

    public Map<String, Integer> getReactionNumber() {
        ReportDAOImpl reportDAO = new ReportDAOImpl();

        return reportDAO.getReactionNumber();
    }
}
