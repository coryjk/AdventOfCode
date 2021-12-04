package org.coryjk.AdventOfCode.year2021.day03;

import lombok.extern.slf4j.Slf4j;
import org.coryjk.AdventOfCode.commons.InputReader;
import org.coryjk.AdventOfCode.commons.SolutionLogger;
import org.coryjk.AdventOfCode.commons.math.AdventMath;

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

        // compute final answer by most common bits per column
        final int gammaRate = Integer.parseInt(mostCommonBitsPerColumn(bitMap, 1), 2);
        return gammaRate * invertBits(gammaRate) + "";
    }

    @Override
    public String solvePart2() {
        final int oxygenGeneratorRating = toDecimal(
                reduceBits(createBitMap(input), true));
        final int co2ScrubberRating =  toDecimal(
                reduceBits(createBitMap(input), false));

        return oxygenGeneratorRating * co2ScrubberRating + "";
    }

    private byte[] reduceBits(byte[][] bitMap, final boolean byMostCommonBits) {
        int col = 0;
        do {
            int filterBit = Character.getNumericValue(
                    // convert most common bit at designated column to number
                    mostCommonBitsPerColumn(bitMap, 1).charAt(col));
            if (!byMostCommonBits) {
                filterBit = filterBit == 1 ? 0 : 1;
            }

            // create new bit map
            bitMap = filterBytesByBit(bitMap, filterBit, col++);
        } while (bitMap.length > 1);
        return bitMap[0];
    }

    private int toDecimal(final byte[] bits) {
        int n = 0;
        for (int i = 0; i < bits.length; i++) {
            final int j = bits.length-1 - i;
            final int bit = bits[j];
            if (bit > 0) {
                n += AdventMath.simplePow(2, i);
            }
        }
        return n;
    }

    private byte[][] filterBytesByBit(final byte[][] bitMap, final int bit, final int i) {
        final byte[][] newBitMap = new byte[countMatchingBitsByIndex(bitMap, bit, i)][bitMap[0].length];
        int j = 0;
        for (final byte[] bytes : bitMap) {
            if (bytes[i] == bit) {
                newBitMap[j++] = bytes;
            }
        }
        return newBitMap;
    }

    private int countMatchingBitsByIndex(final byte[][] bitMap, final int bit, final int i) {
        int count = 0;
        for (final byte[] bytes : bitMap) {
            count += bytes[i] == bit ? 1 : 0;
        }
        return count;
    }

    private String mostCommonBitsPerColumn(final byte[][] bitMap, final int tieBreaker) {
        final StringBuilder mostCommonBitsPerColumn = new StringBuilder(bitMap[0].length);
        for (int i = 0; i < bitMap[0].length; i++) {
            int count = 0;
            for (final byte[] bytes : bitMap) {
                count += bytes[i];
            }
            if (bitMap.length % 2 == 0 && count == bitMap.length/2) {
                mostCommonBitsPerColumn.append(tieBreaker);
            } else {
                mostCommonBitsPerColumn.append(count > bitMap.length / 2 ? 1 : 0);
            }
        }
        return mostCommonBitsPerColumn.toString();
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
