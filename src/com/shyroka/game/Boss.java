package com.shyroka.game;
import com.shyroka.engine.ImageParse;
import com.shyroka.engine.Renderer;
import com.shyroka.engine.CoreGame;
import java.awt.*;
import java.util.Arrays;

public class Boss extends GameObject
{
    private boolean phase2 = true;
    private boolean phase3 = true;
    private boolean phase4 = true;
    private boolean phase5 = true;

    private int tileX, tileY;
    private float offX, offY;
    private float normal_speed = 0.5F;

    private float gravity = 2;
    private float fallDistance = 0;
    private float jump = -2;

    //animation files
    boolean once = true;
    float temp;
    float temp_bite;
    private ImageParse boss_default_right;
    private ImageParse boss_default_left;

    private ImageParse boss_bite_right;
    private ImageParse boss_bite_left;

    //player states
    private int direction;
    private int[] states;

    public Boss(int posX, int posY)
    {

        this.health = 300;


        this.tag = "boss";
        this.posX = posX * GameManager.TileSize;
        this.posY = posY * GameManager.TileSize;
        this.tileX = posX;
        this.tileY = posY;
        this.offX = 0;
        this.offY = 0;
        this.width = GameManager.TileSize;
        this.height = GameManager.TileSize;

        //animation files
        this.boss_default_right = new ImageParse("/Assets/boss_right.png", 106, 96);
        this.boss_default_left = new ImageParse("/Assets/boss_left.png", 106, 96);


        this.boss_bite_left = new ImageParse("/Assets/boss_attack_left.png", 120, 96);
        this.boss_bite_right = new ImageParse("/Assets/boss_attack_right.png", 120, 96);

        //enemy states
        this.direction = 1;
        this.temp = 0;
        this.temp_bite = 0;
        this.states = new int[5];
        Arrays.fill(this.states, 0);
        states[0] = 1;

        //this.health = 10;
    }

    @Override
    public void update(CoreGame core, GameManager gm, float dt) {

        if(this.health <= 0)
        {
            System.out.println("dead boss");
            this.isDead = true;
            gm.Menu.GW.isWon = true;
        }

        if (this.health < 200 && phase2)
        {
            phase2 = false;
            //gm.objects.add(new Skull(20,5));
            //gm.objects.add(new Skull(10,5));
            gm.objects.add(new Skull(15,5));
        }

        if (this.health < 150 && phase3)
        {
            phase3 = false;
            //gm.objects.add(new Skull(5,5));
            gm.objects.add(new Skull(10,5));
            gm.objects.add(new Skull(15,5));
        }

        if (this.health < 100 && phase4)
        {
            phase4 = false;
            gm.objects.add(new Skull(5,5));
            gm.objects.add(new Skull(10,5));
            gm.objects.add(new Skull(15,5));
        }

        if (this.health < 50 && phase5)
        {
            phase5 = false;
            gm.objects.add(new Skull(5,5));
            gm.objects.add(new Skull(10,5));
            gm.objects.add(new Skull(20,5));
            gm.objects.add(new Skull(17,5));
            gm.objects.add(new Skull(15,5));

        }

        //basically player movement is here
        //temporary collision values will be shown in debug mode

        if(health <= 0 )
            isDead = true;

        if (states[0] == 1 || states[1] == 1 || states[2] == 1) {
            temp += dt * 5;
            if (temp > 6)
                temp = 0;
        }

        if (states[4] == 1) {
            temp_bite += dt * 10;
            if (temp_bite > 11)
                temp_bite = 0;

        }

        fallDistance += dt * gravity;
        offY += fallDistance;

        //horizontal axis movement

        if (posX == 200) {
            direction = 1;
        }
        if (posX == 400) {
            direction = -1;
        }


        if (direction == -1 && states[4]==0 ) {
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

        if(direction == 1&& states[4]==0){
            once = false;
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

        //giving some damage to the player
        if (gm.getObjects().get(0).posX < posX + 10 && gm.getObjects().get(0).posX > posX - 10 && gm.getObjects().get(0).posY < posY + 60 && gm.getObjects().get(0).posY > posY - 60) {
            if (states[1] == 1)
                states[1] = 0;
            if (states[2] == 1)
                states[2] = 0;
            states[0] = 0;
            states[4] = 1;
            if(temp_bite > 7 && temp_bite < 8 )
                gm.getObjects().get(0).health = gm.getObjects().get(0).health - 3;
        } else
            states[4] = 0;

        //vertical axis movement
        fallDistance += dt * gravity;
        if ((gm.getCollision(this.tileX + 1, this.tileY) >= 1 || gm.getCollision(this.tileX - 1, this.tileY) >= 1) && !isFalling) {
            fallDistance = jump;
            isFalling = true;
        }
        offY += fallDistance;
        if (fallDistance < 0)
        {
            if ((gm.getCollision(tileX, tileY - 1) >= 1 || gm.getCollision(tileX + (int) Math.signum(offX), tileY - 1) >= 1) && offY >= 0) {
                fallDistance = 0;
                offY = 0;
            }
        }
        if (fallDistance > 0)
        {
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
        //r.drawText("Health:" + this.health,0,10,new Color(100,0,0).getRGB());
        //debug mode

        //r.drawText("Health:" ,this.posX ,this.posY - 20,new Color(255,255,255).getRGB());
        int spacer = 0;

        //r.drawText("posx " + posX + " posY "+ posY, this.posX + 20 + spacer, this.posY - 80, new Color(0, 0, 255).getRGB());

        for (int i = 0; i < this.health; i = i + 10) {
            r.drawText("0", this.posX + 20 + spacer, this.posY - 90, new Color(0, 255, 0).getRGB());
            spacer += 5;

        }


        if (this.states[0] == 1 && states[4] != 1) {
            if (direction == 1)
                r.displayImageParse(boss_default_right, (int) posX - 10, (int) posY - 80, (int) temp, 0);
            else
                r.displayImageParse(boss_default_left, (int) posX - 10, (int) posY - 80, (int) temp, 0);
        }
        if (this.states[3] == 1) {
            if (direction == 1)
                r.displayImageParse(boss_default_right, (int) posX - 10, (int) posY - 80, (int) temp, 0);
            else
                r.displayImageParse(boss_default_left, (int) posX - 10, (int) posY - 80, (int) temp, 0);
        }
        if (this.states[1] == 1)
            r.displayImageParse(boss_default_right, (int) posX - 10, (int) posY - 80, (int) temp, 0);
        if (this.states[2] == 1)
            r.displayImageParse(boss_default_left, (int) posX - 10, (int) posY - 80, (int) temp, 0);

        if (this.states[4] == 1 && direction == 1)
            r.displayImageParse(boss_bite_right, (int) posX - 10, (int) posY - 80, (int) temp_bite, 0);
        if (this.states[4] == 1 && direction == -1)
            r.displayImageParse(boss_bite_left, (int) posX - 10, (int) posY - 80, (int) temp_bite, 0);

    }
}