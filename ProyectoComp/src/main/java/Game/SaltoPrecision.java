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
    private JLabel jugador1, jugador2, fondo;

    public SaltoPrecision() {
        setTitle("Dragon Ball 8-Bit Battle");
        setSize(600, 400);
        setLayout(null);
        setFocusable(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

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

        lblInstrucciones = new JLabel("Jugador 1, presiona ESPACIO", SwingConstants.CENTER);
        lblInstrucciones.setBounds(150, 300, 300, 30);
        lblInstrucciones.setForeground(Color.WHITE);
        lblInstrucciones.setFont(new Font("Pixelated", Font.BOLD, 14));

        add(jugador1);
        add(jugador2);
        add(barraProgreso);
        add(lblInstrucciones);
        add(fondo);

        objetivo = new Random().nextInt(20) * 5 + 5;
        JOptionPane.showMessageDialog(this, "¡Carga tu ki al nivel correcto! Objetivo secreto: " + objetivo);

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
            lblInstrucciones.setText("Jugador 2, presiona ESPACIO");
        } else {
            determinarGanador();
        }
    }

    private void determinarGanador() {
        int diff1 = Math.abs(objetivo - resultados[0]);
        int diff2 = Math.abs(objetivo - resultados[1]);
        String mensaje;
        if (diff1 < diff2) {
            mensaje = "¡Goku gana la batalla! Nivel de ki objetivo: " + objetivo + " | Goku: " + resultados[0] + " | Vegeta: " + resultados[1];
        } else if (diff2 < diff1) {
            mensaje = "¡Vegeta gana la batalla! Nivel de ki objetivo: " + objetivo + " | Goku: " + resultados[0] + " | Vegeta: " + resultados[1];
        } else {
            mensaje = "¡Empate de ki! Nivel objetivo: " + objetivo + " | Goku: " + resultados[0] + " | Vegeta: " + resultados[1];
        }
        JOptionPane.showMessageDialog(this, mensaje);
        dispose();
    }
}
