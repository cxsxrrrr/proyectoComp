package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class GestorJugadores extends JFrame {
	private JTextField txtNombre;
	private DefaultListModel<String> modeloLista;
	private JList<String> listaJugadores;

	public GestorJugadores() {
		setTitle("Gestión de Jugadores");
		setSize(400, 300);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		modeloLista = new DefaultListModel<>();
		listaJugadores = new JList<>(modeloLista);
		actualizarLista();

		JPanel panelSuperior = new JPanel();
		panelSuperior.add(new JLabel("Nombre:"));
		txtNombre = new JTextField(15);
		panelSuperior.add(txtNombre);

		JButton btnAgregar = new JButton("Agregar");
		JButton btnEliminar = new JButton("Eliminar");

		btnAgregar.addActionListener(e -> agregarJugador());
		btnEliminar.addActionListener(e -> eliminarJugador());

		JPanel panelBotones = new JPanel();
		panelBotones.add(btnAgregar);
		panelBotones.add(btnEliminar);

		add(panelSuperior, BorderLayout.NORTH);
		add(new JScrollPane(listaJugadores), BorderLayout.CENTER);
		add(panelBotones, BorderLayout.SOUTH);

		setVisible(true);
	}

	private void actualizarLista() {
		modeloLista.clear();
		try (Connection conn = BaseDeDatos.conectar();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT nombre FROM jugadores")) {
			while (rs.next()) {
				modeloLista.addElement(rs.getString("nombre"));
			}
		} catch (SQLException e) {
			System.out.println("Error al cargar jugadores: " + e.getMessage());
		}
	}

	private void agregarJugador() {
		String nombre = txtNombre.getText().trim();
		if (nombre.isEmpty()) {
			JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío.");
			return;
		}

		String sql = "INSERT INTO jugadores (nombre) VALUES (?)";
		try (Connection conn = BaseDeDatos.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, nombre);
			pstmt.executeUpdate();
			actualizarLista();
			txtNombre.setText("");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Error al agregar jugador: " + e.getMessage());
		}
	}

	private void eliminarJugador() {
		String jugadorSeleccionado = listaJugadores.getSelectedValue();
		if (jugadorSeleccionado == null) {
			JOptionPane.showMessageDialog(this, "Seleccione un jugador para eliminar.");
			return;
		}

		String sql = "DELETE FROM jugadores WHERE nombre = ?";
		try (Connection conn = BaseDeDatos.conectar(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, jugadorSeleccionado);
			pstmt.executeUpdate();
			actualizarLista();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, "Error al eliminar jugador: " + e.getMessage());
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(GestorJugadores::new);
	}
}
