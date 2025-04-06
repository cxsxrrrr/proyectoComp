package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class VelociRunner extends JFrame {
    private PanelJuego panelJuego;

    public VelociRunner() {
        setTitle("VELOCIRUNNER: ESCAPANDO DEL COMUNISMO");
        setSize(799, 295);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // <-- para no cerrar todo el programa
        setResizable(false);
        setLocationRelativeTo(null);

        panelJuego = new PanelJuego();
        add(panelJuego);

        addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_SPACE) {
                    panelJuego.jump();
                }
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new VelociRunner();
    }
}

class PanelJuego extends JPanel implements ActionListener {
    private Timer timer;
    private JLabel jugador, nombreJugador, puntaje, fondo;
    private ArrayList<JLabel> obstaculos;
    private int jugY = 200, velocidad = 0;
    private final int gravedad = 2;
    private boolean salto = false;
    private int puntuacion = 0;
    private Random rand;
    private int ultimoObst = 800;

    public PanelJuego() {
        setLayout(null);
        setBackground(Color.WHITE);
        rand = new Random();

        fondo = new JLabel(new ImageIcon("src/main/java/Game/imagenes/fondo_VELOCIRUNNER.jpg"));
        fondo.setBounds(0, -180, 800, 436);

        // nombre del jugador
        nombreJugador = new JLabel(GestorJugadores.getJugador1());
        nombreJugador.setBounds(50, 190, 80, 15);
        nombreJugador.setForeground(Color.green);
        nombreJugador.setOpaque(true);
        nombreJugador.setBackground(Color.black);
        nombreJugador.setFont(new Font("Pixelated", Font.BOLD, 15));
        add(nombreJugador);

        // jugador
        jugador = new JLabel(new ImageIcon("src/main/java/Game/imagenes/velociraptor.png"));
        jugador.setBounds(50, jugY, 50, 50);
        add(jugador);

        // puntos
        puntaje = new JLabel("Puntaje: 0");
        puntaje.setForeground(Color.white);
        puntaje.setFont(new Font("Arial", Font.BOLD, 20));
        puntaje.setBounds(650, 10, 150, 30);
        add(puntaje);

        obstaculos = new ArrayList<>();

        timer = new Timer(20, this);
        timer.start();
        generarObstaculo();

        add(fondo);
    }

    public void jump() {
        if (!salto) {
            salto = true;
            velocidad = -25;
        }
    }

    private void generarObstaculo() {
        if (obstaculos.isEmpty() || ultimoObst < 550) {
            JLabel obstaculo = new JLabel(new ImageIcon("src/main/java/Game/imagenes/simbolo_comunista.png"));
            int altura = 200;
            obstaculo.setBounds(800, altura, 38, 48);
            add(obstaculo);
            obstaculos.add(obstaculo);
            ultimoObst = 800;
            setComponentZOrder(obstaculo, 0);
            new Timer(2000 + rand.nextInt(1000), e -> generarObstaculo()).start();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // salto y gravedad
        if (salto) {
            jugY += velocidad;
            velocidad += gravedad;
            if (jugY >= 200) {
                jugY = 200;
                salto = false;
            }
            jugador.setBounds(50, jugY, 50, 50);
            nombreJugador.setBounds(50, jugY - 10, 80, 15);
        }

        // mover obstáculos
        for (int i = 0; i < obstaculos.size(); i++) {
            JLabel obstaculo = obstaculos.get(i);
            obstaculo.setLocation(obstaculo.getX() - 5, obstaculo.getY());

            if (obstaculo.getX() + obstaculo.getWidth() < 0) {
                remove(obstaculo);
                obstaculos.remove(i);
                i--;
            }

            if (jugador.getBounds().intersects(obstaculo.getBounds())) {
                timer.stop();

                // Sumar puntaje al jugador 1
                String nombre = GestorJugadores.getJugador1();
                int id = new GestorJugadores().obtenerIdJugadorPorNombre(nombre);
                BaseDeDatos.sumarPuntos(id, "VelociRunner", puntuacion); // guarda lo que hizo en partida

                JOptionPane.showMessageDialog(this, "¡Game Over!\nPuntaje: " + puntuacion);

                // Mostrar ventana de puntuaciones después de 1 segundo
                Timer ventanaPuntajesTimer = new Timer(1000, evt -> {
                    new VentanaPuntuaciones();
                    SwingUtilities.getWindowAncestor(this).dispose(); // cerrar ventana de juego
                });
                ventanaPuntajesTimer.setRepeats(false);
                ventanaPuntajesTimer.start();

                return;
            }
        }

        puntuacion += 1;
        puntaje.setText("Puntaje: " + puntuacion);

        if (!obstaculos.isEmpty()) {
            ultimoObst = obstaculos.get(obstaculos.size() - 1).getX();
        }

        repaint();
    }
}
