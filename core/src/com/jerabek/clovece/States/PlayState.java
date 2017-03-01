package com.jerabek.clovece.States;


import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.BitmapFont;

import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
import com.jerabek.clovece.CloveceNezlobSe;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static com.badlogic.gdx.math.MathUtils.random;
import static com.badlogic.gdx.math.MathUtils.round;
import static com.jerabek.clovece.CloveceNezlobSe.*;

/**
 * Created by Tomas on 2/11/2017.
 */
public class PlayState extends State {

    //renderovani textu
    private static final String TEXT = "textstring";
    private static final Color COLOR = Color.BLACK;

    private int error;

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

    private int zoom = 90;
    private float fieldSize = 94, smallFieldSize = fieldSize*0.85f, pieceSize = 61;

    private GameField[] data = GameField.getData();

    private ArrayList<Integer> movablePieces = new ArrayList<Integer>();

    private int playersCount = 4, pieceCount = 4;
    private Piece[] piece = new Piece[pieceCount*playersCount];
    private Player[] player = new Player[playersCount];
    private Label[][] statsLabels = new Label[playersCount][2];

    DecimalFormat df2 = new DecimalFormat("#.##");

    private int currentPlayer=0, nextPlayer=1, touchedPieces, framePerThrow =0, fromField, targetField;


    private Skin comicSkin = new Skin(Gdx.files.internal("skin/comic-ui.json"));
    private TextButton rollButton = new TextButton("Roll dice", comicSkin);
    private enum playerType {NONE, HUMAN, AI_DUMB, AI_SAFE, AI_EVIL};
    private int [] home = new int[playersCount];
    private boolean rollAgain = false, gameOver = false, turnOver = true, nasazeni = false, move = false, rollBtnPressed = false, goToHomeN = false, goToHomeF = false;
    private Piece movingPiece = null;
    private int nextField, dice = 0;
    private float movingProgress, step = 0.0625f, moveX, moveY, touchx, touchy, pixelWidth, pixelHeight;
    private Texture deska = new Texture("deskaq.png");
    boolean playerSelectedPiece = false, diceRolled = false;


    public PlayState(GameStateManager gsm) {
        super(gsm);
        //cam.setToOrtho(false, appWidth / 2, CloveceNezlobSe.appHeight / 2);
        stage = new Stage(new ExtendViewport(appWidth,appHeight*1.33f,appWidth,appHeight*1.66f, cam));

        Gdx.input.setInputProcessor(stage);

        pixelWidth = Gdx.graphics.getWidth() / 1080f;
        pixelHeight = Gdx.graphics.getHeight() / 1794f;
        if(pixelHeight < 1.2) pixelHeight+=0.05f;
        Texture dice = new Texture("dice.png");

        distanceFieldTexture = new Texture(Gdx.files.internal("verdana39distancefield.png"), false);
        distanceFieldFont = new BitmapFont(Gdx.files.internal("verdana39distancefield.fnt"), new TextureRegion(
                distanceFieldTexture), false);
        distanceFieldFont.setColor(COLOR);
        distanceFieldShader = new DistanceFieldShader();
        labelStyle = new LabelStyle(distanceFieldFont, Color.BLACK);


        fieldImg[0] = new Texture("pole.png");
        fieldImg[1] = new Texture("poleB.png");
        fieldImg[2] = new Texture("poleY.png");
        fieldImg[3] = new Texture("poleR.png");
        fieldImg[4] = new Texture("poleG.png");

        //nastav kameny
        for(int a=0; a < playersCount; a++){
            for(int b=0; b < pieceCount; b++){
                piece[a*4+b] = new Piece(data[40+a*8+b].getX(),
                                         data[40+a*8+b].getY(),
                                         a*4+b,
                                         a,
                                         40+a*8+b );
                data[40+a*8+b].setPieceID(a*4+b);
            }
        }

        //nastav hráče
        //for(int a=0; a < playersCount; a++){
            player[0] = new Player("Player", 0, 0, 0);
            player[1] = new Player("Yellow AI", 1, 0, 0);
            player[2] = new Player("Red AI", 1, 0, 0);
            player[3] = new Player("Green AI", 1, 0, 0);
        //}


        label();
        rollButton();
        statsLabels();

        statsLabels[2][0].setText("y: " + pixelHeight);
        statsLabels[2][1].setText("x: " + pixelWidth);
    }

