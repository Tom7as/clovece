package com.jerabek.clovece;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
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
    private Texture segoe96Texture;
    private BitmapFont segoe96Font;
    private Label logoLabel;
    private Label.LabelStyle fontStyle96;
    private Skin uiSkin = new Skin(Gdx.files.internal("skin/glassyui/glassy-ui.json"));
    private TextButton backButton = new TextButton(langStr.get("back"), uiSkin);
    private TextButton rulesButton = new TextButton(langStr.get("rules"), uiSkin);
    private TextButton okRulesButton = new TextButton("ok", uiSkin);
    private TextButton playButton = new TextButton(langStr.get("play"), uiSkin);
    private Stage stage;
    private int BACK = 1, RULES = 2, START = 3, action, rulesSlide = 0, rulesOpened=1;
    private Texture woodTexture = new Texture("gameImage/wood.png");

    public SettingState(GameStateManager gsm) {
        super(gsm);
        stage = new Stage(new ExtendViewport(appWidth,appHeight*1.33f,appWidth,appHeight*1.7f, cam));
        Gdx.input.setInputProcessor(stage);

        segoe96Texture = new Texture(Gdx.files.internal("font/segoe96.png"), false);
        segoe96Texture.setFilter(Texture.TextureFilter.MipMapLinearNearest, Texture.TextureFilter.Linear);
        segoe96Font = new BitmapFont(Gdx.files.internal("font/segoe96.fnt"), new TextureRegion(segoe96Texture), false);
        segoe96Font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.MipMapNearestLinear);

        fontStyle96 = new Label.LabelStyle(segoe96Font, Color.BLACK);
        logoLabel = new Label(langStr.get("settings"),fontStyle96);


        backButton();
        rulesButton();
        okRulesButton();
        playButton();
        logoLabel();
    }


    private void logoLabel(){
        logoLabel.setSize(stage.getWidth(),120);
        logoLabel.setPosition(50 , cam.position.y + 50);
        logoLabel.setAlignment(Align.center);
        logoLabel.setFontScale(1.5f);
        stage.addActor(logoLabel);
    }
    private void backButton(){
        backButton.setSize(300,150);
        backButton.setPosition(cam.position.x - backButton.getWidth() / 2 - 350, cam.position.y - 700);
        backButton.getLabel().setFontScale(1.2f);
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
        rulesButton.getLabel().setFontScale(1.2f);
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
        okRulesButton.getLabel().setFontScale(1.2f);
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
        playButton.getLabel().setFontScale(1.2f);
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
                    cam.position.y -= 12 * rulesOpened;
                    rulesSlide += 12 * rulesOpened;
                }
                break;
            case 3:
                gsm.push(new PlayState(gsm));
                break;
            default:
                break;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();

        sb.draw(woodTexture, 0, -2100, 1080, 3900, 0, 0, 1, 1);

        sb.end();
        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {

    }
}
