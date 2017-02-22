package com.jerabek.clovece.States;


import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.BitmapFont;

import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import java.util.ArrayList;

import static com.badlogic.gdx.math.MathUtils.random;

/**
 * Created by Tomas on 2/11/2017.
 */
public class PlayState extends State {

    //renderovani textu
    private static final String TEXT = "textstring";
    private static final Color COLOR = Color.BLACK;
    private static final float[] SCALES = {0.25f, 0.5f, 1, 2, 4};

    private static class DistanceFieldShader extends ShaderProgram {
        public DistanceFieldShader () {
            super(Gdx.files.internal("distancefield.vert"), Gdx.files.internal("distancefield.frag"));
            if (!isCompiled()) {
                throw new RuntimeException("Shader compilation failed:\n" + getLog());
            }
        }

        /** @param smoothing a value between 0 and 1 */
        public void setSmoothing (float smoothing) {
            float delta = 0.5f * MathUtils.clamp(smoothing, 0, 1);
            setUniformf("u_lower", 0.5f - delta);
            setUniformf("u_upper", 0.5f + delta);
        }
    }

    private Texture distanceFieldTexture;
    private BitmapFont distanceFieldFont;
    private DistanceFieldShader distanceFieldShader;
    private GlyphLayout layout = new GlyphLayout();

    private Texture[] fieldImg = new Texture[5];
    private Stage stage;

    private LabelStyle labelStyle;
    private Label outputLabel;

    private int zoom = 70;
    private float fieldSize = 80, smallFieldSize = fieldSize*0.8f, pieceSize = 43;

    private GameField[] data = GameField.getData();

    private ArrayList<Integer> movablePieces = new ArrayList<Integer>();

    private int playersCount = 4, pieceCount = 4;
    private Piece[] piece = new Piece[pieceCount*playersCount];

    private int dice, currentPlayer=1, nextPlayer=2, touchedPieces;

    private Skin comicSkin = new Skin(Gdx.files.internal("skin/comic-ui.json"));
    private TextButton rollButton = new TextButton("Roll dice", comicSkin);



