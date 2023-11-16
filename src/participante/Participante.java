package participante;
import dado.Dados;
import java.util.Scanner;


public class Participante {
    private String nombre;
    private Dados dados;
    private int[] puntajes;
    private Puntaje puntajeCalculator;


    public Participante(String nombre) {
        this.nombre = nombre;
        this.dados = new Dados();
        this.puntajes = new int[13];
        this.puntajeCalculator = new Puntaje();
    }

    public String getNombre() {

        return nombre;
    }

    public Dados getDados() {

        return dados;
    }

    public int[] getPuntajes() {

        return puntajes;
    }


    public void mostrarPuntajes() {
        System.out.println("Puntajes:");
        for (int i = 0; i < puntajes.length; i++) {
            System.out.println("• Suma de dado " + (i + 1) + ": " + puntajes[i]);
        }
    }

    public void anotarPuntaje(int categoria) {
        int puntaje = puntajeCalculator.calcularPuntaje(dados.getDados(), categoria);
        puntajes[categoria - 1] = puntaje;
    }

    public Puntaje getPuntajeCalculator() {

        return puntajeCalculator;
    }

    public void setPuntajeCalculator(Puntaje puntajeCalculator) {

        this.puntajeCalculator = puntajeCalculator;
    }
}