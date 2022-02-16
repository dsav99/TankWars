package Tank.GameObjects;

import Tank.GameConstants;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Bullet extends gameObject {
    private Tank tank;
    private int vx=0,vy=0;
    final private int R=5;
    private String owner ;
    private BufferedImage exp;
    private BufferedImage bigExp;
    private int explosionTime;


    public Bullet(Tank tank,BufferedImage image){
        super(tank.getX(),tank.getY(), tank.angle, image);
        this.owner=tank.getPlayer();
        explosionTime=0;
        try{
            exp = ImageIO.read(this.getClass().getClassLoader().getResource("Explosion_small.gif"));
            bigExp = ImageIO.read(this.getClass().getClassLoader().getResource("Explosion_large.gif"));
        } catch (Exception e ){
            System.out.println("Bullet explosion error");
        }

    }

    public String getOwner(){
        return this.owner;
    }

    private void moveForward(){
        vx = (int) Math.round(R * Math.cos(Math.toRadians(this.angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(this.angle)));
        this.x+=vx;
        this.y+=vy;
    }

    @Override
    public void update() {
        if(!collided) {

            moveForward();
            this.hitbox = new Rectangle(this.x, this.y, image.getWidth(), image.getHeight());
        }
    }


    @Override
    public void collided(){

        this.image=exp;
        Explosion explosion = new Explosion("Explosion_small.wav");
        (new Thread(explosion)).start();
            explosionTime=0;

            while(explosionTime<=4){

                try {
                    explosionTime++;
                    collided=true;
                    Thread.sleep(10);
                }catch (Exception e){
                    System.out.println("Error efewf");
                }
            }


        explosionTime=0;

    }


    @Override
    public void drawImage(Graphics g) {

            AffineTransform rotation = AffineTransform.getTranslateInstance(this.x, this.y);
            rotation.rotate(Math.toRadians(this.angle), this.image.getWidth() / 2.0, this.image.getHeight() / 2.0);
            Graphics2D g2d = (Graphics2D) g;
            g2d.drawImage(this.image, rotation, null);

    }
}
