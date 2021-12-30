package com.shyroka.game;

import com.shyroka.engine.CoreGame;

public class Camera {
    //position information
    private float offX,offY;


    //target information
    private String targettag;
    private GameObject target = null;

    //constructor bascially gives the target that will be followed, in this case the player

    public Camera(String tag)
    {
        this.targettag = tag;
    }


    public void update(CoreGame core, GameManager gm, float dt)
    {
        if(target == null)
            target = gm.getObject(targettag);

        if(target == null)
            return;


        offX = (target.getPosX() + target.getWidth()/2 )- core.getWidth() /2;
        offY = (target.getPosY() + target.getHeight()/2 )- core.getHeight() /2;
        core.getRenderer().setCamX((int) offX);
        core.getRenderer().setCamY((int) offY);
    }

    public float getOffX() {
        return offX;
    }

    public void setOffX(float offX) {
        this.offX = offX;
    }

    public float getOffY() {
        return offY;
    }

    public void setOffY(float offY) {
        this.offY = offY;
    }

    public String getTargettag() {
        return targettag;
    }

    public void setTargettag(String targettag) {
        this.targettag = targettag;
    }

    public GameObject getTarget() {
        return target;
    }

    public void setTarget(GameObject target) {
        this.target = target;
    }
}
