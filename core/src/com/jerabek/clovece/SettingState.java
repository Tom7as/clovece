package com.jerabek.clovece;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import static com.jerabek.clovece.CloveceNezlobSe.appHeight;
import static com.jerabek.clovece.CloveceNezlobSe.appWidth;

/**
 * Created by Tomas on 13.03.2017.
 */


public class SettingState extends State{
    private I18NBundle langStr = I18NBundle.createBundle(Gdx.files.internal("strings/strings"));;
    private Texture segoe96Texture, segoe36Texture;
    private BitmapFont segoe96Font, segoe36Font ;
    private Label settingLabel, space;
    private Label.LabelStyle fontStyle96, fontStyle36;
    private ImageTextButton radioButton;
    private TextField player0Name, player1Name, player2Name, player3Name;
    private ButtonGroup buttonGroup0, buttonGroup1, buttonGroup2, buttonGroup3;
    private Table table;
    private Skin uiSkin2 = new Skin(Gdx.files.internal("skin/flatearthui/flat-earth-ui.json"));
    private Skin uiSkin = new Skin(Gdx.files.internal("skin/glassyui/glassy-ui.json"));
    private TextButton backButton = new TextButton(langStr.get("back"), uiSkin);
    private TextButton rulesButton = new TextButton(langStr.get("rules"), uiSkin);
    private TextButton okRulesButton = new TextButton(langStr.get("saveRule"), uiSkin);
    private TextButton playButton = new TextButton(langStr.get("play"), uiSkin);
    private Stage stage;
    private float fontScale = 1.2f;
    private int BACK = 1, RULES = 2, START = 3, action, rulesSlide = 0, rulesOpened=1, worldHalfHeight;
    private Texture woodTexture = new Texture("gameImage/wood.png"), deska = new Texture("gameImage/deskaq.png") ;


    public SettingState(GameStateManager gsm) {
        super(gsm);
        stage = new Stage(new ExtendViewport(appWidth,1440,appWidth,1920, cam));
        Gdx.input.setInputProcessor(stage);
        worldHalfHeight = (int) stage.getViewport().getWorldHeight() / 2;
        segoe96Texture = new Texture(Gdx.files.internal("font/segoe96.png"), false);
        segoe96Texture.setFilter(Texture.TextureFilter.MipMapLinearNearest, Texture.TextureFilter.Linear);
        segoe96Font = new BitmapFont(Gdx.files.internal("font/segoe96.fnt"), new TextureRegion(segoe96Texture), false);
        segoe96Font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.MipMapNearestLinear);

        segoe36Texture = new Texture(Gdx.files.internal("font/segoe36.png"), false);
        segoe36Texture.setFilter(Texture.TextureFilter.MipMapLinearNearest, Texture.TextureFilter.Linear);
        segoe36Font = new BitmapFont(Gdx.files.internal("font/segoe36.fnt"), new TextureRegion(segoe36Texture), false);
        segoe36Font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.MipMapNearestLinear);

        fontStyle36 = new Label.LabelStyle(segoe36Font, Color.BLACK);
        fontStyle96 = new Label.LabelStyle(segoe96Font, Color.BLACK);
        settingLabel = new Label(langStr.get("settings"),fontStyle96);
