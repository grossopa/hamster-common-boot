package org.hamster.common.boot.test.logger;

import ch.qos.logback.classic.spi.ILoggingEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.function.Function;
import java.util.regex.Pattern;

import static com.google.common.collect.Lists.newArrayList;
import static java.text.MessageFormat.format;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.*;

/**
 * The Logback implementation of the {@link LoggerMatcher} for verifying whether Slf4j logging is printed as expected
 * within the test case.
 *
 * @author Jack Yin
 * @since 1.0
 */
@RequiredArgsConstructor
public class LogbackLoggerMatcher implements LoggerMatcher {

    private final LogbackMemoryAppender appender;

    @Override
    public void verify(String keyword) {
        verify(keyword, -1, null, false);
    }

    @Override
    public void verify(Pattern pattern) {
        verify(pattern, -1, null, false);
    }

    @Override
    public void verify(String keyword, int index) {
        verify(keyword, index, null, false);
    }

    @Override
    public void verify(Pattern pattern, int index) {
        verify(pattern, index, null, false);
    }

    @Override
    public void verify(String keyword, int index, String level) {
        verify(keyword, index, level, false);
    }

    @Override
    public void verify(Pattern pattern, int index, String level) {
        verify(pattern, index, level, false);
    }

    @Override
    public void verify(String keyword, int index, String level, boolean negative) {
        doVerify(event -> contains(event.getFormattedMessage(), keyword), getList(index), index, level, negative,
                keyword);
    }

    @Override
    public void verify(Pattern pattern, int index, String level, boolean negative) {
        doVerify(event -> pattern.matcher(event.getFormattedMessage()).find(), getList(index), index, level, negative,
                pattern.toString());
    }

    protected void doVerify(Function<ILoggingEvent, Boolean> action, List<ILoggingEvent> eventList, int index,
            String level, boolean negative, String expected) {
        List<ILoggingEvent> matchedList = eventList.stream().filter(action::apply)
                .filter(event -> isBlank(level) || equalsIgnoreCase(level, event.getLevel().toString()))
                .collect(toList());
        String expectedStr = getExpectedStr(level, expected, index);
        if (!negative && CollectionUtils.isEmpty(matchedList)) {
            // if matched list is already empty and we are expecting not empty, throw assertion error
            throw new AssertionError(format("Expected {0} but not printed.", expectedStr));
        } else if (negative && !CollectionUtils.isEmpty(matchedList)) {
            // Expect negative but still there are full matches
            throw new AssertionError(format("Expected {0} NOT to print but actually caught on:\n  {1}", expectedStr,
                    matchedList.stream().map(this::mapToString).collect(joining("\n  "))));
        }
    }

    private List<ILoggingEvent> getList(int index) {
        List<ILoggingEvent> eventList = newArrayList();
        if (index < 0) {
            eventList.addAll(appender.getEventList());
        } else if (index < appender.getEventList().size()) {
            eventList.add(appender.getEventList().get(index));
        }
        return eventList;
    }

    private String mapToString(ILoggingEvent event) {
        StackTraceElement element = event.getCallerData()[0];
        return format("{0}:{1} {2}", element.getFileName(), element.getLineNumber(), event.getMessage());
    }

    private String getExpectedStr(String level, String expected, int index) {
        return format("{0}:{1}{2}", defaultIfBlank(level, "<any-levels>"), expected,
                index >= 0 ? " at index:" + index : " ");
    }
}