    public void getPieceByXY(float x, float y){
        for(int a = 0; a < movablePieces.size(); a++){
            if(piece[movablePieces.get(a)].getX()==x && piece[movablePieces.get(a)].getY()==y){
                fromField = piece[movablePieces.get(a)].getFieldNumber();
                playerSelectedPiece = true;
            }
        }
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            touchx = ((Gdx.input.getX() / pixelWidth) - 540) ;
            touchy = (897 - (Gdx.input.getY() / pixelHeight));

            touchx = round(touchx / zoom );
            touchy = round(touchy / zoom );

            statsLabels[0][0].setText("x: " + touchx);
            statsLabels[0][1].setText("y: " + touchy);
        }
    }

    @Override
    public void update(float dt) {
        checkWin();
        if(!gameOver) {
            if ( framePerThrow > 5 || player[currentPlayer].getAi()==0 ) {
                if ( turnOver ) {

                    if(!diceRolled) {
                        if(player[currentPlayer].getAi()==0){
                            dice = throwDice();
                            getMovablePieces(currentPlayer, dice);

                        } else {
                            dice = throwDice();
                            getMovablePieces(currentPlayer, dice);

                        }
                    }
                    if(!movablePieces.isEmpty()) { // pokud je dostupna figurka pro pohyb
                        if(!playerSelectedPiece) { // a hrač žadnou nevybral
                            if(player[currentPlayer].getAi() == 0){
                                handleInput();
                                getPieceByXY(touchx, touchy);
                            }else{
                                callAi(player[currentPlayer].getAi());
                            }

                        }
                        if(playerSelectedPiece){
                            setMove();//hodit kostkou
                            turnOver = false;
                        }
                    } else turnOver = false;
                } else {
                    if(move){startMove();}
                    else {
                        if(!rollAgain) nextPlayer();
                        rollBtnPressed = false;
                        diceRolled = false;
                        turnOver = true;
                        playerSelectedPiece = false;
                        rollButton.setVisible(false);
                        rollAgain = false;
                        framePerThrow = 0;
                    }
                }

            }framePerThrow++;
    //        else {
    //        showRestartButton...
//          }
        }
    }


    private void callAi(int ai) {
        switch(ai){
            case 1:
                fromField = piece[movablePieces.get(0)].getFieldNumber();
                playerSelectedPiece = true;
                break;
            case 2: break;
            case 3: break;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();

        sb.draw(deska, cam.position.x, cam.position.y - deska.getHeight(),
                0, 0, 540, 540, 1f, 1f, 0, 0, 0, 540, 540, true, true);
        sb.draw(deska, cam.position.x, cam.position.y,
                0, 0, 540, 540, 1f, 1f, 0, 0, 0, 540, 540, true, false);
        sb.draw(deska, cam.position.x - deska.getWidth(), cam.position.y - deska.getHeight(),
                0, 0, 540, 540, 1f, 1f, 0, 0, 0, 540, 540, false, true);
        sb.draw(deska, cam.position.x - deska.getWidth() , cam.position.y,
                0, 0, 540, 540, 1f, 1f, 0, 0, 0, 540, 540, false, false);



        refreshBoard(sb);
        paintPieces(sb);
        //int x = 10;
        //x += drawFont(sb, distanceFieldFont, true, true, 1f, x);
        sb.end();

        stage.act();
        stage.draw();
    }

    private void setMove(){
            //markTargetFields();
            movablePieces.clear();
            if (fromField > 39) nasazeni = true; else nasazeni = false;
            movingPiece = piece[data[fromField].getPieceID()];
            setMoveTarget();
            player[currentPlayer].setRound(player[currentPlayer].getRound()+1);
            //round[currentPlayer]++;
            player[currentPlayer].setSum(player[currentPlayer].getSum()+dice);
            //stats[currentPlayer] += dice;
            statsLabels[currentPlayer][0].setText("avg: " + df2.format(player[currentPlayer].getSum() / player[currentPlayer].getRound()));
            statsLabels[currentPlayer][1].setText("sum: " + player[currentPlayer].getSum());

    }

    private void markTargetFields() {
        for(int i = 0; i < movablePieces.size(); i++) {
            // nejak označit - piece[movablePieces.get(i)]
            // na jeho pozici vykreslit button nebo chytit klik
        }
    }

    private void checkWin() {
        if(home[currentPlayer]==pieceCount && !move){
            gameOver = true;
            outputLabel.setText(player[currentPlayer].getName() + " player wins!");
        }
    }

    public void setMoveTarget(){
        Vector2 vector;

        if(!nasazeni){ // posunout dal
            targetField = dice+fromField;

            if (fromField <= goHomeField() && targetField > goHomeField()) { //do domecku
                targetField = movingPiece.getFieldNumber() + dice - goHomeField() + 43 + 8 * currentPlayer;
                home[currentPlayer]++; //win condition +1
                goToHomeN = true;
            }
            else if (targetField > 39){ // pokracuj v kole
                targetField -= 40;
            }

            int targetPiece = data[targetField].getPieceID();
            if (targetPiece != -1) {
                kickPiece(targetPiece);
            }
            movingPiece.setFieldNumber(targetField);
            data[targetField].setPieceID(movingPiece.getPieceId());//nastav novej
            data[fromField].setPieceID(-1);//vynuluj starej
            move=true;
            movingProgress = 1;
            nextField = fromField;
            fromField -= 1;
            if (fromField < 0){ fromField = 39; }

        }
        else { // nasadit
            int targetField = 10+currentPlayer*10;
            if(targetField==40) targetField = 0;//pro posledniho hrace

            int targetPiece = data[targetField].getPieceID();//vyhodit panaka
            if (targetPiece != -1) kickPiece(targetPiece);

            movingPiece.setFieldNumber(targetField);//funkci na validovani - podle hrace posovat o 10
            movingPiece.setPosition(data[targetField].getX(), data[targetField].getY());
            data[targetField].setPieceID(movingPiece.getPieceId());//nastav novej
            data[fromField].setPieceID(-1);//vynuluj starej
//            nasazeni = false;

        }
    }

    private void startMove() {

        if(movingProgress == 1) {
            dice--;
            movingProgress = 0;

            if(!goToHomeF){
                fromField++;
                nextField = nextField + 1;
                if (fromField == 40){ fromField = 0; }
                if (nextField == 40){ nextField = 0; }
                if (nextField > 71){ nextField = 71; }
            }
            else {
                fromField = nextField;
                nextField++;
                if (nextField > 71){ nextField = 71; }
                goToHomeF=false;
            }

            if (goToHomeN) { //do domecku pro cilove pole
                if(fromField == goHomeField()){
                    nextField = 44 + 8 * currentPlayer;
                    goToHomeN = false;
                    goToHomeF = true;
                }
            }

            moveX = data[fromField].getX();
            moveY = data[fromField].getY();
            moveX -= data[nextField].getX();
            moveY -= data[nextField].getY();
            moveX *= step;
            moveY *= step;
        }

        if(dice >= 0){
            movingPiece.movePiece(moveX, moveY);
        }else{
            move = false; //pohyb dokončen
        }

        movingProgress += step;
    }

    private int goHomeField() {
        int home = 100;
        switch (currentPlayer){
            case 0:{ home = 9; break; }
            case 1:{ home = 19; break; }
            case 2:{ home = 29; break; }
            case 3:{ home = 39; break; }
        }
        return home;
    }

    private void kickPiece(int targetPiece){

        int startField = piece[targetPiece].getStartFieldNumber();
        piece[targetPiece].setFieldNumber(startField);
        piece[targetPiece].setPosition(data[startField].getX(), data[startField].getY());
        data[startField].setPieceID(targetPiece);
    }

    private ArrayList<Integer> getMovablePieces(int currentPlayer, int dice) {
        int pieceField;
        for (int i = currentPlayer * pieceCount; i < currentPlayer * pieceCount + pieceCount; i++) {
            pieceField = piece[i].getFieldNumber();
            if(piece[i].getPlayer()==currentPlayer) { // vymazat
                //pro normalni posun po ploše
                if (pieceField < 40 && !(pieceField <= goHomeField() && pieceField + dice > goHomeField())) {
                    int targetPiece = data[pieceField + dice].getPieceID();
                    if(targetPiece == -1) {
                        movablePieces.add(piece[i].getPieceId());
                    }else{
                        if (piece[targetPiece].getPlayer() != currentPlayer){
                            movablePieces.add(piece[i].getPieceId());
                        }
                    }
                }
                //pro nasazeni nove figurky
                else if (pieceField < 44 + 8 * currentPlayer && pieceField > 39 + 8 * currentPlayer){
                    int targetPiece = data[currentPlayer*10].getPieceID();
                    if ((dice == 6)) {
                        if(targetPiece == -1) {
                            movablePieces.add(piece[i].getPieceId());
                        }else{
                            if(piece[targetPiece].getPlayer() != currentPlayer){
                                movablePieces.add(piece[i].getPieceId());
                            }
                        }
                    }
                }
                //zajeti do domečku
                else if(pieceField < 40 && pieceField <= goHomeField() && pieceField + dice > goHomeField()){ //piecefield 44-47 + 8*currentPlayer


                    if(34 + pieceField + dice <= 47 + 10 * currentPlayer) {
                        int targetField = pieceField + dice - goHomeField() + 43 + 8 * currentPlayer;
                        int targetPiece = data[targetField].getPieceID();
                        if(targetPiece == -1) {
                            movablePieces.add(piece[i].getPieceId());
                        }
                    }
                }
            }
        }
        return movablePieces;
    }

    private void paintPieces(SpriteBatch sb){
        Piece[] pieceSort;
        pieceSort = piece.clone();
        //seřazení pro odstranení překryvů
        for (int i = 0; i<pieceSort.length; i++)
        {
            for (int j = 0; j<pieceSort.length; j++)
            {
                if (pieceSort[i].getY() > pieceSort[j].getY())
                {
                    Piece temp = pieceSort[i];
                    pieceSort[i] = pieceSort[j];
                    pieceSort[j] = temp;
                }
            }
        }

        for(Piece pieces : pieceSort)
            sb.draw(pieces.getTexture(),
                    cam.position.x + pieces.getX() * zoom - pieceSize * 0.5f,
                    cam.position.y + pieces.getY() * zoom - pieceSize * 0.4f);
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
        if(dice==6) rollAgain = true;
        outputLabel.setText(player[currentPlayer].getName() + " threw " + dice);
        rollButton.setVisible(false);
        diceRolled = true;
        return dice;

    }

    private void nextPlayer(){
        currentPlayer=nextPlayer;
        nextPlayer++;
        if(nextPlayer > playersCount -1)
            nextPlayer = 0;
        outputLabel.setText(player[currentPlayer].getName() + " is on turn");
    }

    public void rollButton(){
        rollButton.setSize(500,150);
        rollButton.setPosition(cam.position.x - rollButton.getWidth() / 2, cam.position.y - 9 * zoom );
        rollButton.getLabel().setFontScale(2,2);
        rollButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                //outputLabel.setText("Player" + nextPlayer +" is on turn");
                rollBtnPressed = true;
                rollButton.setVisible(false);
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(rollButton);
    }

    public void label(){
        outputLabel = new Label(player[currentPlayer].getName() + " is on turn",comicSkin);
        outputLabel.setSize(stage.getWidth(),80);
        outputLabel.setPosition(cam.position.x - outputLabel.getWidth() /2 , cam.position.y + 7 * zoom );
        outputLabel.setAlignment(Align.center);
        outputLabel.setFontScale(1.5f);
        outputLabel.setStyle(labelStyle);

        stage.addActor(outputLabel);
    }

    public void statsLabels(){
        int labelWidth = 80, labelHeight = 40;
        float fontSize = 0.85f;

        statsLabels[0][0] = new Label("avg: ",labelStyle);
        statsLabels[0][0].setSize(labelWidth,labelHeight);
        statsLabels[0][0].setPosition(cam.position.x - zoom * 3f - 30, cam.position.y + zoom * (5) - 30);
        statsLabels[0][0].setFontScale(fontSize);
        stage.addActor(statsLabels[0][0]);

        statsLabels[0][1] = new Label("sum: ",labelStyle);
        statsLabels[0][1].setSize(labelWidth,labelHeight);
        statsLabels[0][1].setPosition(cam.position.x - zoom * 3f - 30, cam.position.y + zoom * (4) - 30);
        statsLabels[0][1].setFontScale(fontSize);
        stage.addActor(statsLabels[0][1]);

        statsLabels[1][0] = new Label("avg: ",labelStyle);
        statsLabels[1][0].setSize(labelWidth,labelHeight);
        statsLabels[1][0].setPosition(cam.position.x + zoom * 2f - 30, cam.position.y + zoom * (5) - 30);
        statsLabels[1][0].setFontScale(fontSize);
        stage.addActor(statsLabels[1][0]);

        statsLabels[1][1] = new Label("sum: ",labelStyle);
        statsLabels[1][1].setSize(labelWidth,labelHeight);
        statsLabels[1][1].setPosition(cam.position.x + zoom * 2f - 30, cam.position.y + zoom * (4) - 30);
        statsLabels[1][1].setFontScale(fontSize);
        stage.addActor(statsLabels[1][1]);

        statsLabels[2][0] = new Label("avg: ",labelStyle);
        statsLabels[2][0].setSize(labelWidth,labelHeight);
        statsLabels[2][0].setPosition(cam.position.x + zoom * 2f - 30, cam.position.y + zoom * (-4) - 30);
        statsLabels[2][0].setFontScale(fontSize);
        stage.addActor(statsLabels[2][0]);

        statsLabels[2][1] = new Label("sum: ",labelStyle);
        statsLabels[2][1].setSize(labelWidth,labelHeight);
        statsLabels[2][1].setPosition(cam.position.x + zoom * 2f - 30, cam.position.y + zoom * (-5) - 30);
        statsLabels[2][1].setFontScale(fontSize);
        stage.addActor(statsLabels[2][1]);

        statsLabels[3][0] = new Label("avg: ",labelStyle);
        statsLabels[3][0].setSize(labelWidth,labelHeight);
        statsLabels[3][0].setPosition(cam.position.x - zoom * 3f - 30, cam.position.y + zoom * (-4) - 30);
        statsLabels[3][0].setFontScale(fontSize);
        stage.addActor(statsLabels[3][0]);

        statsLabels[3][1] = new Label("sum: ",labelStyle);
        statsLabels[3][1].setSize(labelWidth,labelHeight);
        statsLabels[3][1].setPosition(cam.position.x - zoom * 3f - 30, cam.position.y + zoom * (-5) - 30);
        statsLabels[3][1].setFontScale(fontSize);
        stage.addActor(statsLabels[3][1]);

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

//    private int drawFont (SpriteBatch spriteBatch,BitmapFont font, boolean linearFiltering, boolean useShader, float smoothing, int x) {
//        int y = 10;
//        float maxWidth = 0;
//
//        // set filters for each page
//        TextureFilter minFilter = linearFiltering ? TextureFilter.MipMapLinearNearest : TextureFilter.Nearest;
//        TextureFilter magFilter = linearFiltering ? TextureFilter.Linear : TextureFilter.Nearest;
//        for (int i = 0; i < font.getRegions().size; i++) {
//            font.getRegion(i).getTexture().setFilter(minFilter, magFilter);
//        }
//
//        if (useShader) {
//            spriteBatch.setShader(distanceFieldShader);
//        } else {
//            spriteBatch.setShader(null);
//        }
//
//        for (float scale : SCALES) {
//            font.getData().setScale(scale);
//            layout.setText(font, TEXT);
//            maxWidth = Math.max(maxWidth, layout.width);
//            if (useShader) {
//                distanceFieldShader.setSmoothing(smoothing / scale);
//            }
//            font.draw(spriteBatch, layout, x, y);
//            y += font.getLineHeight();
//            spriteBatch.flush();
//        }
//        getBaselineShift(distanceFieldFont);
//        return (int)Math.ceil(maxWidth);
//    }

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

    public enum player {blue, yellow, red, green}
}