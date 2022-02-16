package Tank.menus;
import Tank.GameConstants;
import Tank.Launcher;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;


public class EndGamePanel extends JPanel {
    private BufferedImage menuBackground;
    private JButton restart;
    private JButton exit;
    private Launcher lf;

    public EndGamePanel(Launcher lf){
        this.lf=lf;
        try{
            menuBackground = ImageIO.read(this.getClass().getClassLoader().getResource("Title.bmp"));
        }catch(Exception e){
            System.out.println("Error while loading end Screen");
        }

        this.setBackground(Color.BLACK);
        this.setLayout(null);

        restart = new JButton("Restart");
        restart.setSize(new Dimension(200,100));
        restart.setBounds(150,300,100,50);
        restart.addActionListener(e -> {
            lf.setFrame("game");
        });

        exit = new JButton("exit");
        exit.setSize(new Dimension(200,100));
        exit.setBounds(150,400,150,50);
        exit.addActionListener(e -> {
            this.lf.closeGame();
        });

        this.add(restart);
        this.add(exit);
    }

    @Override
    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.menuBackground,0,0,null);
    }

}
