package org.hamster.common.boot.mail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mail.SimpleMailMessage;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

/**
 * Tests for {@link OverrideRecipientsAction}
 *
 * @author Jack Yin
 * @since 1.0
 */
class OverrideRecipientsActionTest {

    OverrideRecipientsAction testSubject;

    @BeforeEach
    void setUp() {
        testSubject = new OverrideRecipientsAction(
                new String[]{"a@hamster.com"}, new String[]{"b@hamster.com"}, new String[]{"c@hamster.com", "c2@hamster.com"});
    }

    @Test
    void constructor1() {
        testSubject = new OverrideRecipientsAction(
                new String[]{"a@hamster.com"}, new String[]{"b@hamster.com"}, new String[]{"c@hamster.com", "c2@hamster.com"});
        assertArrayEquals(new String[]{"a@hamster.com"}, testSubject.getOverrideTo());
        assertArrayEquals(new String[]{"b@hamster.com"}, testSubject.getOverrideCc());
        assertArrayEquals(new String[]{"c@hamster.com", "c2@hamster.com"}, testSubject.getOverrideBcc());
    }

    @Test
    void constructor2() {
        testSubject = new OverrideRecipientsAction(
                null, new String[]{"b@hamster.com"}, new String[]{"c@hamster.com", "c2@hamster.com"});
        assertArrayEquals(new String[]{}, testSubject.getOverrideTo());
        assertArrayEquals(new String[]{"b@hamster.com"}, testSubject.getOverrideCc());
        assertArrayEquals(new String[]{"c@hamster.com", "c2@hamster.com"}, testSubject.getOverrideBcc());
    }

    @Test
    void constructor3() {
        testSubject = new OverrideRecipientsAction(
                null, null, new String[]{"c@hamster.com", "c2@hamster.com"});
        assertArrayEquals(new String[]{}, testSubject.getOverrideTo());
        assertArrayEquals(new String[]{}, testSubject.getOverrideCc());
        assertArrayEquals(new String[]{"c@hamster.com", "c2@hamster.com"}, testSubject.getOverrideBcc());
    }

    @Test
    void constructor4() {
        assertThrows(IllegalArgumentException.class, () -> new OverrideRecipientsAction(null, null, null));
    }

    @Test
    void prepare() throws MessagingException {
        MimeMessage mimeMessage = mock(MimeMessage.class);
        testSubject.prepare(mimeMessage);
        verify(mimeMessage, times(1)).setRecipients(eq(MimeMessage.RecipientType.TO),
                eq(new InternetAddress[]{new InternetAddress("a@hamster.com")}));
        verify(mimeMessage, times(1)).setRecipients(eq(MimeMessage.RecipientType.CC),
                eq(new InternetAddress[]{new InternetAddress("b@hamster.com")}));
        verify(mimeMessage, times(1)).setRecipients(eq(MimeMessage.RecipientType.BCC),
                eq(new InternetAddress[]{new InternetAddress("c@hamster.com"), new InternetAddress("c2@hamster.com")}));
    }

    @Test
    void prepare2() {
        testSubject = new OverrideRecipientsAction(
                null, null, new String[]{"c@hamster.com", "c2@hamster.com"});
        SimpleMailMessage message = mock(SimpleMailMessage.class);
        testSubject.prepare(message);
        verify(message, times(1)).setBcc(eq("c@hamster.com"), eq("c2@hamster.com"));
    }
}