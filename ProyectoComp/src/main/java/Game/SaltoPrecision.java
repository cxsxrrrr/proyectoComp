package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SaltoPrecision extends JFrame {
    private JProgressBar barraProgreso;
    private Timer timer;
    private boolean direccion = true;
    private JLabel lblInstrucciones;

    public SaltoPrecision() {
        setTitle("Salto de Precisión");
        setSize(400, 200);
        setLayout(new BorderLayout());

        barraProgreso = new JProgressBar(0, 100);
        barraProgreso.setValue(0);
        barraProgreso.setStringPainted(true);

        lblInstrucciones = new JLabel("Presiona ESPACIO cuando la barra esté en 50%", SwingConstants.CENTER);

        add(barraProgreso, BorderLayout.CENTER);
        add(lblInstrucciones, BorderLayout.SOUTH);

        timer = new Timer(50, e -> moverBarra());
        timer.start();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    verificarPrecision();
                }
            }
        });

        setFocusable(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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

    private void verificarPrecision() {
        int valor = barraProgreso.getValue();
        if (valor >= 45 && valor <= 55) {
            JOptionPane.showMessageDialog(this, "¡Salto Perfecto! Has ganado.");
        } else {
            JOptionPane.showMessageDialog(this, "¡Fallaste! Inténtalo de nuevo.");
        }
        dispose();
    }
}

