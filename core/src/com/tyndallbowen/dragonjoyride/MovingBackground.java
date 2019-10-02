package com.tyndallbowen.dragonjoyride;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;


public class MovingBackground
{
    public static Texture img;
    public static Sprite background;
    public static Texture img2;
    public static Sprite background2;
    Vector2 position;
    Vector2 position2;
    Vector2 velocity;
    Vector2 velocity2;

    public MovingBackground(float push) {
        img = new Texture("clouds1.jpg");
        background = new Sprite(img);
        background.setPosition(0.0F, 0.0F);
        background.setSize((MainGame.width + 20), MainGame.height);

        img2 = new Texture("clouds2.jpg");
        background2 = new Sprite(img2);
        background2.setPosition(MainGame.width, 0.0F);
        background2.setSize((MainGame.width + 20), (MainGame.height + 20));

        this.position = new Vector2(0.0F, 0.0F);
        this.position2 = new Vector2(MainGame.width, 0.0F);
        this.velocity = new Vector2(push, 0.0F);
        this.velocity2 = new Vector2(push, 0.0F);
    }

    public void update(float deltaTime) {
        this.position.x -= this.velocity.x * deltaTime;
        this.position2.x -= this.velocity.x * deltaTime;
        background.setPosition(this.position.x, this.position.y);
        background2.setPosition(this.position2.x, this.position2.y);
        if (background.getX() < -MainGame.width) {
            this.position.x = MainGame.width;
            background.setPosition(MainGame.width, 0.0F);
        }
        if (background2.getX() < -MainGame.width) {
            this.position2.x = MainGame.width;

            background2.setPosition(MainGame.width, 0.0F);
        }
    }

    public static void draw(SpriteBatch b) {
        background.draw(b);
        background2.draw(b);
    }
}