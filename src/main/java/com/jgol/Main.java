package com.jgol;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JPanel {

    private final int GRID_WIDTH = 192;
    private final int CELL_HEIGHT = 108;
    private final int CELL_SIZE = 10;

    private Point[] LivingCells = new Point[GRID_WIDTH * CELL_HEIGHT];
    private int LivingCellsCount = 0;

    private Point LastClicked = new Point();

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
            graphics.fillRect((int) LivingCells[i].x, (int) LivingCells[i].y, CELL_SIZE, CELL_SIZE);
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
                    LastClicked = new Point(e.getX(), e.getY());
                    CellHandler(LastClicked.x, LastClicked.y);

                }
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                LastClicked = new Point(e.getX(), e.getY());
                CellHandler(LastClicked.x, LastClicked.y);
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

        for (int i = 0; i < GRID_WIDTH * CELL_HEIGHT; i++) {//BUG: To big but who cares
            if (x < GRID_WIDTH) {
                graphics.drawRect(CELL_SIZE * x, CELL_SIZE * y, CELL_SIZE, CELL_SIZE);
                x++;
            } else {
                x = 0;
                y++;
            }
        }
    }

    private void CellHandler(int ClickedX, int ClickedY) {
        Point CellCornerPos = getCellCorner(ClickedX, ClickedY);
        Point CellGridPos = getCellCorner(ClickedX, ClickedY);
        saveLivingCell(CellGridPos.x, CellGridPos.y);
        repaint();
    }

    private Point getCellCorner(int ClickedX, int ClickedY) {
        String CellCornerXString = String.valueOf(ClickedX);
        String CellCornerXflooredString = CellCornerXString.substring(0, CellCornerXString.length() - 1) + "0";
        int CellCornerX = Integer.parseInt(CellCornerXflooredString);

        String CellCornerYString = String.valueOf(ClickedY);
        String CellCornerYflooredString = CellCornerYString.substring(0, CellCornerYString.length() - 1) + "0";
        int CellCornerY = Integer.parseInt(CellCornerYflooredString);

        Point output = new Point(CellCornerX, CellCornerY);

        return output;
    }

    private void saveLivingCell(int GridCellPosX, int GridCellPosY) {
        LivingCells[LivingCellsCount] = new Point(GridCellPosX, GridCellPosY);

        LivingCellsCount++;
    }
}
