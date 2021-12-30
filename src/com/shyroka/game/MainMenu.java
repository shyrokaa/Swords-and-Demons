package com.shyroka.game;

import com.shyroka.engine.CoreGame;
import com.shyroka.engine.Image;
import com.shyroka.engine.Renderer;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MainMenu {

    int regular = new Color(100, 0, 0).getRGB();
    int selected = new Color(0, 100, 0).getRGB();
    public boolean isMain;
    public boolean isRunning;
    int verticalNav = 0;
    public boolean[] menuList = new boolean[5];
    public int posX;
    public int posY;

    LoadMenu Load;
    PauseMenu Pause;
    GameOverMenu GOver;
    SettingsMenu Sett;
    GameWon GW;
    Tutorial Tut;

    public Image background_mm;



    public MainMenu(GameManager gm) {
        background_mm = new Image("/Assets/background_menu.png");
        this.Load = new LoadMenu(gm);
        this.Pause = new PauseMenu(gm);
        this.GOver = new GameOverMenu(gm);
        this.GW = new GameWon(gm);
        this.Sett = new SettingsMenu(gm);
        this.Tut = new Tutorial(gm);
        this.isMain = true;
        this.isRunning = false;
        this.posX = (int) (gm.cam.getOffX());
        this.posY = (int) gm.cam.getOffY();
    }

    public void updateMain(GameManager gm, CoreGame core, float dt) {


        if (!GOver.isGameOver && !GW.isWon) {
            if (!isMain && isRunning) {
                if (menuList[0]) {
                    gm.L.levelUpdate(gm, core, dt);
                }
                if (menuList[1]) {
                    Load.updateLoad(this, gm, core, dt);
                }
                if (menuList[3]) {
                    if (gm.load0) {
                        gm.objects.clear();
                        gm.objects.add(new Player(4, 4, 0, 100));
                        gm.L.loadLevel(gm, "/Assets/level0.png");
                        gm.cam = new Camera("player");
                        gm.load0 = false;
                    }
                }
                for (int i = 0; i < gm.objects.size(); i++) {
                    gm.objects.get(i).update(core, gm, dt);
                    if (gm.objects.get(i).isDead()) {
                        gm.objects.remove(i);
                        i--;
                    }
                }
                gm.cam.update(core, gm, dt);
            }
        }
    }

    public void renderMain(GameManager gm, CoreGame core, Renderer render) {

        this.posX = (int) (gm.cam.getOffX());
        this.posY = (int) gm.cam.getOffY();

        //System.out.println(isMain+ " " + isRunning+ " " + Pause.isPaused + " " + GOver.isGameOver);

        if (Tut.isTutorial)
        {
            Tut.renderT(this,gm,core,render);
        } else {
            if (GW.isWon) {
                GW.renderGW(this, gm, core, render);
            } else {
                if (GOver.isGameOver) {
                    GOver.renderGov(this, gm, core, render);
                } else {

                    //pause menu things
                    if (isMain && isRunning && Pause.isPaused) {
                        //System.out.println("commencing rendering");
                        Pause.renderPause(this, gm, core, render);
                    }

                    //main menu things
                    if (isMain && !isRunning) {
                        render.drawSimpleImage(gm.logo_image, (int) (gm.cam.getOffX()) + 100, (int) (gm.cam.getOffY()) + 45);

                        if (verticalNav > 4 || verticalNav < 0)
                            verticalNav = 0;

                        if (core.getInput().isKey(KeyEvent.VK_W)) {
                            if (verticalNav == 0) {
                                menuList[verticalNav] = false;
                                verticalNav = 4;
                                menuList[verticalNav] = true;
                            } else {
                                menuList[verticalNav] = false;
                                verticalNav--;
                                menuList[verticalNav] = true;
                            }
                        }
                        if (core.getInput().isKey(KeyEvent.VK_S)) {

                            if (verticalNav == 4) {
                                menuList[verticalNav] = false;
                                verticalNav = 0;
                                menuList[verticalNav] = true;

                            } else {
                                menuList[verticalNav] = false;
                                verticalNav++;
                                menuList[verticalNav] = true;
                            }
                        }

                        if (core.getInput().isKey(KeyEvent.VK_ENTER) && menuList[4])
                        {
                            Tut.isTutorial = true;
                        }


                        if (core.getInput().isKey(KeyEvent.VK_ENTER) && (menuList[0] || menuList[1] || menuList[2] || menuList[3])) {
                            isMain = false;
                            isRunning = true;

                            if (menuList[0] || menuList[3]) {

                                for (int i = 0; i < gm.objects.size(); i++) {
                                    if (gm.objects.get(i).tag.equals("devil")) {
                                        System.out.println("removed");
                                        gm.objects.remove(i);
                                    }
                                }
                                System.out.println("reset triggered");

                                gm.saveLevel = 1;
                                gm.saveHealth = 100;
                                gm.saveScore = 0;
                                gm.saveX = 5;
                                gm.saveY = 5;
                                gm.reset = false;
                                gm.load0 = true;
                                gm.load1 = true;
                                gm.load2 = true;
                                gm.load3 = true;
                                gm.reset = true;
                            }

                            if (menuList[1] || menuList[2])
                                isRunning = false;
                            Load.isLoad = true;
                        }

                        if (menuList[0])
                            render.drawText(">new game", posX + 80, posY + 100, selected);
                        else
                            render.drawText(" new game", posX + 80, posY + 100, regular);

                        if (menuList[1])
                            render.drawText(">load save", posX + 90, posY + 120, selected);
                        else
                            render.drawText(" load save", posX + 90, posY + 120, regular);


                        if (menuList[2])
                            render.drawText(">settings", posX + 100, posY + 140, selected);
                        else
                            render.drawText(" settings", posX + 100, posY + 140, regular);

                        if (menuList[3])
                            render.drawText(">debug room", posX + 110, posY + 160, selected);
                        else
                            render.drawText(" debug room", posX + 110, posY + 160, regular);


                        if (menuList[4])
                            render.drawText(">help", posX + 120, posY + 180, selected);
                        else
                            render.drawText(" help", posX + 120, posY + 180, regular);

                        render.drawSimpleImage(this.background_mm, posX, posY-100);
                    }
                    if (!isMain && isRunning && menuList[0]) {
                        gm.L.levelRender(gm, core, render);
                    }

                    if (!isMain && menuList[1]) {

                        Load.renderLoad(this, gm, core, render);
                    }

                    if (!isMain && !isRunning && menuList[2]) {
                        //System.out.println("here");
                        Sett.renderLoad(this, gm, core, render);
                    }
                    if (!isMain && isRunning && menuList[3]) {
                        gm.L.levelRender(gm, core, render);

                    }
                }
            }
        }
    }
}