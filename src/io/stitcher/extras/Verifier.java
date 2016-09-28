package io.stitcher.extras;

import java.util.Collection;

public class Verifier {

    public static final String UNEXPECTED_NULL = "Unexpected null object";
    public static final String UNEXPECTED_FALSE = "Unexpected false value";
    public static final String UNEXPECTED_TRUE = "Unexpected true value";

    public static void isTrue(boolean b) {
        if (!b) {
            throw new IllegalArgumentException(UNEXPECTED_FALSE);
        }
    }

    public static void isFalse(boolean b) {
        if (b) {
            throw new IllegalArgumentException(UNEXPECTED_TRUE);
        }
    }

    public static void illegalArgumentIfNull(Object o) {
        if (o == null) {
            throw new IllegalArgumentException(UNEXPECTED_NULL);
        }
    }

    public static void illegalArgumentIfNull(Object o1, Object o2) {
        illegalArgumentIfNull(o1);
        illegalArgumentIfNull(o2);
    }

    public static void illegalArgumentIfNull(Object o1, Object o2, Object o3) {
        illegalArgumentIfNull(o1);
        illegalArgumentIfNull(o2);
        illegalArgumentIfNull(o3);
    }

    public static void illegalArgumentIfNull(Object o1, Object o2, Object o3, Object o4) {
        illegalArgumentIfNull(o1);
        illegalArgumentIfNull(o2);
        illegalArgumentIfNull(o3);
        illegalArgumentIfNull(o4);
    }

    public static void illegalArgumentIfAnyNull(Collection<Object> col) {
        for (Object o : col) {
            illegalArgumentIfNull(o);
        }
    }

    public static void illegalStateIfNull(Object o) {
        if (o == null) {
            throw new IllegalStateException(UNEXPECTED_NULL);
        }
    }

    public static void illegalStateIfEmtpy(Collection<?> c) {
        if (c.isEmpty()) {
            throw new IllegalStateException(UNEXPECTED_NULL);
        }
    }

}
