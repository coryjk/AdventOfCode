package org.coryjk.AdventOfCode.year2020.day04;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.coryjk.AdventOfCode.commons.InputReader;
import org.coryjk.AdventOfCode.commons.SolutionLogger;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

@Slf4j
@NoArgsConstructor
public final class PassportProcessing extends SolutionLogger {

    private Passport[] passports;

    /**
     * Initializes state by loading all required resources.
     */
    @Override
    public void feed() {
        final String[] input = InputReader.getStringInput(2020, 4);
        final List<Passport> passports = new LinkedList<>();

        Passport passport = new Passport();
        for (final String line : input) {
            if (line == null || StringUtils.isEmpty(line.trim())) {
                // begin building new passport
                passports.add(passport);
                passport = new Passport();
            } else {
                // continue building passport
                final String[] fields = line.split(" ");
                for (final String field : fields) {
                    passport.put(field.substring(0, 3), field.substring(4));
                }
            }
        }

        this.passports = new Passport[passports.size()];
        passports.toArray(this.passports);
    }

    /**
     * Returns solution to part 1 as a string.
     *
     * @return Answer.
     */
    @Override
    public String solvePart1() {
        return Integer.toString(countValidPassports(this::requiredFieldsPresent));
    }

    /**
     * Returns solution to part 2 as a string.
     *
     * @return Answer.
     */
    @Override
    public String solvePart2() {
        final Predicate<String[]> requirement = fields -> {
            if (requiredFieldsPresent(fields)) {
                // now go through each field requirement
                for (int ordinal = 0; ordinal < fields.length; ordinal++) {
                    final Passport.Field field = Passport.Field.values()[ordinal];
                    if (!Passport.Field.isValid(field, fields[ordinal])) {
                        return false;
                    }
                }
                // all fields passed, is valid
                return true;
            }
            return false;
        };
        return Integer.toString(countValidPassports(requirement));
    }

    private boolean requiredFieldsPresent(final String[] fields) {
        return Arrays.stream(fields).allMatch(Objects::nonNull)
                // see if only cid is missing
                || (Arrays.stream(fields).filter(Objects::nonNull).count() == fields.length-1
                    && fields[Passport.Field.CID.ordinal()] == null);
    }

    private int countValidPassports(final Predicate<String[]> passportRequirement) {
        int count = 0;
        for (final Passport passport : passports) {
            if (passport.isValidPassport(passportRequirement)) {
                count++;
            }
        }
        return count;
    }
}
