package Model.Utils.DAOImpl;

import Model.DataBase.DataBaseConnection;
import Model.Notice;
import Model.Utils.DAO.NoticeDAO;
import Model.Utils.Exceptions.NullStringException;
import javafx.beans.property.SimpleStringProperty;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NoticeDAOImpl implements NoticeDAO {
    DataBaseConnection pConnection;

    @Override
    public List<Notice> getAllNotices(String pharm) throws NullStringException {

        List<Notice> notices = new ArrayList<>();

        if (pharm.isEmpty()) {
            throw new NullStringException();
        }

        pConnection = new DataBaseConnection();
        pConnection.openConnection();

        try {
            pConnection.statement = pConnection.connection.createStatement();
            pConnection.rs = pConnection.statement.executeQuery("SELECT DISTINCT R.noticeid, N.content, N.noticedate FROM readnotice R " +
                    "JOIN notice N ON N.id = R.noticeid " +
                    "WHERE R.noticeid IN (" +
                    "SELECT R1.noticeid" +
                    "    FROM readnotice R1 " +
                    "    WHERE pharmid = '" + pharm + "')");

            while (pConnection.rs.next()) {
                notices.add(new Notice(
                        new SimpleStringProperty(pConnection.rs.getString("noticeid")),
                        new SimpleStringProperty(pConnection.rs.getString("content")),
                        new SimpleStringProperty(pConnection.rs.getString("noticedate"))
                ));
            }
        } catch (SQLException sqle) {
            System.out.println("Error: " + sqle.getMessage());
            sqle.printStackTrace();
        } finally {
            pConnection.closeConnection();
        }

        return notices;
    }

    @Override
    public List<Notice> getNotReadNotices(String pharm) throws NullStringException {

        List<Notice> notRead = new ArrayList<>();
        if (pharm.isEmpty()) {
            throw new NullStringException();
        }

        pConnection = new DataBaseConnection();
        pConnection.openConnection();

        try {
            pConnection.statement = pConnection.connection.createStatement();
            pConnection.rs = pConnection.statement.executeQuery("SELECT N.id, N.content, N.noticedate FROM notice N " +
                    "WHERE N.id NOT IN (" +
                    "SELECT R1.noticeid " +
                    "FROM readnotice R1 " +
                    "WHERE R1.pharmid = '" + pharm + "')");

            while (pConnection.rs.next()) {
                notRead.add(new Notice(
                        new SimpleStringProperty(pConnection.rs.getString("id")),
                        new SimpleStringProperty(pConnection.rs.getString("content")),
                        new SimpleStringProperty(pConnection.rs.getString("noticedate"))
                ));
            }

        } catch (SQLException sqle) {
            System.err.println("Error: " + sqle.getMessage());
            sqle.printStackTrace();
        } finally {
            pConnection.closeConnection();
        }

        return notRead;
    }

    @Override
    public void setReadNotice(String pharm, String notice) throws NullStringException {

        if (pharm.isEmpty() || notice.isEmpty()) {
            throw new NullStringException();
        }

        pConnection = new DataBaseConnection();
        pConnection.openConnection();

        try {
            pConnection.statement = pConnection.connection.createStatement();
            pConnection.statement.executeUpdate("INSERT INTO readnotice " +
                    "VALUES( '" + notice + "', '" + pharm + "' )"
            );
        } catch (SQLException sqle) {
            System.out.println("Error: " + sqle.getMessage());
            sqle.printStackTrace();
        } finally {
            pConnection.closeConnection();
        }

    }

    @Override
    public void createNotice(String content) throws NullStringException {

        List<String> existingNotice = new ArrayList<>();

        if (content.isEmpty()) {
            throw new NullStringException();
        }

        pConnection = new DataBaseConnection();
        pConnection.openConnection();

        try {
            pConnection.statement = pConnection.connection.createStatement();
            pConnection.rs = pConnection.statement.executeQuery("SELECT id FROM notice " +
                    "WHERE content = '" + content + "' " +
                    "AND noticedate = CURRENT_DATE");

            while (pConnection.rs.next()) {
                existingNotice.add(pConnection.rs.getString("id"));
            }
        } catch (SQLException ignored) {
        }

        if (existingNotice.isEmpty()) {
            try {
                pConnection.statement = pConnection.connection.createStatement();
                pConnection.statement.executeUpdate("INSERT INTO notice " +
                        "VALUES( DEFAULT , '" + content + "', CURRENT_DATE)"
                );
            } catch (SQLException sqle) {
                System.out.println("Error: " + sqle.getMessage());
                sqle.printStackTrace();
            }
        }

        pConnection.closeConnection();

    }
}
