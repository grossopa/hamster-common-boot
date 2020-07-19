package org.hamster.common.boot.core.property.validation.rule;

import javax.annotation.Nonnull;

import static java.text.MessageFormat.format;

/**
 * No operation property validating rule.
 *
 * @author Jack Yin
 * @since 1.0
 */
public class NoOpPropertyRule implements PropertyRule {

    @Nonnull
    @Override
    public String getName() {
        return "No Operation";
    }

    @Nonnull
    @Override
    public String getFailureMessage(String propertyName, String propertyValue) {
        return format("No Operations for {0}", propertyName);
    }

    @Override
    public Boolean apply(String s) {
        return true;
    }
}
