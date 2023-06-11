package minesweeper.view;

import javax.swing.JPanel;
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
     * Class constructor that displays the grid at its center.
     */
    public GameView() {
        this.gridView = new GridView();
        this.setLayout(new BorderLayout());
        this.add(gridView, BorderLayout.CENTER);
        this.setPreferredSize(new Dimension(1000, 800));
    }

    @Override
    public void update(Game model) {
        this.gridView.setModel(new RevealOnlyGrid(model.grid()));
        this.gridView.repaint();
        if (model.hasMineRevealed())
            this.displayEndGameDialog();
    }

    // Displays the end game dialog
    private void displayEndGameDialog() {
        JOptionPane.showInternalMessageDialog(null, "The game has ended",
                "Game ended", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * {@return the grid view of this GameView}
     */
    public GridView gridView() {
        return this.gridView;
    }
}
