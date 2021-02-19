import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class StringCalculatorTest {

    //Test case to check empty string, if yes return 0
    @Test
    public void addsEmptyStringTo0() {
        assertThat(StringCalculator.add(""), is(0));
    }

    //Test case to check whether whether it is a single number, if yes return number itself
    @Test
    public void addsSingleNumberToItself() {
        assertThat(StringCalculator.add("5"), is(5));
        assertThat(StringCalculator.add("42"), is(42));
    }

    // Test case to return sum if we are passing two numbers
    @Test
    public void addsTwoNumbersSeparatedByComma() {
        assertThat(StringCalculator.add("1,2"), is(3));
        assertThat(StringCalculator.add("1,3"), is(4));
    }

    // Test case to handle more than 2 numbers which are separated by Comma
    @Test
    public void addThreeNumbersSeparatedByComma() {
        assertThat(StringCalculator.add("1,2,3"), is(6));
    }

    // Test case to handle new line
    @Test
    public void addsNumbersDelimitedByNewline() {
        assertThat(StringCalculator.add("1\n2"), is(3));
    }

    // Test case two handle numbers by new line as well as by comma
    @Test
    public void addsNumbersDelimitedByCommaOrNewline() {
        assertThat(StringCalculator.add("1,2\n3"), is(6));
    }

    // Test case to handle delimiter
    @Test
    public void usesDelimiterSpecified() {
        assertThat(StringCalculator.add("//;\n1;2"), is(3));
        assertThat(StringCalculator.add("//.\n2.3.1"), is(6));
    }

    // Test case to throw exception for negative number
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void throwsOnNegativeNumber() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("negative number: -3");

        StringCalculator.add("-3");
    }

    // Test case to throw exception for all negative numbers present in input
    @Test
    public void throwsOnNegativeNumbersWithAllNumbersInExceptionMessage() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("negative number: -3,-5,-13");

        StringCalculator.add("1,-3,5,-5,-13");
    }

    // Test case if number i greater than 1000 then map accordingly
    @Test
    public void mapsNumbersAbove1000ToLastThreeDigits() {
        assertThat(StringCalculator.add("1002"), is(2));
        assertThat(StringCalculator.add("1040,10002"), is(42));
    }

    // Test case to accept any length delimiter
    @Test
    public void acceptsDelimiterOfArbitraryLength() {
        assertThat(StringCalculator.add("//[***]\n1***2***3"), is(6));
    }

    // Test case to accept multiple length delimiter
    @Test
    public void acceptsMultipleDelimiters() {
        assertThat(StringCalculator.add("//[-][;]\n1-2;3"), is(6));
        assertThat(StringCalculator.add("//[--][...]\n2--3...4"), is(9));
    }

}
