package Game;

import java.sql.*;

public class BaseDeDatos {
    private static final String URL = "jdbc:mysql://localhost:3306/minijuegos";
    private static final String USER = "root"; 
    private static final String PASSWORD = ""; // pilas

    // conectar con la bdd
    public static Connection conectar() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("error al conectar con la bdd: " + e.getMessage());
        }
        return conn;
    }


    public static void inicializarBD() {
    	// crear tablas si no existen 
        String jugadoresSQL = "CREATE TABLE IF NOT EXISTS jugadores ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "nombre VARCHAR(50) NOT NULL UNIQUE, "
                + "puntuacion INT DEFAULT 0)";

        String puntuacionesSQL = "CREATE TABLE IF NOT EXISTS puntuaciones ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "jugador_id INT, "
                + "juego VARCHAR(20) NOT NULL, "
                + "puntos INT NOT NULL, "
                + "FOREIGN KEY (jugador_id) REFERENCES jugadores(id) ON DELETE CASCADE)";

        try (Connection conn = conectar();
             Statement stmt = conn.createStatement()) {
            stmt.execute(jugadoresSQL);
            stmt.execute(puntuacionesSQL);
        } catch (SQLException e) {
            System.out.println("error al iniciar la bdd: " + e.getMessage());
        }
    }
    
    public static void sumarPuntos(int jugadorId, String tipoJuego, int puntos) {
        String selectSQL = "SELECT puntos FROM puntuaciones WHERE jugador_id = ? AND juego = ?";
        String insertSQL = "INSERT INTO puntuaciones (jugador_id, juego, puntos) VALUES (?, ?, ?)";
        String updateSQL = "UPDATE puntuaciones SET puntos = puntos + ? WHERE jugador_id = ? AND juego = ?";

        try (Connection conn = conectar();
             PreparedStatement selectStmt = conn.prepareStatement(selectSQL)) {

            selectStmt.setInt(1, jugadorId);
            selectStmt.setString(2, tipoJuego);
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                try (PreparedStatement updateStmt = conn.prepareStatement(updateSQL)) {
                    updateStmt.setInt(1, puntos);
                    updateStmt.setInt(2, jugadorId);
                    updateStmt.setString(3, tipoJuego);
                    updateStmt.executeUpdate();
                }
            } else {
                try (PreparedStatement insertStmt = conn.prepareStatement(insertSQL)) {
                    insertStmt.setInt(1, jugadorId);
                    insertStmt.setString(2, tipoJuego);
                    insertStmt.setInt(3, puntos);
                    insertStmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            System.out.println("error al sumar puntos: " + e.getMessage());
        }
    }
    
}