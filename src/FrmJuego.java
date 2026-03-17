import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class FrmJuego extends JFrame {

    JPanel pnlJugador1, pnlJugador2;
    Jugador jugador1 = new Jugador();
    Jugador jugador2 = new Jugador();
    JTabbedPane tpJugadores;

    public FrmJuego() {
        setSize(500, 300);
        setTitle("Juegos al Apuntado!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JButton btnRepartir = new JButton("Repartir");
        btnRepartir.setBounds(10, 10, 100, 25);
        add(btnRepartir);

        JButton btnVerificar = new JButton("Verificar");
        btnVerificar.setBounds(120, 10, 100, 25);
        add(btnVerificar);

        tpJugadores = new JTabbedPane();
        tpJugadores.setBounds(10, 50, 460, 200);
        add(tpJugadores);

        pnlJugador1 = new JPanel();
        pnlJugador1.setBackground(new Color(0, 255, 0));

        pnlJugador2 = new JPanel();
        pnlJugador2.setBackground(new Color(0, 255, 255));

        tpJugadores.addTab("Martín Estrada Contreras", pnlJugador1);
        tpJugadores.addTab("Raúl Vidal", pnlJugador2);

        // eventos
        btnRepartir.addActionListener(e -> {
            repartir();
        });

        btnVerificar.addActionListener(e -> {
            verificar();
        });

    }

    private void repartir() {
        jugador1.repartir();
        jugador2.repartir();

        jugador1.mostrar(pnlJugador1);
        jugador2.mostrar(pnlJugador2);
    }

    private void verificar() {

        int puntos1 = jugador1.getPuntos();
        int puntos2 = jugador2.getPuntos();

        String resultado1 = jugador1.analizarJuego();
        String resultado2 = jugador2.analizarJuego();

        String ganador;

        if (puntos1 < puntos2) {
            ganador = "Jugador 1 gana";
        } else if (puntos2 < puntos1) {
            ganador = "Jugador 2 gana";
        } else {
            ganador = "Empate";
        }

        JOptionPane.showMessageDialog(null,
                "Jugador 1:\n" + resultado1 +
                "\n\nJugador 2:\n" + resultado2 +
                "\n\nResultado final: " + ganador);
    }

}
