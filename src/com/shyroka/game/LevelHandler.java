package com.shyroka.game;
import com.shyroka.engine.CoreGame;
import com.shyroka.engine.Image;
import com.shyroka.engine.Renderer;

import java.awt.*;
import java.awt.event.KeyEvent;

public class LevelHandler {

    public boolean load1_file = false;
    public boolean load2_file = false;
    public boolean load3_file = false;

    public void levelUpdate(GameManager G, CoreGame core, float dt)
    {
        if(G.getObjects().size() > 0) {
            if (G.getObjects().get(0).isDeadPlayer) {
                G.Menu.GOver.isGameOver = true;
                G.getObjects().get(0).isDead = true;
            }
        }

        //level 1
        if (G.saveLevel == 1) {
            //System.out.println("you are on level 2");
            if (G.load1)
            {
                G.objects.clear();
                G.objects.add(new Player(2, 2, 0,100));
                this.loadLevel(G,"/Assets/level1.png");
                G.cam = new Camera("player");
                G.load1 = false;
            }
            if (G.objects.size() < 2)
                G.objects.add(new Devil(10, 2));

            for (int i = 0; i < G.objects.size(); i++) {
                G.objects.get(i).update(core, G, dt);
                if (G.objects.get(i).isDead()) {
                    G.objects.remove(i);
                    i--;
                }
            }
            G.cam.update(core, G, dt);
        }

        //level 2
        if (G.saveLevel == 2) {
            if (G.load2) {
                int score_temporary = G.objects.get(0).score;
                G.objects.clear();
                G.objects.add(new Player(2, 2, score_temporary,100));
                this.loadLevel(G,"/Assets/level2.png");
                G.cam = new Camera("player");
                G.load2 = false;
            }
            if (G.objects.size() < 2)
                G.objects.add(new Devil(10, 2));

            for (int i = 0; i < G.objects.size(); i++) {
                G.objects.get(i).update(core, G, dt);
                if (G.objects.get(i).isDead()) {
                    G.objects.remove(i);
                    i--;
                }
            }
            G.cam.update(core, G, dt);
        }

        //level3
        if (G.saveLevel == 3)
        {
            if (G.load3)
            {
                int score_temporary = G.objects.get(0).score;
                G.objects.clear();
                G.objects.add(new Player(2, 10, score_temporary,100));
                this.loadLevel(G,"/Assets/level3.png");
                G.cam = new Camera("player");
                G.load3 = false;
            }
            for (int i = 0; i < G.objects.size(); i++)
            {
                G.objects.get(i).update(core, G, dt);
                if (G.objects.get(i).isDead()) {

                    G.objects.remove(i);
                    i--;
                }
            }
            G.cam.update(core, G, dt);
        }
    }

