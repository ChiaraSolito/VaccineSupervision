package Control.FarmacologistControl;

import Model.Notice;
import Model.User;
import Model.Utils.DAOImpl.NoticeDAOImpl;
import Model.Utils.Exceptions.NullStringException;

import java.util.ArrayList;
import java.util.List;


public class MainControllerPharm {
    private final User model;

    private NoticeDAOImpl noticesDAO;

    public MainControllerPharm(User model) {
        this.model = model;
    }

    public List<Notice> getUnreadNotices(){
        noticesDAO = new NoticeDAOImpl();
        List<Notice> notices = new ArrayList<>();
        try{
            notices = noticesDAO.getNotReadNotices(model.getUsername());
        } catch (NullStringException nse){
            System.err.println("String Error: " + nse.getMessage());
        }
        return notices;
    }

    public void setNoticeRead(Notice notice){
        noticesDAO = new NoticeDAOImpl();
        try{
            noticesDAO.setReadNotice(model.getUsername(), notice.getId());
        } catch (NullStringException nse){
            System.err.println("String Error: ");
            nse.printStackTrace();
        }
    }
}
