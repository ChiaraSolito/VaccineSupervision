package Test.DoctorControllerTest;

import Control.DoctorControl.PatientInfoController;
import Model.User;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PatientInfoControllerTest {
    PatientInfoController patientInfoController;


    @BeforeEach
    void setUp() {
        patientInfoController = new PatientInfoController(new User("doc1", "DOC1", false));
    }

    @Test
    @Order(1)
    @DisplayName("Existing patient access")
    void getExistingPatient() {

        //assertEquals(patientInfoController.getPatient("1"));
    }

    @Test
    @Order(1)
    @DisplayName("Nonexistent patient access")
    void exceptionTesting() {
        assertEquals(null, patientInfoController.getPatient(""));
        try {
            patientInfoController.getPatient("");
        } catch (Exception e) {
            Assertions.fail("Exception " + e);
        }
    }

    @Test
    void getPatientReports() {
    }

    @Test
    void getPatientVaccinations() {
    }

    @Test
    void getPatientTwoMonthsVaccination() {
    }

    @Test
    void getNumberVaccination() {
    }

    @Test
    void getNumberReport() {
    }
}