    public void levelRender(GameManager G, CoreGame core, Renderer render)
    {

        if (core.getInput().isKey(KeyEvent.VK_ESCAPE) ||  G.Menu.Pause.isPaused)
        {
            System.out.println("i am here");
            G.isRunning = false;
            G.Menu.Pause.isPaused = true;
            G.Menu.isMain = true;
        }
        else {
            if (G.saveLevel == 1) {
                render.drawSimpleImage(G.background_image1, -200, -180);

                for (int y = 0; y < G.levelHeight; y++)
                {
                    for (int x = 0; x < G.levelWidth; x++)
                    {
                        if (G.collision[x][y] == 1)
                            render.drawSimpleImage(G.tile_image_1, x * GameManager.TileSize, y * GameManager.TileSize);
                        else if (G.collision[x][y] == 2)
                            render.drawSimpleImage(G.tile_image_3, x * GameManager.TileSize, y * GameManager.TileSize);

                        else if (G.collision[x][y] == 3)
                            render.drawSimpleImage(G.tile_image_4, x * GameManager.TileSize, y * GameManager.TileSize);

                        else if (G.collision[x][y] == -1)
                            render.drawSimpleImage(G.tile_image_5, x * GameManager.TileSize, y * G.TileSize);

                        else

                            render.drawSimpleImage(G.tile_image_2, x * GameManager.TileSize, y * GameManager.TileSize);
                    }
                }
                //render objects
                for (GameObject element : G.objects) {
                    element.render(core, G, render);
                }
            }
            if (G.saveLevel == 2) {
                render.drawSimpleImage(G.background_image1, -200, -180);
                for (int y = 0; y < G.levelHeight; y++) {
                    for (int x = 0; x < G.levelWidth; x++) {
                        if (G.collision[x][y] == 1)
                            render.drawSimpleImage(G.tile_image_1, x * GameManager.TileSize, y * GameManager.TileSize);
                        else if (G.collision[x][y] == 2)
                            render.drawSimpleImage(G.tile_image_3, x * GameManager.TileSize, y * GameManager.TileSize);

                        else if (G.collision[x][y] == 3)
                            render.drawSimpleImage(G.tile_image_4, x * GameManager.TileSize, y * GameManager.TileSize);

                        else if (G.collision[x][y] == -1)
                            render.drawSimpleImage(G.tile_image_5, x * GameManager.TileSize, y * GameManager.TileSize);
                        else
                            render.drawSimpleImage(G.tile_image_2, x * GameManager.TileSize, y * GameManager.TileSize);
                    }
                }
                //render objects
                for (GameObject element : G.objects) {
                    element.render(core, G, render);
                }
            }
            if (G.saveLevel == 3) {
                render.drawSimpleImage(G.background_image1, -200, -180);
                for (int y = 0; y < G.levelHeight; y++) {
                    for (int x = 0; x < G.levelWidth; x++) {
                        if (G.collision[x][y] == 1)
                            render.drawSimpleImage(G.tile_image_1, x * GameManager.TileSize, y * GameManager.TileSize);
                        else if (G.collision[x][y] == 2)
                            render.drawSimpleImage(G.tile_image_3, x * GameManager.TileSize, y * GameManager.TileSize);

                        else if (G.collision[x][y] == 3)
                            render.drawSimpleImage(G.tile_image_4, x * GameManager.TileSize, y * GameManager.TileSize);

                        else if (G.collision[x][y] == -1)
                            render.drawSimpleImage(G.tile_image_5, x * GameManager.TileSize, y * GameManager.TileSize);
                        else
                            render.drawSimpleImage(G.tile_image_2, x * GameManager.TileSize, y * GameManager.TileSize);
                    }
                }
                //render objects
                for (GameObject element : G.objects) {
                    element.render(core, G, render);
                }
            }
        }
    }

