package com.pendu.pendu.controllers;

import com.pendu.pendu.App;
import com.pendu.pendu.models.GameModel;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class MenuController {
    @FXML
    private TextField playerNameTxtField;

    @FXML
    void onPlayBtnAction() {
        GameModel gameModel = new GameModel();
        gameModel.setPlayerName(playerNameTxtField.getText());
        App.loadView("views/GameView.fxml", gameModel);
    }

    @FXML
    public void onShowScoreBtnAction() {
        App.loadView("views/ScoreView.fxml");
    }
}
