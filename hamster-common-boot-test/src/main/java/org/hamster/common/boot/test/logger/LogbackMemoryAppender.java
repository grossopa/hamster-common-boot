package org.hamster.common.boot.test.logger;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * Appends the loggers into memory for unit test verification
 *
 * @author Jack Yin
 * @since 1.0
 */
public class LogbackMemoryAppender extends AppenderBase<ILoggingEvent> {

    private final List<ILoggingEvent> eventList = newArrayList();

    @Override
    protected void append(ILoggingEvent iLoggingEvent) {
        eventList.add(iLoggingEvent);
    }

    public List<ILoggingEvent> getEventList() {
        return eventList;
    }
}
