package org.hamster.common.boot.core.property.validation.rule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for {@link MustExistPropertyRule}
 *
 * @author Jack Yin
 * @since 1.0
 */
class MustExistPropertyRuleTest {

    MustExistPropertyRule testSubject;

    @BeforeEach
    void beforeEach() {
        testSubject = new MustExistPropertyRule();
    }

    @Test
    void getName() {
        assertEquals("Must Exists", testSubject.getName());
    }

    @Test
    void getFailureMessage() {
        assertEquals("The property \"some_property_name\" must exist but its value is empty.",
                testSubject.getFailureMessage("some_property_name", ""));
    }

    @Test
    void apply() {
        assertTrue(testSubject.apply("some_value"));
    }

    @Test
    void applyNegative() {
        assertFalse(testSubject.apply(""));
    }
}