package org.hamster.common.boot.core.property.validation;

import com.google.common.collect.ImmutableMap;
import org.hamster.common.boot.core.property.validation.rule.MustExistPropertyRule;
import org.hamster.common.boot.core.property.validation.rule.PropertyRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Sets.newHashSet;
import static java.util.regex.Pattern.compile;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toSet;
import static org.hamster.common.boot.core.property.validation.PropertyValidatingResultType.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for {@link PropertyValidatorBuilder}
 *
 * @author Jack Yin
 * @since 1.0
 */
class PropertyValidatorBuilderTest {

    PropertyValidatorBuilder testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new PropertyValidatorBuilder();
    }


    @Test
    void builderSimple() {
        PropertyValidator propertyValidator = testSubject
                // 1.2.
                .properties("some-properties.1", "some-properties.2").mustExist(WARN)
                .mustMatch("SomePropertiesValue123", ERROR).and()
                // 3.
                .properties("some-properties.3").mustNotExist(ERROR).and()
                // 4.
                .properties("some-properties.4").mustMatch(compile("^[0-9]+$"), INFO).and()
                // 5.
                .properties("some-properties.5").mustExist(INFO).and().build();

        assertEquals(7, testSubject.getAllInfoList().size());

        List<PropertyValidatingResult> results = propertyValidator
                .validate(ImmutableMap.of("some-properties.4", "123"));
        Map<String, List<PropertyValidatingResult>> map = results.stream()
                .collect(groupingBy(PropertyValidatingResult::getPropertyName));

        assertEquals(2, map.get("some-properties.1").size());
        assertEquals(2, map.get("some-properties.2").size());
        assertEquals(1, map.get("some-properties.3").size());
        assertEquals(1, map.get("some-properties.4").size());
        assertEquals(1, map.get("some-properties.5").size());

        assertEquals(newHashSet(WARN, ERROR),
                map.get("some-properties.1").stream().map(PropertyValidatingResult::getType).collect(toSet()));
        assertEquals(newHashSet(WARN, ERROR),
                map.get("some-properties.2").stream().map(PropertyValidatingResult::getType).collect(toSet()));
        assertEquals(newHashSet(SUCCESS),
                map.get("some-properties.3").stream().map(PropertyValidatingResult::getType).collect(toSet()));
        assertEquals(newHashSet(SUCCESS),
                map.get("some-properties.4").stream().map(PropertyValidatingResult::getType).collect(toSet()));
        assertEquals(newHashSet(INFO),
                map.get("some-properties.5").stream().map(PropertyValidatingResult::getType).collect(toSet()));
    }

    @Test
    void add() {
        testSubject.add(new PropertyValidationInfo("abc", new MustExistPropertyRule(), ERROR));
        List<PropertyValidatingResult> results = testSubject.build().validate(ImmutableMap.of());
        assertEquals(1, results.size());
        assertEquals(ERROR, results.get(0).getType());
    }

    @Test
    void customRule() {
        PropertyValidator propertyValidator = testSubject.properties("abc").custom(new PropertyRule() {
            @Nonnull
            @Override
            public String getName() {
                return "some-property-rule";
            }

            @Nonnull
            @Override
            public String getFailureMessage(String propertyName, String propertyValue) {
                return "failure message " + propertyName + "," + propertyValue;
            }

            @Override
            public Boolean apply(String s) {
                return "aaaa".equals(s);
            }
        }, ERROR).build();

        List<PropertyValidatingResult> results = propertyValidator.validate(ImmutableMap.of("abc", "bbbb"));
        assertEquals(ERROR, results.get(0).getType());

        List<PropertyValidatingResult> results2 = propertyValidator.validate(ImmutableMap.of("abc", "aaaa"));
        assertEquals(SUCCESS, results2.get(0).getType());
    }
}