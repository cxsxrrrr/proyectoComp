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
                + "juego ENUM('Carrera', 'Poder', 'Salto', 'Reaccion') NOT NULL, "
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
}