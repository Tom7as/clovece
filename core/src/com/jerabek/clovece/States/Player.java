package com.jerabek.clovece.States;

/**
 * Created by Tomas on 01.03.2017.
 */

public class Player {
    private String name;
    private int ai;
    private int sum;
    private int round;


    public Player(String name, int ai, int stats, int round) {
        this.name = name;
        this.ai = ai;
        this.sum = stats;
        this.round = round;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAi() {
        return ai;
    }

    public void setAi(int ai) {
        this.ai = ai;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }
}
