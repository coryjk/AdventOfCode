package org.coryjk.AdventOfCode;

import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.BasicConfigurator;
import org.coryjk.AdventOfCode.year2020.day01.ReportRepair;
import org.coryjk.AdventOfCode.year2020.day02.PasswordPhilosophy;
import org.coryjk.AdventOfCode.year2020.day03.TobogganTrajectory;

@Slf4j
public final class AdventOfCode {
    public static void main(String[] args) {
        BasicConfigurator.configure();
        new ReportRepair().solve();
        new PasswordPhilosophy().solve();
        new TobogganTrajectory().solve();
    }
}
