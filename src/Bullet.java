package src;

import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Graphics;

public class Bullet extends Rectangle{
    
    public int dir = 1;
    public int speed = 8;
    public int frames = 0;

    public Bullet(int x, int y, int dir) {
        super(x + 16, y + 16, 10, 10);
        this.dir = dir;
    }

    public void tick() {
        if(dir == 1) x += speed*1;
        else if (dir == 2) x += speed*-1;
        else if (dir == 3) y += speed*-1;
        else if (dir == 4) y += speed*1;

        frames++;
        if(frames == 60) {
            Player.bullets.remove(this);
            return;
        }
    }

    public void render(Graphics g) {
        g.setColor(Color.yellow);
        g.fillOval(x, y, width, height);
    }
}
