package org.coryjk.AdventOfCode.year2021.day04;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.coryjk.AdventOfCode.commons.InputReader;
import org.coryjk.AdventOfCode.commons.SolutionLogger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class GiantSquid extends SolutionLogger {

    private static final int N = 5;

    private String[] input;
    private List<Integer> calledNumbers;
    private List<BingoBoard> bingoBoards;

    @Override
    public void feed() {
        input = InputReader.getStringInput(2021, 4);

        // parse called numbers
        calledNumbers = Arrays.stream(input[0].split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());


        // input rows
        List<List<Integer>> allRows = new ArrayList<>(N);
        for (int i = 2; i < input.length; i++) {
            final String inputRow = input[i];
            // disregard empty lines
            if (inputRow.length() != 0) {
                // accumulate numbers
                final List<Integer> numbersRow = Arrays.stream(inputRow.split("\s+"))
                        .filter(n -> n.length() > 0)
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());
                allRows.add(numbersRow);
            }
        }

        // parse bingo boards with N rows
        bingoBoards = new ArrayList<>(50);
        for (int i = 0; i < allRows.size(); i += N) {
            bingoBoards.add(
                    new BingoBoard(allRows.subList(i, i+N), false));
        }
    }

    @Override
    public String solvePart1() {
        return "" + score(callUntilFirstWinner(calledNumbers, bingoBoards));
    }

    @Override
    public String solvePart2() {
        return "" + score(callUntilLastWinner(calledNumbers, bingoBoards));
    }

    private int score(final Pair<BingoBoard, Integer> winningResult) {
        final BingoBoard winner = winningResult.getLeft();
        final int winningNumber = winningResult.getRight();
        if (winner == null) {
            throw new IllegalStateException("No winning boards found!");
        }
        return winningNumber * winner.getNumbers().stream()
                .flatMap(Collection::stream)
                .filter(n -> !winner.isMarked(n))
                .reduce(Integer::sum)
                .orElse(0);
    }

    private Pair<BingoBoard, Integer> callUntilFirstWinner(final List<Integer> calledNumbers,
                                                           final List<BingoBoard> bingoBoards) {
        for (final int n : calledNumbers) {
            for (final BingoBoard bingoBoard : bingoBoards) {
                if (bingoBoard.mark(n, true)) {
                    return ImmutablePair.of(bingoBoard, n);
                }
            }
        }
        return ImmutablePair.nullPair();
    }

    private Pair<BingoBoard, Integer> callUntilLastWinner(final List<Integer> calledNumbers,
                                                          final List<BingoBoard> bingoBoards) {
        final boolean[] finishedBoards = new boolean[bingoBoards.size()];
        int boardsWon = 0;

        for (final int n : calledNumbers) {
            for (int i = 0; i < bingoBoards.size(); i++) {
                // only count each board once
                if (bingoBoards.get(i).mark(n, true)) {
                    boardsWon += finishedBoards[i] ? 0 : 1;
                    finishedBoards[i] = true;

                    if (boardsWon == bingoBoards.size()) {
                        return ImmutablePair.of(bingoBoards.get(i), n);
                    }
                }
            }
        }

        return ImmutablePair.nullPair();
    }
}
