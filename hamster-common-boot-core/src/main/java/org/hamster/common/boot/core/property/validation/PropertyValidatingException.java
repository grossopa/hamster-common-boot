package org.hamster.common.boot.core.property.validation;

import lombok.Getter;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Objects.requireNonNull;

/**
 * Throws when property validation fails and result type is {@link PropertyValidatingResultType#ERROR}.
 *
 * @author Jack Yin
 * @since 1.0
 */
public class PropertyValidatingException extends RuntimeException {

    @Getter
    private final List<PropertyValidatingResult> validatingResultList;

    /**
     * Constructs a new {@link PropertyValidatingException} instance with {@code null} as its detail message.
     *
     * @param validatingResultList
     *         the result lists with types as {@link PropertyValidatingResultType#ERROR}
     */
    public PropertyValidatingException(List<PropertyValidatingResult> validatingResultList) {
        super();
        requireNonNull(validatingResultList);
        this.validatingResultList = newArrayList(validatingResultList);
    }

    /**
     * Constructs a new {@link PropertyValidatingException} instance with the specified detail message.
     *
     * @param validatingResultList
     *         the result lists with types as {@link PropertyValidatingResultType#ERROR}
     * @param message
     *         the detail message. The detail message is saved for later retrieval by the {@link #getMessage()} method.
     */
    public PropertyValidatingException(List<PropertyValidatingResult> validatingResultList, String message) {
        super(message);
        requireNonNull(validatingResultList);
        this.validatingResultList = newArrayList(validatingResultList);
    }

    /**
     * Constructs a new {@link PropertyValidatingException} instance with the specified detail message and cause.
     *
     * @param validatingResultList
     *         the result lists with types as {@link PropertyValidatingResultType#ERROR}
     * @param message
     *         the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     * @param cause
     *         the cause (which is saved for later retrieval by the {@link #getCause()} method).  (A {@code null} value
     *         is permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public PropertyValidatingException(List<PropertyValidatingResult> validatingResultList, String message,
            Throwable cause) {
        super(message, cause);
        requireNonNull(validatingResultList);
        this.validatingResultList = newArrayList(validatingResultList);
    }

    /**
     * Constructs a new {@link PropertyValidatingException} instance with the specified cause and a detail message of
     * {@code (cause==null ? null : cause.toString())}.
     *
     * @param validatingResultList
     *         the result lists with types as {@link PropertyValidatingResultType#ERROR}
     * @param cause
     *         the cause (which is saved for later retrieval by the {@link #getCause()} method).  (A {@code null} value
     *         is permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public PropertyValidatingException(List<PropertyValidatingResult> validatingResultList, Throwable cause) {
        super(cause);
        requireNonNull(validatingResultList);
        this.validatingResultList = newArrayList(validatingResultList);
    }

}
