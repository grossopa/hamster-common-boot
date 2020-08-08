package org.hamster.common.boot.core.property.validation;

import com.google.common.collect.Iterables;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static org.hamster.common.boot.core.property.validation.PropertyValidatingResultType.*;

/**
 * The property validator bean that could throw exception when property validation results are with {@link
 * PropertyValidatingResultType#ERROR}.
 *
 * @author Jack Yin
 * @since 1.0
 */
@Slf4j
public class SpringPropertyValidator implements PropertyValidator {

    private final PropertyValidator propertyValidator;

    /**
     * Constructs an instance with given property validator.
     *
     * @param propertyValidator
     *         the inner property validator for the actual validation
     */
    public SpringPropertyValidator(PropertyValidator propertyValidator) {
        requireNonNull(propertyValidator);
        this.propertyValidator = propertyValidator;
    }

    @Override
    public List<PropertyValidatingResult> validate(Map<String, String> propertyMap) {
        List<PropertyValidatingResult> results = propertyValidator.validate(propertyMap);
        processResults(results);
        return results;
    }

    @Override
    public List<PropertyValidatingResult> validate(Properties properties) {
        List<PropertyValidatingResult> results = propertyValidator.validate(properties);
        processResults(results);
        return results;
    }

    private void processResults(List<PropertyValidatingResult> results) {
        List<PropertyValidatingResult> copiedResults = newArrayList(results);
        copiedResults.sort((r1, r2) -> StringUtils.compare(r1.getPropertyName(), r2.getPropertyName()));
        copiedResults.forEach(result -> {
            if (INFO == result.getType()) {
                log.info(result.getMessage());
            } else if (WARN == result.getType()) {
                log.warn(result.getMessage());
            }
        });

        List<PropertyValidatingResult> errorResults = copiedResults.stream().filter(result -> ERROR == result.getType())
                .collect(toList());

        if (!Iterables.isEmpty(errorResults)) {
            throw new PropertyValidatingException(errorResults,
                    "Property validation fails.\n    " + errorResults.stream().map(PropertyValidatingResult::getMessage)
                            .collect(joining("\n    ")));
        }
    }
}