//// TODO: 15.03.2017 sehnat HD skin - udelat, srovnat settings
//// TODO:            přidat reklamu , repeat textury nebo barvu
        backButton();
        rulesButton();
        okRulesButton();
        playButton();
        settingLabel();

        table = new Table();
        //table.setFillParent(true);
        table.left().top();
        stage.addActor(table);
        table.setClip(true);
        //table.setDebug(true);
        table.setSize(260, 260);
        table.scaleBy(2f);
        table.setPosition(60, cam.position.y - 480);

        buttonGroup0 = new ButtonGroup();
        buttonGroup1 = new ButtonGroup();
        buttonGroup2 = new ButtonGroup();
        buttonGroup3 = new ButtonGroup();

        //hrac 0
        table.row().height(10);
            Label nameLabel = new Label(langStr.get("blue") + " " + langStr.get("player"), fontStyle36);
            table.add(nameLabel).width(100).height(30);
            Label playerType = new Label(langStr.get("off"), fontStyle36);
            playerType.setAlignment(Align.center);
            table.add(playerType).width(50).height(30);
            playerType = new Label(langStr.get("human"), fontStyle36);
            playerType.setAlignment(Align.center);
            table.add(playerType).width(60).height(30);
            playerType = new Label(langStr.get("computer"), fontStyle36);
            playerType.setAlignment(Align.center);
            table.add(playerType).width(50).height(30);
        table.row().height(10);
            player0Name = new TextField(langStr.get("name"), uiSkin2);
            table.add(player0Name).width(100).height(25);
            radioButton = new ImageTextButton("",uiSkin2, "radio");
            radioButton.setName("0");
            table.add(radioButton).width(50);
            buttonGroup0.add(radioButton);
            radioButton = new ImageTextButton("",uiSkin2, "radio");
            radioButton.setName("1");
            radioButton.toggle();
            table.add(radioButton).width(60);
            buttonGroup0.add(radioButton);
            radioButton = new ImageTextButton("",uiSkin2, "radio");
            radioButton.setName("2");
            table.add(radioButton).width(50);
            buttonGroup0.add(radioButton);
        table.row().height(12);
                space = new Label("", uiSkin2);
                table.add(space);
        //1player
        table.row().height(10);
            nameLabel = new Label(langStr.get("yellow") + " " + langStr.get("player"), fontStyle36);
            table.add(nameLabel).width(100).height(30);
            playerType = new Label(langStr.get("off"), fontStyle36);
            playerType.setAlignment(Align.center);
            table.add(playerType).width(50).height(30);
            playerType = new Label(langStr.get("human"), fontStyle36);
            playerType.setAlignment(Align.center);
            table.add(playerType).width(60).height(30);
            playerType = new Label(langStr.get("computer"), fontStyle36);
            playerType.setAlignment(Align.center);
            table.add(playerType).width(50).height(30);
        table.row().height(10);
            player1Name = new TextField(langStr.get("name"), uiSkin2);
            table.add(player1Name).width(100).height(25);
            radioButton = new ImageTextButton("",uiSkin2, "radio");
            radioButton.setName("0");
            table.add(radioButton).width(50);
            buttonGroup1.add(radioButton);
            radioButton = new ImageTextButton("",uiSkin2, "radio");
            radioButton.setName("1");
            table.add(radioButton).width(60);
            buttonGroup1.add(radioButton);
            radioButton = new ImageTextButton("",uiSkin2, "radio");
            radioButton.setName("2");
            radioButton.toggle();
            table.add(radioButton).width(50);
            buttonGroup1.add(radioButton);
        table.row().height(12);
                table.add(space);
        //2player
        table.row().height(10);
             nameLabel = new Label(langStr.get("red") + " " + langStr.get("player"), fontStyle36);
            table.add(nameLabel).width(100).height(30);
            playerType = new Label(langStr.get("off"), fontStyle36);
            playerType.setAlignment(Align.center);
            table.add(playerType).width(50).height(30);
            playerType = new Label(langStr.get("human"), fontStyle36);
            playerType.setAlignment(Align.center);
            table.add(playerType).width(60).height(30);
            playerType = new Label(langStr.get("computer"), fontStyle36);
            playerType.setAlignment(Align.center);
            table.add(playerType).width(50).height(30);
        table.row().height(10);
            player2Name = new TextField(langStr.get("name"), uiSkin2);
            table.add(player2Name).width(100).height(25);
            radioButton = new ImageTextButton("",uiSkin2, "radio");
            radioButton.setName("0");
            table.add(radioButton).width(50);
            buttonGroup2.add(radioButton);
            radioButton = new ImageTextButton("",uiSkin2, "radio");
            radioButton.setName("1");
            table.add(radioButton).width(60);
            buttonGroup2.add(radioButton);
            radioButton = new ImageTextButton("",uiSkin2, "radio");
            radioButton.setName("2");
            radioButton.toggle();
            table.add(radioButton).width(50);
            buttonGroup2.add(radioButton);
        table.row().height(12);
                table.add(space);
        //3player
        table.row().height(10);
            nameLabel = new Label(langStr.get("green") + " " + langStr.get("player"), uiSkin2);
            table.add(nameLabel).width(100).height(30);
            playerType = new Label(langStr.get("off"), fontStyle36);
            playerType.setAlignment(Align.center);
            table.add(playerType).width(50).height(30);
            playerType = new Label(langStr.get("human"), fontStyle36);
            playerType.setAlignment(Align.center);
            table.add(playerType).width(60).height(30);
            playerType = new Label(langStr.get("computer"), fontStyle36);
            playerType.setAlignment(Align.center);
            table.add(playerType).width(50).height(30);
        table.row().height(10);
            player3Name = new TextField(langStr.get("name"), uiSkin2);
            table.add(player3Name).width(100).height(25);
            radioButton = new ImageTextButton("",uiSkin2, "radio");
            radioButton.setName("0");
            radioButton.toggle();
            table.add(radioButton).width(50);
            buttonGroup3.add(radioButton);
            radioButton = new ImageTextButton("",uiSkin2, "radio");
            radioButton.setName("1");
            table.add(radioButton).width(60);
            buttonGroup3.add(radioButton);
            radioButton = new ImageTextButton("",uiSkin2, "radio");
            radioButton.setName("2");
            table.add(radioButton).width(50);
            buttonGroup3.add(radioButton);


