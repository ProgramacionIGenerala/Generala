import excepciones.GeneralaException;
import tablero.Tablero;
import participante.Participante;

public class Main {
    public static void main(String[] args) {
        try {
            Tablero tablero = new Tablero();
            tablero.jugar();
        }catch (GeneralaException e){
            System.out.println(e.getMessage());
        }
    }
}