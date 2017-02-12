package com.jerabek.clovece;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tomas on 11.02.2017.
 */

public class Field {
    private int sequence;
    private int x;
    private int y;
    private int player;
    private int pieces;
    private int color;

    //ArrayList<Field> fields = new ArrayList<Field>();

    public Field(int sequence, int x, int y, int player, int pieces, int color)  {
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


/*    Field players[] = {

        //hrač1 start, cil
        new Field(0, , , 1, 1, 1),
        new Field(0, , , 1, 2, 1),
        new Field(0, , , 1, 3, 1),
        new Field(0, , , 1, 4, 1),
        new Field(40, , , 0, 0, 1),
        new Field(41, , , 0, 0, 1),
        new Field(42, , , 0, 0, 1),
        new Field(43, , , 0, 0, 1),
        //hrač2 start, cil
        new Field(0, , , 2, 1, 2),
        new Field(0, , , 2, 2, 2),
        new Field(0, , , 2, 3, 2),
        new Field(0, , , 2, 4, 2),
        new Field(40, , , 0, 0, 2),
        new Field(41, , , 0, 0, 2),
        new Field(42, , , 0, 0, 2),
        new Field(43, , , 0, 0, 2),
        //hrač3 start, cil
        new Field(0, , , 3, 1, 3),
        new Field(0, , , 3, 2, 3),
        new Field(0, , , 3, 3, 3),
        new Field(0, , , 3, 4, 3),
        new Field(40, , , 0, 0, 3),
        new Field(41, , , 0, 0, 3),
        new Field(42, , , 0, 0, 3),
        new Field(43, , , 0, 0, 3),
        //hrač4 start, cil
        new Field(0, , , 4, 1, 4),
        new Field(0, , , 4, 2, 4),
        new Field(0, , , 4, 3, 4),
        new Field(0, , , 4, 4, 4),
        new Field(40, , , 0, 0, 4),
        new Field(41, , , 0, 0, 4),
        new Field(42, , , 0, 0, 4),
        new Field(43, , , 0, 0, 4),
    };
    */
}
