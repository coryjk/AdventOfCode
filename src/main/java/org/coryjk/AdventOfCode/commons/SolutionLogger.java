package org.coryjk.AdventOfCode.commons;

import lombok.Getter;
import lombok.extern.slf4j.XSlf4j;

@XSlf4j
public abstract class SolutionLogger implements Solution {
    @Getter static SolutionLogger instance;

    public SolutionLogger() {
        if (getInstance() != null) {
            throw new IllegalStateException("There is an existing Advent solution logger singleton!");
        }
        instance = this;
    }

    public void solve() {
        log.info("[{}]:", getClass().getSimpleName());
        log.info("Part 1: " + solvePart1());
        log.info("Part 2: " + solvePart2());
    }
}
