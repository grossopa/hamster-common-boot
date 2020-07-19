package org.hamster.common.boot.core.property.validation;

import com.google.common.collect.Maps;
import org.hamster.common.boot.core.property.validation.rule.PropertyRule;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.*;
import static org.hamster.common.boot.core.property.validation.PropertyValidatingResultType.SUCCESS;

/**
 * Default implementation of {@link PropertyValidator}
 *
 * @author Jack Yin
 * @since 1.0
 */
public class DefaultPropertyValidator implements PropertyValidator {

    private final Map<String, List<PropertyValidationInfo>> infoList;

    /**
     * Constructs an instance with given property rules.
     *
     * @param infoList
     *         the property validation information list for validation
     */
    public DefaultPropertyValidator(@Nonnull List<PropertyValidationInfo> infoList) {
        this.infoList = requireNonNull(infoList).stream().collect(groupingBy(PropertyValidationInfo::getName));
    }

    @Override
    public List<PropertyValidatingResult> validate(Map<String, String> propertyMap) {
        return propertyMap.entrySet().stream().flatMap(entry -> {
            String propertyName = entry.getKey();
            String propertyValue = entry.getValue();
            List<PropertyValidationInfo> propertyInfoList = infoList.get(propertyName);
            return propertyInfoList.stream().map(info -> {
                PropertyValidatingResultType resultType =
                        info.getRule().apply(propertyValue) ? SUCCESS : info.getFailureResultLevel();
                return new PropertyValidatingResult(propertyName, propertyValue,
                        info.getRule().getFailureMessage(propertyName, propertyValue), resultType);
            });
        }).collect(toList());
    }

    @Override
    public List<PropertyValidatingResult> validate(Properties properties) {
        Map<String, String> propertyMap = properties.entrySet().stream()
                .collect(toMap(ent -> String.valueOf(ent.getKey()), ent -> String.valueOf(ent.getValue())));
        return validate(propertyMap);
    }
}
