package util;

import org.junit.Assert;

public class TestAssert {

	public static void assertEquals(String message, String expected, String actual) {
        if(!actual.equals(expected)) {
            Assert.fail(message + ". Expected: '" + expected + "' but Actual: '" + actual + "'");
        }
    }
	
	public static void assertContains(String message, String expected, String actual) {
        if(!actual.contains(expected)) {
            Assert.fail(message + ". Expected: '" + expected + "' but Actual: '" + actual + "'");
        }
    }
	
	public static void assertTrue(Boolean expected){
        if(!expected) {
            Assert.fail("Assertion error! Expected: 'true' but Actual: 'false'");
        }
    }
}
