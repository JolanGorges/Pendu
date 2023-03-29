package com.pendu.pendu.models;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Normalizer;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GameModel {
    private final StringProperty playerName = new SimpleStringProperty("Jolan");
    private final IntegerProperty moveCount = new SimpleIntegerProperty(0);
    private final IntegerProperty errorCount = new SimpleIntegerProperty(0);
    private final StringProperty hiddenWord = new SimpleStringProperty("");
    private final ObjectProperty<Image> hangmanImage = new SimpleObjectProperty<>();
    private final SimpleObjectProperty<ObservableList<String>> words = new SimpleObjectProperty<>(FXCollections.observableArrayList());
    private final StringProperty duration = new SimpleStringProperty("00:00");
    private final StringProperty score = new SimpleStringProperty("0");
    private final SimpleBooleanProperty gameOver = new SimpleBooleanProperty(false);
    private final JsonScoreDAO scoreDao;
    private String wordToGuess;
    private long startTime;

    public GameModel() {
        scoreDao = new JsonScoreDAO();
        Charset charset = StandardCharsets.UTF_8;
        try {
            List<String> lines = Files.readAllLines(Path.of("src\\main\\resources\\liste_francais.txt"), charset);
            int randomIndex = (int) (Math.random() * lines.size());
            wordToGuess = lines.get(randomIndex);
            hiddenWord.set("_".repeat(wordToGuess.length()));
        } catch (IOException ex) {
            System.out.format("I/O error: %s%n", ex);
        }
    }

    public StringProperty durationProperty() {
        return duration;
    }

    public StringProperty scoreProperty() {
        return score;
    }

    public SimpleBooleanProperty gameOverProperty() {
        return gameOver;
    }

    public ObjectProperty<Image> hangmanImageProperty() {
        return hangmanImage;
    }

    public StringProperty hiddenWordProperty() {
        return hiddenWord;
    }

    public String getWordToGuess() {
        return wordToGuess;
    }

    public void setPlayerName(String value) {
        playerName.set(value);
    }

    public IntegerProperty moveCountProperty() {
        return moveCount;
    }

    public IntegerProperty errorCountProperty() {
        return errorCount;
    }

    public SimpleObjectProperty<ObservableList<String>> wordsProperty() {
        return words;
    }

    public void updateImage() {
        int errorCountTemp = this.errorCount.get();
        if (errorCountTemp > 0 && errorCountTemp < 11) {
            InputStream stream = getClass().getResourceAsStream("/com/pendu/pendu/images/" + errorCountTemp + ".png");
            Image image = new Image(stream);
            hangmanImage.set(image);
        }
    }

    public String stripAccents(String s) {
        return Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }
    public void checkWord(String word) {
        if (gameOver.get()) return;
        if (startTime == 0) startTime = System.currentTimeMillis();
        String strippedWordToGuess = stripAccents(wordToGuess);
        String strippedWord = stripAccents(word);
        if (strippedWordToGuess.equals(strippedWord)) {
            hiddenWord.set(wordToGuess);
        } else {
            errorCount.set(errorCount.get() + 1);
            if (!words.get().contains(word)) words.get().add(word);
        }
        moveCount.set(moveCount.get() + 1);
        updateImage();
        checkGameOver();
    }

    public void checkLetter(String letter) {
        if (gameOver.get()) return;
        if (startTime == 0) startTime = System.currentTimeMillis();
        String strippedWordToGuess = stripAccents(wordToGuess);
        if (strippedWordToGuess.contains(letter) && !hiddenWord.get().contains(letter)) {
            StringBuilder newWord = new StringBuilder();
            for (int i = 0; i < strippedWordToGuess.length(); i++) {
                if (strippedWordToGuess.charAt(i) == letter.charAt(0)) {
                    newWord.append(wordToGuess.charAt(i));
                } else {
                    newWord.append(hiddenWord.get().charAt(i));
                }
            }
            hiddenWord.set(newWord.toString());
        } else {
            errorCount.set(errorCount.get() + 1);
        }
        moveCount.set(moveCount.get() + 1);
        updateImage();
        checkGameOver();
    }

    public void checkGameOver() {
        if (errorCount.get() >= 10 || hiddenWord.get().equals(wordToGuess)) {
            long endTime = System.currentTimeMillis();
            long durationMillis = endTime - startTime;
            long hours = TimeUnit.MILLISECONDS.toHours(durationMillis);
            long minutes = TimeUnit.MILLISECONDS.toMinutes(durationMillis) - TimeUnit.HOURS.toMinutes(hours);
            long seconds = TimeUnit.MILLISECONDS.toSeconds(durationMillis) - TimeUnit.MINUTES.toSeconds(minutes);
            String durationTemp = "";
            if (hours > 0) {
                durationTemp = hours + "h" + minutes + "m" + seconds + "s";
            } else if (minutes > 0) {
                durationTemp = minutes + "m" + seconds + "s";
            } else if (seconds > 0) {
                durationTemp = seconds + "s";
            }
            duration.set("Duration : " + durationTemp);

            int scoreTemp;
            if (errorCount.get() >= 10) scoreTemp = 0;
            else if (minutes == 0) scoreTemp = 10000;
            else scoreTemp = (int) (10000 / (minutes * errorCount.get() * (moveCount.get() / 2)));
            score.set("Score : " + scoreTemp);
            scoreDao.saveScore(new Score(playerName.get(), scoreTemp, durationTemp, moveCount.get(), errorCount.get()));
            gameOver.set(true);
        }
    }
}
