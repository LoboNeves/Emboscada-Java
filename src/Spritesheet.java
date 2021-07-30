package src;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Spritesheet {
    public static BufferedImage spritesheet;
    public static BufferedImage[] player_right;
    public static BufferedImage[] player_left;
    public static BufferedImage[] player_down;
    public static BufferedImage[] player_up;
    public static BufferedImage[] enemy_right;
    public static BufferedImage[] enemy_left;
    public static BufferedImage[] enemy_down;
    public static BufferedImage[] enemy_up;
    public static BufferedImage tileWall;

    public Spritesheet() {
        try {
            spritesheet = ImageIO.read(getClass().getResource("/spritesheet.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        player_right = new BufferedImage[2];
        player_left = new BufferedImage[2];
        player_down = new BufferedImage[2];
        player_up = new BufferedImage[2];

        enemy_right = new BufferedImage[2];
        enemy_left = new BufferedImage[2];
        enemy_down = new BufferedImage[2];
        enemy_up = new BufferedImage[2];

        player_right[0] = Spritesheet.getSprite(34, 11, 16, 16);
        player_right[1] = Spritesheet.getSprite(50, 11, 16, 16);
        
        player_left[0] = Spritesheet.getSprite(320, 221, 16, 16);
        player_left[1] = Spritesheet.getSprite(304, 221, 16, 16);
        
        player_down[0] = Spritesheet.getSprite(0, 11, 16, 16);
        player_down[1] = Spritesheet.getSprite(16, 11, 16, 16);
        
        player_up[0] = Spritesheet.getSprite(69, 11, 16, 16);
        player_up[1] = Spritesheet.getSprite(86, 11, 16, 16);

        enemy_right[0] = Spritesheet.getSprite(292, 244, 16, 16);
        enemy_right[1] = Spritesheet.getSprite(308, 244, 16, 16);
        
        enemy_left[0] = Spritesheet.getSprite(310, 263, 16, 16);
        enemy_left[1] = Spritesheet.getSprite(294, 263, 16, 16);
        
        enemy_down[0] = Spritesheet.getSprite(258, 244, 16, 16);
        enemy_down[1] = Spritesheet.getSprite(276, 244, 16, 16);
        
        enemy_up[0] = Spritesheet.getSprite(327, 244, 16, 16);
        enemy_up[1] = Spritesheet.getSprite(344, 244, 16, 16);

        tileWall = Spritesheet.getSprite(280, 221, 16, 16);
    }

    public static BufferedImage getSprite(int x, int y, int width, int height) {
        return spritesheet.getSubimage(x, y, width, height);
    }
}
