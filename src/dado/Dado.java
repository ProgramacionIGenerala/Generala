package dado;
public class Dado {
    private int valor;

    public Dado(int i) {
        this.valor = valor; // Inicializamos el dado con valor 1 por defecto
    }

    public void lanzar() {
        this.valor = (int) (Math.random() * 6) + 1;
    }

    public int getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return String.valueOf(valor);
    }
}