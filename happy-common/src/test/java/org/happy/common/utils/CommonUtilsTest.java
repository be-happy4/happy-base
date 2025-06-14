/*
 * DBeaver - Universal Database Manager
 * Copyright (C) 2010-2025 DBeaver Corp and others
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.happy.common.utils;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommonUtilsTest {

    @Test
    public void testIsJavaIdentifier() {
        assertEquals(false, CommonUtils.isJavaIdentifier(""));
        assertEquals(false, CommonUtils.isJavaIdentifier("|"));
        assertEquals(false, CommonUtils.isJavaIdentifier("a-b"));
        assertEquals(true, CommonUtils.isJavaIdentifier("aa"));
    }

    @Test
    public void testEscapeJavaString() throws Exception {
        assertEquals("", CommonUtils.escapeJavaString(""));
        assertEquals("\\\"", CommonUtils.escapeJavaString("\""));
        assertEquals("\\n", CommonUtils.escapeJavaString("\n"));
        assertEquals("\\r", CommonUtils.escapeJavaString("\r"));
        assertEquals("\\t", CommonUtils.escapeJavaString("\t"));
        assertEquals("a", CommonUtils.escapeJavaString("a"));
    }

    @Test
    public void testEscapeIdentifier() {
        assertNull(CommonUtils.escapeIdentifier(null));
        assertEquals("", CommonUtils.escapeIdentifier(""));
        assertEquals("_", CommonUtils.escapeIdentifier("|"));
        assertEquals("_", CommonUtils.escapeIdentifier("||"));
        assertEquals("a_", CommonUtils.escapeIdentifier("a|"));
    }

    @Test
    public void testEscapeFileName() {
        assertEquals("", CommonUtils.escapeFileName(null));
        assertEquals("_", CommonUtils.escapeFileName("\\"));
        assertEquals("_", CommonUtils.escapeFileName("\u0013"));
        assertEquals("_", CommonUtils.escapeFileName("/"));
        assertEquals("_", CommonUtils.escapeFileName("<"));
        assertEquals("_", CommonUtils.escapeFileName(">"));
        assertEquals("_", CommonUtils.escapeFileName("|"));
        assertEquals("_", CommonUtils.escapeFileName("\""));
        assertEquals("_", CommonUtils.escapeFileName(":"));
        assertEquals("_", CommonUtils.escapeFileName("*"));
        assertEquals("_", CommonUtils.escapeFileName("?"));
        assertEquals("a", CommonUtils.escapeFileName("a"));
    }

    @Test
    public void testMakeDirectoryName() {
        assertEquals("a/", CommonUtils.makeDirectoryName("a"));
        assertEquals("a/", CommonUtils.makeDirectoryName("a/"));
    }

    @Test
    public void testRemoveTrailingSlash() {
        assertEquals("a", CommonUtils.removeTrailingSlash("a/"));
        assertEquals("a", CommonUtils.removeTrailingSlash("a\\"));
        assertEquals("a", CommonUtils.removeTrailingSlash("a"));
    }

    @Test
    public void testCapitalizeWord() {
        assertEquals("", CommonUtils.capitalizeWord(""));
        assertEquals("Abc", CommonUtils.capitalizeWord("Abc"));
        assertEquals("Abc", CommonUtils.capitalizeWord("abc"));
    }

    @Test
    public void testNotNull() {
        final Object value = "value";
        final Object defaultValue = "defaultValue";
        assertEquals(value, CommonUtils.notNull(value, defaultValue));
        assertEquals(defaultValue, CommonUtils.notNull(null, defaultValue));
    }

    @Test
    public void testIsEmptyCharSequence() {
        final CharSequence nullValue = null;
        final CharSequence emptyValue = "";
        final CharSequence value = "abc";
        assertTrue(CommonUtils.isEmpty(nullValue));
        assertTrue(CommonUtils.isEmpty(emptyValue));
        assertFalse(CommonUtils.isEmpty(value));
    }

    @Test
    public void testIsEmptyString() {
        final String nullValue = null;
        final String emptyValue = "";
        final String value = "abc";
        assertTrue(CommonUtils.isEmpty(nullValue));
        assertTrue(CommonUtils.isEmpty(emptyValue));
        assertFalse(CommonUtils.isEmpty(value));
    }

    @Test
    public void testIsNotEmpty() {
        final String nullValue = null;
        final String emptyValue = "";
        final String value = "abc";
        assertFalse(CommonUtils.isNotEmpty(nullValue));
        assertFalse(CommonUtils.isNotEmpty(emptyValue));
        assertTrue(CommonUtils.isNotEmpty(value));
    }

    @Test
    public void testIsEmptyCollection() {
        final ArrayList<Character> nullValue = null;
        final ArrayList<Character> emptyValue = new ArrayList<>();
        final ArrayList<Character> value = new ArrayList<>();
        value.add('a');
        assertTrue(CommonUtils.isEmpty(nullValue));
        assertTrue(CommonUtils.isEmpty(emptyValue));
        assertFalse(CommonUtils.isEmpty(value));
    }

    @Test
    public void testIsEmptyMap() {
        final HashMap<Integer, Character> nullValue = null;
        final HashMap<Integer, Character> emptyValue = new HashMap<>();
        final HashMap<Integer, Character> value = new HashMap<>();
        value.put(0, 'a');
        assertTrue(CommonUtils.isEmpty(nullValue));
        assertTrue(CommonUtils.isEmpty(emptyValue));
        assertFalse(CommonUtils.isEmpty(value));
    }

    @Test
    public void testSafeCollection() {
        final ArrayList<Character> theList = new ArrayList<>();
        assertEquals(theList, CommonUtils.safeCollection(null));
        assertEquals(theList, CommonUtils.safeCollection(theList));
    }

    @Test
    public void testSafeList() {
        final ArrayList<Character> theList = new ArrayList<>();
        assertEquals(theList, CommonUtils.safeList(null));
        assertEquals(theList, CommonUtils.safeList(theList));
    }

    @Test
    public void testCopyList() {
        final ArrayList<Integer> theList = new ArrayList<>();
        assertEquals(theList, CommonUtils.copyList(null));

        theList.add(0);
        assertEquals(theList, CommonUtils.copyList(theList));
    }

    @Test
    public void testNotEmpty() {
        assertEquals("", CommonUtils.notEmpty(null));
        assertEquals("abc", CommonUtils.notEmpty("abc"));
    }

    @Test
    public void testNullIfEmpty() {
        assertNull(CommonUtils.nullIfEmpty(null));
        assertNull(CommonUtils.nullIfEmpty(""));
        assertEquals("abc", CommonUtils.nullIfEmpty("abc"));
    }

    @Test
    public void testIsTrue() {
        assertTrue(CommonUtils.isTrue(true));
        assertFalse(CommonUtils.isTrue(false));
        assertFalse(CommonUtils.isTrue(null));
    }

    @Test
    public void testGetBooleanString() {
        assertTrue(CommonUtils.getBoolean("true"));
        assertFalse(CommonUtils.getBoolean("false"));
        assertFalse(CommonUtils.getBoolean("null"));
    }

    @Test
    public void testGetBooleanStringDefault() {
        assertTrue(CommonUtils.getBoolean("", true));
        assertFalse(CommonUtils.getBoolean("false", true));
    }

    @Test
    public void testGetBooleanObjectDefault() {
        final Object nullValue = null;
        final Object value = 0;
        assertTrue(CommonUtils.getBoolean(nullValue, true));
        assertTrue(CommonUtils.getBoolean(true, false));
        assertFalse(CommonUtils.getBoolean(value, true));
    }

/*
  @PrepareForTest({ CommonUtils.class, System.class })
  @Test
  public void testGetLineSeparator() {
    PowerMockito.mockStatic(System.class);
    PowerMockito.when(System.getProperty(or(isA(String.class), isNull(String.class)))).thenReturn("\r\n");
    assertEquals("\r\n", CommonUtils.getLineSeparator());

    PowerMockito.when(System.getProperty(or(isA(String.class), isNull(String.class)))).thenReturn(null);
    assertEquals("\n", CommonUtils.getLineSeparator());
  }
*/

    @Test
    public void testGetRootCause() {
        assertEquals("def", CommonUtils.getRootCause(new Throwable("abc", new Throwable("def"))).getMessage());
        assertEquals("abc", CommonUtils.getRootCause(new Throwable("abc")).getMessage());
        assertNull(CommonUtils.getRootCause(new InvocationTargetException(null)).getMessage());
    }

    @Test
    public void testEqualOrEmptyStrings() {
        assertTrue(CommonUtils.equalOrEmptyStrings("abc", "abc"));
        assertFalse(CommonUtils.equalOrEmptyStrings("abc", null));
        assertFalse(CommonUtils.equalOrEmptyStrings(null, "def"));
        assertFalse(CommonUtils.equalOrEmptyStrings("abc", "def"));
        assertTrue(CommonUtils.equalOrEmptyStrings("", ""));
    }

    @Test
    public void testToStringObject() {
        assertEquals("", CommonUtils.toString(null));
        assertEquals("a", CommonUtils.toString("a"));
        assertEquals("1", CommonUtils.toString(1));
    }

    @Test
    public void testToStringDef() {
        assertEquals("", CommonUtils.toString(null, ""));
        assertEquals("a", CommonUtils.toString("a", ""));
        assertEquals("1", CommonUtils.toString(1, ""));
    }

    @Test
    public void testToBoolean() {
        assertFalse(CommonUtils.toBoolean(null));
        assertFalse(CommonUtils.toBoolean("false"));
        assertTrue(CommonUtils.toBoolean("true"));
    }

    @Test
    public void testToIntDef() {
        assertEquals(1, CommonUtils.toInt(null, 1));
        assertEquals(2, CommonUtils.toInt(2, 1));
        assertEquals(2, CommonUtils.toInt("2", 1));
        assertEquals(1, CommonUtils.toInt("a", 1));
    }

    @Test
    public void testToInt() {
        assertEquals(1, CommonUtils.toInt(1));
    }

    @Test
    public void testIsInt() {
        assertFalse(CommonUtils.isInt(null));
        assertTrue(CommonUtils.isInt(1));
        assertTrue(CommonUtils.isInt("2"));
        assertFalse(CommonUtils.isInt("a"));
    }

    @Test
    public void testToLong() {
        assertEquals(1, CommonUtils.toLong(1L));
    }

    @Test
    public void testToLongDef() {
        assertEquals(1, CommonUtils.toLong(null, 1L));
        assertEquals(2, CommonUtils.toLong(2L, 1L));
        assertEquals(2, CommonUtils.toLong("2", 1L));
        assertEquals(1, CommonUtils.toLong("a", 1L));
    }

    @Test
    public void testIsLong() {
        assertFalse(CommonUtils.isLong(null));
        assertTrue(CommonUtils.isLong(1L));
        assertTrue(CommonUtils.isLong("2"));
        assertFalse(CommonUtils.isLong("a"));
    }

    @Test
    public void testToDouble() {
        assertEquals(0.0, CommonUtils.toDouble(null), 0);
        assertEquals(0.1, CommonUtils.toDouble(0.1), 0);
        assertEquals(0.1, CommonUtils.toDouble("0.1"), 0);
        assertEquals(Double.NaN, CommonUtils.toDouble("a"), 0);
    }

    @Test
    public void testToDoubleDef() {
        assertEquals(0.1, CommonUtils.toDouble(null, 0.1), 0);
        assertEquals(0.2, CommonUtils.toDouble(0.2, 0.1), 0);
        assertEquals(0.2, CommonUtils.toDouble("0.2", 0.1), 0);
        assertEquals(0.1, CommonUtils.toDouble("a", 0.1), 0);
    }

    @Test
    public void testToHexString() {
        assertEquals("", CommonUtils.toHexString(null));
        assertEquals("", CommonUtils.toHexString(new byte[]{}));
        assertEquals("000102", CommonUtils.toHexString(new byte[]{0, 1, 2}));
        assertEquals("", CommonUtils.toHexString(null, 0, 0));
    }

    @Test
    public void testToBinaryString() {
        assertEquals("1100100", CommonUtils.toBinaryString(100L, 6));
        assertEquals("01010", CommonUtils.toBinaryString(10L, 5));
    }

    @Test
    public void testSplitWithDelimiter() {
        assertNull(CommonUtils.splitWithDelimiter(null, ":"));
        assertArrayEquals(new String[]{"abc", ":def"}, CommonUtils.splitWithDelimiter("abc:def", ":"));
    }

    @Test
    public void testSplitString() {
        assertNotNull(CommonUtils.splitString("", ':'));
        List<String> result = new ArrayList<>();
        result.add("abc");
        result.add("def");
        assertArrayEquals(result.toArray(), CommonUtils.splitString("abc:def", ':').toArray());
    }

    @Test
    public void testSplit() {
        assertArrayEquals(new String[]{}, CommonUtils.split("", ":"));
        assertArrayEquals(new String[]{"abc", "def"}, CommonUtils.split("abc:def", ":"));
    }

    @Test
    public void testMakeString() {
        assertEquals("", CommonUtils.makeString(null, ':'));

        List<String> tokens = new ArrayList<>();
        tokens.add("abc");
        assertEquals("abc", CommonUtils.makeString(tokens, ':'));

        tokens.add("def");
        assertEquals("abc:def", CommonUtils.makeString(tokens, ':'));
    }

    @Test
    public void testTruncteString() {
        assertEquals(null, CommonUtils.truncateString(null, 3));
        assertEquals("abc", CommonUtils.truncateString("abc", 3));
        assertEquals("abc", CommonUtils.truncateString("abcdef", 3));
    }

    @Test
    public void testJoinStringsArray() {
        final String[] nullArray = null;
        assertEquals("", CommonUtils.joinStrings(":", nullArray));
        assertEquals("abc:def", CommonUtils.joinStrings(":", new String[]{"abc", "def"}));
    }

    @Test
    public void testJoinStringsCollection() {
        final ArrayList<String> nullCol = null;
        final ArrayList<String> col = new ArrayList<String>();
        col.add("abc");
        col.add("def");
        assertEquals("", CommonUtils.joinStrings(":", nullCol));
        assertEquals("abc:def", CommonUtils.joinStrings(":", col));
    }

    @Test
    public void testIsEmptyTrimmed() {
        assertTrue(CommonUtils.isEmptyTrimmed(null));
        assertTrue(CommonUtils.isEmptyTrimmed(""));
        assertTrue(CommonUtils.isEmptyTrimmed(" "));
        assertFalse(CommonUtils.isEmptyTrimmed(":"));
    }

    @Test
    public void testIsBitSet() {
        assertTrue(CommonUtils.isBitSet(1, 1));
        assertFalse(CommonUtils.isBitSet(1, 2));
    }

    enum enumClass {
        A_B,
    }

    enum enumClassEmpty {
    }

    @Test
    public void testValueOf() {
        assertNull(CommonUtils.valueOf(enumClass.class, null));

        assertEquals(enumClass.A_B, CommonUtils.valueOf(enumClass.class, null, enumClass.A_B, false));
        assertEquals(enumClass.A_B, CommonUtils.valueOf(enumClass.class, " ", enumClass.A_B, false));
        assertEquals(enumClass.A_B, CommonUtils.valueOf(enumClass.class, "A B", enumClass.A_B, true));

        assertEquals(enumClass.A_B, CommonUtils.valueOf(enumClass.class, "", enumClass.A_B));
        assertEquals(enumClass.A_B, CommonUtils.valueOf(enumClass.class, "A_B", enumClass.A_B));
    }

    @Test
    public void testGetItem() {
        final ArrayList<String> collectionList = new ArrayList<>();
        collectionList.add("a");
        assertEquals("a", CommonUtils.getItem(collectionList, 0));

        final HashSet<String> collectionSet = new LinkedHashSet<>();
        collectionSet.add("a");
        collectionSet.add("b");
        assertEquals("b", CommonUtils.getItem(collectionSet, 1));
    }

    @Test
    public void testFromOrdinal() {
        assertEquals(enumClass.A_B, CommonUtils.fromOrdinal(enumClass.class, 0));
        //assertNotEquals(enumClass.A_B, CommonUtils.fromOrdinal(enumClass.class, 3));
        assertThrows(IllegalArgumentException.class, () ->
                CommonUtils.fromOrdinal(enumClassEmpty.class, 3));
    }

    @Test
    public void testFilterCollection() {
        final ArrayList<Object> collection = new ArrayList<>();
        collection.add("a");
        collection.add(1);
        assertEquals(new String[]{"a"}, CommonUtils.filterCollection(collection, String.class).toArray());
    }

    @Test
    public void testEscapeDisplayString() {
        assertEquals("\\n\\r\\t:", CommonUtils.escapeDisplayString("\n\r\t:"));
    }

    @Test
    public void testUnescapeDisplayString() {
        assertEquals("\t\r\n", CommonUtils.unescapeDisplayString("\\t\\r\\n"));
    }

    @Test
    public void testHashCode() {
        assertEquals(0, CommonUtils.hashCode(null));
        assertEquals(96354, CommonUtils.hashCode("abc"));
    }

    @Test
    public void testGetOption() {
        final HashMap<String, Boolean> options = new HashMap<>();
        options.put("A", false);
        options.put("B", false);
        options.put("C", true);

        assertEquals("default", CommonUtils.getOption(options, "D", "default"));
        assertEquals(true, CommonUtils.getOption(options, "C", "default"));

        assertFalse(CommonUtils.getOption(null, "A"));

        assertFalse(CommonUtils.getOption(options, "D", false));
        assertTrue(CommonUtils.getOption(options, "C", false));
    }

    @Test
    public void testFixedLengthString() {
        assertEquals("abc", CommonUtils.fixedLengthString("abc", 3));
    }

    @Test
    public void testStartsWithIgnoreCase() {
        assertFalse(CommonUtils.startsWithIgnoreCase("", "a"));
        assertFalse(CommonUtils.startsWithIgnoreCase("abc", ""));
        assertTrue(CommonUtils.startsWithIgnoreCase("abc", "a"));
        assertTrue(CommonUtils.startsWithIgnoreCase("Abc", "aB"));
    }

    @Test
    public void testNiceFormatFloat() {
        assertEquals("1", CommonUtils.niceFormatFloat(1));
        assertEquals("1.1", CommonUtils.niceFormatFloat(1.1f));
    }

    @Test
    public void testNiceFormatDouble() {
        assertEquals("1", CommonUtils.niceFormatDouble(1.0));
        assertEquals("1.1", CommonUtils.niceFormatDouble(1.1));
    }

    @Test
    public void testTrim() {
        assertNull(CommonUtils.trim(null));
        assertEquals("abcdef", CommonUtils.trim("abcdef "));
    }

    @Test
    public void testCompactWhiteSpaces() {
        assertEquals("abc def", CommonUtils.compactWhiteSpaces("abc  def"));
    }

    @Test
    public void testgetSingleLineString() {
        assertEquals("a¶bc d ", CommonUtils.getSingleLineString("a\nb\rc\td\0"));
    }

    @Test
    public void testEscapeStringForBourneShell() {
        assertEquals("''", CommonUtils.escapeBourneShellString(""));
        assertEquals("'string'", CommonUtils.escapeBourneShellString("string"));
        assertEquals("'string with '\\''one single quote symbol'", CommonUtils.escapeBourneShellString("string with 'one single quote symbol"));
        assertEquals("'string with '\\''two '\\''single quote symbols'", CommonUtils.escapeBourneShellString("string with 'two 'single quote symbols"));
        assertEquals("'string with '\\''three '\\''single '\\''quote symbols'", CommonUtils.escapeBourneShellString("string with 'three 'single 'quote symbols"));
        assertEquals("''\\'''", CommonUtils.escapeBourneShellString("'"));
        assertEquals("'unit'\\'''\\''test'", CommonUtils.escapeBourneShellString("unit''test"));
        assertEquals("'unit'\\'''\\'''\\''test'", CommonUtils.escapeBourneShellString("unit'''test"));
    }

    @Test
    public void testUnescapeStringForBourneShell() {
        assertEquals("", CommonUtils.unescapeBourneShellString("''"));
        assertEquals("string", CommonUtils.unescapeBourneShellString("'string'"));
        assertEquals("string with 'one single quote symbol", CommonUtils.unescapeBourneShellString("'string with '\\''one single quote symbol'"));
        assertEquals("string with 'two 'single quote symbols", CommonUtils.unescapeBourneShellString("'string with '\\''two '\\''single quote symbols'"));
        assertEquals("string with 'three 'single 'quote symbols", CommonUtils.unescapeBourneShellString("'string with '\\''three '\\''single '\\''quote symbols'"));
        assertEquals("'", CommonUtils.unescapeBourneShellString("''\\'''"));
        assertEquals("unit''test", CommonUtils.unescapeBourneShellString("'unit'\\'''\\''test'"));
        assertEquals("unit'''test", CommonUtils.unescapeBourneShellString("'unit'\\'''\\'''\\''test'"));
        assertEquals("'''unit'''test'''", CommonUtils.unescapeBourneShellString("''\\'''\\'''\\''unit'\\'''\\'''\\''test'\\'''\\'''\\'''"));
    }

    @Test
    public void testGroup() {
        final List<String> values = Arrays.asList("aaa", "abb", "bbb", "bab", "ccc");
        final Map<Character, List<String>> groups = CommonUtils.group(values, x -> x.charAt(0));
        assertEquals(Arrays.asList("aaa", "abb"), groups.get('a'));
        assertEquals(Arrays.asList("bbb", "bab"), groups.get('b'));
        assertEquals(Arrays.asList("ccc"), groups.get('c'));
    }

    @Test
    public void testNormalizeResourcePath() {
        var emptyString = "";
        var normalizedPath = "place";
        var normalizedTwoLevelPath = "some/place";
        var pathWithBackslashSuffix = "/some/place";
        var pathWithMultipleBackslashSuffix = "//some/place";
        var pathWithWrongBackslash = "some\\place";
        var mixedCasesPath = "//some\\place";

        assertEquals(emptyString, CommonUtils.normalizeResourcePath(emptyString));
        assertEquals(normalizedPath, CommonUtils.normalizeResourcePath(normalizedPath));
        assertEquals(normalizedTwoLevelPath, CommonUtils.normalizeResourcePath(normalizedTwoLevelPath));
        assertEquals(normalizedTwoLevelPath, CommonUtils.normalizeResourcePath(pathWithBackslashSuffix));
        assertEquals(normalizedTwoLevelPath, CommonUtils.normalizeResourcePath(pathWithMultipleBackslashSuffix));
        assertEquals(normalizedTwoLevelPath, CommonUtils.normalizeResourcePath(pathWithWrongBackslash));
        assertEquals(normalizedTwoLevelPath, CommonUtils.normalizeResourcePath(mixedCasesPath));
    }

    @Test
    public void testReplaceLast() {
        assertEquals(CommonUtils.replaceLast("foobarfoobar", "foo", "bar"), "foobarbarbar");
        assertEquals(CommonUtils.replaceLast("foobarbarbar", "foo", "bar"), "barbarbarbar");
        assertEquals(CommonUtils.replaceLast("foo", "bar", "foo"), "foo");
        assertEquals(CommonUtils.replaceLast("", "bar", "foo"), "");
    }
}
