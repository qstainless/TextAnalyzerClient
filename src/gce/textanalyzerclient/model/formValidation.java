package gce.textanalyzerclient.model;

import javafx.scene.control.Label;

/**
 * Validation class to check whether or not the URL text field is empty when
 * the user clicks the Analyze! button. The class does not check for valid
 * URLs.
 */
public class formValidation {

    /**
     * {@code out} defaults to false if the input parameter is <tt>null</tt>
     * or empty.
     *
     * @param input The value of the URL textfield.
     * @return True if the URL textfield is not empty
     *
     * @see formValidation#textFieldNotEmpty(String, Label, String)
     */
    public static boolean textFieldNotEmpty (String input) {
        boolean out = false;

        if (input != null && !input.isEmpty()) {
            out = true;
        }

        return out;
    }

    /**
     * @param input The value of the URL textfield.
     * @param messageLabel Placeholder in the GUI for error messages
     * @param validationText Feedback to user on errors.
     * @return True if the URL textfield is not empty
     */
    public static boolean textFieldNotEmpty (String input, Label messageLabel, String validationText) {
        boolean out = true;
        String message = null;

        if (!textFieldNotEmpty(input)) {
            out = false;
            message = validationText;
        }

        messageLabel.setText(message);

        return out;
    }
}
