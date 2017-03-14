package com.jerabek.clovece;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
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


public class MenuState extends State{
    private I18NBundle langStr = I18NBundle.createBundle(Gdx.files.internal("strings/strings"), "UTF-8");
    private Texture segoe96Texture;
    private BitmapFont segoe96Font;
    private Label logoLabel;
    private Label.LabelStyle fontStyle96;
    private Skin uiSkin = new Skin(Gdx.files.internal("skin/glassyui/glassy-ui.json"));
    private TextButton newGameButton = new TextButton(langStr.get("newGame"), uiSkin);
    private TextButton resumeButton = new TextButton(langStr.get("resumeGame"), uiSkin);
    private TextButton quitButton = new TextButton(langStr.get("quitGame"), uiSkin);
    private TextButton helpButton = new TextButton(langStr.get("gameRules"), uiSkin);
    private Stage stage;
    private int NEW_GAME = 1, RESUME = 2, QUIT = 3, HELP = 4, action;
    private Texture woodTexture = new Texture("gameImage/wood.png"), logoImage = new Texture("logo.png");

    public MenuState(GameStateManager gsm) {
        super(gsm);
        stage = new Stage(new ExtendViewport(appWidth,appHeight*1.33f,appWidth,appHeight*1.7f, cam));
        Gdx.input.setInputProcessor(stage);

        segoe96Texture = new Texture(Gdx.files.internal("font/segoe96.png"), false);
        segoe96Texture.setFilter(Texture.TextureFilter.MipMapLinearNearest, Texture.TextureFilter.Linear);
        segoe96Font = new BitmapFont(Gdx.files.internal("font/segoe96.fnt"), new TextureRegion(segoe96Texture), false);
        segoe96Font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.MipMapNearestLinear);

        fontStyle96 = new Label.LabelStyle(segoe96Font, Color.BLACK);
        logoLabel = new Label(langStr.get("gameName"),fontStyle96);
        Gdx.input.setCatchBackKey(true);
        logoLabel();
        newGameButton();
        resumeButton();
        helpButton();
        quitButton();
    }

    private void logoLabel(){
        logoLabel.setSize(stage.getWidth(),120);
        logoLabel.setPosition(cam.position.x - logoLabel.getWidth() /2 , cam.position.y + 350);
        logoLabel.setAlignment(Align.center);
        logoLabel.setFontScale(1.5f);
        stage.addActor(logoLabel);
    }
    private void newGameButton(){
        newGameButton.setSize(600,150);
        newGameButton.setPosition(cam.position.x - newGameButton.getWidth() / 2, cam.position.y );
        newGameButton.getLabel().setFontScale(1.5f);
        newGameButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
//              outputLabel.setText("Player" + nextPlayer +" is on turn");
                action = NEW_GAME;
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(newGameButton);
    }
    private void resumeButton(){
        resumeButton.setSize(600,150);
        resumeButton.setPosition(cam.position.x - resumeButton.getWidth() / 2, cam.position.y - 200);
        resumeButton.getLabel().setFontScale(1.5f);
        resumeButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
//              outputLabel.setText("Player" + nextPlayer +" is on turn");
                action = RESUME;
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(resumeButton);
    }
    private void helpButton(){
        helpButton.setSize(600,150);
        helpButton.setPosition(cam.position.x - helpButton.getWidth() / 2, cam.position.y - 400);
        helpButton.getLabel().setFontScale(1.5f);
        helpButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
//              outputLabel.setText("Player" + nextPlayer +" is on turn");
                action = HELP;
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(helpButton);
    }
    private void quitButton(){
        quitButton.setSize(600,150);
        quitButton.setPosition(cam.position.x - quitButton.getWidth() / 2, cam.position.y - 600);
        quitButton.getLabel().setFontScale(1.5f);
        quitButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
//              outputLabel.setText("Player" + nextPlayer +" is on turn");
                action = QUIT;
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(quitButton);
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {
        switch(action){
            case 1:
                gsm.push(new SettingState(gsm));
                break;
            case 2:
                break;
            case 4:
                break;
            case 3:
                Gdx.app.exit();
                break;
            default:
                break;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();

        sb.draw(woodTexture, 0, 0, 1080, 2100, 0, 0, 1, 1);
        sb.draw(logoImage, cam.position.x - logoImage.getWidth() , cam.position.y + 500, 288, 288);

        sb.end();
        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {

    }
}
