package com.shyroka.engine;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;


public class Window
{
    private JFrame frame;
    private BufferedImage image;
    private Canvas canvas;
    private BufferStrategy bs;
    private Graphics g;

    public Window(CoreGame core)
    {
        image = new BufferedImage(core.getWidth() , core.getHeight(), BufferedImage.TYPE_INT_RGB);
        canvas = new Canvas();
        Dimension size = new Dimension((int)(core.getWidth() * core.getScale()), (int)(core.getHeight() * core.getScale()));
        canvas.setPreferredSize(size);
        canvas.setMaximumSize(size);
        canvas.setMinimumSize(size);


        frame = new JFrame(core.getTitle());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(canvas, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        canvas.createBufferStrategy(2);
        bs = canvas.getBufferStrategy();
        g = bs.getDrawGraphics();


    }

    public void Update()
    {
        g.drawImage(image,0,0,canvas.getWidth(),canvas.getHeight(),null);
        bs.show();
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public BufferedImage getImage() {
        return image;
    }

    public JFrame getFrame() {
        return frame;
    }
}
