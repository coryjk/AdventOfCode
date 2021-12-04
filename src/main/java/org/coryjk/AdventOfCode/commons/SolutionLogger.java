package org.coryjk.AdventOfCode.commons;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class SolutionLogger implements Solution {

    public void solve() {
        final String solution1;
        final String solution2;

        /*
         * Initialize state before each solution part. This is to reset any change in initial state
         * done during solving of an earlier part.
         */
        feed();
        solution1 = solvePart1();
        feed();
        solution2 = solvePart2();

        // display solutions
        log.info("[{}]: Part 1 [{}], Part 2 [{}]", getClass().getSimpleName(), solution1, solution2);
    }

    public String solvePart1() {
        return "PART 1 NOT IMPLEMENTED";
    }

    public String solvePart2() {
        return "PART 2 NOT IMPLEMENTED";
    }
}
