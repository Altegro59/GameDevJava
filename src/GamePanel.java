import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable, KeyListener {
    //FIELDS
    public static int WIDTH = 400;
    public static int HEIGHT = 400;

    private Thread thread;
    private boolean running;

    private BufferedImage image; // this is our canvas
    private Graphics2D g;        // this is our paintbrush

    private int FPS = 30;
    private double averageFPS;

    public  static Player player;
    public static ArrayList<Bullet> bullets;

    //CONSTRUCTOR

    public GamePanel() {
        super();
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        setFocusable(true);
        requestFocus();
    }

    //FUNCTIONS

    public void addNotify(){
        super.addNotify();

        if(thread == null){
            thread = new Thread(this);
            thread.start();
        }
        addKeyListener(this);
    }

    @Override
    public void run() {
        running = true;

        image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
        g = image.createGraphics();

        long startTime;
        long URDTimeMilis;
        long waitTime;
        long totalTime = 0;

        int frameCount = 0;
        int maxFrameCount = 30;

        long targetTime = 1000 / FPS;

        player = new Player();
        bullets = new ArrayList<Bullet>();
        //GAME LOOP
        while (running){

            startTime = System.nanoTime();

            gameUpdate();
            gameRander();
            gameDraw();

            URDTimeMilis = (System.nanoTime() - startTime)/ 1_000_000;

            waitTime = targetTime - URDTimeMilis;

            try {
                Thread.sleep(waitTime);
            } catch (Exception e) {
            //    e.printStackTrace();
            }
            totalTime += System.nanoTime() - startTime;
            frameCount ++;
            if (frameCount == maxFrameCount){
                averageFPS = 1000.0 / ((totalTime / frameCount) / 1_000_000);
                frameCount = 0;
                totalTime = 0;

            }

        }
    }

    private void gameUpdate(){

        player.update();
        for (int i  = 0; i < bullets.size(); i++){
            boolean remove = bullets.get(i).update();
            if (remove){
                bullets.remove(i);
                i--;
            }


        }

    }
    private void gameRander(){

        g.setColor(Color.WHITE);
        g.fillRect(0,0, WIDTH, HEIGHT);
        g.setColor(Color.BLACK);
        g.drawString("FPS:" + averageFPS, 10,10);

        player.draw(g);


        for (int i  = 0; i < bullets.size(); i++){
            bullets.get(i).draw(g);
            }
    }
    private void gameDraw(){

        Graphics g2 = this.getGraphics();
        g2.drawImage(image,0,0,null);
        g2.dispose();

    }

    @Override
    public void keyTyped(KeyEvent e) {


    }

    @Override
    public void keyPressed(KeyEvent e) {

        int KeyKode = e.getKeyCode();

        if (KeyKode == KeyEvent.VK_LEFT){
            player.setLeft(true);
        }

        if (KeyKode == KeyEvent.VK_RIGHT){
            player.setRight(true);
        }
        if (KeyKode == KeyEvent.VK_UP){
            player.setUp(true);
        }
        if (KeyKode == KeyEvent.VK_DOWN){
            player.setDown(true);
        }
        if(KeyKode == KeyEvent.VK_SPACE){
            player.setFiring(true);
        }


    }

    @Override
    public void keyReleased(KeyEvent e) {

        int KeyKode = e.getKeyCode();

        if (KeyKode == KeyEvent.VK_LEFT){
            player.setLeft(false);
        }

        if (KeyKode == KeyEvent.VK_RIGHT){
            player.setRight(false);
        }
        if (KeyKode == KeyEvent.VK_UP){
            player.setUp(false);
        }
        if (KeyKode == KeyEvent.VK_DOWN){
            player.setDown(false);
        }
        if(KeyKode == KeyEvent.VK_SPACE){
            player.setFiring(false);
        }

    }
}
