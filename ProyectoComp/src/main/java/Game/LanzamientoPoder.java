package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LanzamientoPoder extends JFrame {
    private int energiaJ1 = 0, energiaJ2 = 0;
    private JLabel lblEnergiaJ1, lblEnergiaJ2;

    public LanzamientoPoder() {
        setTitle("Lanzamiento de Poder");
        setSize(400, 200);
        setLayout(new GridLayout(3, 1));

        lblEnergiaJ1 = new JLabel("Jugador 1: 0%", SwingConstants.CENTER);
        lblEnergiaJ2 = new JLabel("Jugador 2: 0%", SwingConstants.CENTER);
        JLabel lblInstrucciones = new JLabel("Jugador 1: A | Jugador 2: L", SwingConstants.CENTER);

        add(lblEnergiaJ1);
        add(lblEnergiaJ2);
        add(lblInstrucciones);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == 'a') {
                    energiaJ1 += 5;
                    lblEnergiaJ1.setText("Jugador 1: " + energiaJ1 + "%");
                } else if (e.getKeyChar() == 'l') {
                    energiaJ2 += 5;
                    lblEnergiaJ2.setText("Jugador 2: " + energiaJ2 + "%");
                }

                verificarGanador();
            }
        });

        setFocusable(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void verificarGanador() {
        if (energiaJ1 >= 100) {
            JOptionPane.showMessageDialog(this, "¡Jugador 1 lanza un Kamehameha y gana!");
            dispose();
        } else if (energiaJ2 >= 100) {
            JOptionPane.showMessageDialog(this, "¡Jugador 2 lanza un Kamehameha y gana!");
            dispose();
        }
    }
}
