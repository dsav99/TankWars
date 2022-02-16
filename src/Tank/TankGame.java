package Tank;

import Tank.GameObjects.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class TankGame extends JPanel implements Runnable {

    private BufferedImage world;
    private BufferedImage breakableWall;
    private BufferedImage unbreakableWall;
    private BufferedImage powerUp;
    private BufferedImage background;
    private Tank t1;
    private Tank t2;
    private ArrayList<gameObject> walls;
    private ArrayList<gameObject> bg;
    private ArrayList<gameObject> t1bullets;
    private ArrayList<gameObject> t2bullets;
    private ArrayList<Animation> explosions;
    private ArrayList<gameObject> activeObjects;


    final private int gameScreenWidth = 1290;
    final private int gameScreenHeight = 960;




    private Launcher lf;
    private long tick=0;


    public  void addBullet(Bullet bullet){
        activeObjects.add(bullet);
    }

    public TankGame(Launcher lf){
        this.lf=lf;
        this.walls = new ArrayList<>();
        this.bg = new ArrayList<>();
        this.activeObjects = new ArrayList<>();
    }

    @Override
    public void run() {



        try{
            while (true) {

                for(int i=0;i<activeObjects.size();i++){
                    gameObject gameObject = activeObjects.get(i);
                    if(gameObject.isActive()){
                        gameObject.update();
                    }
                    else{
                        activeObjects.remove(i);
                    }
                }

                checkCollisions();
                this.tick++;

                this.repaint();   // redraw game
                Thread.sleep(1000 / 144); //sleep for a few milliseconds
                /*
                 * simulate an end game event
                 * we will do this with by ending the game when drawn 2000 frames have been drawn
                 */
                if(this.tick > 200000){
                    tick=0;
                    this.lf.setFrame("end");
                    return;
                }
            }
        } catch(Exception e){
            tick=0;
            System.out.println(e.getMessage());
        }

    }

    public void checkCollisions(){

       for(gameObject gameObject:activeObjects){
           for (gameObject gameObject1:activeObjects){
               if(!gameObject.equals(gameObject1)){
                   CollisionChecker collisionChecker = new CollisionChecker(gameObject,gameObject1);
                   collisionChecker.checkCollision();
               }
           }
       }




    }

    public void gameInitialize() {
        this.world = new BufferedImage(gameScreenWidth,
                gameScreenHeight,
                BufferedImage.TYPE_INT_RGB);

        BufferedImage t1img = null;
        BufferedImage t2img = null;
        try{
            t1img =ImageIO.read(this.getClass().getClassLoader().getResource("tank1.png"));
            t2img =ImageIO.read(this.getClass().getClassLoader().getResource("tank2.png"));

            breakableWall = ImageIO.read(this.getClass().getClassLoader().getResource("Wall2.gif"));
            unbreakableWall = ImageIO.read(this.getClass().getClassLoader().getResource("Wall1.gif"));
            powerUp = ImageIO.read(this.getClass().getClassLoader().getResource("powerup.png"));
            background = ImageIO.read(this.getClass().getClassLoader().getResource("Background.png"));


            BufferedReader mapReader = new BufferedReader(new InputStreamReader(TankGame.class.getClassLoader().getResourceAsStream("maps/map")));


            String row = mapReader.readLine();

            String[] mapInfo;
            int numRows = 32;
            int numColumns = 43;

            for(int curRow=0;curRow<numRows;curRow++){
                row = mapReader.readLine();
                mapInfo = row.split("\t");
                for(int curCol=0;curCol<numColumns;curCol++){
                    switch (mapInfo[curCol]){
                        case "2": //breakable wall
                            BreakableWall wall =  new BreakableWall(curCol*30,curRow*30,breakableWall);
                            activeObjects.add(wall);
                            break;
                        case "9": // border wall
                        case "3": //unbreakable wall
                            UnbreakableWall wall1 = new UnbreakableWall(curCol*30,curRow*30,unbreakableWall);
                            activeObjects.add(wall1);
                            break;
                        case "1":
                            Health health = new Health(curCol*30,curRow*30, powerUp);
                            activeObjects.add(health);
                            break;


                    }
                }
            }

        } catch(Exception e){
            System.out.println("Error loading tank 1 image");
        }



        t1 = new Tank(100,100,0,0,0,t1img,"one",this);
        TankControl tc1 = new TankControl(t1, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER);
        this.lf.getJf().addKeyListener(tc1);
        t2 = new Tank(600,700,0,0,180,t2img,"two",this);
        TankControl tc2 = new TankControl(t2, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SPACE);
        this.lf.getJf().addKeyListener(tc2);
        activeObjects.add(t1);
        activeObjects.add(t2);

        this.setBackground(Color.BLACK);
    }






    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Graphics2D buffer = world.createGraphics();
        buffer.setColor(Color.WHITE);
        buffer.fillRect(0,0,GameConstants.gameScreenWidth,GameConstants.gameScreenHeight);
        buffer.setColor(Color.BLACK);
        for(int i=-1;i<5;i++){
            for(int j=0;j<4;j++){
                    buffer.drawImage(background,i*background.getWidth(),j*background.getHeight(),null);
            }
        }




        for (gameObject gameObject:activeObjects){

            gameObject.drawImage(buffer);
        }

        //Rectangle rectangle = new Rectangle(t1.getX()-50,t1.getY()-50,100,100);


        int sub1x=t1.getX();
        int sub1y=t1.getY();
        int sub2x=t2.getX();
        int sub2y=t2.getY();

        if(sub1x-GameConstants.GAME_SCREEN_WIDTH/2<0){
            sub1x=0;
        }

        if(sub1y-GameConstants.GAME_SCREEN_HEIGHT/2<0){
            sub1y=0;
        }

        if(sub1x+GameConstants.GAME_SCREEN_WIDTH/2>gameScreenWidth){
            sub1x=gameScreenWidth-GameConstants.GAME_SCREEN_WIDTH/2;
        }
        if(sub1y+GameConstants.GAME_SCREEN_HEIGHT>gameScreenHeight){
            sub1y=gameScreenHeight-GameConstants.GAME_SCREEN_HEIGHT+120;
        }



        if(sub2x-GameConstants.GAME_SCREEN_WIDTH/2<0){
            sub1x=0;
        }

        if(sub2y-GameConstants.GAME_SCREEN_HEIGHT/2<0){
            sub1y=0;
        }

        if(sub2x+GameConstants.GAME_SCREEN_WIDTH/2>gameScreenWidth){
            sub2x=gameScreenWidth-GameConstants.GAME_SCREEN_WIDTH/2;
        }
        if(sub2y+GameConstants.GAME_SCREEN_HEIGHT>gameScreenHeight){
            sub2y=gameScreenHeight-GameConstants.GAME_SCREEN_HEIGHT+120;
        }












        //g2.drawImage(world,0,100,gameScreenWidth,gameScreenHeight-100,null);
        //BufferedImage one = this.world.getSubimage(t1.getX(),t1.getY(),gameScreenWidth, gameScreenHeight);
        BufferedImage tankOne = this.world.getSubimage(sub1x,sub1y,GameConstants.GAME_SCREEN_WIDTH/2,GameConstants.GAME_SCREEN_HEIGHT-120);
        BufferedImage tankTwo = this.world.getSubimage(sub2x,sub2y,GameConstants.GAME_SCREEN_WIDTH/2,GameConstants.GAME_SCREEN_HEIGHT-120);

//        System.out.println(t1.getX()+gameScreenWidth/2);
//        System.out.println(t1.getY()+gameScreenHeight);
        g2.drawImage(tankOne,0,120,null);
        g2.drawImage(tankTwo,GameConstants.GAME_SCREEN_WIDTH/2+10,120,null);
        BufferedImage miniMap = this.world.getSubimage(0, 0, gameScreenWidth, gameScreenHeight);
       // g2.drawImage(one,0,100,100,100,null);
        g2.drawImage(miniMap.getScaledInstance(gameScreenWidth * 1 / 10, gameScreenWidth * 1 / 10, BufferedImage.TYPE_INT_RGB), gameScreenWidth * 7 / 20, 0, null);


//        BufferedImage subImage = this.world.getSubimage(0,100,gameScreenWidth,gameScreenHeight);
//        g2.drawImage(world,0,100,500,500,null);
        //g2.drawImage(world,0,100,t1.getX()+100,t1.getY()+100,null);


        g2.setColor(Color.WHITE);
        g2.drawString("Tank 1 Lives Remaining: " , 50,40);
        g2.drawRect(50 - 1, 60 -1, 102, 22);
        g2.setColor(Color.GREEN);
        int i =this.t1.getTankHealth();
        g2.drawRect(50, 60, 10, 20);
        g2.fillRect(50,60,(100*i)/5,20);

        g2.setColor(Color.WHITE);
        g2.drawString("Tank 2 Lives Remaining: " , GameConstants.gameScreenWidth/2+100,40);
        g2.drawRect(GameConstants.gameScreenWidth/2+100 - 1, 60 -1, 102, 22);
        g2.setColor(Color.GREEN);
        int j =this.t2.getTankHealth();
        g2.drawRect(GameConstants.gameScreenWidth/2+100, 60, 10, 20);
        if(j!=0)
        g2.fillRect(GameConstants.gameScreenWidth/2+100,60,(100*j)/5,20);



    }
}
