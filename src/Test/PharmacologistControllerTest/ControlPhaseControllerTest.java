package Test.PharmacologistControllerTest;

import Control.FarmacologistControl.ControlPhaseController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ControlPhaseControllerTest {

    ControlPhaseController controller;

    @BeforeEach
    void setUp() {
        controller = new ControlPhaseController();
    }


    @Test
    @Order(1)
    @DisplayName("get All Vaccines")
    void getAllVaccines() {
        assertEquals(5, controller.getAllVaccines().size());
        assertEquals("Moderna", controller.getAllVaccines().get(4));
    }


    @Test
    @Order(2)
    @DisplayName("Get total number of the proposed control phases for a vaccine")
    void getTotalNumberControlPhase() {
        assertEquals(2, controller.getTotalNumberControlPhase("Moderna"));
    }

    @Test
    @Order(3)
    @DisplayName("Get total number with no input")
    void getNoNumber() {
        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errContent));

        //Proving the handling of the exception
        assertEquals(0, controller.getTotalNumberControlPhase(""));
        String expectedOutput = "Error vaccine: Null String.\n";
        assertEquals(expectedOutput, errContent.toString());
    }


    @Test
    @Order(4)
    @DisplayName("Get the number of control phases proposed in the last six months")
    void getSixMonthsNumberControlPhase() {
        assertEquals(2, controller.getTotalNumberControlPhase("Moderna"));
    }

    @Test
    @Order(5)
    @DisplayName("Get six months number with no input")
    void getNoNumberSixMonths() {
        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errContent));

        //Proving the handling of the exception
        assertEquals(0, controller.getSixMonthsNumberControlPhase(""));
        String expectedOutput = "Error vaccine: Null String.\n";
        assertEquals(expectedOutput, errContent.toString());
    }

}