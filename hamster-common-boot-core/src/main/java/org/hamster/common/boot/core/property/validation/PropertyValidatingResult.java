package org.hamster.common.boot.core.property.validation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Validating property result
 *
 * @author Jack Yin
 * @since 1.0
 */
@Getter
@RequiredArgsConstructor
public class PropertyValidatingResult {
    private final String propertyName;
    private final String propertyValue;
    private final String message;
    private final PropertyValidatingResultType type;
}
