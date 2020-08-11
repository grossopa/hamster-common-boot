package org.hamster.common.boot.core.property.helper;

import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;

import java.util.Map;

/**
 * Reads Spring properties,
 *
 * @author Jack Yin
 * @since 1.0
 */
public interface SpringPropertyReader {

    /**
     * Reads all loaded properties. It reads the final loaded properties by finding the {@link EnumerablePropertySource}
     * from {@link ConfigurableEnvironment#getPropertySources()}.
     *
     * @param environment
     *         the environment to find the properties
     * @return the found properties
     */
    Map<String, Object> readAllProperties(ConfigurableEnvironment environment);
}
