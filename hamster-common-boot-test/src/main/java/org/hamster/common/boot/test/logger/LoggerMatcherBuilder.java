package org.hamster.common.boot.test.logger;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Builds an instance of {@link LoggerMatcher}
 *
 * @author Jack Yin
 * @since 1.0
 */
@RequiredArgsConstructor
public abstract class LoggerMatcherBuilder {

    protected final Logger logger;

    /**
     * Builds and Creates the {@link LogbackLoggerMatcher} instance with the given Slf4j Logger.
     *
     * @param logger
     *         the Slf4j logger to create the matcher with
     * @return the builder instance
     */
    public static LogbackLoggerMatcherBuilder logback(Logger logger) {
        return new LogbackLoggerMatcherBuilder(logger);
    }

    /**
     * Builds and Creates the {@link LogbackLoggerMatcher} instance with the given target class.
     *
     * @param clazz
     *         the Slf4j logger target class to create the matcher with
     * @return the builder instance
     */
    public static LogbackLoggerMatcherBuilder logback(Class<?> clazz) {
        return new LogbackLoggerMatcherBuilder(LoggerFactory.getLogger(clazz));
    }

    /**
     * Builds and Creates the {@link LogbackLoggerMatcher} instance with the given Slf4j Logger name.
     *
     * @param loggerName
     *         the Slf4j logger target name to create the matcher with
     * @return the builder instance
     */
    public static LogbackLoggerMatcherBuilder logback(String loggerName) {
        return new LogbackLoggerMatcherBuilder(LoggerFactory.getLogger(loggerName));
    }

    /**
     * Builds the {@link LoggerMatcher} instance.
     *
     * @param start
     *         whether starts the logging immediately.
     * @return built {@link LoggerMatcher} instance
     */
    public abstract LoggerMatcher<?> build(boolean start);

    public static class LogbackLoggerMatcherBuilder extends LoggerMatcherBuilder {

        public LogbackLoggerMatcherBuilder(Logger logger) {
            super(logger);
        }

        @Override
        public LoggerMatcher<ILoggingEvent> build(boolean start) {
            ch.qos.logback.classic.Logger logbackLogger = (ch.qos.logback.classic.Logger) logger;
            LogbackMemoryAppender appender = new LogbackMemoryAppender();
            appender.setContext(logbackLogger.getLoggerContext());
            logbackLogger.addAppender(appender);
            LogbackLoggerMatcher matcher = new LogbackLoggerMatcher(logbackLogger, appender);
            if (start) {
                matcher.start();
            }
            return matcher;
        }

    }


}

