package org.coryjk.AdventOfCode.commons;

/**
 * Generalized solution format for Advent of Code problems.
 */
public interface Solution {
    /**
     * Initializes state by loading all required resources.
     */
    void feed();

    /**
     * Returns solution to part 1 as a string.
     *
     * @return Answer.
     */
    default String solvePart1() {
        return null;
    }

    /**
     * Returns solution to part 2 as a string.
     *
     * @return Answer.
     */
    default String solvePart2() {
        return null;
    }
}
