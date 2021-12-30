package com.shyroka.game;

import com.shyroka.engine.CoreGame;
import com.shyroka.engine.Renderer;
import java.awt.*;
import java.awt.event.KeyEvent;

public class GameOverMenu {
    int regular = new Color(100, 0, 0).getRGB();
    int selected = new Color(0, 100, 0).getRGB();
    boolean isGameOver;
    public int posX;
    public int posY;

    public GameOverMenu(GameManager gm) {
        isGameOver = false;
        this.posX = (int) (gm.cam.getOffX());
        this.posY = (int) gm.cam.getOffY();
    }

    public void updateGOv(GameManager gm)
    {

    }
    public void renderGov(MainMenu mm, GameManager gm, CoreGame core, Renderer render)
    {

        this.posX = (int) (gm.cam.getOffX());
        this.posY = (int) gm.cam.getOffY();

        if (isGameOver)
        {
            //home
            if (core.getInput().isKey(KeyEvent.VK_E))
            {
                mm.isMain = true;
                mm.isRunning = false;
                isGameOver = false;
                gm.reset = true;
                gm.Smag.music_load_menu = true;
                gm.Smag.music_load_boss = true;
                gm.Smag.music_load_level = true;
                //System.out.println("event triggered");
            }
            render.drawText(">Game Over<", posX + 180, posY  + 100, regular);
            render.drawText("final score:"+ gm.finalscore, posX + 180, posY  + 110, regular);
            render.drawText(">home", posX  + 200, posY + 130, selected);

        }
    }
}