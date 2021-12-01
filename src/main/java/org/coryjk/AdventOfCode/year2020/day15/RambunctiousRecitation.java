package org.coryjk.AdventOfCode.year2020.day15;

import lombok.extern.slf4j.Slf4j;
import org.coryjk.AdventOfCode.commons.InputReader;
import org.coryjk.AdventOfCode.commons.SolutionLogger;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public final class RambunctiousRecitation extends SolutionLogger {

    private static final int PART_ONE_END = 2020;
    private static final int PART_TWO_END = 30000000;

    private Map<Integer, Integer> valuesToTurns;
    private int[] startingNumbers;


    @Override
    public void feed() {
        startingNumbers = InputReader.getIntInput(2020, 15);
        valuesToTurns = new HashMap<>();
    }

    @Override
    public String solvePart1() {
        return "" + solve(PART_ONE_END);
    }

    @Override
    public String solvePart2() {
        return "" + solve(PART_TWO_END);
    }

    private int solve(final int end) {
        for (int i = 0; i < startingNumbers.length; i++) {
            valuesToTurns.put(startingNumbers[i], i);
        }

        int current = 0;
        int previous;

        for (int i = startingNumbers.length; i < end-1; i++) {
            previous = current;
            if (!valuesToTurns.containsKey(current)) {
                current = 0;
            } else {
                current = i - valuesToTurns.get(current);
            }
            valuesToTurns.put(previous, i);
        }

        return current;
    }
}
