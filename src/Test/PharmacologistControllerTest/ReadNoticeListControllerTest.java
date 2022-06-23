package Test.PharmacologistControllerTest;

import Control.FarmacologistControl.MainControllerPharm;
import Control.FarmacologistControl.ReadNoticeListController;
import Model.Notice;
import Model.User;
import javafx.beans.property.SimpleStringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReadNoticeListControllerTest {

    User user;

    ReadNoticeListController controller;

    @BeforeEach
    void setUp() {
        controller = new ReadNoticeListController(new User("farm1", "FARM1", true));
    }

    @Test
    void getReadNotice() {
        List<Notice> notices = new ArrayList<>();
        notices.add(new Notice(new SimpleStringProperty("1"), new SimpleStringProperty("Questo avviso è per il primo accesso del farmacologo. Benvenuto! Riceverai avvisi come pop up, potrai riguardarli nella sezione Avvisi già letti"),
                new SimpleStringProperty("2022-06-08")));
        notices.add(new Notice(new SimpleStringProperty("2"), new SimpleStringProperty("Questo è il secondo avviso"),
                new SimpleStringProperty("2022-06-07")));

        System.out.println(controller.getReadNotice());

        assertEquals(notices, controller.getReadNotice());
    }
}