package com.tyndallbowen.dragonjoyride;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import org.w3c.dom.css.Rect;

import java.util.ArrayList;
import java.util.Random;


public class MainGame implements Screen {
    SpriteBatch batch;
    OrthographicCamera camera;
    public static int width = 414;
    public static int height = 736;
    Random r; MovingBackground bg;
    boolean[] isBlocked; Dragon dragon;
    ArrayList<Movers> obstacles;
    float time;
    int[] changerArray;
    ArrayList<ParticleEffect> fire;
    ParticlePool pool;
    GameController myGame;
    Sprite firebar;
    ArrayList<Movers> coins;
    ArrayList<Movers> fyes;
    ArrayList<Movers> powerUps;
    float powerTime;
    float drawTime;
    float push;
    public static int scoreS;
    public static int coinsC;
    boolean invincible;
    boolean destroyAll;
    boolean newTimer;
    boolean destroyTimer;
    Sprite Everything;
    float duration;
    Sprite Coinage;
    Sprite cantdie;
    Sprite gameCoins;
    BitmapFont coinsBitmap;
    String hitCoin;
    String gameScore;
    BitmapFont scoreBitmap;
    boolean timerjustStarted;
    float coinTime;
    float collectTime;
    boolean done;
    Sprite firebutton;
    ObjectPool objectPool;
    boolean haveMoving;
    float movingTimer;
    int movingY;
    Sprite movingWarning;
    Music gameMusic;
    Sound breath;
    float powerClock;
    int fastCount;
    boolean coinage;
    ArrayList<Sprite> powerText;

    public MainGame(GameController g) {
        this.time = 0.0F;
        this.changerArray = new int[] { -1, 1 };

        this.scoreS = 0;
        this.coinsC = 0;


        this.hitCoin = "+ 5";


        this.timerjustStarted = false;
        this.coinTime = 0.0F;

        this.collectTime = 0.0F;

        this.done = false;
        this.myGame = g;

        this.movingTimer = 0.0F;
        haveMoving = false;
    }

    public void show() {
        this.batch = new SpriteBatch();
        this.r = new Random();

        this.bg = new MovingBackground(100.0F);

        this.obstacles = new ArrayList();
        fastCount = 0;
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, width, height);
        push = 5;
        this.isBlocked = new boolean[20];
        duration = 0;
        this.dragon = new Dragon(100, height / 2);
        drawTime = 0;
        this.fire = new ArrayList();
        this.pool = new ParticlePool();
        invincible = false;
        destroyAll = false;
        coinage = false;
        powerTime = 0;
        newTimer = false;
        powerClock = 0;
        Everything = new Sprite(new Texture("destroyAll.png"));
        Everything.setSize(300, 150);
        Everything.setPosition(65, 500);
        cantdie = new Sprite(new Texture("invincibleText.png"));
        cantdie.setSize(300, 150);
        cantdie.setPosition(65, 500);
        this.firebar = new Sprite(new Texture("energy.gif"));
        this.firebutton = new Sprite(new Texture("FIREBUTTON.png"));
        this.movingWarning = new Sprite(new Texture("badlogic.jpg"));

        this.coinsBitmap = new BitmapFont();
        this.coins = new ArrayList();
        this.fyes = new ArrayList();

        objectPool = new ObjectPool();

