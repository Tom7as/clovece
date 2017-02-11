package com.jerabek.clovece;

/**
 * Created by Tomas on 11.02.2017.
 */

public class Field {
    int sequence;
    int x;
    int y;
    int player;
    int pieces;
    int color;

    public Field(int sequence, int x, int y, int player, int pieces, int color)  {
        this.sequence = sequence; //pořadi na desce 0-40, 
        this.x = x; //souradnice pole 3-23
        this.y = y; //souradnice pole 3-23
        this.player = player; //barva hrače - 0,1,2,3,4,
        this.pieces = pieces; //n-tá figurka - 0,1,2,3,4
        this.color = color; // 0,1,2,3,4 - r y g b
    } //poradi, zhora, zleva, bez hrace0, bez figurky 0, bez barvy 0

    Field field[] = {
        new Field(0, 1, 9, 0, 0, 0),
        new Field(1, 1, 11, 0, 0, 0),
        new Field(2, 1, 13, 0, 0, 0),
        new Field(3, 3, 13, 0, 0, 0),
        new Field(4, 5, 13, 0, 0, 0),
        new Field(5, 7, 13, 0, 0, 0),
        new Field(6, 7, 15, 0, 0, 0),
        new Field(7, 9, 15, 0, 0, 0),
        new Field(8, 9, 17, 0, 0, 0),
        new Field(9, 9, 19, 0, 0, 0),
        new Field(10, 9, 21, 0, 0, 0),
        new Field(11, 11, 21, 0, 0, 0),
        new Field(12, 13, 21, 0, 0, 0),
        new Field(13, 13, 19, 0, 0, 0),
        new Field(14, 13, 17, 0, 0, 0),
        new Field(15, 13, 15, 0, 0, 0),
        new Field(16, 15, 15, 0, 0, 0),
        new Field(17, 15, 13, 0, 0, 0),
        new Field(18, 17, 13, 0, 0, 0),
        new Field(19, 19, 13, 0, 0, 0),
        new Field(20, 21, 13, 0, 0, 0),
        new Field(21, 21, 11, 0, 0, 0),
        new Field(22, 21, 9, 0, 0, 0),
        new Field(23, 19, 9, 0, 0, 0),
        new Field(24, 17, 9, 0, 0, 0),
        new Field(25, 15, 9, 0, 0, 0),
        new Field(26, 15, 7, 0, 0, 0),
        new Field(27, 13, 7, 0, 0, 0),
        new Field(28, 13, 5, 0, 0, 0),
        new Field(29, 13, 3, 0, 0, 0),
        new Field(30, 13, 1, 0, 0, 0),
        new Field(31, 11, 1, 0, 0, 0),
        new Field(32, 9, 1, 0, 0, 0),
        new Field(33, 9, 3, 0, 0, 0),
        new Field(34, 9, 5, 0, 0, 0),
        new Field(35, 9, 7, 0, 0, 0),
        new Field(36, 7, 7, 0, 0, 0),
        new Field(37, 7, 9, 0, 0, 0),
        new Field(38, 5, 9, 0, 0, 0),
        new Field(39, 3, 9, 0, 0, 0),
    };
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
