package com.tyndallbowen.dragonjoyride;

import com.badlogic.gdx.Game;

public class GameController extends Game {

    @Override
    public void create() {
        setScreen(new StoreMenu(this));
    }

}