        powerUps = new ArrayList();
        powerText = new ArrayList();
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("inGameMusic.mp3"));
        gameMusic.setLooping(true);
        gameMusic.play();
        breath = Gdx.audio.newSound(Gdx.files.internal("breathSound.mp3"));
    }


    public void render(float deltaTime) {
        this.time += Gdx.graphics.getDeltaTime();
        this.collectTime += deltaTime;

        if (collectTime >= 10) {
            push += (.1 * push);
            collectTime = 0;
            dragon.increaseDrop += (.1*dragon.increaseDrop);
            System.out.println(push);
        }

        Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 1.0F);
        Gdx.gl.glClear(16384);
        this.camera.update();
        this.batch.setProjectionMatrix(this.camera.combined);
        this.batch.begin();

        this.bg.draw(this.batch);
        this.dragon.draw(this.batch);

        this.firebar.setPosition(10.0F, 10.0F);
        this.firebar.setSize((this.dragon.fireCharge * 50), 40.0F);
        this.firebar.draw(this.batch);

        this.firebutton.setPosition(340.0F, 10.0F);
        this.firebutton.setSize(60F, 60.0F);
        this.firebutton.draw(this.batch);

        for (Movers s : this.obstacles) {
            s.draw(this.batch);
        }

        for (ParticleEffect f : this.fire) {
            f.draw(this.batch);
        }

        for (Movers c : this.coins) {
            c.draw(this.batch);
        }
        for (Movers f : this.fyes) {
            f.draw(this.batch);
        }

        for (Movers p : powerUps) {
            p.draw(this.batch);
        }

        if (haveMoving && movingTimer < 2.0F) {
            this.movingWarning.setPosition(width - 60, movingY);
            this.movingWarning.setSize(60F, 60.0F);
            this.movingWarning.draw(this.batch);
        }

        this.batch.end();

        this.scoreS++;

        if (this.time > 2.0F && (coinage == false)) {
            addNewObstacle();
            this.time = 0.0F;
            this.done = false;
        }

        if ((int) this.collectTime % 2 == 0 && !this.done && coinage==false) {
            if (this.r.nextBoolean()) {
                addNewCollectable(ObType.coin);
                addNewCollectable(ObType.coin);
                addNewCollectable(ObType.coin);
            }
            addNewCollectable(ObType.fire);
            addNewCollectable(ObType.fire);
            addNewCollectable(ObType.fire);
            addNewCollectable(ObType.fire);
            addNewCollectable(ObType.fire);


            this.done = true;
        }
        if ((coinage == true)) {
            for (int i = 0; i < obstacles.size(); i++) {
                obstacles.remove(i);
                i--;
            }
            duration += Gdx.graphics.getDeltaTime();
            for (int i = 0; i < 1; i++) {
                this.coins.add(new Movers((int) (50 + Math.random() * 300), (int) (100.0D + Math.random() * 700.0D), 50, 50, "coin.png", ObType.coin));
            }
            if ((int) duration == 3) {
                coinage = false;
                duration = 0;
            }
        }
        powerTime += Gdx.graphics.getDeltaTime();
        if ((int) powerTime == 10) {
            powerUps.add(new Movers(400, ((int) (Math.random() * 700.0D)), 50, 50, "orb.png", ObType.powerUp));
            powerTime = 0;

        }
        for (int i = 0; i < powerUps.size(); i++) {
            if (dragon.hitbox.overlaps(powerUps.get(i).hitbox)) {
                powerUps.remove(powerUps.get(i));
                i--;
                int random = (int)(1+(Math.random()*3));
                if (random == 1) {
                    newTimer = true;
                    //duration += Gdx.graphics.getDeltaTime();
                    invincible = true;


                } else if (random == 2) {
                    for(int j= 0; j<obstacles.size(); j++){
                        obstacles.remove(j);
                        j--;
                    }
                    destroyTimer = true;
                    destroyAll = true;

                    //powerClock+= Gdx.graphics.getDeltaTime();
                    System.out.print("dest");


					/*if ((int)duration == 3) {
						destroyAll = false;
						duration = 0;
					}
					*/
                } else {
                    //duration += Gdx.graphics.getDeltaTime();
                    System.out.print("coin");

                    coinage = true;
					/*if ((int)duration == 3) {
						coinage = false;
						duration = 0;
					}
					*/
                }
            }
        }
        if (this.destroyAll) {
            duration += Gdx.graphics.getDeltaTime();
            if ((int) duration == 8 ) {
                for (int i = 0; i < obstacles.size(); i++) {
                    obstacles.remove(i);
                    i--;
                }
            }
            if ((int) duration == 10) {
                destroyAll = false;
                duration = 0;
                System.out.println("SKUNK APE!!!");
            }
        }
        if (this.invincible) {
            duration += Gdx.graphics.getDeltaTime();
            if ((int) duration == 8 ) {
                for (int i = 0; i < obstacles.size(); i++) {
                    obstacles.remove(i);
                    i--;
                }
            }
            if ((int) duration == 9) {
                invincible = false;
                duration = 0;
                System.out.println("squid");
            }
        }
        if (this.newTimer) {
            batch.begin();
            cantdie.draw(batch);
            batch.end();
            drawTime += Gdx.graphics.getDeltaTime();
            powerClock += Gdx.graphics.getDeltaTime();
            if ((int) (this.powerClock) >= 1) {
                this.newTimer = false;
                this.powerClock = 0;
            }
        }
        if (this.destroyTimer) {
            batch.begin();
            Everything.draw(batch);
            batch.end();
            powerClock += Gdx.graphics.getDeltaTime();
            if ((int) (this.powerClock) >= 9) {
                this.destroyTimer = false;
                this.powerClock = 0;
            }
        }

        for (int i = 0; i < this.fyes.size(); i++) {
            ((Movers) this.fyes.get(i)).update(5);
        }

        for (int i = 0; i < this.coins.size(); i++) {
            ((Movers) this.coins.get(i)).update(5);
        }
        for (int i = 0; i < this.powerUps.size(); i++) {
            ((Movers) this.powerUps.get(i)).update(5);
        }

        for (Movers s : this.obstacles) {
            if (s.getobType() == ObType.oneWay || s.getobType() == ObType.wall) {
                s.update((float) (push * 0.76));
            } else {
                s.update(push);
            }
            if (this.dragon.hitbox.overlaps(s.hitbox)&&(invincible==false)) {
//                this.myGame.setScreen(new GameOver(this.myGame, this.scoreS));
                gameMusic.stop();
                saveCoins(coinsC);
                this.myGame.setScreen(new GameOver(this.myGame));
            }
        }


        for (int i = 0; i < this.obstacles.size(); i++) {
            if (((Movers) this.obstacles.get(i)).hitbox.x == 0.0F - ((Movers) this.obstacles.get(i)).hitbox.width) {
                this.obstacles.remove(i);
                i--;
            }
        }

        for (int i = 0; i < this.coins.size(); i++) {
            if (((Movers) this.coins.get(i)).hitbox.x == 0.0F - ((Movers) this.coins.get(i)).hitbox.width) {
                objectPool.free(this.coins.get(i));
                this.coins.remove(i);
                i--;
            }
        }

        for (int i = 0; i < this.fyes.size(); i++) {
            if (((Movers) this.fyes.get(i)).hitbox.x == 0.0F - ((Movers) this.fyes.get(i)).hitbox.width) {
                objectPool.free(this.fyes.get(i));
                this.fyes.remove(i);
                i--;
            }
        }

        for (int i = 0; i < this.fyes.size(); i++) {
            if (((Movers) this.fyes.get(i)).hitbox.overlaps(this.dragon.hitbox)) {
                objectPool.free(this.fyes.get(i));
                this.fyes.remove(i);

                if (this.dragon.fireCharge < 5) {
                    this.dragon.fireCharge++;
                }
            }
        }

        if (this.dragon.isFiring) {
            this.dragon.getBreathBox(Gdx.graphics.getDeltaTime());
        }

        if (this.dragon.hitbox.y < 0.0F - this.dragon.hitbox.height&&(invincible==false)) {
//            this.myGame.setScreen(new GameOver(this.myGame, this.scoreS));
            saveCoins(coinsC);
            gameMusic.stop();
            myGame.setScreen(new GameOver(myGame));
        }

        if (this.dragon.hitbox.y > height - this.dragon.hitbox.height) {
            this.dragon.hitbox.y = height - this.dragon.hitbox.height;
        }
        if (this.dragon.hitbox.y < 0.0F - this.dragon.hitbox.height && (invincible == true)) {
            this.dragon.hitbox.y = 0 + dragon.hitbox.height;
        }


        this.bg.update(Gdx.graphics.getDeltaTime());

        for (ParticleEffect f : this.fire) {
            f.update(Gdx.graphics.getDeltaTime());
            if (f.isComplete()) {
                this.dragon.isFiring = false;
            }

            f.setPosition(this.dragon.hitbox.x + this.dragon.width - 15.0F, this.dragon.hitbox.y + (this.dragon.height / 2) + 15.0F);
        }

        this.dragon.update(Gdx.graphics.getDeltaTime());

        for (int i = 0; i < this.coins.size(); i++) {
            if (((Movers) this.coins.get(i)).hitbox.overlaps(this.dragon.hitbox)) {
                this.coinTime += Gdx.graphics.getDeltaTime();
                this.timerjustStarted = true;
                objectPool.free(this.coins.get(i));
                this.coins.remove(i);
                this.scoreS += 10;
                this.coinsC ++;
            }
        }

        if (this.coinTime < 1.0F && this.timerjustStarted) {
            this.batch.begin();
            this.coinsBitmap.setColor(1.0F, 1.0F, 1.0F, 1.0F);
            this.coinsBitmap.getData().setScale(2.0F);
            this.coinsBitmap.draw(this.batch, this.hitCoin, this.dragon.hitbox.x, this.dragon.hitbox.y);
            this.batch.end();
            this.coinTime += Gdx.graphics.getDeltaTime();
            if (this.coinTime >= 1.0F) {
                this.timerjustStarted = false;
                this.coinTime--;
            }
        }
        displayCurrScore(this.scoreS);


        for (int i = 0; i < this.obstacles.size(); i++) {
            Rectangle hitbox = this.dragon.getBreathBox(Gdx.graphics.getDeltaTime());

            if(hitbox.overlaps(((Movers) this.obstacles.get(i)).hitbox)&&(destroyAll)){
                obstacles.remove(i);
                this.dragon.isFiring = false;
                i--;
            }else if (hitbox.overlaps(((Movers) this.obstacles.get(i)).hitbox) && (
                    ((Movers) this.obstacles.get(i)).type == ObType.falling || ((Movers) this.obstacles.get(i)).type == ObType.moving || ((Movers) this.obstacles.get(i)).type == ObType.wall) || ((Movers) this.obstacles.get(i)).type == ObType.rock) {
                this.dragon.isFiring = false;
                this.obstacles.remove(i);
                i--;
            }else{
                continue;
            }
        }

        if (haveMoving) {
            movingTimer += Gdx.graphics.getDeltaTime();
            if (movingTimer >= 2.3F) {
                movingTimer = 0.0F;
                haveMoving = false;
                makeMoving();
            }
        }

        if (Gdx.input.justTouched()) {
            Vector3 touched = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touched);
            Rectangle touchBox = new Rectangle(touched.x, touched.y, 10, 10);
            if (touchBox.overlaps(firebutton.getBoundingRectangle())) {
                if (this.dragon.breathTime > 1.0F && this.dragon.fireCharge > 0) {
                    this.dragon.fireCharge--;
                    this.dragon.isFiring = true;
                    this.dragon.countDown = 1.0F;
                    this.dragon.breathTime = 0.0F;
                    ParticleEffect part = (ParticleEffect) this.pool.obtain();
                    part.setPosition(this.dragon.hitbox.x + this.dragon.width, this.dragon.hitbox.y + (this.dragon.height / 2));
                    part.start();
                    this.fire.add(part);
                    breath.play();
                }
            } else {
                this.dragon.jump();
            }
        }


        if (Gdx.input.isKeyJustPressed(62)) {
            this.dragon.jump();
        }

        if (Gdx.input.isKeyJustPressed(66) &&
                this.dragon.breathTime > 1.0F && this.dragon.fireCharge > 0) {
            this.dragon.fireCharge--;
            this.dragon.isFiring = true;
            this.dragon.countDown = 1.0F;
            this.dragon.breathTime = 0.0F;
            ParticleEffect temp = (ParticleEffect) this.pool.obtain();
            temp.setPosition(this.dragon.hitbox.x + this.dragon.width, this.dragon.hitbox.y + (this.dragon.height / 2));
            temp.start();
            this.fire.add(temp);
            breath.play();
        }


        if (Gdx.input.isKeyJustPressed(46)) {
//            this.myGame.setScreen(new GameOver(this.myGame, this.scoreS));
            saveCoins(coinsC);
            this.myGame.setScreen(new MainGame(this.myGame));
        }
    }



    public void resize(int width, int height) {}




    public void pause() {}




    public void resume() {}




    public void hide() {}



    public void displayCurrScore(int score) {
        this.gameScore = "Score: " + score;

        this.scoreBitmap = new BitmapFont();
        this.scoreBitmap.getData().setScale(2.0F);



        this.batch.begin();
        this.scoreBitmap.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        this.scoreBitmap.draw(this.batch, this.gameScore, 50.0F, (height - 25));
        this.batch.end();
    }

    public void addNewObstacle() {
        int x = this.r.nextInt(3);

        if (x == 0) {
            makeOneWay();

        } else if (x == 1) {
            makeRegularRock();

        } else {
            makeRegular();
        }

        if (this.r.nextBoolean()) {
            prepareMoving();
        }
    }



        public void makeOneWay() {
            int range = height - 250 - 250 + 1;
            int startY = this.r.nextInt(range) + 250;


            int changer = this.changerArray[(int) Math.random() * 2];
            if (startY <= height - 250 && startY >= height - 350) {
                changer = -1;
            }
            if (startY >= 250 && startY <= 350) {
                changer = 1;
            }
            int endY = startY + changer * 250;

            if (changer == -1) {
                Movers box1 = new Movers(width + 100, startY, 100, height - startY, "darkStoneStack.jpg", ObType.oneWay);
                Movers box2 = new Movers(width + 100, 0, 100, endY, "darkStoneStack.jpg", ObType.oneWay);
                this.obstacles.add(createObject(width + 100, startY, 100, height - startY, "darkStoneStack.jpg", ObType.oneWay));
                this.obstacles.add(createObject(width + 100, 0, 100, endY, "darkStoneStack.jpg", ObType.oneWay));
                makeWall((int) box2.hitbox.x, (int) box2.hitbox.y + (int) box2.hitbox.height, (int) box1.hitbox.y, (int) box2.hitbox.width);
            }

            if (changer == 1) {
                Movers box1 = new Movers(width + 100, endY, 100, height - startY, "darkStoneStack.jpg", ObType.oneWay);
                Movers box2 = new Movers(width + 100, 0, 100, startY, "darkStoneStack.jpg", ObType.oneWay);
                this.obstacles.add(createObject(width + 100, endY, 100, height - startY, "darkStoneStack.jpg", ObType.oneWay));
                this.obstacles.add(createObject(width + 100, 0, 100, startY, "darkStoneStack.jpg", ObType.oneWay));
                makeWall((int) box1.hitbox.x, (int) box1.hitbox.y + (int) box1.hitbox.height, (int) box2.hitbox.y, (int) box1.hitbox.width);
            }
        }




        public void makeFalling() {
//            Movers falling = new Movers(width + 50, height + 50, 50, 50, "meteorite-clipart-31.gif", ObType.falling);
            this.obstacles.add(createObject(width + 50, height + 50, 50, 50, "meteorite-clipart-31.gif", ObType.falling));
        }

        public void makeRegular() {
            int size = 2;

            ArrayList<Movers> platforms = new ArrayList<Movers>();

            int r1 = this.r.nextInt(height - 60);
            Movers platform = new Movers(width + 100, r1, 100, 50, "pillar.png", ObType.regular);
            platforms.add(platform);

            while (platforms.size() < size) {
                int random = this.r.nextInt(height - 60);

                for (int i = 0; i < platforms.size(); i++) {
                    if (Math.abs(random - ((Movers) platforms.get(i)).hitbox.y) >= 300.0F) {
                        if (i == platforms.size() - 1) {
                            Movers p = new Movers(width + 100, random, 100, 50, "pillar.png", ObType.regular);
                            platforms.add(p);
                            break;
                        } else {
                            continue;
                        }
                    } else {
                        break;
                    }
                }
            }

            for (Movers plat : platforms) {
                this.obstacles.add(plat);
            }

        }


        public void makeRegularRock() {
            int size = 2;

            ArrayList<Movers> platforms = new ArrayList<Movers>();

            int r1 = this.r.nextInt(height - 60);
            Movers platform = new Movers(width + 100, r1, 50, 50, "meteorite-clipart-31.gif", ObType.rock);
            platforms.add(platform);

            while (platforms.size() < size) {
                int random = this.r.nextInt(height - 60);

                for (int i = 0; i < platforms.size(); i++) {
                    if (Math.abs(random - ((Movers) platforms.get(i)).hitbox.y) >= 300.0F) {
                        if (i == platforms.size() - 1) {
                            Movers p = new Movers(width + 100, random, 50, 50, "meteorite-clipart-31.gif", ObType.rock);
                            platforms.add(p);
                            break;
                        } else {
                            continue;
                        }
                    } else {
                        break;
                    }
                }
            }

            for (Movers plat : platforms) {
                this.obstacles.add(plat);
            }

        }


        public void makeWall(int x, int y1, int y2, int w) {
            int height = Math.abs(y1 - y2);
//            Movers wall = new Movers(x, y1, w, height, "wall.png", ObType.wall);
//            this.obstacles.add(wall);
            this.obstacles.add(createObject(x, y1, w, height, "wall.png", ObType.wall));
        }

        public void prepareMoving() {
            if (haveMoving == false) {
                haveMoving = true;
                int range = height - 50 - 50 + 1;
                movingY = this.r.nextInt(range) + 50;
            }
        }

        public void makeMoving() {
//            Movers moving = new Movers(width + 50, movingY, 50, 50, "meteorite-clipart-31.gif", ObType.moving);
//            this.obstacles.add(moving);
            this.obstacles.add(createObject(width + 50, movingY, 50, 50, "meteorite-clipart-31.gif", ObType.moving));
        }

        public Movers createObject(int x, int y, int width, int height, String img, ObType ot) {
            Movers object = objectPool.obtain();
            object.sprite = new Sprite(new Texture(img));
            object.sprite.setPosition(x, y);
            object.sprite.setSize(width, height);
            object.hitbox = new Rectangle(x, y, width, height);
            object.type = ot;
            return object;
        }

        public void addNewCollectable(ObType obType) {
            boolean made = false;
            while (made == false) {
                int randomY = (int) (Math.random() * 700.0D);
                for (Movers fire : fyes) {
                    Rectangle randomrect = new Rectangle(fire.hitbox.x, randomY, 50, 50);
                    if (randomrect.overlaps(fire.hitbox)) {
                        break;
                    }
                }
                for (Movers coin : coins) {
                    Rectangle randomrect = new Rectangle(coin.hitbox.x, randomY, 50, 50);
                    if (randomrect.overlaps(coin.hitbox)) {
                        break;
                    }
                }
                if (obType == ObType.fire) {
                    this.fyes.add(createObject('΄', randomY, 50, 50, "firepic.png", obType));
                } else if (obType == ObType.coin) {
                    this.coins.add(createObject('΄', randomY, 50, 50, "coin.png", obType));
                }
                made = true;
            }
        }

        public void saveCoins(int coins) {
            //This will get your preferences from storage
            Preferences prefs = Gdx.app.getPreferences("myprefs");

            //If the preference key is empty, create it by putting a value into it
            if(!prefs.contains("coins")) {
                prefs.putInteger("coins", 0);
            }

            //Get value from a preference key "key" (must not be empty)
            int val = prefs.getInteger("coins");

            //Do something with your value and put it back to the preference
            val+=coins;
            prefs.putInteger("coins", val);

            //This will finally save the changes to storage
            prefs.flush();
        }
    public static int getScore(){
        return scoreS;
    }
    public static int getCoins(){
        return coinsC;
    }




    public void dispose() { this.batch.dispose(); }
}
