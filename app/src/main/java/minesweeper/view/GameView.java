package minesweeper.view;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import minesweeper.model.Game;
import minesweeper.model.RevealOnlyGrid;

import java.awt.Dimension;
import java.awt.BorderLayout;

/**
 * Defines the view of a Game.
 */
public class GameView extends JPanel implements Observer {
    
    /**
     * The grid view of this GameView.
     */
    private final GridView gridView;

    /**
     * Displays the number of unflagged mines.
     */
    private final JLabel unflaggedMinesLabel;

    /**
     * The model of this GameView.
     */
    private Game model;

    /**
     * Class constructor that displays the grid at its center.
     */
    public GameView() {
        this.gridView = new GridView();
        this.model = null;
        this.unflaggedMinesLabel = new JLabel();
        this.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.add(unflaggedMinesLabel);

        this.add(panel, BorderLayout.PAGE_START);
        this.add(gridView, BorderLayout.CENTER);
    }

    @Override
    public void update(Game model) {
        this.model = model;
        this.gridView.setModel(new RevealOnlyGrid(model.grid()));
        int unflagged = model.unflaggedMines();
        StringBuilder newLabel = new StringBuilder();
        newLabel.append(unflagged);
        this.unflaggedMinesLabel.setText(newLabel.toString());
        this.gridView.repaint();
        if (model.hasMineRevealed())
            this.displayEndGameDialog();
    }

    // Displays the end game dialog
    private void displayEndGameDialog() {
        StringBuilder builder = new StringBuilder();
        builder.append("The game has ended\n");
        builder.append("Time: ");
        if (this.model != null)
            builder.append(this.model.gameTime());
        else
            builder.append("0.0s");
        JOptionPane.showInternalMessageDialog(null, builder.toString(),
                "Game ended", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * {@return the grid view of this GameView}
     */
    public GridView gridView() {
        return this.gridView;
    }
}
