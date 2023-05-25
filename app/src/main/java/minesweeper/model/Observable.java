package minesweeper.model;

import minesweeper.view.Observer;

/**
 * Represents an object that is observable by Observers. Such Observers can
 * register themselves as observers of this Observable and be notified of an
 * update of this Observable.
 */
public interface Observable {

    /**
     * Adds the given Observer as an observer of this Observable.
     * @param observer the new observer
     */
    void add(Observer observer);

    /**
     * Notifies every observer of an update of this Observable.
     */
    void notifyObservers();
}
