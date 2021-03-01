package org.coryjk.AdventOfCode.year2020.day09;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.coryjk.AdventOfCode.commons.InputReader;
import org.coryjk.AdventOfCode.commons.SolutionLogger;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Slf4j
public final class EncodingError extends SolutionLogger {
    private static final int PREAMBLE_SIZE = 25;
    private long[] encoding;
    private long invalidNumber = -1;

    /**
     * Initializes state by loading all required resources.
     */
    @Override
    public void feed() {
        encoding = Arrays.stream(InputReader.getStringInput(2020, 9))
                .mapToLong(Long::parseLong)
                .toArray();
    }

    /**
     * Returns solution to part 1 as a string.
     *
     * @return Answer.
     */
    @Override
    public String solvePart1() {
        final Map<Long, Integer> valueByIndex = new HashMap<>();
        int i = PREAMBLE_SIZE;
        /*
         * Process each number by processing the sub-encoding for a static preamble size.
         * Return the number if no two numbers in the sub-encoding sums to it.
         */
        while (i < encoding.length) {
            final long num = encoding[i++];
            if (!isValidEncoding(num, ArrayUtils.subarray(encoding, i-PREAMBLE_SIZE-1, i),
                    // map to be reused to avoid unnecessary heap usage
                    valueByIndex)) {
                // save for part 2
                invalidNumber = num;
                return Long.toString(num);
            }
        }
        // no solution :(
        return null;
    }

    /**
     * Returns solution to part 2 as a string.
     *
     * @return Answer.
     */
    @Override
    public String solvePart2() {
        if (invalidNumber > 0) {
            // no need to check values after the invalid number or values less than it
            final long[] subEncoding = ArrayUtils.subarray(
                    encoding, 0, ArrayUtils.indexOf(encoding, invalidNumber));

            // mark larger values that are immediately disqualified, including contiguous sums that are too large
            for (int i = subEncoding.length - 1; i >= 1; i--) {
                if (subEncoding[i] >= invalidNumber
                        || subEncoding[i] + subEncoding[i - 1] > invalidNumber) {
                    subEncoding[i] = -1;
                }
            }

            /*
             * Starting from larger values, seek contiguous numbers that sum to the invalid number,
             * represented by pairs of indices i and j, where: invalid number = sum(encoding[i..j+1]).
             * All pre-invalidated values are marked with -1, and should be skipped.
             */
            final List<Long> values = new LinkedList<>();
            for (int i = subEncoding.length - 1; i >= 1; i--) {
                if (subEncoding[i] > 0) {
                    for (int j = i - 1; j >= 0; j--) {
                        if (subEncoding[j] < 0) {
                            break;
                        }
                        // compute new sum
                        final long sum = sum(values) + subEncoding[j];
                        values.add(subEncoding[j]);
                        if (sum == invalidNumber) {
                            // answer found
                            return Long.toString(Collections.min(values) + Collections.max(values));
                        }
                    }
                    // reset, as current attempt failed
                    values.clear();
                }
            }
        }
        // no solution :(
        return null;
    }

    private boolean isValidEncoding(final long num, final long[] subEncoding, final Map<Long, Integer> values) {
        values.clear();
        for (int i = 0; i < subEncoding.length; i++) {
            if (values.containsKey(num - subEncoding[i])) {
                return true;
            }
            values.put(subEncoding[i], i);
        }
        return false;
    }

    private long sum(final Collection<Long> values) {
        int sum = 0;
        for (final Long value : values) {
            sum += value;
        }
        return sum;
    }
}
