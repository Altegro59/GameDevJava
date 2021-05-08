import java.awt.*;

public class Player {
    //FIELDS

    private int x;
    private int y;
    private int r;

    private int dx;
    private int dy;
    private int speed;

    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;

    private int lives;

    private Color color1;
    private Color color2;

    private boolean firing;
    private long firingTimer;
    private long firingDelay;


    //CONSTRUCTOR


    public Player() {
        y = GamePanel.HEIGHT / 2;
        x = GamePanel.WIDTH / 2;
        speed = 5;
        r=3;

        dx = 0;
        dy = 0;
        lives = 3;
        color1 = Color.WHITE;
        color2 = Color.RED;
        firing = false;
        firingTimer = System.nanoTime();
        firingDelay = 200;
    }


    //FUNCTIONS
    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setFiring(boolean b){
        firing = b;
    }

    public void update(){

        if(left){
            dx = -speed;
        }
        if(right){
            dx = speed;
        }
        if (up){
            dy = -speed;
        }
        if (down){
            dy = speed;
        }

        x += dx;
        y += dy;

        if (x < r) x = r;
        if (y < r) y = r;

        if (x > GamePanel.WIDTH - r) x = GamePanel.WIDTH -r;
        if (y > GamePanel.HEIGHT - r) y = GamePanel.HEIGHT - r;

        dx = 0;
        dy = 0;

        if (firing){
            long elapsed =  (System.nanoTime() - firingTimer) / 1_000_000 ;
            if (elapsed > firingDelay){
                GamePanel.bullets.add(new Bullet( x, y,270));
                firingTimer = System.nanoTime();
            }
        }

    }

    public void draw(Graphics2D g){

        g.setColor(color1);
        g.fillOval(x-r, y-r, 2*r, 2*r);
        g.setStroke(new BasicStroke(3));
        g.setColor(color1.darker());
        g.drawOval(x - r, y - r, 2 * r, 2 * r);
        g.setStroke(new BasicStroke(1));
    }
}
