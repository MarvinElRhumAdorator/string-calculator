package fr.kaibee;

import java.util.ArrayList;
import java.util.List;

public class StringCalculator {

    public static final int MAX_NUMBER = 1000;
    public static final int EMPTY_RESULT = 0;
    private final NumberExtractor numberExtractor;

    public StringCalculator(NumberExtractor numberExtractor) {
        this.numberExtractor = numberExtractor;
    }

    public int add(String input) {
        if (input.isEmpty()) {
            return EMPTY_RESULT;
        }

        int[] numbers = numberExtractor.extractNumbers(input);
        int sum = 0;
        List<Integer> negativeNumbers = new ArrayList<>();

        // We could have end up with separation between the validation phase (no negative) and the filter phase (currentNumber <= 1000)
        // Here it's not the case and it's not a big deal as long as code is expressive
        // (and it's more efficient yeeeaaah)
        for (int currentNumber : numbers) {
            if (currentNumber < 0) {
                negativeNumbers.add(currentNumber);
            }

            if (currentNumber <= MAX_NUMBER) {
                sum += currentNumber;
            }
        }
        assertThereIsNo(negativeNumbers);
        return sum;
    }

    private void assertThereIsNo(List<Integer> negativeNumbers) {
        if (negativeNumbers.isEmpty()) {
            return;
        }
        if (negativeNumbers.size() > 1) {
            throw new NegativeNotAllowedException(negativeNumbers);
        }
        throw new NegativeNotAllowedException();
    }
}
