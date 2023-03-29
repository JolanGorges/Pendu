package com.pendu.pendu.controllers;

import com.pendu.pendu.App;
import com.pendu.pendu.models.JsonScoreDAO;
import com.pendu.pendu.models.Score;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ScoreController {

    @FXML
    private TableView<Score> scoreTbl;

    @FXML
    private TableColumn<Score, String> playerNameColumn;

    @FXML
    private TableColumn<Score, Integer> scoreColumn;

    @FXML
    private TableColumn<Score, String> durationColumn;

    @FXML
    private TableColumn<Score, Integer> moveCountColumn;

    @FXML
    private TableColumn<Score, Integer> errorCountColumn;

    private final ObservableList<Score> scores = FXCollections.observableArrayList();

    public ScoreController() {
        JsonScoreDAO jsonScoreDAO = new JsonScoreDAO();
        scores.addAll(jsonScoreDAO.getAllScores());
    }
    public void initialize() {
        if (scores.isEmpty()) {
            return;
        }
        playerNameColumn.setCellValueFactory(new PropertyValueFactory<>("playerName"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));
        moveCountColumn.setCellValueFactory(new PropertyValueFactory<>("moveCount"));
        errorCountColumn.setCellValueFactory(new PropertyValueFactory<>("errorCount"));
        scoreTbl.setItems(scores);
        scoreTbl.getSortOrder().add(scoreColumn);
        scoreColumn.setSortType(TableColumn.SortType.DESCENDING);
        scoreTbl.sort();
    }

    public void onBackToMenuBtnAction() {
        App.loadView("views/MenuView.fxml");
    }
}
