package Game;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GestorPuntuaciones {

    // metodo para obtener el ID del jugador
    private static int obtenerIdJugador(String nombreJugador) {
        String sql = "SELECT id FROM jugadores WHERE nombre = ?";
        try (Connection conexion = BaseDeDatos.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, nombreJugador);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener ID del jugador: " + e.getMessage());
        }
        return -1; // no encontrado
    }
    
    public static void registrarPuntuacion(String nombreJugador, String tipoJuego, int puntos) {
        int jugadorId = obtenerIdJugador(nombreJugador);
        if (jugadorId != -1) {
            BaseDeDatos.sumarPuntos(jugadorId, tipoJuego, puntos);
        } else {
            System.out.println("No se encontró el jugador: " + nombreJugador);
        }
    }

    //metodo para sumar puntos a un jugador para cada juego
    public void agregarPuntos(String nombreJugador, String tipoJuego, int puntos) {
        int jugadorId = obtenerIdJugador(nombreJugador);
        if (jugadorId != -1) {
            BaseDeDatos.sumarPuntos(jugadorId, tipoJuego, puntos);
        } else {
            System.out.println("Jugador no encontrado en la base de datos.");
        }
    }
    
    
}
