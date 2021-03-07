package org.coryjk.AdventOfCode.year2020.day10;

import lombok.extern.slf4j.Slf4j;
import org.coryjk.AdventOfCode.commons.InputReader;
import org.coryjk.AdventOfCode.commons.SolutionLogger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Slf4j
public final class AdapterArray extends SolutionLogger {
    private final static int MAX_JOLTS_DIFF = 3;

    final List<Integer> jolts = new ArrayList<>();

    /**
     * Initializes state by loading all required resources.
     */
    @Override
    public void feed() {
        jolts.clear();
        Arrays.stream(InputReader.getIntInput(2020, 10)).forEach(jolts::add);
    }

    /**
     * Returns solution to part 1 as a string.
     *
     * @return Answer.
     */
    @Override
    public String solvePart1() {
        // sort in ascending order
        jolts.sort(Integer::compareTo);

        // track differentials
        final Map<Integer, Integer> diffCount = new TreeMap<Integer, Integer>();
        int totalJolts = 0;
        for (int i = 0; i < jolts.size(); i++) {
            int diff = jolts.get(i) - totalJolts;
            if (diff > MAX_JOLTS_DIFF) {
                throw new IllegalStateException(String.format("Found invalid jolts differential at i=%d for"
                        + " jolt %d, with current total at %d.", i, jolts.get(i), totalJolts));
            }
            // accumulate and track
            totalJolts += diff;
            diffCount.put(diff, diffCount.getOrDefault(diff, 0) + 1);
        }
        // track last amount
        diffCount.put(MAX_JOLTS_DIFF, diffCount.getOrDefault(MAX_JOLTS_DIFF, 0) + 1);
        return Integer.toString(diffCount.get(1) * diffCount.get(3));
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
}
