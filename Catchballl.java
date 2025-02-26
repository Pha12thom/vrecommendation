import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class CatchBallsGame extends JPanel implements KeyListener, MouseMotionListener, ActionListener {

    private static final int SCREEN_WIDTH = 800;
    private static final int SCREEN_HEIGHT = 600;
    private static final int BALL_RADIUS = 20;
    private static final int CATCHER_WIDTH = 100;
    private static final int CATCHER_HEIGHT = 20;
    private static final int BALL_SPEED_MIN = 1;
    private static final int BALL_SPEED_MAX = 6;
    private static final int MAX_MISSES = 30;

    private ArrayList<Ball> balls;
    private Catcher catcher;
    private Timer timer;
    private int score;
    private int misses;
    private int level;
    private int highestScore;
    private JButton newGameButton, quitButton;

    public CatchBallsGame() {
        this.balls = new ArrayList<>();
        this.catcher = new Catcher();
        this.score = 0;
        this.misses = 0;
        this.level = 0;
        this.highestScore = 0;

        setFocusable(true);
        addKeyListener(this);
        addMouseMotionListener(this);

        timer = new Timer(20, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        for (Ball ball : balls) {
            ball.draw(g);
        }

        catcher.draw(g);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 30));
        g.drawString("Score: " + score, 10, 30);
        g.drawString("Highest Score: " + highestScore, SCREEN_WIDTH - 250, 30);
        g.drawString("Misses: " + misses + "/" + MAX_MISSES, 10, 60);
        g.drawString("Level: " + level, 10, 90);

        if (misses >= MAX_MISSES) {
            gameOver(g);
        }
    }

    private void gameOver(Graphics g) {
        timer.stop();
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.setColor(Color.RED);
        g.drawString("Game Over", SCREEN_WIDTH / 2 - 150, SCREEN_HEIGHT / 2 - 50);
        
        newGameButton = new JButton("New Game");
        quitButton = new JButton("Quit");
        newGameButton.setBounds(SCREEN_WIDTH / 2 - 100, SCREEN_HEIGHT / 2, 150, 50);
        quitButton.setBounds(SCREEN_WIDTH / 2 - 100, SCREEN_HEIGHT / 2 + 60, 150, 50);

        newGameButton.addActionListener(e -> restartGame());
        quitButton.addActionListener(e -> System.exit(0));

        this.setLayout(null);
        this.add(newGameButton);
        this.add(quitButton);
    }

    private void restartGame() {
        score = 0;
        misses = 0;
        level = 0;
        balls.clear();
        catcher = new Catcher();
        timer.start();
        this.remove(newGameButton);
        this.remove(quitButton);
        repaint();
    }

    public void actionPerformed(ActionEvent e) {
        if (score > highestScore) {
            highestScore = score;
        }

        if (score % 20 == 0 && score > 0) {
            level++;
            for (Ball ball : balls) {
                ball.randomizeBehavior();
            }
        }

        if (new Random().nextInt(100) < 5) {
            balls.add(new Ball());
        }

        for (int i = balls.size() - 1; i >= 0; i--) {
            Ball ball = balls.get(i);
            ball.fall();
            
            if (catcher.collidesWith(ball)) {
                balls.remove(i);
                score++;
            } else if (ball.getY() > SCREEN_HEIGHT) {
                balls.remove(i);
                misses++;
                if (misses >= MAX_MISSES) {
                    repaint();
                    return;
                }
            }
        }
        repaint();
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            catcher.moveLeft();
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            catcher.moveRight();
        }
    }

    public void mouseMoved(MouseEvent e) {
        catcher.moveTo(e.getX());
    }

    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}
    public void mouseDragged(MouseEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Catch the Falling Balls");
        CatchBallsGame gamePanel = new CatchBallsGame();
        frame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(gamePanel);
        frame.setVisible(true);
    }

    class Ball {
        private int x, y, speed;
        private boolean reversing;

        public Ball() {
            this.x = new Random().nextInt(SCREEN_WIDTH - BALL_RADIUS * 2) + BALL_RADIUS;
            this.y = -BALL_RADIUS;
            this.speed = BALL_SPEED_MIN;
            this.reversing = false;
        }

        public void fall() {
            if (reversing) {
                this.y -= this.speed;
                if (y < 50) {
                    reversing = false;
                }
            } else {
                if (new Random().nextInt(100) < 5) {
                    speed = new Random().nextInt(BALL_SPEED_MAX - BALL_SPEED_MIN + 1) + BALL_SPEED_MIN;
                }
                this.y += this.speed;
                if (y > SCREEN_HEIGHT / 2 && new Random().nextInt(100) < 5) {
                    reversing = true;
                }
            }
        }

        public void randomizeBehavior() {
            this.speed = new Random().nextInt(BALL_SPEED_MAX - BALL_SPEED_MIN + 1) + BALL_SPEED_MIN;
        }

        public int getY() { return this.y; }
        public int getX() { return this.x; }

        public void draw(Graphics g) {
            g.setColor(level % 2 == 0 ? Color.RED : Color.BLUE);
            g.fillOval(x - BALL_RADIUS, y - BALL_RADIUS, BALL_RADIUS * 2, BALL_RADIUS * 2);
        }
    }

    class Catcher {
        private int x;
        
        public Catcher() {
            this.x = SCREEN_WIDTH / 2;
        }

        public void moveLeft() {
            x -= 20;
        }

        public void moveRight() {
            x += 20;
        }

        public void moveTo(int mouseX) {
            x = mouseX - CATCHER_WIDTH / 2;
        }

        public void draw(Graphics g) {
            g.setColor(Color.GREEN);
            g.fillRect(x, SCREEN_HEIGHT - 50, CATCHER_WIDTH, CATCHER_HEIGHT);
        }

        public boolean collidesWith(Ball ball) {
            return ball.getY() >= SCREEN_HEIGHT - 50 && ball.getX() >= x && ball.getX() <= x + CATCHER_WIDTH;
        }
    }
}
