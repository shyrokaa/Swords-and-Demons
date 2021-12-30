package com.shyroka.game;

import com.shyroka.engine.CoreGame;
import com.shyroka.engine.Renderer;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Arrays;

public class Tutorial {

    int regular = new Color(0, 100, 250).getRGB();
    int selected = new Color(200, 200, 0).getRGB();
    boolean isTutorial;
    public int posX;
    public int posY;

    public Tutorial(GameManager gm) {
        isTutorial = true;
        this.posX = (int) (gm.cam.getOffX());
        this.posY = (int) gm.cam.getOffY();

    }

    public void updateT(GameManager gm) {

    }

    public void renderT(MainMenu mm, GameManager gm, CoreGame core, Renderer render) {

        this.posX = (int) (gm.cam.getOffX());
        this.posY = (int) gm.cam.getOffY();

        if (core.getInput().isKey(KeyEvent.VK_E)) {
            isTutorial = false;
        }
        //title
        render.drawText(">How to play<", posX + 180, posY + 30, regular);

       //regular
        render.drawText("Menu Controls", posX + 150, posY + 60, regular);


        render.drawText("Menu Navigation", posX + 100, posY + 70, regular);
        render.drawText("W,A,S,D Keys", posX + 250, posY + 70, selected);


        render.drawText("Menu Selection:", posX + 100, posY + 80, regular);
        render.drawText("Enter Key", posX + 250, posY + 80, selected);


        render.drawText("Secondary Menu Selection:", posX + 100, posY + 90, regular);
        render.drawText("E Key", posX + 250, posY + 90, selected);


        //player
        render.drawText("Player Controls", posX + 150, posY + 110, regular);


        render.drawText("player movement", posX + 100, posY + 120, regular);
        render.drawText("W,A,S,D Keys", posX + 250, posY + 120, selected);

        render.drawText("Attack:", posX + 100, posY + 130, regular);
        render.drawText("sPACE Key", posX + 250, posY + 130, selected);

        render.drawText("Interract:", posX + 100, posY + 140, regular);
        render.drawText("F Key", posX + 250, posY + 140, selected);


        render.drawText("press e to continue", posX + 300, posY + 270, regular);

    }
}
