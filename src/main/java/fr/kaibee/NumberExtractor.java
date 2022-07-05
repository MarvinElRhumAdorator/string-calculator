package fr.kaibee;

import java.util.Arrays;
import java.util.StringJoiner;
import java.util.regex.Pattern;

/**
 * Class extracted after making all test pass, to enforce SRP => Responsibility = extract numbers from the charSequence
 */
public class NumberExtractor {
    private static final String CUSTOM_DELIMITER_MARKER = "//";
    private static final String DEFAULT_DELIMITERS = "[,\n]";
    private static final char OPENING_BRACKET = '[';
    private static final char CLOSING_BRACKET = ']';

    public int[] extractNumbers(String input) {
        String[] numbers = extractStringNumbers(input);
        return Arrays.stream(numbers)
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    private String[] extractStringNumbers(String input) {
        if (input.startsWith(CUSTOM_DELIMITER_MARKER)) {
            String delimiters = extractDelimiters(input);
            return getSecondLine(input).split(delimiters);
        }
        return input.split(DEFAULT_DELIMITERS);
    }

    private String extractDelimiters(String input) {
        if (input.charAt(CUSTOM_DELIMITER_MARKER.length()) == OPENING_BRACKET) {
            return extractCustomDelimiters(input);
        }
        return quotesCharAt(input, CUSTOM_DELIMITER_MARKER.length());
    }

    private String extractCustomDelimiters(String input) {
        StringJoiner joiner = new StringJoiner("|");
        int bracketIndex = CUSTOM_DELIMITER_MARKER.length();
        while ((bracketIndex = input.indexOf(OPENING_BRACKET, bracketIndex)) != -1) {
            joiner.add(extractDelimiterFromBrackets(input, bracketIndex));
            bracketIndex++;
        }
        return joiner.toString();
    }

    private String getSecondLine(String input) {
        return input.lines().toList().get(1);
    }

    private String extractDelimiterFromBrackets(String input, int openingbracketIndex) {
        String delimiter = input.substring(openingbracketIndex + 1, input.indexOf(CLOSING_BRACKET, openingbracketIndex));
        return Pattern.quote(delimiter);
    }

    private String quotesCharAt(String input, int fromIndex) {
        return Pattern.quote(String.valueOf(input.charAt(fromIndex)));
    }
}
