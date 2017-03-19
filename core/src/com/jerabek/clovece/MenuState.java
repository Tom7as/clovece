package com.jerabek.clovece;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import static com.badlogic.gdx.graphics.Texture.TextureWrap.Repeat;
import static com.jerabek.clovece.CloveceNezlobSe.appHeight;
import static com.jerabek.clovece.CloveceNezlobSe.appWidth;

/**
 * Created by Tomas on 13.03.2017.
 */


public class MenuState extends State{

    ShapeRenderer shapeRenderer;
    private final int worldHalfHeight;
    private I18NBundle langStr = I18NBundle.createBundle(Gdx.files.internal("strings/strings"), "UTF-8");
    private Texture segoe96Texture, segoe48Texture;
    private BitmapFont segoe96Font, segoe48Font;
    private Label logoLabel, helpLabel;
    private Label.LabelStyle fontStyle96, fontStyle48;
    private Skin uiSkin = new Skin(Gdx.files.internal("skin/glassyui/glassy-ui.json"));
    private TextButton newGameButton = new TextButton(langStr.get("newGame"), uiSkin);
    private TextButton resumeButton = new TextButton(langStr.get("resumeGame") , uiSkin);
    private TextButton quitButton = new TextButton(langStr.get("quitGame"), uiSkin);
    private TextButton helpButton = new TextButton(langStr.get("gameRules"), uiSkin);
    private TextButton okHelpButton = new TextButton(langStr.get("ok"), uiSkin);
    private Stage stage;
    private int NEW_GAME = 1, RESUME = 2, QUIT = 3, HELP = 4, action, helpOpened = 1, helpSlide = 0;
    private Texture woodTexture = new Texture("gameImage/wood.png"), logoImage = new Texture("logo.png");

    public MenuState(GameStateManager gsm) {
        super(gsm);
        shapeRenderer = new ShapeRenderer();
        stage = new Stage(new ExtendViewport(appWidth,appHeight*1.33f,appWidth,appHeight*1.7f, cam));
        Gdx.input.setInputProcessor(stage);
        worldHalfHeight = (int) stage.getViewport().getWorldHeight() / 2;
        woodTexture.setWrap(Repeat, Repeat);
        segoe96Texture = new Texture(Gdx.files.internal("font/segoe96.png"), false);
        segoe96Texture.setFilter(Texture.TextureFilter.MipMapLinearNearest, Texture.TextureFilter.Linear);
        segoe96Font = new BitmapFont(Gdx.files.internal("font/segoe96.fnt"), new TextureRegion(segoe96Texture), false);
        segoe96Font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.MipMapNearestLinear);

        segoe48Texture = new Texture(Gdx.files.internal("font/segoe48.png"), false);
        segoe48Texture.setFilter(Texture.TextureFilter.MipMapLinearNearest, Texture.TextureFilter.Linear);
        segoe48Font = new BitmapFont(Gdx.files.internal("font/segoe48.fnt"), new TextureRegion(segoe48Texture), false);
        segoe48Font.getRegion().getTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.MipMapNearestLinear);

        fontStyle96 = new Label.LabelStyle(segoe96Font, Color.BLACK);
        fontStyle48 = new Label.LabelStyle(segoe48Font, Color.BLACK);



        logoLabel = new Label(langStr.get("gameName"),fontStyle96);
        helpLabel = new Label(langStr.get("help"),fontStyle48);

//        Gdx.input.setCatchBackKey(true);
        logoLabel();
        helpLabel();
        newGameButton();
        resumeButton();
        helpButton();
        quitButton();
        okHelpButton();

    }

    private void logoLabel(){
        logoLabel.setSize(stage.getWidth(),120);
        logoLabel.setPosition(cam.position.x - logoLabel.getWidth() /2 , cam.position.y + 250);
        logoLabel.setAlignment(Align.center);
        logoLabel.setFontScale(1.5f);
        stage.addActor(logoLabel);
    }
    private void helpLabel(){
        helpLabel.setSize(980,800);
        helpLabel.setWrap(true);
        helpLabel.setPosition(-1030, cam.position.y - 150);
        helpLabel.setAlignment(Align.topLeft);
        stage.addActor(helpLabel);
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
    private void okHelpButton(){
        okHelpButton.setSize(400,150);
        okHelpButton.setPosition(cam.position.x - okHelpButton.getWidth() / 2 - 1080, cam.position.y - 700);
        okHelpButton.getLabel().setFontScale(1.5f);
        okHelpButton.addListener(new InputListener(){
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
        stage.addActor(okHelpButton);
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
                gsm.set(new SettingState(gsm));
                break;
            case 2:
                break;
            case 4:
                if(helpSlide>1080 && helpOpened == 1){
                    action = 0;
                    helpOpened = -1;
                }else if (helpSlide <= 0 && helpOpened == -1){
                    action = 0;
                    helpOpened = 1;
                }else {
                    cam.position.x -= 12 * helpOpened;
                    helpSlide += 12 * helpOpened;
                }
                break;
            case 3:
                Gdx.app.exit();
                System.exit(0);
                break;
            default:
                break;
        }

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();

        sb.draw(woodTexture, -1080, 0 , 2160, worldHalfHeight*2);
        sb.draw(logoImage, 540 - logoImage.getWidth() , cam.position.y + 400, 288, 288);
        sb.end();

        shapeRenderer.setProjectionMatrix(cam.combined);
        shapeRenderer.setColor(0.8f, 0.86f, 0.89f, 1);
        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.rect(-1060, cam.position.y - 470, 1010,1140);
        shapeRenderer.end();

//        helpLabel.setSize(980,800);
//        helpLabel.setWrap(true);
//        helpLabel.setPosition(-1030, cam.position.y - 150);

        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        segoe96Texture.dispose();
        segoe96Font.dispose();
        segoe48Texture.dispose();
        segoe48Font.dispose();
        logoImage.dispose();
    }
}
