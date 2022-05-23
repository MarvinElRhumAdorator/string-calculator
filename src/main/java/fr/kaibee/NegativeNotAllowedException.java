package fr.kaibee;

import java.util.List;

public class NegativeNotAllowedException extends IllegalArgumentException {
    public NegativeNotAllowedException(List<Integer> negatives) {
        super("Negatives not allowed : " + negatives.toString());
    }

    public NegativeNotAllowedException() {
        super("Negatives not allowed");
    }
}
