package org.coryjk.AdventOfCode.commons;

import lombok.extern.slf4j.XSlf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@XSlf4j
public final class InputReader {
    private static final String PATH_FORMAT = "%s/%s";

    public static String getPath(final int year, final int day) {
        return String.format(PATH_FORMAT, year,
                StringUtils.leftPad(Integer.toString(day), 2, '0'));
    }

    public static Integer[] getIntInput(final String path) {
        return Arrays.stream(getResourceBuffer(path))
                .map(Integer::parseInt)
                .collect(Collectors.toList())
                .toArray(Integer[]::new);
    }

    public static String[] getStringInput(final String path) {
        return getResourceBuffer(path);
    }

    private static String[] getResourceBuffer(final String path) {
        final URL resource = InputReader.class.getClassLoader().getResource(path);
        if (resource != null) {
            try {
                final File inputSource = new File(resource.getFile());
                final BufferedReader reader = new BufferedReader(new FileReader(inputSource));

                String line;
                final List<String> lines = new LinkedList<>();
                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }
                reader.close();
                return (String[]) lines.toArray();
            } catch (IOException exception) {
                log.error("Exception thrown while getting resource " + path, exception);
            }
        }
        return new String[]{};
    }
}
