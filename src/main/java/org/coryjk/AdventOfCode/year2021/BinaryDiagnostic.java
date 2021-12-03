package org.coryjk.AdventOfCode.year2021;

import lombok.extern.slf4j.Slf4j;
import org.coryjk.AdventOfCode.commons.InputReader;
import org.coryjk.AdventOfCode.commons.SolutionLogger;

@Slf4j
public class BinaryDiagnostic extends SolutionLogger {

    private String[] input;

    @Override
    public void feed() {
        input = InputReader.getStringInput(2021, 3);
    }

    @Override
    public String solvePart1() {
        final byte[][] bitMap = createBitMap(input);
        final StringBuilder mostCommonBitsPerColumn = new StringBuilder(bitMap[0].length);
        for (int i = 0; i < bitMap[0].length; i++) {
            int count = 0;
            for (final byte[] bytes : bitMap) {
                count += bytes[i];
            }
            mostCommonBitsPerColumn.append(count > bitMap.length/2 ? 1 : 0);
        }

        // compute final answer
        final int gammaRate = Integer.parseInt(mostCommonBitsPerColumn.toString(), 2);
        return gammaRate * invertBits(gammaRate) + "";
    }

    @Override
    public String solvePart2() {
        return "";
    }

    private byte[][] createBitMap(final String[] binaryStrings) {
        final byte[][] bitMap = new byte[binaryStrings.length][binaryStrings[0].length()];
        for (int i = 0; i < binaryStrings.length; i++) {
            // true = 1, false = 0
            for (int j = 0; j < binaryStrings[0].length(); j++) {
                bitMap[i][j] = (byte) (binaryStrings[i].charAt(j) == '1' ? 1 : 0);
            }
        }
        return bitMap;
    }

    private int countLeadingZeros(int bits) {
        int leadingZeros = 0;
        while ((bits & (1 << (Integer.SIZE-1))) == 0) {
            bits = bits << 1;
            leadingZeros++;
        }
        return leadingZeros;
    }

    private int invertBits(final int bits) {
        final int leadingZeros = countLeadingZeros(bits);

        // truncate bits that were once leading zeros (now "leading ones")
        return ~bits << leadingZeros >> leadingZeros;
    }
}
