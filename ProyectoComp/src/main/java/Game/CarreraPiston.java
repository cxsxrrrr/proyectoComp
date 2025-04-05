package Game;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CarreraPiston extends JFrame {
    private JLabel fondo, fondo2, jugador1, jugador2, nombre1, nombre2, txt;
    private int posicionJ1 = 5, posicionJ2 = 5;
    private final int meta = 540;
    private JFrame ganador = new JFrame();
    private String Ganador;
    
    public CarreraPiston() {
        setTitle("Carrera");
        setSize(625, 430);
        setLayout(null);
        setFocusable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // fondo
        fondo = new JLabel(new ImageIcon("src/main/java/Game/imagenes/pista_carrera_fondo.jpg"));
        fondo.setBounds(0, 0, 625, 400);
        
        // carro 1
        jugador1 = new JLabel(new ImageIcon("src/main/java/Game/imagenes/carro1.png"));
        jugador1.setBounds(posicionJ1, 90, 80, 60);
        
        nombre1 = new JLabel("Jugador 1");
        nombre1.setBounds(posicionJ1, 70, 80, 15);
        nombre1.setForeground(Color.blue);
        nombre1.setOpaque(true);
        nombre1.setBackground(Color.black);
        nombre1.setFont(new Font("Pixelated", Font.BOLD, 15));
        
        // carro 2
        jugador2 = new JLabel(new ImageIcon("src/main/java/Game/imagenes/carro2.png"));
        jugador2.setBounds(posicionJ2, 245, 80, 60);
        nombre2 = new JLabel("Jugador 2");
        nombre2.setBounds(posicionJ2, 225, 80, 15);
        nombre2.setForeground(Color.red);
        nombre2.setOpaque(true);
        nombre2.setBackground(Color.black);
        nombre2.setFont(new Font("Pixelated", Font.BOLD, 15));
        
        
        
        JLabel tuto = new JLabel("Jugador 1: A | Jugador 2: L");
        tuto.setBounds(150, 10, 400, 30);
        tuto.setForeground(Color.yellow);
        tuto.setFont(new Font("Pixelated", Font.BOLD, 25));
        
        add(nombre1);
        add(nombre2);
        add(jugador1);
        add(jugador2);
        add(tuto);
        add(fondo);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == 'a' && posicionJ1 < meta) {
                    posicionJ1 += 5;
                    jugador1.setBounds(posicionJ1, 90, 80, 60);
                    nombre1.setBounds(posicionJ1, 70, 80, 15);
                } else if (e.getKeyChar() == 'l' && posicionJ2 < meta) {
                    posicionJ2 += 5;
                    jugador2.setBounds(posicionJ2, 245, 80, 60);
                    nombre2.setBounds(posicionJ2, 225, 80, 15);
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
        // fondo
        fondo2 = new JLabel(new ImageIcon("src/main/java/Game/imagenes/copa_piston.jpg"));
        fondo2.setBounds(0, 0, 400, 400);
        //texto
        txt = new JLabel();
        txt.setFont(new Font("Pixelated", Font.BOLD, 20));
        txt.setForeground(Color.white);
        txt.setBounds(5, 200, 400, 30);
        
        if (posicionJ1 >= meta) {
        txt.setText("¡FELICIDADES JUGADOR 1 GANASTE!");
        ganador.add(txt);
        ganador.add(fondo2);
        ganador.setVisible(true);
        } else if (posicionJ2 >= meta) {
       
        txt.setText("¡FELICIDADES JUGADOR 2 GANASTE!");
        ganador.add(txt);
        ganador.add(fondo2);
        ganador.setVisible(true);
        }
        
    }
}
