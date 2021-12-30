package com.shyroka.game;
import com.shyroka.engine.ImageParse;
import com.shyroka.engine.Renderer;
import com.shyroka.engine.CoreGame;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Arrays;

public class Devil extends GameObject {
    private int tileX, tileY;
    private float offX, offY;
    private float normal_speed = 0.5f;
    //private float sprinting_speed = 4;
    private float gravity = 2;
    private float fallDistance = 0;
    private float jump = -2;
    //animation files
    float temp;
    float temp_run;
    float temp_attack;
    private ImageParse devil_default_right;
    private ImageParse devil_default_left;
    private ImageParse devil_speak_right;
    private ImageParse devil_speak_left;

    private boolean[] menu_list = new boolean[2];
    //states
    private int direction;
    private int[] states;

    public Devil(int posX, int posY) {

        //decisions
        menu_list[1] = true;

        this.health = 70;
        this.tag = "devil";
        this.posX = posX * GameManager.TileSize;
        this.posY = posY * GameManager.TileSize;
        this.tileX = posX;
        this.tileY = posY;
        this.offX = 0;
        this.offY = 0;
        this.width = GameManager.TileSize;
        this.height = GameManager.TileSize;

        //animation files
        this.devil_default_right = new ImageParse("/Assets/devil_idle.png", 55, 67);
        this.devil_default_left = new ImageParse("/Assets/devil_idle.png", 55, 67);

        this.devil_speak_left = new ImageParse("/Assets/devil_talking.png",64 , 64);
        this.devil_speak_right = new ImageParse("/Assets/devil_talking.png", 64, 64);

        //enemy states
        this.direction = 1;
        this.temp = 0;
        this.temp_run = 0;
        this.temp_attack = 0;
        this.states = new int[4];
        Arrays.fill(this.states, 0);
        states[0] = 1;
        //this.health = 10;
    }

    @Override
    public void update(CoreGame core, GameManager gm, float dt) {

        if(isTalking && (gm.getObjects().get(0).posX < posX + 10 && gm.getObjects().get(0).posX > posX - 10 && gm.getObjects().get(0).posY < posY + 40 && gm.getObjects().get(0).posY > posY - 40)) {
            states[0] = 0;
            states[1] = 1;
        }
        else
        {
            states[0] = 1;
            states[1] = 0;
        }




        //this dude does not die, he is only here for level progression

        if (states[0] == 1)
        {
            temp += dt * 5;
            if (temp > 5)
                temp = 0;
        }

        if (states[1] == 1)
        {
            temp += dt * 2;
            if (temp > 4)
                temp = 0;
        }

        fallDistance += dt * gravity;
        offY += fallDistance;

        //vertical axis movement
        fallDistance += dt * gravity;
        offY += fallDistance;
        if (fallDistance < 0)
        {
            if ((gm.getCollision(tileX, tileY - 1) >= 1 || gm.getCollision(tileX + (int) Math.signum(offX), tileY - 1) >= 1) && offY >= 0) {
                fallDistance = 0;
                offY = 0;
            }
        }
        if (fallDistance > 0) {
            if ((gm.getCollision(tileX, tileY + 1) >= 1 || gm.getCollision(tileX + (int) Math.signum(offX), tileY + 1) >= 1) && offY >= 0) {
                isFalling = false;
                fallDistance = 0;
                offY = 0;
            }
        }
        //position correction
        if (offY > 0) {
            tileY++;
            offY -= GameManager.TileSize;

        }
        if (offY < 0) {
            tileY--;
            offY += GameManager.TileSize;
        }
        //horizontal
        if (offX > 0) {
            tileX++;
            offX -= GameManager.TileSize;
        }
        if (offX < 0) {
            tileX--;
            offX += GameManager.TileSize;

        }
        if (isFalling) {
            states[0] = 0;
            states[3] = 1;
        } else
            states[3] = 0;
        posX = (int) (tileX * GameManager.TileSize + offX);
        posY = (int) (tileY * GameManager.TileSize + offY);
        if (states[1] == 0 && states[2] == 0 && states[3] == 0) states[0] = 1;

    }
    @Override
    public void render(CoreGame core, GameManager gm, Renderer r) {

        if (this.states[0] == 1 ) {
            if (direction == 1)
                r.displayImageParse(devil_default_right, (int) posX - 10, (int) posY -50, (int) temp, 0);
            else
                r.displayImageParse(devil_default_left, (int) posX - 10, (int) posY - 50, (int) temp, 0);
        }
        if (this.states[3] == 1) {
            if (direction == 1)
                r.displayImageParse(devil_default_right, (int) posX - 10, (int) posY - 50, 0, 0);
            else
                r.displayImageParse(devil_default_left, (int) posX - 10, (int) posY - 50, 0, 0);
        }
        if (this.states[1] == 1)
        {
            r.displayImageParse(devil_speak_right, (int) posX - 10, (int) posY - 50, (int) temp, 0);
            r.drawRectangleShape(gm.getObjects().get(0).posX - 150, gm.getObjects().get(0).posY + 100, 300, 50, new Color(0, 0, 0).getRGB());
            r.drawText("You have completed this dungeon!", gm.getObjects().get(0).posX - 140, gm.getObjects().get(0).posY + 110, new Color(255, 255, 255).getRGB());
            r.drawText("Proceed to the next floor?", gm.getObjects().get(0).posX - 140, gm.getObjects().get(0).posY + 120, new Color(255, 255, 255).getRGB());


            if(core.getInput().isKey(KeyEvent.VK_LEFT))
            {
                menu_list[0] = true;
                menu_list[1] = false;
            }

            if(core.getInput().isKey(KeyEvent.VK_RIGHT))
            {
                menu_list[1] = true;
                menu_list[0] = false;
            }

            if(menu_list[0]) {
                r.drawText(">yes<", gm.getObjects().get(0).posX - 130, gm.getObjects().get(0).posY + 130, new Color(255, 0, 0).getRGB());
                if (core.getInput().isKey(KeyEvent.VK_ENTER)) {

                    System.out.println("level increased");

                    if (gm.saveLevel == 2 || gm.overload_save == 2) {
                        gm.saveLevel = 3;
                        gm.overload_save = 3;
                        menu_list[0] = false;
                    }

                    if (gm.saveLevel == 1 || gm.overload_save == 1)
                    {
                        gm.overload_save = 2;
                        gm.saveLevel = 2;
                        menu_list[0] = false;
                    }
                    isTalking = false;
                }
            }
             else
                r.drawText(" yes ", gm.getObjects().get(0).posX - 130, gm.getObjects().get(0).posY + 130, new Color(255, 255, 255).getRGB());

            if(menu_list[1]) {
                r.drawText(">no<", gm.getObjects().get(0).posX - 100, gm.getObjects().get(0).posY + 130, new Color(255, 0, 0).getRGB());
                if (core.getInput().isKey(KeyEvent.VK_ENTER))
                    isTalking = false;
            }
            else
                r.drawText(" no ", gm.getObjects().get(0).posX - 100, gm.getObjects().get(0).posY + 130, new Color(255, 255, 255).getRGB());

        }

    }
}