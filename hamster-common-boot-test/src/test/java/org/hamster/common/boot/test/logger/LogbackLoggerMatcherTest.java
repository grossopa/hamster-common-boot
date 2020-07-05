package org.hamster.common.boot.test.logger;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.regex.Pattern;

import static java.util.Arrays.asList;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link LogbackLoggerMatcher}
 *
 * @author Jack Yin
 * @since 1.0
 */
class LogbackLoggerMatcherTest {

    LogbackMemoryAppender appender;
    LogbackLoggerMatcher testSubject;

    @BeforeEach
    void setUp() {
        appender = mock(LogbackMemoryAppender.class);

        List<ILoggingEvent> eventList = asList(createMockEvent("INFO", "Some message dog", "File1.java", 35),
                createMockEvent("WARN", "Some message cat", "File1.java", 48),
                createMockEvent("ERROR", "Some message bird JP-334", "File2.java", 55));

        when(appender.getEventList()).thenReturn(eventList);

        testSubject = new LogbackLoggerMatcher(appender);
    }


    @Test
    void verifyKeyword() {
        assertDoesNotThrow(() -> testSubject.verify("cat"));
    }

    @Test
    void verifyKeywordNegative() {
        assertThrows(AssertionError.class, () -> testSubject.verify("tom"));
    }

    @Test
    void verifyPattern() {
        assertDoesNotThrow(() -> testSubject.verify(Pattern.compile("[A-Z]+-[0-9]+$")));
    }

    @Test
    void verifyPatternNegative() {
        assertThrows(AssertionError.class, () -> testSubject.verify(Pattern.compile("[A-Z]+-[0-9]{6,}$")));
    }

    @Test
    void verifyKeywordIndex() {
        assertDoesNotThrow(() -> testSubject.verify("cat", 1));
    }

    @Test
    void verifyKeywordIndexNegative() {
        assertThrows(AssertionError.class, () -> testSubject.verify("cat", 0));
    }

    @Test
    void verifyKeywordIndexNegativeOutOfBound() {
        assertThrows(AssertionError.class, () -> testSubject.verify("cat", 5));
    }

    @Test
    void verifyPatternIndex() {
        assertDoesNotThrow(() -> testSubject.verify(Pattern.compile("[A-Z]+-[0-9]+$"), 2));
    }

    @Test
    void verifyPatternIndexNegative() {
        assertThrows(AssertionError.class, () -> testSubject.verify(Pattern.compile("[A-Z]+-[0-9]{6,}$"), 1));
    }

    @Test
    void verifyPatternIndexNegativeOutOfBound() {
        assertThrows(AssertionError.class, () -> testSubject.verify(Pattern.compile("[A-Z]+-[0-9]{6,}$"), 5));
    }

    @Test
    void verifyKeywordIndexLevel() {
        assertDoesNotThrow(() -> testSubject.verify("cat", 1, "WARN"));
    }

    @Test
    void verifyKeywordIndexLevelNegative() {
        assertThrows(AssertionError.class, () -> testSubject.verify("cat", 5, "SOME INFO"));
    }

    @Test
    void verifyPatternIndexLevel() {
        assertDoesNotThrow(() -> testSubject.verify(Pattern.compile("[A-Z]+-[0-9]+$"), 2, "ERROR"));
    }

    @Test
    void verifyPatternIndexLevelNegative() {
        assertThrows(AssertionError.class, () -> testSubject.verify(Pattern.compile("[A-Z]+-[0-9]+$"), 2, "SOME INFO"));
    }

    @Test
    void verifyKeywordIndexLevelNeg() {
        assertDoesNotThrow(() -> testSubject.verify("blablabla", 1, "WARN", true));
    }

    @Test
    void verifyKeywordIndexLevelNegDiffLevel() {
        assertDoesNotThrow(() -> testSubject.verify("cat", 1, "INFO", true));
    }

    @Test
    void verifyKeywordIndexLevelNegDiffIndex() {
        assertDoesNotThrow(() -> testSubject.verify("cat", 2, "WARN", true));
    }

    @Test
    void verifyKeywordIndexLevelNegNegative() {
        assertThrows(AssertionError.class, () -> testSubject.verify("cat", -1, "WARN", true));
    }

    private ILoggingEvent createMockEvent(String level, String formattedMessage, String fileName, int lineNumber) {
        ILoggingEvent event = mock(ILoggingEvent.class);
        when(event.getLevel()).thenReturn(isBlank(level) ? null : Level.valueOf(level));
        when(event.getFormattedMessage()).thenReturn(formattedMessage);

        StackTraceElement element = new StackTraceElement("SomeClass", "SomeMethod", fileName, lineNumber);
        when(event.getCallerData()).thenReturn(new StackTraceElement[]{element});
        return event;
    }

}