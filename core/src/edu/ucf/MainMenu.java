package edu.ucf;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class MainMenu implements Screen {
    SpriteBatch batch;
    GameController myGame;
    OrthographicCamera camera;
//    Music music;
    //Sprite background;
    Sprite buttons;
    Sprite mainMenuDrag;
    Sprite startButton;
    Sprite leaderButton;
    Sprite storeButton;
    Rectangle startGame;
    Rectangle leaderBoard;
    Rectangle store;
    MovingBackground bg;
    Music menuMusic;

    public MainMenu(GameController g)
    {
        myGame = g;

    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 414, 736);
       // background = new Sprite( new Texture("clouds.jpg"));
        buttons = new Sprite(new Texture("DragonJoyridePic.png"));
        buttons.setSize(200,400);
        buttons.setPosition(107,200);
        mainMenuDrag = new Sprite(new Texture("mainMenuDragFont.png"));
        mainMenuDrag.setSize(400,150);
        mainMenuDrag.setPosition(10,550);
        startButton = new Sprite(new Texture("startButton.png"));
        startButton.setSize(200,150);
        startButton.setPosition(110,350);
        startGame = new Rectangle(110,350,200,150);
        leaderButton = new Sprite(new Texture("leaderboardButton.png"));
        leaderButton.setSize(200,150);
        leaderButton.setPosition(110,200);
        leaderBoard = new Rectangle(110,200,200,150);
        storeButton = new Sprite(new Texture("storeButton.png"));
        storeButton.setSize(200,150);
        storeButton.setPosition(110,50);
        store = new Rectangle(110,50,200,150);
       // background.setSize(414,736);
        this.bg = new MovingBackground(100.0F);

        menuMusic = Gdx.audio.newMusic(Gdx.files.internal("menuMusic.mp3"));
       menuMusic.setLooping(true);
       menuMusic.play();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        this.bg.update(Gdx.graphics.getDeltaTime());
        batch.begin();
        bg.draw(batch);
        mainMenuDrag.draw(batch);
        startButton.draw(batch);
        leaderButton.draw(batch);
        storeButton.draw(batch);
       // background.draw(batch);
      //  buttons.draw(batch);

        batch.end();
        if(Gdx.input.justTouched()){
            Vector3 touched = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touched);
            Rectangle temp = new Rectangle(touched.x, touched.y, 10, 10);
            if(temp.overlaps(startGame)){
                myGame.setScreen(new MyGdxGame(myGame));
                menuMusic.stop();
            }else if(temp.overlaps(leaderBoard)){
                // send to leaderboard
            }else if(temp.overlaps(store)){
                //send to store
            }
//            music.stop();

        }

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
    public void draw(SpriteBatch b){
        //background.draw(b);
    }
}
