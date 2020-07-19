package org.hamster.common.boot.core.property.validation;

import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Validates that mandatory properties are present within given source.
 *
 * @author Jack Yin
 * @since 1.0
 */
public interface PropertyValidator {

    /**
     * Validates the given {@link Map} object with properties.
     *
     * @param propertyMap
     *         the property map to validate
     * @return the validating result list
     */
    List<PropertyValidatingResult> validate(Map<String, String> propertyMap);

    /**
     * Validates the given {@link Properties} object with properties.
     *
     * @param properties
     *         the property object to validate
     * @return the validating result list
     */
    List<PropertyValidatingResult> validate(Properties properties);
}
