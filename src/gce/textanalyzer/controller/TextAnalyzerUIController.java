package gce.textanalyzer.controller;

import gce.textanalyzer.model.Word;
import gce.textanalyzer.model.formValidation;
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
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * This is the main controller for the GUI of the TextAnalyzer application.
 */
public class TextAnalyzerUIController implements Initializable {
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
     * Called by the {@code FXMLLoader} to initialize the controller after its root
     * element has been completely processed. Defines the properties of the
     * column names in the {@link TableView} where the programs results
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
     * Action to perform when the Analyze! button is clicked
     */
    @FXML
    public void handleAnalyzeButtonAction() throws IOException, ClassNotFoundException, InterruptedException {
        connectToServer(urlTextField.getText());
    }

    public void connectToServer(String targetUrl) throws IOException, ClassNotFoundException, InterruptedException {
        InetAddress host = InetAddress.getLocalHost();

        while (true) {
            try {
                Socket socket;

                ObjectOutputStream outputStream;
                ObjectInputStream inputStream;

                socket = new Socket(host.getHostName(), 9876);

                outputStream = new ObjectOutputStream(socket.getOutputStream());

                if (!validateUrl(targetUrl)) {
                    outputStream.writeObject("bad_url");
                } else {
                    System.out.println("\n------------------------------");
                    System.out.println("User input URL: " + targetUrl);
                    System.out.println("\n==> URL sent to TextAnalyzer server for parsing.");
                    outputStream.writeObject(urlTextField.getText());
                }

                inputStream = new ObjectInputStream(socket.getInputStream());

                System.out.println("\n<== Response received.");

                int uniqueWords = (int) inputStream.readObject();
                int totalWords = (int) inputStream.readObject();

                ObservableList<Word> wordsList = FXCollections.observableArrayList();

                for (int i = 1; i <= uniqueWords; ++i) {
                    String wordContent = (String) inputStream.readObject();
                    int wordFrequency = (int) inputStream.readObject();

                    wordsList.add(new Word(i, wordContent, wordFrequency));

                    System.out.println(i + ". " + wordContent + " (" + wordFrequency + ")");
                }

                displaySortedWords(uniqueWords, totalWords, wordsList);

                System.out.println("\nUnique words: " + uniqueWords + "  Total words: " + totalWords);

                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                System.out.println(e.toString());
            }
        }
    }

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
     * Calls the {@link formValidation} class methods to check whether
     * or not the URL fields is empty.
     *
     * @param url The URL submitted by the user
     * @return True if the URL field is not empty
     */
    public boolean validateUrl(String url) {
        return formValidation.textFieldNotEmpty(url, messageLabel, "The URL cannot be empty.");
    }

}
