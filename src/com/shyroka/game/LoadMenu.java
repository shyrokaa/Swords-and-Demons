package com.shyroka.game;

import com.shyroka.engine.CoreGame;
import com.shyroka.engine.Renderer;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Arrays;

public class LoadMenu {
    int regular = new Color(100, 0, 0).getRGB();
    int selected = new Color(0, 100, 0).getRGB();
    public boolean[][] saveList = new boolean[3][2];
    int verticalNav = 0;
    int horizontalNav = 0;
    boolean isLoad;
    public int posX;
    public int posY;

    public LoadMenu(GameManager gm) {
        this.saveList[0][0] = true;
        this.horizontalNav = 0;
        this.verticalNav = 0;
        this.isLoad = true;
        this.posX = (int) (gm.cam.getOffX());
        this.posY = (int) gm.cam.getOffY();
    }

    public void updateLoad(MainMenu mm, GameManager gm, CoreGame core, float dt)
    {

        if (mm.menuList[1] && (saveList[0][0] || saveList[1][0] || saveList[2][0])) {
            if (saveList[0][0])
            {
                //System.out.println("data imported from " + gm.Saves[0]);
                gm.saveDB.importData(gm.Saves[0],gm);
                if (gm.saveLevel == 1)
                    gm.L.load1_file = true;
                if (gm.saveLevel == 2)
                    gm.L.load2_file = true;
                if (gm.saveLevel == 3)
                    gm.L.load3_file = true;
                gm.L.levelUpdateFile(gm, core, dt);
            }
            if (saveList[1][0]) {
                //System.out.println("data imported from " + gm.Saves[1]);
                gm.saveDB.importData(gm.Saves[1],gm);
                gm.L.levelUpdateFile(gm, core, dt);
            }
            if (saveList[2][0]) {
                //System.out.println("data imported from " + gm.Saves[2]);
                gm.saveDB.importData(gm.Saves[2],gm);
                gm.L.levelUpdateFile(gm, core, dt);
            }

        }
    }

    public void renderLoad(MainMenu mm, GameManager gm, CoreGame core, Renderer render) {
        this.posX = (int) (gm.cam.getOffX());
        this.posY = (int) gm.cam.getOffY();
        if (isLoad && !mm.isRunning) {

            //deletion
            for (int index = 0; index < gm.indexSaves; index++) {
                if (saveList[index][0] && core.getInput().isKey(KeyEvent.VK_E)) {
                    mm.isRunning = true;
                    mm.isMain = false;
                    this.isLoad = false;
                }
                if (saveList[index][1] && core.getInput().isKey(KeyEvent.VK_E)) {
                    //System.out.println("something should have been deleted");
                    gm.saveDB.deleteData(gm.Saves[index]);
                    gm.indexSaves = gm.saveDB.importSaves(gm.Saves);
                }
            }

            if (core.getInput().isKey(KeyEvent.VK_ESCAPE))
            {
                mm.isRunning = false;
                isLoad = false;
                mm.isMain = true;
            }

            render.drawText(">LOAD A SAVE FILE<", posX + 50, posY + 70, regular);
            if (gm.indexSaves == 0) {
                render.drawText("no saves found", posX + 140, posY + 90, regular);

            } else {
                if (core.getInput().isKey(KeyEvent.VK_A)) {
                    gm.Menu.Load.saveList[verticalNav][horizontalNav] = false;
                    horizontalNav = 0;
                    gm.Menu.Load.saveList[verticalNav][horizontalNav] = true;
                }
                if (core.getInput().isKey(KeyEvent.VK_D)) {
                    gm.Menu.Load.saveList[verticalNav][horizontalNav] = false;
                    horizontalNav = 1;
                    gm.Menu.Load.saveList[verticalNav][horizontalNav] = true;
                }

                if (core.getInput().isKey(KeyEvent.VK_W)) {
                    if (verticalNav == 0) {
                        gm.Menu.Load.saveList[verticalNav][horizontalNav] = false;
                        verticalNav = gm.indexSaves - 1;
                        gm.Menu.Load.saveList[verticalNav][horizontalNav] = true;
                    } else {
                        gm.Menu.Load.saveList[verticalNav][horizontalNav] = false;
                        verticalNav--;
                        gm.Menu.Load.saveList[verticalNav][horizontalNav] = true;
                    }
                }

                if (core.getInput().isKey(KeyEvent.VK_S)) {

                    if (verticalNav == gm.indexSaves - 1) {
                        gm.Menu.Load.saveList[verticalNav][horizontalNav] = false;
                        verticalNav = 0;
                        gm.Menu.Load.saveList[verticalNav][horizontalNav] = true;

                    } else {
                        gm.Menu.Load.saveList[verticalNav][horizontalNav] = false;
                        verticalNav++;
                        gm.Menu.Load.saveList[verticalNav][horizontalNav] = true;
                    }
                }
                for (int index = 0; index < gm.indexSaves; index++) {
                    if (saveList[index][0]) {

                        render.drawText(gm.Saves[index], posX + 140, posY + 120 + index * 10, selected);
                    } else {
                        render.drawText(gm.Saves[index], posX + 140, posY + 120 + index * 10, regular);
                    }
                    if (saveList[index][1])
                        render.drawText(">delete<", posX + 180, posY + 120 + index * 10, selected);
                    else
                        render.drawText(" delete ", posX + 180, posY + 120 + index * 10, regular);
                }
            }
        }

        if (!isLoad && mm.isRunning)
        {
            gm.L.levelRenderFile(gm, core, render);
        }
    }
}
