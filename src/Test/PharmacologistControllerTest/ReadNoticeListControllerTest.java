package Test.PharmacologistControllerTest;

import Control.FarmacologistControl.MainControllerPharm;
import Control.FarmacologistControl.ReadNoticeListController;
import Model.Notice;
import Model.User;
import javafx.beans.property.SimpleStringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReadNoticeListControllerTest {

    User user;

    ReadNoticeListController controller;

    @Test
    void getReadNotice() {
        controller = new ReadNoticeListController(new User("farm1", "FARM1", true));
        List<Notice> notices = controller.getReadNotice();

        assertEquals("9",notices.get(0).getId());
        assertEquals("Avviso importante! I vaccini\n" +
                "[Moderna]\n" +
                " hanno riportato più di 5 reazioni di gravità maggiore di 3 negli ultimi 7 giorni.",notices.get(0).getContent());
        assertEquals("2022-06-21",notices.get(0).getNoticeDate());
    }

    @Test
    void getNoNotice(){
        controller = new ReadNoticeListController(new User());
        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errContent));

        //Proving the handling of the esxception
        assertNull(controller.getReadNotice());
        String expectedOutput = "Error username: Null String.\n";
        assertEquals(expectedOutput, errContent.toString());
    }

}