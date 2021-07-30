package src;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class Enemy extends Rectangle{

    public int spd = 4;
    public int right = 1, up = 0, down = 0, left = 0;

    public int curAnimation = 0;
    public int curFrames = 0, targetFrames = 15;

    public static List<Bullet> bullets = new ArrayList<Bullet>();
    public boolean shoot = false;

    public int dir = 1;

    public Enemy(int x, int y) {
        super(x, y, 32, 32);
    }

    public void tick() {
        boolean moved = true;
        if(right == 1 && World.isFree(x+spd, y)) {
            x+=spd;
        }

        if(moved){
            curFrames++;
            if(curFrames == targetFrames) {
                curFrames = 0;
                curAnimation++;
                if(curAnimation == 2) {
                    curAnimation = 0;
                }
            }
        }

        if(shoot) {
            shoot = false;
            bullets.add(new Bullet(x, y, dir));
        }

        for(int i = 0; i < bullets.size(); i++) {
            bullets.get(i).tick();
        }
    }

    public void render(Graphics g) {
        //g.setColor(Color.blue);
        //g.fillRect(x, y, width, height);
        if(dir == 1) g.drawImage(Spritesheet.enemy_right[curAnimation], x, y, 32, 32, null);
        else if(dir == 2) g.drawImage(Spritesheet.enemy_left[curAnimation], x, y, 32, 32, null);
        else if(dir == 3) g.drawImage(Spritesheet.enemy_up[curAnimation], x, y, 32, 32, null);
        else if(dir == 4) g.drawImage(Spritesheet.enemy_down[curAnimation], x, y, 32, 32, null);
        
        for(int i = 0; i < bullets.size(); i++) {
            bullets.get(i).render(g);
        }
    }
}
