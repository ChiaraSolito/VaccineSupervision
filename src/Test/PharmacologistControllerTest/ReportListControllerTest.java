package Test.PharmacologistControllerTest;

import Control.FarmacologistControl.ReportListController;
import Model.Report;
import Model.Vaccination;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReportListControllerTest {

    ReportListController controller;

    @BeforeEach
    void setUp() {
        controller = new ReportListController();
    }

    @Test
    @Order(1)
    void getReportList() {
        List<Report> allReports = controller.getReportList();

        assertEquals("1", allReports.get(0).getId());
        assertEquals("2022-05-04", allReports.get(0).getReportDate());
        assertEquals("2022-05-02", allReports.get(0).getReactionDate());
        assertEquals("miocardite", allReports.get(0).getReaction());
        assertEquals("1", allReports.get(0).getPatient());
        assertEquals("doc1", allReports.get(0).getDoctor());

        assertEquals("6", allReports.get(5).getId());
        assertEquals("2022-05-03", allReports.get(5).getReportDate());
        assertEquals("2022-05-03", allReports.get(5).getReactionDate());
        assertEquals("psoriasi", allReports.get(5).getReaction());
        assertEquals("3", allReports.get(5).getPatient());
        assertEquals("doc2", allReports.get(5).getDoctor());

    }

    @Test
    @Order(2)
    void getPatientTwoMonthsVaccination() {
        List<Vaccination> twoMonthsVaccination = controller.getPatientTwoMonthsVaccination("2", "2022-06-01");

        assertEquals("2", twoMonthsVaccination.get(0).getPatient());
        assertEquals("Jannsen", twoMonthsVaccination.get(0).getVaccine());
        assertEquals("Unica", twoMonthsVaccination.get(0).getTypeSomministration());
        assertEquals("Padova", twoMonthsVaccination.get(0).getVaccinationSite());
        assertEquals("2022-04-05", twoMonthsVaccination.get(0).getVaccinationDate());
    }

    @Test
    @Order(2)
    @DisplayName("Get two months vaccinations with no input")
    void getNoTwoMonthsVaccinations() {
        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errContent));

        //Proving the handling of the exception
        assertNull(controller.getPatientTwoMonthsVaccination("", ""));
        String expectedOutput = "Error patient or reactionDate: Null String.\r\n";
        assertEquals(expectedOutput, errContent.toString());
    }

}