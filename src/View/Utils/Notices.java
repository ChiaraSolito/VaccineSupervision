package View.Utils;

import Model.Utils.DAOImpl.NoticeDAOImpl;
import Model.Utils.DAOImpl.ReportDAOImpl;
import Model.Utils.Exceptions.NullStringException;

import java.util.Calendar;
import java.util.List;


public class Notices {
    public static void createNotices() {
        ReportDAOImpl reportDAO = new ReportDAOImpl();
        NoticeDAOImpl noticeDAO = new NoticeDAOImpl();
        Calendar calendar = Calendar.getInstance();

        if (reportDAO.getReportNumber() > 50) {
            try {
                noticeDAO.createNotice("Negli ultimi 7 giorni si sono superate le 50 segnalazioni. Riceverai questo messaggio ad ogni apertura dell applicazione, se non diminuiscono.");
            } catch (NullStringException e) {
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
                    calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                try {
                    noticeDAO.createNotice("Avviso di default: è il weekend, ricordati di controllare la situazione.");
                } catch (NullStringException e) {
                    System.out.println("Error: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }

        List<String> dangerousVaccines = reportDAO.countSevereReaction();
        if (!dangerousVaccines.isEmpty()) {
            try {
                noticeDAO.createNotice("Avviso importante! I vaccini\n" + dangerousVaccines + "" +
                        "\n hanno riportato più di 5 reazioni di gravità maggiore di 3 negli ultimi 7 giorni.");
            } catch (NullStringException e) {
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
