package com.tyndallbowen.dragonjoyride;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class GameOver implements Screen {

    SpriteBatch batch;
    GameController myGame;
    OrthographicCamera camera;
    MovingBackground bg;
    Sprite score;
    String gameScore;
    BitmapFont scoreBitmap;
    Sprite coin;
    String gameCoin;
    BitmapFont coinBitmap;
    Sprite playAgain;
    Sprite menuButton;
    Rectangle again;
    Rectangle menu;
    Music gameMusic;
    public GameOver(GameController g){
        myGame = g;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 414, 736);
        this.bg = new MovingBackground(100.0F);
        score = new Sprite(new Texture("finalScore.png"));
        score.setSize(150,100);
        score.setPosition(125,550);
        coin = new Sprite(new Texture("finalCoins.png"));
        coin.setSize(150,100);
        coin.setPosition(125,350);
        playAgain = new Sprite(new Texture("againButton.png"));
        playAgain.setSize(250,200);
        playAgain.setPosition(-25,100);
        again = new Rectangle(25,100,200,150);
        menuButton = new Sprite(new Texture("menuButton.png"));
        menuButton.setSize(250,200);
        menuButton.setPosition(195,100);
        menu = new Rectangle(175,100,200,150);
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("gameOverMusic.mp3"));
        gameMusic.setLooping(true);
        gameMusic.play();
    }

    @Override
    public void render(float delta) {
        bg.update(Gdx.graphics.getDeltaTime());
        batch.begin();
        bg.draw(batch);
        score.draw(batch);
        coin.draw(batch);
        playAgain.draw(batch);
        menuButton.draw(batch);
        batch.end();
        displayScore(MainGame.getScore(), MainGame.getCoins());
        if(Gdx.input.justTouched()){
            Vector3 touched = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touched);
            Rectangle temp = new Rectangle(touched.x, touched.y, 10, 10);
            if(temp.overlaps(again)){
                myGame.setScreen(new MainGame(myGame));
                gameMusic.stop();
            }else if(temp.overlaps(menu)){
                myGame.setScreen(new MainMenu(myGame));
                gameMusic.stop();
            }
//            music.stop();

        }
    }
    public void displayScore(int score, int coins){

        gameScore = "" + score;
        gameCoin = "" + coins;

        //scoreBitmap = BitmapFactory.decodeFile(dragonFont.fnt.getAbsolutePath());
        scoreBitmap = new BitmapFont();
        scoreBitmap.getData().setScale(4);
        coinBitmap = new BitmapFont();
        coinBitmap.getData().setScale(4);


        //draw
        batch.begin();
        scoreBitmap.setColor(255, 255, 255, 1.0f);
        scoreBitmap.draw(batch, gameScore, 150, 525);
        coinBitmap.setColor(255, 255, 255, 1.0f);
        coinBitmap.draw(batch, gameCoin, 150, 325);
        batch.end();
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
        scoreBitmap.dispose();
    }
}
