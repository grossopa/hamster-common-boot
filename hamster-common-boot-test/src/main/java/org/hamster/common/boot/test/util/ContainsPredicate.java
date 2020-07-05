package org.hamster.common.boot.test.util;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@RequiredArgsConstructor
public class ContainsPredicate implements Predicate<String> {

    private final List<String> keywords;
    private boolean ignoreCase = false;

    @Override
    public boolean test(String str) {
        return keywords.stream()
                .filter(keyword -> ignoreCase ? containsIgnoreCase(str, keyword) : contains(str, keyword)).findAny()
                .isEmpty();
    }
}
