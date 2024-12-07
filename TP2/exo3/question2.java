
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class BallFiller extends JFrame {
    JButton startButton;

    public BallFiller() {
        setTitle("Remplissage");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

       
        startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	new Thread(new Runnable() {
                    @Override
                    public void run() {
                        remplir();
                    }
                }).start();
            }
        });

        add(startButton, BorderLayout.SOUTH);
    }

    void remplir() {
        Graphics g = getGraphics();
        for (int y = 0; y < 100; y += 20) {
            for (int x = 0; x < 300; x += 20) {
                g.fillOval(x, y, 10, 10);
                try {
                    Thread.sleep(100); 
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
public class question2 {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
        	BallFiller frame = new BallFiller();
            frame.setVisible(true);
        });
    }
}