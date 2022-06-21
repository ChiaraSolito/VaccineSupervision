package Control.FarmacologistControl;

import Model.Notice;
import Model.User;
import Model.Utils.DAOImpl.NoticeDAOImpl;
import Model.Utils.DAOImpl.ReportDAOImpl;
import Model.Utils.Exceptions.NullStringException;
import View.PharmaView.ReportAnalysis;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportAnalysisController {

    private final User model;

    public ReportAnalysisController(User model) { this.model = model; }

    public Map<String, Integer> getReactionProvince() {
        ReportDAOImpl reportDAO = new ReportDAOImpl();
        Map<String, Integer> vaccineReactions = reportDAO.getReactionProvince();

        return vaccineReactions;
    }

    public Map<String, Integer> getReactionSite() {
        ReportDAOImpl reportDAO = new ReportDAOImpl();
        Map<String, Integer> vaccineReactions = reportDAO.getReactionSite();

        return vaccineReactions;
    }

    public Map<String, Integer> countVaccineSevereReaction() {
        ReportDAOImpl reportDAO = new ReportDAOImpl();
        Map<String, Integer> vaccineReactions = reportDAO.countVaccineSevereReaction();

        return vaccineReactions;
    }

    public Map<String, Integer> getReaction6Months() {
        ReportDAOImpl reportDAO = new ReportDAOImpl();
        Map<String, Integer> vaccineReactions = reportDAO.getReaction6Months();

        return vaccineReactions;
    }

    public Map<String, Integer> getReactionNumber() {
        ReportDAOImpl reportDAO = new ReportDAOImpl();
        Map<String, Integer> vaccineReactions = reportDAO.getReactionNumber();

        return vaccineReactions;
    }
}
