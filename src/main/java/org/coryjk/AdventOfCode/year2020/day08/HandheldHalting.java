package org.coryjk.AdventOfCode.year2020.day08;

import org.coryjk.AdventOfCode.commons.InputReader;
import org.coryjk.AdventOfCode.commons.SolutionLogger;

import java.util.HashSet;
import java.util.Set;

public final class HandheldHalting extends SolutionLogger {
    private Console console;

    /**
     * Initializes state by loading all required resources.
     */
    @Override
    public void feed() {
        console = new Console(InputReader.getStringInput(2020, 8));
    }

    /**
     * Returns solution to part 1 as a string.
     *
     * @return Answer.
     */
    @Override
    public String solvePart1() {
        final Set<Integer> programCounterStates = new HashSet<>();
        int pc;
        while (!programCounterStates.contains((pc = console.next()))) {
            programCounterStates.add(pc);
        }
        return Integer.toString(console.peek());
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
