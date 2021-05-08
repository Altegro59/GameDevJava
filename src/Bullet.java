import java.awt.*;

public class Bullet {

    //FIELDS
    private double x;
    private double y;

    private int r;
    private double dx;
    private double dy;

    private double rad;
    private int speed;
    private Color color1;

    //CONSTRUCTOR
    public Bullet(int x, int y, int angle) {
        this.x = x;
        this.y = y;
        this.r = 2;
        this.rad = Math.toRadians(angle);
        this.speed = 10;
        this.dx = Math.cos(rad) * speed;
        this.dy = Math.sin(rad) * speed;

        color1 = Color.YELLOW;
    }

    //FUNCTIONS


    public boolean update(){

        x+=dx;
        y+=dy;

        if(x < -r || x > GamePanel.WIDTH + r || y < - r ||
        y < -r  || y > GamePanel.HEIGHT + r){
            return true;
        }
        return  false;
    }

    public void draw (Graphics2D g){

        g.setColor(color1);
        g.fillOval((int) x -  r, (int) y - r, 2*r, 2*r);

    }
}
