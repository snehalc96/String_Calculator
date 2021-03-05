import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class StringCalculatorTest {
    @Mock
    private DBLogger dbLogger;
    StringCalculator stringCalculator;
    //Test case to verify stringCalculator.add(str) is called dblogger.log(str) is also called
    @Before
    public void setUp(){
        dbLogger = Mockito.mock(DBLogger.class);
        stringCalculator= new StringCalculator("","",dbLogger);
    }
    @Test
    public void testObjectCallCount(){
        stringCalculator.add("3");
        Mockito.verify(dbLogger).log("3");
    }
    //Test case to check empty string, if yes return 0
    @Test
    public void addsEmptyStringTo0() {
        assertThat(stringCalculator.add(""), is(0));
    }

    //Test case to check whether whether it is a single number, if yes return number itself
    @Test
    public void addsSingleNumberToItself() {
        assertThat(stringCalculator.add("5"), is(5));
        assertThat(stringCalculator.add("42"), is(42));
    }

    // Test case to return sum if we are passing two numbers
    @Test
    public void addsTwoNumbersSeparatedByComma() {
        assertThat(stringCalculator.add("1,2"), is(3));
        assertThat(stringCalculator.add("1,3"), is(4));
    }

    // Test case to handle more than 2 numbers which are separated by Comma
    @Test
    public void addThreeNumbersSeparatedByComma() {
        assertThat(stringCalculator.add("1,2,3"), is(6));
    }

    // Test case to handle new line
    @Test
    public void addsNumbersDelimitedByNewline() {
        assertThat(stringCalculator.add("1\n2"), is(3));
    }

    // Test case two handle numbers by new line as well as by comma
    @Test
    public void addsNumbersDelimitedByCommaOrNewline() {
        assertThat(stringCalculator.add("1,2\n3"), is(6));
    }

    // Test case to handle delimiter
    @Test
    public void usesDelimiterSpecified() {
        assertThat(stringCalculator.add("//;\n1;2"), is(3));
        assertThat(stringCalculator.add("//.\n2.3.1"), is(6));
    }

    // Test case to throw exception for negative number
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void throwsOnNegativeNumber() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("negative number: -3");

        stringCalculator.add("-3");
    }

    // Test case to throw exception for all negative numbers present in input
    @Test
    public void throwsOnNegativeNumbersWithAllNumbersInExceptionMessage() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("negative number: -3,-5,-13");

        stringCalculator.add("1,-3,5,-5,-13");
    }

    // Test case if number i greater than 1000 then map accordingly
    @Test
    public void mapsNumbersAbove1000ToLastThreeDigits() {
        assertThat(stringCalculator.add("1002"), is(2));
        assertThat(stringCalculator.add("1040,10002"), is(42));
    }

    // Test case to accept any length delimiter
    @Test
    public void acceptsDelimiterOfArbitraryLength() {
        assertThat(stringCalculator.add("//[***]\n1***2***3"), is(6));
    }

    // Test case to accept multiple length delimiter
    @Test
    public void acceptsMultipleDelimiters() {
        assertThat(stringCalculator.add("//[-][;]\n1-2;3"), is(6));
        assertThat(stringCalculator.add("//[--][...]\n2--3...4"), is(9));
    }

}
