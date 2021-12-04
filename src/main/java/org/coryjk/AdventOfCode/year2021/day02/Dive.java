package org.coryjk.AdventOfCode.year2021.day02;

import lombok.extern.slf4j.Slf4j;
import org.coryjk.AdventOfCode.commons.InputReader;
import org.coryjk.AdventOfCode.commons.SolutionLogger;
import org.coryjk.AdventOfCode.commons.math.Vector;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class Dive extends SolutionLogger {

    private static final String UP = "up";
    private static final String DOWN = "down";
    private static final String FORWARD = "forward";

    private String[] input;
    private List<Vector> vectors;

    @Override
    public void feed() {
        input = InputReader.getStringInput(2021, 2);
        vectors = Arrays.stream(input)
                .map(i -> i.split(" "))
                .map(split -> {
                    final int n = Integer.parseInt(split[1]);
                    return switch (split[0]) {
                        case UP -> new Vector(0, n, 0);
                        case DOWN -> new Vector(0, -n, 0);
                        case FORWARD -> new Vector(n, 0, 0);
                        default -> throw new IllegalArgumentException("Invalid directional input: " + split[0]);
                    };
                })
                .collect(Collectors.toList());
    }

    @Override
    public String solvePart1() {
        final Vector position = vectors.stream()
                .reduce(Vector::add)
                .orElse(null);
        if (position == null) {
            throw new IllegalStateException("Vector position evaluated to null");
        }
        return "" + Math.abs(position.getX() * position.getY());
    }

    @Override
    public String solvePart2() {
        int aim = 0;
        Vector position = new Vector(0, 0, 0);
        for (final Vector v : vectors) {
            // moving forward based on aim
            if (v.getX() > 0) {
                position = position.add(new Vector(v.getX(), v.getX() * aim, 0));
            }
            // changing aim
            else {
                aim += -v.getY();
            }
        }
        return "" + Math.abs(position.getX() * position.getY());
    }
}