//        ImageTextButton imageTextButton = new ImageTextButton("Flat", skin, "radio");
//        buttonGroup.add(imageTextButton);
//        table.add(imageTextButton);
//
//        imageTextButton = new ImageTextButton("Round", skin, "radio");
//        buttonGroup.add(imageTextButton);
//        table.add(imageTextButton);
//
//        imageTextButton = new ImageTextButton("Cuboid", skin, "radio");
//        buttonGroup.add(imageTextButton);
//        table.add(imageTextButton);



    }

    private void settingLabel(){
        settingLabel.setSize(stage.getWidth(),120);
        settingLabel.setPosition(cam.position.x - settingLabel.getWidth() / 2 , cam.position.y + 580);
        settingLabel.setAlignment(Align.center);
        settingLabel.setFontScale(1f);
        stage.addActor(settingLabel);
    }
    private void backButton(){
        backButton.setSize(300,150);
        backButton.setPosition(cam.position.x - backButton.getWidth() / 2 - 350, cam.position.y - 700);
        backButton.getLabel().setFontScale(fontScale);
        backButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
//              outputLabel.setText("Player" + nextPlayer +" is on turn");
                action = BACK;
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(backButton);
    }
    private void rulesButton(){
        rulesButton.setSize(300,150);
        rulesButton.setPosition(cam.position.x - rulesButton.getWidth() / 2, cam.position.y - 700);
        rulesButton.getLabel().setFontScale(fontScale);
        rulesButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
//              outputLabel.setText("Player" + nextPlayer +" is on turn");
                action = RULES;
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(rulesButton);
    }
    private void okRulesButton(){
        okRulesButton.setSize(300,150);
        okRulesButton.setPosition(cam.position.x - okRulesButton.getWidth() / 2, cam.position.y - 2700);
        okRulesButton.getLabel().setFontScale(fontScale);
        okRulesButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
//              outputLabel.setText("Player" + nextPlayer +" is on turn");
                action = RULES;
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(okRulesButton);
    }
    private void playButton(){
        playButton.setSize(300,150);
        playButton.setPosition(cam.position.x - playButton.getWidth() / 2 + 350, cam.position.y - 700);
        playButton.getLabel().setFontScale(fontScale);
        playButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
//              outputLabel.setText("Player" + nextPlayer +" is on turn");
                action = START;
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(playButton);
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {

        switch(action){
            case 1:
                gsm.push(new MenuState(gsm));
                break;
            case 2:
                if(rulesSlide>2000 && rulesOpened == 1){
                    action = 0;
                    rulesOpened = -1;
                }else if (rulesSlide <= 0 && rulesOpened == -1){
                    action = 0;
                    rulesOpened = 1;
                }else {
                    cam.position.y -= 18 * rulesOpened;
                    rulesSlide += 18 * rulesOpened;
                }
                break;
            case 3:
                gsm.push(new PlayState(gsm, saveSetting()));
                break;
            default:
                break;
        }
    }

    public SettingData saveSetting(){
        int [] playerType = new int[4];
        String [] playerName = new String[4];
        int [] otherSettings = new int[0];
        SettingData settingData;

        playerType[0] = Integer.parseInt(buttonGroup0.getChecked().getName());
        playerType[1] = Integer.parseInt(buttonGroup1.getChecked().getName());
        playerType[2] = Integer.parseInt(buttonGroup2.getChecked().getName());
        playerType[3] = Integer.parseInt(buttonGroup3.getChecked().getName());

        playerName[0] = player0Name.getText();
        playerName[1] = player1Name.getText();
        playerName[2] = player2Name.getText();
        playerName[3] = player3Name.getText();

        for(int a=0; a < playerType.length; a++) {
            if (playerName[a].equals(langStr.get("name"))) {
                switch (playerType[a]) {
                    case 0: playerName[a] = "";
                        break;
                    case 1:
                        playerName[a] = langStr.get("player");
                        break;
                    case 2:
                        playerName[a] = langStr.get(a + "Ai");
                        break;
                    default:
                        break;
                }
            }
        }



        //otherSettings[0] = something;

        return settingData = new SettingData(playerName, playerType, otherSettings);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();

        sb.draw(woodTexture, 0, worldHalfHeight * (-2), 1080, worldHalfHeight * 4, 0, 0, 1, 1);
        sb.draw(deska, 540, worldHalfHeight - deska.getHeight(),
                0, 0, 540, 540, 1f, 1f, 0, 0, 0, 540, 540, true, true);
        sb.draw(deska, 540,worldHalfHeight,
                0, 0, 540, 540, 1f, 1f, 0, 0, 0, 540, 540, true, false);
        sb.draw(deska, 540 - deska.getWidth(), worldHalfHeight - deska.getHeight(),
                0, 0, 540, 540, 1f, 1f, 0, 0, 0, 540, 540, false, true);
        sb.draw(deska, 540 - deska.getWidth() , worldHalfHeight,
                0, 0, 540, 540, 1f, 1f, 0, 0, 0, 540, 540, false, false);

        sb.end();
        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {

    }
}
