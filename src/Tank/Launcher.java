package Tank;

import Tank.menus.EndGamePanel;
import Tank.menus.StartGamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

public class Launcher {

    /*  Main panel in JFrame. This will have Card Layout. */
    private JPanel mainPanel;

    /* Start menu */
    private JPanel startPanel;

    /* End menu */
    private JPanel endPanel;

    /* Stores our main panel. All event listeners are attached to this.*/
    private JFrame jFrame;

    /* game panel is used to show our game to the screen.
    * It also contains the game loop.This is where objects are updated and redrawn.
    * This panel executes game loop on a different thread for smooth GUI. */
    private TankGame gamePanel;

    /* Card layout is used to manage our sub-panels. This is a layout
    * manager used for our game. It will be attached to the main panel. */
    private CardLayout cardLayout;

    public Launcher(){
        this.jFrame = new JFrame("Tank Wars Game"); //Make a screen window
        this.jFrame.setLocationRelativeTo(null); //Centers the screen
        this.jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Closes VM when game is closed.
    }

    public void initUIComponents(){
        //Load Start,end,game screens
        // Add all to main panel.
        //Add main panel to JFrame
        //Set frame to Start menu

       // this.jFrame.setVisible(true);
        this.mainPanel = new JPanel();
        this.startPanel = new StartGamePanel(this);
        this.gamePanel = new TankGame(this);
        this.gamePanel.gameInitialize();
        this.endPanel = new EndGamePanel(this);
        cardLayout = new CardLayout();
        this.jFrame.setResizable(false);
        this.mainPanel.setLayout(cardLayout);
        this.mainPanel.add(startPanel,"start");
        this.mainPanel.add(gamePanel,"game");
        this.mainPanel.add(endPanel,"end");
        this.jFrame.add(mainPanel);
        this.setFrame("start");
    }

    public void setFrame(String frame){
        this.jFrame.setVisible(false);
        switch(frame){
            case "start":
                this.jFrame.setSize(GameConstants.START_MENU_SCREEN_WIDTH,GameConstants.START_MENU_SCREEN_HEIGHT);
                break;
            case "end":
                this.jFrame.setSize(GameConstants.END_MENU_SCREEN_WIDTH,GameConstants.END_MENU_SCREEN_HEIGHT);
                break;
            case "game":
                this.jFrame.setSize(GameConstants.GAME_SCREEN_WIDTH,GameConstants.GAME_SCREEN_HEIGHT);
                (new Thread(this.gamePanel)).start();
                break;
        }

        this.cardLayout.show(mainPanel, frame);
        this.jFrame.setVisible(true);
    }


    public void closeGame(){
        this.jFrame.dispatchEvent(new WindowEvent(this.jFrame, WindowEvent.WINDOW_CLOSING));
    }

    public JFrame getJf() {
        return jFrame;
    }

    public static void main(String[] args) {

        Launcher launch = new Launcher();
        launch.initUIComponents();
    }
}
