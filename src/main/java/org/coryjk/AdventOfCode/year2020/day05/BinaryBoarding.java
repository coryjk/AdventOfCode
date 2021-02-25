package org.coryjk.AdventOfCode.year2020.day05;

import lombok.extern.slf4j.Slf4j;
import org.coryjk.AdventOfCode.commons.InputReader;
import org.coryjk.AdventOfCode.commons.SolutionLogger;

import java.util.Arrays;
import java.util.stream.IntStream;

@Slf4j
public final class BinaryBoarding extends SolutionLogger {
    private final Airplane airplane = new Airplane();
    private String[] input;

    /**
     * Initializes state by loading all required resources.
     */
    @Override
    public void feed() {
        input = InputReader.getStringInput(2020, 5);
    }

    /**
     * Returns solution to part 1 as a string.
     *
     * @return Answer.
     */
    @Override
    public String solvePart1() {
        return Integer.toString(IntStream.of(getSeatIds(input))
                .max().orElse(-1));
    }

    /**
     * Returns solution to part 2 as a string.
     *
     * @return Answer.
     */
    @Override
    public String solvePart2() {
        final int[] seatIds = getSeatIds(input);
        final int maxSeatId = IntStream.of(seatIds)
                .max().orElse(-1);

        // populate present seats
        final boolean[] ids = new boolean[maxSeatId+1];
        for (final int id : seatIds) {
            ids[id] = true;
        }

        // missing seat is the only one missing with both -1 and +1 ids present
        return Integer.toString(IntStream.range(1, ids.length-1)
                .filter(i -> ids[i-1] && !ids[i] && ids[i+1])
                .findFirst()
                .orElse(-1));
    }

    private int[] getSeatIds(final String[] boardingPasses) {
        return Arrays.stream(boardingPasses)
                .map(airplane::getSeatId)
                .mapToInt(Integer::intValue).toArray();
    }
}
