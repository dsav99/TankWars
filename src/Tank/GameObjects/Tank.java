package Tank.GameObjects;

import Tank.GameConstants;
import  Tank.TankGame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Tank extends gameObject {

    private int vx,vy;
    private ArrayList<gameObject> ammo;
    int timeOfFirstBullet = LocalDateTime.now().toLocalTime().toSecondOfDay();
    private String player;
    private BufferedImage bigExp;
    private int explosionTime;


    private TankGame game;

    private int tankHealth =5;
    public ArrayList<gameObject> getAmmo() {
        return ammo;
    }
    @Override
    public Rectangle getHitbox(){
        return this.hitbox;
    }

    private BufferedImage bulletImage;


    final private int R=2;
    final float rotationSpeed = 3.0f;



    boolean UpPressed;
    boolean LeftPressed;
    boolean RightPressed;
    boolean DownPressed;
    boolean shootPressed;

    public String getPlayer() {
        return player;
    }

    public int getTankHealth() {
        return tankHealth;
    }

    public Tank(int x, int y, int vx, int vy, float angle, BufferedImage image, String player, TankGame game){
        super(x,y,angle,image);
        this.game = game;
        explosionTime=0;
        try{
            bulletImage = ImageIO.read(getClass().getClassLoader().getResource("Weapon.gif"));
            bigExp = ImageIO.read(this.getClass().getClassLoader().getResource("Explosion_large.gif"));

        } catch(Exception e){
            System.out.println("Error loading bullet image!");
        }
        this.player=player;
        ammo = new ArrayList<>();

    }


    public void toggleUpPressed() {
        this.UpPressed = true;
    }

    public void toggleDownPressed() {
        this.DownPressed = true;
    }

    public void toggleRightPressed() {
        this.RightPressed = true;
    }

    public void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    public void toggleShootPressed(){
        this.shootPressed=true;
    }

    public void unToggleUpPressed() {
        this.UpPressed = false;
    }

    public void unToggleDownPressed() {
        this.DownPressed = false;
    }

    public void unToggleRightPressed() {
        this.RightPressed = false;
    }

    public void unToggleLeftPressed() {
        this.LeftPressed = false;
    }

    public void unToggleShootPressed(){
        this.shootPressed=false;
    }




     void moveForward() {

        vx = (int) Math.round(R * Math.cos(Math.toRadians(this.angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(this.angle)));
        this.x+=vx;
        this.y+=vy;

    }

     void moveBackward() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(this.angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(this.angle)));
        this.x-=vx;
        this.y-=vy;
    }


    void rotateLeft() {
        if(angle<=0)
            angle +=360;

        this.angle-=rotationSpeed;
    }

     void rotateRight() {

         if(angle>=360)
             angle-=360;
         this.angle+=rotationSpeed;
     }

    void shoot(){
        int timeOfSecondBullet = LocalDateTime.now().toLocalTime().toSecondOfDay()+4;
        if(timeOfSecondBullet-timeOfFirstBullet>=4) {
            Bullet bullet = new Bullet(this, bulletImage);
            this.game.addBullet(bullet);
            timeOfFirstBullet=timeOfSecondBullet;
        }
    }

    public void addHealth(){
        this.tankHealth++;
    }

    @Override
    public void update() {
        this.hitbox=new Rectangle(this.x,this.y,image.getWidth(),image.getHeight());
        if (this.UpPressed) {
            this.moveForward();
        }
        if (this.DownPressed) {
            this.moveBackward();
        }

        if (this.LeftPressed) {
            this.rotateLeft();
        }
        if (this.RightPressed) {
            this.rotateRight();
        }
        if(this.shootPressed){

            this.shoot();
        }
        checkBorder();
    }

    public void collisionWithBullet(){
        tankHealth--;
        System.out.println("Tank health decreased to: "+tankHealth);
    }


    public void collisionWithWall(boolean down,boolean left,boolean right,boolean up){
        if(down){
            if(angle<180){
                moveBackward();
            }
            else{
                moveForward();
            }

        }
        if(left){
            if(angle>90 && angle<270){
                moveForward();
            }
            else{
                moveBackward();
            }

        }
        if(right){
            if(angle>90 && angle<270){
                moveBackward();
            }
            else{
                moveForward();
            }

        }
        if(up){
            if(angle>180){
                moveBackward();
            }
            else{
                moveForward();
            }
        }

    }


    private void checkBorder() {
        if (this.x < 30) {
            this.x=30;
        }
        if (this.x >= GameConstants.gameScreenWidth - 88) {
            this.x=(GameConstants.gameScreenWidth - 88);
        }
        if (this.y < 40) {
            this.y=(40);
        }
        if (this.y >= GameConstants.gameScreenHeight - 120) {
            this.y=(GameConstants.gameScreenHeight - 120);
        }
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public float getAngle(){
        return this.angle;
    }


    @Override
    public boolean isActive(){
        if(tankHealth>0){
            return true;
        }
        else{
            this.image =bigExp;
            explosionTime++;
            try{
                Thread.sleep(10);
            } catch(Exception e){
                System.out.println("dsffsd");
            }

            return false;
        }
    }




    @Override
    public void drawImage(Graphics g) {
//        for(gameObject gameObject:ammo){
//            gameObject.drawImage(g);
//        }
        AffineTransform rotation = AffineTransform.getTranslateInstance(this.x, this.y);
        rotation.rotate(Math.toRadians(this.angle), this.image.getWidth() / 2.0, this.image.getHeight() / 2.0 );
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.image, rotation, null);
    }
}
