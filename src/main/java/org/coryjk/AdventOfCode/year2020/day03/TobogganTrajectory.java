package org.coryjk.AdventOfCode.year2020.day03;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.coryjk.AdventOfCode.commons.InputReader;
import org.coryjk.AdventOfCode.commons.SolutionLogger;

@Slf4j
public final class TobogganTrajectory extends SolutionLogger {
    private static final char TREE = '#';
    private static final int[] SLOPE = {1, 3};
    private static final int[][] SLOPES = {{1, 1}, {1, 3}, {1, 5}, {1, 7}, {2, 1}};

    private char[][] forest;

    /**
     * Initializes state by loading all required resources.
     */
    @Override
    public void feed() {
        final String[] input = InputReader.getStringInput(2020, 3);
        int i = 0;

        forest = new char[input.length][input[0].length()];
        for (final String line : input) {
            int j = 0;
            for (final char c : line.toCharArray()) {
                forest[i][j++] = c;
            }
            i++;
        }
    }

    /**
     * Returns solution to part 1 as a string.
     *
     * @return Answer.
     */
    @Override
    public String solvePart1() {
        return Integer.toString(treesEncountered(SLOPE));
    }

    /**
     * Returns solution to part 2 as a string.
     *
     * @return Answer.
     */
    @Override
    public String solvePart2() {
        long treesProduct = 1;
        for (final int[] slope : SLOPES) {
            treesProduct *= treesEncountered(slope);
        }
        return Long.toString(treesProduct);
    }

    private int treesEncountered(final int[] slope) {
        Pair<Integer, Integer> position = new MutablePair<>(0, 0);
        int trees = 0;

        while ((position = getNext(position, slope)) != null) {
            if (forest[position.getLeft()][position.getRight()] == TREE) {
                trees++;
            }
        }
        return trees;
    }


    private Pair<Integer, Integer> getNext(final Pair<Integer, Integer> position, final int[] slope) {
        final Pair<Integer, Integer> next = new MutablePair<>(
                position.getLeft() + slope[0],
                (position.getRight() + slope[1]) % forest[0].length);
        return next.getLeft() < forest.length ? next : null;
    }
}
