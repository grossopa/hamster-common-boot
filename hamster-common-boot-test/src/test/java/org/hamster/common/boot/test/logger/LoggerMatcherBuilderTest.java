package org.hamster.common.boot.test.logger;

import ch.qos.logback.classic.spi.ILoggingEvent;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for {@link LoggerMatcherBuilder}
 *
 * @author Jack Yin
 * @since 1.0
 */
class LoggerMatcherBuilderTest {

    @Test
    void logbackLoggerObject() {
        Logger logger = LoggerFactory.getLogger("test-logger");
        LogbackLoggerMatcher matcher = (LogbackLoggerMatcher) LoggerMatcherBuilder.logback(logger).build(true);
        logger.info("test message 1");
        logger.info("test message 2");
        List<ILoggingEvent> events = matcher.getEventList();

        assertEquals(2, events.size());
        assertEquals("test message 1", events.get(0).getFormattedMessage());
        assertEquals("test message 2", events.get(1).getFormattedMessage());
    }

    @Test
    void logbackName() {
        LogbackLoggerMatcher matcher = (LogbackLoggerMatcher) LoggerMatcherBuilder.logback("test-logger").build(true);

        Logger logger = LoggerFactory.getLogger("test-logger");

        logger.info("test message 1");
        logger.info("test message 2");
        List<ILoggingEvent> events = matcher.getEventList();

        assertEquals(2, events.size());
        assertEquals("test message 1", events.get(0).getFormattedMessage());
        assertEquals("test message 2", events.get(1).getFormattedMessage());
    }

    @Test
    void logbackClass() {
        LogbackLoggerMatcher matcher = (LogbackLoggerMatcher) LoggerMatcherBuilder
                .logback(LoggerMatcherBuilderTest.class).build(true);

        Logger logger = LoggerFactory.getLogger(LoggerMatcherBuilderTest.class);

        logger.info("test message 1");
        logger.info("test message 2");
        List<ILoggingEvent> events = matcher.getEventList();

        assertEquals(2, events.size());
        assertEquals("test message 1", events.get(0).getFormattedMessage());
        assertEquals("test message 2", events.get(1).getFormattedMessage());
    }

    @Test
    void start() {
        LogbackLoggerMatcher matcher = (LogbackLoggerMatcher) LoggerMatcherBuilder
                .logback(LoggerMatcherBuilderTest.class).build(false);

        Logger logger = LoggerFactory.getLogger(LoggerMatcherBuilderTest.class);

        logger.info("test message 0");
        List<ILoggingEvent> events = matcher.getEventList();
        assertTrue(events.isEmpty());

        matcher.start();

        logger.info("test message 1");
        logger.info("test message 2");

        events = matcher.getEventList();

        assertEquals(2, events.size());
        assertEquals("test message 1", events.get(0).getFormattedMessage());
        assertEquals("test message 2", events.get(1).getFormattedMessage());
    }

    @Test
    void stop() {
        LogbackLoggerMatcher matcher = (LogbackLoggerMatcher) LoggerMatcherBuilder
                .logback(LoggerMatcherBuilderTest.class).build(true);

        Logger logger = LoggerFactory.getLogger(LoggerMatcherBuilderTest.class);

        logger.info("test message 1");
        logger.info("test message 2");
        List<ILoggingEvent> events = matcher.getEventList();

        assertEquals(2, events.size());
        assertEquals("test message 1", events.get(0).getFormattedMessage());
        assertEquals("test message 2", events.get(1).getFormattedMessage());

        matcher.stop();
        logger.info("test message 3");
        events = matcher.getEventList();
        assertEquals(2, events.size());

        matcher.start();
        logger.info("test message 4");
        events = matcher.getEventList();
        assertEquals(3, events.size());
        assertEquals("test message 4", events.get(2).getFormattedMessage());
    }

    @Test
    void terminate() {
        LogbackLoggerMatcher matcher = (LogbackLoggerMatcher) LoggerMatcherBuilder
                .logback(LoggerMatcherBuilderTest.class).build(true);

        Logger logger = LoggerFactory.getLogger(LoggerMatcherBuilderTest.class);

        logger.info("test message 1");
        logger.info("test message 2");
        List<ILoggingEvent> events = matcher.getEventList();

        assertEquals(2, events.size());
        assertEquals("test message 1", events.get(0).getFormattedMessage());
        assertEquals("test message 2", events.get(1).getFormattedMessage());

        matcher.terminate();
        logger.info("test message 3");
        assertEquals(2, events.size());

        matcher.start();
        logger.info("test message 4");
        assertEquals(2, events.size());
    }

}