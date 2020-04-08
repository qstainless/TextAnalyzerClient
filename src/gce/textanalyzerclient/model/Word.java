package gce.textanalyzerclient.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Word model used to populate the {@code TableView} with the text analysis results.
 */
public class Word {
    private final SimpleIntegerProperty wordRankProperty;
    private final SimpleStringProperty wordContentProperty;
    private final SimpleIntegerProperty wordFrequencyProperty;

    /**
     * Object to hold the words that will be displayed in the GUIs {@code TableView}
     *
     * @param wordRank      The ranking of each unique word
     * @param wordContent   Each unique word
     * @param wordFrequency The frequency of each unique word
     */
    public Word(int wordRank, String wordContent, int wordFrequency) {
        this.wordRankProperty = new SimpleIntegerProperty(wordRank);
        this.wordContentProperty = new SimpleStringProperty(wordContent);
        this.wordFrequencyProperty = new SimpleIntegerProperty(wordFrequency);
    }

    /**
     * @return The wordRankProperty
     */
    public int getWordRank() {
        return wordRankProperty.get();
    }

    /**
     * @return The wordContentProperty
     */
    public String getWordContent() {
        return wordContentProperty.get();
    }

    /**
     * @return The wordFrequencyProperty
     */
    public int getWordFrequency() {
        return wordFrequencyProperty.get();
    }
}
