package org.coryjk.AdventOfCode.year2020.day13;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.coryjk.AdventOfCode.commons.InputReader;
import org.coryjk.AdventOfCode.commons.SolutionLogger;
import org.coryjk.AdventOfCode.commons.math.AdventMath;

@Slf4j
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
        final Pair<int[], int[]> idsAndDeltas = parseIdsAndGetTimeDeltas(busIds);
        final int[] ids = idsAndDeltas.getLeft();
        final int[] deltas = idsAndDeltas.getRight();

        /*
         * Provided:
         *      t == a1 (mod m1)
         *      t == a2 (mod m2)
         *      ...
         *      t == an (mod mn),
         *
         * Let f(k, ID) = π(ID) / ID[k], and let a = ID[i] - m, where m is our time delta.
         * We may find t, such that:
         *           A    F         Q
         *           ---  ---       ---
         *      t == a1 * f(1,ID) * invmod(f(1,ID), m1)
         *         + a2 * f(2,ID) * invmod(f(2,ID), m2)
         *         + ...
         *         + an * f(n,ID) * invmod(f(n,ID), mn)
         *      or
         *
         *      t == Σ(A*F*Q) % π(ID)
         *
         * From: https://math.stackexchange.com/questions/219796/find-a-number-x100-that-satisfies-three-congruences?rq=1
         */
        final int N = idsAndDeltas.getLeft().length;

        // precompute π(ID)
        final long pi = multiplicativeSequence(idsAndDeltas.getLeft());

        // compute t using vectors A, F, and Q
        long a, f, q, t = 0;
        for (int i = 0; i < N; i++) {
            a = ids[i] - deltas[i];
            f = pi / ids[i];
            q = AdventMath.lazyInverseMod(f, ids[i]).longValue();
            t += a * f * q;
        }

        // apply final mod
        return Long.toString(t % pi);
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

    private Pair<int[], int[]> parseIdsAndGetTimeDeltas(final String[] stringIds) {
        final int[] ids = new int[stringIds.length];
        final int[] deltas = new int[stringIds.length];
        int k = 0;
        int delta = -1; // start at 0 since we iterate first
        for (final String id : stringIds) {
            delta++;
            if (OUT_OF_SERVICE.equals(id)) {
                continue;
            }
            ids[k] = Integer.parseInt(id);
            deltas[k] = delta;
            k++;
        }
        return new ImmutablePair<>(
                ArrayUtils.subarray(ids, 0, k),
                ArrayUtils.subarray(deltas, 0, k));
    }

    private long multiplicativeSequence(final int[] set) {
        long pi = 1;
        for (final int i : set) {
            pi *= i;
        }
        return pi;
    }
}
