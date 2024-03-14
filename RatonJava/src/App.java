import java.awt.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class App {
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
                JComboBox<String> algoritmosBx = new JComboBox<>(algoritmosLabels);
                algoritmosBx.setBounds(40, space, 120, 25);
                frameInput.add(algoritmosBx);
                

                space += buff;

                mouse2 = new JLabel("Raton 2");
                mouse2.setBounds(40, space, 120, 25);
                frameInput.add(mouse2);
                space += buffL;
                JComboBox<String> algoritmosBx2 = new JComboBox<>(algoritmosLabels);
                algoritmosBx2.setBounds(40, space, 120, 25);
                frameInput.add(algoritmosBx2);
                

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

               
          
                
                // cellsL = new JLabel(cells + "x" + cells);

                // size = new JSlider(1, 5, 2);
                // sizeL = new JLabel("Tamano:");
                // //speed = new JSlider(0, 500, this.delay);
                

                // size.setMajorTickSpacing(10);
                // size.setBounds(40, space, 120, 25);
                // sizeL.setBounds(15, space, 120, 25);
                // cellsL.setBounds(160, space, 40, 25);
                // frameInput.add(size);
                // frameInput.add(sizeL);
                // frameInput.add(cellsL);
                
                space += buff;

                // obstacles = new JSlider(1, 100, 50);
                // obstaclesL = new JLabel("Obstaculos:");
                // densityL = new JLabel(obstacles.getValue() + "%");
                // obstacles.setMajorTickSpacing(10);
                // obstacles.setBounds(40, space, 120, 25);
                // obstaclesL.setBounds(15, space, 120, 25);
                // densityL.setBounds(160, space, 100, 25);
                // frameInput.add(obstacles);
                // frameInput.add(obstaclesL);
                // frameInput.add(densityL);

                // space += buff;

                // checkL = new JLabel("Visitados: " + checks);
                // checkL.setBounds(40, space, 120, 25);
                // frameInput.add(checkL);


                // space += buff;

                // lengthL = new JLabel("Longitud de ruta: " + length);
                // lengthL.setBounds(40, space, 120, 25);
                // frameInput.add(lengthL);

                //--------------------------------------------------------------------------
                // EVENTO CAMBIO DE TAMAÑO
                //-----------------------------------------------------------------
                size.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    //int value = slider.getValue();
                    //label.setText("Valor: " + value);
                    cellsL.setText((size.getValue() * 10) + "x" + (size.getValue() * 10));

                    //cellsL.setText("10");
                }
               });

               //--------------------------------------------------------------------------
                // EVENTO CAMBIO DE OBSTACULOS
                //-----------------------------------------------------------------
                obstacles.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    densityL.setText(obstacles.getValue() + "%");


                    }
                });


                //--------------------------------------------------------------------------
                // EVENTO GENERAR LABERINTO
                //-----------------------------------------------------------------
                generarmapB.addActionListener((e) -> {
                    GrafoMatriz grafoMatriz = new GrafoMatriz (20 ,20, 20);
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
                searchB.addItemListener(null);


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
