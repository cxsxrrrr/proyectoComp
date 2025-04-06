package Game;

import javax.swing.*;
import java.util.List;

import java.awt.*;


public class MenuPrincipal extends JFrame {
    private GestorJugadores gestorJugadores;
    private List<String> jugadoresBD;
    private JButton btnJugar, btnGestionarJugadores;
    private JLabel titulo, fondo;
    private Font fuente = new Font("Arial", Font.BOLD, 20);
   
    public MenuPrincipal() {
    	gestorJugadores = new GestorJugadores(); 
    	jugadoresBD = gestorJugadores.obtenerJugadoresDesdeBD();

        setTitle("Menú Principal");
        setSize(1080,720); 
        setLayout(null);
        setFocusable(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // fondo
        fondo = new JLabel(new ImageIcon("src/main/java/Game/imagenes/fondo_MENU.jpg"));
        fondo.setBounds(0, 0, 1080, 720);
        
        // titulo
        titulo = new JLabel("ARCADEA", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setForeground(Color.YELLOW);
        titulo.setBounds(430,35,200,50);
        add(titulo);

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(2, 1, 10, 10)); 
        panelBotones.setOpaque(false); 

        // jugar
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
        // gestionar jugadores
        btnGestionarJugadores = new JButton("Gestionar Jugadores");
        btnGestionarJugadores.setFont(fuente);
        btnGestionarJugadores.setForeground(Color.magenta);
        btnGestionarJugadores.setContentAreaFilled(false); 
        btnGestionarJugadores.setBorderPainted(false); 
        btnGestionarJugadores.setFocusable(false);
        btnGestionarJugadores.addActionListener(e -> gestionarJugadores());

        // agg al panel
        panelBotones.add(btnJugar);
        panelBotones.add(btnGestionarJugadores);

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

    // ventana minijuegos
    JFrame ventanaMinijuegos = new JFrame("Minijuegos");
    ventanaMinijuegos.setSize(1080, 720);
    ventanaMinijuegos.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    ventanaMinijuegos.setLocationRelativeTo(null);
    ventanaMinijuegos.setLayout(null);

    // nombres de los juegos
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

    // fondo
    JLabel fondo = new JLabel(new ImageIcon("src/main/java/Game/imagenes/fondo_MINIJUEGOS.jpg"));
    fondo.setBounds(0, 0, 1080, 720);
    ventanaMinijuegos.add(fondo);

    ventanaMinijuegos.setVisible(true);
}



 // gestionar jugadores


    private void gestionarJugadores() {
       if (gestorJugadores == null) {
        
            gestorJugadores.setResizable(false);
            gestorJugadores.setLocationRelativeTo(null);
        }
        gestorJugadores.setVisible(true);
    }



    // registrar un jugador
    private void registrarNuevoJugador() {
        String nuevoJugador = JOptionPane.showInputDialog("Ingresa el nombre del nuevo jugador:");
        if (nuevoJugador != null && !nuevoJugador.trim().isEmpty()) {
            gestorJugadores.agregarJugador(nuevoJugador); // instanciamos de GestorJugadores
        } else {
            JOptionPane.showMessageDialog(this, "Nombre inválido.");
        }
    }

    // inicio juego
    private void iniciarJuego(String juego) {
        String jugador1;
        String jugador2 = null;

        if (juego.equals("VelociRunner")) {
            // aqui seleccionamos un solo jugador
            jugador1 = (String) JOptionPane.showInputDialog(this, "Selecciona el jugador:", 
                    "Seleccionar Jugador", JOptionPane.QUESTION_MESSAGE, null, jugadoresBD.toArray(), jugadoresBD.get(0));
            
            if (jugador1 != null) {
                GestorJugadores.setJugador1(jugador1);
                new VelociRunner();
            }
        } else {
            // para juegos de dos jugadores
            jugador1 = (String) JOptionPane.showInputDialog(this, "Selecciona el primer jugador:", 
                    "Seleccionar Jugador", JOptionPane.QUESTION_MESSAGE, null, jugadoresBD.toArray(), jugadoresBD.get(0));
            
            jugador2 = (String) JOptionPane.showInputDialog(this, "Selecciona el segundo jugador:", 
                    "Seleccionar Jugador", JOptionPane.QUESTION_MESSAGE, null, jugadoresBD.toArray(), jugadoresBD.get(1));
            
            if (jugador1 != null && jugador2 != null && !jugador1.equals(jugador2)) {
                GestorJugadores.setJugador1(jugador1);
                GestorJugadores.setJugador2(jugador2);

                switch (juego) {
                    case "CarreraPiston":
                        new CarreraPiston();
                        break;
                    case "DueloVaqueros":
                        new DueloVaqueros();
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
    }


    public static void main(String[] args) {
        new MenuPrincipal();
    }
}
