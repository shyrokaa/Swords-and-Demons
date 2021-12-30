package com.shyroka.game;

import com.shyroka.engine.CoreGame;
import com.shyroka.engine.Renderer;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Arrays;

public class PauseMenu {
    int regular = new Color(100, 0, 0).getRGB();
    int selected = new Color(0, 100, 0).getRGB();
    int verticalNav = 0;
    int horizontalNav = 0;
    boolean isPaused;
    boolean[] pause_menu_list = new boolean[3];
    public int posX;
    public int posY;

    public PauseMenu(GameManager gm) {
        isPaused = false;
        this.posX = (int) (gm.cam.getOffX());
        this.posY = (int) gm.cam.getOffY();
    }

    public void updatePause(GameManager gm) {
        //useless, this is just a menu with toggles
    }

    public void renderPause(MainMenu mm, GameManager gm, CoreGame core, Renderer render) {
        this.posX = (int) (gm.cam.getOffX());
        this.posY = (int) gm.cam.getOffY();
        if (isPaused) {

            //resume
            if (core.getInput().isKey(KeyEvent.VK_E) && pause_menu_list[0]) {
                mm.isMain = false;
                isPaused = false;

            }
            //home
            if (core.getInput().isKey(KeyEvent.VK_E) && pause_menu_list[2]) {
                mm.isMain = true;
                mm.isRunning = false;
                isPaused = false;
                gm.reset = true;
                gm.Smag.music_load_menu = true;
                gm.Smag.music_load_boss = true;
                gm.Smag.music_load_level = true;
            }
            //save a file
            if (core.getInput().isKey(KeyEvent.VK_E) && pause_menu_list[1]) {

                boolean isFull = false;
                if(Arrays.stream(gm.Saves).anyMatch("SAVE1"::equals) && Arrays.stream(gm.Saves).anyMatch("SAVE2"::equals) && Arrays.stream(gm.Saves).anyMatch("SAVE3"::equals))
                {
                    isFull = true;
                }

            if(!isFull) {
                if (!Arrays.stream(gm.Saves).anyMatch("SAVE1"::equals)) {
                    gm.saveDB.exportData("SAVE1", gm.saveLevel, gm.saveHealth, gm.saveScore, gm.saveX, gm.saveY);
                    gm.indexSaves = gm.saveDB.importSaves(gm.Saves);
                }
                else {
                    if (!Arrays.stream(gm.Saves).anyMatch("SAVE2"::equals)) {
                        gm.saveDB.exportData("SAVE2", gm.saveLevel, gm.saveHealth, gm.saveScore, gm.saveX, gm.saveY);
                        gm.indexSaves = gm.saveDB.importSaves(gm.Saves);
                    } else {

                        if (!Arrays.stream(gm.Saves).anyMatch("SAVE3"::equals)) {
                            gm.saveDB.exportData("SAVE3", gm.saveLevel, gm.saveHealth, gm.saveScore, gm.saveX, gm.saveY);
                            gm.indexSaves = gm.saveDB.importSaves(gm.Saves);
                        }
                    }
                }
            }
            else
            {
                gm.saveDB.deleteData("SAVE3");
                gm.saveDB.exportData("SAVE3", gm.saveLevel, gm.saveHealth, gm.saveScore, gm.saveX, gm.saveY);
                gm.indexSaves = gm.saveDB.importSaves(gm.Saves);
            }
                mm.isMain = false;
                isPaused = false;
            }
        }


        render.drawText(">PAUSE<", posX + 180, posY + 100, regular);
        {

            if (verticalNav > 2 || verticalNav < 0)
                verticalNav = 0;

            if (core.getInput().isKey(KeyEvent.VK_W)) {
                if (verticalNav == 0) {
                    pause_menu_list[verticalNav] = false;
                    verticalNav = 2;
                    pause_menu_list[verticalNav] = true;

                } else {
                    pause_menu_list[verticalNav] = false;
                    verticalNav--;
                    pause_menu_list[verticalNav] = true;
                }
            }

            if (core.getInput().isKey(KeyEvent.VK_S)) {
                // System.out.println("going down");
                if (verticalNav == 2) {
                    pause_menu_list[verticalNav] = false;
                    verticalNav = 0;
                    pause_menu_list[verticalNav] = true;
                } else {
                    pause_menu_list[verticalNav] = false;
                    verticalNav++;
                    pause_menu_list[verticalNav] = true;
                }
            }

            if (pause_menu_list[0])
                render.drawText(">resume", posX + 200, posY + 110, selected);
            else
                render.drawText(" resume", posX + 200, posY + 110, regular);

            if (pause_menu_list[1])
                render.drawText(">save game", posX + 200, posY + 120, selected);
            else
                render.drawText(" save game", posX + 200, posY + 120, regular);

            if (pause_menu_list[2])
                render.drawText(">home", posX + 200, posY + 130, selected);
            else
                render.drawText(" home", posX + 200, posY + 130, regular);

        }
    }
}