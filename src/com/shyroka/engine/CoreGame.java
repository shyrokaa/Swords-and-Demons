package com.shyroka.engine;
import java.awt.*;

public class CoreGame implements Runnable {

    private Window window;
    private Renderer renderer;
    private Input input;
    private AbstractCore a_core;

    private Thread thread;
    private boolean running = false;
    private final double Update_limit = 1.0/60.0;
    //window information
    private int width = 300, height = 300 ;
    private float scale = 4f;
    private String title = "Game Engine";
    int fps = 0;
    public CoreGame(AbstractCore a_core)
    {
        this.a_core = a_core;
    }

    public void start()
    {
        window = new Window(this);
        renderer = new Renderer(this);
        input = new Input(this);
        thread = new Thread(this);
        thread.run();
    }

    public void stop()
    {


    }
    public void run()
    {
        running = true;

        boolean render = false;

        double first_time = 0;
        double last_time = System.nanoTime() / 1000000000.0;
        double passed_time = 0;
        double unprocessed_time = 0;

        double frame_time = 0;
        int frames = 0;


        while(running)
        {
            render = false;

            first_time = System.nanoTime() / 1000000000.0;
            passed_time = first_time - last_time;
            last_time = first_time;

            frame_time += passed_time;
            unprocessed_time += passed_time;

            while(unprocessed_time >= Update_limit)
            {
                //System.out.println("[Updating...]");
                unprocessed_time -= Update_limit;
                render = true;

                //input test
                /*
                if(input.isButtonDown(MouseEvent.BUTTON1))
                {
                    System.out.println("A was pressed!");
                }
                */
                a_core.update(this,(float)Update_limit);

                input.Update();

                if(frame_time >= 1.0)
                {
                    frame_time = 0;
                    fps = frames;
                    frames = 0;
                }
            }
            if(render)
            {
                renderer.clear();
                //render the game
                a_core.render(this,renderer);
                //renderer.drawText("Fps:" + fps,0,0,new Color(100,0,0).getRGB());
                window.Update();
                frames ++;
            }
            else
            {
                try
                {
                    Thread.sleep(1);
                 }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }
        dispose();
    }

    public void dispose()
    {

    }

    //a ton of getters and setters which are used in other classes

    public int getFps() {
        return fps;
    }

    public Window getWindow() {
        return window;
    }

    public void setWindow(Window window) {
        this.window = window;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Input getInput() {
        return input;
    }

    public Renderer getRenderer()
    {
        return renderer;
    }

    public void setRenderer(Renderer renderer) {
        this.renderer = renderer;
    }
}