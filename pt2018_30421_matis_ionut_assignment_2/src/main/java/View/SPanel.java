package View;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;

/**
 * Class that models the server concept. It is crucial to build such a panel, because it displays
 * very well the server and its tasks.
 *
 * @author Ionut Matis, 30421
 */
public class SPanel extends JPanel {
    private JTextArea textArea;     //has a text are to display the tasks
    private JScrollPane jp;         //scroller in case the tasks exit the bound
    private JLabel servedNow;       //a label displaying the client currently served

    /**
     * Constructor for a class that creates the layout and its dimensions. It also adds scrollers
     * for text areas
     *
     * @param nr  the number of the server so that I can assign it a name
     * @param len the length of the panel
     */
    public SPanel(int nr, int len) {
        TitledBorder border = new TitledBorder("Server " + nr);
        border.setTitleJustification(TitledBorder.CENTER);
        border.setTitlePosition(TitledBorder.TOP);
        border.setTitleFont(new Font("Times New Roman", Font.BOLD, 30));

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        servedNow = new JLabel("Serving:         ");
        servedNow.setFont(new Font("Times New Roman", Font.PLAIN, 21));
        textArea = new JTextArea();
        textArea.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        textArea.setPreferredSize(new Dimension(len + 160, 300));
        textArea.setEditable(false);

        jp = new JScrollPane(textArea);
        jp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        jp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JPanel secondP = new JPanel();
        secondP.setSize(new Dimension(len + 150, 300));
        secondP.add(jp);

        this.setSize(new Dimension(len, 350));
        this.setBorder(border);
        this.add(servedNow);
        this.add(secondP);
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public JLabel getServedNow() {
        return servedNow;
    }
}
