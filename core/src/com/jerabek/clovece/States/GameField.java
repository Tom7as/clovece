package com.jerabek.clovece.States;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Tomas on 11.02.2017.
 */

public class GameField {
    private int field;
    private int x;
    private int y;
    private int pieces;
    private int color;
    private int player;

    static GameField[] getData() {
        return data;
    }

    private GameField(int sequence, int x, int y, int pieces, int color, int player) {
        this.field = sequence; //pořadi na desce 0-40,
        this.x = x; //souradnice pole 3-23
        this.y = y; //souradnice pole 3-23
        this.pieces = pieces; //n-tá figurka - 0 - 15
        this.color = color; // 0,1,2,3,4 - r y g b
        this.player = player;
    } //poradi, zhora, zleva, bez hrace0, bez figurky 0, bez barvy 0

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public void setPieces(int pieces) {
        this.pieces = pieces;
    }

    public int getPieces() {
        return pieces;
    }

    public Vector2 presun(int pozice,int kostka){
        Vector2 vector2 = null;
        vector2.set(data[pozice+1].getX(),data[pozice+1].getY());
        return vector2;
    }

    int getField() {
        return field;
    }

    Vector2 getFieldCoordinates(){
        return new Vector2(x,y);
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    int getColor() {
        return color;
    }

    private static GameField[] data = {
            new GameField(0, -5, 1, -1, 4, 0),
            new GameField(1, -4, 1, -1, 0, 0),
            new GameField(2, -3, 1, -1, 0, 0),
            new GameField(3, -2, 1, -1, 0, 0),
            new GameField(4, -1, 1, -1, 0, 0),
            new GameField(5, -1, 2, -1, 0, 0),
            new GameField(6, -1, 3, -1, 0, 0),
            new GameField(7, -1, 4, -1, 0, 0),
            new GameField(8, -1, 5, -1, 0, 0),
            new GameField(9,  0, 5, -1, 0, 0),
            new GameField(10, 1, 5, -1, 3, 0),
            new GameField(11, 1, 4, -1, 0, 0),
            new GameField(12, 1, 3, -1, 0, 0),
            new GameField(13, 1, 2, -1, 0, 0),
            new GameField(14, 1, 1, -1, 0, 0),
            new GameField(15, 2, 1, -1, 0, 0),
            new GameField(16, 3, 1, -1, 0, 0),
            new GameField(17, 4, 1, -1, 0, 0),
            new GameField(18, 5, 1, -1, 0, 0),
            new GameField(19, 5, 0, -1, 0, 0),
            new GameField(20, 5, -1, -1, 1, 0),
            new GameField(21, 4, -1, -1, 0, 0),
            new GameField(22, 3, -1, -1, 0, 0),
            new GameField(23, 2, -1, -1, 0, 0),
            new GameField(24, 1, -1, -1, 0, 0),
            new GameField(25, 1, -2, -1, 0, 0),
            new GameField(26, 1, -3, -1, 0, 0),
            new GameField(27, 1, -4, -1, 0, 0),
            new GameField(28, 1, -5, -1, 0, 0),
            new GameField(29, 0, -5, -1, 0, 0),
            new GameField(30, -1, -5, -1, 2, 0),
            new GameField(31, -1, -4, -1, 0, 0),
            new GameField(32, -1, -3, -1, 0, 0),
            new GameField(33, -1, -2, -1, 0, 0),
            new GameField(34, -1, -1, -1, 0, 0),
            new GameField(35, -2, -1, -1, 0, 0),
            new GameField(36, -3, -1, -1, 0, 0),
            new GameField(37, -4, -1, -1, 0, 0),
            new GameField(38, -5, -1, -1, 0, 0),
            new GameField(39, -5,  0, -1, 0, 0),

            //hrač0 start, cil red
            new GameField(40, 5, -5, 0, 1, 0),
            new GameField(41, 4, -5, 0, 1, 0),
            new GameField(42, 5, -4, 0, 1, 0),
            new GameField(43, 4, -4, 0, 1, 0),
            new GameField(44, 4, 0, 0, 1, 0),
            new GameField(45, 3, 0, 0, 1, 0),
            new GameField(46, 2, 0, 0, 1, 0),
            new GameField(47, 1, 0, 0, 1, 0),
            //hrač1 start, cil yellow
            new GameField(48, -4, -5, 0, 2, 1),
            new GameField(49, -4, -4, 0, 2, 1),
            new GameField(50, -5, -5, 0, 2, 1),
            new GameField(51, -5, -4, 0, 2, 1),
            new GameField(52, 0, -4, 0, 2, 0),
            new GameField(53, 0, -3, 0, 2, 0),
            new GameField(54, 0, -2, 0, 2, 0),
            new GameField(55, 0, -1, 0, 2, 0),
            //hrač2 start, cil green
            new GameField(56, 4, 5, 0, 3, 2),
            new GameField(57, 4, 4, 0, 3, 2),
            new GameField(58, 5, 5, 0, 3, 2),
            new GameField(59, 5, 4, 0, 3, 2),
            new GameField(60, 0, 4, 0, 3, 0),
            new GameField(61, 0, 3, 0, 3, 0),
            new GameField(62, 0, 2, 0, 3, 0),
            new GameField(63, 0, 1, 0, 3, 0),
            //hrač3 start, cil blue
            new GameField(64, -5, 4, 0, 4, 3),
            new GameField(65, -4, 4, 0, 4, 3),
            new GameField(66, -5, 5, 0, 4, 3),
            new GameField(67, -4, 5, 0, 4, 3),
            new GameField(68, -4, 0, 0, 4, 0),
            new GameField(69, -3, 0, 0, 4, 0),
            new GameField(70, -2, 0, 0, 4, 0),
            new GameField(71, -1, 0, 0, 4, 0),
    };
}