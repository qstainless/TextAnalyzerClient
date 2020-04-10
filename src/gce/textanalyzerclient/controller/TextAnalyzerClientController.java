package gce.textanalyzerclient.controller;

import gce.textanalyzerclient.model.Word;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * This is the main controller for the GUI of the TextAnalyzer application.
 */
public class TextAnalyzerClientController implements Initializable {
    @FXML
    private Label messageLabel;

    @FXML
    private TextField urlTextField;

    @FXML
    private TableView<Word> wordTableView;

    @FXML
    private TableColumn<Word, Integer> wordRank;

    @FXML
    private TableColumn<Word, String> wordContent;

    @FXML
    private TableColumn<Word, Integer> wordFrequency;

    /**
     * Called by the {@code FXMLLoader} to initialize the controller after its
     * root element has been completely processed. Defines the properties of
     * the column names in the {@link TableView} where the program's results
     * will be displayed.
     *
     * @param location  The location used to resolve relative paths for the
     *                  root object, or <tt>null</tt> if the location is
     *                  not known.
     * @param resources The resources used to localize the root object, or
     *                  <tt>null</tt> if the root object was not localized.
     */
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        wordRank.setCellValueFactory(new PropertyValueFactory<>("wordRank"));
        wordContent.setCellValueFactory(new PropertyValueFactory<>("wordContent"));
        wordFrequency.setCellValueFactory(new PropertyValueFactory<>("wordFrequency"));
        System.out.println("TextAnalyzer Client ready. Waiting for user input.");
    }

    /**
     * Action to perform when the <code>Analyze!</code> button is clicked
     */
    @FXML
    public void handleAnalyzeButtonAction() {
        String targetUrl = urlTextField.getText();

        if (validateUrl(targetUrl)) {
            try {
                InetAddress host = InetAddress.getLocalHost();
                Socket socket = new Socket(host.getHostName(), 9876);

                ObjectOutputStream clientOut;
                ObjectInputStream clientIn;

                clientOut = new ObjectOutputStream(socket.getOutputStream());

                if (targetUrl.equals("exit")) {
                    System.out.println("\n==> User requested program termination. Exiting.");
                    clientOut.writeObject("exit");
                    clientOut.close();
                    socket.close();
                    System.exit(2);
                }

                System.out.println("\nUser input URL: " + targetUrl);
                System.out.println("\n==> URL sent to TextAnalyzer server for parsing.");
                clientOut.writeObject(urlTextField.getText());

                clientIn = new ObjectInputStream(socket.getInputStream());

                String dateTime = TextAnalyzerClientController.getDateTime();

                System.out.println("\n<== Data received at " + dateTime + ".");
                System.out.println("Displaying results.");

                int uniqueWords = (int) clientIn.readObject();
                int totalWords = (int) clientIn.readObject();

                ObservableList<Word> wordsList = FXCollections.observableArrayList();

                for (int i = 1; i <= uniqueWords; ++i) {
                    String wordContent = (String) clientIn.readObject();
                    int wordFrequency = (int) clientIn.readObject();

                    wordsList.add(new Word(i, wordContent, wordFrequency));
                }

                displaySortedWords(uniqueWords, totalWords, wordsList);

                clientIn.close();
                clientOut.close();
                socket.close();

                System.out.println("\nTextAnalyzer Client ready. Waiting for user input.");
            } catch (IOException | ClassNotFoundException e) {
                System.out.println(e.toString());
            }
        }
    }

    /**
     * Action to perform when the Quit menu item or Quit button is clicked
     */
    @FXML
    public void handleQuitButtonAction() {
        urlTextField.setText("exit");
        handleAnalyzeButtonAction();
    }

    /**
     * Populate the {@code TableView} with the words sorted by frequency in
     * descending order. Displays the total number of words and number of
     * unique words found in the source URL.
     *
     * @param uniqueWords    The number of unique words sent by the server
     * @param totalWords     The total number of words sent by the server
     * @param wordsToDisplay The word/frequency pairs to display
     */
    public void displaySortedWords(int uniqueWords, int totalWords, ObservableList<Word> wordsToDisplay) {
        wordTableView.getItems().clear();
        wordTableView.setEditable(false);

        NumberFormat wordCountFormat = NumberFormat.getInstance();

        messageLabel.setText("After parsing, " + wordCountFormat.format(uniqueWords)
                + " unique words were found, out of a total of "
                + wordCountFormat.format(totalWords) + " words.");

        wordTableView.setItems(wordsToDisplay);
    }

    /**
     * Checks whether or not the URL field is empty or invalid.
     *
     * @param url The URL submitted by the user
     * @return True if the URL field is not empty
     */
    public boolean validateUrl(String url) {
        wordTableView.getItems().clear();
        return textFieldNotEmpty(url, messageLabel, "The URL must be valid and cannot be empty.");
    }


    public static String getDateTime() {
        LocalDateTime dateTime = LocalDateTime.now();
        return dateTime.toString();
    }

    /**
     * {@code out} defaults to false if the input parameter is <tt>null</tt>
     * or empty and if it is a valid URL.
     *
     * @param targetUrl The URL entered by the user in the URL textfield.
     * @return True if the URL textfield is not empty and is a valid URL
     * @see TextAnalyzerClientController#textFieldNotEmpty(String, Label, String)
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
