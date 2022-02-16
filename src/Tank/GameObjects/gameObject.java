package Tank.GameObjects;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.security.PrivateKey;

public abstract class gameObject {
     int x,y; // Position of Object
     float angle;
     BufferedImage image; //Object Image
     Rectangle hitbox;
     boolean collided = false;
     boolean active=true;


    public gameObject(int x,int y,float angle,BufferedImage image){
        this.x=x;
        this.y=y;
        this.hitbox = new Rectangle(x,y,image.getWidth(),image.getHeight());
        this.angle=angle;
        this.image = image;

    }

    public gameObject() {

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Rectangle getHitbox(){
        return this.hitbox;
    }

    public boolean isActive(){
        if(collided){
            return false;
        }
        else{
            return true;
        }
    }

    public void collided(){
        this.collided=true;
    }


    abstract public void update();
     abstract public void drawImage(Graphics g);

}
