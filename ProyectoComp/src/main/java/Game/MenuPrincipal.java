package Game;

import javax.swing.*;
import java.util.List;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class MenuPrincipal extends JFrame {
    private GestorJugadores gestorJugadores;
    private List<String> jugadoresBD;
    private JButton btnJugar, btnGestionarJugadores;
    private JLabel lblTitulo, fondo;
    private Font fuente = new Font("Arial", Font.BOLD, 20);
   
    public MenuPrincipal() {
    	gestorJugadores = new GestorJugadores(); // Ahora se inicializa correctamente
    	jugadoresBD = gestorJugadores.obtenerJugadoresDesdeBD(); // Se obtiene después de inicializar

        setTitle("Menú Principal");
        setSize(1080,720); 
        setLayout(null);
        setFocusable(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Fondo
        fondo = new JLabel(new ImageIcon("src/main/java/Game/imagenes/fondo_MENU.jpg"));
        fondo.setBounds(0, 0, 1080, 720);
        
        // Título
        lblTitulo = new JLabel("ARCADEA", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(Color.YELLOW);
        lblTitulo.setBounds(430,35,200,50);
        add(lblTitulo);

        // Crear el panel invisible
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(2, 1, 10, 10)); 
        panelBotones.setOpaque(false); 

        // Botón Jugar
        btnJugar = new JButton("Jugar");
        btnJugar.setFont(fuente);
        btnJugar.setForeground(Color.magenta);
        btnJugar.setContentAreaFilled(false); 
        btnJugar.setBorderPainted(false);
        btnJugar.setFocusable(false);
        btnJugar.addActionListener(e -> {
            jugadoresBD = gestorJugadores.obtenerJugadoresDesdeBD(); // Recargar lista
            if (jugadoresBD.size() >= 2) {
                this.dispose();
                mostrarVentanaMinijuegos();
            } else {
                JOptionPane.showMessageDialog(this, "Debe haber al menos dos jugadores registrados.");
            }
        });
        // Botón Gestionar Jugadores
        btnGestionarJugadores = new JButton("Gestionar Jugadores");
        btnGestionarJugadores.setFont(fuente);
        btnGestionarJugadores.setForeground(Color.magenta);
        btnGestionarJugadores.setContentAreaFilled(false); 
        btnGestionarJugadores.setBorderPainted(false); 
        btnGestionarJugadores.setFocusable(false);
        btnGestionarJugadores.addActionListener(e -> gestionarJugadores());

        // Agregar los botones al panel
        panelBotones.add(btnJugar);
        panelBotones.add(btnGestionarJugadores);

        // Centrar el panel en la ventana
        panelBotones.setBounds(400, 200, 250, 100); 
        this.add(panelBotones);
        this.add(fondo);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void mostrarVentanaMinijuegos() {
    jugadoresBD = gestorJugadores.obtenerJugadoresDesdeBD();
    if (jugadoresBD.size() < 2) {
        JOptionPane.showMessageDialog(this, "Debe haber al menos dos jugadores registrados.");
        return;
    }

    // Crear la ventana
    JFrame ventanaMinijuegos = new JFrame("Minijuegos");
    ventanaMinijuegos.setSize(1080, 720);
    ventanaMinijuegos.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    ventanaMinijuegos.setLocationRelativeTo(null);
    ventanaMinijuegos.setLayout(null);

    // Crear los nombres y botones
    String[] nombresJuegos = {"Carrera Piston", "Duelo de Vaqueros", "VelociRunner: Escapando del Comunismo", "Dragon Ball"};
    String[] rutasImagenes = {
        "src/main/java/Game/imagenes/portada_Cars.jpg",
        "src/main/java/Game/imagenes/portada_DUELO.jpg",
        "src/main/java/Game/imagenes/portada_VELOCIRUNNER.jpg",
        "src/main/java/Game/imagenes/portada_DB.jpg"
    };
    String[] nombresInternos = {"CarreraPiston", "DueloVaqueros", "VelociRunner", "DragonBall"};

    int[][] posiciones = {
        {100, 50, 400, 270}, {580, 50, 400, 270},
        {100, 350, 400, 270}, {580, 350, 400, 270}
    };

    for (int i = 0; i < nombresJuegos.length; i++) {
        JLabel labelJuego = new JLabel(nombresJuegos[i], SwingConstants.CENTER);
        labelJuego.setForeground(Color.WHITE);
        labelJuego.setBounds(posiciones[i][0], posiciones[i][1] - 30, 400, 30);

        JLabel btnJuego = new JLabel();
        btnJuego.setIcon(new ImageIcon(rutasImagenes[i]));
        btnJuego.setHorizontalAlignment(SwingConstants.CENTER);
        btnJuego.setVerticalAlignment(SwingConstants.CENTER);
        btnJuego.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        btnJuego.setBounds(posiciones[i][0], posiciones[i][1], posiciones[i][2], posiciones[i][3]);
        
        final String juego = nombresInternos[i];
        btnJuego.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iniciarJuego(juego);
                ventanaMinijuegos.dispose();
            }
        });

        ventanaMinijuegos.add(labelJuego);
        ventanaMinijuegos.add(btnJuego);
    }

    // Agregar fondo
    JLabel fondo = new JLabel(new ImageIcon("src/main/java/Game/imagenes/fondo_MINIJUEGOS.jpg"));
    fondo.setBounds(0, 0, 1080, 720);
    ventanaMinijuegos.add(fondo);

    ventanaMinijuegos.setVisible(true);
}


    // Ventana para gestionar jugadores
 // Ventana para gestionar jugadores
    private JFrame ventanaJugadores; // Nueva variable para controlar la ventana

    private void gestionarJugadores() {
       if (gestorJugadores == null) { // Solo la creamos si no existe
        
            gestorJugadores.setResizable(false);
            gestorJugadores.setLocationRelativeTo(null);
        }
        gestorJugadores.setVisible(true); // Mostrar solo cuando se llame al botón
    }



    // Registrar un nuevo jugador
    private void registrarNuevoJugador() {
        String nuevoJugador = JOptionPane.showInputDialog("Ingresa el nombre del nuevo jugador:");
        if (nuevoJugador != null && !nuevoJugador.trim().isEmpty()) {
            gestorJugadores.agregarJugador(nuevoJugador); // Llamar a GestorJugadores
        } else {
            JOptionPane.showMessageDialog(this, "Nombre inválido.");
        }
    }

    // Iniciar el juego seleccionado con los jugadores
    private void iniciarJuego(String juego) {

        // Selección de jugadores
        String jugador1 = (String) JOptionPane.showInputDialog(this, "Selecciona el primer jugador:", 
                "Seleccionar Jugador", JOptionPane.QUESTION_MESSAGE, null, jugadoresBD.toArray(), jugadoresBD.get(0));
        
        String jugador2 = (String) JOptionPane.showInputDialog(this, "Selecciona el segundo jugador:", 
                "Seleccionar Jugador", JOptionPane.QUESTION_MESSAGE, null, jugadoresBD.toArray(), jugadoresBD.get(1));
        
        if (jugador1 != null && jugador2 != null && !jugador1.equals(jugador2)) {
            switch (juego) {
                case "CarreraPiston":
                    new CarreraPiston();
                    break;
                case "DueloVaqueros":
                    new DueloVaqueros();
                    break;
                case "VelociRunner":
                    new VelociRunner();
                    break;
                case "DragonBall":
                    new DragonBall();
                    break;
                default:
                    break;
            }
        } else {
            JOptionPane.showMessageDialog(this, "Los jugadores seleccionados no pueden ser los mismos.");
        }
    }

    public static void main(String[] args) {
        new MenuPrincipal();
    }
}
