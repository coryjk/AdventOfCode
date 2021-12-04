package org.coryjk.AdventOfCode.commons.math;

import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;

@Slf4j
public final class AdventMath {

    public static BigInteger lazyInverseMod(final long a, final long m) {
        return BigInteger.valueOf(a).modInverse(BigInteger.valueOf(m));
    }

    public static int simplePow(final int a, final int b) {
        int i = 0;
        int n = 1;
        while (i++ < b) {
            n *= a;
        }
        return n;
    }
}
