package Model.Utils.DAO;

import Model.Notice;

import java.sql.SQLException;
import java.util.List;

public interface NoticeDAO {
    List<Notice> getAllNotices(String pharm) throws SQLException;
    List<Notice> getNotReadNotices(String pharm) throws SQLException;
    void setReadNotice(String pharm, String notice) throws SQLException;
    void createNotice(String content) throws SQLException;
}
