package Model.Utils.DAO;

import Model.Notice;
import Model.Utils.Exceptions.NullStringException;

import java.sql.SQLException;
import java.util.List;

public interface NoticeDAO {
    List<Notice> getAllNotices(String pharm) throws SQLException, NullStringException;
    List<Notice> getNotReadNotices(String pharm) throws SQLException, NullStringException;
    void setReadNotice(String pharm, String notice) throws SQLException, NullStringException;
    void createNotice(String content) throws SQLException, NullStringException;
}
