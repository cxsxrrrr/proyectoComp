package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class DueloReaccion extends JFrame {
    private JLabel lblMensaje;
    private Timer timer;
    private boolean listoParaReaccionar = false;

    public DueloReaccion() {
        setTitle("Duelo de Reacción");
        setSize(400, 200);
        setLayout(new BorderLayout());

        lblMensaje = new JLabel("Prepárate...", SwingConstants.CENTER);
        lblMensaje.setFont(new Font("Arial", Font.BOLD, 18));
        add(lblMensaje, BorderLayout.CENTER);

        Random random = new Random();
        int tiempoAleatorio = 2000 + random.nextInt(4000);

        timer = new Timer(tiempoAleatorio, e -> {
            lblMensaje.setText("¡Presiona una tecla!");
            listoParaReaccionar = true;
            timer.stop();
        });
        timer.start();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (listoParaReaccionar) {
                    if (e.getKeyChar() == 'a') {
                        JOptionPane.showMessageDialog(DueloReaccion.this, "¡Jugador 1 ganó!");
                    } else if (e.getKeyChar() == 'l') {
                        JOptionPane.showMessageDialog(DueloReaccion.this, "¡Jugador 2 ganó!");
                    }
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(DueloReaccion.this, "¡Demasiado pronto! Perdiste.");
                }
            }
        });

        setFocusable(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }
}

