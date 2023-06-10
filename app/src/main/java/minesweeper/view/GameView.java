package minesweeper.view;

import javax.swing.JPanel;
import javax.swing.JOptionPane;

import minesweeper.model.Game;
import minesweeper.model.RevealOnlyGrid;

import java.awt.Dimension;
import java.awt.BorderLayout;

public class GameView extends JPanel implements Observer {
    private final GridView gridView;

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
    public void displayEndGameDialog() {
        JOptionPane.showInternalMessageDialog(null, "The game has ended",
                "Game ended", JOptionPane.INFORMATION_MESSAGE);
    }

    public GridView gridView() {
        return this.gridView;
    }
}
