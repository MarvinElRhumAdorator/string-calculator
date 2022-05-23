import fr.kaibee.NegativeNotAllowedException;
import fr.kaibee.NumberExtractor;
import fr.kaibee.StringCalculator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringCalculatorTest {

    private StringCalculator stringCalculator;

    @BeforeEach
    void setUp() {
        stringCalculator = new StringCalculator(new NumberExtractor());
    }

    @Test
    void should_return_0_when_empty() {
        // Given
        String input = "";

        // When
        int result = stringCalculator.add(input);

        // Then
        assertThat(result).isEqualTo(0);
    }

    @Test
    void should_return_n_when_n() {
        assertThat(stringCalculator.add("1")).isEqualTo(1);
        assertThat(stringCalculator.add("2")).isEqualTo(2);
    }

    @Test
    void should_return_sum_of_n_and_m_when_n_and_m() {
        assertThat(stringCalculator.add("1,2")).isEqualTo(3);
        assertThat(stringCalculator.add("1,3")).isEqualTo(4);
        assertThat(stringCalculator.add("2,3")).isEqualTo(5);
    }

    @Test
    void should_handle_any_amount_of_numbers() {
        assertThat(stringCalculator.add("2,3,1")).isEqualTo(6);
        assertThat(stringCalculator.add("2,3,4")).isEqualTo(9);
        assertThat(stringCalculator.add("2,3,4,5")).isEqualTo(14);
    }

    @Test
    void should_handle_newline_as_separators() {
        assertThat(stringCalculator.add("2\n3,1")).isEqualTo(6);
    }

    @Test
    void should_handle_custom_delimiters() {
        assertThat(stringCalculator.add("//;\n1;2")).isEqualTo(3);
        assertThat(stringCalculator.add("//;\n2;2")).isEqualTo(4);
        assertThat(stringCalculator.add("//*\n2*2")).isEqualTo(4);
    }

    @Test
    void should_throw_exception_when_negative_number_present() {
        NegativeNotAllowedException exception = Assertions.assertThrows(NegativeNotAllowedException.class,
                () -> stringCalculator.add("//;\n1;-2"));
        assertThat(exception.getMessage()).isEqualTo("Negatives not allowed");
    }

    @Test
    void should_throw_exception_with_list_of_negatives_when_multiple_negative_numbers_present() {
        NegativeNotAllowedException exception = Assertions.assertThrows(NegativeNotAllowedException.class,
                () -> stringCalculator.add("//;\n1;-2;-3"));
        assertThat(exception.getMessage()).isEqualTo("Negatives not allowed : [-2, -3]");
    }

    @Test
    void should_ignore_numbers_bigger_than_1000() {
        assertThat(stringCalculator.add("//;\n1001;2")).isEqualTo(2);
        assertThat(stringCalculator.add("//;\n1002;2")).isEqualTo(2);
    }

    @Test
    void should_accept_delimiters_of_any_length() {
        assertThat(stringCalculator.add("//[***]\n4***2***5")).isEqualTo(11);
        assertThat(stringCalculator.add("//[++++]\n4++++2++++6")).isEqualTo(12);
    }

    @Test
    void should_handle_multiple_delimiters() {
        assertThat(stringCalculator.add("//[**][+]\n4+2**7")).isEqualTo(13);
        assertThat(stringCalculator.add("//[**][+]\n5+2**7")).isEqualTo(14);
        assertThat(stringCalculator.add("//[**][+][^^]\n3^^2+2**7")).isEqualTo(14);
        assertThat(stringCalculator.add("//[**][+][^^][$$$]\n3^^2+2**7$$$5")).isEqualTo(19);
    }
}
