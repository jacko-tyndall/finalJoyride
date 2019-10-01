package com.tyndallbowen.dragonjoyride;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Dragon
{
    Sprite sprite;
    public Rectangle hitbox;
    int width;
    int height;
    public float countDown;
    float velocity;
    float time;
    public boolean isFiring;
    public float breathTime;
    int fireCharge;
    public Texture downText;
    public Texture upText;

    public Dragon(int x, int y) {
        pickDragon();
        this.width = 105;
        this.height = 55;
        this.countDown = 0.0F;
        this.time = 0.0F;
        this.isFiring = false;
        this.breathTime = 0.0F;
        this.fireCharge = 5;
        this.sprite = new Sprite(downText);
        this.hitbox = new Rectangle(x, y, this.width, this.height);
        this.sprite.setPosition(x, y);
        this.sprite.setSize(this.width, this.height);
    }


    public void draw(SpriteBatch b) {
        this.sprite.draw(b);
    }

    public void update(float deltaTime) {
        this.countDown -= deltaTime;
        this.time += deltaTime;
        this.velocity += -1000.0F * deltaTime;
        this.breathTime += deltaTime;


        this.hitbox.y += this.velocity * deltaTime;

        this.sprite.setPosition(this.hitbox.x, this.hitbox.y);

        if (this.time > 0.2D) {
            if (this.sprite.getTexture() == downText) {
                this.sprite.setTexture(upText);
            } else {
                this.sprite.setTexture(downText);
            }
            this.time = 0.0F;
        }
    }



    public void jump() {
        this.velocity = 500.0F;
    }


    public Rectangle getBreathBox(float deltaTime) {
        float width = 200.0F * this.breathTime;
        Rectangle rect = new Rectangle(this.hitbox.x + this.hitbox.width, this.hitbox.y, width * 2.0F, this.hitbox.height);
        if (this.countDown < 0.0F) {
            rect = new Rectangle(-1000.0F, -1000.0F, 1.0F, 1.0F);
        }

        return rect;
    }

    public void pickDragon() {
        Preferences prefs = Gdx.app.getPreferences("myprefs");

        int val = prefs.getInteger("currentDragon");
        System.out.println(val);

        if (val == 1) {
            System.out.println("hre");
            downText = new Texture("ogDragDown.png");
            upText = new Texture("ogDragUp.png");
        }

        if (val == 2) {
            System.out.println("Na hare");
            downText = new Texture("dragon.png");
            upText = new Texture("dragon.png");
        }
    }
}