package org.coryjk.AdventOfCode.year2020.day01;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.coryjk.AdventOfCode.commons.InputReader;
import org.coryjk.AdventOfCode.commons.SolutionLogger;

import java.util.LinkedList;
import java.util.List;

@Slf4j
@NoArgsConstructor
public final class ReportRepair extends SolutionLogger {
    private int[] input;

    @Override
    public void feed() {
        input = InputReader.getIntInput(2020, 1);
    }

    @Override
    public String solvePart1() {
        final List<Integer> sumTo2020 = new LinkedList<>();
        for (int i = 0; i < input.length; i++) {
            for (int j = i+1; j < input.length-1; j++) {
                if (sumsTo2020(i, j)) {
                    sumTo2020.add(input[i]);
                    sumTo2020.add(input[j]);
                }
            }
        }
        return reduceProduct(sumTo2020);
    }

    @Override
    public String solvePart2() {
        final List<Integer> sumTo2020 = new LinkedList<>();
        for (int i = 0; i < input.length; i++) {
            for (int j = i+1; j < input.length-1; j++) {
                for (int k = j+1; k < input.length-2; k++) {
                    if (sumsTo2020(i, j, k)) {
                        sumTo2020.add(input[i]);
                        sumTo2020.add(input[j]);
                        sumTo2020.add(input[k]);
                    }
                }
            }
        }
        return reduceProduct(sumTo2020);
    }

    private boolean sumsTo2020(final int ... indices) {
        int sum = 0;
        for (final int i : indices) {
            sum += input[i];
        }
        return sum == 2020;
    }

    private String reduceProduct(final List<Integer> sums) {
        return Integer.toString(sums.stream()
                .reduce(Math::multiplyExact)
                .orElse(0));
    }
}
