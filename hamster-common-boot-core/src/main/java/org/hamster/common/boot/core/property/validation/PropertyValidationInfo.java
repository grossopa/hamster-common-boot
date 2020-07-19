package org.hamster.common.boot.core.property.validation;

import lombok.Getter;
import org.hamster.common.boot.core.property.validation.rule.PropertyRule;

import javax.annotation.Nonnull;

import static java.util.Objects.requireNonNull;

/**
 * The property validation information
 *
 * @author Jack Yin
 * @since 1.0
 */
@Getter
public class PropertyValidationInfo {
    private final String name;
    private final PropertyRule rule;
    private final PropertyValidatingResultType failureResultLevel;

    /**
     * Constructs an instance with name, rule and expected failure result level.
     *
     * @param name
     *         the display name of the validation information
     * @param rule
     *         the detailed rule of the validation
     * @param failureResultLevel
     *         result level when validation failed
     */
    public PropertyValidationInfo(@Nonnull String name, @Nonnull PropertyRule rule,
            @Nonnull PropertyValidatingResultType failureResultLevel) {
        this.name = requireNonNull(name);
        this.rule = requireNonNull(rule);
        this.failureResultLevel = requireNonNull(failureResultLevel);
    }
}
