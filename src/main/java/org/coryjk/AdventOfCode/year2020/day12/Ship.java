package org.coryjk.AdventOfCode.year2020.day12;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.coryjk.AdventOfCode.commons.math.Vector;

@Slf4j
@NoArgsConstructor
public final class Ship {
    enum Action {
        NORTH,
        SOUTH,
        EAST,
        WEST,
        LEFT,
        RIGHT,
        FORWARD;

        public static Action getAction(final String instruction) {
            for (final Action action : Action.values()) {
                if (action.name().charAt(0) == instruction.charAt(0)) {
                    return action;
                }
            }
            throw new IllegalArgumentException("Invalid instruction input, no matching action: " + instruction);
        }

        public static int getValue(final String instruction) {
            return Integer.parseInt(instruction.substring(1));
        }
    }

    @RequiredArgsConstructor
    enum Direction {
        NORTH(Action.NORTH, new Vector(0, 1, 0)),
        EAST(Action.EAST,   new Vector(1, 0, 0)),
        SOUTH(Action.SOUTH, new Vector(0, -1, 0)),
        WEST(Action.WEST,   new Vector(-1, 0, 0));

        private final Action action;
        private final Vector vector;

        public Direction turn(final Action action, final int degrees) {
            if (degrees % 90 != 0) {
                throw new IllegalArgumentException("Invalid turning angle given: " + degrees);
            }
            if (!Action.LEFT.equals(action) && !Action.RIGHT.equals(action)) {
                throw new IllegalArgumentException("Invalid action given: " + action);
            }
            final int N = Direction.values().length;
            final int offset = (Action.RIGHT.equals(action) ? 1 : -1) * degrees/90;
            final int resultantOrdinal = (ordinal() + offset) % N;
            return Direction.values()[resultantOrdinal >= 0 ? resultantOrdinal : N + resultantOrdinal];
        }

        public Vector apply(final Vector position, final int value) {
            return position.add(vector.multiply(value));
        }
    }

    @Getter private Vector position = new Vector(0, 0, 0);
    @Getter private Direction direction = Direction.EAST;

    public void processAction(final Action action, final int value) {
        switch (action) {
            case NORTH:
            case SOUTH:
            case EAST:
            case WEST:
                position = Direction.valueOf(action.name()).apply(position, value);
                return;
            case LEFT:
            case RIGHT:
                direction = direction.turn(action, value);
                return;
            case FORWARD:
                position = direction.apply(position, value);
                return;
            default:
        }
        throw new IllegalArgumentException("Unrecognized action provided: " + action);
    }

    public void moveTo(final Vector vector) {
        position = vector;
    }
}
