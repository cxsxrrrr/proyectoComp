package Game;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class VentanaPuntuaciones extends JFrame {

    public VentanaPuntuaciones() {
        setTitle("Puntuaciones");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Tabla de Puntuaciones", JLabel.CENTER);
        titulo.setFont(new Font("Pixelated", Font.BOLD, 24));
        titulo.setForeground(Color.white);
        titulo.setBackground(Color.black);
        titulo.setOpaque(true);
        add(titulo, BorderLayout.NORTH);

        String[] columnas = {"Jugador", "Juego", "Puntos"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        JTable tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);
        add(scroll, BorderLayout.CENTER);

        cargarPuntuaciones(modelo);

       
        JButton volver = new JButton("Volver al Menú");
        volver.addActionListener(e -> {
            dispose();
            new MenuPrincipal(); // devolvemos al menu principal
        });
        add(volver, BorderLayout.SOUTH);

        setVisible(true);
        
        
    }

    private void cargarPuntuaciones(DefaultTableModel modelo) {
        String sql = "SELECT jugadores.nombre, puntuaciones.juego, puntuaciones.puntos " +
                     "FROM puntuaciones " +
                     "JOIN jugadores ON puntuaciones.jugador_id = jugadores.id " +
                     "ORDER BY puntos DESC";

        try (Connection conn = BaseDeDatos.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String nombre = rs.getString("nombre");
                String juego = rs.getString("juego");
                int puntos = rs.getInt("puntos");
                modelo.addRow(new Object[]{nombre, juego, puntos});
            }

        } catch (SQLException e) {
            System.out.println("Error al cargar puntuaciones: " + e.getMessage());
        }
    }
}
