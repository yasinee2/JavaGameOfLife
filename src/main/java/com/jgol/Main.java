package com.jgol;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.badlogic.gdx.math.Vector2;

public class Main extends JPanel {

    private final int CELL_WIDTH = 192;
    private final int CELL_HEIGHT = 108;
    private final int CELL_SIZE = 10;

    private Vector2[] LivingCells = new Vector2[CELL_WIDTH * CELL_HEIGHT];
    private int LivingCellsCount = 0;

    private Vector2 LastClicked = new Vector2();

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

        for (int i = 0; i < LivingCellsCount; i++) {
            graphics.drawRect((int) LivingCells[i].x, (int) LivingCells[i].y, CELL_SIZE, CELL_SIZE);
        }
    }

    private void initMouseListener() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    isMousePressed = true;

                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    isMousePressed = false;
                    LastClicked = new Vector2(e.getX(), e.getY());

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

        for (int i = 0; i < CELL_WIDTH * CELL_HEIGHT; i++) {//BUG: To big but who cares
            if (x < CELL_WIDTH) {
                graphics.drawRect(CELL_SIZE * x, CELL_SIZE * y, CELL_SIZE, CELL_SIZE);
                x++;
            } else {
                x = 0;
                y++;
            }
        }
    }

    private Vector2 TurnPosIntoCellPos(int clickedX, int clickedY) {
        Vector2 CellCorner = getCellCorner(clickedX, clickedY);
        Vector2 output = new Vector2(CellCorner.x / 10, CellCorner.y / 10);

        return output;
    }

    private Vector2 getCellCorner(int ClickedX, int ClickedY) {
        String CellCornerXString = String.valueOf(ClickedX);
        String CellCornerXflooredString = CellCornerXString.substring(0, CellCornerXString.length() - 1) + "0";
        int CellCornerX = Integer.parseInt(CellCornerXflooredString);

        String CellCornerYString = String.valueOf(ClickedY);
        String CellCornerYflooredString = CellCornerYString.substring(0, CellCornerYString.length() - 1) + "0";
        int CellCornerY = Integer.parseInt(CellCornerYflooredString);

        Vector2 output = new Vector2(CellCornerX, CellCornerY);
        System.out.println(output);

        return output;
    }

    private void RenderLivingCells(int clickedX, int clickedY {

    }


)
}
