package Tank.GameObjects;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UnbreakableWall extends gameObject{
    public UnbreakableWall(int x, int y,  BufferedImage image) {
        super(x, y, 0, image);
    }



    @Override
    public void update() {

    }

    @Override
    public boolean isActive(){
        return true;
    }

    @Override
    public void drawImage(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.image, this.x, this.y, null);
    }
}
