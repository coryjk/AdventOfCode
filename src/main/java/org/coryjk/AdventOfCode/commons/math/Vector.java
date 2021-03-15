package org.coryjk.AdventOfCode.commons.math;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@RequiredArgsConstructor
public class Vector {
    @Getter private final int x, y, z;

    public Vector add(final Vector v) {
        return new Vector(getX()+v.getX(), getY()+v.getY(), getZ()+v.getZ());
    }

    public Vector subtract(final Vector v) {
        return new Vector(getX()-v.getX(), getY()-v.getY(), getZ()-v.getZ());
    }

    public Vector multiply(final int c) {
        return new Vector(c*getX(), c*getY(), c*getZ());
    }

    public int manhattanDistance() {
        return Math.abs(x) + Math.abs(y) + Math.abs(z);
    }
}
