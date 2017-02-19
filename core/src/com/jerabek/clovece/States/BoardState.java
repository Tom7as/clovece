package com.jerabek.clovece.States;


import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.DistanceFieldFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import static com.badlogic.gdx.math.MathUtils.random;
import static com.jerabek.clovece.CloveceNezlobSe.appWidth;

/**
 * Created by Tomas on 2/11/2017.
 */
public class BoardState extends State {

    //renderovani textu
    private static final String TEXT = "Ta";
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
    private SpriteBatch spriteBatch;
    private Texture descriptionTexture;
    private Texture distanceFieldTexture;
    private BitmapFont descriptionFont;
    private BitmapFont distanceFieldFont;
    private DistanceFieldShader distanceFieldShader;
    private GlyphLayout layout = new GlyphLayout();

    private Texture[] fieldImg = new Texture[5];
    private Texture diceImg;
    private Stage stage;
    private Label outputLabel;

    private int zoom = 70;
    private float fieldSize = 80, smallFieldSize = fieldSize*0.8f, pieceSize = 43;
    private GameField[] data = GameField.getData();

    private int playersCount = 4, pieceCount = 4;
    private int fieldScan = 40,pieceArray=0;
    private Piece[] piece = new Piece[pieceCount*playersCount ];
    private int dice, currentPlayer=1;

    Skin orangeSkin = new Skin(Gdx.files.internal("skin/comic-ui.json"));
    TextButton button3 = new TextButton("Roll dice", orangeSkin);



    public BoardState(GameStateManager gsm) {
        super(gsm);
        //cam.setToOrtho(false, appWidth / 2, CloveceNezlobSe.appHeight / 2);
        stage = new Stage(new ExtendViewport(960, 1280, 960, 1600, cam));
        Gdx.input.setInputProcessor(stage);

        diceImg = new Texture("dice.png");
        fieldImg[0] = new Texture("pole.png");
        fieldImg[1] = new Texture("poleR.png");
        fieldImg[2] = new Texture("poleY.png");
        fieldImg[3] = new Texture("poleG.png");
        fieldImg[4] = new Texture("poleB.png");

        //for(int i = 0; i <= playersCount * pieceCount; i++) pouziti pro data.setPlayer a nasadit panacky do pole dat
        // obsahuje herni figurky prop všechny hráče
        for (fieldScan = 40; fieldScan < data.length; fieldScan++) {
            if (data[fieldScan].getPlayer() > 0) {
                piece[pieceArray] = new Piece(data[fieldScan].getX(),
                                     data[fieldScan].getY(),
                                     data[fieldScan].getPlayer(),
                                     data[fieldScan].getSequence());
                pieceArray++;
            }
        }
        // obsahuje herni kostku
        //piece[0] = new Piece(0, 0, 0, 100);

        diceButton();

        label();


        distanceFieldTexture = new Texture(Gdx.files.internal("verdana39distancefield.png"), true);
        distanceFieldFont = new BitmapFont(Gdx.files.internal("verdana39distancefield.fnt"), new TextureRegion(
                distanceFieldTexture), true);
        distanceFieldFont.setColor(COLOR);
        distanceFieldShader = new DistanceFieldShader();

    }



    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
//            button3.setVisible(false);
//            piece[0].move();
//            piece[0].

//            dice = throwDice();
//            while(dice > 0){
//
//            }

            nextPlayer();
            //gsm.set(new PlayState(gsm));
        }
    }

    private void throwMessage(SpriteBatch sb){

        sb.draw(diceImg, cam.position.x - diceImg.getWidth() / 2, cam.position.y - diceImg.getHeight() / 2);

    }

    @Override
    public void update(float dt) {

        //refreshBoard();
//        piece[f].update(dt);
        //piece[f].getPosition().x + 80;

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();

        refreshBoard(sb);
        drawPieces(sb);
        int x = 10;
        x += drawFont(sb, distanceFieldFont, true, true, 1f, x);
        handleInput();
        sb.end();



        stage.act();
        stage.draw();
    }

    private void drawPieces(SpriteBatch sb){
        for(Piece pieces : piece)
            sb.draw(pieces.getTexture(),
                    cam.position.x + pieces.getPosition().x * zoom - pieceSize * 0.5f,
                    cam.position.y + pieces.getPosition().y * zoom - pieceSize * 0.4f);
    }

    private void refreshBoard(SpriteBatch sb) {
        for (GameField aData : data) {
//            aData.getSequence();
//            aData.getColor();

            if (aData.getSequence() < 40) {
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
        return dice;
    }

    private int nextPlayer(){
        currentPlayer++;
        if(currentPlayer > 4)
            return currentPlayer = 1;
        else return currentPlayer;
    }

    public void diceButton(){
        button3.setSize(diceImg.getWidth()*2,diceImg.getHeight()*0.7f);
        button3.setPosition(cam.position.x - diceImg.getWidth() , cam.position.y - diceImg.getHeight() *0.35f);
        button3.getLabel().setFontScale(2,2);
        button3.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                outputLabel.setText("Press a Button");
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                outputLabel.setText("Pressed Image Button");
                return true;
            }
        });
        stage.addActor(button3);
    }
    public void label(){
        outputLabel = new Label("Press a Button",orangeSkin);
        outputLabel.setSize(Gdx.graphics.getWidth(),50);
        outputLabel.setPosition(0,20);
        outputLabel.setAlignment(Align.center);
        outputLabel.setFontScale(6,6);

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
}