    public PlayState(GameStateManager gsm) {
        super(gsm);
        //cam.setToOrtho(false, appWidth / 2, CloveceNezlobSe.appHeight / 2);
        stage = new Stage(new ExtendViewport(850,850*1.33f,850,850*1.77f, cam));

        Gdx.input.setInputProcessor(stage);

        Texture dice = new Texture("dice.png");

        distanceFieldTexture = new Texture(Gdx.files.internal("verdana39distancefield.png"), false);
        distanceFieldFont = new BitmapFont(Gdx.files.internal("verdana39distancefield.fnt"), new TextureRegion(
                distanceFieldTexture), false);
        distanceFieldFont.setColor(COLOR);
        distanceFieldShader = new DistanceFieldShader();
        labelStyle = new LabelStyle(distanceFieldFont, Color.BLACK);

        fieldImg[0] = new Texture("pole.png");
        fieldImg[1] = new Texture("poleR.png");
        fieldImg[2] = new Texture("poleY.png");
        fieldImg[3] = new Texture("poleG.png");
        fieldImg[4] = new Texture("poleB.png");

        for(int a=0; a < playersCount; a++){
            for(int b=0; b < pieceCount; b++){
                piece[a*4+b] = new Piece(data[40+a*8+b].getX(),
                                         a*4+b,
                                         data[40+a*8+b].getY(),
                                         a,
                                         40+a*8+b );
                data[40+a*8+b].setPieces(a*4+b);
            }
        }

        label();
        rollButton();

    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
        }
    }

    private void throwMessage(SpriteBatch sb){
        //sb.draw(dice, cam.position.x - dice.getWidth() / 2, cam.position.y - dice.getHeight() / 2);
    }

    @Override
    public void update(float dt) {
        //refreshBoard();
        //piece[f].update(dt);
        //piece[f].getPosition().x + 80;
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        refreshBoard(sb);
        paintPieces(sb);
        //int x = 10;
        //x += drawFont(sb, distanceFieldFont, true, true, 1f, x);
        sb.end();

        stage.act();
        stage.draw();
    }

    private void round(){
        outputLabel.setText("Player " + currentPlayer + " is on turn");

        int dice = throwDice();
        getMovablePieces(currentPlayer, dice);
        if(!movablePieces.isEmpty())  {
            //markTargetFields();//možná cílová pole
            //select piece to move
            int fromField = movablePieces.get(getTouchedPieces());
            Piece tempPiece = piece[data[fromField].getPieces()];
            startMove(tempPiece, fromField);

            }

        nextPlayer();
        outputLabel.setText("Player " + currentPlayer + " is on turn");
        rollButton.setVisible(true);
        movablePieces.clear();

    }

    public void startMove(Piece tempPiece,int field){
        Vector2 vector;
        if(field >= 0 && field <= 39){ // posunout dal
            //for (int m = 0; m < dice; m++) {//posun o 1 pole ( dostat input, kliknutej piece)
                finishMove(tempPiece, field);
            //}
        }
        else { // nasadit

            finishMove(tempPiece, field);
        }
    }

    private void finishMove(Piece tempPiece, int fromField) {
        int targetPiece = data[fromField+dice].getPieces();
        if(targetPiece == 0) {

            tempPiece.setFieldNumber(dice+fromField);//funkci na validovani - podle hrace posovat o 10
            tempPiece.setPosition(data[fromField+dice].getFieldCoordinates());
            data[fromField+dice].setPieces(tempPiece.getPieceId());//nastav novej
            data[fromField].setPieces(0);//vynuluj starej
        }
        else {
            if(targetPiece == currentPlayer){ // validovat dřív že neskočí na sveho --> v getMovablePiece
            }
            else {

                kickPiece(targetPiece);

                tempPiece.setFieldNumber(currentPlayer*10);//funkci na validovani - podle hrace posovat o 10
                tempPiece.setPosition(data[currentPlayer*10].getFieldCoordinates());
                data[currentPlayer*10].setPieces(tempPiece.getPieceId());//nastav novej
                data[fromField].setPieces(0);//vynuluj starej

            }
        }
    }

    private void kickPiece(int targetField){
        int startField = piece[targetField].getStartFieldNumber();
        piece[targetField].setFieldNumber(startField);
        piece[targetField].setPosition(data[startField].getFieldCoordinates());
    }

    private ArrayList<Integer> getMovablePieces(int currentPlayer, int dice) {

        for (int i = 0; i < piece.length; i++) {
            if(piece[i].getPlayer()==currentPlayer) {// pridat podminku na dice = 1-5
                if ((piece[i].getFieldNumber() < 40))
                    movablePieces.add(piece[i].getFieldNumber());
                else {
                    if ((dice == 6)) {
                        movablePieces.add(piece[i].getFieldNumber());
                    }
                }
            }
        }
        return movablePieces;
    }

    private int getTouchedPieces() {
        touchedPieces = 1;
        return touchedPieces;
    }

    private void paintPieces(SpriteBatch sb){
        Piece[] pieceSort;
        pieceSort = piece.clone();
        //seřazení pro odstranení překryvů
        for (int i = 0; i<pieceSort.length; i++)
        {
            for (int j = 0; j<pieceSort.length; j++)
            {
                if (pieceSort[i].getPosition().y > pieceSort[j].getPosition().y)
                {
                    Piece temp = pieceSort[i];
                    pieceSort[i] = pieceSort[j];
                    pieceSort[j] = temp;
                }
            }
        }

        for(Piece pieces : pieceSort)
            sb.draw(pieces.getTexture(),
                    cam.position.x + pieces.getPosition().x * zoom - pieceSize * 0.5f,
                    cam.position.y + pieces.getPosition().y * zoom - pieceSize * 0.4f);
    }

    private void refreshBoard(SpriteBatch sb) {
        for (GameField aData : data) {
//            aData.getField();
//            aData.getColor();

            if (aData.getField() < 40) {
                sb.draw(fieldImg[aData.getColor()],
                        cam.position.x + aData.getX() * zoom - fieldSize * 0.5f,
                        cam.position.y + aData.getY() * zoom - fieldSize * 0.5f);
            } else {
                //fieldImg(data[i].getX(), data[i].getY());
                sb.draw(fieldImg[aData.getColor()],
                        cam.position.x + aData.getX() * zoom - smallFieldSize * 0.5f,
                        cam.position.y + aData.getY() * zoom - smallFieldSize * 0.5f,
                        smallFieldSize,
                        smallFieldSize);
            }
        }
    }

    private int throwDice(){
        dice = random(1,6);
        outputLabel.setText("Player " + currentPlayer + " threw " + dice);
        return dice;
    }

    private void nextPlayer(){
        currentPlayer=nextPlayer;
        nextPlayer++;
        if(nextPlayer > 4)
            nextPlayer = 1;
        //outputLabel.setText("Player" + currentPlayer +" is on turn");
    }

    public void rollButton(){
        rollButton.setSize(500,110);
        rollButton.setPosition(cam.position.x - rollButton.getWidth() / 2, cam.position.y - rollButton.getHeight() * 0.35f - 7 * zoom );
        rollButton.getLabel().setFontScale(2,2);
        rollButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                //outputLabel.setText("Player" + nextPlayer +" is on turn");
                rollButton.setVisible(false);
                round();
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(rollButton);
    }

    public void label(){
        outputLabel = new Label("Player " + currentPlayer + " is on turn",comicSkin);
        outputLabel.setSize(stage.getWidth(),80);
        outputLabel.setPosition(cam.position.x - outputLabel.getWidth() /2 , cam.position.y + 7 * zoom );
        outputLabel.setAlignment(Align.center);
        outputLabel.setFontScale(2,2);
        outputLabel.setStyle(labelStyle);

        stage.addActor(outputLabel);
    }

    private float getBaselineShift (BitmapFont font) {
        if (font == distanceFieldFont) {
            // We set -8 paddingAdvanceY in Hiero to compensate for 4 padding on each side.
            // Unfortunately the padding affects the baseline inside the font description.
            return -8;
        } else {
            return 0;
        }
    }

    private int drawFont (SpriteBatch spriteBatch,BitmapFont font, boolean linearFiltering, boolean useShader, float smoothing, int x) {
        int y = 10;
        float maxWidth = 0;

        // set filters for each page
        TextureFilter minFilter = linearFiltering ? TextureFilter.MipMapLinearNearest : TextureFilter.Nearest;
        TextureFilter magFilter = linearFiltering ? TextureFilter.Linear : TextureFilter.Nearest;
        for (int i = 0; i < font.getRegions().size; i++) {
            font.getRegion(i).getTexture().setFilter(minFilter, magFilter);
        }

        if (useShader) {
            spriteBatch.setShader(distanceFieldShader);
        } else {
            spriteBatch.setShader(null);
        }

        for (float scale : SCALES) {
            font.getData().setScale(scale);
            layout.setText(font, TEXT);
            maxWidth = Math.max(maxWidth, layout.width);
            if (useShader) {
                distanceFieldShader.setSmoothing(smoothing / scale);
            }
            font.draw(spriteBatch, layout, x, y);
            y += font.getLineHeight();
            spriteBatch.flush();
        }
        getBaselineShift(distanceFieldFont);
        return (int)Math.ceil(maxWidth);
    }

    @Override
    public void dispose() {

        for(int i = 0; i < data.length; i++)
            fieldImg[i].dispose();

        for(int i = 0; i < piece.length; i++)
            piece[i].dispose();
        distanceFieldTexture.dispose();
        distanceFieldFont.dispose();
        distanceFieldShader.dispose();
        System.out.println("Board Disposed");
    }

    //// TODO: 22.02.2017 SAVEGAME
    public void saveGame(){

    }
}
