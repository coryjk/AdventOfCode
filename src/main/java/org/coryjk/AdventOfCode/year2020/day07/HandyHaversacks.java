package org.coryjk.AdventOfCode.year2020.day07;

import lombok.extern.slf4j.Slf4j;
import org.coryjk.AdventOfCode.commons.InputReader;
import org.coryjk.AdventOfCode.commons.SolutionLogger;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Predicate;

@Slf4j
public final class HandyHaversacks extends SolutionLogger {
    private static final String SHINY_GOLD = "shiny gold";

    private Bag[] rules;
    private final Map<String, Integer> colorToRuleIndex = new TreeMap<>();

    /**
     * Initializes state by loading all required resources.
     */
    @Override
    public void feed() {
        final String[] input = InputReader.getStringInput(2020, 7);

        // create bags and store the index associated by color
        rules = new Bag[input.length];
        for (int i = 0; i < rules.length; i++) {
            rules[i] = new Bag(input[i]);
            colorToRuleIndex.put(rules[i].getColor(), i);
        }
    }

    /**
     * Returns solution to part 1 as a string.
     *
     * @return Answer.
     */
    @Override
    public String solvePart1() {
        final Predicate<String> hasShinyGoldBag = SHINY_GOLD::equalsIgnoreCase;
        return Integer.toString((int) Arrays.stream(rules)
                .filter(bag -> countBagsThatMeetCriteria(bag, hasShinyGoldBag) > 0)
                .count());
    }

    /**
     * Returns solution to part 2 as a string.
     *
     * @return Answer.
     */
    @Override
    public String solvePart2() {
        final Bag shinyGoldBag = getBagByColor(SHINY_GOLD);
        return Integer.toString(countBagsThatMeetCriteria(shinyGoldBag, bag -> true));
    }

    /**
     * Recursively counts the total number of bags that pass the input
     * predicate by bag color.
     *
     * @param bag Bag.
     * @param countingCriteria Predicate by bag color.
     * @return Count of all bags whose color passes the input predicate.
     */
    private int countBagsThatMeetCriteria(final Bag bag, final Predicate<String> countingCriteria) {
        if (bag.size() == 0) {
            return 0;
        }
        // get all bags that match criteria
        int count = 0;
        for (final String color : bag.getBagColors()) {
            final int bagColorCapacity = bag.getBagCapacity(color);
            final int criteriaCount = countingCriteria.test(color) ? bagColorCapacity : 0;

            // add all bags at current recursion or "bag" level that pass the input predicate.
            count += criteriaCount + bagColorCapacity*countBagsThatMeetCriteria(getBagByColor(color), countingCriteria);
        }
        return count;
    }

    /**
     * @param color Bag color.
     * @return {@link Bag} corresponding to input color.
     */
    private Bag getBagByColor(final String color) {
        return rules[colorToRuleIndex.get(color)];
    }
}
