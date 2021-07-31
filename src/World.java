package src;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class World {

    public static List<Blocks> blocos = new ArrayList<Blocks>();

    public World() {
        for(int xx = 0; xx < 20; xx++) {
            blocos.add(new Blocks(xx*32, 0));
        }

        for(int xx = 0; xx < 20; xx++) {
            blocos.add(new Blocks(xx*32, 480-32));
        }

        for(int yy = 0; yy < 15; yy++) {
            blocos.add(new Blocks(0, yy*32));
        }

        for(int yy = 0; yy < 15; yy++) {
            blocos.add(new Blocks(640-32, yy*32));
        }

        blocos.add(new Blocks(96, 96));
        blocos.add(new Blocks(640-128, 96));
        blocos.add(new Blocks(640-128, 480-128));
        blocos.add(new Blocks(96, 480-128));
        blocos.add(new Blocks(320, 240));
        blocos.add(new Blocks(320, 176));
        blocos.add(new Blocks(256, 240));
        blocos.add(new Blocks(256, 176));
        blocos.add(new Blocks(288, 96));
        blocos.add(new Blocks(288, 480-128));
    }

    public static boolean isFree(int x, int y) {
        for(int i = 0; i < blocos.size(); i++) {
            Blocks blocoAtual = blocos.get(i);
            if(blocoAtual.intersects(new Rectangle(x, y, 32, 32))) {
                return false; 
            }
        }
        return true;
    }

    public void render(Graphics g) {
        for(int i = 0; i < blocos.size(); i++) {
            blocos.get(i).render(g);
        }
    }
}
