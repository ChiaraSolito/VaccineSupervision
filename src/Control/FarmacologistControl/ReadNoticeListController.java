package Control.FarmacologistControl;

import Model.Notice;
import Model.User;
import Model.Utils.DAOImpl.NoticeDAOImpl;
import Model.Utils.DAOImpl.PatientDAOImpl;
import Model.Utils.Exceptions.NullStringException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class ReadNoticeListController {

    private User model;

    //Costruttore
    public ReadNoticeListController(User model) { this.model = model; }

    public List<Notice> getReadNotice() throws NullStringException {
        NoticeDAOImpl noticeDAO = new NoticeDAOImpl();
        ObservableList<Notice> notices;

        try {
            notices = FXCollections.observableArrayList(noticeDAO.getAllNotices(model.getUsername()));
        } catch (NullStringException e) {
            throw new NullStringException();
        }

        return notices;
    }
}
