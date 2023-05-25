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

/**
 * The application.
 */
public class App {

    /**
     * Class constructor that initializes the GUI.
     */
    public App() {

        // The frame of the application
        JFrame frame = new JFrame("Minesweeper");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // The menu bar of the application
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");
        JMenuItem newGameMenuItem = new JMenuItem("New");
        newGameMenuItem.setAccelerator(
          KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        newGameMenuItem.addActionListener(e -> System.out.println("new game!"));
        gameMenu.add(newGameMenuItem);
        menuBar.add(gameMenu);

        // The grid view
        GridView gridView = new GridView();
        frame.getContentPane().add(gridView);

        // Display
        frame.setJMenuBar(menuBar);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
    }

    /**
     * Entry point that starts the GUI.
     * @param args the arguments provided to the application
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(App::new);
    }
}
