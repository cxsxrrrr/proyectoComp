package Game;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CarreraPulsaciones extends JFrame {
    private JLabel lblJugador1, lblJugador2;
    private int puntosJ1 = 0, puntosJ2 = 0;

    public CarreraPulsaciones() {
        setTitle("Carrera de Pulsaciones");
        setSize(400, 200);
        setLayout(new GridLayout(3, 1));

        lblJugador1 = new JLabel("Jugador 1: 0 pulsaciones", SwingConstants.CENTER);
        lblJugador2 = new JLabel("Jugador 2: 0 pulsaciones", SwingConstants.CENTER);
        JLabel lblInstrucciones = new JLabel("Jugador 1: A | Jugador 2: L", SwingConstants.CENTER);

        add(lblJugador1);
        add(lblJugador2);
        add(lblInstrucciones);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == 'a') {
                    puntosJ1++;
                    lblJugador1.setText("Jugador 1: " + puntosJ1 + " pulsaciones");
                } else if (e.getKeyChar() == 'l') {
                    puntosJ2++;
                    lblJugador2.setText("Jugador 2: " + puntosJ2 + " pulsaciones");
                }

                verificarGanador();
            }
        });

        setFocusable(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void verificarGanador() {
        if (puntosJ1 >= 20) {
            JOptionPane.showMessageDialog(this, "¡Jugador 1 gana la carrera!");
            dispose();
        } else if (puntosJ2 >= 20) {
            JOptionPane.showMessageDialog(this, "¡Jugador 2 gana la carrera!");
            dispose();
        }
    }
}

