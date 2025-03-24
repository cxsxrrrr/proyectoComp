package Game;

import java.sql.*;

public class BaseDeDatos {
    private static final String URL = "jdbc:mysql://localhost:3306/minijuegos";
    private static final String USER = "root";  // Cambia esto si tu usuario es diferente
    private static final String PASSWORD = ""; // Cambia si tienes contraseña en MySQL

    // Método para conectar con la base de datos
    public static Connection conectar() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos: " + e.getMessage());
        }
        return conn;
    }

    // Inicializa la base de datos verificando que las tablas existan
    public static void inicializarBD() {
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
            System.out.println("Base de datos inicializada correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al inicializar la base de datos: " + e.getMessage());
        }
    }
}