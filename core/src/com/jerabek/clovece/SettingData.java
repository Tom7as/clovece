package com.jerabek.clovece;

import com.badlogic.gdx.utils.BooleanArray;

/**
 * Created by Tomas on 15.03.2017.
 */

public class SettingData {
    String [] playerName;
    int [] playerType;
    Boolean [] otherSettings;
    int pieceCount;


    public SettingData(String[] playerName, int[] playerType, Boolean[] otherSettings, int pieceCount) {
        this.playerName = playerName;
        this.playerType = playerType;
        this.otherSettings = otherSettings;
        this.pieceCount = pieceCount;
    }

    public int getPieceCount() {
        return pieceCount;
    }

    public void setPieceCount(int pieceCount) {
        this.pieceCount = pieceCount;
    }

    public String[] getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String[] playerName) {
        this.playerName = playerName;
    }

    public int[] getPlayerType() {
        return playerType;
    }

    public void setPlayerType(int[] playerType) {
        this.playerType = playerType;
    }

    public Boolean[] getOtherSettings() {
        return otherSettings;
    }

    public void setOtherSettings(Boolean[] otherSettings) {
        this.otherSettings = otherSettings;
    }
}
