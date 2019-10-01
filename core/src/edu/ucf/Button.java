package edu.ucf;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Button {
    private Sprite sprite;
    private float x;
    private float y;
    public float width = 150;
    public float height = 150;
    private Rectangle hitBox;
    public Button(float x, float y){
        sprite = new Sprite(new Texture("jumpButton.png"));
        sprite.setPosition(x,y);
        sprite.setSize(width,height);
        hitBox = new Rectangle(x,y,width,height);
    }
    public Rectangle getHitBox(){
        return hitBox;
    }
    public void draw(SpriteBatch b){
        sprite.draw(b);
    }
}
