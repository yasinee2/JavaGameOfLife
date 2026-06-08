package com.jgol;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JPanel {

    private final int SCREEN_WIDTH = 1920;
    private final int SCREEN_HEIGHT = 1080;
    private final int CELL_SIZE = 10;

    private String[][] LivingCells = new String[SCREEN_WIDTH / CELL_SIZE][SCREEN_HEIGHT / CELL_SIZE];

    private Graphics graphics;

    private boolean isMousePressed = false;

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(java.awt.Toolkit.getDefaultToolkit().getScreenSize());
        frame.add(new Main());
        //frame.setSize(500, 500);
        frame.setTitle("JavaGameOfLife");
        frame.setVisible(true);

    }

    @Override
    protected void paintComponent(Graphics graphics) {
        this.graphics = graphics;
        super.paintComponent(graphics);
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, getWidth(), getHeight());

        initField();
    }

    private void initMouseListener() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    isMousePressed = true;
                    TurnPosIntoCellPos(e.getX(), e.getY());
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    isMousePressed = false;
                }
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {

            }
        });
    }

    private Main() {
        initMouseListener();

    }

    private void initField() {
        graphics.setColor(Color.gray);
        int x = 0;
        int y = 0;

        for (int i = 0; i < SCREEN_WIDTH * SCREEN_HEIGHT; i++) {//BUG: To big but who cares
            if (x < SCREEN_WIDTH / CELL_SIZE) {
                graphics.drawRect(CELL_SIZE * x, CELL_SIZE * y, CELL_SIZE, CELL_SIZE);
                x++;
            } else {
                x = 0;
                y++;
            }
        }
    }

    private void TurnPosIntoCellPos(int clickedX, int clickedY) {
        int HRayPos = clickedX;
        int VRayPos = clickedY;

        while (IsOnGrid(HRayPos) == false) {
            HRayPos++;
        }
    }

    private boolean IsOnGrid(int pos) {
        if ((pos / 10) % 1 != 0) {
            System.out.println(pos);
            return true;
        } else {
            System.err.println(pos);
            return false;
        }
    }
}
