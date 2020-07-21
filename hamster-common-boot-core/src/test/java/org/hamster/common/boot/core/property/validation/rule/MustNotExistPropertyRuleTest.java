package org.hamster.common.boot.core.property.validation.rule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

/**
 * Tests for {@link MustNotExistPropertyRule}
 *
 * @author Jack Yin
 * @since 1.0
 */
class MustNotExistPropertyRuleTest {

    MustNotExistPropertyRule testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new MustNotExistPropertyRule();
    }


    @Test
    void getName() {
        assertEquals("Must not exist", testSubject.getName());
    }

    @Test
    void getFailureMessage() {
        assertEquals("The property \"some-property\" must exist but its value is \"some-value\".",
                testSubject.getFailureMessage("some-property", "some-value"));
    }

    @Test
    void apply() {
        assertTrue(testSubject.apply(""));
    }

    @Test
    void applyNegative() {
        assertFalse(testSubject.apply("some value"));
    }
}