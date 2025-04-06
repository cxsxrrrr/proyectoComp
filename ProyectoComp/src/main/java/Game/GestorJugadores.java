package Game;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GestorJugadores extends JFrame {
    
    private static String jugador1;
    private static String jugador2;

    private JTextField txtNombre;
    private DefaultListModel<String> modeloLista;
    private JList<String> listaJugadores;


    public GestorJugadores() {
        setTitle("Gestión de Jugadores");
        setSize(400, 350);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        modeloLista = new DefaultListModel<>();
        listaJugadores = new JList<>(modeloLista);
        actualizarLista(); // Bdd
        
        JPanel panelSuperior = new JPanel();
        panelSuperior.add(new JLabel("Nombre:"));
        txtNombre = new JTextField(15);
        panelSuperior.add(txtNombre);

        JButton btnAgregar = new JButton("Agregar");
        JButton btnEliminar = new JButton("Eliminar");
//        JButton btnSeleccionar = new JButton("Seleccionar Jugadores");

        btnAgregar.addActionListener(e -> agregarJugador());
        btnEliminar.addActionListener(e -> eliminarJugador());
//        btnSeleccionar.addActionListener(e -> seleccionarJugadores());

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnAgregar);
        panelBotones.add(btnEliminar);
//        panelBotones.add(btnSeleccionar);

        add(panelSuperior, BorderLayout.NORTH);
        add(new JScrollPane(listaJugadores), BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

    }

    private void actualizarLista() {
        modeloLista.clear();
        List<String> jugadores = obtenerJugadoresDesdeBD();
        for (String jugador : jugadores) {
            modeloLista.addElement(jugador);
        }
    }

    private void agregarJugador() {
        String nombre = txtNombre.getText().trim();
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "el nombre no puede estar vacío.");
            return;
        }

        try (Connection conn = BaseDeDatos.conectar();
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO jugadores (nombre) VALUES (?)")) {
            pstmt.setString(1, nombre);
            pstmt.executeUpdate();
            actualizarLista();
            txtNombre.setText("");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "error al agregar jugador: " + e.getMessage());
        }
    }

    private void eliminarJugador() {
        String jugadorSeleccionado = listaJugadores.getSelectedValue();
        if (jugadorSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "seleccione un jugador para eliminar.");
            return;
        }

        try (Connection conn = BaseDeDatos.conectar();
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM jugadores WHERE nombre = ?")) {
            pstmt.setString(1, jugadorSeleccionado);
            pstmt.executeUpdate();
            actualizarLista();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "error al eliminar jugador: " + e.getMessage());
        }
    }

    List<String> obtenerJugadoresDesdeBD() {
        List<String> jugadores = new ArrayList<>();
        try (Connection conn = BaseDeDatos.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT nombre FROM jugadores")) {
            while (rs.next()) {
                jugadores.add(rs.getString("nombre"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al obtener jugadores: " + e.getMessage());
        }
        return jugadores;
    }
    
    public void agregarJugador(String nombre) {
        String sql = "INSERT INTO jugadores (nombre) VALUES (?)";
        try (Connection conn = BaseDeDatos.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al agregar jugador: " + e.getMessage());
        }
    }
    
    public int obtenerIdJugadorPorNombre(String nombre) {
        String sql = "SELECT id FROM jugadores WHERE nombre = ?";
        try (Connection conn = BaseDeDatos.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void setJugador1(String nombre) {
        jugador1 = nombre;
    }

    public static void setJugador2(String nombre) {
        jugador2 = nombre;
    }

    public static String getJugador1() {
        return jugador1;
    }

    public static String getJugador2() {
        return jugador2;
    }
    
    
}
