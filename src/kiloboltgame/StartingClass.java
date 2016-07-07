package kiloboltgame;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;

public class StartingClass extends Applet implements Runnable, KeyListener {

    private Robot robot;
    private Image image;
    private Image character;
    private Graphics graphics;
    private URL base;

    @Override
    public void init() {
        super.init();

        initWindow();
        addKeyListener(this);

        try {
            base = getCodeBase();
        } catch (Exception e) {
            e.printStackTrace();
        }

        character = getImage(base, "./character.png");
    }

    @Override
    public void start() {
        super.start();

        robot = new Robot();

        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void stop() {
        super.stop();
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    private void infinityLoop() {
        while (true) {
            robot.update();
            repaint();
            try {
                Thread.sleep(17);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void initWindow() {
        setSize(800, 480);
        setBackground(Color.BLACK);
        setFocusable(true);

        Frame frame = (Frame) this.getParent().getParent();
        frame.setTitle("Q-Bot Alpha");
    }

    @Override
    public void run() {
        infinityLoop();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                System.out.println("UP");
                break;
            case KeyEvent.VK_DOWN:
                System.out.println("DOWN");
                break;
            case KeyEvent.VK_LEFT:
                System.out.println("LEFT");
                robot.moveLeft();
                break;
            case KeyEvent.VK_RIGHT:
                System.out.println("RIGHT");
                robot.moveRight();
                break;
            case KeyEvent.VK_SPACE:
                System.out.println("SPACE");
                robot.jump();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                System.out.println("UP RELEASED");
                break;
            case KeyEvent.VK_DOWN:
                System.out.println("DOWN RELEASED");
                break;
            case KeyEvent.VK_LEFT:
                System.out.println("LEFT RELEASED");
                robot.stop();
                break;
            case KeyEvent.VK_RIGHT:
                System.out.println("RIGHT RELEASED");
                robot.stop();
                break;
            case KeyEvent.VK_SPACE:
                System.out.println("SPACE RELEASED");
                break;
        }
    }

    @Override
    public void update(Graphics g) {
        if (image == null) {
            image = createImage(this.getWidth(), this.getHeight());
            graphics = image.getGraphics();
        }

        graphics.setColor(getBackground());
        graphics.fillRect(0, 0, getWidth(), getHeight());
        graphics.setColor(getForeground());
        paint(graphics);

        g.drawImage(image, 0, 0, this);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(character, robot.getCenterX() - 61, robot.getCenterY() - 63, this);
    }
}
