package com.jerabek.clovece.States;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Tomas on 11.02.2017.
 */

public class GameField {
    private int sequence;
    private int x;
    private int y;
    private int player;
    private int pieces;
    private int color;

    static GameField[] getData() {
        return data;
    }

    private GameField(int sequence, int x, int y, int player, int pieces, int color) {
        this.sequence = sequence; //pořadi na desce 0-40,
        this.x = x; //souradnice pole 3-23
        this.y = y; //souradnice pole 3-23
        this.player = player; //barva hrače - 0,1,2,3,4,
        this.pieces = pieces; //n-tá figurka - 0,1,2,3,4
        this.color = color; // 0,1,2,3,4 - r y g b
    } //poradi, zhora, zleva, bez hrace0, bez figurky 0, bez barvy 0

    public Vector2 presun(int pozice,int kostka){
        Vector2 vector2 = null;
        vector2.set(data[pozice+1].getX(),data[pozice+1].getY());
        return vector2;
    }

    int getSequence() {
        return sequence;
    }

    public Vector2 getFieldCoordinates(){
        Vector2 souradnice = null;
        souradnice.set(getX(), getY());
        return souradnice;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    int getPlayer() {
        return player;
    }

    void setPlayer(int player) {
        this.player = player;
    }

    int getPieces() {
        return pieces;
    }

    void setPieces(int pieces) {
        this.pieces = pieces;
    }

    int getColor() {
        return color;
    }

    void setColor(int color) {
        this.color = color;
    }

    private static GameField[] data = {
            new GameField(0, -5, 1, 0, 0, 4),
            new GameField(1, -4, 1, 0, 0, 0),
            new GameField(2, -3, 1, 0, 0, 0),
            new GameField(3, -2, 1, 0, 0, 0),
            new GameField(4, -1, 1, 0, 0, 0),
            new GameField(5, -1, 2, 0, 0, 0),
            new GameField(6, -1, 3, 0, 0, 0),
            new GameField(7, -1, 4, 0, 0, 0),
            new GameField(8, -1, 5, 0, 0, 0),
            new GameField(9, 0, 5, 0, 0, 0),
            new GameField(10, 1, 5, 0, 0, 3),
            new GameField(11, 1, 4, 0, 0, 0),
            new GameField(12, 1, 3, 0, 0, 0),
            new GameField(13, 1, 2, 0, 0, 0),
            new GameField(14, 1, 1, 0, 0, 0),
            new GameField(15, 2, 1, 0, 0, 0),
            new GameField(16, 3, 1, 0, 0, 0),
            new GameField(17, 4, 1, 0, 0, 0),
            new GameField(18, 5, 1, 0, 0, 0),
            new GameField(19, 5, 0, 0, 0, 0),
            new GameField(20, 5, -1, 0, 0, 1),
            new GameField(21, 4, -1, 0, 0, 0),
            new GameField(22, 3, -1, 0, 0, 0),
            new GameField(23, 2, -1, 0, 0, 0),
            new GameField(24, 1, -1, 0, 0, 0),
            new GameField(25, 1, -2, 0, 0, 0),
            new GameField(26, 1, -3, 0, 0, 0),
            new GameField(27, 1, -4, 0, 0, 0),
            new GameField(28, 1, -5, 0, 0, 0),
            new GameField(29, 0, -5, 0, 0, 0),
            new GameField(30, -1, -5, 0, 0, 2),
            new GameField(31, -1, -4, 0, 0, 0),
            new GameField(32, -1, -3, 0, 0, 0),
            new GameField(33, -1, -2, 0, 0, 0),
            new GameField(34, -1, -1, 0, 0, 0),
            new GameField(35, -2, -1, 0, 0, 0),
            new GameField(36, -3, -1, 0, 0, 0),
            new GameField(37, -4, -1, 0, 0, 0),
            new GameField(38, -5, -1, 0, 0, 0),
            new GameField(39, -5, 0, 0, 0, 0),



    //hrač1 start, cil red1
            new GameField(40, 5, -5, 1, 1, 1),
            new GameField(41, 4, -5, 1, 2, 1),
            new GameField(42, 5, -4, 1, 3, 1),
            new GameField(43, 4, -4, 1, 4, 1),
            new GameField(44, 4, 0, 0, 0, 1),
            new GameField(45, 3, 0, 0, 0, 1),
            new GameField(46, 2, 0, 0, 0, 1),
            new GameField(47, 1, 0, 0, 0, 1),
            //hrač2 start, cil yellow2
            new GameField(50, -4, -5, 2, 1, 2),
            new GameField(51, -4, -4, 2, 2, 2),
            new GameField(52, -5, -5, 2, 3, 2),
            new GameField(53, -5, -4, 2, 4, 2),
            new GameField(54, 0, -4, 0, 0, 2),
            new GameField(55, 0, -3, 0, 0, 2),
            new GameField(56, 0, -2, 0, 0, 2),
            new GameField(57, 0, -1, 0, 0, 2),
            //hrač3 start, cil green3
            new GameField(60, 4, 5, 3, 1, 3),
            new GameField(61, 4, 4, 3, 2, 3),
            new GameField(62, 5, 5, 3, 3, 3),
            new GameField(63, 5, 4, 3, 4, 3),
            new GameField(64, 0, 4, 0, 0, 3),
            new GameField(65, 0, 3, 0, 0, 3),
            new GameField(66, 0, 2, 0, 0, 3),
            new GameField(67, 0, 1, 0, 0, 3),
            //hrač4 start, cil blue4
            new GameField(70, -5, 4, 4, 1, 4),
            new GameField(71, -4, 4, 4, 2, 4),
            new GameField(72, -5, 5, 4, 3, 4),
            new GameField(73, -4, 5, 4, 4, 4),
            new GameField(74, -4, 0, 0, 0, 4),
            new GameField(75, -3, 0, 0, 0, 4),
            new GameField(76, -2, 0, 0, 0, 4),
            new GameField(77, -1, 0, 0, 0, 4),
    };
}