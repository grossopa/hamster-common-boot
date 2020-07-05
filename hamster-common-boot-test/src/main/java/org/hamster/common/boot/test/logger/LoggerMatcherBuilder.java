package org.hamster.common.boot.test.logger;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Predicate;

import static com.google.common.collect.Lists.newArrayList;

/**
 * Builds an instance of {@link LoggerMatcher}
 *
 * @author Jack Yin
 * @since 1.0
 */
@RequiredArgsConstructor
public abstract class LoggerMatcherBuilder {

    private final Logger logger;

    List<ContainsMatch> matchList = newArrayList();

    public static LoggerMatcherBuilder logback(Class<?> clazz) {
        return new LogbackLoggerMatcherBuilder(LoggerFactory.getLogger(clazz));
    }

    public abstract LoggerMatcher build();

    static class LogbackLoggerMatcherBuilder extends LoggerMatcherBuilder {

        public LogbackLoggerMatcherBuilder(Logger logger) {
            super(logger);
        }

        @Override
        public LoggerMatcher build() {
            return null;
        }
    }


}

