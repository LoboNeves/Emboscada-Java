package src;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class Player extends Rectangle{

    public int spd = 4;
    public boolean right, up, down, left;

    public int curAnimation = 0;
    public int curFrames = 0, targetFrames = 15;

    public static List<Bullet> bullets = new ArrayList<Bullet>();
    public boolean shoot = false;

    public int dir = 1;

    public Player(int x, int y) {
        super(x, y, 32, 32);
    }

    public static boolean Hit(int x, int y) {
        for(int i = 0; i < Player.bullets.size(); i++) {
            Bullet CurBullet = bullets.get(i);
            if(CurBullet.intersects(new Rectangle(x, y, 32, 32))) {
                bullets.remove(CurBullet);
                return true; 
            }
        }
        return false;
    }

    public void tick() {
        boolean moved = false;
        if(right && World.isFree(x+spd, y)) {
            x+=spd;
            moved = true;
            dir = 1;
        }
        else if (left && World.isFree(x-spd, y)) {
            x-=spd;
            moved = true;
            dir = 2;
        }

        if(up && World.isFree(x, y-spd)) {
            y-=spd;
            moved = true;
            dir = 3;
        }
        else if (down && World.isFree(x, y+spd)) {
            y+=spd;
            moved = true;
            dir = 4;
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

        for(int i = 0; i < Game.enemys.size(); i++) {
            if(Hit(Game.enemys.get(i).x, Game.enemys.get(i).y)) {
                Game.enemys.remove(Game.enemys.get(i));
            }
        }
    }

    public void render(Graphics g) {
        //g.setColor(Color.blue);
        //g.fillRect(x, y, width, height);
        if(dir == 1) g.drawImage(Spritesheet.player_right[curAnimation], x, y, 32, 32, null);
        else if(dir == 2) g.drawImage(Spritesheet.player_left[curAnimation], x, y, 32, 32, null);
        else if(dir == 3) g.drawImage(Spritesheet.player_up[curAnimation], x, y, 32, 32, null);
        else if(dir == 4) g.drawImage(Spritesheet.player_down[curAnimation], x, y, 32, 32, null);
        
        for(int i = 0; i < bullets.size(); i++) {
            bullets.get(i).render(g);
        }
    }
}
