package org.hamster.common.boot.test.logger;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Jack Yin
 * @since 1.0
 */
@Getter
@RequiredArgsConstructor
public class ContainsMatch {
    private final String keyword;
    private final int index;
    private final String level;
    private final boolean negative;
}
