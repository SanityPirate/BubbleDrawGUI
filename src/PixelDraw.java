import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class PixelDraw extends JPanel {

    Random rand = new Random();
    ArrayList<Bubble> bubbleList;
    int size = 25;
    Timer timer;
    int delay = 15;

    public PixelDraw() {
        timer = new Timer (delay, new BubbleListener());
        bubbleList = new ArrayList<>();
        setBackground(Color.BLACK);
        // testBubbles();
        addMouseListener(new BubbleListener());
        addMouseMotionListener(new BubbleListener());
        addMouseWheelListener(new BubbleListener());
        timer.start();
    }

    public void paintComponent(Graphics canvas) {
        super.paintComponent(canvas);
        for (Bubble b : bubbleList) {
            b.draw(canvas);
        }
    }

    public void testBubbles() {
        for (int i = 0; i < 100; i++) {
            int x = rand.nextInt(600);
            int y = rand.nextInt(400);
            int size = rand.nextInt(50);
            bubbleList.add(new Bubble(x, y, size));
        }
        repaint();
    }

    private class BubbleListener extends MouseAdapter implements ActionListener {

        @Override
        public void mousePressed(MouseEvent e) {
            bubbleList.add(new Bubble(e.getX(), e.getY(), size));
            repaint();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            bubbleList.add(new Bubble(e.getX(), e.getY(), size));
            repaint();
        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            if (System.getProperty("os.name").startsWith("Mac")) {
                size += e.getUnitsToScroll();
            } else {
                size -= e.getUnitsToScroll();
            }

            if (size < 3) {
                size = 3;
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            for (Bubble b : bubbleList) {
                b.update();
            }
            repaint();
        }
    }

    private class Bubble {
        private int x;
        private int y;
        private int size;
        private Color color;
        private int xSpeed, ySpeed;
        private final int MAX_SPEED = 5;

        public Bubble(int newX, int newY, int newSize) {
            x = (newX / newSize) * newSize + newSize/2;
            y = (newY / newSize) * newSize + newSize/2;
            size = newSize;
            color = new Color(rand.nextInt(256),
                    rand.nextInt(256),
                    rand.nextInt(256),
                    rand.nextInt(256));
            xSpeed = rand.nextInt(MAX_SPEED * 2 + 1) - MAX_SPEED;
            ySpeed = rand.nextInt(MAX_SPEED * 2 + 1) - MAX_SPEED;
            if (xSpeed == 0 && ySpeed == 0) {
                xSpeed = 1;
                ySpeed = 1;
            }
        }

        public void draw(Graphics canvas) {
            canvas.setColor(color);
            canvas.fillRect(x - size/2, y - size/2, size, size);
        }

        public void update() {
            x += xSpeed;
            y += ySpeed;
            if (x - size/2 <= 0 || x + size/2 >= getWidth()) {
                xSpeed = -xSpeed;
            }
            if ( y - size/2 <= 0 || y + size/2 >= getHeight()) {
                ySpeed = -ySpeed;
            }
        }
    }
}
