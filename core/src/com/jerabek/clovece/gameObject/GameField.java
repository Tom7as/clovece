package com.jerabek.clovece.gameObject;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Tomas on 11.02.2017.
 */

public class GameField {
    private int field;
    private int x;
    private int y;
    private int pieceID;
    private int color;

    public static GameField[] getPlayBoard() {
        return playBoard;
    }

    GameField(int sequence, int x, int y, int pieceID, int color) {
        this.field = sequence; //pořadi na desce 0-39,40-48...
        this.x = x; //souradnice pole -5 , 5
        this.y = y; //souradnice pole -5 , 5
        this.pieceID = pieceID; //n-tá figurka -> -1,0 - 15
        this.color = color; // 0,1,2,3,4 - r y g b
    } //poradi, zhora, zleva, bez figurky -1, bez barvy 0, bez hrace -1

    public void setPieceID(int pieceID) {
        this.pieceID = pieceID;
    }

    public int getPieceID() {
        return pieceID;
    }

    public Vector2 presun(int pozice,int kostka){
        Vector2 vector2 = null;
        vector2.set(playBoard[pozice+1].getX(), playBoard[pozice+1].getY());
        return vector2;
    }

    public int getField() {
        return field;
    }

    public Vector2 getFieldCoordinates(){
        return new Vector2(x,y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getColor() {
        return color;
    }

    private static GameField[] playBoard = {
            new GameField(0, -1, -5, -1, 4),
            new GameField(1, -1, -4, -1, 0),
            new GameField(2, -1, -3, -1, 0),
            new GameField(3, -1, -2, -1, 0),
            new GameField(4, -1, -1, -1, 0),
            new GameField(5, -2, -1, -1, 0),
            new GameField(6, -3, -1, -1, 0),
            new GameField(7, -4, -1, -1, 0),
            new GameField(8, -5, -1, -1, 0),
            new GameField(9, -5,  0, -1, 0),
            new GameField(10, -5, 1, -1, 1),
            new GameField(11, -4, 1, -1, 0),
            new GameField(12, -3, 1, -1, 0),
            new GameField(13, -2, 1, -1, 0),
            new GameField(14, -1, 1, -1, 0),
            new GameField(15, -1, 2, -1, 0),
            new GameField(16, -1, 3, -1, 0),
            new GameField(17, -1, 4, -1, 0),
            new GameField(18, -1, 5, -1, 0),
            new GameField(19,  0, 5, -1, 0),
            new GameField(20, 1, 5, -1, 2),
            new GameField(21, 1, 4, -1, 0),
            new GameField(22, 1, 3, -1, 0),
            new GameField(23, 1, 2, -1, 0),
            new GameField(24, 1, 1, -1, 0),
            new GameField(25, 2, 1, -1, 0),
            new GameField(26, 3, 1, -1, 0),
            new GameField(27, 4, 1, -1, 0),
            new GameField(28, 5, 1, -1, 0),
            new GameField(29, 5, 0, -1, 0),
            new GameField(30, 5, -1, -1, 3),
            new GameField(31, 4, -1, -1, 0),
            new GameField(32, 3, -1, -1, 0),
            new GameField(33, 2, -1, -1, 0),
            new GameField(34, 1, -1, -1, 0),
            new GameField(35, 1, -2, -1, 0),
            new GameField(36, 1, -3, -1, 0),
            new GameField(37, 1, -4, -1, 0),
            new GameField(38, 1, -5, -1, 0),
            new GameField(39, 0, -5, -1, 0),
            //hrač0 start, cil blue
            new GameField(40, -5, 5, -1, 1),
            new GameField(41, -5, 4, -1, 1),
            new GameField(42, -4, 5, -1, 1),
            new GameField(43, -4, 4, -1, 1),
            new GameField(44, -4, 0, -1, 1),
            new GameField(45, -3, 0, -1, 1),
            new GameField(46, -2, 0, -1, 1),
            new GameField(47, -1, 0, -1, 1),
            //hrač1 start, cil yellow
            new GameField(48, 4, 5, -1, 2),
            new GameField(49, 4, 4, -1, 2),
            new GameField(50, 5, 5, -1, 2),
            new GameField(51, 5, 4, -1, 2),
            new GameField(52, 0, 4, -1, 2),
            new GameField(53, 0, 3, -1, 2),
            new GameField(54, 0, 2, -1, 2),
            new GameField(55, 0, 1, -1, 2),
            //hrač2 start, cil red
            new GameField(56, 4, -5, -1, 3),
            new GameField(57, 4, -4, -1, 3),
            new GameField(58, 5, -5, -1, 3),
            new GameField(59, 5, -4, -1, 3),
            new GameField(60, 4, 0, -1, 3),
            new GameField(61, 3, 0, -1, 3),
            new GameField(62, 2, 0, -1, 3),
            new GameField(63, 1, 0, -1, 3),
            //hrač3 start, cil green
            new GameField(64, -5, -4, -1, 4),
            new GameField(65, -4, -4, -1, 4),
            new GameField(66, -5, -5, -1, 4),
            new GameField(67, -4, -5, -1, 4),
            new GameField(68, 0, -4, -1, 4),
            new GameField(69, 0, -3, -1, 4),
            new GameField(70, 0, -2, -1, 4),
            new GameField(71, 0, -1, -1, 4),
    };
}