import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Graficos extends JPanel {
    private GrafoMatriz grafoMatriz;
    private Nodo[][] grid;
    String[] algoritmosLabels = { "BFS", "DFS", "Voraz", "A*"};
    JComboBox<String> algoritmosBx = new JComboBox<>(algoritmosLabels);
    JPanel panelInput;
    Border loweredetched;

    public Graficos(GrafoMatriz grafoMatriz) {
        this.grafoMatriz = grafoMatriz;
        createGrid();


        this.loweredetched = BorderFactory.createEtchedBorder(1);
        setLayout(new BorderLayout()); // Agregar esta línea
        this.panelInput = new JPanel();
        this.panelInput.setBorder(BorderFactory.createTitledBorder(this.loweredetched, "Controls"));
        add(this.panelInput, BorderLayout.WEST);

        this.panelInput.setLayout((LayoutManager)null);
        this.panelInput.setBounds(10, 10, 210, 600);
        this.algoritmosBx.setBounds(40, 40, 120, 25);
        this.panelInput.add(this.algoritmosBx);
        
/*
        this.toolP.setLayout((LayoutManager)null);
      this.toolP.setBounds(10, 10, 210, 600);
      this.searchB.setBounds(40, space, 120, 25);
      this.toolP.add(this.searchB)
      */
        
        //frameInput.setTitle("Raton laberinto");
        //frameInput.setResizable(true);
        // frameInput.setSize(800, 600);
        // frameInput.setLocation(0, 0);
        // frameInput.setAlwaysOnTop(true);
        // frameInput.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frameInput.setVisible(true);
        //frameInput.add(this);
        //this.add(frameInput, BorderLayout.WEST);
        //this.add(new TextArea(), BorderLayout.EAST);
        //this.add(algoritmosBx, BorderLayout.WEST);
        //space += 25;
        //this.algorithmsBx.setBounds(40, space, 120, 25);
    }

    public void updateGUI() {
        repaint();
    }

    private void createGrid() {
        int rows = grafoMatriz.getFilas();
        int cols = grafoMatriz.getColumnas();

        grid = new Nodo[rows][cols];
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                grid[x][y] = grafoMatriz.getNodo(x, y);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw((Graphics2D) g);
    }

    private void draw(Graphics2D g) {
        int rows = grafoMatriz.getFilas();
        int cols = grafoMatriz.getColumnas();

        int tileSizeX = getWidth() / cols;
        int tileSizeY = getHeight() / rows;

        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                Nodo current = grid[x][y];
                int rx = x * tileSizeX;
                int ry = y * tileSizeY;

                if (current.getBloqueado()) {
                    g.setColor(Color.BLACK);
                } else {
                    switch (current.getEstado().toString()) {
                        case "ABIERTO":
                            g.setColor(Color.CYAN);
                            break;
                        case "CERRADO":
                            g.setColor(Color.YELLOW);
                            break;
                        case "META":
                            g.setColor(Color.RED);
                            break;
                        case "INICIO":
                            g.setColor(Color.GREEN);
                            break;
                        default:
                            g.setColor(Color.WHITE);
                            break;
                    }
                }

                g.fillRect(rx, ry, tileSizeX, tileSizeY);
                g.setColor(Color.BLACK);
                g.drawRect(rx, ry, tileSizeX, tileSizeY);

                g.setColor(Color.BLACK);
                g.drawString("(" + x + "," + y + ")", rx + 5, ry + 15);
            }
        }
    }
}