    public void levelUpdateFile(GameManager G, CoreGame core, float dt)
    {

        if(G.getObjects().size() > 0) {
            if (G.getObjects().get(0).isDeadPlayer) {
                G.Menu.GOver.isGameOver = true;
                G.getObjects().get(0).isDead = true;
            }
        }

        //level 1
       // System.out.println(G.saveLevel);
        if (G.saveLevel == 1 || G.overload_save == 1) {
            //System.out.println("you are on level 2");
            if (G.load1)
            {
                if(this.load1_file)
                {
                    G.objects.clear();
                    G.objects.add(new Player(G.saveX / 16, G.saveY/16, G.saveScore, G.saveHealth));
                    this.loadLevel(G, "/Assets/level1.png");
                    G.cam = new Camera("player");
                    G.load1 = false;
                    this.load1_file = false;

                    System.out.println("Loading level 1 save");
                    for (int y = 0; y < G.levelHeight; y++) {
                        for (int x = 0; x < G.levelWidth; x++) {
                            System.out.print(G.collision[x][y] + " ");
                        }
                        System.out.println();
                    }
                }
                else
                    {
                    G.objects.clear();
                    G.objects.add(new Player(2, 2, G.saveScore, G.saveHealth));
                    this.loadLevel(G, "/Assets/level1.png");
                    G.cam = new Camera("player");
                    G.load1 = false;
                }
            }

            if (G.objects.size() < 2)
                G.objects.add(new Devil(10, 2));

            for (int i = 0; i < G.objects.size(); i++) {
                G.objects.get(i).update(core, G, dt);
                if (G.objects.get(i).isDead())
                {

                    G.objects.remove(i);
                    i--;
                }
            }
            G.cam.update(core, G, dt);
        }

        //level 2
        if (G.saveLevel == 2 || G.overload_save == 2)
        {
            if (G.load2) {
                if (load2_file)
                {
                    G.objects.clear();
                    G.objects.add(new Player(G.saveX/16, G.saveY/16, G.saveScore, G.saveHealth));
                    this.loadLevel(G, "/Assets/level2.png");
                    G.cam = new Camera("player");
                    G.load2 = false;
                    this.load2_file = false;
                }
                else
                    {
                    int score_temporary = G.objects.get(0).score;
                    G.objects.clear();
                    G.objects.add(new Player(2, 2, score_temporary, 100));
                    this.loadLevel(G, "/Assets/level2.png");
                    G.cam = new Camera("player");
                    G.load2 = false;
                }
            }
            if (G.objects.size() < 2)
                G.objects.add(new Devil(10, 2));

            for (int i = 0; i < G.objects.size(); i++) {
                G.objects.get(i).update(core, G, dt);
                if (G.objects.get(i).isDead()) {
                    G.objects.remove(i);
                    i--;
                }
            }
            G.cam.update(core, G, dt);
        }

        //level3
        if (G.saveLevel == 3 ||  G.overload_save == 3)
        {
            if (G.load3) {
                if (load3_file)
                {
                    G.objects.clear();
                    G.objects.add(new Player(G.saveX/16, G.saveY/16, G.saveScore, G.saveHealth));
                    this.loadLevel(G, "/Assets/level3.png");
                    G.cam = new Camera("player");
                    G.load3 = false;
                    this.load3_file = false;
                }
                else
                    {
                    int score_temporary = G.objects.get(0).score;
                    G.objects.clear();
                    G.objects.add(new Player(5, 5, score_temporary, 100));
                    this.loadLevel(G, "/Assets/level3.png");
                    G.cam = new Camera("player");
                    G.load3 = false;
                }
            }

            for (int i = 0; i < G.objects.size(); i++)
            {
                G.objects.get(i).update(core, G, dt);
                if (G.objects.get(i).isDead()) {
                    G.objects.remove(i);
                    i--;
                }
            }
            G.cam.update(core, G, dt);
        }
    }

