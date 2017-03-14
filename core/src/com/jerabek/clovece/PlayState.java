package com.jerabek.clovece;


import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import com.badlogic.gdx.utils.Align;

import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static com.badlogic.gdx.math.MathUtils.random;
import static com.badlogic.gdx.math.MathUtils.round;
import static com.jerabek.clovece.CloveceNezlobSe.*;

/**
 * Created by Tomas on 2/11/2017.
 */
public class PlayState extends State {

    private Texture segoe96Texture, segoe48Texture , segoe36Texture;
    private BitmapFont segoe96Font, segoe48Font, segoe36Font;

    private Texture[] fieldImg = new Texture[5];
    private Stage stage;

    private LabelStyle fontStyle96, fontStyle48, fontStyle36;
    private Label outputLabel;

    private int zoom = 90;
    private float fieldSize = 94, smallFieldSize = fieldSize*0.85f, pieceSize = 61;

    private com.jerabek.clovece.GameField[] data = com.jerabek.clovece.GameField.getData();

    private ArrayList<Integer> movablePieces = new ArrayList<Integer>();

    private int playersCount = 4, pieceCount = 4;
    private com.jerabek.clovece.Piece[] piece = new com.jerabek.clovece.Piece[pieceCount*playersCount];
    private com.jerabek.clovece.Player[] player = new com.jerabek.clovece.Player[playersCount];
    private Label[][] statsLabels = new Label[playersCount][3];

    private int currentPlayer=0, nextPlayer=1, framePerThrow =0, fromField, targetField;
    private Vector3 touchPoint = new Vector3();
    private I18NBundle langStr = I18NBundle.createBundle(Gdx.files.internal("strings/strings"));;
    private Skin uiSkin = new Skin(Gdx.files.internal("skin/glassyui/glassy-ui.json"));
    private TextButton rollButton = new TextButton(langStr.get("roll"), uiSkin);
    private TextButton backButton = new TextButton(langStr.get("back"), uiSkin);
    private int [] home = new int[playersCount];
    private boolean rollAgain = false, gameOver = false, turnOver = true, nasazeni = false, move = false, rollBtnPressed = false, goToHomeN = false, goToHomeF = false;
    private com.jerabek.clovece.Piece movingPiece = null;
    private int nextField, dice = 0, BACK = 1, action= 0;
    private float movingProgress, step = 0.0625f, moveX, moveY, touchx, touchy, pixelWidth, pixelHeight;
    private Texture woodTexture = new Texture("gameImage/wood.png"), deska = new Texture("gameImage/deskaq.png") , pieceMark;
    private boolean playerSelectedPiece = false, diceRolled = false;
    private String sixString, totalStr;
    private String playerStr, blueStr, redStr, yellowStr, greenStr, winStr, onTurnStr, threwStr, rollStr;


