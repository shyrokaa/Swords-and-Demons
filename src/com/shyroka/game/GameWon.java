package com.shyroka.game;
import com.shyroka.engine.CoreGame;
import com.shyroka.engine.Renderer;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Arrays;

public class GameWon {

    int regular = new Color(100, 0, 0).getRGB();
    int selected = new Color(0, 100, 0).getRGB();
    int verticalNav = 0;
    int horizontalNav = 0;
    boolean isWon;
    boolean[] GO_menu_list = new boolean[3];
    public int posX;
    public int posY;

    public GameWon(GameManager gm) {
        isWon = false;
        this.posX = (int) (gm.cam.getOffX());
        this.posY = (int) gm.cam.getOffY();
    }

    public void updateGW(GameManager gm)
    {

    }
    public void renderGW(MainMenu mm, GameManager gm, CoreGame core, Renderer render)
    {
        this.posX = (int) (gm.cam.getOffX());
        this.posY = (int) gm.cam.getOffY();
        if (isWon)
        {
            //home
            if (core.getInput().isKey(KeyEvent.VK_E) && GO_menu_list[0])
            {
                mm.isMain = true;
                mm.isRunning = false;
                isWon = false;
                gm.reset = true;
                gm.Smag.music_load_menu = true;
                gm.Smag.music_load_boss = true;
                gm.Smag.music_load_level = true;
                //System.out.println("event triggered");
            }
            render.drawText("YOU WIN!", posX + 180, posY  + 100, regular);
            render.drawText("final score:"+ gm.finalscore, posX + 180, posY  + 110, regular);
            {
                if (verticalNav > 2 || verticalNav < 0)
                    verticalNav = 0;

                if (core.getInput().isKey(KeyEvent.VK_W)) {
                    if (verticalNav == 0)
                    {
                        GO_menu_list[verticalNav] = false;
                        verticalNav = 0;
                        GO_menu_list[verticalNav] = true;

                    } else {
                        GO_menu_list[verticalNav] = false;
                        verticalNav--;
                        GO_menu_list[verticalNav] = true;
                    }
                }
                if (core.getInput().isKey(KeyEvent.VK_S)) {
                    // System.out.println("going down");
                    if (verticalNav == 2) {
                        GO_menu_list[verticalNav] = false;
                        verticalNav = 0;
                        GO_menu_list[verticalNav] = true;
                    } else {
                        GO_menu_list[verticalNav] = false;
                        verticalNav++;
                        GO_menu_list[verticalNav] = true;
                    }
                }

                if (GO_menu_list[0])
                    render.drawText(">home", posX  + 200, posY + 130, selected);
                else
                    render.drawText(" home", posX  + 200, posY + 130, regular);
            }
        }
    }
}