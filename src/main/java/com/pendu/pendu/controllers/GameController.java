package com.pendu.pendu.controllers;

import com.pendu.pendu.App;
import com.pendu.pendu.models.GameModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

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
    private Label hiddenWordLbl;
    @FXML
    private Label durationLbl;
    @FXML
    private HBox scoreHBox;
    @FXML
    private Label scoreLbl;
    @FXML
    private FlowPane chooseLetterFlow;
    @FXML
    private Button tryWordHBox;
    @FXML
    private Label wordToGuessLbl;

    public GameController(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    public void initialize() {
        movesLbl.textProperty().bind(gameModel.moveCountProperty().asString());
        errorsLbl.textProperty().bind(gameModel.errorCountProperty().asString());
        hiddenWordLbl.textProperty().bind(gameModel.hiddenWordProperty());
        hangmanView.imageProperty().bind(gameModel.hangmanImageProperty());
        wordsListView.itemsProperty().bind(gameModel.wordsProperty());
        scoreHBox.visibleProperty().bind(gameModel.gameOverProperty());
        chooseLetterFlow.disableProperty().bind(gameModel.gameOverProperty());
        tryWordHBox.disableProperty().bind(gameModel.gameOverProperty());
        wordToGuessLbl.setText("Mot : " + gameModel.getWordToGuess());
        scoreLbl.textProperty().bind(gameModel.scoreProperty());
        durationLbl.textProperty().bind(gameModel.durationProperty());
    }

    @FXML
    void onTestBtnAction() {
        gameModel.checkWord(wordTextField.getText());
    }

    @FXML
    public void onLetterBtnAction(ActionEvent actionEvent) {
        gameModel.checkLetter(((Button) actionEvent.getSource()).getText());
    }

    @FXML
    void onBackToMenuBtnAction() {
        App.loadView("views/MenuView.fxml");
    }

    @FXML
    void onRestartBtnAction() {
        App.loadView("views/GameView.fxml", new GameModel());
    }
}

