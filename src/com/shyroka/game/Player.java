package com.shyroka.game;
import com.shyroka.engine.Image;
import com.shyroka.engine.ImageParse;
import com.shyroka.engine.Renderer;
import com.shyroka.engine.CoreGame;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import com.shyroka.sql_save.*;

public class Player extends GameObject {

    private int tileX, tileY;
    private float offX, offY;
    private float normal_speed = 2;
    private float sprinting_speed = 4;

    private float gravity = 2;
    private float fallDistance = 0;
    private float jump = -2;

    //animation files
    float temp;
    float temp_run;
    float temp_attack;
    private ImageParse player_default_right;
    private ImageParse player_default_left;

    private ImageParse player_run_right;
    private ImageParse player_run_left;

    private ImageParse player_fall_right;
    private ImageParse player_fall_left;

    private ImageParse player_attack_right;
    private ImageParse player_attack_left;

    //player states
    private int direction;
    private int[] states;


    public Player(int posX, int posY , int current_score,int health) {
        this.score = current_score;
        this.health = health;
        this.tag = "player";
        this.posX = posX * GameManager.TileSize;
        this.posY = posY * GameManager.TileSize;
        this.tileX = posX;
        this.tileY = posY;
        this.offX = 0;
        this.offY = 0;
        this.width = GameManager.TileSize;
        this.height = GameManager.TileSize;

        //animation files

        this.player_default_right = new ImageParse("/Assets/player_right.png", 25, 32);
        this.player_default_left = new ImageParse("/Assets/player_left.png", 25, 32);

        this.player_run_right = new ImageParse("/Assets/player_run_right.png", 44, 32);
        this.player_run_left = new ImageParse("/Assets/player_run_left.png", 44, 32);

        this.player_fall_right = new ImageParse("/Assets/player_fall_right.png", 44, 32);
        this.player_fall_left = new ImageParse("/Assets/player_fall_left.png", 44, 32);

        this.player_fall_left = new ImageParse("/Assets/player_fall_left.png", 44, 32);

        this.player_attack_right = new ImageParse("/Assets/player_attack_right.png", 64, 32);
        this.player_attack_left = new ImageParse("/Assets/player_attack_left.png", 64, 32);

        //player states
        this.direction = 1;
        this.temp = 0;
        this.temp_run = 0;
        this.temp_attack = 0;
        this.states = new int[6];
        Arrays.fill(this.states, 0);
        states[0] = 1;

        //this.health = 10;
    }

