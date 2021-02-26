package org.coryjk.AdventOfCode.year2020.day06;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.coryjk.AdventOfCode.commons.InputReader;
import org.coryjk.AdventOfCode.commons.SolutionLogger;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Slf4j
public final class CustomCustoms extends SolutionLogger {
    private final List<Group> groups = new LinkedList<>();

    /**
     * Initializes state by loading all required resources.
     */
    @Override
    public void feed() {
        groups.clear();
        final String[] input = InputReader.getStringInput(2020, 6);

        Group group = new Group();
        for (final String answers : input) {
            if (StringUtils.isEmpty(answers)) {
                groups.add(group);
                group = new Group();
            } else {
                group.withAllAnswers(answers.trim());
            }
        }
        // in case hanging last element
        if (!group.getAllAnswers().isEmpty()) {
            groups.add(group);
        }
    }

    /**
     * Returns solution to part 1 as a string.
     *
     * @return Answer.
     */
    @Override
    public String solvePart1() {
        int sum = 0;
        final Set<Character> questions = new HashSet<>();
        for (final Group group : groups) {
            group.getAllAnswers().forEach(answers -> {
                for (final char answer : answers.toCharArray()) {
                    questions.add(answer);
                }
            });
            // accumulate and reset
            sum += questions.size();
            questions.clear();
        }
        return Integer.toString(sum);
    }

    /**
     * Returns solution to part 2 as a string.
     *
     * @return Answer.
     */
    @Override
    public String solvePart2() {
        int sum = 0;
        final Set<Character> questions = new HashSet<>();
        for (final Group group : groups) {
            group.getAllAnswers().forEach(answers -> {
                for (final char answer : answers.toCharArray()) {
                    questions.add(answer);
                }
            });
            // now that question set found, check questions everybody answered
            for (final char question : questions) {
                // for a given question, increment if all answers contain that question
                if (group.getAllAnswers().stream()
                        .allMatch(answer -> answer.indexOf(question) >= 0)) {
                    sum++;
                }
            }
            questions.clear();
        }
        return Integer.toString(sum);
    }

    @NoArgsConstructor
    static final class Group {
        @Getter private final List<String> allAnswers = new LinkedList<>();

        public Group withAllAnswers(final String answer) {
            allAnswers.add(answer);
            return this;
        }
    }
}
