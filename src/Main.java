import javax.swing.JFrame;

/*
 * This is the main class for executing the game
 */
public class Main extends JFrame {
    Graphic Graphic;
    static final int Width = 800 , Height = 600 , LocX = 200 , LocY = 80;

    // appearance of the window
    public Main() {
        super("SnakeGame");
        Graphic = new Graphic();
        add(Graphic);
        this.setSize(Width, Height);
        this.setVisible(true);
        this.setLocation(LocX, LocY);

    }

    public static void main(String[] args) {
        new Main();
    }
}