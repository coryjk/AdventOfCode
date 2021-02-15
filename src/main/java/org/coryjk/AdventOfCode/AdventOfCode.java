package org.coryjk.AdventOfCode;

import lombok.extern.slf4j.XSlf4j;
import org.coryjk.AdventOfCode.commons.InputReader;

import java.util.Arrays;

@XSlf4j
public final class AdventOfCode {
    public static void main(String[] args) {
       log.info(Arrays.toString(InputReader.getIntInput(InputReader.getPath(2020, 1))));
    }
}
