package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class DueloVaqueros extends JFrame {
    private JLabel lblMensaje, jugador1,nick1, jugador2,nick2,  bala, fondo;
    private boolean listoParaReaccionar = false;
    private boolean juegoTerminado = false;
    private Timer timer, balaTimer;
    private Random random = new Random();

    public DueloVaqueros() {
        setTitle("Duelo de Vaqueros");
        setSize(600, 400);
        setLayout(null);
        setFocusable(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.white);

        lblMensaje = new JLabel("¡Prepárate...!", SwingConstants.CENTER);
        lblMensaje.setFont(new Font("Arial", Font.BOLD, 30));
        lblMensaje.setBounds(150, 5, 300, 30);
        add(lblMensaje);
        
        //Jugador 1
        jugador1 = new JLabel(new ImageIcon("src/main/java/Game/imagenes/vaquero_bueno.png"));
        jugador1.setBounds(50, 290, 80, 80);
        
        // Etiqueta Jugador 1
        nick1 = new JLabel("Jugador 1");
        nick1.setBounds(50, 280, 80, 15);
        nick1.setForeground(Color.blue);
        nick1.setOpaque(true);
        nick1.setBackground(Color.black);
        nick1.setFont(new Font("Pixelated", Font.BOLD, 15));
        add(jugador1);
        add(nick1);

        //Jugador 2
        jugador2 = new JLabel(new ImageIcon("src/main/java/Game/imagenes/vaquero_malo.png"));
        jugador2.setBounds(440, 290, 80, 80);
        
        // Etiqueta Jugador 2
        nick2 = new JLabel("Jugador 2");
        nick2.setBounds(440, 280, 80, 15);
        nick2.setForeground(Color.red);
        nick2.setOpaque(true);
        nick2.setBackground(Color.black);
        nick2.setFont(new Font("Pixelated", Font.BOLD, 15));
        add(jugador2);
        add(nick2);

        bala = new JLabel("💥");
        bala.setForeground(Color.yellow);
        bala.setVisible(false);
        bala.setBounds(0, 0, 30, 30);
        add(bala);
        
        // Fondo
        fondo = new JLabel(new ImageIcon("src/main/java/Game/imagenes/fondo_VO.jpg"));
        fondo.setBounds(0, 0, 600, 400);
        add(fondo);

        int tiempoAleatorio = 2000 + random.nextInt(4000);
        timer = new Timer(tiempoAleatorio, e -> {
            getContentPane().setBackground(Color.black);
            lblMensaje.setForeground(Color.white);
            lblMensaje.setText("¡DISPARA!");
            listoParaReaccionar = true;
            timer.stop();
        });
        timer.start();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (juegoTerminado) return;
                if (!listoParaReaccionar) {
                    JOptionPane.showMessageDialog(DueloVaqueros.this, "¡Disparo antes de tiempo! Perdiste.");
                    dispose();
                } else {
                    disparar(e.getKeyChar());
                }
            }
        });

        setFocusable(true);
        setVisible(true);
    }

    private void disparar(char tecla) {
        if (tecla == 'a') {
            iniciarBala(80, 300, 1);
        } else if (tecla == 'l') {
            iniciarBala(470, 300, -1);
        }
    }

    private void iniciarBala(int x, int y, int direccion) {
        bala.setBounds(x, y, 30, 30);
        bala.setVisible(true);
        juegoTerminado = true;
        balaTimer = new Timer(10, new ActionListener() {
            int posX = x;

            @Override
            public void actionPerformed(ActionEvent e) {
                posX += 5 * direccion;
                bala.setBounds(posX, y, 30, 30);
                if ((direccion == 1 && posX >= 470) || (direccion == -1 && posX <= 80)) {
                    balaTimer.stop();
                    JOptionPane.showMessageDialog(DueloVaqueros.this, "¡Gana " + (direccion == 1 ? "Jugador 1" : "Jugador 2") + "!");
                    dispose();
                }
            }
        });
        balaTimer.start();
    }
}
