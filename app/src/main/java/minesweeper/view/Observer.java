package minesweeper.view;

import minesweeper.model.Game;

/**
 * Observes the updates on an observable object.
 */
public interface Observer {

    /**
     * Updates this Observer with the new state of the observed object.
     * @param game the new state of the observed Game
     */
    void update(Game game);
}
