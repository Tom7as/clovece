package com.jerabek.clovece;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener.FocusEvent;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.BooleanArray;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import static com.badlogic.gdx.graphics.Texture.TextureWrap.Repeat;
import static com.jerabek.clovece.CloveceNezlobSe.appHeight;
import static com.jerabek.clovece.CloveceNezlobSe.appWidth;

/**
 * Created by Tomas on 13.03.2017.
 */


public class SettingState extends State{
    private I18NBundle langStr = I18NBundle.createBundle(Gdx.files.internal("strings/strings"));;
    private Texture segoe96Texture, segoe36Texture;
    private BitmapFont segoe96Font, segoe36Font ;
    private Label settingLabel, space, pieceCountLabel, checkbox1label, checkbox2label;
    private Label.LabelStyle fontStyle96, fontStyle36;
    private CheckBox radioButton, threeSix, onSixAgain;
    private TextField player0Name, player1Name, player2Name, player3Name;
    private ButtonGroup<CheckBox> buttonGroup0, buttonGroup1, buttonGroup2, buttonGroup3;
    private Table table, table2;
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
    private final Slider slider = new Slider(1f, 4f, 1f, false, uiSkin);

    public SettingState(GameStateManager gsm) {
        super(gsm);
        stage = new Stage(new ExtendViewport(appWidth,1440,appWidth,1920, cam));
        Gdx.input.setInputProcessor(stage);
        worldHalfHeight = (int) stage.getViewport().getWorldHeight() / 2;
        segoe96Texture = new Texture(Gdx.files.internal("font/segoe96.png"), false);
        segoe96Texture.setFilter(TextureFilter.MipMapLinearNearest, TextureFilter.Linear);
        segoe96Font = new BitmapFont(Gdx.files.internal("font/segoe96.fnt"), new TextureRegion(segoe96Texture), false);
        segoe96Font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.MipMapNearestLinear);

        segoe36Texture = new Texture(Gdx.files.internal("font/segoe36.png"), false);
        segoe36Texture.setFilter(TextureFilter.MipMapLinearNearest, TextureFilter.Linear);
        segoe36Font = new BitmapFont(Gdx.files.internal("font/segoe36.fnt"), new TextureRegion(segoe36Texture), false);
        segoe36Font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.MipMapNearestLinear);

        segoe36Font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        segoe36Font.getData().setScale(0.5f);
        fontStyle36 = new Label.LabelStyle(segoe36Font, Color.BLACK);
        fontStyle96 = new Label.LabelStyle(segoe96Font, Color.BLACK);
        settingLabel = new Label(langStr.get("settings"),fontStyle96);

        woodTexture.setWrap(Repeat, Repeat);

