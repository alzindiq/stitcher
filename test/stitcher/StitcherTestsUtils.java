package com.hp.hpl.stitcher;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Map;

public class StitcherTestsUtils {

    public static <B, C> void checkSameStitches(Map<B, Collection<C>> expected, Map<B, Collection<C>> actual) {
        checkSameStitches("", expected, actual);
    }
    
    public static <B, C> void checkSameStitches(String message, Map<B, Collection<C>> expected, Map<B, Collection<C>> actual) {

        // Check that both have the same keys
        Collection<B> expectedKeys = expected.keySet();
        Collection<B> actualKeys = actual.keySet();
        assertEquals(message, expectedKeys, actualKeys);

        // Check each key has the same values
        for (B key : expectedKeys) {
            checkSameElements( message, expected.get(key), actual.get(key));
        }
    }

    public static <T> void checkSameElements(Collection<T> expected, Collection<T> actual) {
        checkSameElements("", expected, actual);
    }
    
    public static <T> void checkSameElements(String message, Collection<T> expected, Collection<T> actual) {
        // Symmetric comparison
        assertTrue(message, expected.containsAll(actual));
        assertTrue(message, actual.containsAll(expected));
    }

}
