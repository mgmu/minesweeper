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

import minesweeper.model.Game;

/**
 * Represents the game settings dialog.
 */
public class GameSettings extends JDialog {
    private static final String DIALOG_TITLE = "Settings";

    private static final int HEIGHT_INCREMENT_SPINNER = 1;
    private static final String HEIGHT_SPINNER_LABEL = "Height of the grid";

    private static final int WIDTH_INCREMENT_SPINNER = 1;
    private static final String WIDTH_SPINNER_LABEL = "Width of the grid";    

    private static final int MINES_INCREMENT_SPINNER = 1;
    private static final String MINES_SPINNER_LABEL = "Mines on the grid";

    private static final String OK_BUTTON_LABEL = "OK";
    private static final String CANCEL_BUTTON_LABEL = "Cancel";

    /**
     * The initial number of mines to place on the grid.
     */
    private int mines = Game.DEFAULT_MINES;

    /**
     * The initial height of the grid.
     */
    private int height = Game.DEFAULT_HEIGHT;

    /**
     * The initial width of the grid.
     */
    private int width = Game.DEFAULT_WIDTH;

    /**
     * Indicates if the values of the settings have to be ignored.
     */
    private boolean canceled = false;

    /**
     * Class constructor that creates the game settings dialog.
     */
    public GameSettings() {
        super((Frame)null, DIALOG_TITLE, true);

        // Main panel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(400, 300));

        // Number of mines spinner
        JPanel minesPanel = new JPanel();
        SpinnerModel minesModel = new SpinnerNumberModel(Game.DEFAULT_MINES,
                Game.MIN_MINES, Game.MAX_MINES, MINES_INCREMENT_SPINNER);
        this.addLabeledSpinner(minesPanel, MINES_SPINNER_LABEL, minesModel);

        // Height of grid spinner
        JPanel heightPanel = new JPanel();
        SpinnerModel heightModel = new SpinnerNumberModel(Game.DEFAULT_HEIGHT,
                Game.MIN_HEIGHT, Game.MAX_HEIGHT, HEIGHT_INCREMENT_SPINNER);
        this.addLabeledSpinner(heightPanel, HEIGHT_SPINNER_LABEL, heightModel);

        // Width of grid spinner
        JPanel widthPanel = new JPanel();
        SpinnerModel widthModel = new SpinnerNumberModel(Game.DEFAULT_WIDTH,
                Game.MIN_WIDTH, Game.MAX_WIDTH, WIDTH_INCREMENT_SPINNER);
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
        this.rootPane.setDefaultButton(okButton);
        okButton.requestFocusInWindow();
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
