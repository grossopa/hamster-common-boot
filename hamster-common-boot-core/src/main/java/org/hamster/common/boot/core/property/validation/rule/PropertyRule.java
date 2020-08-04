package org.hamster.common.boot.core.property.validation.rule;

import javax.annotation.Nonnull;
import java.util.function.Function;

/**
 * The property validating rule, it holds the basic information of the rule and also could perform the validating check
 * against one given property value. the input of {@link #apply} is the value of the property and returns true for
 * matching.
 *
 * @author Jack Yin
 * @since 1.0
 */
public interface PropertyRule extends Function<String, Boolean> {

    /**
     * Gets the name of the rule.
     *
     * @return the name of the rule.
     */
    String getName();

    /**
     * gets the message by property name and value.
     *
     * @return the formatted message
     */
    String getFailureMessage(String propertyName, String propertyValue);
}
