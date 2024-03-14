import java.awt.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class App {
    enum Algoritmo {
        DESACTIVADO, BFS, DFS, VORAZ, AESTRELLA
    }
    int cells = 20;
    int checks = 0;
    int length = 0;
    int obstaculos = 20;
    JSlider size;
    JSlider obstacles;
    JLabel sizeL;
    JLabel obstaclesL;
    JLabel cellsL;
    JLabel densityL;
    JLabel checkL;
    JLabel lengthL;
    JLabel metaL;
    JLabel inicio1L;
    JLabel inicio2L;
    JTextField inicio1XInput;
    JTextField inicio1YInput;
    JTextField inicio2XInput;
    JTextField inicio2YInput;
    JTextField metaXInput;
    JTextField metaYInput;
    Thread raton1;
    Thread raton2;
    boolean ratonActivo1 = false;
    boolean ratonActivo2 = false;
    Algoritmo raton1Algoritmo = Algoritmo.DESACTIVADO;
    Algoritmo raton2Algoritmo = Algoritmo.DESACTIVADO;
    int raton1inicioX = 0;
    int raton1inicioY = 0;
    int raton2inicioX = cells-1;
    int raton2inicioY = cells-1;
    int metaX = 0;
    int metaY = 0;

    public static void main(String[] args) throws Exception {
        new App();
    }



    public App() {
        SwingUtilities.invokeLater(() -> {
            
            JFrame frameInput = new JFrame();
                frameInput.setLayout((LayoutManager)null);
                frameInput.setLocation(0, 0);
                frameInput.setBounds(10, 10, 210, 600);
                frameInput.setResizable(true);
                frameInput.setSize(250, 600);
                frameInput.setAlwaysOnTop(true);
                frameInput.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frameInput.setVisible(true);

                int space = 40;
                int buff = 45;
                int buffL = 25;
                JLabel mouse1;
                JLabel mouse2;

                mouse1 = new JLabel("Raton 1");
                mouse1.setBounds(40, space, 120, 25);
                frameInput.add(mouse1);
                space += buffL;

                String[] algoritmosLabels = { "Desactivado","BFS", "DFS", "Voraz", "A*"};
                JComboBox<String> algoritmosBx1 = new JComboBox<>(algoritmosLabels);
                algoritmosBx1.setBounds(40, space, 120, 25);
                frameInput.add(algoritmosBx1);

                space += buff;
                inicio1XInput = new JTextField("0");
                inicio1XInput.setBounds(40, space, 50, 25);
                frameInput.add(inicio1XInput);

                inicio1YInput = new JTextField("0");
                inicio1YInput.setBounds(110, space, 50, 25);
                frameInput.add(inicio1YInput);

                inicio1L = new JLabel("Inicio");
                inicio1L.setBounds(5, space, 120, 25);
                frameInput.add(inicio1L);
                

                space += buff;

                mouse2 = new JLabel("Raton 2");
                mouse2.setBounds(40, space, 120, 25);
                frameInput.add(mouse2);
                space += buffL;
                JComboBox<String> algoritmosBx2 = new JComboBox<>(algoritmosLabels);
                algoritmosBx2.setBounds(40, space, 120, 25);
                frameInput.add(algoritmosBx2);
                space += buff;

                inicio2XInput = new JTextField((cells-1) + "");
                inicio2XInput.setBounds(40, space, 50, 25);
                frameInput.add(inicio2XInput);

                inicio2YInput = new JTextField((cells-1) + "");
                inicio2YInput.setBounds(110, space, 50, 25);
                frameInput.add(inicio2YInput);

                inicio1L = new JLabel("Inicio");
                inicio1L.setBounds(5, space, 120, 25);
                frameInput.add(inicio1L);

                space += buff;

                metaL = new JLabel("Meta");
                metaL.setBounds(40, space, 120, 25);
                frameInput.add(metaL);


                space += buff;

                metaXInput = new JTextField("0");
                metaXInput.setBounds(40, space, 50, 25);
                frameInput.add(metaXInput);

                metaYInput = new JTextField("0");
                metaYInput.setBounds(110, space, 50, 25);
                frameInput.add(metaYInput);
                
                

                space += buff;
                JButton searchB = new JButton("Buscar");
                searchB.setBounds(40, space, 120, 25);
                frameInput.add(searchB);

                space += buff;

                JButton generarmapB = new JButton("Generar Laberinto");
                generarmapB.setBounds(40, space, 120, 25);
                frameInput.add(generarmapB);
                
                

                space += buff;

                /*
                int cells = 20;
                int checks = 0;
                int length = 0;
                JSlider size;
                JSlider obstacles;
                JLabel sizeL;
                JLabel obstaclesL;
                JLabel cellsL;
                JLabel densityL;
                JLabel checkL;
                JLabel lengthL;
                */
                
                cellsL = new JLabel(cells + "x" + cells);

                size = new JSlider(1, 5, 2);
                sizeL = new JLabel("Tamano:");
                //speed = new JSlider(0, 500, this.delay);
                

                size.setMajorTickSpacing(10);
                size.setBounds(40, space, 120, 25);
                sizeL.setBounds(15, space, 120, 25);
                cellsL.setBounds(160, space, 40, 25);
                frameInput.add(size);
                frameInput.add(sizeL);
                frameInput.add(cellsL);
                
                space += buff;

                obstacles = new JSlider(1, 100, 50);
                obstaclesL = new JLabel("Obstaculos:");
                densityL = new JLabel(obstacles.getValue() + "%");
                obstacles.setMajorTickSpacing(10);
                obstacles.setBounds(40, space, 120, 25);
                obstaclesL.setBounds(15, space, 120, 25);
                densityL.setBounds(160, space, 100, 25);
                frameInput.add(obstacles);
                frameInput.add(obstaclesL);
                frameInput.add(densityL);

                space += buff;

                checkL = new JLabel("Visitados: " + checks);
                checkL.setBounds(40, space, 120, 25);
                frameInput.add(checkL);


                space += buff;

                lengthL = new JLabel("Longitud de ruta: " + length);
                lengthL.setBounds(40, space, 120, 25);
                frameInput.add(lengthL);

                
                space += buff;


                //--------------------------------------------------------------------------
                // EVENTO CAMBIO DE TAMAÑO
                //-----------------------------------------------------------------
                size.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    cellsL.setText((size.getValue() * 10) + "x" + (size.getValue() * 10));
                    cells = size.getValue() * 10;

                }
               });

               //--------------------------------------------------------------------------
                // EVENTO CAMBIO DE OBSTACULOS
                //-----------------------------------------------------------------
                obstacles.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    densityL.setText(obstacles.getValue() + "%");
                    obstaculos = Integer.parseInt(obstacles.getValue() + "");


                    }
                });

                //--------------------------------------------------------------------------
                // SELECCION DE ALGORITMO
                //-----------------------------------------------------------------
                algoritmosBx1.addItemListener((e) -> {
                    if (e.getStateChange() == 1) {
                        if (algoritmosBx1.getSelectedIndex() == 0) {
                            ratonActivo1 = false;
                        } else {
                            ratonActivo1 = true;
                            switch(algoritmosBx1.getSelectedIndex()){
                                case 1:
                                    raton1Algoritmo = Algoritmo.BFS;
                                    break;
                                case 2:
                                    raton1Algoritmo = Algoritmo.DFS;
                                    break;
                                case 3:
                                    raton1Algoritmo = Algoritmo.VORAZ;
                                    break;
                                case 4:
                                    raton1Algoritmo = Algoritmo.AESTRELLA;
                                    break;

                                
                            }
                        }
                    }
                });

                algoritmosBx2.addItemListener((e) -> {
                    if (e.getStateChange() == 1) {
                        if (algoritmosBx2.getSelectedIndex() == 0) {
                            ratonActivo2 = false;
                        } else {
                            ratonActivo2 = true;
                            switch(algoritmosBx2.getSelectedIndex()){
                                case 1:
                                    raton2Algoritmo = Algoritmo.BFS;
                                    break;
                                case 2:
                                    raton2Algoritmo = Algoritmo.DFS;
                                    break;
                                case 3:
                                    raton2Algoritmo = Algoritmo.VORAZ;
                                    break;
                                case 4:
                                    raton2Algoritmo = Algoritmo.AESTRELLA;
                                    break;
                        }
                    }
                }});

                //--------------------------------------------------------------------------
                // EVENTO GENERAR LABERINTO
                //-----------------------------------------------------------------
                generarmapB.addActionListener((e) -> {
                    GrafoMatriz grafoMatriz = new GrafoMatriz (cells ,cells, obstaculos);
                    Graficos mapView1 = new Graficos(grafoMatriz);
                JFrame frame1 = new JFrame();
                frame1.setTitle("Raton laberinto");
                frame1.getContentPane().add(mapView1);
                frame1.setResizable(true);
                frame1.setSize(800, 600);
                frame1.setLocation(210, 0);
                frame1.setAlwaysOnTop(true);
            
                frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame1.setVisible(true);
                });

                //--------------------------------------------------------------------------
                // EVENTO BUSCAR
                //-----------------------------------------------------------------
                searchB.addActionListener((e) -> {
                    if (ratonActivo1) {
                        raton1 = new Thread(() -> {
                            
                        });
                        
                    }
                    if (ratonActivo2) {
                        raton2 = new Thread(() -> {
                            GrafoMatriz grafoMatriz = new GrafoMatriz (20 ,20, 20);
                            Leer leer = new Leer();
                            String inicio = "";
                            String[] metasplit;
                            String meta = "";
                            if (ratonActivo2) {
                                inicio = inicio2XInput.getText() + "," + inicio2YInput.getText();
                                metasplit = metaXInput.getText().split(",");
                                meta = metasplit[0] + "," + metasplit[1];
                                //leer.buscarRuta(grafoMatriz, inicio, meta, raton2Algoritmo);
                            }
                        });

                        raton1.start();
                        raton2.start();
                    }
                });


            Thread GUI = new Thread(() -> {
                //GrafoMatriz grafoMatriz = new GrafoMatriz (filas ,columnas, porcentaje);
            
            Leer leer = new Leer();
            String inicio = "";
            String[] metasplit;
            String meta = "";
            Thread raton1;
            Thread raton2;
           /* 
            Graficos mapView1 = new Graficos(grafoMatriz);
                JFrame frame1 = new JFrame();
                frame1.setTitle("Raton laberinto");
                frame1.getContentPane().add(mapView1);
                frame1.setResizable(true);
                frame1.setSize(800, 600);
                frame1.setLocation(210, 0);
                frame1.setAlwaysOnTop(true);
            
                frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame1.setVisible(true);
                */

                

                

                


            });
            GUI.start();
    });
}
}
