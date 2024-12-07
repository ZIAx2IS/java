
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

class BallAnimation extends JFrame {
    JButton startButton;
    BallPanel ballPanel;
    List<Thread> ballThreads;

    BallAnimation() {
        setTitle("Balles rebondissantes");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        ballThreads = new ArrayList<>();
        ballPanel = new BallPanel();
        add(ballPanel, BorderLayout.CENTER);

        
        startButton = new JButton("Ajouter une balle");
        add(startButton, BorderLayout.SOUTH);
        startButton.addActionListener(e -> {
            Ball ball = new Ball();
            ballPanel.addBall(ball);

            // Lancer un thread pour animer cette balle
            Thread thread = new Thread(() -> ball.move(ballPanel));
            ballThreads.add(thread);
            thread.start();
        });

        
    }
}

class BallPanel extends JPanel {
    List<Ball> balls;

    BallPanel() {
        balls = new ArrayList<>();
    }

    void addBall(Ball ball) {
        balls.add(ball);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Ball ball : balls) {
            g.fillOval(ball.x, ball.y, ball.size, ball.size);
        }
    }
}

class Ball {
    int x, y;
    int dx = 2, dy = 2;
    int size = 15; 

    public Ball() {
        this.x = 0; 
        this.y = 0;
    }
    public void move(JPanel panel) {
        while (true) {
            x += dx;
            y += dy;

            int panelWidth = panel.getWidth();
            int panelHeight = panel.getHeight();

            if (x <= 0 || x + size >= panelWidth) {
                dx = -dx;
            }
            if (y <= 0 || y + size >= panelHeight) {
                dy = -dy;
            }

            panel.repaint();

            try {
                Thread.sleep(10); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class question3 {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
        	BallAnimation frame = new BallAnimation();
            frame.setVisible(true);
        });
    }
}