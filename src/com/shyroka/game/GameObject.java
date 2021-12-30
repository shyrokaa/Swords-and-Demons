package com.shyroka.game;

import com.shyroka.engine.CoreGame;
import com.shyroka.engine.Renderer;

public abstract class GameObject {

    protected String tag;
    protected int posX,posY;
    protected int width, height;
    protected int health;

    //object states
    protected boolean isFalling = false;
    protected boolean isDead = false;
    protected boolean isDeadPlayer = false;
    protected boolean isDeadBoss = false;
    protected boolean isTalking = false;
    public int score = 0;
    public abstract void update(CoreGame core,GameManager gm, float dt);

    public abstract void render(CoreGame core,GameManager gm, Renderer r);

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isDead()
    {
        return this.isDead;
    }

}
