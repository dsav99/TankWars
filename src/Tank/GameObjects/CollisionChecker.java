package Tank.GameObjects;

import Tank.menus.SoundPlayer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class CollisionChecker {

    private gameObject object1;
    private gameObject object2;
    private ArrayList<Animation> explosions;

    private SoundPlayer soundPlayer ;


    public CollisionChecker(gameObject object1,gameObject object2){
        this.object1=object1;
        this.object2=object2;
        this.explosions=new ArrayList<>();
    }

    public void checkCollision(){
        if(object1 instanceof Tank && object2 instanceof UnbreakableWall){
            boolean moveLeft=true;
            boolean moveRight=true;
            boolean moveUp=true;
            boolean moveDown =true;


            Rectangle wallUp = new Rectangle(object1.getX(),object1.getY()+10,60,60);
            Rectangle wallDown = new Rectangle(object1.getX(),object1.getY()-10,60,60);
            Rectangle wallRight = new Rectangle(object1.getX()+10,object1.getY(),60,60);
            Rectangle wallLeft = new Rectangle(object1.getX()-10,object1.getY(),60,60);
            if(object2.getHitbox().intersects(wallUp)){
                moveUp=false;
            }
            if(object2.getHitbox().intersects(wallDown)){
                moveDown=false;
            }
            if(object2.getHitbox().intersects(wallRight)){
                moveRight=false;
            }
            if(object2.getHitbox().intersects(wallLeft)){
                moveLeft=false;
            }
            if(object2.getHitbox().intersects(object1.getHitbox())){
                ((Tank) object1).collisionWithWall(moveDown,moveLeft,moveRight,moveUp);
            }

        }
        if(object1 instanceof Tank && object2 instanceof BreakableWall){

            boolean moveLeft=true;
            boolean moveRight=true;
            boolean moveUp=true;
            boolean moveDown =true;


            Rectangle wallUp = new Rectangle(object1.getX(),object1.getY()+10,60,60);
            Rectangle wallDown = new Rectangle(object1.getX(),object1.getY()-10,60,60);
            Rectangle wallRight = new Rectangle(object1.getX()+10,object1.getY(),60,60);
            Rectangle wallLeft = new Rectangle(object1.getX()-10,object1.getY(),60,60);
            if(object2.getHitbox().intersects(wallUp)){
                moveUp=false;
            }
            if(object2.getHitbox().intersects(wallDown)){
                moveDown=false;
            }
            if(object2.getHitbox().intersects(wallRight)){
                moveRight=false;
            }
            if(object2.getHitbox().intersects(wallLeft)){
                moveLeft=false;
            }
            if(object2.getHitbox().intersects(object1.getHitbox())){
                ((Tank) object1).collisionWithWall(moveDown,moveLeft,moveRight,moveUp);
            }
        }
        if(object1 instanceof Tank && object2 instanceof Health){
            if (object1.getHitbox().intersects(object2.getHitbox())){
                ((Tank) object1).addHealth();
                object2.collided();
                //System.out.println("Add health to tank");
            }
        }
        if(object1 instanceof Tank && object2 instanceof Bullet){

            if(object1.getHitbox().intersects(object2.getHitbox())) {
                String tankName = ((Tank) object1).getPlayer();
                String bulletOwner = ((Bullet) object2).getOwner();

                if (tankName.equals(bulletOwner)) {
                    //Nothing to do here
                } else {
                    //Decrease Tank health;
                    object2.collided();
                    ((Tank) object1).collisionWithBullet();
                    //System.out.println("Decrease tank health");

                }
            }
        }

        if(object1 instanceof Bullet && object2 instanceof BreakableWall){
           // Break the Wall and remove the bullet.

            if(object1.getHitbox().intersects(object2.getHitbox())) {
                ((Bullet)object1).collided();
                object2.collided();
                //System.out.println("Break wall and remove bullet");
            }
        }

        if(object1 instanceof Bullet && object2 instanceof UnbreakableWall){
            // Break the Wall and remove the bullet.

            if(object1.getHitbox().intersects(object2.getHitbox())){
                (object1).collided();
                object2.collided();
                //System.out.println("Break wall and remove bullet");
            }

        }
    }
}