    @Override
    public void update(CoreGame core, GameManager gm, float dt) {


        //basically player movement is here
        //temporary collision values will be shown in debug mode

        //some damage dealing, not the most efficient thing ever since it has to do a check for all entity positions(might break stuff if there are many)

        if(this.health <= 0)
            this.isDeadPlayer = true;

        for (int i = 1; i < gm.getObjects().size(); i++)
        {
            if (gm.getObjects().get(i).posX < posX + 20 && gm.getObjects().get(i).posX > posX - 20 && gm.getObjects().get(i).posY < posY + 30 && gm.getObjects().get(i).posY > posY - 30)
            {
                if (core.getInput().isKey(KeyEvent.VK_F) && gm.getObjects().get(i).tag == "devil") {
                    gm.getObjects().get(i).isTalking = true;
                    states[5] = 1;
                }

                if (core.getInput().isKey(KeyEvent.VK_SPACE) && gm.getObjects().get(i).tag != "devil") {
                    states[0] = 0;
                    if (states[1] == 1)
                        states[1] = 0;
                    if (states[2] == 1)
                        states[2] = 0;

                    states[4] = 1;
                    if (temp_attack > 3 && temp_attack < 4)
                        gm.getObjects().get(i).health -= 5;

                    if (gm.getObjects().get(i).health <= 0)
                    {

                        if(gm.getObjects().get(i).tag.equals("wolf"))
                            score += 10;
                        if(gm.getObjects().get(i).tag.equals("knight"))
                            score += 20;

                        health += 30;
                    }
                } else {
                    states[0] = 1;
                    states[4] = 0;

                }
            }
            else if(gm.getObjects().get(i).tag == "devil")
                gm.getObjects().get(i).isTalking = false;

        }



        //just the talking state, no animation exists for this

        if (states[5] == 1 && core.getInput().isKey(KeyEvent.VK_SPACE) && !isFalling) {
            gm.getObjects().get(1).isTalking = false;
            states[5] = 0;
        }
        if (core.getInput().isKey(KeyEvent.VK_SPACE))
        {
            states[0] = 0;
            if (states[1] == 1)
                states[1] = 0;
            if (states[2] == 1)
                states[2] = 0;

            states[4] = 1;
        } else {
            states[0] = 1;
            states[4] = 0;
        }


        if (states[0] == 1) {
            temp += dt * 5;
            if (temp > 3)
                temp = 0;
        }
        if (states[1] == 1 || states[2] == 1) {
            temp_run += dt * 10;
            if (temp_run > 11)
                temp_run = 0;
        }

        if (states[4] == 1) {
            temp_attack += dt * 10;
            if (temp_attack > 6)
                temp_attack = 0;
        }


        fallDistance += dt * gravity;
        offY += fallDistance;

        //horizontal axis movement

        if (core.getInput().isKey(KeyEvent.VK_A) && states[4] == 0 ) {
            states[0] = 0;
            states[2] = 1;
            direction = -1;
            if (gm.getCollision(tileX - 1, tileY) >= 1 || gm.getCollision(tileX - 1, tileY + (int) Math.signum((int) offY)) >= 1) {
                if (offX > 0)
                    offX -= normal_speed;
            } else {
                offX -= normal_speed;
            }
        } else {
            states[2] = 0;
            states[0] = 1;
        }

        if (core.getInput().isKey(KeyEvent.VK_D) && states[4] == 0 ) {
            states[1] = 1;
            states[0] = 0;
            direction = 1;
            if (gm.getCollision(tileX + 1, tileY) >= 1 || gm.getCollision(tileX + 1, tileY + (int) Math.signum((int) offY)) >= 1) {
                if (offX < 0)
                    offX += normal_speed;
            } else {
                offX += normal_speed;
            }
        } else {
            states[1] = 0;
        }
        //vertical axis movement
        fallDistance += dt * gravity;
        if (core.getInput().isKeyDown(KeyEvent.VK_W) && !isFalling) {
            //System.out.println("jump triggered!");
            fallDistance = jump;
            isFalling = true;
        }

        offY += fallDistance;
        if (fallDistance < 0) {
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
        //vertical
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

        if (states[1] == 0 && states[2] == 0 && states[3] == 0)
            states[0] = 1;

    }
    @Override
    public void render(CoreGame core, GameManager gm, Renderer r )
    {
        //health displayer
        gm.finalscore = score;
        r.drawText("Health:", this.posX - 180, this.posY - 140, new Color(255, 255, 255).getRGB());
        r.drawText("Score:" + score, this.posX - 180, this.posY - 130, new Color(255, 255, 255).getRGB());
        r.drawText("fps:" + core.getFps(), this.posX + 180, this.posY - 140, new Color(255, 255, 255).getRGB());
        int spacer = 5;

        for (int i = 0; i < this.health; i = i + 10) {

            r.drawText("0", this.posX - 150 + spacer, this.posY - 140, new Color(255, 0, 0).getRGB());
            spacer += 5;
        }

        //only shows up in debug room for obvious purposes
        if(gm.Menu.menuList[3]) {
            r.drawText("X:" + this.posX + " Y:" + this.posY + " tile X:" + this.tileX + "  tile Y:" + this.tileY, this.posX - 100, this.posY - 50, new Color(100, 0, 0).getRGB());
            if (gm.getCollision(this.tileX, this.tileY - 1) >= 1)
                r.drawText("collision above", this.posX - 100, this.posY - 40, new Color(100, 0, 0).getRGB());

            if (gm.getCollision(this.tileX, this.tileY + 1) >= 1)
                r.drawText("collision bellow", this.posX - 100, this.posY - 40, new Color(100, 0, 0).getRGB());

            if (gm.getCollision(this.tileX + 1, this.tileY) >= 1)
                r.drawText("collision right", this.posX - 20, this.posY - 40, new Color(100, 0, 0).getRGB());

            if (gm.getCollision(this.tileX - 1, this.tileY) >= 1)
                r.drawText("collision left", this.posX - 20, this.posY - 40, new Color(100, 0, 0).getRGB());

            r.drawText(states[0] + " " + states[1] + " " + states[2] + " " + states[3], this.posX - 100, this.posY - 30, new Color(100, 0, 0).getRGB());

            r.drawText("is falling: " + Boolean.toString(this.isFalling), this.posX - 100, this.posY - 20, new Color(100, 0, 0).getRGB());
            //r.drawRectangleShape(posX, posY, width, height, new Color(0, 100, 0).getRGB());
        }


        if (this.states[0] == 1 && states[4] == 0 && states[3] == 0 || (states[1] == 1 && states[2] == 1))
            if (direction == 1)
                r.displayImageParse(player_default_right, (int) posX - 10, (int) posY - 15, (int) temp, 0);
            else
                r.displayImageParse(player_default_left, (int) posX - 10, (int) posY - 15, (int) temp, 0);

        if (this.states[3] == 1) {
            if (direction == 1)
                r.displayImageParse(player_fall_right, (int) posX - 10, (int) posY - 15, 0, 0);
            else
                r.displayImageParse(player_fall_left, (int) posX - 10, (int) posY - 15, 0, 0);
        }

        if (this.states[1] == 1 && states[3] == 0 && states[2] != 1)
            r.displayImageParse(player_run_right, (int) posX - 10, (int) posY - 15, (int) temp_run, 0);
        if (this.states[2] == 1 && states[3] == 0 && states[1] != 1)
            r.displayImageParse(player_run_left, (int) posX - 10, (int) posY - 15, (int) temp_run, 0);


        if (this.states[4] == 1 && direction == 1)
            r.displayImageParse(player_attack_right, (int) posX - 20, (int) posY - 15, (int) temp_attack, 0);
        if (this.states[4] == 1 && direction == -1)
            r.displayImageParse(player_attack_left, (int) posX - 20, (int) posY - 15, (int) temp_attack, 0);

    }
}
