package org.hamster.common.boot.core.property.validation.rule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for {@link MustMatchPropertyRule}
 *
 * @author Jack Yin
 * @since 1.0
 */
class MustMatchPropertyRuleTest {

    MustMatchPropertyRule testSubject;

    @BeforeEach
    void beforeEach() {
        testSubject = new MustMatchPropertyRule(Pattern.compile("^some[0-9]{3}$"));
    }

    @Test
    void getName() {
        assertEquals("Must match the pattern", testSubject.getName());
    }

    @Test
    void getFailureMessage() {
        assertEquals(
                "The property \"some_property\" with value \"some_value\" does not match the expected pattern ^some[0-9]{3}$",
                testSubject.getFailureMessage("some_property", "some_value"));
    }

    @Test
    void apply() {
        assertTrue(testSubject.apply("some397"));
    }

    @Test
    void applyNegative() {
        assertFalse(testSubject.apply("ssssssss"));
    }

    @Test
    void applyNullNegative() {
        assertFalse(testSubject.apply(null));
    }

    @Test
    void getPattern() {
        assertEquals(Pattern.compile("^some[0-9]{3}$").toString(), testSubject.getPattern().toString());
    }
}