package org.hamster.common.boot.core.property.validation;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;
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
        this.infoList = requireNonNull(infoList).stream().collect(groupingBy(PropertyValidationInfo::getPropertyName));
    }

    @Override
    public List<PropertyValidatingResult> validate(Map<String, String> propertyMap) {
        List<PropertyValidatingResult> resultList = newArrayList();
        infoList.forEach((propertyName, value) -> {
            String propertyValue = propertyMap.get(propertyName);
            for (PropertyValidationInfo info : value) {
                boolean result = info.getRule().apply(propertyValue);
                PropertyValidatingResultType resultType;
                String message;
                if (result) {
                    resultType = SUCCESS;
                    message = "";
                } else {
                    resultType = info.getFailureResultLevel();
                    message = info.getRule().getFailureMessage(propertyName, propertyValue);
                }

                resultList.add(new PropertyValidatingResult(propertyName, propertyValue, message, resultType));
            }
        });

        return resultList;
    }

    @Override
    public List<PropertyValidatingResult> validate(Properties properties) {
        Map<String, String> propertyMap = properties.entrySet().stream()
                .collect(toMap(ent -> String.valueOf(ent.getKey()), ent -> String.valueOf(ent.getValue())));
        return validate(propertyMap);
    }
}
