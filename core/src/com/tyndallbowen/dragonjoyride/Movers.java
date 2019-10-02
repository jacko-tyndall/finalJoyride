package com.tyndallbowen.dragonjoyride;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

enum ObType {
    oneWay, falling, regular, wall, moving, coin, fire, rock, powerUp;
}

public class Movers
{
    Sprite sprite;
    public Rectangle hitbox;
    public ObType type;

    public Movers(int x, int y, int width, int height, String img, ObType ot) {
        this.sprite = new Sprite(new Texture(img));
        this.hitbox = new Rectangle(x, y, width, height);
        this.sprite.setPosition(x, y);
        this.sprite.setSize(width, height);
        this.type = ot;
    }


    public void draw(SpriteBatch b) { this.sprite.draw(b); }


    public void update(float push) {
        this.hitbox.x-=push;
        this.sprite.setPosition(this.hitbox.x, this.hitbox.y);

        if (this.type == ObType.falling) {
            this.hitbox.y -= 9.0F;
        }

        if (this.type == ObType.moving)
            this.hitbox.x -= 9.0F;
    }

    public ObType getobType(){
        return type;
    }
}