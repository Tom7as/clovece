package com.jerabek.clovece.States;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import sun.java2d.loops.DrawPath;

/**
 * Created by Tomas on 17.02.2017.
 */

public class Piece {
    private int fieldNumber, startFieldNumber;
    private Vector2 position;
    private Vector2 velocity;
    private Texture texture;
    private int player, pieceId;

    public int getPieceId() {
        return pieceId;
    }

    public void setPieceId(int pieceId) {
        this.pieceId = pieceId;
    }

    Piece(int x, int id, int y, int playerId, int FieldNumber){
        position = new Vector2(x, y);
        pieceId = id;
        velocity = new Vector2(0, 0);
        player = playerId;
        String[] pieceTexture = new String[]{"panakR.png", "panakY.png", "panakG.png", "panakB.png"};
        texture = new Texture(pieceTexture[playerId]);
        fieldNumber = FieldNumber;
        startFieldNumber = FieldNumber;
    }

    public int getStartFieldNumber() {
        return startFieldNumber;
    }

    public int getFieldNumber() {
        return fieldNumber;
    }

    public void setFieldNumber(int fieldNumber) {
        this.fieldNumber = fieldNumber;
    }

    public Texture getTexture() {
        return texture;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }
    public void setPosition(int x, int y) {
        this.position.set(x,y);
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public void movePiece(int x, int y){//SpriteBatch sb, int position, Texture texture
        this.position.x = x;
        this.position.y = y;
    }

    void update(float dt){
    }

    public void dispose(){
        texture.dispose();

    }
}
