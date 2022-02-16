package Tank.menus;

import Tank.Launcher;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class StartGamePanel extends JPanel {
    private BufferedImage menuBackground;
    private JButton start;
    private JButton exit;
    private Launcher lf;

    public StartGamePanel(Launcher lf){
        this.lf=lf;
        try{
            menuBackground = ImageIO.read(this.getClass().getClassLoader().getResource("Title.bmp"));
        }  catch(Exception e){
            System.out.println("Error while loading start menu screen");
        }

        this.setBackground(Color.BLACK);
        this.setLayout(null);

        start = new JButton("Start");
        start.setSize(new Dimension(200,100));
        start.setBounds(150,300,150,50);
        start.addActionListener((e -> {
            this.lf.setFrame("game");
        }));

        exit = new JButton("exit");
        exit.setSize(new Dimension(200,100));
        exit.setBounds(150,400,150,50);
        exit.addActionListener(e -> {
            this.lf.closeGame();
        });

        this.add(start);
        this.add(exit);
    }

    @Override
    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.menuBackground,0,0,null);
    }
}
