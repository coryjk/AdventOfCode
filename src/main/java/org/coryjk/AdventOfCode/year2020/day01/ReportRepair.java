package org.coryjk.AdventOfCode.year2020.day01;

import lombok.Builder;
import lombok.extern.slf4j.XSlf4j;
import org.coryjk.AdventOfCode.commons.InputReader;
import org.coryjk.AdventOfCode.commons.Solution;

import java.util.LinkedList;
import java.util.List;

@XSlf4j
@Builder
public final class ReportRepair implements Solution {
    private final int[] input;

    @Override
    public String solve() {
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
        return Integer.toString(sumTo2020.stream()
                .reduce(Math::multiplyExact)
                .orElse(0));
    }

    @Override
    public void solveAndLog() {
        log.info(ReportRepair.builder()
                .input(InputReader.getIntInput(2020,1))
                .build()
                .solve());
    }

    private boolean sumsTo2020(final int ... indices) {
        int sum = 0;
        for (final int i : indices) {
            sum += input[i];
        }
        return sum == 2020;
    }
}
