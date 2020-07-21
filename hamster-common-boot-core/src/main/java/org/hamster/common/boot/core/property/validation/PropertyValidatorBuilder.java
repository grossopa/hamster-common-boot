package org.hamster.common.boot.core.property.validation;

import lombok.RequiredArgsConstructor;
import org.hamster.common.boot.core.property.validation.rule.MustExistPropertyRule;
import org.hamster.common.boot.core.property.validation.rule.MustMatchPropertyRule;

import java.util.List;
import java.util.regex.Pattern;

import static com.google.common.collect.Lists.newArrayList;

/**
 * The builder for building {@link PropertyValidator}
 *
 * @author Jack Yin
 * @since 1.0
 */
public class PropertyValidatorBuilder {

    private final List<PropertyValidationInfo> allInfoList = newArrayList();

    /**
     * Gets a copy of the defined {@link PropertyValidationInfo} list.
     *
     * @return the copy of the defined {@link PropertyValidationInfo} list.
     */
    public List<PropertyValidationInfo> getAllInfoList() {
        return newArrayList(allInfoList);
    }

    /**
     * Adds the validation info to current builder.
     *
     * @param info
     *         the instance of {@link PropertyValidationInfo} to build
     */
    void add(PropertyValidationInfo info) {
        allInfoList.add(info);
    }

    /**
     * Builds the validation rules for specified properties.
     *
     * @param properties
     *         the properties to build rules against
     * @return the created builder for rules under the specified properties
     */
    public PropertyValidatorPropertyBuilder properties(String... properties) {
        return new PropertyValidatorPropertyBuilder(this, properties);
    }

    /**
     * The validation rule builder for specified properties.
     *
     * @author Jack Yin
     * @since 1.0
     */
    @RequiredArgsConstructor
    public static class PropertyValidatorPropertyBuilder {
        private final PropertyValidatorBuilder parent;
        private final String[] properties;

        /**
         * Adds the {@link MustExistPropertyRule} validation for specified properties. The properties must exist and has
         * a non-blank value.
         *
         * @param failureResultType
         *         the result type if validation fails
         * @return this instance
         */
        public PropertyValidatorPropertyBuilder mustExist(PropertyValidatingResultType failureResultType) {
            for (String propertyName : properties) {
                parent.add(new PropertyValidationInfo(propertyName, new MustExistPropertyRule(), failureResultType));
            }
            return this;
        }

        /**
         * Adds the {@link MustMatchPropertyRule} validation for specified properties. The properties must match the
         * given regular expression.
         *
         * @param pattern
         *         the pattern in String format
         * @param failureResultType
         *         the result type if validation fails
         * @return this instance
         */
        public PropertyValidatorPropertyBuilder mustMatch(String pattern,
                PropertyValidatingResultType failureResultType) {
            return mustMatch(Pattern.compile(pattern), failureResultType);
        }

        /**
         * Adds the {@link MustMatchPropertyRule} validation for specified properties. The properties must match the
         * given regular expression.
         *
         * @param pattern
         *         the {@link Pattern} instance
         * @param failureResultType
         *         the result type if validation fails
         * @return this instance
         */
        public PropertyValidatorPropertyBuilder mustMatch(Pattern pattern,
                PropertyValidatingResultType failureResultType) {
            for (String propertyName : properties) {
                parent.add(new PropertyValidationInfo(propertyName, new MustMatchPropertyRule(pattern),
                        failureResultType));
            }
            return this;
        }

        /**
         * Ends current properties builder and return back to parent {@link PropertyValidatorBuilder}.
         *
         * @return the parent instance {@link PropertyValidatorBuilder}
         */
        public PropertyValidatorBuilder and() {
            return parent;
        }
    }
}
