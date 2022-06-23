package Test;

import Control.LoginController;
import Model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoginControllerTest {
    LoginController loginController;
    User user;

    @Test
    @Order(1)
    @DisplayName("Existing Doctor User")
    void checkAccessDoctor() throws SQLException, FileNotFoundException {
        user = new User("doc3", "DOC3", false);
        loginController = new LoginController(user);

        assertEquals(0, loginController.checkAccess(), "Return for doctor should be zero");
    }

    @Test
    @Order(2)
    @DisplayName("Existing pharmacologist user")
    void checkAccessPharm() throws SQLException, FileNotFoundException {
        user = new User("farm2", "FARM2", true);
        loginController = new LoginController(user);

        assertEquals(1, loginController.checkAccess(), "Return for pharmacologist should be one");
    }

    @Test
    @Order(3)
    @DisplayName("Nonexistent User")
    void checkInesistentAccess() throws SQLException, FileNotFoundException {
        user = new User("123", "prova", true);
        loginController = new LoginController(user);

        assertEquals(2, loginController.checkAccess(), "Return for nonexistent user should be two");
    }
}