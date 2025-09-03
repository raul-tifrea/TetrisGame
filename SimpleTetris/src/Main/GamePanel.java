package Main;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    final int FPS = 60;
    Thread gameThread;
    PlayManager playManager;

    public GamePanel() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.BLACK);
        this.setLayout(null);
        this.addKeyListener(new KeyHandler());
        this.setFocusable(true);

        playManager = new PlayManager();
    }

    public void start() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    //delta/acumulator method
    @Override
    public void run() {


        double drawInterval = 1000000000 / FPS;
        double nextdrawInternal = System.nanoTime() + drawInterval;

        while(gameThread != null){
            update();
            repaint();
            double remaining = nextdrawInternal - System.nanoTime();
            remaining = remaining / 1000000;
            if(remaining < 0){
                remaining += 0;
            }

            try{
                Thread.sleep((long) (remaining));
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            nextdrawInternal += drawInterval;
        }

    }

    public void update() {
       if(KeyHandler.pausePressed == false && playManager.gameOver == false) {
           playManager.update();
       }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        playManager.draw(g2d);
    }
}
