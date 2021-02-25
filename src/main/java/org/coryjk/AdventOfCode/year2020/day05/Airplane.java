package org.coryjk.AdventOfCode.year2020.day05;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
@NoArgsConstructor
public final class Airplane {
    private final static int ROWS = 128;
    private final static int COLUMNS = 8;

    private final static int ROW_MULTIPLIER = 8;

    @RequiredArgsConstructor
    enum Side {
        FRONT(true), BACK(false), LEFT(true), RIGHT(false);

        private final boolean lowerHalf;

        static Side getSide(final char s) {
            return Arrays.stream(Side.values())
                    .filter(side -> s == side.name().charAt(0))
                    .findFirst()
                    .orElse(null);
        }
    }

    public int getSeatId(final String partitioning) {
        final int[] ranges = new int[]{0, ROWS-1, 0, COLUMNS-1};

        // keeps iterating until pivot point found
        int i = 0;
        while (isRowMovement(Side.getSide(partitioning.charAt(i)))) {
            i++;
        }

        return ROW_MULTIPLIER*seekSeat(ranges, partitioning.substring(0, i))
                + seekSeat(ranges, partitioning.substring(i));
    }

    private int seekSeat(final int[] ranges, final String partitioning) {
        final Side side = Side.getSide(partitioning.charAt(0));
        if (partitioning.length() <= 1) {
            return chooseSeat(ranges, side);
        }
        // transform based on row vs. column movement
        final int l = offset(side);
        final int r = offset(side)+1;

        // move bounds
        int lower = ranges[l];
        int upper = ranges[r];
        if (side.lowerHalf) {
            upper = lower + (upper - lower)/2;
        } else {
            lower += (upper - lower)/2 + 1;
        }

        // update
        ranges[l] = lower;
        ranges[r] = upper;

        return seekSeat(ranges, partitioning.substring(1));
    }

    private int chooseSeat(final int[] ranges, final Side side) {
        return ranges[side.lowerHalf ? offset(side) : offset(side)+1];
    }

    private boolean isRowMovement(final Side side) {
        switch (side) {
            case FRONT:
            case BACK:
                return true;
            case LEFT:
            case RIGHT:
                return false;
            default:
        }
        return false;
    }

    private int offset(final Side side) {
        return isRowMovement(side) ? 0 : 2;
    }
}
