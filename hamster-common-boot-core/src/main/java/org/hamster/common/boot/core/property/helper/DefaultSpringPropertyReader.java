package org.hamster.common.boot.core.property.helper;

import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.PropertySource;

import java.util.HashMap;
import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;

/**
 * Default implementation of {@link SpringPropertyReader}.
 *
 * @author Jack Yin
 * @since 1.0
 */
public class DefaultSpringPropertyReader implements SpringPropertyReader {
    @Override
    public Map<String, Object> readAllProperties(ConfigurableEnvironment environment) {
        Map<String, Object> result = newHashMap();
        for (PropertySource<?> propertySource : environment.getPropertySources()) {
            if (propertySource instanceof EnumerablePropertySource) {
                for (String key : ((EnumerablePropertySource) propertySource).getPropertyNames()) {
                    result.put(key, propertySource.getProperty(key));
                }
            }
        }
        return result;
    }
}
