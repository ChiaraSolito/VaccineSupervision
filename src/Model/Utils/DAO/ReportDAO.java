package Model.Utils.DAO;

import Model.Report;
import Model.Utils.Exceptions.NullStringException;

import java.util.List;

public interface ReportDAO {
    List<Report> getAllReports(String username) throws NullStringException;

    //metodo usato solo per farmacologo
    //username serve per fare controllo che sia farmacologo
    void createReport(String idPatient, String reactionName, String reactionDate, String doctor) throws NullStringException;
    //id viene creato dentro tramite una query serial + 1 (altrimenti il seriale non è più seriale)
}
