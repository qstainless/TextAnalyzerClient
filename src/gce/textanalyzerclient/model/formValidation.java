package gce.textanalyzerclient.model;

import javafx.scene.control.Label;

import java.net.URL;

/**
 * Validation class to check whether or not the URL text field is empty when
 * the user clicks the Analyze! button. The class does not check for valid
 * URLs.
 */
public class formValidation {

    /**
     * {@code out} defaults to false if the input parameter is <tt>null</tt>
     * or empty and if it is a valid URL.
     *
     * @param targetUrl The URL entered by the user in the URL textfield.
     * @return True if the URL textfield is not empty and is a valid URL
     * @see formValidation#textFieldNotEmpty(String, Label, String)
     */
    public static boolean textFieldNotEmpty(String targetUrl) {
        boolean out = false;

        if (targetUrl != null && !targetUrl.isEmpty() && isValidURL(targetUrl)) {
            out = true;
        }

        return out;
    }

    /**
     * @param targeturl      The value of the URL textfield.
     * @param messageLabel   Placeholder in the GUI for error messages
     * @param validationText Feedback to user on errors.
     * @return True if the URL textfield is not empty
     */
    public static boolean textFieldNotEmpty(String targeturl, Label messageLabel, String validationText) {
        boolean out = true;
        String message = null;

        if (!textFieldNotEmpty(targeturl)) {
            out = false;
            message = validationText;
        }

        messageLabel.setText(message);

        return out;
    }

    /**
     * Checks if the targetUrl is a valid URL
     *
     * @param targetUrl The target URL entered by the user
     * @return True if the URL is valir
     */
    public static boolean isValidURL(String targetUrl) {
        if (targetUrl.equals("exit")) {
            return true;
        }

        try {
            URL url = new URL(targetUrl);
            url.toURI();
            return true;
        } catch (Exception exception) {
            return false;
        }
    }
}
