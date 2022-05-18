package Model.Utils.DAO;

import Model.Report;
import javafx.beans.property.SimpleStringProperty;
import java.util.Date;
import java.util.List;

public interface ReportDAO {
    List<Report> getAllReports(String username);
    void createReport(String id, String idPatient, String reactionName,
                      Date reportDate, Date reactionDate,
                      String vaccination, SimpleStringProperty doctor);
}
