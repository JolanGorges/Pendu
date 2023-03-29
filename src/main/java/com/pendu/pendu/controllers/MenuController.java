package com.pendu.pendu.controllers;

import com.pendu.pendu.App;
import com.pendu.pendu.models.GameModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class MenuController {

    @FXML
    private TextField playerNameTxtField;

    @FXML
    void onPlayBtnAction(ActionEvent event) {
        GameModel gameModel = new GameModel();
        gameModel.setPlayerName(playerNameTxtField.getText());
        App.loadView("views/GameView.fxml", gameModel);
    }
}
