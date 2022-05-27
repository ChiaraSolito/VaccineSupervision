package Model.Utils.DAO;

import Model.Report;
import javafx.beans.property.SimpleStringProperty;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface ReportDAO {
    List<Report> getAllReports(String username) throws SQLException;
    //metodo usato solo per farmacologo
    //username serve per fare controllo che sia farmacologo
    void createReport(String id, String idPatient, String reactionName,
                      Date reportDate, Date reactionDate,
                      String vaccination, SimpleStringProperty doctor) throws SQLException;
    //id viene creato dentro tramite una query serial + 1 (altrimenti il seriale non è più seriale)
}
