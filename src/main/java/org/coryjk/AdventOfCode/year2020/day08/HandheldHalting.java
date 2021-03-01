package org.coryjk.AdventOfCode.year2020.day08;

import org.coryjk.AdventOfCode.commons.InputReader;
import org.coryjk.AdventOfCode.commons.SolutionLogger;

import java.util.HashSet;
import java.util.Set;

public final class HandheldHalting extends SolutionLogger {
    private String[] input;
    private Console console;

    /**
     * Initializes state by loading all required resources.
     */
    @Override
    public void feed() {
        input = InputReader.getStringInput(2020, 8);
        console = new Console(input);
    }

    /**
     * Returns solution to part 1 as a string.
     *
     * @return Answer.
     */
    @Override
    public String solvePart1() {
        terminates(console);
        return Integer.toString(console.peek());
    }

    /**
     * Returns solution to part 2 as a string.
     *
     * @return Answer.
     */
    @Override
    public String solvePart2() {
        Console console;
        int i = 0;

        while (i < input.length) {
            // go forward with computing replacement if at JMP or NOP instruction
            final String replacedLine = flipJmpNop(input[i]);
            if (replacedLine != null) {
                input[i] = replacedLine;
                console = new Console(input);
                // peek accumulator value if termination occurs
                if (terminates(console)) {
                    return Integer.toString(console.peek());
                }
                // revert line back to original state if no termination occurs
                input[i] = flipJmpNop(input[i]);
            }
            i++;
        }

        // nothing found :(
        return null;
    }

    private boolean terminates(final Console console) {
        final Set<Integer> programCounterStates = new HashSet<>();
        int pc;
        while (!programCounterStates.contains((pc = console.next())) && pc >= 0) {
            programCounterStates.add(pc);
        }
        return pc < 0;
    }

    private String flipJmpNop(final String line) {
        String from, to;
        if (line.contains(Console.Instruction.JMP.toString())) {
            from = Console.Instruction.JMP.toString();
            to = Console.Instruction.NOP.toString();
        } else if (line.contains(Console.Instruction.NOP.toString())) {
            from = Console.Instruction.NOP.toString();
            to = Console.Instruction.JMP.toString();
        } else {
            return null;
        }
        return line.replace(from, to);
    }
}
