package org.hamster.common.boot.core.property.validation.rule;

import lombok.Getter;

import javax.annotation.Nonnull;
import java.util.regex.Pattern;

import static java.text.MessageFormat.format;
import static java.util.Objects.requireNonNull;

/**
 * Validates the property must match the pattern.
 *
 * @author Jack Yin
 * @since 1.0
 */
@Getter
public class MustMatchPropertyRule implements PropertyRule {

    private final Pattern pattern;

    /**
     * Constructs an instance with pattern.
     *
     * @param pattern
     *         the pattern for {@link #apply(String)} method to validate property value.
     */
    public MustMatchPropertyRule(@Nonnull Pattern pattern) {
        this.pattern = requireNonNull(pattern);
    }

    @Nonnull
    @Override
    public String getName() {
        return "Must match the pattern";
    }

    @Nonnull
    @Override
    public String getFailureMessage(String propertyName, String propertyValue) {
        return format("The property \"{0}\" with value \"{1}\" does not match the expected pattern {2}", propertyName,
                propertyValue, pattern);
    }

    @Override
    public Boolean apply(String s) {
        if (s == null) {
            return false;
        }
        return pattern.matcher(s).matches();
    }
}
