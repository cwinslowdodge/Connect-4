package cis163.connect4;


import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;


public class Disk extends JPanel {
    private final static int cellSize = 80;
    private final static int diskSize = 60;
    public Color diskColor;
    public JLabel label = new JLabel();


    /**
     * Constructor that will create a new Circle.
     */
    public Disk(){
        Dimension dimensions = new Dimension(cellSize, cellSize);
        setPreferredSize(dimensions);
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.WHITE));
        diskColor = new Color(255, 255, 255);

        add(label);
    }

    public Disk(Color c){
        Dimension dimensions = new Dimension(cellSize, cellSize);
        setPreferredSize(dimensions);
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, Color.WHITE));
        diskColor = c;
    }


    @Override
    public void paintComponent(Graphics g){
        int w = this.getSize().width;
        int h = this.getSize().height;
        int w1 = (w - diskSize)/2;
        int h1 = (h - diskSize)/2;


        g.setColor(diskColor);
        g.fillOval(w1, h1, diskSize, diskSize);
    }

}
