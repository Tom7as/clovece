package com.jerabek.clovece.States;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.badlogic.gdx.utils.JsonValue.ValueType.array;

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

//    public GameField[] getField() {
//        return field;
//    }
//
//    public void setField(GameField[] field) {
//        this.field = field;
//    }

    public GameField(int sequence, int x, int y, int player, int pieces, int color) {
        this.sequence = sequence; //pořadi na desce 0-40, 
        this.x = x; //souradnice pole 3-23
        this.y = y; //souradnice pole 3-23
        this.player = player; //barva hrače - 0,1,2,3,4,
        this.pieces = pieces; //n-tá figurka - 0,1,2,3,4
        this.color = color; // 0,1,2,3,4 - r y g b
    } //poradi, zhora, zleva, bez hrace0, bez figurky 0, bez barvy 0

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public int getPieces() {
        return pieces;
    }

    public void setPieces(int pieces) {
        this.pieces = pieces;
    }

//    private GameField[] field = {
//            new GameField(0, -5, -1, 0, 0, 4),
//            new GameField(1, -5, 0, 0, 0, 0),
//            new GameField(2, -5, 1, 0, 0, 0),
//            new GameField(3, -4, 1, 0, 0, 0),
//            new GameField(4, -3, 1, 0, 0, 0),
//            new GameField(5, -2, 1, 0, 0, 0),
//            new GameField(6, -1, 1, 0, 0, 0),
//            new GameField(7, -1, 2, 0, 0, 0),
//            new GameField(8, -1, 3, 0, 0, 0),
//            new GameField(9, -1, 4, 0, 0, 0),
//            new GameField(10, -1, 5, 0, 0, 2),
//            new GameField(11, 0, 5, 0, 0, 0),
//            new GameField(12, 1, 5, 0, 0, 0),
//            new GameField(13, 1, 4, 0, 0, 0),
//            new GameField(14, 1, 3, 0, 0, 0),
//            new GameField(15, 1, 2, 0, 0, 0),
//            new GameField(16, 1, 1, 0, 0, 0),
//            new GameField(17, 2, 1, 0, 0, 0),
//            new GameField(18, 3, 1, 0, 0, 0),
//            new GameField(19, 4, 1, 0, 0, 0),
//            new GameField(20, 5, 1, 0, 0, 1),
//            new GameField(21, 5, 0, 0, 0, 0),
//            new GameField(22, 5, -1, 0, 0, 0),
//            new GameField(23, 4, -1, 0, 0, 0),
//            new GameField(24, 3, -1, 0, 0, 0),
//            new GameField(25, 2, -1, 0, 0, 0),
//            new GameField(26, 1, -1, 0, 0, 0),
//            new GameField(27, 1, -2, 0, 0, 0),
//            new GameField(28, 1, -3, 0, 0, 0),
//            new GameField(29, 1, -4, 0, 0, 0),
//            new GameField(30, 1, -5, 0, 0, 3),
//            new GameField(31, 0, -5, 0, 0, 0),
//            new GameField(32, -1, -5, 0, 0, 0),
//            new GameField(33, -1, -4, 0, 0, 0),
//            new GameField(34, -1, -3, 0, 0, 0),
//            new GameField(35, -1, -2, 0, 0, 0),
//            new GameField(36, -1, -1, 0, 0, 0),
//            new GameField(37, -2, -1, 0, 0, 0),
//            new GameField(38, -3, -1, 0, 0, 0),
//            new GameField(39, -4, -1, 0, 0, 0),
//            //hrač1 start, cil red1
//            new GameField(40, 5, 5, 1, 1, 1),
//            new GameField(41, 4, 5, 1, 2, 1),
//            new GameField(42, 5, 4, 1, 3, 1),
//            new GameField(43, 4, 4, 1, 4, 1),
//            new GameField(44, 4, 0, 0, 0, 1),
//            new GameField(45, 3, 0, 0, 0, 1),
//            new GameField(46, 2, 0, 0, 0, 1),
//            new GameField(47, 1, 0, 0, 0, 1),
//            //hrač2 start, cil yellow2
//            new GameField(50, -4, 5, 2, 1, 2),
//            new GameField(51, -4, 4, 2, 2, 2),
//            new GameField(52, -5, 5, 2, 3, 2),
//            new GameField(53, -5, 4, 2, 4, 2),
//            new GameField(54, 0, 4, 0, 0, 2),
//            new GameField(55, 0, 3, 0, 0, 2),
//            new GameField(56, 0, 2, 0, 0, 2),
//            new GameField(57, 0, 1, 0, 0, 2),
//            //hrač3 start, cil green3
//            new GameField(60, 4, -5, 3, 1, 3),
//            new GameField(61, 4, -4, 3, 2, 3),
//            new GameField(62, 5, -5, 3, 3, 3),
//            new GameField(63, 5, -4, 3, 4, 3),
//            new GameField(64, 0, -4, 0, 0, 3),
//            new GameField(65, 0, -3, 0, 0, 3),
//            new GameField(66, 0, -2, 0, 0, 3),
//            new GameField(67, 0, -1, 0, 0, 3),
//            //hrač4 start, cil blue4
//            new GameField(70, -5, -4, 4, 1, 4),
//            new GameField(71, -4, -4, 4, 2, 4),
//            new GameField(72, -5, -5, 4, 3, 4),
//            new GameField(73, -4, -5, 4, 4, 4),
//            new GameField(74, -4, 0, 0, 0, 4),
//            new GameField(75, -3, 0, 0, 0, 4),
//            new GameField(76, -2, 0, 0, 0, 4),
//            new GameField(77, -1, 0, 0, 0, 4),
//    };
}
