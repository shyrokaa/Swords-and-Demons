package com.shyroka.game;
import com.shyroka.engine.*;
import com.shyroka.engine.Image;
import com.shyroka.sql_save.*;
import java.awt.*;
import java.util.ArrayList;
import com.shyroka.game.SoundManager;
//import java.util.Arrays;


public class GameManager extends AbstractCore
{
    //game saving related


    public int finalscore = 0;

    Game_Server saveDB;
    int overload_save = 0;
    String[] Saves;
    public int indexSaves;
    public int saveLevel;
    public int saveX;
    public int saveY;
    public int saveHealth;
    public int saveScore;

    //tile map related info
    public static final int TileSize = 16;
    public int[][] collision;
    public int levelWidth, levelHeight;
    public boolean load0 = true;
    public boolean load1 = true;
    public boolean load2 = true;
    public boolean load3 = true;
    public boolean reset = true;
    public boolean load_menu = true;
    //info for levels

    LevelHandler L = new LevelHandler();
    public Image background_image1;
    public Image tile_image_1;
    public Image tile_image_2;
    public Image tile_image_3;
    public Image tile_image_4;
    public Image tile_image_5;

    //menu related info
    MainMenu Menu;
    boolean isRunning = false;
    public Image logo_image;
    SoundManager Smag;


    int regular =  new Color(100, 0, 0).getRGB();
    int selected =  new Color(0, 100, 0).getRGB();

    //game objects + camera
    public ArrayList<GameObject> objects = new ArrayList<GameObject>();
    public Camera cam;

    public GameManager() {

        saveDB =Game_Server.getInstance();
        Saves = new String[3];
        this.Smag = new SoundManager();
        //saveDB.wipeData();
        indexSaves = saveDB.importSaves(Saves);
        saveLevel = 1;
        saveX = 0;
        saveY = 0;
        saveHealth = 0;
        saveScore = 0;

        objects.add(new Player(2, 2, 0,100));
        //this player is only used to get the first camera position for the main menu and others. the entity gets deleted once the game starts

        //regular levels assets

        tile_image_1 = new Image("/Assets/level1_tile1.png");
        tile_image_2 = new Image("/Assets/level1_tile2.png");
        tile_image_3 = new Image("/Assets/level1_tile3.png");
        tile_image_4 = new Image("/Assets/level1_tile4.png");
        tile_image_5 = new Image("/Assets/level1_tile5.png");
        background_image1 = new Image("/Assets/background1.png");

        //other
        logo_image = new Image("/Assets/logo.png");
        cam = new Camera("player");
        Menu = new MainMenu(this);

    }
    @Override
    public void update(CoreGame core, float dt) {
        if(overload_save > 0)
            saveLevel = overload_save;

       Menu.updateMain(this,core,dt);
       Smag.update(this);
    }
    @Override
    public void render(CoreGame core, Renderer render) {

        this.saveX = objects.get(0).posX;
        this.saveY = objects.get(0).posY;
        this.saveHealth = objects.get(0).health;
        this.saveScore = objects.get(0).score;

        Menu.renderMain(this,core,render);
    }


    public void addObject(GameObject obj)
    {
        objects.add(obj);
    }

    public GameObject getObject(String tag) {
        for (GameObject object : objects) {
            if (object.getTag().equals(tag))
                return object;
        }
        return null;
    }

    public int getCollision(int x,int y)
    {
        if(x < 0 || x >= levelWidth || y < 0 || y>= levelHeight)
            return 1;
        return collision[x][y];

    }

    public ArrayList<GameObject> getObjects() {
        return objects;
    }

    public static void main(String[] args)
    {
        CoreGame G = new CoreGame(new GameManager());
        G.setWidth(400);
        G.setHeight(300);
        G.setScale(3f);
        G.start();
    }
}