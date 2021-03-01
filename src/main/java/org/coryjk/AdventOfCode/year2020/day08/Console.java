package org.coryjk.AdventOfCode.year2020.day08;

import org.coryjk.AdventOfCode.commons.Lang;

public final class Console {
    private int accumulator = 0;
    private int pc = 0;

    private final Instruction[] instructions;
    private final int[] args;

    enum Instruction {
        ACC,
        JMP,
        NOP
    }

    public Console(final String[] input) {
        instructions = new Instruction[input.length];
        args = new int[input.length];

        // parse and populate
        int i = 0;
        for (final String line : input) {
            final String[] split = line.split(" ");
            instructions[i] = Instruction.valueOf(split[0].toUpperCase());
            args[i] = Integer.parseInt(split[1].substring(
                    // only include sign for negative values
                    split[1].charAt(0) == Lang.PLUS ? 1 : 0));
            i++;
        }
    }

    public int next() {
        return pc < instructions.length ? (pc = execute(pc)) : -1;
    }

    public int peek() {
        return accumulator;
    }

    private int execute(final int pc) {
        final int arg = args[pc];
        switch (instructions[pc]) {
            case ACC:
                accumulator += arg;
            case NOP:
                return pc + 1;
            case JMP:
                return pc + args[pc];
            default:
        }
        throw new IllegalStateException(String.format(
                "Unknown instruction found at pc = [%s]: instruction = %s.", pc, instructions[pc]));
    }
}
