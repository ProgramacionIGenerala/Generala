package dado;
import java.util.ArrayList;
import java.util.List;

public class Dados {
    private List<Dado> dados;

    public Dados() {
        this.dados = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            dados.add(new Dado(1));
        }
    }

    public void lanzarDados() {
        for (Dado dado : dados) {
            dado.lanzar();
        }
    }

    public void mostrarDados() {
        System.out.println("Dados: " + dados);
    }

    public List<Dado> getDados() {
        return dados;
    }

    public void seleccionarDados(int... indices) {
        for (int indice : indices) {
            dados.get(indice).lanzar();
        }
    }
}