package org.coryjk.AdventOfCode.year2020.day02;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.coryjk.AdventOfCode.commons.InputReader;
import org.coryjk.AdventOfCode.commons.SolutionLogger;

import java.util.function.Predicate;

@Slf4j
public final class PasswordPhilosophy extends SolutionLogger {
    private static final String POLICY_AND_PASSWORD_DELIMITER = ": ";

    private String[] input;

    @Override
    public void feed() {
        input = InputReader.getStringInput(2020, 2);
    }

    @Override
    public String solvePart1() {
        int count = 0;
        for (final String line : input) {
            if (isValidPasswordPart1(line)) {
                count++;
            }
        }
        return Integer.toString(count);
    }

    @Override
    public String solvePart2() {
        int count = 0;
        for (final String line : input) {
            if (iSValidPasswordPart2(line)) {
                count++;
            }
        }
        return Integer.toString(count);
    }

    private int[] policyNumbers(final String policy) {
        final String[] numbersAsStrings = policy.substring(0, policy.length()-1)
                .trim()
                .split("-");
        return new int[] {Integer.parseInt(numbersAsStrings[0]), Integer.parseInt(numbersAsStrings[1])};
    }

    private boolean isValidPasswordPart1(final String line) {
        final String[] policyAndPassword = line.split(POLICY_AND_PASSWORD_DELIMITER);
        if (policyAndPassword.length != 2) {
            throw new IllegalStateException("Unexpected format for policy and password line: " + line);
        }
        return getRangePolicy(policyAndPassword[0]).test(policyAndPassword[1]);
    }

    private boolean iSValidPasswordPart2(final String line) {
        final String[] policyAndPassword = line.split(POLICY_AND_PASSWORD_DELIMITER);
        if (policyAndPassword.length != 2) {
            throw new IllegalStateException("Unexpected format for policy and password line: " + line);
        }
        return getXorPolicy(policyAndPassword[0]).test(policyAndPassword[1]);
    }

    private Predicate<String> getRangePolicy(final String policy) {
        final char target = policy.charAt(policy.length()-1);
        final int[] policyNumbers = policyNumbers(policy);
        return password ->  {
            final int occurrences = StringUtils.countMatches(password, target);
            return occurrences >= policyNumbers[0] && occurrences <= policyNumbers[1];
        };
    }

    private Predicate<String> getXorPolicy(final String policy) {
        final char target = policy.charAt(policy.length()-1);
        final int[] policyNumbers = policyNumbers(policy);
        return password -> (password.charAt(policyNumbers[0]-1) == target)
                ^ (policyNumbers[1]-1 < password.length() && password.charAt(policyNumbers[1]-1) == target);
    }
}
