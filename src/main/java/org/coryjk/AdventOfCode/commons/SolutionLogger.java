package org.coryjk.AdventOfCode.commons;

import lombok.Getter;

public abstract class SolutionLogger implements Solution {
    @Getter static SolutionLogger instance;

    public SolutionLogger() {
        if (getInstance() != null) {
            throw new IllegalStateException("There is an existing Advent solution logger singleton!");
        }
        instance = this;
    }

    public abstract void solveAndLog();
}
