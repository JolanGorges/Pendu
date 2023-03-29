package com.pendu.pendu.models;

public class Score {
    private String playerName;
    private Integer score;
    private String duration;

    public Score(String playerName, Integer score, String duration, Integer moveCount, Integer errorCount) {
        this.playerName = playerName;
        this.score = score;
        this.duration = duration;
        this.moveCount = moveCount;
        this.errorCount = errorCount;
    }

    public Score() {
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Integer getMoveCount() {
        return moveCount;
    }

    public void setMoveCount(Integer moveCount) {
        this.moveCount = moveCount;
    }

    public Integer getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(Integer errorCount) {
        this.errorCount = errorCount;
    }

    private Integer moveCount;
    private Integer errorCount;
}
