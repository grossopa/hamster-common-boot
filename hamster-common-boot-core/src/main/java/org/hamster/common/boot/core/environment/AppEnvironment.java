package org.hamster.common.boot.core.environment;

/**
 * The default application environment for profile-based spring beans
 *
 * @author Jack Yin
 * @since 1.0
 */
public enum AppEnvironment {
    /**
     * The development environment
     */
    DEVELOPMENT, LOCAL, QA, STAGE, PRODUCTION;
}
