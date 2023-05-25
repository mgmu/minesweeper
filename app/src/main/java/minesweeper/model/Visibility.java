package minesweeper.model;

/**
 * The Visibility represents the visibility state of a Cell at any time. A Cell
 * can be either hidden, flagged or revealed.
 */
public enum Visibility {
    
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
