package org.coryjk.AdventOfCode.year2020.day14;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.coryjk.AdventOfCode.commons.InputReader;
import org.coryjk.AdventOfCode.commons.SolutionLogger;

import java.util.Arrays;

@Slf4j
public final class DockingData extends SolutionLogger {
    private static final String MASK = "mask";

    private Memory memory;
    private Memory.Subroutine[] subroutines;

    /**
     * Initializes state by loading all required resources.
     */
    @Override
    public void feed() {
        final String[] input = InputReader.getStringInput(2020, 14);
        memory = new Memory();
        subroutines = new Memory.Subroutine[input.length];

        int k = 0;
        Memory.Subroutine subroutine = new Memory.Subroutine();
        for (int i = 0; i < input.length; i++) {
            if (input[i].contains(MASK)) {
                subroutines[k++] = subroutine;
                subroutine = new Memory.Subroutine().withMask(input[i]);
            } else {
                subroutine.withOperation(input[i]);
            }
        }
        // need to store last subroutine
        subroutines[k++] = subroutine;

        // cut null tail
        subroutines = ArrayUtils.subarray(subroutines, 0, k);
    }

    /**
     * Returns solution to part 1 as a string.
     *
     * @return Answer.
     */
    @Override
    public String solvePart1() {
        long sum = 0;
        Arrays.stream(subroutines).forEach(subroutine
                -> subroutine.applyOperations(memory, false));
        for (final long address : memory.getSetAddresses()) {
            sum += memory.get(address);
        }
        return Long.toString(sum);
    }

    /**
     * Returns solution to part 2 as a string.
     *
     * @return Answer.
     */
    @Override
    public String solvePart2() {
        long sum = 0;
        Arrays.stream(subroutines).forEach(subroutine
                -> subroutine.applyOperations(memory, true));
        for (final long address : memory.getSetAddresses()) {
            sum += memory.get(address);
        }
        return Long.toString(sum);
    }
}
