package org.coryjk.AdventOfCode.year2020.day13;

import org.apache.commons.lang3.ArrayUtils;
import org.coryjk.AdventOfCode.commons.InputReader;
import org.coryjk.AdventOfCode.commons.SolutionLogger;

public final class ShuttleSearch extends SolutionLogger {
    private static final String OUT_OF_SERVICE = "x";

    private int timestamp;
    private String[] busIds;

    /**
     * Initializes state by loading all required resources.
     */
    @Override
    public void feed() {
        final String[] input = InputReader.getStringInput(2020, 13);
        timestamp = Integer.parseInt(input[0]);
        busIds = input[1].split(",");
    }

    /**
     * Returns solution to part 1 as a string.
     *
     * @return Answer.
     */
    @Override
    public String solvePart1() {
        final int[] ids = parseIds(busIds);
        int t = timestamp;
        while (true) {
            // check if any bus is available for time t
            for (final int id : ids) {
                if (t % id == 0) {
                    return Integer.toString(id * (t-timestamp));
                }
            }
            t++;
        }
    }

    /**
     * Returns solution to part 2 as a string.
     *
     * @return Answer.
     */
    @Override
    public String solvePart2() {
        return null;
    }

    private int[] parseIds(final String[] stringIds) {
        final int[] ids = new int[stringIds.length];
        int k = 0;
        for (final String id : stringIds) {
            if (OUT_OF_SERVICE.equals(id)) {
                continue;
            }
            ids[k++] = Integer.parseInt(id);
        }
        return ArrayUtils.subarray(ids, 0, k);
    }
}
