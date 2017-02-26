package com.jerabek.clovece.States;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;


/**
 * Created by Tomas on 17.02.2017.
 */

public class Piece {
    private int fieldNumber, startFieldNumber;
    private float x, y;
    private Texture texture;
    private int player, pieceId;

    public int getPieceId() {
        return pieceId;
    }

    public void setPieceId(int pieceId) {
        this.pieceId = pieceId;
    }


    Piece(int x, int y, int id, int playerId, int FieldNumber){
        this.x = x;
        this.y = y;
        this.pieceId = id;
        this.player = playerId;
        String[] pieceTexture = new String[]{"panakB.png", "panakY.png","panakR.png", "panakG.png"};
        this.texture = new Texture(pieceTexture[playerId]);
        this.fieldNumber = FieldNumber;
        this.startFieldNumber = FieldNumber;
    }

    int getStartFieldNumber() {
        return startFieldNumber;
    }

    int getFieldNumber() {
        return fieldNumber;
    }

    void setFieldNumber(int fieldNumber) {
        this.fieldNumber = fieldNumber;
    }

    Texture getTexture() {
        return texture;
    }


    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void movePiece(float x, float y){
        this.x -= x;
        this.y -= y;
    }

    void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    int getPlayer() {
        return player;
    }

    void dispose(){
        texture.dispose();

    }
}
