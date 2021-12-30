package com.shyroka.game;

import com.shyroka.engine.CoreGame;
import com.shyroka.engine.Renderer;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Arrays;

public class SettingsMenu {
    int regular = new Color(100, 0, 0).getRGB();
    int selected = new Color(0, 100, 0).getRGB();
    boolean isSettings;
    boolean AudioOn = true;
    public int posX;
    public int posY;

    public SettingsMenu(GameManager gm) {
        this.isSettings = true;
        this.posX = (int) (gm.cam.getOffX());
        this.posY = (int) gm.cam.getOffY();
    }

    public void updateLoad(MainMenu mm, GameManager gm, CoreGame core, float dt) {
    }

    public void renderLoad(MainMenu mm, GameManager gm, CoreGame core, Renderer render) {
        this.posX = (int) (gm.cam.getOffX());
        this.posY = (int) gm.cam.getOffY();
        if (isSettings && !mm.isRunning) {
            //home
            if (core.getInput().isKey(KeyEvent.VK_ESCAPE)) {
                mm.isRunning = false;
                isSettings = true;
                mm.isMain = true;
            }

            render.drawText(">Settings<", posX + 50, posY + 70, regular);
            render.drawText("Sound", posX + 140, posY + 90, regular);

            if (AudioOn)
                render.drawText("X", posX + 180, posY + 90, selected);
            else
                render.drawText("X", posX + 180, posY + 90, regular);

            if (core.getInput().isKey(KeyEvent.VK_A) && AudioOn) {
                AudioOn = false;
            }
            if (core.getInput().isKey(KeyEvent.VK_D) && !AudioOn) {
                AudioOn = true;
            }

        }
    }


}
