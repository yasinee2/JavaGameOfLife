package com.jgol;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Main extends JPanel {

    private final int GRID_WIDTH = 192;
    private final int GRID_HEIGHT = 108;
    private final int CELL_SIZE = 10;
    private int FPS = 5;
    private final int FPSModifier = 5;

    private Set<Point> LivingCells = new HashSet<>();
    private Set<Point> Cells = new HashSet<>();

    private Point LastClicked = new Point();

    private Graphics graphics;

    private boolean isMousePressed = false;
    private boolean paused = true;
    private boolean wasPaused = false;
    Timer timer;

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

        //DOES: Draws all living cells
        for (Point cell : LivingCells) {
            graphics.fillRect((int) cell.x, (int) cell.y, CELL_SIZE, CELL_SIZE);
        }
    }

    private void initListeners() {
        setFocusable(true);
        requestFocusInWindow();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (!paused) {
                        wasPaused = true;
                        paused = true;
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (wasPaused) {
                    wasPaused = false;
                    paused = false;
                }
                if (e.getButton() == MouseEvent.BUTTON1) {
                    LastClicked = new Point(e.getX(), e.getY());
                    CellHandler(LastClicked);
                }
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                LastClicked = new Point(e.getX(), e.getY());
                CellHandler(LastClicked);
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    NextFrame();
                }
                if (e.getKeyCode() == KeyEvent.VK_P) {
                    paused = !paused;
                }
                if (e.getKeyCode() == KeyEvent.VK_C) {
                    LivingCells.clear();
                    repaint();
                }
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    FPS += FPSModifier;
                    initTimer(FPS);
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    if (FPS - FPSModifier <= 0) {
                        FPS = 1;
                    } else {
                        FPS -= FPSModifier;
                    }
                    System.out.println(FPS);
                    initTimer(FPS);
                }
            }
        });
    }

    private Main() {
        initListeners();
        initTimer(FPS);
    }

    private void initTimer(int fps) {
        if (timer != null) {
            timer.stop();
        }
        timer = new Timer(1000 / fps, e -> tick());
        timer.start();
    }

    private void tick() {
        if (paused == false) {
            NextFrame();
        }
    }

    @Override
    public void addNotify() {
        super.addNotify();
        requestFocusInWindow();
    }

    private void initField() {
        graphics.setColor(Color.gray);
        int x = 0;
        int y = 0;

        for (int i = 0; i < GRID_WIDTH * GRID_HEIGHT; i++) {//BUG: To big but who cares
            if (x < GRID_WIDTH) {
                graphics.drawRect(CELL_SIZE * x, CELL_SIZE * y, CELL_SIZE, CELL_SIZE);
                Cells.add(new Point(x * CELL_SIZE, y * CELL_SIZE));
                x++;
            } else {
                x = 0;
                y++;
            }
        }

    }

    private void CellHandler(Point ClickedPos) {
        Point CellGridPos = getCellCorner(ClickedPos);
        saveLivingCell(CellGridPos);
        repaint();
    }

    private Point getCellCorner(Point ClickedPos) {
        String CellCornerXString = String.valueOf(ClickedPos.x);
        String CellCornerXflooredString = CellCornerXString.substring(0, CellCornerXString.length() - 1) + "0";
        int CellCornerX = Integer.parseInt(CellCornerXflooredString);

        String CellCornerYString = String.valueOf(ClickedPos.y);
        String CellCornerYflooredString = CellCornerYString.substring(0, CellCornerYString.length() - 1) + "0";
        int CellCornerY = Integer.parseInt(CellCornerYflooredString);

        Point output = new Point(CellCornerX, CellCornerY);

        return output;
    }

    private void saveLivingCell(Point GridCellPos) {
        LivingCells.add(GridCellPos);
    }

    private void NextFrame() {
        Set<Point> nextGen = new HashSet<>();

        for (Point cell : Cells) {
            int neighbors = GetNeighborCount(cell);
            if (LivingCells.contains(cell)) {
                if (neighbors == 2 || neighbors == 3) {
                    nextGen.add(cell);
                }
            } else {
                if (neighbors == 3) {
                    nextGen.add(cell);
                }
            }
        }
        LivingCells = nextGen;
        repaint();
    }

    private int GetNeighborCount(Point CellCorner) {
        int[][] directions = {
            {-1, -1}, {0, -1}, {1, -1},
            {-1, 0}, {1, 0},
            {-1, 1}, {0, 1}, {1, 1}
        };

        int LivingNeighbors = 0;
        for (int[] direction : directions) {
            if (LivingCells.contains(new Point(CellCorner.x + direction[0] * CELL_SIZE,
                    CellCorner.y + direction[1] * CELL_SIZE))) {
                LivingNeighbors++;
            }
        }
        return LivingNeighbors;

    }
}
