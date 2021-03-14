package org.coryjk.AdventOfCode.year2020.day11;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.coryjk.AdventOfCode.commons.InputReader;
import org.coryjk.AdventOfCode.commons.SolutionLogger;
import org.coryjk.AdventOfCode.commons.Utils;
import org.coryjk.AdventOfCode.commons.functional.TriFunction;

import java.util.function.BiFunction;

@Slf4j
public final class SeatingSystem extends SolutionLogger {

    @Getter
    @RequiredArgsConstructor
    public enum SeatState {
        FLOOR('.'),
        AVAILABLE('L'),
        OCCUPIED('#');

        public static SeatState getStateBySymbol(final char symbol) {
            for (final SeatState state : SeatState.values()) {
                if (state.getSymbol() == symbol) {
                    return state;
                }
            }
            throw new IllegalArgumentException("No matching SeatState found for symbol: " + symbol);
        }

        private final char symbol;
    }

    private static final int[][] DIRECTIONS = {
            { 1,  0}, { 0,  1}, {-1,  0}, { 0, -1},
            { 1,  1}, {-1,  1}, { 1, -1}, {-1, -1},
    };

    private char[][] seats;

    /**
     * Initializes state by loading all required resources.
     */
    @Override
    public void feed() {
        final String[] input = InputReader.getStringInput(2020, 11);
        seats = new char[input.length][input[0].length()];
        for (int i = 0; i < seats.length; i++) {
            seats[i] = input[i].toCharArray();
        }
    }

    /**
     * Returns solution to part 1 as a string.
     *
     * @return Answer.
     */
    @Override
    public String solvePart1() {
        simulate((state, adjacentSeats) -> {
            switch (state) {
                // if seat is empty and no occupied adjacent seats, becomes occupied
                case AVAILABLE:
                    if (Utils.countChars(SeatState.OCCUPIED.getSymbol(), adjacentSeats) == 0) {
                        return SeatState.OCCUPIED;
                    }
                    // if seat is occupied and >= 4 seats adjacent are occupied, becomes empty
                case OCCUPIED:
                    if (Utils.countChars(SeatState.OCCUPIED.getSymbol(), adjacentSeats) >= 4) {
                        return SeatState.AVAILABLE;
                    }
                default:
                    break;
                case FLOOR:
                    return SeatState.FLOOR;
            }
            // no change
            return state;
        }, this::getAdjacentSeats);
        return Integer.toString(Utils.countChars(SeatState.OCCUPIED.getSymbol(), seats));
    }

    /**
     * Returns solution to part 2 as a string.
     *
     * @return Answer.
     */
    @Override
    public String solvePart2() {
        simulate((state, adjacentSeats) -> {
            switch (state) {
                // if seat is empty and no occupied adjacent seats, becomes occupied
                case AVAILABLE:
                    if (Utils.countChars(SeatState.OCCUPIED.getSymbol(), adjacentSeats) == 0) {
                        return SeatState.OCCUPIED;
                    }
                    // if seat is occupied and >= 5 seats adjacent are occupied, becomes empty
                case OCCUPIED:
                    if (Utils.countChars(SeatState.OCCUPIED.getSymbol(), adjacentSeats) >= 5) {
                        return SeatState.AVAILABLE;
                    }
                default:
                    break;
                case FLOOR:
                    return SeatState.FLOOR;
            }
            // no change
            return state;
        }, this::getVisibleSeats);
        return Integer.toString(Utils.countChars(SeatState.OCCUPIED.getSymbol(), seats));
    }

    private void simulate(final BiFunction<SeatState, char[], SeatState> seatingRule,
                          final TriFunction<Integer, Integer, char[][], char[]> getSeats) {
        char[][] nextState;
        while (!isEquals(seats, (nextState = nextState(seatingRule, getSeats)))) {
            seats = nextState;
        }
    }

    private char[][] nextState(final BiFunction<SeatState, char[], SeatState> seatingRule,
                               final TriFunction<Integer, Integer, char[][], char[]> getSeats) {
        final char[][] nextState = new char[seats.length][seats[0].length];
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[0].length; j++) {
                nextState[i][j] = seatingRule.apply(SeatState.getStateBySymbol(seats[i][j]),
                        getSeats.apply(i, j, seats)).getSymbol();
            }
        }
        return nextState;
    }

    private char[] getAdjacentSeats(final int i, final int j, final char[][] seats) {
        int k = 0;
        final char[] adjacentSeats = new char[DIRECTIONS.length];
        for (final int[] dir : DIRECTIONS) {
            int x = i + dir[0];
            int y = j + dir[1];
            // out of bounds
            if (x < 0 || x >= seats.length || y < 0 || y >= seats[0].length) {
                continue;
            }
            // seat found
            adjacentSeats[k++] = seats[x][y];
        }
        return ArrayUtils.subarray(adjacentSeats, 0, k);
    }

    private char[] getVisibleSeats(final int i, final int j, final char[][] seats) {
        int k = 0;
        final char[] visibleSeats = new char[DIRECTIONS.length];
        for (final int[] dir : DIRECTIONS) {
            int x = i + dir[0], y = j + dir[1];
            char visibleSeat = SeatState.FLOOR.getSymbol();
            // within seating area
            while (!(x < 0 || x >= seats.length || y < 0 || y >= seats[0].length)) {
                // continue until out of seating area or until non-floor seat found
                if (seats[x][y] != SeatState.FLOOR.getSymbol()) {
                    visibleSeat = seats[x][y];
                    break;
                }
                x += dir[0];
                y += dir[1];
            }
            visibleSeats[k++] = visibleSeat;
        }
        return ArrayUtils.subarray(visibleSeats, 0, k);
    }

    private boolean isEquals(final char[][] m1, final char[][] m2) {
        if (m1.length != m2.length || m1[0].length != m2[0].length) {
            return false;
        }
        for (int i = 0; i < m1.length; i++) {
            for (int j = 0; j < m1[0].length; j++) {
                if (m1[i][j] != m2[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
}
