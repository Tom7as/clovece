package com.jerabek.clovece;

/**
 * Created by Tomas on 15.03.2017.
 */

public class SettingData {
    String [] playerName;
    int [] playerType;
    int [] otherSettings;

    public SettingData(String[] playerName, int[] playerType, int[] otherSettings) {
        this.playerName = playerName;
        this.playerType = playerType;
        this.otherSettings = otherSettings;
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

    public int[] getOtherSettings() {
        return otherSettings;
    }

    public void setOtherSettings(int[] otherSettings) {
        this.otherSettings = otherSettings;
    }
}
