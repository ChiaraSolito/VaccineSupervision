package Control.FarmacologistControl;

import Model.Notice;
import Model.User;
import Model.Utils.DAOImpl.NoticeDAOImpl;
import Model.Utils.Exceptions.NullStringException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class ReadNoticeListController {

    private final User model;

    //Costruttore
    public ReadNoticeListController(User model) { this.model = model; }

    public List<Notice> getReadNotice() {
        NoticeDAOImpl noticeDAO = new NoticeDAOImpl();
        ObservableList<Notice> notices = null;

        try {
            notices = FXCollections.observableArrayList(noticeDAO.getAllNotices(model.getUsername()));
        } catch (NullStringException e) {
            System.err.println("Error username: " + e.getMessage());
        }

        return notices;
    }
}
