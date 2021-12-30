package com.shyroka.engine;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Image {

    private int width, height;
    private  int[] p;
    public Image(String path)
    {
        BufferedImage image = null;

        try
        {
            image  = ImageIO.read(Image.class.getResourceAsStream(path));
        }
        catch  (IOException e)
        {
            e.printStackTrace();
        }

        this.width = image.getWidth();
        this.height = image.getHeight();
        this.p = image.getRGB(0,0,this.width,this.height,null, 0, this.width);

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

    public int[] getP() {
        return p;
    }

    public void setP(int[] p) {
        this.p = p;
    }
}