//// TODO: 15.03.2017 sehnat HD skin - udelat, srovnat settings
//// TODO:            přidat reklamu , repeat textury nebo barvu
        backButton();
        rulesButton();
        okRulesButton();
        playButton();
        settingLabel();


        playersSettings();
        rulesSettings();

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


    private void rulesSettings() {
        table2 = new Table();
        //table.setFillParent(true);
        table2.left().top();
        stage.addActor(table2);
        table2.setClip(true);
        table2.setDebug(false);
        table2.setSize(330, 330);
        table2.scaleBy(2f);
        table2.setPosition( 390 - table2.getWidth(), -worldHalfHeight - table2.getHeight() - 180);


        threeSix = new CheckBox("",uiSkin); // prvni checkbox
        threeSix.setName("0");
        table2.add(threeSix).width(20).height(80);

        checkbox1label = new Label(langStr.get("Throw3Time"),fontStyle36);
        checkbox1label.setSize(300,90);
        checkbox1label.setWrap(true);
//        checkbox1label.setPosition(-1030, cam.position.y - 150);
       // checkbox1label.setAlignment(Align.topLeft);
        table2.add(checkbox1label).colspan(4).left();
        table2.row();

        onSixAgain = new CheckBox("",uiSkin); // druhy check
        onSixAgain.setName("0");
        onSixAgain.toggle();
        table2.add(onSixAgain).width(20).height(50);
        table2.add(new Label(langStr.get("6ThrowAgain"), fontStyle36)).height(50).colspan(4).left();
        table2.row();

        pieceCountLabel = new Label(langStr.get("pieceCount") + " 4", fontStyle36);
        table2.add(pieceCountLabel).colspan(2).width(140);

        slider.setValue(4);
        slider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                pieceCountLabel.setText(langStr.get("pieceCount") + " " + (int)slider.getValue());
            }
        });
        table2.add(slider).height(40).colspan(3).width(160);



    }

    private void playersSettings() {
        table = new Table();
        //table.setFillParent(true);
        table.left().top();
        stage.addActor(table);
        table.setClip(true);
        table.setDebug(false);
        table.setSize(330, 330);
        table.scaleBy(2f);
        table.setPosition( 390 - table.getWidth(), worldHalfHeight - table.getHeight() - 180);

        buttonGroup0 = new ButtonGroup<CheckBox>();
        buttonGroup1 = new ButtonGroup<CheckBox>();
        buttonGroup2 = new ButtonGroup<CheckBox>();
        buttonGroup3 = new ButtonGroup<CheckBox>();

        //hrac 0
        table.row().height(10);
        Label nameLabel = new Label(langStr.get("blue") + " " + langStr.get("player"), fontStyle36);
        table.add(nameLabel).width(140).height(30);
        Label playerType = new Label(langStr.get("off"), fontStyle36);
        playerType.setAlignment(Align.center);
        table.add(playerType).width(60).height(30);
        playerType = new Label(langStr.get("human"), fontStyle36);
        playerType.setAlignment(Align.center);
        table.add(playerType).width(60).height(30);
        playerType = new Label(langStr.get("computer"), fontStyle36);
        playerType.setAlignment(Align.center);
        table.add(playerType).width(60).height(30);
        table.row();
        player0Name = new TextField(langStr.get("name"), uiSkin);
        table.add(player0Name).width(130).height(40).left();
        radioButton = new CheckBox("",uiSkin, "radio");
        radioButton.setName("0");
        table.add(radioButton).width(60).height(40);
        buttonGroup0.add(radioButton);
        radioButton = new CheckBox("",uiSkin, "radio");
        radioButton.setName("1");
        radioButton.toggle();
        table.add(radioButton).width(70).height(40);
        buttonGroup0.add(radioButton);
        radioButton = new CheckBox("",uiSkin, "radio");
        radioButton.setName("2");
        table.add(radioButton).width(60).height(40);
        buttonGroup0.add(radioButton);
        table.row().height(12);
        space = new Label("", uiSkin);
        table.add(space);
        //1player
        table.row().height(10);
        nameLabel = new Label(langStr.get("yellow") + " " + langStr.get("player"), fontStyle36);
        table.add(nameLabel).width(140).height(30);
        playerType = new Label(langStr.get("off"), fontStyle36);
        playerType.setAlignment(Align.center);
        table.add(playerType).width(60).height(30);
        playerType = new Label(langStr.get("human"), fontStyle36);
        playerType.setAlignment(Align.center);
        table.add(playerType).width(60).height(30);
        playerType = new Label(langStr.get("computer"), fontStyle36);
        playerType.setAlignment(Align.center);
        table.add(playerType).width(60).height(30);
        table.row();
        player1Name = new TextField(langStr.get("name"), uiSkin);
        table.add(player1Name).width(130).height(40).left();
        radioButton = new CheckBox("",uiSkin, "radio");
        radioButton.setName("0");
        table.add(radioButton).width(60).height(40);
        buttonGroup1.add(radioButton);
        radioButton = new CheckBox("",uiSkin, "radio");
        radioButton.setName("1");
        table.add(radioButton).width(70).height(40);
        buttonGroup1.add(radioButton);
        radioButton = new CheckBox("",uiSkin, "radio");
        radioButton.setName("2");
        radioButton.toggle();
        table.add(radioButton).width(60).height(40);
        buttonGroup1.add(radioButton);
        table.row().height(12);
        table.add(space);
        //2player
        table.row().height(10);
        nameLabel = new Label(langStr.get("red") + " " + langStr.get("player"), fontStyle36);
        table.add(nameLabel).width(140).height(30);
        playerType = new Label(langStr.get("off"), fontStyle36);
        playerType.setAlignment(Align.center);
        table.add(playerType).width(60).height(30);
        playerType = new Label(langStr.get("human"), fontStyle36);
        playerType.setAlignment(Align.center);
        table.add(playerType).width(60).height(30);
        playerType = new Label(langStr.get("computer"), fontStyle36);
        playerType.setAlignment(Align.center);
        table.add(playerType).width(60).height(30);
        table.row();
        player2Name = new TextField(langStr.get("name"), uiSkin);
        table.add(player2Name).width(130).height(40).left();
        radioButton = new CheckBox("",uiSkin, "radio");
        radioButton.setName("0");
        table.add(radioButton).width(60).height(40);
        buttonGroup2.add(radioButton);
        radioButton = new CheckBox("",uiSkin, "radio");
        radioButton.setName("1");
        table.add(radioButton).width(70).height(40);
        buttonGroup2.add(radioButton);
        radioButton = new CheckBox("",uiSkin, "radio");
        radioButton.setName("2");
        radioButton.toggle();
        table.add(radioButton).width(60).height(40);
        buttonGroup2.add(radioButton);
        table.row().height(12);
        table.add(space);
        //3player
        table.row().height(10);
        nameLabel = new Label(langStr.get("green") + " " + langStr.get("player"), fontStyle36);
        table.add(nameLabel).width(140).height(30);
        playerType = new Label(langStr.get("off"), fontStyle36);
        playerType.setAlignment(Align.center);
        table.add(playerType).width(60).height(30);
        playerType = new Label(langStr.get("human"), fontStyle36);
        playerType.setAlignment(Align.center);
        table.add(playerType).width(60).height(30);
        playerType = new Label(langStr.get("computer"), fontStyle36);
        playerType.setAlignment(Align.center);
        table.add(playerType).width(60).height(30);
        table.row();
        player3Name = new TextField(langStr.get("name"), uiSkin);
        table.add(player3Name).width(130).height(40).left();
        radioButton = new CheckBox("",uiSkin, "radio");
        radioButton.setName("0");
        radioButton.toggle();
        table.add(radioButton).width(60).height(40);
        buttonGroup3.add(radioButton);
        radioButton = new CheckBox("",uiSkin, "radio");
        radioButton.setName("1");
        table.add(radioButton).width(70).height(40);
        buttonGroup3.add(radioButton);
        radioButton = new CheckBox("",uiSkin, "radio");
        radioButton.setName("2");
        table.add(radioButton).width(60).height(40);
        buttonGroup3.add(radioButton);
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
        okRulesButton.setPosition(540 - okRulesButton.getWidth() / 2, -worldHalfHeight - 700 );
        okRulesButton.getLabel().setFontScale(fontScale);
        okRulesButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
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
                gsm.set(new MenuState(gsm));
                break;
            case 2:
                if(rulesSlide>worldHalfHeight*2 && rulesOpened == 1){
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
                gsm.set(new PlayState(gsm, saveSetting()));
                break;
            default:
                break;
        }
    }

    public SettingData saveSetting(){
        int [] playerType = new int[4];
        String [] playerName = new String[4];
        Boolean[] otherSettings = new Boolean[2];
        int pieceCount = 4;
        SettingData settingData;

        otherSettings[0] = threeSix.isChecked();
        otherSettings[1] = onSixAgain.isChecked();

        pieceCount = (int) slider.getValue();

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

        return settingData = new SettingData(playerName, playerType, otherSettings, pieceCount);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(woodTexture, 0, -worldHalfHeight * 2.5f, 1080, worldHalfHeight * 5);

        sb.draw(deska, 540,worldHalfHeight - deska.getHeight(),
                0, 0, 540, 540, 1f, 1f, 0, 0, 0, 540, 540, true, true);
        sb.draw(deska, 540,worldHalfHeight,
                0, 0, 540, 540, 1f, 1f, 0, 0, 0, 540, 540, true, false);
        sb.draw(deska, 540 - deska.getWidth(), worldHalfHeight - deska.getHeight(),
                0, 0, 540, 540, 1f, 1f, 0, 0, 0, 540, 540, false, true);
        sb.draw(deska, 540 - deska.getWidth() , worldHalfHeight,
                0, 0, 540, 540, 1f, 1f, 0, 0, 0, 540, 540, false, false);

        sb.draw(deska, 540,-worldHalfHeight - deska.getHeight() ,
                0, 0, 540, 540, 1f, 1f, 0, 0, 0, 540, 540, true, true);
        sb.draw(deska, 540,-worldHalfHeight,
                0, 0, 540, 540, 1f, 1f, 0, 0, 0, 540, 540, true, false);
        sb.draw(deska, 540 - deska.getWidth(), -worldHalfHeight  - deska.getHeight(),
                0, 0, 540, 540, 1f, 1f, 0, 0, 0, 540, 540, false, true);
        sb.draw(deska, 540 - deska.getWidth() , -worldHalfHeight,
                0, 0, 540, 540, 1f, 1f, 0, 0, 0, 540, 540, false, false);
        
        sb.end();
        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        woodTexture.dispose();
        deska.dispose();
        segoe96Texture.dispose();
        segoe96Font.dispose();
        segoe36Texture.dispose();
        segoe36Font.dispose();
    }
}
