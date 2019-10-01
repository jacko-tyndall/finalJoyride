package com.tyndallbowen.dragonjoyride;

import com.badlogic.gdx.utils.Pool;

public class ObjectPool extends Pool<Movers> {
    @Override
    protected Movers newObject() {
        return new Movers(0, 0, 50, 50, "coin.png", ObType.coin);
    }
}
