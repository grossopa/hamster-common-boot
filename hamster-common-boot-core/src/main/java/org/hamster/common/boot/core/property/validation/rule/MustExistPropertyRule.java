package org.hamster.common.boot.core.property.validation.rule;

import javax.annotation.Nonnull;

import static java.text.MessageFormat.format;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * The property value must exist.
 *
 * @author Jack Yin
 * @since 1.0
 */
public class MustExistPropertyRule implements PropertyRule {

    @Nonnull
    @Override
    public String getName() {
        return "Must Exists";
    }

    @Nonnull
    @Override
    public String getFailureMessage(String propertyName, String propertyValue) {
        return format("The property \"{0}\" must exist but its value is empty.", propertyName);
    }

    @Override
    public Boolean apply(String s) {
        return isNotBlank(s);
    }
}
