package org.hamster.common.boot.test.logger;

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

    public static LoggerMatcherBuilder logback(Logger logger) {
        return new LogbackLoggerMatcherBuilder(logger);
    }

    public static LoggerMatcherBuilder logback(Class<?> clazz) {
        return new LogbackLoggerMatcherBuilder(LoggerFactory.getLogger(clazz));
    }

    public static LoggerMatcherBuilder logback(String loggerName) {
        return new LogbackLoggerMatcherBuilder(LoggerFactory.getLogger(loggerName));
    }

    public abstract LoggerMatcher<?> build(boolean start);

    static class LogbackLoggerMatcherBuilder extends LoggerMatcherBuilder {

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

