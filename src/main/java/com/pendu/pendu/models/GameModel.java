package com.pendu.pendu.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class GameModel {
    private final StringProperty playerName = new SimpleStringProperty("Jolan");

    private String wordToGuess;


    public GameModel() {
        Charset charset = StandardCharsets.UTF_8;
        try {
            List<String> lines = Files.readAllLines(Path.of("src\\main\\resources\\liste_francais.txt"), charset);
            int randomIndex = (int) (Math.random() * lines.size());
            wordToGuess = lines.get(randomIndex);
        } catch (IOException ex) {
            System.out.format("I/O error: %s%n", ex);
        }
    }

    public String getWordToGuess() {
        return wordToGuess;
    }


    public String getPlayerName() {
        return playerName.get();
    }

    public void setPlayerName(String value) {
        playerName.set(value);
    }

    public StringProperty playerNameProperty() {
        return playerName;
    }


}
