package org.hamster.common.boot.core.property.validation;

/**
 * The property validating result type
 *
 * @author Jack Yin
 * @since 1.0
 */
public enum PropertyValidatingResultType {
    /**
     * The target property matches the expectation
     */
    SUCCESS,
    /**
     * The target property doesn't match the expectation, but should simply ignore it or just print an info message.
     */
    INFO,
    /**
     * The target property doesn't match the expectation, but should treat it as a warning rather than a fatal error.
     */
    WARN,
    /**
     * The target property doesn't match the expectation, and should treat it as a fatal error. e.g. terminate the
     * application starting.
     */
    ERROR
}
