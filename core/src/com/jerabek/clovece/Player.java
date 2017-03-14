package com.jerabek.clovece;

/**
 * Created by Tomas on 01.03.2017.
 */

public class Player {
    private String name;
    private int ai;
    private int sum;
    private int sixs;


    public Player(String name, int ai, int stats, int sixs) {
        this.name = name;
        this.ai = ai;
        this.sum = stats;
        this.sixs = sixs;
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

    public int getSixs() {
        return sixs;
    }

    public void setSixs(int sixs) {
        this.sixs = sixs;
    }

    public void addSix(){
        this.sixs += 1;
    }
}
