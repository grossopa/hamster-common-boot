package org.hamster.common.boot.core.property.validation;

import org.hamster.common.boot.core.property.validation.rule.MustExistPropertyRule;
import org.hamster.common.boot.core.property.validation.rule.MustMatchPropertyRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static java.util.stream.Collectors.toList;
import static org.hamster.common.boot.core.property.validation.PropertyValidatingResultType.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Tests for {@link DefaultPropertyValidator}
 *
 * @author Jack Yin
 * @since 1.0
 */
class DefaultPropertyValidatorTest {

    DefaultPropertyValidator testSubject;

    @BeforeEach
    void beforeEach() {
        List<PropertyValidationInfo> infoList = newArrayList();
        infoList.add(new PropertyValidationInfo("property1", new MustExistPropertyRule(), INFO));
        infoList.add(new PropertyValidationInfo("property2", new MustExistPropertyRule(), WARN));
        infoList.add(new PropertyValidationInfo("property3", new MustExistPropertyRule(), ERROR));
        infoList.add(
                new PropertyValidationInfo("property3", new MustMatchPropertyRule(Pattern.compile("^some_[0-9]{5}")),
                        WARN));

        testSubject = new DefaultPropertyValidator(infoList);
    }


    @Test
    void validateMap() {
        Map<String, String> properties = newHashMap();
        properties.put("property1", "");
        properties.put("property2", null);
        properties.put("property3", "some_other_value");
        properties.put("property4", "some_other_value");

        List<PropertyValidatingResult> results = testSubject.validate(properties);
        List<PropertyValidatingResult> results1 = results.stream().filter(r -> r.getPropertyName().equals("property1"))
                .collect(toList());
        List<PropertyValidatingResult> results2 = results.stream().filter(r -> r.getPropertyName().equals("property2"))
                .collect(toList());
        List<PropertyValidatingResult> results3 = results.stream().filter(r -> r.getPropertyName().equals("property3"))
                .collect(toList());
        List<PropertyValidatingResult> results4 = results.stream().filter(r -> r.getPropertyName().equals("property4"))
                .collect(toList());

        assertEquals(1, results1.size());
        assertEquals(INFO, results1.get(0).getType());
        assertEquals("", results1.get(0).getPropertyValue());

        assertEquals(1, results2.size());
        assertEquals(WARN, results2.get(0).getType());
        assertNull(results2.get(0).getPropertyValue());
        assertEquals("The property \"property2\" must exist but its value is empty.", results2.get(0).getMessage());

        assertEquals(2, results3.size());
        assertEquals(SUCCESS, results3.get(0).getType());
        assertEquals("", results3.get(0).getMessage());

        assertEquals("some_other_value", results3.get(0).getPropertyValue());
        assertEquals(WARN, results3.get(1).getType());
        assertEquals("some_other_value", results3.get(1).getPropertyValue());

        assertEquals(0, results4.size());
    }

    @Test
    void validateProperties() {
        Properties properties = new Properties();
        properties.put("property1", "some_value");

        List<PropertyValidatingResult> results = testSubject.validate(properties);

        assertEquals(1, results.size());
        assertEquals(SUCCESS, results.get(0).getType());
        assertEquals("some_value", results.get(0).getPropertyValue());

    }
}