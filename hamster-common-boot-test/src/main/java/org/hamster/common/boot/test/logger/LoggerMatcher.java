package org.hamster.common.boot.test.logger;

import javax.annotation.Nullable;
import java.util.List;
import java.util.regex.Pattern;

/**
 * The matcher for logger
 *
 * @param <T>
 *         event type
 * @author Jack Yin
 * @since 1.0
 */
public interface LoggerMatcher<T> {

    /**
     * Verifies any of the appended loggers contains the keyword.
     *
     * @param keyword
     *         the keyword to search
     * @throws AssertionError
     *         if keyword could not be found
     */
    void verify(String keyword);

    /**
     * Verifies any of the appended loggers matches the pattern.
     *
     * @param pattern
     *         the pattern to match
     * @throws AssertionError
     *         if none of the appended loggers matches
     */
    void verify(Pattern pattern);

    /**
     * Verifies any of the appended loggers matches the pattern.
     *
     * @param keyword
     *         the keyword to search
     * @param index
     *         optional, the logging history position, default to be -1 for scanning all loggers
     * @throws AssertionError
     *         if none of the appended loggers matches
     */
    void verify(String keyword, int index);

    /**
     * Verifies any of the appended loggers matches the pattern.
     *
     * @param pattern
     *         the pattern to match
     * @param index
     *         optional, the logging history position, default to be -1 for scanning all loggers
     * @throws AssertionError
     *         if none of the appended loggers matches
     */
    void verify(Pattern pattern, int index);

    /**
     * Verifies any of the appended loggers matches the pattern.
     *
     * @param keyword
     *         the keyword to search
     * @param index
     *         optional, the logging history position, default to be -1 for scanning all loggers
     * @param level
     *         optional, expected logging level, default to be null for all logging levels
     * @throws AssertionError
     *         if none of the appended loggers matches
     */
    void verify(String keyword, int index, @Nullable String level);

    /**
     * Verifies any of the appended loggers matches the pattern.
     *
     * @param pattern
     *         the pattern to match
     * @param index
     *         optional, the logging history position, default to be -1 for scanning all loggers
     * @param level
     *         optional, expected logging level, default to be null for all logging levels
     * @throws AssertionError
     *         if none of the appended loggers matches
     */
    void verify(Pattern pattern, int index, @Nullable String level);

    /**
     * Verifies any of the appended loggers matches the pattern.
     *
     * @param keyword
     *         the keyword to search
     * @param index
     *         optional, the logging history position, default to be -1 for scanning all loggers
     * @param level
     *         optional, expected logging level, default to be null for all logging levels
     * @param negative
     *         optional, the log must not be printed, default to be false
     * @throws AssertionError
     *         if none of the appended loggers matches
     */
    void verify(String keyword, int index, @Nullable String level, boolean negative);

    /**
     * Verifies any of the appended loggers matches the pattern.
     *
     * @param pattern
     *         the pattern to match
     * @param index
     *         optional, the logging history position, default to be -1 for scanning all loggers
     * @param level
     *         optional, expected logging level, default to be null for all logging levels
     * @param negative
     *         optional, the log must not be printed, default to be false
     * @throws AssertionError
     *         if none of the appended loggers matches
     */
    void verify(Pattern pattern, int index, @Nullable String level, boolean negative);

    /**
     * Starts the underlying logging appender
     */
    void start();

    /**
     * Stops the underlying logging appender
     */
    void stop();

    /**
     * Stops the underlying logging appender and terminates the logging (e.g. detach the appender).
     */
    void terminate();

    /**
     * Gets the tracked event list
     *
     * @return the tracked event list
     */
    List<T> getEventList();

}
