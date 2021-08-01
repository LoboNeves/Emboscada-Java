package src;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable, KeyListener{
    
    public static int WIDTH = 640, HEIGHT = 480;
    public static int SCALE = 3;
    public static Player player;

    public World world;

    public static String gameState = "GAME_TITLE";
    private boolean showMessageGameOver = false;
    private int framesGameOver = 0;
    private boolean restartGame = false;

    public static List<Enemy> enemys = new ArrayList<Enemy>();

    public Game() {
        this.addKeyListener(this);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        new Spritesheet();

        world = new World();

        player = new Player(288, 208);

        enemys.add(new Enemy(32, 32));
        enemys.add(new Enemy(640-64, 32));
        enemys.add(new Enemy(640-64, 480-64));
        enemys.add(new Enemy(32, 480-64));
    }

    public void tick() {
        if(gameState == "NORMAL") {
            this.restartGame = false;
            player.tick();

            for(int i = 0; i < enemys.size(); i++) {
                enemys.get(i).tick();
            }
    
            for(int i = 0; i < World.blocos.size(); i++) {
                Blocks curBlock = World.blocos.get(i);
                Player.Hit(curBlock.x, curBlock.y);
                for(int j = 0; j < enemys.size(); j++) {
                    enemys.get(j).Hit(curBlock.x, curBlock.y);
                }
            }
        }
        else if(gameState == "GAME_OVER" || gameState == "GAME_WON" || gameState == "GAME_TITLE") {
            this.framesGameOver++;
            if(this.framesGameOver == 30) {
                this.framesGameOver = 0;
                if(this.showMessageGameOver) this.showMessageGameOver = false;
                else this.showMessageGameOver = true;
            }

            if(restartGame) {
                this.restartGame = false;
                gameState = "NORMAL";

                player = new Player(288, 208);

                enemys.clear();
        
                enemys.add(new Enemy(32, 32));
                enemys.add(new Enemy(640-64, 32));
                enemys.add(new Enemy(640-64, 480-64));
                enemys.add(new Enemy(32, 480-64));
            }
        }
    }

    public void render() {
        BufferStrategy bs = this.getBufferStrategy();

        if(bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.setColor(new Color(0, 135,13));
        g.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);

        player.render(g);

        for(int i = 0; i < enemys.size(); i++) {
            enemys.get(i).render(g);
        }

        world.render(g);

        if(gameState == "GAME_TITLE") {
            Graphics2D g2 = (Graphics2D) g;

            g2.setColor(new Color(0, 0, 0, 100));
            g2.fillRect(0, 0, WIDTH, HEIGHT);
            g.setFont(new Font("arial", Font.BOLD, 45));
            g.setColor(Color.red);
            g.drawString("EMBOSCADA", 165, 80);

            g.setFont(new Font("arial", Font.BOLD, 28));
            if(showMessageGameOver) {
                g.drawString(">Pressione ENTER para iniciar<", 100, 300);
                g.drawString(">e Z para atirar<", 200, 420);
            }
        }

        if(gameState == "GAME_OVER") {
            Graphics2D g2 = (Graphics2D) g;

            g2.setColor(new Color(0, 0, 0, 100));
            g2.fillRect(0, 0, WIDTH, HEIGHT);
            g.setFont(new Font("arial", Font.BOLD, 28));
            g.setColor(Color.red);
            g.drawString("Morreu na emboscada", 150, 230);

            if(showMessageGameOver) g.drawString(">Pressione ENTER para tentar de novo<", 50, 300);
        }
        else if(gameState == "GAME_WON") {
            Graphics2D g2 = (Graphics2D) g;

            g2.setColor(new Color(0, 0, 0, 100));
            g2.fillRect(0, 0, WIDTH, HEIGHT);
            g.setFont(new Font("arial", Font.BOLD, 28));
            g.setColor(Color.white);
            g.drawString("Sobreviveu a emboscada", 130, 230);

            if(showMessageGameOver) g.drawString(">Pressione ENTER para tentar de novo<", 50, 300);
        }

        bs.show();
    }

    public static void main(String[] args) {
        Game game = new Game();
        JFrame frame = new JFrame();

        frame.add(game);
        frame.setTitle("Emboscada");
        frame.pack();

        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        new Thread(game).start();
    }

    @Override
    public void run() {
        while(true) {
        tick();
        render();
            try {
                Thread.sleep(1000/60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            player.right = true;
        }
        else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            player.left = true;
        }

        if(e.getKeyCode() == KeyEvent.VK_UP) {
            player.up = true;
        }
        else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
            player.down = true;
        }

        if(e.getKeyCode() == KeyEvent.VK_Z) {
            player.shoot = true;
        }

        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            this.restartGame = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            player.right = false;
        }
        else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            player.left = false;
        }

        if(e.getKeyCode() == KeyEvent.VK_UP) {
            player.up = false;
        }
        else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
            player.down = false;
        }
    }
}