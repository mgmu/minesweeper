package minesweeper.model;

import minesweeper.view.Observer;

import java.util.List;
import java.util.ArrayList;

public class Game implements Observable {
    private final Grid grid;

    public static final int DEFAULT_HEIGHT = 16;
    public static final int MIN_HEIGHT = 1;
    public static final int MAX_HEIGHT = 256;

    public static final int DEFAULT_WIDTH = 30;
    public static final int MIN_WIDTH = 1;
    public static final int MAX_WIDTH = 256;

    public static final int DEFAULT_MINES = 99;
    public static final int MIN_MINES = 0;
    public static final int MAX_MINES = MAX_HEIGHT * MAX_WIDTH;

    // The observers of this Grid.
    private final List<Observer> observers;

    public Game() {
        this.grid = new Grid(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_MINES);
        this.observers = new ArrayList<>();
    }

    public Game(Grid grid) {
        this.grid = grid;
        this.observers = new ArrayList<>();
    }

    public Grid grid() {
        return this.grid;
    }

    @Override
    public void add(Observer observer) {
        this.observers.add(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer: this.observers) {
            observer.update(this);
        }
    }

    public boolean hasMineRevealed() {
        return this.grid.hasMineRevealed();
    }

    public boolean revealCellAt(Position position) {
        return this.grid.revealCellAt(position);
    }

    public boolean flagCellAt(Position position) {
        return this.grid.flagCellAt(position);
    }

    public boolean isMined() {
        return this.grid.isMined();
    }

    public void placeMines(List<Position> excluded) {
        this.grid.placeMines(excluded);
    }
}
