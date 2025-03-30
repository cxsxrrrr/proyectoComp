package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class SaltoPrecision extends JFrame {
    private JProgressBar barraProgreso;
    private Timer timer;
    private boolean direccion = true;
    private JLabel lblInstrucciones;
    private int objetivo;
    private int turno = 1;
    private int[] resultados = new int[2];
    private JLabel jugador1, jugador2, fondo, fondo2,fondo3, txt, txt2, txt3;
    private JFrame nivelKi, resultado;
    private Font fuente = new Font("Arial", Font.BOLD, 30);
    // Obtener nombres desde GestorJugadores
    private String nombreJugador1 = GestorJugadores.getJugador1();
    private String nombreJugador2 = GestorJugadores.getJugador2();

    public SaltoPrecision() {
        setTitle("Dragon Ball 8-Bit Battle");
        setSize(600, 400);
        setLayout(null);
        setFocusable(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        System.out.println("Jugador 1: " + nombreJugador1);
        System.out.println("Jugador 2: " + nombreJugador2);

        
        // Fondo
        fondo = new JLabel(new ImageIcon("src/main/java/Game/imagenes/fondo8bit.jpg"));
        fondo.setBounds(0, 0, 600, 400);

        // Jugadores
        jugador1 = new JLabel(new ImageIcon("src/main/java/Game/imagenes/goku.png"));
        jugador1.setBounds(50, 200, 100, 100);
        jugador2 = new JLabel(new ImageIcon("src/main/java/Game/imagenes/vegeta.png"));
        jugador2.setBounds(450, 200, 100, 100);

        // Barra de poder
        barraProgreso = new JProgressBar(0, 100);
        barraProgreso.setValue(0);
        barraProgreso.setStringPainted(true);
        barraProgreso.setBounds(150, 50, 300, 30);

        lblInstrucciones = new JLabel(nombreJugador1 + ", presiona ESPACIO", SwingConstants.CENTER);
        lblInstrucciones.setBounds(150, 300, 300, 30);
        lblInstrucciones.setForeground(Color.red);
        lblInstrucciones.setFont(new Font("Pixelated", Font.BOLD, 20));

        add(jugador1);
        add(jugador2);
        add(barraProgreso);
        add(lblInstrucciones);
        add(fondo);

        objetivo = new Random().nextInt(20) * 5 + 5;
        
        //Frame para saber nivel de Ki
        nivelKi = new JFrame("Nivel de Ki");
        nivelKi.setTitle("Nivel de Ki");
        nivelKi.setSize(800, 600);
        nivelKi.setLayout(null);
        nivelKi.setFocusable(true);
        nivelKi.setLocationRelativeTo(null);
        nivelKi.setAlwaysOnTop(true);
        nivelKi.setDefaultCloseOperation(nivelKi.DISPOSE_ON_CLOSE);
        
        nivelKi.setVisible(true);
        // Fondo
        fondo2 = new JLabel(new ImageIcon("src/main/java/Game/imagenes/fondo_resplandor.jpg"));
        fondo2.setBounds(0, 0, 800, 600);
        
        
        
        txt = new JLabel();
        txt.setFont(fuente);
        txt.setForeground(Color.white);
        txt.setBounds(200, 150, 450, 100);
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
            lblInstrucciones.setText(nombreJugador2 + ", presiona ESPACIO");
        } else {
            determinarGanador();
        }
    }


    private void determinarGanador() {
        int diff1 = Math.abs(objetivo - resultados[0]);
        int diff2 = Math.abs(objetivo - resultados[1]);
        String mensaje;
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
            // Fondo
            fondo3 = new JLabel(new ImageIcon("src/main/java/Game/imagenes/goku_win.jpeg"));
            fondo3.setBounds(0, 0, 800, 600);


            txt3 = new JLabel();
            txt3.setFont(fuente);
            txt3.setForeground(Color.orange);
            txt3.setBounds(300, 80, 450, 100);
            txt3.setText("¡" + nombreJugador1 + " Ganó!");
            
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
            // Fondo
            fondo3 = new JLabel(new ImageIcon("src/main/java/Game/imagenes/vegeta_win.jpeg"));
            fondo3.setBounds(0, 0, 800, 600);


            txt3 = new JLabel();
            txt3.setFont(fuente);
            txt3.setForeground(Color.blue);
            txt3.setBounds(300, 80, 450, 100);
            txt3.setText("¡" + nombreJugador2 + " Ganó!");

            
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
            // Fondo
            fondo3 = new JLabel(new ImageIcon("src/main/java/Game/imagenes/GyV_empate.jpeg"));
            fondo3.setBounds(0, 0, 800, 600);


            txt3 = new JLabel();
            txt3.setFont(fuente);
            txt3.setForeground(Color.black);
            txt3.setBounds(300, 80, 450, 100);
            txt3.setText("¡Empate entre " + nombreJugador1 + " y " + nombreJugador2 + "!");
            
            resultado.add(txt3);
            resultado.add(fondo3);
        //***********************************************************************************************
        }
        dispose();
    }
}
