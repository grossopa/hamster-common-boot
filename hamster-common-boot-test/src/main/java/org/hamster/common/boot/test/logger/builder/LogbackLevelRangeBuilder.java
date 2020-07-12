package org.hamster.common.boot.test.logger.builder;

import ch.qos.logback.classic.Level;

import java.util.List;

/**
 * Logback implementation of {@link LevelRangeBuilder}
 *
 * @author Jack Yin
 * @since 1.0
 */
public class LogbackLevelRangeBuilder implements LevelRangeBuilder<Level> {


    @Override
    public List<Level> lowerThan(String level) {
        return lowerThan(Level.toLevel(level));
    }

    @Override
    public List<Level> lowerThan(Level level) {
        return null;
    }

    @Override
    public List<Level> higherThan(String level) {
        return null;
    }

    @Override
    public List<Level> higherThan(Level level) {
        return null;
    }

    @Override
    public List<Level> of(String... level) {
        return null;
    }
}
