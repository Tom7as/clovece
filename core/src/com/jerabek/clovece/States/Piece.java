package com.jerabek.clovece.States;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import sun.java2d.loops.DrawPath;

/**
 * Created by Tomas on 17.02.2017.
 */

public class Piece {
    private int fieldNumber;
    private static final int MOVEMENT = 100;
    private Vector3 position;
    private Vector3 velocity;
    private Texture texture;

    public int getFieldNumber() {
        return fieldNumber;
    }

    public void setFieldNumber(int fieldNumber) {
        this.fieldNumber = fieldNumber;
    }

    public Texture getTexture() {
        return texture;
    }

    public Vector3 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector3 velocity) {
        this.velocity = velocity;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }

    Piece(int x, int y, int player, int startFieldNumber){
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);
        String[] pieceTexture = new String[]{"dice.png","panakR.png", "panakY.png", "panakG.png", "panakB.png"};
        texture = new Texture(pieceTexture[player]);
        fieldNumber = startFieldNumber;
    }

    public Vector3 getPosition() {
        return position;
    }

    public void move(){//SpriteBatch sb, int position, Texture texture
        this.position.x = 0;
        this.position.y = 0;
    }

    void update(float dt){

    }
    public void dispose(){
        texture.dispose();

    }
}
