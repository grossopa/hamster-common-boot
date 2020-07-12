package org.hamster.common.boot.test.logger.builder;

import java.util.List;

/**
 * Builds the levels from given range requirement inclusively.
 *
 * @author Jack Yin
 * @since 1.0
 */
public interface LevelRangeBuilder<T> {

    List<T> lowerThan(String level);

    List<T> lowerThan(T level);

    List<T> higherThan(String level);

    List<T> higherThan(T level);

    List<T> of(String... level);
}
