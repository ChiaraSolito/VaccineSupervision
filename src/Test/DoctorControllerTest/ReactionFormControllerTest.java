package Test.DoctorControllerTest;

import Control.DoctorControl.ReactionFormController;
import Model.Patient;
import Model.Reaction;
import Model.RiskFactor;
import Model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ReactionFormControllerTest {
    ReactionFormController reactionFormController;

    @Test
    @Order(1)
    @DisplayName("Get All Patients")
    void getAllPatients() {
        reactionFormController = new ReactionFormController(new User("doc1", "DOC1", false));
        assertEquals(14, reactionFormController.getAllPatients().size());
        assertEquals("16", reactionFormController.getAllPatients().get(5));
    }

    @Test
    @Order(2)
    @DisplayName("Get No Patients for non existing user")
    void getNoPatients() {
        reactionFormController = new ReactionFormController(new User());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        //Proving the handling of the esxception
        reactionFormController.getAllPatients();
        String expectedOutput = "Error patient: Null String.\n";
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    @Order(3)
    @DisplayName("Get all existing risk")
    void getAllExistingRisks() {
        reactionFormController = new ReactionFormController(new User("doc1", "DOC1", false));
        assertEquals(6, reactionFormController.getAllExistingRisks().size());
        assertEquals("Malattia cerebrovascolare", reactionFormController.getAllExistingRisks().get(5));
    }

    @Test
    @Order(4)
    @DisplayName("Get No risk")
    void getNoRisks() {
        reactionFormController = new ReactionFormController(new User());
        assertEquals(0, reactionFormController.getAllExistingRisks().size());
    }

    @Test
    @Order(5)
    @DisplayName("Get existing reactions")
    void getAllExistingReactions() {
        reactionFormController = new ReactionFormController(new User("doc1", "DOC1", false));
        assertEquals(6, reactionFormController.getAllExistingReactions().size());
        assertEquals("Eruzione cutanea", reactionFormController.getAllExistingReactions().get(4));
    }

    @Test
    @Order(6)
    @DisplayName("Existing patient access")
    void getExistingPatient() {
        reactionFormController = new ReactionFormController(new User("doc1", "DOC1", false));
        Patient patient = reactionFormController.getPatient("1");
        assertNotNull(patient);
        assertEquals("1", patient.getIdPatient());
        assertEquals("1970", patient.getBirthYear());
        assertEquals("VR", patient.getProvince());
        assertEquals("Insegnante", patient.getProfession());
    }

    @Test
    @Order(7)
    @DisplayName("Nonexistent patient access")
    void getNoPatient() {
        reactionFormController = new ReactionFormController(new User());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        //Proving the handling of the esxception
        reactionFormController.getPatient("");
        String expectedOutput = "Error: Null String.\n";
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    @Order(8)
    @DisplayName("Get existing risk")
    void getRisk() {
        reactionFormController = new ReactionFormController(new User("doc1", "DOC1", false));
        RiskFactor risk = reactionFormController.getRisk("Diabete");
        assertNotNull(risk);
        assertEquals("Diabete", risk.getName());
        assertEquals("Livelli di glucosio alti nel sangue.", risk.getDescription());
        assertEquals(3, risk.getRiskLevel());
    }

    @Test
    @Order(9)
    @DisplayName("Nonexistent risk")
    void getNoRisk() {
        reactionFormController = new ReactionFormController(new User());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        //Proving the handling of the esxception
        reactionFormController.getRisk("");
        String expectedOutput = "Error risk: Null String.\n";
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    @Order(10)
    @DisplayName("Get existing reaction")
    void getReaction() {
        reactionFormController = new ReactionFormController(new User("doc1", "DOC1", false));
        Reaction reaction = reactionFormController.getReaction("Vomito");
        assertNotNull(reaction);
        assertEquals("Vomito", reaction.getName());
        assertEquals("Attacchi di vomito e nausea", reaction.getDescription());
        assertEquals(2, reaction.getGravity());
    }

    @Test
    @Order(11)
    @DisplayName("Get nonexistent reaction")
    void getNoReaction() {
        reactionFormController = new ReactionFormController(new User());
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        //Proving the handling of the esxception
        reactionFormController.getReaction("");
        String expectedOutput = "Error reaction: Null String.\n";
        assertEquals(expectedOutput, outContent.toString());
    }

}