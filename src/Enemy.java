package src;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Enemy extends Rectangle{

    public int spd = 2;
    public int right = 1, up = 0, down = 0, left = 0;

    public int curAnimation = 0;
    public int curFramesMovement = 0, targetFramesMovement = 15;
    public int curFramesShoot = 0, targetFramesShoot = 15;

    public List<Bullet> bullets = new ArrayList<Bullet>();
    public boolean shoot = false;

    public int dir = 4;

    public Enemy(int x, int y) {
        super(x, y, 32, 32);
    }

    public void perseguirPlayer() {
        Player p = Game.player;
        if(x < p.x && World.isFree(x+spd, y)) {
            dir = 1;
            if(new Random().nextInt(100) < 50) {
                x += spd;
                this.shoot = true;
            }
        }
        else if(x > p.x && World.isFree(x-spd, y)) {
            dir = 2;
            if(new Random().nextInt(100) < 50) {
                x -= spd;
                this.shoot = true;
            }
        }

        if(y < p.y && World.isFree(x, y+spd)) {
            dir = 4;
            if(new Random().nextInt(100) < 50) {
                y += spd;
                this.shoot = true;
            }
        }
        else if(y > p.y && World.isFree(x, y-spd)) {
            dir = 3;
            if(new Random().nextInt(100) < 50) {
                y -= spd;
                this.shoot = true;
            }
        }
    }

    public boolean Hit(int x, int y) {
        for(int i = 0; i < bullets.size(); i++) {
            Bullet CurBullet = bullets.get(i);
            if(CurBullet.intersects(new Rectangle(x, y, 32, 32))) {
                bullets.remove(CurBullet);
                return true; 
            }
        }
        return false;
    }

    public void tick() {
        boolean moved = true;

        perseguirPlayer();

        if(moved){
            curFramesMovement++;
            if(curFramesMovement == targetFramesMovement) {
                curFramesMovement = 0;
                curAnimation++;
                if(curAnimation == 2) {
                    curAnimation = 0;
                }
            }
        }

        if(shoot) {
            shoot = false;
            curFramesShoot++;
            if(curFramesShoot == targetFramesShoot) {
                curFramesShoot = 0;
                bullets.add(new Bullet(x, y, dir));
            }
        }

        for(int i = 0; i < bullets.size(); i++) {
            bullets.get(i).tick();
        }

        if(Hit(Game.player.x, Game.player.y)) {
            Game.gameState = "GAME_OVER";
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
