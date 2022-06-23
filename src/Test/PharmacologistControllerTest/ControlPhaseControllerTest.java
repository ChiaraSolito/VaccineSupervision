package Test.PharmacologistControllerTest;

import Control.FarmacologistControl.ControlPhaseController;
import Control.FarmacologistControl.MainControllerPharm;
import Model.ControlPhase;
import Model.User;
import javafx.beans.property.SimpleStringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ControlPhaseControllerTest {

    User user;

    ControlPhaseController controller;

    @BeforeEach
    void setUp() {
        controller = new ControlPhaseController();
    }


    @Test
    @DisplayName("get All Vaccines")
    void getAllVaccines() {
        List<String> vaccines = new ArrayList<>();
        assertEquals(vaccines, controller.getAllVaccines());
    }

    @Test
    void addControlPhase() {
        ControlPhase controlPhase = new ControlPhase(new SimpleStringProperty("2022-06-23"),
                new SimpleStringProperty("Moderna"), new SimpleStringProperty(user.getUsername()));
        //assertEquals("controlPhase", controller.addControlPhase(controlPhase));
    }

    @Test
    void getTotalNumberControlPhase() {
        String vaccine = "Moderna";
        assertEquals(1, controller.getTotalNumberControlPhase(vaccine));
    }

    @Test
    @DisplayName("Get Total Number with no vaccine")
    void exceptionTestingGetTotalNumber() {

    }

    @Test
    void getSixMonthsNumberControlPhase() {
        fail("Not implemented yet");
    }
}