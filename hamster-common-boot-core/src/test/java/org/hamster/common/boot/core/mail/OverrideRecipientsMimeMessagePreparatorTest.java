package org.hamster.common.boot.core.mail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.mail.internet.MimeMessage;

import static org.mockito.Mockito.*;

/**
 * Tests for {@link OverrideRecipientsMimeMessagePreparator}
 *
 * @author Jack Yin
 * @since 1.0
 */
class OverrideRecipientsMimeMessagePreparatorTest {

    MimeMessagePreparator messagePreparator;
    OverrideRecipientsAction action;

    OverrideRecipientsMimeMessagePreparator testSubject;

    @BeforeEach
    void setUp() {
        messagePreparator = mock(MimeMessagePreparator.class);
        action = mock(OverrideRecipientsAction.class);

        testSubject = new OverrideRecipientsMimeMessagePreparator(messagePreparator, action);
    }

    @Test
    void prepare() throws Exception {
        MimeMessage message = mock(MimeMessage.class);
        testSubject.prepare(message);
        verify(messagePreparator, times(1)).prepare(eq(message));
        verify(action, times(1)).prepare(eq(message));
    }
}