    public PlayState(GameStateManager gsm) {
        super(gsm);
        playerStr = langStr.get("player");
        blueStr = langStr.get("blue");
        redStr = langStr.get("red");
        yellowStr = langStr.get("yellow");
        greenStr = langStr.get("green");
        winStr = " " + langStr.get("win");
        onTurnStr = " " + langStr.get("onTurn");
        threwStr = " " + langStr.get("threw");
        sixString = langStr.get("six");
        totalStr = langStr.get("total");

        //cam.setToOrtho(false, appWidth / 2, CloveceNezlobSe.appHeight / 2);
        stage = new Stage(new ExtendViewport(appWidth,appHeight*1.33f,appWidth,appHeight*1.7f, cam));
        Gdx.input.setInputProcessor(stage);

        pieceMark = new Texture("gameImage/pieceMark.png");

        segoe96Texture = new Texture(Gdx.files.internal("font/segoe96.png"), false);
        segoe96Texture.setFilter(TextureFilter.MipMapLinearNearest, TextureFilter.Linear);
        segoe96Font = new BitmapFont(Gdx.files.internal("font/segoe96.fnt"), new TextureRegion(segoe96Texture), false);
        segoe96Font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.MipMapNearestLinear);
        segoe48Texture = new Texture(Gdx.files.internal("font/segoe48.png"), false);
        segoe48Font = new BitmapFont(Gdx.files.internal("font/segoe48.fnt"), new TextureRegion(segoe48Texture), false);
        segoe48Font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.MipMapLinearLinear);
        segoe36Texture = new Texture(Gdx.files.internal("font/segoe36.png"), false);
        segoe36Texture.setFilter(TextureFilter.MipMapLinearNearest, TextureFilter.Linear);
        segoe36Font = new BitmapFont(Gdx.files.internal("font/segoe36.fnt"), new TextureRegion(segoe36Texture), false);
        segoe36Font.getRegion().getTexture().setFilter(TextureFilter.Nearest, TextureFilter.MipMapNearestLinear);

        fontStyle96 = new LabelStyle(segoe96Font, Color.BLACK);
        fontStyle48 = new LabelStyle(segoe48Font, Color.BLACK);
        fontStyle36 = new LabelStyle(segoe36Font, Color.BLACK);

        fieldImg[0] = new Texture("gameImage/pole.png");
        fieldImg[1] = new Texture("gameImage/poleB.png"); // 1c78ff
        fieldImg[2] = new Texture("gameImage/poleY.png"); // ffcc00
        fieldImg[3] = new Texture("gameImage/poleR.png"); // e63434
        fieldImg[4] = new Texture("gameImage/poleG.png"); // 23c923
        for(int a=0; a < fieldImg.length; a++) {
            fieldImg[a].setFilter(TextureFilter.Linear, TextureFilter.Linear);
        }
        //nastav kameny
        for(int a=0; a < playersCount; a++){
            for(int b=0; b < pieceCount; b++){
                piece[a*4+b] = new com.jerabek.clovece.Piece(data[40+a*8+b].getX(),
                                         data[40+a*8+b].getY(),
                                         a*4+b,
                                         a,
                                         40+a*8+b);
                data[40+a*8+b].setPieceID(a*4+b);
            }
        }

        //nastav hráče
        //for(int a=0; a < playersCount; a++){
            player[0] = new com.jerabek.clovece.Player(playerStr, 0, 0, 0);
            player[1] = new com.jerabek.clovece.Player(yellowStr, 1, 0, 0);
            player[2] = new com.jerabek.clovece.Player(redStr, 1, 0, 0);
            player[3] = new com.jerabek.clovece.Player(greenStr, 1, 0, 0);
        //}

        label();
        rollButton();
        backButton();
        statsLabels();
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
            touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            cam.unproject(touchPoint);

            touchx = (touchPoint.x - (cam.viewportWidth / 2));
            touchy = (touchPoint.y - (cam.viewportHeight / 2));

            touchx = round(touchx / zoom );
            touchy = round(touchy / zoom );
        }
    }

    @Override
    public void update(float dt) {
        checkWin();
        if(action==1) gsm.push(new MenuState(gsm));
        if(!gameOver) {
            if ( framePerThrow > 40 || player[currentPlayer].getAi()==0 ) {
                if ( turnOver ) {
                    if(!diceRolled) {
                        if(player[currentPlayer].getAi()==0){

                            if(rollBtnPressed){
                                rollBtnPressed = false;
                                diceRolled = true;
                                getMovablePieces(currentPlayer, dice);
                            } else rollButton.setVisible(true);
                        } else {
                            dice = throwDice();
                            diceRolled = true;
                            getMovablePieces(currentPlayer, dice);
                        }
                    }
                    if(!movablePieces.isEmpty() && diceRolled) { // pokud je dostupna figurka pro pohyb
                        if(!playerSelectedPiece) { // a hrač žadnou nevybral

                            if(player[currentPlayer].getAi() == 0){
                                handleInput();
                                getPieceByXY(touchx, touchy);
                                //getPieceByXY(touchx, touchy);
                            }else if (framePerThrow > 80){
                                callAi(player[currentPlayer].getAi());
                            }

                        } else {
                            setMove();//hodit kostkou
                            turnOver = false;

                        }
                    } else if (diceRolled) turnOver = false;
                } else {
                    if(move){startMove();}
                    else if (framePerThrow > 80){
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

        sb.draw(woodTexture, 0, 0, 1080, 2100, 0, 0, 1, 1);
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

        sb.end();

        stage.act();
        stage.draw();
    }

    private void setMove(){
            movablePieces.clear();
            if (fromField > 39) nasazeni = true; else nasazeni = false;
            movingPiece = piece[data[fromField].getPieceID()];
            setMoveTarget();
            player[currentPlayer].setSum(player[currentPlayer].getSum()+dice);
            statsLabels[currentPlayer][1].setText(totalStr + " " + player[currentPlayer].getSum());
    }

    private void checkWin() {
        if(home[currentPlayer]==pieceCount && !move){
            gameOver = true;
            outputLabel.setText(player[currentPlayer].getName() + winStr);
        }
    }

    private void setMoveTarget(){
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
                player[currentPlayer].setSixs(player[currentPlayer].getSixs()+1);
            }
            movingPiece.setFieldNumber(targetField);
            data[targetField].setPieceID(movingPiece.getPieceId());//nastav novej
            data[fromField].setPieceID(-1);//vynuluj starej
            move=true;
            movingProgress = 1;
            nextField = fromField;
            fromField -= 1;
            if (fromField < 0){ fromField = 39; }

        } else { // nasadit
            int targetField = 10+currentPlayer*10;
            if(targetField==40) targetField = 0;//pro posledniho hrace

            int targetPiece = data[targetField].getPieceID();//vyhodit panaka
            if (targetPiece != -1) kickPiece(targetPiece);

            movingPiece.setFieldNumber(targetField);//funkci na validovani - podle hrace posovat o 10
            movingPiece.setPosition(data[targetField].getX(), data[targetField].getY());
            data[targetField].setPieceID(movingPiece.getPieceId());//nastav novej
            data[fromField].setPieceID(-1);//vynuluj starej
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
                    targetField = pieceField + dice;
                    if(targetField > 39) targetField -= 40;
                    int targetPiece = data[targetField].getPieceID();

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
                    int targetPiece = data[getStartFieldByPlayer()].getPieceID();
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

    public int getStartFieldByPlayer(){
        int fieldNumber = 0;
        switch (currentPlayer) {
            case 0:
                fieldNumber = 10;
                break;
            case 1:
                fieldNumber = 20;
                break;
            case 2:
                fieldNumber = 30;
                break;
            case 3:
                fieldNumber = 0;
                break;
        }
        return fieldNumber;
    }

    private void paintPieces(SpriteBatch sb){
        com.jerabek.clovece.Piece[] pieceSort;
        pieceSort = piece.clone();

        //seřazení pro odstranení překryvů
        for (int i = 0; i<pieceSort.length; i++)
        {
            for (int j = 0; j<pieceSort.length; j++)
            {
                if (pieceSort[i].getY() > pieceSort[j].getY())
                {
                    com.jerabek.clovece.Piece temp = pieceSort[i];
                    pieceSort[i] = pieceSort[j];
                    pieceSort[j] = temp;
                }
            }
        }

        //zvyrazni hejbatelne figurky
        if(!playerSelectedPiece && !movablePieces.isEmpty()){
            for(int pieceID : movablePieces){
                sb.draw(pieceMark,
                        cam.position.x + piece[pieceID].getX() * zoom - pieceMark.getWidth() / 2,
                        cam.position.y + piece[pieceID].getY() * zoom - 35);
            }
        }

        //vykresli figurky
        for(com.jerabek.clovece.Piece pieces : pieceSort) {
            sb.draw(pieces.getTexture(),
                    cam.position.x + pieces.getX() * zoom - pieceSize * 0.5f,
                    cam.position.y + pieces.getY() * zoom - pieceSize * 0.4f);
        }
    }

    private void refreshBoard(SpriteBatch sb) {
        for (com.jerabek.clovece.GameField aData : data) {
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
        if(dice==6) {
            rollAgain = true;

            player[currentPlayer].addSix();
            statsLabels[currentPlayer][0].setText(sixString + " " + player[currentPlayer].getSixs());
        }
        outputLabel.setText(player[currentPlayer].getName() + threwStr + " " + dice);
        rollButton.setVisible(false);

        return dice;

    }

    private void nextPlayer(){
        currentPlayer=nextPlayer;
        nextPlayer++;
        if(nextPlayer > playersCount -1)
            nextPlayer = 0;
        outputLabel.setText(player[currentPlayer].getName() + onTurnStr);
    }

    private void backButton(){
        backButton.setSize(250,150);
        backButton.setPosition(cam.position.x - rollButton.getWidth() - 20, cam.position.y - 8 * zoom + 20);
        backButton.getLabel().setFontScale(1.2f);
        backButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
//              outputLabel.setText("Player" + nextPlayer +" is on turn");
                saveGame();
                action = BACK;
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(backButton);
    }

    public void rollButton(){
        rollButton.setSize(500,150);
        rollButton.setPosition(cam.position.x - rollButton.getWidth() / 2, cam.position.y - 8 * zoom + 20);
        rollButton.getLabel().setFontScale(1.5f);
        rollButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
//                outputLabel.setText("Player" + nextPlayer +" is on turn");
                rollBtnPressed = true;
                dice = throwDice();
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
        outputLabel = new Label(player[currentPlayer].getName() + onTurnStr ,fontStyle96);
        outputLabel.setSize(stage.getWidth(),80);
        outputLabel.setPosition(cam.position.x - outputLabel.getWidth() /2 , cam.position.y + 7 * zoom - 30);
        outputLabel.setAlignment(Align.center);
        stage.addActor(outputLabel);
    }

    public void statsLabels() {
        int labelWidth = 80, labelHeight = 40;

        float fontSize = 1;
        //0
        statsLabels[0][2] = new Label(player[0].getName(), fontStyle36);
        statsLabels[0][2].setSize(labelWidth, labelHeight);
        statsLabels[0][2].setPosition(cam.position.x + zoom * (-3) - 40, cam.position.y + zoom * (5));
        stage.addActor(statsLabels[0][2]);

        statsLabels[0][0] = new Label(sixString, fontStyle36);
        statsLabels[0][0].setSize(labelWidth, labelHeight);
        statsLabels[0][0].setPosition(cam.position.x + zoom * (-3) - 40, cam.position.y + zoom * (4));
        stage.addActor(statsLabels[0][0]);

        statsLabels[0][1] = new Label(totalStr, fontStyle36);
        statsLabels[0][1].setSize(labelWidth, labelHeight);
        statsLabels[0][1].setPosition(cam.position.x + zoom * (-3) - 40, cam.position.y + zoom * (4) - 50);
        stage.addActor(statsLabels[0][1]);

        //p1
        statsLabels[1][2] = new Label(player[1].getName(), fontStyle36);
        statsLabels[1][2].setSize(labelWidth, labelHeight);
        statsLabels[1][2].setPosition(cam.position.x + zoom * 2 - 40, cam.position.y + zoom * (5));
        stage.addActor(statsLabels[1][2]);

        statsLabels[1][0] = new Label(sixString, fontStyle36);
        statsLabels[1][0].setSize(labelWidth, labelHeight);
        statsLabels[1][0].setPosition(cam.position.x + zoom * 2 - 40, cam.position.y + zoom * (4) );
        stage.addActor(statsLabels[1][0]);

        statsLabels[1][1] = new Label(totalStr, fontStyle36);
        statsLabels[1][1].setSize(labelWidth, labelHeight);
        statsLabels[1][1].setPosition(cam.position.x + zoom * 2 - 40, cam.position.y + zoom * (4) - 50);
        stage.addActor(statsLabels[1][1]);

        //p2
        statsLabels[2][2] = new Label(player[2].getName(), fontStyle36);
        statsLabels[2][2].setSize(labelWidth, labelHeight);
        statsLabels[2][2].setPosition(cam.position.x + zoom * 2 - 40, cam.position.y + zoom * (-4));
        stage.addActor(statsLabels[2][2]);

        statsLabels[2][0] = new Label(sixString, fontStyle36);
        statsLabels[2][0].setSize(labelWidth, labelHeight);
        statsLabels[2][0].setPosition(cam.position.x + zoom * 2 - 40, cam.position.y + zoom * (-5) + 10);
        stage.addActor(statsLabels[2][0]);

        statsLabels[2][1] = new Label(totalStr, fontStyle36);
        statsLabels[2][1].setSize(labelWidth, labelHeight);
        statsLabels[2][1].setPosition(cam.position.x + zoom * 2 - 40, cam.position.y + zoom * (-5) - 40);
        stage.addActor(statsLabels[2][1]);

        //p3
        statsLabels[3][2] = new Label(player[3].getName(), fontStyle36);
        statsLabels[3][2].setSize(labelWidth, labelHeight);
        statsLabels[3][2].setPosition(cam.position.x + zoom * (-3) - 40, cam.position.y + zoom * (-4));
        stage.addActor(statsLabels[3][2]);

        statsLabels[3][0] = new Label(sixString, fontStyle36);
        statsLabels[3][0].setSize(labelWidth, labelHeight);
        statsLabels[3][0].setPosition(cam.position.x + zoom * (-3) - 40, cam.position.y + zoom * (-5) + 10);
        stage.addActor(statsLabels[3][0]);

        statsLabels[3][1] = new Label(totalStr, fontStyle36);
        statsLabels[3][1].setSize(labelWidth, labelHeight);
        statsLabels[3][1].setPosition(cam.position.x + zoom * (-3) - 40, cam.position.y + zoom * (-5) - 40);
        stage.addActor(statsLabels[3][1]);

    }

    @Override
    public void dispose() {

        for(int i = 0; i < data.length; i++)
            fieldImg[i].dispose();

        for (com.jerabek.clovece.Piece aPiece : piece) aPiece.dispose();

        segoe96Texture.dispose();
        segoe96Font.dispose();
        segoe48Texture.dispose();
        segoe48Font.dispose();
        segoe36Texture.dispose();
        segoe36Font.dispose();
        System.out.println("Board Disposed");
    }

    //// TODO: 22.02.2017 SAVEGAME
    public void saveGame(){

    }

}