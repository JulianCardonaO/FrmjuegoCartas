import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Carta {

    private int indice;

    public Carta(Random r) {
        this.indice = r.nextInt(52) + 1;
    }

    public JLabel mostrar(int x, int y, JPanel pnl) {

        String nombreImagen = "imagenes/Carta" + indice + ".jpg";
        ImageIcon imgCarta = new ImageIcon(getClass().getResource(nombreImagen));

        JLabel lblCarta = new JLabel(imgCarta);
        lblCarta.setBounds(x, y, imgCarta.getIconWidth(), imgCarta.getIconHeight());

        pnl.add(lblCarta);

        lblCarta.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(null, getNombre() + " de " + getPinta());
            }
        });

        return lblCarta;
    }

    public Pinta getPinta() {
        if (indice <= 13)
            return Pinta.TREBOL;
        if (indice <= 26)
            return Pinta.PICA;
        if (indice <= 39)
            return Pinta.CORAZON;
        return Pinta.DIAMANTE;
    }

    public NombreCarta getNombre() {
        int residuo = indice % 13;
        if (residuo == 0)
            residuo = 13;
        return NombreCarta.values()[residuo - 1];

    }

    public int getValorNumerico() {

        int residuo = indice % 13;

        if (residuo == 0)
            residuo = 13;

        return residuo;
    }

    public int getPuntos() {

        int valor = getValorNumerico();

        if (valor >= 11 || valor == 1) {
            return 10;
        }

        return valor;
    }

}
