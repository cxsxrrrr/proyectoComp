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
        lblTitulo = new JLabel("PIXEL-WOLRD", SwingConstants.CENTER);
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
       JFrame ventanaMinijuegos = new JFrame("Escoja un minijuego");
       ventanaMinijuegos.setSize(1080, 720);
       ventanaMinijuegos.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
       ventanaMinijuegos.setLocationRelativeTo(null);
       ventanaMinijuegos.setLayout(null); // Usamos null layout para posicionar manualmente los componentes

       // Crear los JLabel para los nombres de los juegos
       JLabel labelCarreraPulsaciones = new JLabel("Carrera de Pulsaciones", SwingConstants.CENTER);
       JLabel labelDueloReaccion = new JLabel("Duelo de Reacción", SwingConstants.CENTER);
       JLabel labelLanzamientoPoder = new JLabel("Lanzamiento de Poder", SwingConstants.CENTER);
       JLabel labelSaltoPrecision = new JLabel("Salto de Precisión", SwingConstants.CENTER);

       // Estilo de los JLabel de texto (nombres de los juegos)
       labelCarreraPulsaciones.setForeground(Color.WHITE);
       labelDueloReaccion.setForeground(Color.WHITE);
       labelLanzamientoPoder.setForeground(Color.WHITE);
       labelSaltoPrecision.setForeground(Color.WHITE);

       // Crear los JLabel como botones con imágenes de fondo
       JLabel btnCarreraPulsaciones = new JLabel();
       btnCarreraPulsaciones.setIcon(new ImageIcon("src/main/java/Game/imagenes/btn_carrera_pulsaciones.png"));
       btnCarreraPulsaciones.setHorizontalAlignment(SwingConstants.CENTER);
       btnCarreraPulsaciones.setVerticalAlignment(SwingConstants.CENTER);
       btnCarreraPulsaciones.setBorder(BorderFactory.createLineBorder(Color.BLACK));

       JLabel btnDueloReaccion = new JLabel();
       btnDueloReaccion.setIcon(new ImageIcon("src/main/java/Game/imagenes/btn_duelo_reaccion.png"));
       btnDueloReaccion.setHorizontalAlignment(SwingConstants.CENTER);
       btnDueloReaccion.setVerticalAlignment(SwingConstants.CENTER);
       btnDueloReaccion.setBorder(BorderFactory.createLineBorder(Color.BLACK));

       JLabel btnLanzamientoPoder = new JLabel();
       btnLanzamientoPoder.setIcon(new ImageIcon("src/main/java/Game/imagenes/btn_lanzamiento_poder.png"));
       btnLanzamientoPoder.setHorizontalAlignment(SwingConstants.CENTER);
       btnLanzamientoPoder.setVerticalAlignment(SwingConstants.CENTER);
       btnLanzamientoPoder.setBorder(BorderFactory.createLineBorder(Color.BLACK));

       JLabel btnSaltoPrecision = new JLabel();
       btnSaltoPrecision.setIcon(new ImageIcon("src/main/java/Game/imagenes/DB_portada.jpeg"));
       btnSaltoPrecision.setHorizontalAlignment(SwingConstants.CENTER);
       btnSaltoPrecision.setVerticalAlignment(SwingConstants.CENTER);
       btnSaltoPrecision.setBorder(BorderFactory.createLineBorder(Color.BLACK));

       // Establecer el tamaño de los botones
       Dimension size = new Dimension(400, 300); // Tamaño adecuado para las imágenes
       btnCarreraPulsaciones.setPreferredSize(size);
       btnDueloReaccion.setPreferredSize(size);
       btnLanzamientoPoder.setPreferredSize(size);
       btnSaltoPrecision.setPreferredSize(size);

       // Crear los paneles de cada minijuego con nombre y botón
       labelCarreraPulsaciones.setBounds(100, 70, 200, 30); // Ubicar el nombre del juego en la posición (100, 100)
       btnCarreraPulsaciones.setBounds(100, 110, 400, 270); // Ubicar el botón en la posición (100, 140)

       labelDueloReaccion.setBounds(530, 70, 200, 30); // Ubicar el nombre del juego en la posición (400, 100)
       btnDueloReaccion.setBounds(530, 110, 400, 270); // Ubicar el botón en la posición (400, 140)

       labelLanzamientoPoder.setBounds(100, 320, 200, 30); // Ubicar el nombre del juego en la posición (100, 320)
       btnLanzamientoPoder.setBounds(100, 360, 400, 270); // Ubicar el botón en la posición (100, 360)

       labelSaltoPrecision.setBounds(530, 320, 200, 30); // Ubicar el nombre del juego en la posición (400, 320)
       btnSaltoPrecision.setBounds(530, 360, 400, 270); // Ubicar el botón en la posición (400, 360)

       // Agregar los botones y los nombres a la ventana
       ventanaMinijuegos.add(labelCarreraPulsaciones);
       ventanaMinijuegos.add(btnCarreraPulsaciones);
       ventanaMinijuegos.add(labelDueloReaccion);
       ventanaMinijuegos.add(btnDueloReaccion);
       ventanaMinijuegos.add(labelLanzamientoPoder);
       ventanaMinijuegos.add(btnLanzamientoPoder);
       ventanaMinijuegos.add(labelSaltoPrecision);
       ventanaMinijuegos.add(btnSaltoPrecision);

       // Agregar fondo después de los elementos (es lo último para que se vea bien)
       fondo = new JLabel(new ImageIcon("src/main/java/Game/imagenes/fondo_MINIJUEGOS.jpg"));
       fondo.setBounds(0, 0, 1080, 720); // Tamaño del fondo
       ventanaMinijuegos.add(fondo);

       // Agregar acción a los botones (labels)
       btnCarreraPulsaciones.addMouseListener(new java.awt.event.MouseAdapter() {
           public void mouseClicked(java.awt.event.MouseEvent evt) {
               iniciarJuego("CarreraPulsaciones");
               ventanaMinijuegos.dispose();
           }
       });

       btnDueloReaccion.addMouseListener(new java.awt.event.MouseAdapter() {
           public void mouseClicked(java.awt.event.MouseEvent evt) {
               iniciarJuego("DueloReaccion");
               ventanaMinijuegos.dispose();
           }
       });

       btnLanzamientoPoder.addMouseListener(new java.awt.event.MouseAdapter() {
           public void mouseClicked(java.awt.event.MouseEvent evt) {
               iniciarJuego("LanzamientoPoder");
               ventanaMinijuegos.dispose();
           }
       });

       btnSaltoPrecision.addMouseListener(new java.awt.event.MouseAdapter() {
           public void mouseClicked(java.awt.event.MouseEvent evt) {
               iniciarJuego("SaltoPrecision");
               ventanaMinijuegos.dispose();
           }
       });

       // Hacer visible la ventana
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
                case "CarreraPulsaciones":
                    new CarreraPulsaciones();
                    break;
                case "DueloReaccion":
                    new DueloReaccion();
                    break;
                case "LanzamientoPoder":
                    new LanzamientoPoder();
                    break;
                case "SaltoPrecision":
                    new SaltoPrecision();
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
