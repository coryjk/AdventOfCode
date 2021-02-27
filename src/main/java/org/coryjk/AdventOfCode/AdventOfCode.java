package org.coryjk.AdventOfCode;

import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.BasicConfigurator;
import org.coryjk.AdventOfCode.commons.SolutionLogger;
import org.coryjk.AdventOfCode.year2020.day01.ReportRepair;
import org.coryjk.AdventOfCode.year2020.day02.PasswordPhilosophy;
import org.coryjk.AdventOfCode.year2020.day03.TobogganTrajectory;
import org.coryjk.AdventOfCode.year2020.day04.PassportProcessing;
import org.coryjk.AdventOfCode.year2020.day05.BinaryBoarding;
import org.coryjk.AdventOfCode.year2020.day06.CustomCustoms;
import org.coryjk.AdventOfCode.year2020.day07.HandyHaversacks;

@Slf4j
public final class AdventOfCode {
    public static void main(String[] args) {
        BasicConfigurator.configure();
        solve(
                ReportRepair.class,
                PasswordPhilosophy.class,
                TobogganTrajectory.class,
                PassportProcessing.class,
                BinaryBoarding.class,
                CustomCustoms.class,
                HandyHaversacks.class
        );
    }

    @SafeVarargs
    private static void solve(final Class<? extends SolutionLogger> ... solutions) {
        for (final Class<? extends SolutionLogger> solution : solutions) {
            try {
                solution.getConstructor().newInstance().solve();
            } catch (Exception exception) {
                log.error("Issue calling solution driver for " + solution.getName(), exception);
            }
        }
    }
}
