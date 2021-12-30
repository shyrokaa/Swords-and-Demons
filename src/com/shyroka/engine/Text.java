package com.shyroka.engine;

public class Text {
    public static final Text STANDARD = new Text("/Assets/text_tile.png");

    private Image textImage;
    private int[] offset;
    private int[] widths;

    public Text(String path)
    {

        textImage = new Image(path);
        offset = new int[59];
        widths = new int[59];
        int unicode = 0;

        for (int i = 0; i < textImage.getWidth(); i++)
        {
            if(textImage.getP()[i] == 0xff0000ff)
            {
                offset[unicode] = i;
            }
            if(textImage.getP()[i] == 0xffffff00)
            {
                widths[unicode] = i - offset[unicode];
                unicode++;
            }
        }
    }


    public Image getTextImage() {
        return textImage;
    }

    public void setTextImage(Image textImage) {
        this.textImage = textImage;
    }

    public int[] getWidths() {
        return widths;
    }

    public void setWidths(int[] width) {
        this.widths = width;
    }

    public int[] getOffset() {
        return offset;
    }

    public void setOffset(int[] offset) {
        this.offset = offset;
    }

}
