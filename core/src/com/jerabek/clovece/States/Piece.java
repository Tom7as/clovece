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
    private int fieldNumber;
    private static final int MOVEMENT = 100;
    private Vector2 position;
    private Vector2 velocity;
    private Texture texture;
    private int player;

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

    Piece(int x, int y, int playerId, int startFieldNumber){
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
        String[] pieceTexture = new String[]{"dice.png","panakR.png", "panakY.png", "panakG.png", "panakB.png"};
        player = playerId;
        texture = new Texture(pieceTexture[playerId]);
        fieldNumber = startFieldNumber;
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
