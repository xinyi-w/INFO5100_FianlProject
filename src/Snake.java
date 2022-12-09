import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *  This class is used for storing the locations of snake, fruit, etc.
 *  the functions are used for set and get the location information.
 */
class storeLocation {
    private int x , y;
    public storeLocation() {}
    public storeLocation(int a , int b){
        x = a;
        y = b;
    }
    // store the locations of the object called
    public storeLocation(storeLocation tempPosition) {
        x = tempPosition.getX();
        y = tempPosition.getY();
    }
    //getters and setters
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public void setX(int a) {
        x = a;
    }
    public void setY(int b) {
        y = b;
    }
}

/*
 * The class initializes the snake and its corresponding logics. Including checking
 * whether the snake ends up ending the game by colliding the boundaries or itself,
 * and other initialization processes like draw the snake body.
 */
public class Snake {
    static final int direction [][] = {{0 , -1} , {0 , 1} , {-1 , 0} , {1 , 0}};
    private List<storeLocation> body = new ArrayList<storeLocation>();
    private int curDir;



    //initialize the snake by making it to face the right direction
    public Snake() {
        curDir = 3;
        body.add(new storeLocation(350 , 250));
    }

    // get the length of the snake
    public int getLength() {
        return body.size();
    }

    // get the direction that the snake is currently moving to
    public int getDir() {
        return curDir;
    }

    // draw the snake
    public void initialSnake(Graphics g)
    {
        g.setColor(Color.black);
        for(int i = 0; i < body.size(); i++) {
            g.fillRect(body.get(i).getX(), body.get(i).getY(), 10, 10);
        }
    }

    // logics for checking whether the snake collides. Including hit the boundaries
    // and its own body.
    public boolean checkCollide() {
        for(int i = 1; i < body.size(); i++) {
            if(body.get(0).getX() == body.get(i).getX() &&
                    body.get(0).getY() == body.get(i).getY())
                return true;
        }
        for(int i = body.size(); i > 0; i--){
            if(body.get(0).getX() > 730){
                return true;
            }
            if(body.get(0).getY() > 535){
                return true;
            }
            if(body.get(0).getX() < 0){
                return true;
            }
            if(body.get(0).getY() < 0){
                return true;
            }
        }

        return false;
    }

    // the direction of the snake's head
    public storeLocation headPosition()
    {
        int location_x = body.get(0).getX() + Graphic.Size * direction[curDir][0];
        int location_y = body.get(0).getY() + Graphic.Size * direction[curDir][1];

        // emerge the snake's body from the opposite side if it hits the boundary
        if(location_x > Graphic.cood_x + Graphic.width - Graphic.Size)
            location_x = Graphic.cood_x;
        if(location_x < Graphic.cood_x)
            location_x = Graphic.width + Graphic.cood_x - Graphic.Size;
        if(location_y > Graphic.cood_y + Graphic.height - Graphic.Size)
            location_y = Graphic.cood_y;
        if(location_y < Graphic.cood_y)
            location_y = Graphic.height + Graphic.cood_y - Graphic.Size;

        // store the final location
        storeLocation tempPosition = new storeLocation(location_x, location_y);
        return tempPosition;
    }

    // initialize the movements of the snake and its position
    public void move()
    {
        storeLocation snake_head = headPosition();
        storeLocation storeL = new storeLocation();
        boolean eaten = false;

        // check whether the snake get the fruit
        if(Math.abs(snake_head.getX() - Graphic.fruit_x) < 10 &&
                Math.abs(snake_head.getY() - Graphic.fruit_y) < 10) {
            eaten = true;
            storeL = new storeLocation(body.get(body.size() - 1));
            Graphic.fruit_x = (int)(Math.random() * (Graphic.width - 10) +
                    Graphic.cood_x);
            Graphic.fruit_y = (int)(Math.random() * (Graphic.height - 10) +
                    Graphic.cood_y);
        }

        // update the remaining body parts to the new route
        for(int i = body.size() - 1; i > 0; i--)
            body.set(i, body.get(i - 1));
        body.set(0, snake_head);

        // stop game and pop out the end window
        if(checkCollide()) {
            JOptionPane.showMessageDialog(null,
                    "Game Ends", "Message", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        // updates when eat the fruit
        if(eaten) {
            body.add(storeL);
            Graphic.score++;
            Graphic.speed++;
        }
    }

    // change the direction of the snake movement
    public void changeDir(int direction) {
        curDir = direction;
    }
}