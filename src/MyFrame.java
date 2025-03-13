import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;


public class MyFrame extends JFrame implements KeyListener {
    private static final int GAME_WIDTH = 500;
    private static final int GAME_HEIGHT = 500;
    private static final int BALL_SIZE = 20;
    private static final int BALL_SPEED = 3;
    private static final int SPAWN_RATE = 30;

    private JLabel rocket;
    private ArrayList<JLabel> balls = new ArrayList<>();
    private Timer gameTimer;
    private Random random = new Random();
    private boolean gameRunning = true;

    public MyFrame() {
        setTitle("Rocket Game");
        setSize(GAME_WIDTH, GAME_HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());


        JPanel gamePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.WHITE);
                g.drawRect(0, 0, GAME_WIDTH-1, GAME_HEIGHT-1); // Border
            }
        };
        gamePanel.setLayout(null);
        gamePanel.setBackground(Color.BLACK);
        add(gamePanel, BorderLayout.CENTER);

        // Load rocket to the fream
        ImageIcon rocketIcon = createScaledIcon("main/resources/img.icons8.png", 50, 56);
        rocket = new JLabel(rocketIcon);
        rocket.setSize(50, 56);
        rocket.setLocation(GAME_WIDTH/2 - 25, GAME_HEIGHT - 80);
        gamePanel.add(rocket);

        // Game timer for balls when they fall
        gameTimer = new Timer(20, e -> {
            if (!gameRunning) return;

            // Spawn new balls
            if (random.nextInt(SPAWN_RATE) == 0) {
                JLabel ball = createBall();
                gamePanel.add(ball);
                balls.add(ball);
            }

            // Move existing balls
            ArrayList<JLabel> toRemove = new ArrayList<>();
            for (JLabel ball : balls) {
                ball.setLocation(ball.getX(), ball.getY() + BALL_SPEED);

                // Check collision with rocket
                if (ball.getBounds().intersects(rocket.getBounds())) {
                    gameOver();
                }

                // Remove off-screen balls
                if (ball.getY() > GAME_HEIGHT) {
                    toRemove.add(ball);
                    gamePanel.remove(ball);
                }
            }
            balls.removeAll(toRemove);
            repaint();
        });
        gameTimer.start();

        addKeyListener(this);
        setVisible(true);
    }

    private JLabel createBall() {
        JLabel ball = new JLabel();
        ball.setSize(BALL_SIZE, BALL_SIZE);
        ball.setOpaque(true);
        ball.setBackground(Color.RED);
        ball.setLocation(random.nextInt(GAME_WIDTH - BALL_SIZE), 0);
        return ball;
    }

    private ImageIcon createScaledIcon(String path, int width, int height) {
        try {
            ImageIcon originalIcon = new ImageIcon(getClass().getResource(path));
            Image scaledImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading image: " + e.getMessage());
            System.exit(1);
            return null;
        }
    }

    private void gameOver() {
        gameRunning = false;
        gameTimer.stop();
        JOptionPane.showMessageDialog(this, "Game Over!");
        // Reset game
        balls.forEach(this::remove);
        balls.clear();
        rocket.setLocation(GAME_WIDTH/2 - 25, GAME_HEIGHT - 80);
        gameRunning = true;
        gameTimer.start();
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int x = rocket.getX();
        int y = rocket.getY();

        switch(e.getKeyCode()) {
            case  KeyEvent.VK_UP:
                rocket.setLocation(x , y - 10);

            case KeyEvent.VK_LEFT:
                if (x > 10) rocket.setLocation(x - 10, rocket.getY());
                break;
            case KeyEvent.VK_RIGHT:
                if (x < GAME_WIDTH - rocket.getWidth() - 10) rocket.setLocation(x + 10, rocket.getY());
                break;

        }

    }

    // Other required methods
    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyReleased(KeyEvent e) {}

    public static void main(String[] args) {
        new MyFrame();
    }
}