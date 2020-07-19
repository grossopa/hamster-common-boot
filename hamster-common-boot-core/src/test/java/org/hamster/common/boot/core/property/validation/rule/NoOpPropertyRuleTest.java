package org.hamster.common.boot.core.property.validation.rule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for {@link NoOpPropertyRule}
 *
 * @author Jack Yin
 * @since 1.0
 */
class NoOpPropertyRuleTest {

    NoOpPropertyRule testSubject;

    @BeforeEach
    void beforeEach() {
        testSubject = new NoOpPropertyRule();
    }


    @Test
    void getName() {
        assertEquals("No Operation", testSubject.getName());
    }

    @Test
    void getFailureMessage() {
        assertEquals("No Operations for some_property", testSubject.getFailureMessage("some_property", "aa"));
    }

    @Test
    void apply() {
        assertTrue(testSubject.apply("some_property"));
    }
}