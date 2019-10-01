package com.tyndallbowen.dragonjoyride;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Json;

import java.util.Hashtable;

public class StoreMenu implements Screen {
    SpriteBatch batch;
    GameController myGame;
    OrthographicCamera camera;
    Sprite firebutton;

    public StoreMenu(GameController g)
    {
        myGame = g;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 414, 736);
        this.firebutton = new Sprite(new Texture("badlogic.jpg"));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        this.firebutton.setPosition(340.0F, 10.0F);
        this.firebutton.setSize(60F, 60.0F);
        this.firebutton.draw(this.batch);

        batch.end();

        displayCoins(getCoins());

        if(Gdx.input.justTouched()){
            Vector3 touched = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touched);
            Rectangle temp = new Rectangle(touched.x, touched.y, 10, 10);

            if (temp.overlaps(firebutton.getBoundingRectangle())) {
                dragonCheck(100, "2");
                myGame.setScreen(new MainGame(myGame));

            } else {
                dragonCheck(100, "1");
                myGame.setScreen(new MainGame(myGame));

            }

        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {

            if (coinCheck(300)) {
                saveCoins(5);
            }
        }
    }

    public void displayCoins(int coins) {
        String gameScore = "Coins: " + coins;
        BitmapFont scoreBitmap = new BitmapFont();
        scoreBitmap.getData().setScale(2.0F);
        this.batch.begin();
        scoreBitmap.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        scoreBitmap.draw(this.batch, gameScore, 50.0F, (MainGame.height - 25));
        this.batch.end();
    }

    public int getCoins() {
        Preferences prefs = Gdx.app.getPreferences("myprefs");
        if(!prefs.contains("coins")) {
            prefs.putInteger("coins", 0);
        }
        int val = prefs.getInteger("coins");
        return val;
    }

    public void saveCoins(int coins) {
        Preferences prefs = Gdx.app.getPreferences("myprefs");
        if(!prefs.contains("coins")) {
            prefs.putInteger("coins", 0);
        }
        int val = prefs.getInteger("coins");
        val-=coins;
        prefs.putInteger("coins", val);
        prefs.flush();
    }

    public boolean coinCheck(int price) {
        Preferences prefs = Gdx.app.getPreferences("myprefs");
        if(!prefs.contains("coins")) {
            prefs.putInteger("coins", 0);
        }
        int val = prefs.getInteger("coins");
        if (price > val) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isInList(String dragNum) {
        Preferences prefs = Gdx.app.getPreferences("myprefs");
        if(!prefs.contains("list")) {
            prefs.putString("list", "1");
        }
        String list = prefs.getString("list");
        if (list.contains(dragNum)) {
            return true;
        } else {
            return false;
        }
    }

    public void saveDragon(String dragNum) {
        Preferences prefs = Gdx.app.getPreferences("myprefs");
        if(!prefs.contains("currentDragon")) {
            prefs.putInteger("currentDragon", Integer.parseInt(dragNum));
        }
        int val=Integer.parseInt(dragNum);
        System.out.println(val);
        prefs.putInteger("currentDragon", val);
        prefs.flush();
    }

    public void saveToList(String dragNum) {
        Preferences prefs = Gdx.app.getPreferences("myprefs");
        if(!prefs.contains("list")) {
            prefs.putString("list", "1");
        }
        String list = prefs.getString("list");
        list += dragNum;
        prefs.putString("list", list);
        prefs.flush();
    }

    public void dragonCheck(int price, String dragNum) {
        if (isInList(dragNum)) {
            saveDragon(dragNum);
        } else {
            if (coinCheck(price)) {
                saveCoins(price);
                saveToList(dragNum);
                saveDragon(dragNum);
            }
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
}