    public void levelRenderFile(GameManager G, CoreGame core, Renderer render)
    {
        if (core.getInput().isKey(KeyEvent.VK_ESCAPE) ||  G.Menu.Pause.isPaused)
        {
            System.out.println("i am here");
            G.isRunning = false;
            G.Menu.Pause.isPaused = true;
            G.Menu.isMain = true;
        }

        if (G.saveLevel == 1)
        {
            render.drawSimpleImage(G.background_image1, -200, -180);
            for (int y = 0; y < G.levelHeight; y++) {
                for (int x = 0; x < G.levelWidth; x++) {

                    if (G.collision[x][y] == 1)
                        render.drawSimpleImage(G.tile_image_1, x * GameManager.TileSize, y * GameManager.TileSize);
                    else if (G.collision[x][y] == 2)
                        render.drawSimpleImage(G.tile_image_3, x * GameManager.TileSize, y * GameManager.TileSize);

                    else if (G.collision[x][y] == 3)
                        render.drawSimpleImage(G.tile_image_4, x * GameManager.TileSize, y * GameManager.TileSize);

                    else if (G.collision[x][y] == -1)
                        render.drawSimpleImage(G.tile_image_5, x * GameManager.TileSize, y * GameManager.TileSize);
                    else
                        render.drawSimpleImage(G.tile_image_2, x * GameManager.TileSize, y * GameManager.TileSize);
                }
            }
            //render objects
            for (GameObject element : G.objects) {
                element.render(core, G, render);
            }
        }
        if (G.saveLevel == 2)
        {
            render.drawSimpleImage(G.background_image1, -200, -180);
            for (int y = 0; y < G.levelHeight; y++) {
                for (int x = 0; x < G.levelWidth; x++) {
                    if (G.collision[x][y] == 1)
                        render.drawSimpleImage(G.tile_image_1, x * GameManager.TileSize, y * GameManager.TileSize);
                    else if (G.collision[x][y] == 2)
                        render.drawSimpleImage(G.tile_image_3, x * GameManager.TileSize, y * GameManager.TileSize);

                    else if (G.collision[x][y] == 3)
                        render.drawSimpleImage(G.tile_image_4, x * GameManager.TileSize, y * GameManager.TileSize);

                    else if (G.collision[x][y] == -1)
                        render.drawSimpleImage(G.tile_image_5, x * GameManager.TileSize, y * GameManager.TileSize);
                    else
                        render.drawSimpleImage(G.tile_image_2, x * GameManager.TileSize, y * GameManager.TileSize);
                }
            }
            //render objects
            for (GameObject element : G.objects) {
                element.render(core, G, render);
            }
        }
        if (G.saveLevel == 3)
        {
            render.drawSimpleImage(G.background_image1, -200, -180);
            for (int y = 0; y < G.levelHeight; y++)
            {
                for (int x = 0; x < G.levelWidth; x++)
                {
                    if (G.collision[x][y] == 1)
                        render.drawSimpleImage(G.tile_image_1, x * GameManager.TileSize, y * GameManager.TileSize);
                    else if (G.collision[x][y] == 2)
                        render.drawSimpleImage(G.tile_image_3, x * GameManager.TileSize, y * GameManager.TileSize);

                    else if (G.collision[x][y] == 3)
                        render.drawSimpleImage(G.tile_image_4, x * GameManager.TileSize, y * GameManager.TileSize);

                    else if (G.collision[x][y] == -1)
                        render.drawSimpleImage(G.tile_image_5, x * GameManager.TileSize, y * GameManager.TileSize);
                    else
                        render.drawSimpleImage(G.tile_image_2, x * GameManager.TileSize, y * GameManager.TileSize);
                }
            }
            //render objects
            for (GameObject element : G.objects) {
                element.render(core, G, render);
            }
        }

    }

    public void loadLevel(GameManager G, String path) {
        Image levelImage = new Image(path);
        G.levelWidth = levelImage.getWidth();
        G.levelHeight = levelImage.getHeight();
        G.collision = new int[G.levelWidth][G.levelHeight];
        for (int y = 0; y < levelImage.getHeight(); y++) {
            for (int x = 0; x < levelImage.getWidth(); x++) {
                if (levelImage.getP()[x + y * levelImage.getWidth()] == new Color(255, 255, 255).getRGB()) {
                    G.collision[x][y] = 0;
                } else if (levelImage.getP()[x + y * levelImage.getWidth()] == new Color(255, 0, 0).getRGB()) {
                    G.collision[x][y] = 2;
                } else if (levelImage.getP()[x + y * levelImage.getWidth()] == new Color(255, 255, 0).getRGB()) {
                    G.collision[x][y] = 3;
                } else if (levelImage.getP()[x + y * levelImage.getWidth()] == new Color(0, 0, 255).getRGB()) {
                    G.collision[x][y] = -1;
                }
                //adding wolf case
                else if (levelImage.getP()[x + y * levelImage.getWidth()] == new Color(100, 0, 0).getRGB()) {
                    G.objects.add(new Wolf(x, y));
                    //adding knight case
                } else if (levelImage.getP()[x + y * levelImage.getWidth()] == new Color(0, 0, 100).getRGB()) {
                    G.objects.add(new Knight(x, y));
                    //System.out.println("knight found");
                }
                else if (levelImage.getP()[x + y * levelImage.getWidth()] == new Color(100, 200, 200).getRGB()) {
                    G.objects.add(new Boss(x, y));
                }
                else
                    G.collision[x][y] = 1;
            }
        }

    }
}