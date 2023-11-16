package tablero;

import dado.Dado;
import excepciones.GeneralaException;
import participante.Participante;
import participante.Puntaje;

import java.util.*;

public class Tablero {
    private List<Participante> participantes;
    private int rondas;

    public Tablero() {
        this.participantes = new ArrayList<>();
        this.rondas = 0;
    }

    public void agregarParticipante(String nombre) {
        participantes.add(new Participante(nombre));
    }

    public void ingresarParticipantes() throws GeneralaException {
        Scanner scanner = new Scanner(System.in);
        do {
            try {
                System.out.println("Ingrese el número de participantes (entre 2 y 5): ");
                int numParticipantes = scanner.nextInt();

                if (numParticipantes >= 2 && numParticipantes <= 5) {
                    for (int i = 0; i < numParticipantes; i++) {
                        System.out.println("Ingrese el nombre del participante " + (i + 1) + ": ");
                        String nombre = scanner.next();
                        agregarParticipante(nombre);
                    }
                    break;
                } else {
                    System.out.println("Error, debes ingresar un número entre 2 y 5.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Debes ingresar un número válido.");
                scanner.next();  // Limpiar el buffer del scanner
            }
        } while (true);
    }

    public void mostrarNombresParticipantes() {
        System.out.println("Nombres de participantes:");
        for (Participante participante : participantes) {
            System.out.println(participante.getNombre());
        }
    }

    public void jugar() {
        ingresarParticipantes();
        mostrarNombresParticipantes();

        Puntaje puntajeCalculator = new Puntaje();

        while (rondas < 10) {
            for (Participante participante : participantes) {
                if (rondas == 0 && esGeneralaEnPrimeraTirada(participante)) {
                    mostrarResultadosPartida(Arrays.asList(participante));
                    return;
                }

                System.out.println("\n--- Turno de " + participante.getNombre() + " ---");

                participante.getDados().lanzarDados();
                int tiradasRestantes = 3;

                do {
                    System.out.println("\n--- Tirada " + (4 - tiradasRestantes) + " ---");
                    participante.getDados().mostrarDados();

                    System.out.println("Selecciona los dados que quieres volver a lanzar (1-5) o coloca 0 para anotar puntaje: ");
                    Scanner scanner = new Scanner(System.in);
                    String input = scanner.nextLine();

                    if (input.equals("0")) {
                        if (tiradasRestantes < 3) {
                            registrarPuntaje(participante, puntajeCalculator);
                            break;
                        } else {
                            System.out.println("Debes anotar el puntaje en la tercera tirada.");
                        }
                    }

                    String[] indicesStr = input.split("[,\\s]+");
                    int[] indices = Arrays.stream(indicesStr)
                            .mapToInt(Integer::parseInt)
                            .map(idx -> idx - 1)
                            .toArray();

                    participante.getDados().seleccionarDados(indices);
                    tiradasRestantes--;
                } while (tiradasRestantes > 0);
            }

            mostrarResultadosPartida(participantes);
            rondas++;
        }
        System.out.println("Juego terminado.");
    }

    private void registrarPuntaje(Participante participante, Puntaje puntajeCalculator) {
        Puntaje.mostrarOpcionesPuntaje();
        int opcionPuntaje = new Scanner(System.in).nextInt();

        if (opcionPuntaje >= 1 && opcionPuntaje <= 10) {
            int puntaje = puntajeCalculator.calcularPuntaje(participante.getDados().getDados(), opcionPuntaje);
            participante.getPuntajes()[opcionPuntaje - 1] = puntaje;

            System.out.println("Has elegido " + Puntaje.obtenerNombreOpcion(opcionPuntaje) + ". El total es: " + puntaje);
            participante.mostrarPuntajes();
        } else {
            System.out.println("Opción de puntaje no válida.");
        }
    }

    private boolean esGeneralaEnPrimeraTirada(Participante participante) {
        participante.getDados().lanzarDados();
        List<Dado> dados = participante.getDados().getDados();
        return dados.stream().allMatch(d -> d.getValor() == dados.get(0).getValor());
    }

    private void mostrarResultadosPartida(List<Participante> participantes) {
        Participante ganador = determinarGanador(participantes);
        System.out.println("FELICIDADES " + ganador.getNombre() + " has ganado con " + ganador.getPuntajes()[0] + " puntos");

        for (Participante jugador : participantes) {
            if (jugador != ganador) {
                System.out.println(jugador.getNombre() + " - Total General: " + jugador.getPuntajes()[0]);
            }
        }
    }

    private Participante determinarGanador(List<Participante> participantes) {
        return participantes.stream().max(Comparator.comparingInt(jugador -> jugador.getPuntajes()[0])).orElse(null);
    }
}