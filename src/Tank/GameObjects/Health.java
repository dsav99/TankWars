package Tank.GameObjects;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Health extends gameObject{
    public Health(int x, int y, BufferedImage image) {
        super(x, y, 0, image);
    }

    @Override
    public void update() {


    }



    @Override
    public void drawImage(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.image, this.x, this.y, null);
    }
}
