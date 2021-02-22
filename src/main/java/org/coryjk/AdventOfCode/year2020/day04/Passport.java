package org.coryjk.AdventOfCode.year2020.day04;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.function.Predicate;

@Slf4j
@NoArgsConstructor
public final class Passport {

    @RequiredArgsConstructor
    public enum Field {
        BYR("Birth Year"),
        IYR("Issue Year"),
        EYR("Expiration Year"),
        HGT("Height"),
        HCL("Hair Color"),
        ECL("Eye Color"),
        PID("Passport ID"),
        CID("Country ID");

        // for HGT
        private static final String INCH = "in";
        private static final String CM = "cm";

        // for HCL
        private static final char HCL_PREFIX = '#';
        private static final int HCL_LENGTH = 6;

        // for ECL
        private static final String[] ECL_VALID_COLORS = {"amb", "blu", "brn", "gry", "grn", "hzl", "oth"};

        // for PID
        private static final int PID_LENGTH = 9;

        private final String label;

        /**
         * Follows requirements:
         * <pre>
         * <li>byr (Birth Year) - four digits; at least 1920 and at most 2002.</li>
         * <li>iyr (Issue Year) - four digits; at least 2010 and at most 2020.</li>
         * <li>eyr (Expiration Year) - four digits; at least 2020 and at most 2030.</li>
         * <li>hgt (Height) - a number followed by either cm or in:
         *      If cm, the number must be at least 150 and at most 193.
         *      If in, the number must be at least 59 and at most 76.</li>
         * <li>hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f.</li>
         * <li>ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.</li>
         * <li>pid (Passport ID) - a nine-digit number, including leading zeroes.</li>
         * <li>cid (Country ID) - ignored, missing or not.</li>
         * </pre>
         *
         * @param field Field.
         * @param value Value.
         * @return True if field is valid, false otherwise.
         */
        static boolean isValid(final Field field, final String value) {
            if (field != null) {
                try {
                    switch (field) {
                        case BYR:
                            final int birthYear = Integer.parseInt(value);
                            return birthYear >= 1920 && birthYear <= 2002;
                        case IYR:
                            final int issueYear = Integer.parseInt(value);
                            return issueYear >= 2010 && issueYear <= 2020;
                        case EYR:
                            final int expirationYear = Integer.parseInt(value);
                            return expirationYear >= 2020 && expirationYear <= 2030;
                        case HGT:
                            // get units and number
                            final String unit = value.substring(value.length()-2);
                            final int height = Integer.parseInt(value.substring(0, value.length()-2));
                            // enforce height based on unit
                            if (CM.equalsIgnoreCase(unit)) {
                                return height >= 150 && height <= 193;
                            } else if (INCH.equalsIgnoreCase(unit)) {
                                return height >= 59 && height <= 76;
                            } else {
                                // no unit, invalid
                                return false;
                            }
                        case HCL:
                            return value.charAt(0) == HCL_PREFIX
                                    && value.length() == HCL_LENGTH+1
                                    // may only have 0-9 or a-f
                                    && value.substring(1).chars()
                                        .allMatch(ch -> (ch >= '0' && ch <= '9')
                                            || (ch >= 'a' && ch <= 'f'));
                        case ECL:
                            return Arrays.stream(ECL_VALID_COLORS).anyMatch(value::equalsIgnoreCase);
                        case PID:
                            return value.length() == PID_LENGTH && StringUtils.isNumeric(value);
                        case CID:
                            return true;
                        default:
                    }
                } catch (Exception ignored) { }
            }
            return false;
        }
    }

    final String[] passportFields = new String[Field.values().length];

    public boolean isValidPassport(@NonNull final Predicate<String[]> passportRequirement) {
        return passportRequirement.test(passportFields);
    }

    public void put(final String field, final String value) {
        if (field != null && value != null) {
            put(Field.valueOf(field.toUpperCase()), value);
        }
    }

    private void put(@NonNull final Field field, @NonNull final String value) {
        passportFields[field.ordinal()] = value;
    }
}
