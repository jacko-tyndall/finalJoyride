package edu.ucf;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Dragon
{
    Sprite sprite;
    public Rectangle hitbox;
    public int width;
    public int height;
    public float countDown;
    float velocity;
    float time;
    public boolean isFiring;
    public float breathTime;
    public float increaseDrop;
    int fireCharge;
    public static Texture downText = new Texture("ogDragDown.png");
    public static Texture upText = new Texture("ogDragUp.png");

    public Dragon(int x, int y) {
        this.width = 105;
        this.height = 55;
        this.countDown = 0.0F;
        this.time = 0.0F;
        this.isFiring = false;
        this.breathTime = 0.0F;
        this.fireCharge = 5;
        increaseDrop = -1000.0f;
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
        this.velocity += increaseDrop * deltaTime;
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
        this.velocity = -(increaseDrop/2);
    }


    public Rectangle getBreathBox(float deltaTime) {
        float width = 200.0F * this.breathTime;
        Rectangle rect = new Rectangle(this.hitbox.x + this.hitbox.width, this.hitbox.y, width * 2.0F, this.hitbox.height);
        if (this.countDown < 0.0F) {
            rect = new Rectangle(-1000.0F, -1000.0F, 1.0F, 1.0F);
        }

        return rect;
    }
    public void setSprite(String img){
        Texture text = new Texture(img);
        sprite = new Sprite(text);
    }
}