package Test.PharmacologistControllerTest;

import Control.FarmacologistControl.MainControllerPharm;
import Model.Notice;
import Model.User;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

class MainControllerPharmTest {

    MainControllerPharm controller;


    @Test
    @Order(1)
    @DisplayName("get Unread Notices")
    void getUnreadNotices() {
        controller = new MainControllerPharm(new User("farm2", "FARM2", true));
        List<Notice> notices = controller.getUnreadNotices();

        assertEquals("2", notices.get(0).getId());
        assertEquals("Questo è il secondo avviso", notices.get(0).getContent());
        assertEquals("2022-06-08", notices.get(0).getNoticeDate());
    }

    @Test
    @Order(1)
    void exceptionTestingGetUnreadNotices() {
        controller = new MainControllerPharm(new User());
        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errContent));

        //Proving the handling of the esxception
        List<Notice> notices = new ArrayList<>();
        assertEquals(notices, controller.getUnreadNotices());
        String expectedOutput = "String Error: Null String.\r\n";
        assertEquals(expectedOutput, errContent.toString());
    }
}