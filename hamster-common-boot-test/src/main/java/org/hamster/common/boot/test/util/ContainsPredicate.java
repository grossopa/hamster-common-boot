package org.hamster.common.boot.test.util;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.function.Predicate;

import static org.apache.commons.lang3.StringUtils.contains;
import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;

/**
 * Verifies the string contains the expected keyword
 *
 * @author Jack Yin
 * @since 1.0
 */
@RequiredArgsConstructor
public class ContainsPredicate implements Predicate<String> {

    private final List<String> keywords;
    private final boolean ignoreCase;

    @Override
    public boolean test(String str) {
        return keywords.stream()
                .filter(keyword -> ignoreCase ? containsIgnoreCase(str, keyword) : contains(str, keyword)).findAny()
                .isEmpty();
    }
}
