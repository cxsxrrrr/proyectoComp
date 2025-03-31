package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class VelociRunner extends JFrame {
    private GamePanel gamePanel;

    public VelociRunner() {
        setTitle("VELOCIRUNNER: ESCAPANDO DEL COMUNISMO");
        setSize(799, 295);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        gamePanel = new GamePanel();
        add(gamePanel);

        addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_SPACE) {
                    gamePanel.jump();
                }
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new VelociRunner();
    }
}

class GamePanel extends JPanel implements ActionListener {
    private Timer timer;
    private JLabel lblJugador, lblPuntaje, fondo;
    private ArrayList<JLabel> obstaculos;
    private int playerY = 200, playerVelocity = 0;
    private final int gravity = 2;
    private boolean isJumping = false;
    private int score = 0;
    private Random rand;
    private int lastObstacleX = 800; // Posición del último obstáculo generado

    public GamePanel() {
        setLayout(null);
        setBackground(Color.WHITE);
        rand = new Random();
        
        // Fondo
        fondo = new JLabel(new ImageIcon("src/main/java/Game/imagenes/fondo_VELOCIRUNNER.jpg"));
        fondo.setBounds(0, -180, 800, 436); // Ajuste para alinear la base con el frame
        

        // Jugador
        lblJugador = new JLabel(new ImageIcon("src/main/java/Game/imagenes/velociraptor.png"));
        lblJugador.setBounds(50, playerY, 50, 50);
        add(lblJugador);

        // Etiqueta de puntaje
        lblPuntaje = new JLabel("Puntaje: 0");
        lblPuntaje.setForeground(Color.white);
        lblPuntaje.setFont(new Font("Arial", Font.BOLD, 20));
        lblPuntaje.setBounds(650, 10, 150, 30); // Subir un poco para mejor visibilidad
        add(lblPuntaje);
        
        obstaculos = new ArrayList<>();

        timer = new Timer(20, this);
        timer.start();
        spawnObstacle();
        add(fondo);
    }
    
    public void jump() {
        if (!isJumping) {
            isJumping = true;
            playerVelocity = -25;
        }
    }

    private void spawnObstacle() {
        // Verifica que el último obstáculo esté lo suficientemente lejos
        if (obstaculos.isEmpty() || lastObstacleX < 550) {
            JLabel obstaculo = new JLabel(new ImageIcon("src/main/java/Game/imagenes/simbolo_comunista.png"));
            int altura = 200; // Ajustado para estar sobre el suelo del fondo
            obstaculo.setBounds(800, altura, 38, 48);
            add(obstaculo);
            obstaculos.add(obstaculo);
            lastObstacleX = 800; // Actualiza la posición del último obstáculo generado

            // Asegurar que el obstáculo esté al frente del fondo
            setComponentZOrder(obstaculo, 0);

            // Programa el siguiente obstáculo en un tiempo aleatorio
            new Timer(2000 + rand.nextInt(1000), e -> spawnObstacle()).start();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Gravedad y salto
        if (isJumping) {
            playerY += playerVelocity;
            playerVelocity += gravity;
            if (playerY >= 200) {
                playerY = 200;
                isJumping = false;
            }
            lblJugador.setBounds(50, playerY, 50, 50);
        }

        // Mover obstáculos
        for (int i = 0; i < obstaculos.size(); i++) {
            JLabel obstaculo = obstaculos.get(i);
            obstaculo.setLocation(obstaculo.getX() - 5, obstaculo.getY());

            if (obstaculo.getX() + obstaculo.getWidth() < 0) {
                remove(obstaculo);
                obstaculos.remove(i);
                i--;
            }

            // Colisión
            if (lblJugador.getBounds().intersects(obstaculo.getBounds())) {
                timer.stop();
                JOptionPane.showMessageDialog(this, "Game Over! Puntaje: " + score);
                System.exit(0);
            }
        }

        // Actualizar puntaje constantemente
        score += 1;
        lblPuntaje.setText("Puntaje: " + score);

        // Actualizar la última posición del obstáculo más reciente
        if (!obstaculos.isEmpty()) {
            lastObstacleX = obstaculos.get(obstaculos.size() - 1).getX();
        }

        repaint();
    }
}
