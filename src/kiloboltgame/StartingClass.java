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
    private Image characterDown;
    private Image characterJumped;
    private Image currentSprite;
    private Image background;
    private Graphics graphics;
    private URL base;
    private static Background bg1;
    private static Background bg2;

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
        characterDown = getImage(base, "./down.png");
        characterJumped = getImage(base, "./jumped.png");
        currentSprite = character;
        background = getImage(base, "./background.png");
    }

    @Override
    public void start() {
        super.start();

        bg1 = new Background(0,0);
        bg2 = new Background(2160, 0);

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
            if (robot.isJumped()) {
                currentSprite = characterJumped;
            } else if (!robot.isJumped() && !robot.isDucked()) {
                currentSprite = character;
            }
            bg1.update();
            bg2.update();
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
                currentSprite = characterDown;
                if (!robot.isJumped()) {
                    robot.setDucked(true);
                    robot.setSpeedX(0);
                }
                break;
            case KeyEvent.VK_LEFT:
                robot.moveLeft();
                robot.setMovingLeft(true);
                break;
            case KeyEvent.VK_RIGHT:
                robot.moveRight();
                robot.setMovingRight(true);
                break;
            case KeyEvent.VK_SPACE:
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
                currentSprite = character;
                robot.setDucked(false);
                break;
            case KeyEvent.VK_LEFT:
                System.out.println("LEFT RELEASED");
                robot.stopLeft();
                break;
            case KeyEvent.VK_RIGHT:
                System.out.println("RIGHT RELEASED");
                robot.stopRight();
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
        g.drawImage(background, bg1.getBgX(), bg2.getBgY(), this);
        g.drawImage(background, bg2.getBgX(), bg2.getBgY(), this);
        g.drawImage(currentSprite, robot.getCenterX() - 61, robot.getCenterY() - 63, this);
    }

    public static Background getBg1() {
        return bg1;
    }

    public static Background getBg2() {
        return bg2;
    }
}
