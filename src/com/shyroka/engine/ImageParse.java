package com.shyroka.engine;

public class ImageParse extends Image
{
    int sourceW,sourceH;

    public ImageParse(String path , int sourceW, int sourceH)
    {
        super(path);
        this.sourceH = sourceH;
        this.sourceW = sourceW;
    }

    public int getSourceH()
    {
        return sourceH;
    }

    public void setSourceH(int sourceH)
    {
        this.sourceH = sourceH;
    }

    public int getSourceW()
    {
        return sourceW;
    }

    public void setSourceW(int sourceW)
    {
        this.sourceW = sourceW;
    }

}