package minesweeper.view;

import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import java.awt.Frame;
import java.awt.Dimension;

public class GameSettings extends JDialog {
    private static final String DIALOG_TITLE = "Settings";

    public static final int DEFAULT_HEIGHT = 16;
    private static final int MIN_HEIGHT = 1;
    private static final int MAX_HEIGHT = 256;
    private static final int HEIGHT_INCREMENT_SPINNER = 1;
    private static final String HEIGHT_SPINNER_LABEL = "Height of the grid";

    public static final int DEFAULT_WIDTH = 30;
    private static final int MIN_WIDTH = 1;
    private static final int MAX_WIDTH = 256;
    private static final int WIDTH_INCREMENT_SPINNER = 1;
    private static final String WIDTH_SPINNER_LABEL = "Width of the grid";    

    public static final int DEFAULT_MINES = 99;
    private static final int MIN_MINES = 0;
    private static final int MAX_MINES = MAX_HEIGHT * MAX_WIDTH;
    private static final int MINES_INCREMENT_SPINNER = 1;
    private static final String MINES_SPINNER_LABEL = "Mines on the grid";

    private static final String OK_BUTTON_LABEL = "OK";
    private static final String CANCEL_BUTTON_LABEL = "Cancel";

    private int mines = DEFAULT_MINES;
    private int height = DEFAULT_HEIGHT;
    private int width = DEFAULT_WIDTH;
    private boolean canceled = false;

    public GameSettings() {
        super((Frame)null, DIALOG_TITLE, true);

        // Main panel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(400, 300));

        // Number of mines spinner
        JPanel minesPanel = new JPanel();
        SpinnerModel minesModel = new SpinnerNumberModel(DEFAULT_MINES,
                MIN_MINES, MAX_MINES, MINES_INCREMENT_SPINNER);
        this.addLabeledSpinner(minesPanel, MINES_SPINNER_LABEL, minesModel);

        // Height of grid spinner
        JPanel heightPanel = new JPanel();
        SpinnerModel heightModel = new SpinnerNumberModel(DEFAULT_HEIGHT,
                MIN_HEIGHT, MAX_HEIGHT, HEIGHT_INCREMENT_SPINNER);
        this.addLabeledSpinner(heightPanel, HEIGHT_SPINNER_LABEL, heightModel);

        // Width of grid spinner
        JPanel widthPanel = new JPanel();
        SpinnerModel widthModel = new SpinnerNumberModel(DEFAULT_WIDTH,
                MIN_WIDTH, MAX_WIDTH, WIDTH_INCREMENT_SPINNER);
        this.addLabeledSpinner(widthPanel, WIDTH_SPINNER_LABEL, widthModel);

        // Buttons panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
        JButton okButton = new JButton(OK_BUTTON_LABEL);
        okButton.addActionListener(e -> {
                    this.mines = (int) minesModel.getValue();
                    this.height = (int) heightModel.getValue();
                    this.width = (int) widthModel.getValue();
                    this.quit();
                });
        JButton cancelButton = new JButton(CANCEL_BUTTON_LABEL);
        cancelButton.addActionListener(e -> {
                    this.canceled = true;
                    this.quit();
                });
        buttonsPanel.add(okButton);
        buttonsPanel.add(cancelButton);

        // Construction
        panel.add(minesPanel);
        panel.add(heightPanel);
        panel.add(widthPanel);
        panel.add(buttonsPanel);
        this.add(panel);
        this.pack();
    }

    // Creates a labeld spinner with the provided label and model and adds it to
    // to the given panel
    private void addLabeledSpinner(JPanel panel, String label,
            SpinnerModel model) {
        JLabel l = new JLabel(label);
        panel.add(l);
 
        JSpinner spinner = new JSpinner(model);
        l.setLabelFor(spinner);
        panel.add(spinner);
    }

    // Closes the settings
    private void quit() {
        this.setVisible(false);
        this.dispose();
    }

    /**
     * {@return the number of mines selected by the user}
     */
    public int mines() {
        return this.mines;
    }

    /**
     * {@return the height selected by the user}
     */
    public int height() {
        return this.height;
    }

    /**
     * {@return the width selected by the user}
     */
    public int width() {
        return this.width;
    }

    /**
     * {@return true if this dialog was canceled}
     */
    public boolean canceled() {
        return this.canceled;
    }
}
