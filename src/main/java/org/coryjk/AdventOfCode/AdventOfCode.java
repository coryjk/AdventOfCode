package org.coryjk.AdventOfCode;

import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.BasicConfigurator;
import org.coryjk.AdventOfCode.year2020.day01.ReportRepair;
import org.coryjk.AdventOfCode.year2020.day02.PasswordPhilosophy;
import org.coryjk.AdventOfCode.year2020.day03.TobogganTrajectory;
import org.coryjk.AdventOfCode.year2020.day04.PassportProcessing;
import org.coryjk.AdventOfCode.year2020.day05.BinaryBoarding;

@Slf4j
public final class AdventOfCode {
    public static void main(String[] args) {
        BasicConfigurator.configure();
        new ReportRepair().solve();
        new PasswordPhilosophy().solve();
        new TobogganTrajectory().solve();
        new PassportProcessing().solve();
        new BinaryBoarding().solve();
    }
}
