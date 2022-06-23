package Test.PharmacologistControllerTest;

import Control.FarmacologistControl.ReportListController;
import Model.Report;
import Model.User;
import javafx.beans.property.SimpleStringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReportListControllerTest {

    User user;

    ReportListController controller;

    @BeforeEach
    void setUp() {
        controller = new ReportListController();
    }

    @Test
    void getReportList() {
        List<Report> allReports = new ArrayList<>();

        allReports.add(new Report());
/*        1	"2022-05-04"	"2022-05-02"	"miocardite"	1	"doc1"
        2	"2022-04-04"	"2022-04-02"	"miocardite"	1	"doc1"
        3	"2022-04-20"	"2022-04-19"	"miocardite"	1	"doc1"
        4	"2022-06-20"	"2022-06-20"	"miocardite"	1	"doc1"
        5	"2022-06-03"	"2022-06-01"	"asma"	2	"doc2"
        6	"2022-05-03"	"2022-05-03"	"psoriasi"	3	"doc2"
        7	"2022-06-22"	"2022-06-21"	"asma"	4	"doc1"
        8	"2022-06-22"	"2022-06-20"	"febbre"	5	"doc1"
        9	"2022-06-21"	"2022-06-19"	"miocardite"	3	"doc2"
        10	"2022-06-22"	"2022-06-21"	"miocardite"	4	"doc1"
        11	"2022-06-23"	"2022-06-23"	"febbre"	4	"doc1"*/

        System.out.println(controller.getReportList());
    }

    @Test
    void getPatientTwoMonthsVaccination() {
        fail("Not implemented yet");
    }
}