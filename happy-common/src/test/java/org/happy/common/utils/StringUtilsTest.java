package org.happy.common.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringUtilsTest {


    @Test
    public void testToCamelCase() {
        assertNull(StringUtils.toCamelCase(null));
        assertEquals(StringUtils.toCamelCase(""), "");
        assertEquals("Abcd", StringUtils.toCamelCase("abcd"));
        assertEquals("Ab|Cd", StringUtils.toCamelCase("ab|cd"));
    }
}