package com.pendu.pendu.controllers;

import com.pendu.pendu.models.GameModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class GameController {

    private final GameModel gameModel;

    @FXML
    private Label errorsLbl;
    @FXML
    private ImageView hangmanView;
    @FXML
    private Label movesLbl;
    @FXML
    private TextField wordTextField;
    @FXML
    private ListView<String> wordsListView;

    @FXML
    private Label wordToGuessLbl;


    public GameController(GameModel gameModel) {
        this.gameModel = gameModel;

    }

    public void initialize() {
        wordToGuessLbl.setText("_".repeat( gameModel.getWordToGuess().length()));
    }

    @FXML
    void onTestBtnAction(ActionEvent event) {
        String letter = wordTextField.getText();
        if (gameModel.getWordToGuess().contains(letter)) {
            StringBuilder newWordToGuess = new StringBuilder();
            for (int i = 0; i < gameModel.getWordToGuess().length(); i++) {
                if (gameModel.getWordToGuess().charAt(i) == letter.charAt(0)) {
                    newWordToGuess.append(letter);
                } else {
                    newWordToGuess.append(wordToGuessLbl.getText().charAt(i));
                }
            }
            wordToGuessLbl.setText(newWordToGuess.toString());
        } else {
            errorsLbl.setText(errorsLbl.getText() + letter);
        }
    }
}
