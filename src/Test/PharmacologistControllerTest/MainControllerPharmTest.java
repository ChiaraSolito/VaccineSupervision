package Test.PharmacologistControllerTest;

import Control.FarmacologistControl.MainControllerPharm;
import Model.Notice;
import Model.User;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

class MainControllerPharmTest {

    User user;

    MainControllerPharm controller;

    @BeforeEach
    void setUp() {
        controller = new MainControllerPharm(new User("farm1", "FARM1", true));
    }


    @Test
    @Order(1)
    @DisplayName("get Unread Notices")
    void getUnreadNotices() {
        List<Notice> notices = new ArrayList<>();
        assertEquals(notices, controller.getUnreadNotices());
    }

    @Test
    @Order(1)
    @DisplayName("set Notice read with non existing Notice")
    void exceptionTestingGetUnreadNotices() {
        assertEquals(null, controller.getUnreadNotices());
        try {
            controller.getUnreadNotices();
        } catch (Exception e) {
            Assertions.fail("Exception " + e);
        }
    }

    @Test
    @Order(2)
    @DisplayName("set Notice read with existing Notice")
    void setNoticeRead() {
        //assertEquals();
        fail("Not implemented yet");
    }

    @Test
    @Order(2)
    @DisplayName("set Notice read with non existing Notice")
    void exceptionTestingSetNoticeRead() {
/*        assertEquals(null, controller.setNoticeRead(null));
        try {
            controller.setNoticeRead();
        } catch (Exception e) {
            Assertions.fail("Exception " + e);
        }*/

        fail("Not implemented yet");
    }
}