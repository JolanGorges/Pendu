package com.pendu.pendu.models;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonScoreDAO {
    private static final String SCORES_FILE = "scores.json";
    private final ObjectMapper objectMapper;

    public JsonScoreDAO() {
        objectMapper = new ObjectMapper();
    }

    public List<Score> getAllScores() {
        List<Score> scores;
        try {
            scores = objectMapper.readValue(new File(SCORES_FILE), new TypeReference<>(){});
        } catch (IOException e) {
            System.err.println("Erreur de lecture du fichier des scores : " + e.getMessage());
            scores = new ArrayList<>();
        }
        return scores;
    }

    public void saveScore(Score score) {
        List<Score> scores = getAllScores();
        scores.add(score);
        try {
            objectMapper.writeValue(new File(SCORES_FILE), scores);
        } catch (IOException e) {
            System.err.println("Erreur d'écriture du fichiers des scores: " + e.getMessage());
        }
    }
}

