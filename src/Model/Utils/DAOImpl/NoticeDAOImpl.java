package Model.Utils.DAOImpl;

import Model.DataBase.DataBaseConnection;
import Model.Notice;
import Model.Utils.DAO.NoticeDAO;
import javafx.beans.property.SimpleStringProperty;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NoticeDAOImpl implements NoticeDAO {
    DataBaseConnection pConnection;

    @Override
    public List<Notice> getAllNotices(String pharm) throws SQLException {
        pConnection = new DataBaseConnection();
        pConnection.openConnection();

        List<Notice> notices = new ArrayList<>();

        pConnection.statement = pConnection.connection.createStatement();
        pConnection.rs = pConnection.statement.executeQuery("SELECT R.noticeid, N.content FROM readnotice R " +
                "JOIN notice N ON N.id = R.noticeid " +
                "WHERE R.noticeid IN (" +
                "SELECT R1.noticeid" +
                "    FROM readnotice R1 " +
                "    WHERE pharmid = '" + pharm + "')");

        while (pConnection.rs.next()) {
            notices.add(new Notice(
                    new SimpleStringProperty(pConnection.rs.getString("N.id")),
                    new SimpleStringProperty(pConnection.rs.getString("N.content"))

            ));
        }

        pConnection.closeConnection();
        return notices;
    }

    @Override
    public List<Notice> getNotReadNotices(String pharm) throws SQLException {
        pConnection = new DataBaseConnection();
        pConnection.openConnection();

        List<Notice> notRead = new ArrayList<>();

        pConnection.statement = pConnection.connection.createStatement();
        pConnection.rs = pConnection.statement.executeQuery("SELECT R.noticeid, N.content FROM readnotice R " +
                "JOIN notice N ON N.id = R.noticeid " +
                "WHERE R.noticeid NOT IN (" +
                "SELECT R1.noticeid" +
                "    FROM readnotice R1 " +
                "    WHERE pharmid = '" + pharm + "')");

        while (pConnection.rs.next()) {
            notRead.add(new Notice(
                    new SimpleStringProperty(pConnection.rs.getString("N.id")),
                    new SimpleStringProperty(pConnection.rs.getString("N.content"))

            ));
        }

        pConnection.closeConnection();
        return notRead;
    }

    @Override
    public void setReadNotice(String pharm, String notice) throws SQLException {
        pConnection = new DataBaseConnection();
        pConnection.openConnection();

        pConnection.statement = pConnection.connection.createStatement();
        pConnection.rs = pConnection.statement.executeQuery("INSERT INTO readnotice " +
                "VALUES( '" + notice + "', '" + pharm + "' )"
        );

        pConnection.closeConnection();
    }

    @Override
    public void createNotice(String content) throws SQLException {
        pConnection = new DataBaseConnection();
        pConnection.openConnection();

        pConnection.statement = pConnection.connection.createStatement();
        pConnection.rs = pConnection.statement.executeQuery("INSERT INTO notice " +
                "VALUES( DEFAULT , '" + content + "' )"
        );

        pConnection.closeConnection();

    }
}
