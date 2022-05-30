package Model.Utils.DAO;

import Model.Notice;
import Model.Utils.Exceptions.NullStringException;

import java.sql.SQLException;
import java.util.List;

public interface NoticeDAO {
    List<Notice> getAllNotices(String pharm) throws NullStringException;
    List<Notice> getNotReadNotices(String pharm) throws NullStringException;
    void setReadNotice(String pharm, String notice) throws NullStringException;
    void createNotice(String content) throws NullStringException;
}
