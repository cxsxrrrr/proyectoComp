package Game;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class VentanaPuntuaciones extends JFrame {

    public VentanaPuntuaciones() {
        setTitle("Puntuaciones");
        setSize(600, 450); 
        setLocationRelativeTo(null); 
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        getContentPane().setBackground(new Color(30, 30, 30));


        JLabel titulo = new JLabel("Tabla de Puntuaciones", JLabel.CENTER);
        titulo.setFont(new Font("Verdana", Font.BOLD, 28));
        titulo.setForeground(new Color(255, 215, 0));
        titulo.setBorder(new EmptyBorder(20, 0, 20, 0));
        add(titulo, BorderLayout.NORTH);

        //las columnas que va a tener la tabla
        String[] columnas = {"Jugador", "Juego", "Puntos"};
        DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0);

        JTable tablaa = new JTable(modeloTabla);
        tablaa.setFont(new Font("Consolas", Font.PLAIN, 16));
        tablaa.setRowHeight(28);
        tablaa.setBackground(new Color(45, 45, 45));
        tablaa.setForeground(Color.WHITE);
        tablaa.setGridColor(new Color(70, 70, 70));
        tablaa.setFillsViewportHeight(true); 


        DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
        centrado.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < tablaa.getColumnCount(); i++) {
            tablaa.getColumnModel().getColumn(i).setCellRenderer(centrado);
        }


        JScrollPane scroll = new JScrollPane(tablaa);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        add(scroll, BorderLayout.CENTER);


        JButton botonVolver = new JButton("Volver al Menú");
        botonVolver.setFont(new Font("Arial", Font.BOLD, 16));
        botonVolver.setBackground(new Color(50, 50, 50));
        botonVolver.setForeground(Color.WHITE);
        botonVolver.setFocusPainted(false);
        botonVolver.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        botonVolver.addActionListener(e -> {
            dispose();
            new MenuPrincipal(); // abrimos el menu otra vez
        });

        JPanel panelBotonAbajo = new JPanel();
        panelBotonAbajo.setBackground(new Color(30, 30, 30));
        panelBotonAbajo.add(botonVolver);
        add(panelBotonAbajo, BorderLayout.SOUTH);

     
        cargarPuntuacionesDesdeBD(modeloTabla);

        setVisible(true);
    }

    // agarra los datos y los mete en la tabla
    private void cargarPuntuacionesDesdeBD(DefaultTableModel modelo) {
        String consultaSQL = "SELECT jugadores.nombre, puntuaciones.juego, puntuaciones.puntos " +
                             "FROM puntuaciones " +
                             "JOIN jugadores ON puntuaciones.jugador_id = jugadores.id " +
                             "ORDER BY puntos DESC";

        try (Connection conn = BaseDeDatos.conectar();
             PreparedStatement stmt = conn.prepareStatement(consultaSQL);
             ResultSet resultado = stmt.executeQuery()) {

            while (resultado.next()) {
                String nombreJugador = resultado.getString("nombre");
                String nombreJuego = resultado.getString("juego");
                int puntos = resultado.getInt("puntos");

                modelo.addRow(new Object[]{nombreJugador, nombreJuego, puntos});
            }

        } catch (SQLException e) {
            // erorr
            System.out.println("error al cargar puntuaciones: " + e.getMessage());
        }
    }
}
