package minesweeper.view;

import minesweeper.model.RevealOnlyGrid;

/**
 * Observes the updates on an observable object.
 */
public interface Observer {

    /**
     * Updates this Observer with the new state of the observed object.
     * @param rog the new state of the observed RevealOnlyGrid
     */
    void update(RevealOnlyGrid rog);
}
