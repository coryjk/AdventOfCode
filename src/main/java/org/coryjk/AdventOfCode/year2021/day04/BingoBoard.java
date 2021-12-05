package org.coryjk.AdventOfCode.year2021.day04;

import lombok.Getter;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BingoBoard {

    @Getter
    private final List<List<Integer>> numbers;
    private final Map<Integer, Pair<Integer, Integer>> positionByNumber;

    private final boolean[][] marked;
    private final boolean hasDiagonals;
    private final int N;

    private int markedValues = 0;

    public BingoBoard(final List<List<Integer>> numbers, final boolean hasDiagonals) {
        this.numbers = numbers;
        this.hasDiagonals = hasDiagonals;

        // validate board size
        if (numbers.size() != numbers.get(0).size()) {
            throw new IllegalStateException("Invalid potential board size for: " + numbers);
        }
        N = numbers.size();
        marked = new boolean[N][N];

        // pre-populate index-mapping
        positionByNumber = new HashMap<>(N * N);
        for (int i = 0; i < numbers.size(); i++) {
            for (int j = 0; j < numbers.get(0).size(); j++) {
                positionByNumber.put(numbers.get(i).get(j), ImmutablePair.of(i, j));
            }
        }
    }

    public boolean mark(final int number, final boolean evaluateWinner) {
        final Pair<Integer, Integer> position = positionByNumber.get(number);
        if (position == null) {
            return false;
        } else {
            marked[position.getLeft()][position.getRight()] = true;
            markedValues++;
            return evaluateWinner && isComplete(position.getLeft(), position.getRight());
        }
    }

    public boolean isMarked(final int number) {
        final Pair<Integer, Integer> position = positionByNumber.get(number);
        return marked[position.getLeft()][position.getRight()];
    }

    @Override
    public String toString() {
        final StringBuilder boardAsString = new StringBuilder();
        for (final List<Integer> row : numbers) {
            final StringBuilder rowAsString = new StringBuilder();
            row.forEach(n -> rowAsString.append(
                    String.format(isMarked(n) ? " {%d}" : " %d ", n)));
            rowAsString.append('\n');
            boardAsString.append(rowAsString);
        }
        return boardAsString.toString();
    }

    private boolean isComplete(final int row, final int col) {
        if (markedValues < N) {
            return false;
        }

        boolean rowComplete = true;
        boolean columnComplete = true;

        // check rows and columns
        for (int i = 0; i < N; i++) {
            rowComplete = rowComplete && marked[row][i];
            columnComplete = columnComplete && marked[i][col];

            // stop early if completion already impossible
            if (!(rowComplete || columnComplete)) {
                break;
            }
        }

        return rowComplete || columnComplete
                || (hasDiagonals && row == col && isDiagonalComplete());
    }

    private boolean isDiagonalComplete() {
        boolean topDiagonalComplete = true;
        boolean bottomDiagonalComplete = true;
        for (int i = 0; i < N; i++) {
            topDiagonalComplete = topDiagonalComplete && marked[i][i];
            bottomDiagonalComplete = bottomDiagonalComplete && marked[N-1 - i][i];

            // stop early if completion already impossible
            if (!(topDiagonalComplete || bottomDiagonalComplete)) {
                break;
            }
        }

        // check completeness
        return topDiagonalComplete || bottomDiagonalComplete;
    }
}
