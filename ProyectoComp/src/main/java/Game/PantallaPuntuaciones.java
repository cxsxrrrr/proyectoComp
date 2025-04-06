package Game;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class PantallaPuntuaciones extends JFrame {
    private JTextArea txtPuntuaciones;

    public PantallaPuntuaciones() {
        setTitle("Ranking de Puntuaciones");
        setSize(400, 300);
        setLayout(new BorderLayout());

        txtPuntuaciones = new JTextArea();
        txtPuntuaciones.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtPuntuaciones);

        add(scrollPane, BorderLayout.CENTER);
        cargarPuntuaciones();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void cargarPuntuaciones() {
    	//query
        String sql = "SELECT j.nombre, p.tipo_juego, p.puntos FROM puntuaciones p " +
                     "JOIN jugadores j ON p.jugador_id = j.id ORDER BY p.puntos DESC LIMIT 10"; 
        try (Connection conn = BaseDeDatos.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            StringBuilder sb = new StringBuilder("TOP 10 PUNTUACIONES\n\n");
            while (rs.next()) {
                sb.append(rs.getString("nombre"))
                  .append(" - ")
                  .append(rs.getString("tipo_juego"))
                  .append(": ")
                  .append(rs.getInt("puntos"))
                  .append("\n");
            }
            txtPuntuaciones.setText(sb.toString());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "error al cargar puntuaciones: " + e.getMessage());
        }
    }
}


