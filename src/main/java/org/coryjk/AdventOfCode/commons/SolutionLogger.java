package org.coryjk.AdventOfCode.commons;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class SolutionLogger implements Solution {
    @Getter static SolutionLogger instance;

    public SolutionLogger() {
        if (getInstance() != null) {
            throw new IllegalStateException("There is an existing Advent solution logger singleton!");
        }
        instance = this;
    }

    public void solve() {
        feed();
        log.info("[{}]: Part 1 [{}], Part 2 [{}]", getClass().getSimpleName(), solvePart1(), solvePart2());
    }
}
