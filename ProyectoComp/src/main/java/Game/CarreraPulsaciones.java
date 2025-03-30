package Game;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CarreraPulsaciones extends JFrame {
    private JLabel fondo, fondo2, lblJugador1, lblJugador2, nick1, nick2, txt;
    private int posicionJ1 = 5, posicionJ2 = 5;
    private final int META = 540; // 20 píxeles antes del borde derecho
    private JFrame ganador = new JFrame();
    private String Ganador;
    
    public CarreraPulsaciones() {
        setTitle("Carrera");
        setSize(625, 430);
        setLayout(null);
        setFocusable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Fondo
        fondo = new JLabel(new ImageIcon("src/main/java/Game/imagenes/pista_carrera_fondo.jpg"));
        fondo.setBounds(0, 0, 625, 400);
        
        // Carro 1
        lblJugador1 = new JLabel(new ImageIcon("src/main/java/Game/imagenes/carro1.png"));
        lblJugador1.setBounds(posicionJ1, 90, 80, 60);
        
        // Etiqueta Carro 1
        nick1 = new JLabel("Jugador 1");
        nick1.setBounds(posicionJ1, 70, 80, 15);
        nick1.setForeground(Color.blue);
        nick1.setOpaque(true);
        nick1.setBackground(Color.black);
        nick1.setFont(new Font("Pixelated", Font.BOLD, 15));
        
        // Carro 2
        lblJugador2 = new JLabel(new ImageIcon("src/main/java/Game/imagenes/carro2.png"));
        lblJugador2.setBounds(posicionJ2, 245, 80, 60);
        
        // Etiqueta Carro 2
        nick2 = new JLabel("Jugador 2");
        nick2.setBounds(posicionJ2, 225, 80, 15);
        nick2.setForeground(Color.red);
        nick2.setOpaque(true);
        nick2.setBackground(Color.black);
        nick2.setFont(new Font("Pixelated", Font.BOLD, 15));
        
        JLabel lblInstrucciones = new JLabel("Jugador 1: A | Jugador 2: L");
        lblInstrucciones.setBounds(150, 10, 400, 30);
        lblInstrucciones.setForeground(Color.yellow);
        lblInstrucciones.setFont(new Font("Pixelated", Font.BOLD, 25));
        
        add(nick1);
        add(nick2);
        add(lblJugador1);
        add(lblJugador2);
        add(lblInstrucciones);
        add(fondo);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == 'a' && posicionJ1 < META) {
                    posicionJ1 += 5;
                    lblJugador1.setBounds(posicionJ1, 90, 80, 60);
                    nick1.setBounds(posicionJ1, 70, 80, 15);
                } else if (e.getKeyChar() == 'l' && posicionJ2 < META) {
                    posicionJ2 += 5;
                    lblJugador2.setBounds(posicionJ2, 245, 80, 60);
                    nick2.setBounds(posicionJ2, 225, 80, 15);
                }
                verificarGanador();
            }
        });
        
        setFocusable(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void verificarGanador() {
        
        ganador = new JFrame("Ganador");
        ganador.setTitle("Ganador");
        ganador.setSize(400, 400);
        ganador.setLayout(null);
        ganador.setFocusable(true);
        ganador.setLocationRelativeTo(null);
        ganador.setAlwaysOnTop(true);
        ganador.setDefaultCloseOperation(ganador.EXIT_ON_CLOSE);
        // Fondo
        fondo2 = new JLabel(new ImageIcon("src/main/java/Game/imagenes/copa_piston.jpg"));
        fondo2.setBounds(0, 0, 400, 400);
        //texto
        txt = new JLabel();
        txt.setFont(new Font("Pixelated", Font.BOLD, 20));
        txt.setForeground(Color.white);
        txt.setBounds(5, 200, 400, 30);
        
        if (posicionJ1 >= META) {
        txt.setText("¡FELICIDADES JUGADOR 1 GANASTE!");
        ganador.add(txt);
        ganador.add(fondo2);
        ganador.setVisible(true);
        } else if (posicionJ2 >= META) {
       
        txt.setText("¡FELICIDADES JUGADOR 2 GANASTE!");
        ganador.add(txt);
        ganador.add(fondo2);
        ganador.setVisible(true);
        }
        
    }
}
