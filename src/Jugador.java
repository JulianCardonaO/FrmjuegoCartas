import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Jugador {

    private final int TOTAL_CARTAS = 10;
    private final int MARGEN_SUPERIOR = 10;
    private final int MARGEN_IZQUIERDA = 10;
    private final int DISTANCIA_CARTAS = 40;

    private Carta[] cartas = new Carta[TOTAL_CARTAS];
    private JLabel[] etiquetas = new JLabel[TOTAL_CARTAS];
    private Random r = new Random();

    public void repartir() {

        for (int i = 0; i < cartas.length; i++) {
            cartas[i] = new Carta(r);
        }
    }

    public void mostrar(JPanel pnl) {

        pnl.removeAll();
        pnl.setLayout(null);

        int posicion = MARGEN_IZQUIERDA + DISTANCIA_CARTAS * (TOTAL_CARTAS - 1);

        for (int i = 0; i < cartas.length; i++) {

            etiquetas[i] = cartas[i].mostrar(posicion, MARGEN_SUPERIOR, pnl);

            posicion -= DISTANCIA_CARTAS;
        }

        pnl.repaint();
    }

    public int getPuntos() {

        Set<Carta> usadas = new HashSet<>();

        detectarGrupos(usadas);
        detectarEscaleras(usadas);

        int puntos = 0;

        for (Carta carta : cartas) {

            if (!usadas.contains(carta)) {
                puntos += carta.getPuntos();
            }
        }

        return puntos;
    }

    public String analizarJuego() {

        Set<Carta> usadas = new HashSet<>();

        String resultado = "";

        resultado += detectarGrupos(usadas);
        resultado += detectarEscaleras(usadas);

        int puntos = 0;
        String sobrantes = "";

        for (Carta carta : cartas) {

            if (!usadas.contains(carta)) {

                puntos += carta.getPuntos();
                sobrantes += carta.getNombre() + " ";
            }
        }

        resultado += "\nCartas sobrantes: " + sobrantes;
        resultado += "\nPuntos: " + puntos;

        return resultado;
    }

    private String detectarGrupos(Set<Carta> usadas) {

        int[] contadores = new int[NombreCarta.values().length];

        for (Carta carta : cartas) {
            contadores[carta.getNombre().ordinal()]++;
        }

        String grupos = "";

        for (int i = 0; i < contadores.length; i++) {

            if (contadores[i] >= 2) {

                grupos += Grupo.values()[contadores[i]] + " de " + NombreCarta.values()[i] + "\n";

                for (Carta carta : cartas) {
                    if (carta.getNombre() == NombreCarta.values()[i]) {
                        usadas.add(carta);
                        iluminarCarta(carta);
                    }
                }
            }
        }

        return grupos.isEmpty() ? "" : "Grupos encontrados:\n" + grupos;
    }

    private String detectarEscaleras(Set<Carta> usadas) {

        Carta[] copia = Arrays.copyOf(cartas, cartas.length);

        Arrays.sort(copia, Comparator.comparingInt(Carta::getValorNumerico));

        String resultado = "";
        int contador = 1;

        for (int i = 1; i < copia.length; i++) {

            if (copia[i].getValorNumerico() == copia[i - 1].getValorNumerico() + 1) {
                contador++;
            } else {

                if (contador >= 3) {
                    resultado += construirEscalera(copia, i - contador, i - 1, usadas);
                }

                contador = 1;
            }
        }

        if (contador >= 3) {
            resultado += construirEscalera(copia, copia.length - contador, copia.length - 1, usadas);
        }

        return resultado.isEmpty() ? "" : "\nEscaleras encontradas:\n" + resultado;
    }

    private String construirEscalera(Carta[] cartas, int inicio, int fin, Set<Carta> usadas) {

        String escalera = "Escalera: ";

        for (int i = inicio; i <= fin; i++) {

            escalera += cartas[i].getNombre();

            usadas.add(cartas[i]);
            iluminarCarta(cartas[i]);

            if (i < fin) {
                escalera += " - ";
            }
        }

        escalera += "\n";

        return escalera;
    }

    private void iluminarCarta(Carta carta) {

        for (int i = 0; i < cartas.length; i++) {

            if (cartas[i] == carta) {

                etiquetas[i].setBorder(BorderFactory.createLineBorder(new Color(128, 0, 128), 4));
            }
        }
    }
}