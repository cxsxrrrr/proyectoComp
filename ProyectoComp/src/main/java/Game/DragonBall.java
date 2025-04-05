package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class DragonBall extends JFrame {
    private JProgressBar barraProgreso;
    private Timer timer;
    private boolean direccion = true;
    private JLabel tutorial;
    private int objetivo;
    private int turno = 1;
    private int[] resultados = new int[2];
    private JLabel jugador1, jugador2, fondo, fondo2,fondo3, txt, txt2, txt3;
    private JFrame nivelKi, resultado;
    private Font fuente = new Font("Arial", Font.BOLD, 30);
    
    // Obtener nombres desde GestorJugadores
    private String nombreJ1 = GestorJugadores.getJugador1();
    private String nombreJ2 = GestorJugadores.getJugador2();

    public DragonBall() {
        setTitle("Dragon ball batalla de ki");
        setSize(600, 400);
        setLayout(null);
        setFocusable(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        System.out.println("Jugador 1: " + nombreJ1);
        System.out.println("Jugador 2: " + nombreJ2);

        
        // fondo
        fondo = new JLabel(new ImageIcon("src/main/java/Game/imagenes/fondo8bit.jpg"));
        fondo.setBounds(0, 0, 600, 400);

        // jugadores
        jugador1 = new JLabel(new ImageIcon("src/main/java/Game/imagenes/goku.png"));
        jugador1.setBounds(50, 200, 100, 100);
        jugador2 = new JLabel(new ImageIcon("src/main/java/Game/imagenes/vegeta.png"));
        jugador2.setBounds(450, 200, 100, 100);

        // barra
        barraProgreso = new JProgressBar(0, 100);
        barraProgreso.setValue(0);
        barraProgreso.setStringPainted(true);
        barraProgreso.setBounds(150, 50, 300, 30);

        tutorial = new JLabel(nombreJ1 + ", presiona ESPACIO", SwingConstants.CENTER);
        tutorial.setBounds(150, 300, 300, 30);
        tutorial.setForeground(Color.red);
        tutorial.setFont(new Font("Pixelated", Font.BOLD, 20));

        add(jugador1);
        add(jugador2);
        add(barraProgreso);
        add(tutorial);
        add(fondo);

        objetivo = new Random().nextInt(20) * 5 + 5;
        

        
        //nivel ki
        nivelKi = new JFrame("Nivel de Ki");
        nivelKi.setTitle("Nivel de Ki");
        nivelKi.setSize(800, 600);
        nivelKi.setLayout(null);
        nivelKi.setFocusable(true);
        nivelKi.setLocationRelativeTo(null);
        nivelKi.setAlwaysOnTop(true);
        nivelKi.setDefaultCloseOperation(nivelKi.DISPOSE_ON_CLOSE);
        
        nivelKi.setVisible(true);
        //fondo2
        fondo2 = new JLabel(new ImageIcon("src/main/java/Game/imagenes/fondo_resplandor.jpg"));
        fondo2.setBounds(0, 0, 800, 600);
        
        
        
        txt = new JLabel();
        txt.setFont(fuente);
        txt.setForeground(Color.white);
        txt.setBounds(200, 150, 450, 100);
        //arreglar
        txt.setText("¡Carga tu ki al nivel correcto!");
        
        txt2 = new JLabel();
        txt2.setFont(fuente);
        txt2.setForeground(Color.red);
        txt2.setBounds(200, 250, 450, 100);
        txt2.setText("NIVEL DE KI NECESARIO: "+objetivo);
        
        
        nivelKi.add(txt);
        nivelKi.add(txt2);
        nivelKi.add(fondo2);
        
        

        timer = new Timer(50, e -> moverBarra());
        timer.start();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    registrarIntento();
                }
            }
        });

        setVisible(true);
    }

    private void moverBarra() {
        int valor = barraProgreso.getValue();
        if (direccion) {
            barraProgreso.setValue(valor + 5);
            if (valor >= 95) direccion = false;
        } else {
            barraProgreso.setValue(valor - 5);
            if (valor <= 5) direccion = true;
        }
    }

    private void registrarIntento() {
        resultados[turno - 1] = barraProgreso.getValue();
        if (turno == 1) {
            turno = 2;
            tutorial.setText(nombreJ2 + ", PRESIONA ESPACIO");
        } else {
            determinarGanador();
        }
    }


    private void determinarGanador() {
        int diff1 = Math.abs(objetivo - resultados[0]);
        int diff2 = Math.abs(objetivo - resultados[1]); // pa q sea positivo
        if (diff1 < diff2) {
            resultado = new JFrame("Resultado");
            resultado.setTitle("Resultado");
            resultado.setSize(800, 600);
            resultado.setLayout(null);
            resultado.setFocusable(true);
            resultado.setLocationRelativeTo(null);
            resultado.setAlwaysOnTop(true);
            resultado.setDefaultCloseOperation(resultado.DISPOSE_ON_CLOSE);

            resultado.setVisible(true);
            //fondo3
            fondo3 = new JLabel(new ImageIcon("src/main/java/Game/imagenes/goku_win.jpeg"));
            fondo3.setBounds(0, 0, 800, 600);


            txt3 = new JLabel();
            txt3.setFont(fuente);
            txt3.setForeground(Color.orange);
            txt3.setBounds(300, 80, 450, 100);
            txt3.setText("¡" + nombreJ1 + " Ganó!");
            
            resultado.add(txt3);
            resultado.add(fondo3);
        //***********************************************************************************************
        } else if (diff2 < diff1) {
            resultado = new JFrame("Resultado");
            resultado.setTitle("Resultado");
            resultado.setSize(800, 600);
            resultado.setLayout(null);
            resultado.setFocusable(true);
            resultado.setLocationRelativeTo(null);
            resultado.setAlwaysOnTop(true);
            resultado.setDefaultCloseOperation(resultado.DISPOSE_ON_CLOSE);

            resultado.setVisible(true);
            // fondo3
            fondo3 = new JLabel(new ImageIcon("src/main/java/Game/imagenes/vegeta_win.jpeg"));
            fondo3.setBounds(0, 0, 800, 600);


            txt3 = new JLabel();
            txt3.setFont(fuente);
            txt3.setForeground(Color.blue);
            txt3.setBounds(300, 80, 450, 100);
            txt3.setText("¡" + nombreJ2 + " Ganó!");

            
            resultado.add(txt3);
            resultado.add(fondo3);
        //***********************************************************************************************
        } else {
            resultado = new JFrame("Resultado");
            resultado.setTitle("Resultado");
            resultado.setSize(800, 600);
            resultado.setLayout(null);
            resultado.setFocusable(true);
            resultado.setLocationRelativeTo(null);
            resultado.setAlwaysOnTop(true);
            resultado.setDefaultCloseOperation(resultado.DISPOSE_ON_CLOSE);

            resultado.setVisible(true);
            //fondo3
            fondo3 = new JLabel(new ImageIcon("src/main/java/Game/imagenes/GyV_empate.jpeg"));
            fondo3.setBounds(0, 0, 800, 600);


            txt3 = new JLabel();
            txt3.setFont(fuente);
            txt3.setForeground(Color.black);
            txt3.setBounds(300, 80, 450, 100);
            txt3.setText("¡Empate entre " + nombreJ1 + " y " + nombreJ2 + "!");
            
            resultado.add(txt3);
            resultado.add(fondo3);
        //***********************************************************************************************
        }
        dispose();
    }
}
