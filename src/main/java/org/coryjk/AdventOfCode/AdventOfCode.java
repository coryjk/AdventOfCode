package org.coryjk.AdventOfCode;

import lombok.extern.slf4j.XSlf4j;
import org.apache.log4j.BasicConfigurator;
import org.coryjk.AdventOfCode.year2020.day01.ReportRepair;

@XSlf4j
public final class AdventOfCode {
    public static void main(String[] args) {
        BasicConfigurator.configure();
        new ReportRepair().solveAndLog();
    }
}
