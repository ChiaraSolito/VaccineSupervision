package Test.DoctorControllerTest;

import Control.DoctorControl.PatientInfoController;
import Model.Patient;
import Model.Report;
import Model.User;
import Model.Vaccination;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PatientInfoControllerTest {
    PatientInfoController patientInfoController;
    ByteArrayOutputStream outContent;


    @BeforeEach
    void setUp() {
        patientInfoController = new PatientInfoController(new User("doc1", "DOC1", false));
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    @Order(1)
    @DisplayName("Existing patient access")
    void getExistingPatient() {
        Patient patient = patientInfoController.getPatient("1");
        assertNotNull(patient);
        assertEquals("1", patient.getIdPatient());
        assertEquals("1970", patient.getBirthYear());
        assertEquals("VR", patient.getProvince());
        assertEquals("Insegnante", patient.getProfession());
    }

    @Test
    @Order(2)
    @DisplayName("Nonexistent patient access")
    void getNoPatient() {
        //Proving the handling of the esxception
        patientInfoController.getPatient("");
        String expectedOutput = "Error patient: Null String.\n";
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    @Order(3)
    @DisplayName("Existing patient report")
    void getExistingPatientReports() {
        List<Report> reports = patientInfoController.getPatientReports("1");
        assertNotNull(reports);
        assertEquals(3, reports.size());

        //asserting the equality between reports ids
        assertEquals("1", reports.get(0).getId());
        assertEquals("2", reports.get(1).getId());
        assertEquals("5", reports.get(2).getId());

        //asserting the correct id of the patient
        assertEquals("1", reports.get(0).getPatient());
        assertEquals("1", reports.get(1).getPatient());
        assertEquals("1", reports.get(2).getPatient());
    }

    @Test
    @Order(4)
    @DisplayName("Nonexistent patient report")
    void getNonexistentPatientReports() {
        patientInfoController.getPatientReports("");
        String expectedOutput = "Error patient: Null String.\n";
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    @Order(5)
    @DisplayName("Existing patient vaccinations")
    void getPatientVaccinations() {
        List<Vaccination> vaccinations = patientInfoController.getPatientVaccinations("1");
        assertNotNull(vaccinations);
        assertEquals(1, vaccinations.size());

        //asserting the equality between reports ids
        assertEquals("1", vaccinations.get(0).getPatient());

        //asserting the correct id of the patient

        assertEquals("Astrazeneca", vaccinations.get(0).getVaccine());
        assertEquals("Prima Dose", vaccinations.get(0).getTypeSomministration());
    }

    @Test
    @Order(6)
    @DisplayName("Nonexistent patient report")
    void getNonexistentPatientVaccinations() {
        patientInfoController.getPatientVaccinations("");
        String expectedOutput = "Error patient: Null String.\n";
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    @Order(7)
    @DisplayName("Existing patient two months vaccinations")
    void getPatientTwoMonthsVaccination() {
        List<Vaccination> TwoMvaccinations = patientInfoController.getPatientTwoMonthsVaccination("23", "2022-06-23");
        List<Vaccination> vaccinations = patientInfoController.getPatientVaccinations("23");
        assertNotNull(vaccinations);
        assertEquals(3, vaccinations.size());
        assertEquals(2, TwoMvaccinations.size());

        //asserting the correct id of the patient
        assertEquals("23", TwoMvaccinations.get(0).getPatient());

        //asserting correctness of values
        assertEquals("Moderna", TwoMvaccinations.get(0).getVaccine());
        assertEquals("Dose booster", TwoMvaccinations.get(0).getTypeSomministration());
        assertEquals("Moderna", TwoMvaccinations.get(1).getVaccine());
        assertEquals("Seconda dose", TwoMvaccinations.get(1).getTypeSomministration());
    }

    @Test
    @Order(8)
    @DisplayName("Nonexistent patient two months vaccinations")
    void getNoTwoMonthsVaccination() {
        patientInfoController.getPatientTwoMonthsVaccination("", "");
        String expectedOutput = "Error patient or reaction: Null String.\n";
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    @Order(9)
    @DisplayName("Existing patient number of vaccinations")
    void getNumberVaccination() {
        assertEquals(3, patientInfoController.getNumberVaccination("23"));
    }

    @Test
    @Order(10)
    @DisplayName("Nonexistent patient number of vaccinations")
    void getNoNumberVaccination() {
        patientInfoController.getNumberVaccination("");
        String expectedOutput = "Error patient: Null String.\n";
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    @Order(11)
    @DisplayName("Existing patient number of reports")
    void getNumberReport() {
        assertEquals(1, patientInfoController.getNumberReport("23"));
    }

    @Test
    @Order(12)
    @DisplayName("Nonexistent patient number of reports")
    void getNoNumberReport() {
        patientInfoController.getNumberReport("");
        String expectedOutput = "Error patient: Null String.\n";
        assertEquals(expectedOutput, outContent.toString());
    }
}