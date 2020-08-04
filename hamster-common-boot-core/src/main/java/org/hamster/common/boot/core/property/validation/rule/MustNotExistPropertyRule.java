package org.hamster.common.boot.core.property.validation.rule;

import org.apache.commons.lang3.StringUtils;

import static java.text.MessageFormat.format;

/**
 * The property must not exist or the property value must be blank / empty.
 *
 * @author Jack Yin
 * @since 1.0
 */
public class MustNotExistPropertyRule implements PropertyRule {

    @Override
    public String getName() {
        return "Must not exist";
    }

    @Override
    public String getFailureMessage(String propertyName, String propertyValue) {
        return format("The property \"{0}\" must exist but its value is \"{1}\".", propertyName, propertyValue);
    }

    @Override
    public Boolean apply(String s) {
        return StringUtils.isBlank(s);
    }
}
