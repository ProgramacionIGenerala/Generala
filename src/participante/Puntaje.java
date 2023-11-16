package participante;

import dado.Dado;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Puntaje {
    private int[] puntajes;
    private int[] puntajesAnotados;
    private int totalGeneral;

    public Puntaje() {
        this.puntajes = new int[13];
        this.puntajesAnotados = new int[13];
        this.totalGeneral = 0;
    }

    public int calcularPuntaje(List<Dado> dados, int categoria) {
        int puntaje = 0;

        switch (categoria) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                puntaje = dados.stream()
                        .filter(dado -> dado.getValor() == categoria)
                        .mapToInt(Dado::getValor)
                        .sum();
                break;

            case 7: // Generala
                puntaje = esGenerala(dados) ? 60 : 0;
                break;

            case 8: // Escalera
                puntaje = esEscalera(dados) ? 40 : 0;
                break;

            case 9: // Póker
                puntaje = esPoker(dados) ? 50 : 0;
                break;

            case 10: // Full
                puntaje = esFull(dados) ? 30 : 0;
                break;

            default:
                System.out.println("Categoría no válida.");
        }
        return puntaje;
    }

    private static boolean esGenerala(List<Dado> dados) {
        int primerDado = dados.get(0).getValor();
        return dados.stream().allMatch(dado -> dado.getValor() == primerDado);
    }

    private static boolean esEscalera(List<Dado> dados) {
        List<Integer> valores = dados.stream().map(Dado::getValor).distinct().sorted().collect(Collectors.toList());
        return valores.size() == 5 && (valores.get(4) - valores.get(0) == 4 || valores.equals(Arrays.asList(1, 2, 3, 4, 6)));
    }

    private static boolean esPoker(List<Dado> dados) {
        return dados.stream().anyMatch(dado ->
                dados.stream().filter(d -> d.getValor() == dado.getValor()).count() >= 4);
    }

    public static boolean esFull(List<Dado> dados) {
        List<Integer> valores = dados.stream().map(Dado::getValor).collect(Collectors.toList());
        return (valores.get(0) == valores.get(1) && valores.get(1) == valores.get(2) && valores.get(3) == valores.get(4)) ||
                (valores.get(0) == valores.get(1) && valores.get(2) == valores.get(3) && valores.get(3) == valores.get(4));
    }

    public int calcularEscalera(List<Dado> dados) {
        Collections.sort(dados, Comparator.comparingInt(Dado::getValor));
        boolean esEscalera = false;

        if (esCorrelativa(dados, 1, 5) || esCorrelativa(dados, 2, 6) || esCorrelativa(dados, 3, 6) && dados.contains(new Dado(1))) {
            esEscalera = true;
        }

        return esEscalera ? 40 : 0;
    }

    private boolean esCorrelativa(List<Dado> dados, int inicio, int fin) {
        for (int i = inicio; i <= fin; i++) {
            if (!dados.contains(new Dado(i))) {
                return false;
            }
        }
        return true;
    }

    public void anotarPuntaje(int categoria, int puntaje) {
        if (puntajesAnotados[categoria - 1] == 0) {
            puntajes[categoria - 1] = puntaje;
            puntajesAnotados[categoria - 1] = puntaje;
            totalGeneral += puntaje;
        } else {
            System.out.println("Ya has anotado un puntaje para esta categoría. No puedes sobrescribirlo.");
        }
    }

    public void mostrarPuntajes() {
        System.out.println("Puntajes:");
        for (int i = 0; i < 6; i++) {
            System.out.println("• Suma de dado " + (i + 1) + ": " + puntajes[i]);
        }

        System.out.println("• Generala: " + puntajes[6]);
        System.out.println("• Escalera: " + puntajes[7]);
        System.out.println("• Poker: " + puntajes[8]);
        System.out.println("• Full: " + puntajes[9]);

        int totalJugador = Arrays.stream(puntajes).sum();
        System.out.println("Total del jugador: " + totalJugador);
    }

    public int calcularSubtotal(int numero) {
        int subtotal = 0;
        for (int i = 0; i < 5; i++) {
            if (puntajesAnotados[i] == 0 && numero == i + 1) {
                subtotal += puntajes[i];
            } else if (puntajesAnotados[i] != 0) {
                subtotal += puntajesAnotados[i];
            }
        }
        return subtotal;
    }

    public static void mostrarOpcionesPuntaje() {
        System.out.println("Selecciona el puntaje:");
        System.out.println("1. Suma de 1");
        System.out.println("2. Suma de 2");
        System.out.println("3. Suma de 3");
        System.out.println("4. Suma de 4");
        System.out.println("5. Suma de 5");
        System.out.println("6. Suma de 6");
        System.out.println("7. Generala (5 dados iguales)");
        System.out.println("8. Escalera (5 dados consecutivos)");
        System.out.println("9. Poker (4 dados iguales)");
        System.out.println("10. Full (3 dados iguales y 2 dados iguales)");
    }

    public static String obtenerNombreOpcion(int opcion) {
        switch (opcion) {
            case 7:
                return "Generala";
            case 8:
                return "Escalera";
            case 9:
                return "Póker";
            case 10:
                return "Full";
            default:
                return "Opción no válida";
        }
    }

}
