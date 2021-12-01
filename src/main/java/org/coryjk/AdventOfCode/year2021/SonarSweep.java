package org.coryjk.AdventOfCode.year2021;

import lombok.extern.slf4j.Slf4j;
import org.coryjk.AdventOfCode.commons.InputReader;
import org.coryjk.AdventOfCode.commons.SolutionLogger;

@Slf4j
public class SonarSweep extends SolutionLogger {

    private int[] input;

    @Override
    public void feed() {
        input = InputReader.getIntInput(2021, 1);
    }

    @Override
    public String solvePart1() {
        int increased = 0;
        for (int i = 1; i < input.length; i++) {
            if (input[i] > input[i-1]) {
                increased++;
            }
        }
        return "" + increased;
    }

    @Override
    public String solvePart2() {
        int increased = 0;
        int previousSum = -1;
        for (int i = 1; i < input.length-1; i++) {
            final int sum = input[i-1] + input[i] + input[i+1];
            if (sum > previousSum && previousSum > 0) {
                increased++;
            }
            previousSum = sum;
        }

        return "" + increased;
    }
}
