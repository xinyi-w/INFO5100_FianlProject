import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Graphic extends JPanel implements ActionListener, KeyListener {

    static final int Up = 0;
    static final int Down = 1;
    static final int Left = 2;
    static final int Right = 3;

    // set the width and height area of the game

    static final int cood_x = 50;
    static final int cood_y = 50;
    static final int width = 700;
    static final int height = 500;
    static final int Size = 10;

    static int fruit_x;
    static int fruit_y;
    static int score = 0;
    static int speed = 5;
    boolean start = false;

    JButton startButton;
    JButton pauseButton;
    JButton quitButton;
    Snake snake;



    public Graphic() {
        snake = new Snake();
        //randomly assign the location of the fruit
        fruit_x = (int)(Math.random() * (width - 10) + cood_x);
        fruit_y = (int)(Math.random() * (height - 10) + cood_y);

        startButton = new JButton("Start");
        pauseButton = new JButton("Pause");
        quitButton = new JButton("Quit");

        setLayout(new FlowLayout(FlowLayout.LEFT));

        this.add(startButton);
        this.add(pauseButton);
        this.add(quitButton);

        startButton.addActionListener(this);
        pauseButton.addActionListener(this);
        quitButton.addActionListener(this);
        this.addKeyListener(this);
        new Thread(new snackThread()).start();
    }
//set the color and shape of game board, snake, and fruit
    public void paint(Graphics g)
    {
        super.paint(g);
        g.setColor(Color.white);

        g.fillRect(cood_x, cood_y, width, height);
        g.setColor(Color.black);


        g.drawString("Score: " + score + "        Speed: " + speed + "      MaxSpeed 100 " , 250, 25);

        g.setColor(Color.red);
        g.fillOval(fruit_x, fruit_y, 10, 10);

        snake.initialSnake(g);
    }
//when user press different button
    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == startButton) {
            start = true;
            startButton.setEnabled(false);
            pauseButton.setEnabled(true);
        }
        if(e.getSource() == pauseButton) {
            start = false;
            startButton.setEnabled(true);
            pauseButton.setEnabled(false);
        }
//        退出程序
        if(e.getSource() == quitButton) {
            System.exit(0);
        }
        this.requestFocus();




    }

    public void keyPressed(KeyEvent e) {

        if(!start)
            return ;

        switch(e.getKeyCode()) {
//when the length of the snake is 1, it means the user can control the snake to every direction
//when the length of the snake is greater than 1, it means the snake is moving, and it can't move in opposite direction
            case KeyEvent.VK_UP:
                if(snake.getLength() != 1 && snake.getDir() == Down) break;
                snake.changeDir(Up);
                break;
            case KeyEvent.VK_DOWN:
                if(snake.getLength() != 1 && snake.getDir() == Up) break;
                snake.changeDir(Down);
                break;
            case KeyEvent.VK_LEFT:
                if(snake.getLength() != 1 && snake.getDir() == Right) break;
                snake.changeDir(Left);
                break;
            case KeyEvent.VK_RIGHT:
                if(snake.getLength() != 1 && snake.getDir() == Left) break;
                snake.changeDir(Right);
                break;

        }
    }

    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}



    class snackThread implements Runnable
    {
        public void run() {
            while(true) {
                try {
//control the speed of the snake
                    Thread.sleep(100 - speed >= 0 ? 100 - speed : 0);
                    repaint();
                    if(start) {
                        snake.move();
                    }
                }
                catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}