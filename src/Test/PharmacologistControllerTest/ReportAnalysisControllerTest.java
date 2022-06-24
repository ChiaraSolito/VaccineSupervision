package Test.PharmacologistControllerTest;

import Control.FarmacologistControl.ReportAnalysisController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ReportAnalysisControllerTest {

    ReportAnalysisController controller;

    @BeforeEach
    void setUp() {
        controller = new ReportAnalysisController();
    }

    @Test
    void getReactionProvince() {
        Map<String, Integer> reactionProvince = controller.getReactionProvince();
        assertEquals(2, reactionProvince.get("Rovigo"));
        assertEquals(3, reactionProvince.get("Treviso"));
    }

    @Test
    void getReactionSite() {
        Map<String, Integer> reactionSite = controller.getReactionSite();
        assertEquals(1, reactionSite.get("Padova"));
        assertEquals(3, reactionSite.get("Treviso"));
    }

    @Test
    void countVaccineSevereReaction() {
        Map<String, Integer> numberSevereReaction = controller.countVaccineSevereReaction();
        assertEquals(1, numberSevereReaction.get("Astrazeneca"));
    }

    @Test
    void getReaction6Months() {
        Map<String, Integer> reactionSite = controller.getReaction6Months();
        assertEquals(5, reactionSite.get("Moderna"));
        assertEquals(1, reactionSite.get("Jannsen"));
    }

    @Test
    void getReactionNumber() {
        Map<String, Integer> reactionSite = controller.getReactionNumber();
        assertEquals(3, reactionSite.get("Astrazeneca"));
        assertEquals(1, reactionSite.get("Pfizer"));
    }
}