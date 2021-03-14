package org.coryjk.AdventOfCode.year2020.day10;

import lombok.extern.slf4j.Slf4j;
import org.coryjk.AdventOfCode.commons.InputReader;
import org.coryjk.AdventOfCode.commons.SolutionLogger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
        // sort in ascending order
        jolts.sort(Integer::compareTo);

        // implicit start at 0 and end at last jolt + 3
        jolts.add(0, 0);
        jolts.add(jolts.get(jolts.size()-1) + MAX_JOLTS_DIFF);

        return Long.toString(countUniquePaths(0, jolts, new HashMap<>()));
    }

    private long countUniquePaths(final int i, final List<Integer> jolts, final Map<Integer, Long> uniquePaths) {
        // base case, only 1 unique path at the end
        if (i == jolts.size()-1) {
            return 1L;
        }
        // check cache
        if (uniquePaths.containsKey(i)) {
            return uniquePaths.get(i);
        }
        long paths = 0;
        for (int j = i+1; j < jolts.size(); j++) {
            paths += jolts.get(j) - jolts.get(i) <= MAX_JOLTS_DIFF
                    ? countUniquePaths(j, jolts, uniquePaths)
                    : 0L;
        }
        uniquePaths.put(i, paths);
        return paths;
    }
}
