package org.hamster.common.boot.core.property.validation;

import lombok.Getter;
import org.hamster.common.boot.core.property.validation.rule.PropertyRule;

import static java.util.Objects.requireNonNull;

/**
 * The property validation information
 *
 * @author Jack Yin
 * @since 1.0
 */
@Getter
public class PropertyValidationInfo {
    private final String propertyName;
    private final PropertyRule rule;
    private final PropertyValidatingResultType failureResultLevel;

    /**
     * Constructs an instance with name, rule and expected failure result level.
     *
     * @param propertyName
     *         the target property name
     * @param rule
     *         the detailed rule of the validation
     * @param failureResultLevel
     *         result level when validation failed
     */
    public PropertyValidationInfo(String propertyName, PropertyRule rule,
            PropertyValidatingResultType failureResultLevel) {
        this.propertyName = requireNonNull(propertyName);
        this.rule = requireNonNull(rule);
        this.failureResultLevel = requireNonNull(failureResultLevel);
    }
}
