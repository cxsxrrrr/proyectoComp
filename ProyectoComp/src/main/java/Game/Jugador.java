package Game;

public class Jugador {
    private int id;
    private String nombre;
    private int puntuacion;
//constructor jugador
    public Jugador(int id, String nombre, int puntuacion) {
        this.id = id;
        this.nombre = nombre;
        this.puntuacion = puntuacion;
    }
    //getters y setters

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    @Override
    public String toString() {
        return nombre + " (Puntos: " + puntuacion + ")";
    }
}

