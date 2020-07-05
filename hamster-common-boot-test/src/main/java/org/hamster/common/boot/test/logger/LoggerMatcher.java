package org.hamster.common.boot.test.logger;

import java.util.regex.Pattern;

/**
 * The matcher for logger
 *
 * @author Jack Yin
 * @since 1.0
 */
public interface LoggerMatcher {

    /**
     * Verifies any of the appended loggers contains the keyword.
     *
     * @param keyword the keyword to search
     * @throws AssertionError if keyword could not be found
     */
    void verify(String keyword);

    /**
     * Verifies any of the appended loggers matches the pattern.
     *
     * @param pattern the pattern to match
     * @throws AssertionError if none of the appended loggers matches
     */
    void verify(Pattern pattern);

    void verify(String keyword, int index);

    void verify(Pattern pattern, int index);

    void verify(String keyword, int index, String level);

    void verify(Pattern pattern, int index, String level);

    void verify(String keyword, int index, String level, boolean negative);

    void verify(Pattern pattern, int index, String level, boolean negative);

}
