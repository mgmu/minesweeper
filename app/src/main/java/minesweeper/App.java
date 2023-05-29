package minesweeper;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;

import minesweeper.view.GridView;
import minesweeper.view.GameSettings;
import minesweeper.model.Grid;
import minesweeper.controller.GameController;

/**
 * The application.
 */
public class App {

    // The frame of the GUI
    private final JFrame frame;

    // The view of the model
    private GridView gridView;

    // The model
    private Grid model;

    // The model controller
    private GameController gameController;

    /**
     * Class constructor that initializes the GUI.
     */
    public App() {

        // The frame of the application
        frame = new JFrame("Minesweeper");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // The menu bar of the application
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");
        JMenuItem newGameMenuItem = new JMenuItem("New", KeyEvent.VK_N);
        newGameMenuItem.setAccelerator(
          KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        newGameMenuItem.addActionListener(e -> {
                    this.askGameParameters();
                });
        gameMenu.add(newGameMenuItem);
        gameMenu.setMnemonic(KeyEvent.VK_G);
        menuBar.add(gameMenu);

        // The view
        gridView = new GridView();
        frame.getContentPane().add(gridView);

        // The model
        model = new Grid(GameSettings.DEFAULT_WIDTH,
                GameSettings.DEFAULT_HEIGHT, GameSettings.DEFAULT_MINES);
        model.add(gridView);

        // The controller
        gameController = new GameController(model, this);
        gridView.addMouseListener(gameController);
        gridView.addMouseMotionListener(gameController);

        // Display
        frame.setJMenuBar(menuBar);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
    }

    // Displays a dialog and waits for user to provide game settings
    private void askGameParameters() {
        GameSettings settings = new GameSettings();
        settings.setVisible(true);
        if (!settings.canceled()) {
            model = new Grid(settings.width(), settings.height(),
                    settings.mines());
            model.add(gridView);
            gameController.setModel(model);
        }
    }

    /**
     * Entry point that starts the GUI.
     * @param args the arguments provided to the application
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(App::new);
    }
}
