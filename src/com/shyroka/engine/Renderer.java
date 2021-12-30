package com.shyroka.engine;
import java.awt.image.DataBufferInt;
import java.util.Arrays;
import java.util.Locale;

public class Renderer {
    private int pW, pH;
    private int[] p;
    private Text font = Text.STANDARD;
    private int camX,camY;



    public Renderer(CoreGame core) {
        pW = core.getWidth();
        pH = core.getHeight();
        p = ((DataBufferInt) core.getWindow().getImage().getRaster().getDataBuffer()).getData();
    }

    public void clear() {
        //just a blank screen
        Arrays.fill(p, 0);
    }

    public void setPixel(int x, int y, int val) {

        x-= camX;
        y-=camY;


        //checking if the image is out of window bounds
        //the val == 0xffff00ff check is for testing this condition for a pink shade
        if ((x < 0 || x >= this.pW || y < 0 || y >= this.pH) || val == 0xffff00ff) {
            return;
        }
        //here is where the pixel itself is drawn
        p[x + y * this.pW] = val;

    }

    public void drawText(String text, int offX, int offY, int color) {
        text = text.toUpperCase();
        int offset = 0;

        for (int i = 0; i < text.length(); i++) {
            int unicode = text.codePointAt(i) - 32;
            //System.out.println(unicode);

            for (int y = 0; y < font.getTextImage().getHeight(); y++) {
                for (int x = 0; x < font.getWidths()[unicode]; x++) {
                    if (font.getTextImage().getP()[x + font.getOffset()[unicode] + y * font.getTextImage().getWidth()] == 0xffffffff) {
                        setPixel(x + offX + offset, y + offY, color);
                    }
                }
            }
            offset += font.getWidths()[unicode];
        }
    }

    public void drawSimpleImage(Image img, int offX, int offY) {

        int updatedX = 0;
        int updatedY = 0;
        int updatedWidth = img.getWidth();
        int updatedHeight = img.getHeight();

        //allows drawing beyond screen limits(currently broken)

        //main display

        for (int y = updatedY; y < updatedHeight; y++) {
            for (int x = updatedX; x < updatedWidth; x++) {
                setPixel(x + offX, y + offY, img.getP()[x + y * updatedWidth]);
            }
        }
    }

    public void displayImageParse(ImageParse img, int offX, int offY, int ParseX, int ParseY) {
        int updatedX = 0;
        int updatedY = 0;
        int updatedWidth = img.getSourceW();
        int updatedHeight = img.getSourceH();

        //main display

        for (int y = updatedY; y < updatedHeight; y++) {
            for (int x = updatedX; x < updatedWidth; x++) {
                setPixel(x + offX, y + offY, img.getP()[(x + ParseX * img.getSourceW()) + (y + ParseY * img.getSourceH()) * img.getWidth()]);
            }

        }
    }

    //mai mult pentru testat lucruri
    //de exemplu coliziuni/ generare tiles pentru tilemap
    public void drawRectangleBorder(int offX, int offY, int width, int height, int color) {
        for (int y = 0; y <= height; y++) {
            setPixel(offX, y + offY, color);
            setPixel(offX + width, y + offY, color);
        }

        for (int x = 0; x <= width; x++) {
            setPixel(x + offX, offY, color);
            setPixel(x + offX, offY + height, color);
        }
    }

    public void drawRectangleShape(int offX, int offY, int width, int height, int color) {
        for (int y = 0; y <= height; y++) {
            for (int x = 0; x <= width; x++) {
                setPixel(x + offX,y+ offY, color);

            }
        }
    }

    public int getCamX() {
        return camX;
    }

    public void setCamX(int camX) {
        this.camX = camX;
    }

    public int getCamY() {
        return camY;
    }

    public void setCamY(int camY) {
        this.camY = camY;
    }
}