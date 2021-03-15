package org.coryjk.AdventOfCode.year2020.day12;

import lombok.extern.slf4j.Slf4j;
import org.coryjk.AdventOfCode.commons.InputReader;
import org.coryjk.AdventOfCode.commons.SolutionLogger;
import org.coryjk.AdventOfCode.commons.math.Vector;

import java.util.stream.IntStream;

@Slf4j
public final class RainRisk extends SolutionLogger {
    private static final Vector WAYPOINT_OFFSET = new Vector(10, 1, 0);

    private Ship.Action[] actions;
    private int[] values;

    /**
     * Initializes state by loading all required resources.
     */
    @Override
    public void feed() {
        final String[] input = InputReader.getStringInput(2020, 12);
        actions = new Ship.Action[input.length];
        values = new int[input.length];

        for (int i = 0; i < input.length; i++) {
            actions[i] = Ship.Action.getAction(input[i]);
            values[i] = Ship.Action.getValue(input[i]);
        }
    }

    /**
     * Returns solution to part 1 as a string.
     *
     * @return Answer.
     */
    @Override
    public String solvePart1() {
        final Ship ship = new Ship();
        IntStream.range(0, actions.length).forEach(i -> ship.processAction(actions[i], values[i]));
        return Integer.toString(ship.getPosition().manhattanDistance());
    }

    /**
     * Returns solution to part 2 as a string.
     *
     * @return Answer.
     */
    @Override
    public String solvePart2() {
        final Ship ship = new Ship();
        final Ship waypoint = new Ship();
        waypoint.moveTo(WAYPOINT_OFFSET);
        IntStream.range(0, actions.length).forEach(i -> {
            final Vector waypointPosition = waypoint.getPosition();
            final Vector shipPosition = ship.getPosition();

            // treat the ship like a waypoint
            switch (actions[i]) {
                // original behavior still applies
                case NORTH:
                case SOUTH:
                case EAST:
                case WEST:
                    waypoint.processAction(actions[i], values[i]);
                    break;
                /*
                 * Move ship forward towards waypoint and update its position. Waypoint
                 * may remain unchanged, as it is always relative to ship.
                 */
                case FORWARD:
                    ship.moveTo(shipPosition.add(waypointPosition.multiply(values[i])));
                    break;
                /*
                 * Need to rotate waypoint relative to the ship.
                 * (x', y') = (x*cos(t) - y*sin(t), x*sin(t) + y*cos(t))
                 */
                case LEFT:
                case RIGHT:
                    final int transformedDegrees = Ship.Action.LEFT.equals(actions[i])
                            ? values[i]%360
                            : 360 - values[i]%360;
                    final int cos = quickCos(transformedDegrees);
                    final int sin = quickSin(transformedDegrees);
                    final Vector rotated = new Vector(
                            waypointPosition.getX()*cos - waypointPosition.getY()*sin,
                            waypointPosition.getX()*sin + waypointPosition.getY()*cos,
                            0);
                    waypoint.moveTo(rotated);
            }
        });
        return Integer.toString(ship.getPosition().manhattanDistance());
    }

    private int quickCos(final int degrees) {
        switch (degrees) {
            case 0:
                return 1;
            case 90:
            case 270:
                return 0;
            case 180:
                return -1;
        }
        throw new IllegalArgumentException("Invalid angle given for simple cos: " + degrees);
    }

    private int quickSin(final int degrees) {
        switch (degrees) {
            case 0:
            case 180:
                return 0;
            case 90:
                return 1;
            case 270:
                return -1;
        }
        throw new IllegalArgumentException("Invalid angle given for simple sin: " + degrees);
    }
}
