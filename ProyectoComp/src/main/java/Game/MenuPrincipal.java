package Game;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class MenuPrincipal extends JFrame {
    private JLabel lblContadorJugadores;
    private JButton btnJugar, btnGestionarJugadores;
    
    public MenuPrincipal() {
        setTitle("Menú Principal - Minijuegos");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        JPanel panelSuperior = new JPanel();
        lblContadorJugadores = new JLabel("Jugadores registrados: " + contarJugadores());
        panelSuperior.add(lblContadorJugadores);

        btnJugar = new JButton("Jugar");
        btnJugar.setFocusPainted(false);
        btnJugar.setContentAreaFilled(false);
        btnJugar.setOpaque(true);
        btnJugar.setBackground(new Color(100, 200, 255));
        btnJugar.setForeground(Color.WHITE);
        btnJugar.setFont(new Font("Arial", Font.BOLD, 16));


        btnJugar.setBorder(new LineBorder(new Color(0, 150, 200), 2, true));  
        btnJugar.setPreferredSize(new Dimension(120, 40));
        
        
        btnGestionarJugadores = new JButton("Gestionar Jugadores");
        btnGestionarJugadores.setFocusPainted(false);

        btnGestionarJugadores.setContentAreaFilled(false);
        btnGestionarJugadores.setOpaque(true);
        btnGestionarJugadores.setBackground(new Color(100, 200, 255));
        btnGestionarJugadores.setForeground(Color.WHITE);
        btnGestionarJugadores.setFont(new Font("Arial", Font.BOLD, 16));


        btnGestionarJugadores.setBorder(new LineBorder(new Color(0, 150, 200), 2, true));  
        btnGestionarJugadores.setPreferredSize(new Dimension(180, 40));
        
        btnJugar.addActionListener(e -> verificarJugadoresYJugar());
        btnGestionarJugadores.addActionListener(e -> {
            new GestorJugadores();
            
            actualizarContador(); // Para reflejar cambios en el número de jugadores
        });

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnJugar);
        panelBotones.add(btnGestionarJugadores);
        panelBotones.setBackground(Color.black);
        add(panelSuperior, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.CENTER);

        setVisible(true);
    }

    // Método para contar los jugadores en la base de datos
    private int contarJugadores() {
        int cantidad = 0;
        String sql = "SELECT COUNT(*) AS total FROM jugadores";
        try (Connection conn = BaseDeDatos.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                cantidad = rs.getInt("total");
            }
        } catch (SQLException e) {
            System.out.println("Error al contar jugadores: " + e.getMessage());
        }
        return cantidad;
    }

    private void actualizarContador() {
        lblContadorJugadores.setText("Jugadores registrados: " + contarJugadores());
    }

    private void verificarJugadoresYJugar() {
        if (contarJugadores() < 2) {
            JOptionPane.showMessageDialog(this, "Debe haber al menos 2 jugadores registrados para jugar.");
        } else {
            abrirSeleccionDeJuego();
        }
    }

    private void abrirSeleccionDeJuego() {
        String[] opciones = {"Carrera de Pulsaciones", "Lanzamiento de Poder", "Salto de Precisión", "Duelo de Reacción"};
        String seleccion = (String) JOptionPane.showInputDialog(
                this, "Selecciona un juego:", "Seleccionar Juego",
                JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

        if (seleccion != null) {
            switch (seleccion) {
                case "Carrera de Pulsaciones":
                    new CarreraPulsaciones();
                    break;
                case "Lanzamiento de Poder":
                    new LanzamientoPoder();
                    break;
                case "Salto de Precisión":
                    new SaltoPrecision();
                    break;
                case "Duelo de Reacción":
                    new DueloReaccion();
                    break;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MenuPrincipal::new);
    }
}
