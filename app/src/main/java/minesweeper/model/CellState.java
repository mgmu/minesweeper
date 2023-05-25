package minesweeper.model;

/**
 * The CellState represents the state of a Cell at any time. A Cell can be
 * either hidden, flagged or revealed.
 */
public enum CellState {
    
    /**
     * The hidden state.
     */
    HIDDEN,

    /**
     * The flagged state.
     */
    FLAGGED,

    /**
     * The revealed state.
     */
    REVEALED;
}
