package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class DueloVaqueros extends JFrame {
    private JLabel mensaje, jugador1, nombre1, jugador2, nombre2, bala, fondo;
    private boolean listoParaReaccionar = false;
    private boolean juegoTerminado = false;
    private Timer timer, balaTimer;
    private Random random = new Random();

    // Nombres reales desde GestorJugadores
    private String nombreJ1 = GestorJugadores.getJugador1();
    private String nombreJ2 = GestorJugadores.getJugador2();

    public DueloVaqueros() {
        setTitle("Duelo de Vaqueros");
        setSize(600, 400);
        setLayout(null);
        setFocusable(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.white);

        mensaje = new JLabel("¡Prepárate...!", SwingConstants.CENTER);
        mensaje.setFont(new Font("Arial", Font.BOLD, 30));
        mensaje.setBounds(150, 5, 300, 30);
        add(mensaje);

        // Jugador 1
        jugador1 = new JLabel(new ImageIcon("src/main/java/Game/imagenes/vaquero_bueno.png"));
        jugador1.setBounds(50, 290, 80, 80);

        nombre1 = new JLabel(nombreJ1);
        nombre1.setBounds(50, 280, 80, 15);
        nombre1.setForeground(Color.blue);
        nombre1.setOpaque(true);
        nombre1.setBackground(Color.black);
        nombre1.setFont(new Font("Pixelated", Font.BOLD, 15));
        add(jugador1);
        add(nombre1);

        // Jugador 2
        jugador2 = new JLabel(new ImageIcon("src/main/java/Game/imagenes/vaquero_malo.png"));
        jugador2.setBounds(440, 290, 80, 80);

        nombre2 = new JLabel(nombreJ2);
        nombre2.setBounds(440, 280, 80, 15);
        nombre2.setForeground(Color.red);
        nombre2.setOpaque(true);
        nombre2.setBackground(Color.black);
        nombre2.setFont(new Font("Pixelated", Font.BOLD, 15));
        add(jugador2);
        add(nombre2);

        // Bala
        bala = new JLabel("💥");
        bala.setForeground(Color.yellow);
        bala.setVisible(false);
        bala.setBounds(0, 0, 30, 30);
        add(bala);

        // Fondo
        fondo = new JLabel(new ImageIcon("src/main/java/Game/imagenes/fondo_VO.jpg"));
        fondo.setBounds(0, 0, 600, 400);
        add(fondo);

        int tiempoAleatorio = 2000 + random.nextInt(4000); // randomizar el tiempo
        timer = new Timer(tiempoAleatorio, e -> {
            getContentPane().setBackground(Color.black);
            mensaje.setForeground(Color.white);
            mensaje.setText("¡DISPARA!");
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
                    new VentanaPuntuaciones(); // aún así mostramos puntuaciones
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
            iniciarBala(80, 300, 1); // Jugador 1 dispara
        } else if (tecla == 'l') {
            iniciarBala(470, 300, -1); // Jugador 2 dispara
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
                    String ganador = (direccion == 1) ? nombreJ1 : nombreJ2;
                    mostrarResultado(ganador);
                }
            }
        });

        balaTimer.start();
    }

    private void mostrarResultado(String ganador) {
        JOptionPane.showMessageDialog(this, "¡Gana " + ganador + "!");
        GestorPuntuaciones.registrarPuntuacion(ganador, "Duelo", 100);
        dispose();
        new VentanaPuntuaciones(); // Mostrar tabla después del juego
    }
}
