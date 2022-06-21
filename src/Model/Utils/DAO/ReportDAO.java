package Model.Utils.DAO;

import Model.Report;
import Model.Utils.Exceptions.NullStringException;

import java.util.List;
import java.util.Map;

public interface ReportDAO {
    List<Report> getAllReports() throws NullStringException;

    void createReport(String idPatient, String reactionName, String reactionDate, String doctor) throws NullStringException;

    //id viene creato dentro tramite una query serial + 1 (altrimenti il seriale non è più seriale)
    int getReportNumber();

    Map<String, Integer> getReactionNumber();

    Map<String, Integer> getReaction6Months();

    Map<String, Integer> countVaccineSevereReaction();

    Map<String, Integer> getReactionSite();

}
