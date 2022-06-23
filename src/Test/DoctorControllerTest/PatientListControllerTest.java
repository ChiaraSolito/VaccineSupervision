package Test.DoctorControllerTest;

import Control.DoctorControl.PatientListController;
import Model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PatientListControllerTest {

    PatientListController patientListController;

    @Test
    @Order(1)
    @DisplayName("Get All Patients")
    void getAllPatients() {
        patientListController = new PatientListController(new User("doc1", "DOC1", false));
        assertEquals(14, patientListController.getAllPatients().size());
        assertEquals("16", patientListController.getAllPatients().get(5));
    }

    @Test
    @Order(2)
    @DisplayName("Get No Patients for non existing user")
    void getNoPatients() {
        patientListController = new PatientListController(new User());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        //Proving the handling of the esxception
        patientListController.getAllPatients();
        String expectedOutput = "Error patient: Null String.\n";
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    @Order(3)
    @DisplayName("Get an empty list of Patients")
    void getPatientNonexistentDoctor() {
        patientListController = new PatientListController(new User("prova", "prova", true));
        assertEquals(0, patientListController.getAllPatients().size());
    }

}