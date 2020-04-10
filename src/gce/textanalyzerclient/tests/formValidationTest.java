package gce.textanalyzerclient.tests;

import gce.textanalyzerclient.controller.TextAnalyzerClientController;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class formValidationTest {
    static final String nullUrl = null;
    static final String notEmptyUrl = "http://somewhere.com/file_to_read.html";
    static final String malformedUrl = "ervsdfavadfa";

    /**
     * Checks if the URL text field is empty
     */
    @Test
    @Order(1)
    @DisplayName("The URL field is empty.")
    void testEmptyUrl() {
        boolean actualResult = TextAnalyzerClientController.textFieldNotEmpty(nullUrl);
        assertFalse(actualResult);
    }

    /**
     * Checks if the URL text field is not empty.
     */
    @Test
    @Order(2)
    @DisplayName("The URL field is not empty.")
    void testNotEmptyUrl() {
        boolean actualResult = TextAnalyzerClientController.textFieldNotEmpty(notEmptyUrl);
        assertTrue(actualResult);
    }

    /**
     * Checks if the URL text field is malformed.
     */
    @Test
    @Order(3)
    @DisplayName("The URL provided is malformed.")
    void testMalformedUrl() {
        boolean actualResult = TextAnalyzerClientController.textFieldNotEmpty(malformedUrl);
        assertFalse(actualResult);
    }
}