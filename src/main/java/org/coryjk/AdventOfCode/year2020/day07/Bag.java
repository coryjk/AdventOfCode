package org.coryjk.AdventOfCode.year2020.day07;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.coryjk.AdventOfCode.commons.Lang;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

@Slf4j
public final class Bag {
    private static final String SEPARATOR = "contain";
    private static final String BAGS_DELIMITER = ",";
    private static final String IGNORE = "no other bags.";

    @Getter private final String color;
    private final Map<String, Integer> bags = new TreeMap<>();

    /**
     * Initializes bag provided a rule string in the expected format:
     * <br>
     * <code>
     *     "[color] bags contain \d+ [color] [bag|bags], \d+ [color] [bag|bags], ..."
     *     or
     *     "[color] bags contain no other bags."
     * </code>
     *
     * @param rule Rule for a given bag color.
     */
    public Bag(final String rule) {
        final String[] predicate = rule.split(SEPARATOR);
        color = debag(predicate[0].trim()).toLowerCase();

        // no bags, no need to continue
        if (predicate.length < 2 || IGNORE.equalsIgnoreCase(predicate[1].trim())) {
            return;
        }

        // parse listed bags
        parseAndCatalogBags(predicate[1]);
    }

    public int getBagCapacity(final String color) {
        return bags.getOrDefault(color, 0);
    }

    public Set<String> getBagColors() {
        return bags.keySet();
    }

    public int size() {
        return bags.size();
    }

    @Override
    public boolean equals(final Object o) {
        return o instanceof Bag && color.equalsIgnoreCase(((Bag) o).getColor());
    }

    @Override
    public String toString() {
        return color + " bag";
    }

    private void parseAndCatalogBags(final String bagsList) {
        for (final String bag : bagsList.split(BAGS_DELIMITER)) {
            final String trimmed = bag.trim();
            final String color = trimmed.substring(
                    // index of first space after number
                    trimmed.indexOf(Lang.SPACE),
                    // index of last space before "bags"
                    trimmed.lastIndexOf(Lang.SPACE)+1).trim();

            final int count = Integer.parseInt(trimmed.substring(0, trimmed.indexOf(Lang.SPACE)));
            bags.put(color.toLowerCase().trim(), count);
        }
    }

    private String debag(final String s) {
        return s.substring(0, s.lastIndexOf(Lang.SPACE));
    }
}
