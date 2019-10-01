package com.tyndallbowen.dragonjoyride;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Pool;

class ParticlePool extends Pool<ParticleEffect> {
    TextureAtlas particleAtlas;

    protected ParticleEffect newObject() {
        ParticleEffect temp = new ParticleEffect();
        temp.load(Gdx.files.internal("breathe.p"), Gdx.files.internal(""));
        return temp;
    }
}

class MovingPool extends Pool<Movers> {
    protected Movers newObject() { return new Movers(0, 0, 0, 0, "", ObType.regular); }
}