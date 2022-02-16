package Tank.GameObjects;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Animation extends gameObject implements  Runnable{

    private int x,y;
    private BufferedImage image;
    private int explosionTime=0;

    public Animation(int x,int y){
        super();
        this.x=x;
        this.y=y;
        try{
            image= ImageIO.read(this.getClass().getClassLoader().getResource("Explosion_large.gif"));
        }
        catch(Exception e){

        }
    }

    @Override
    public void update() {

    }

    @Override
    public void drawImage(Graphics g) {

            Graphics2D g2d = (Graphics2D) g;
            g2d.drawImage(this.image, this.x, this.y, null);

    }

    @Override
    public void run() {


        while(explosionTime<=4){
            try{
                explosionTime++;
                Thread.sleep(100);
            } catch (Exception e){
                System.out.println(e);
            }
        }
        if(explosionTime>4){
            this.collided=true;
        }

    }
}
