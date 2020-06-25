package org.hamster.common.boot.mail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

/**
 * Tests for {@link OverrideRecipientsMailSender}
 *
 * @author Jack Yin
 * @since 1.0
 */
class OverrideRecipientsMailSenderTest {

    JavaMailSender sender;

    OverrideRecipientsMailSender testSubject;

    @BeforeEach
    void setUp() {
        sender = mock(JavaMailSender.class);
        testSubject = new OverrideRecipientsMailSender(sender, new String[]{"a@hamster.com"}, null, null);
    }

    @Test
    void createMimeMessage() {
        MimeMessage message = mock(MimeMessage.class);
        when(sender.createMimeMessage()).thenReturn(message);

        MimeMessage result = testSubject.createMimeMessage();
        verify(sender, only()).createMimeMessage();
        assertEquals(message, result);
    }

    @Test
    void createMimeMessage2() {
        MimeMessage message = mock(MimeMessage.class);
        when(sender.createMimeMessage(any())).thenReturn(message);
        MimeMessage result = testSubject.createMimeMessage(new ByteArrayInputStream("abc".getBytes()));

        verify(sender, only()).createMimeMessage(any());
        assertEquals(message, result);
    }

    @Test
    void sendMimeMessage() throws MessagingException {
        MimeMessage message = mock(MimeMessage.class);
        message.setRecipients(Message.RecipientType.CC, "bbbb@abc.com");

        doAnswer(a -> {
            MimeMessage copied = a.getArgument(0);
            assertNotEquals(copied, message);
            assertEquals("a@hamster.com", copied.getRecipients(Message.RecipientType.TO)[0].toString());
            assertNull(copied.getRecipients(Message.RecipientType.CC));
            assertNull(copied.getRecipients(Message.RecipientType.BCC));
            return null;
        }).when(sender).send(any(MimeMessage.class));

        testSubject.send(message);
        verify(sender, only()).send(any(MimeMessage.class));
    }

    @Test
    void sendMimeMessages() throws MessagingException {
        MimeMessage message1 = mock(MimeMessage.class);
        message1.setRecipients(Message.RecipientType.CC, "bbbb@abc.com");

        MimeMessage message2 = mock(MimeMessage.class);
        message2.setRecipients(Message.RecipientType.BCC, "cccc@abc.com");

        doAnswer(a -> {
            MimeMessage copied1 = a.getArgument(0);
            assertNotEquals(copied1, message1);
            assertEquals("a@hamster.com", copied1.getRecipients(Message.RecipientType.TO)[0].toString());
            assertNull(copied1.getRecipients(Message.RecipientType.CC));
            assertNull(copied1.getRecipients(Message.RecipientType.BCC));

            MimeMessage copied2 = a.getArgument(1);
            assertNotEquals(copied2, message2);
            assertEquals("a@hamster.com", copied2.getRecipients(Message.RecipientType.TO)[0].toString());
            assertNull(copied2.getRecipients(Message.RecipientType.CC));
            assertNull(copied2.getRecipients(Message.RecipientType.BCC));
            return null;
        }).when(sender).send(any(MimeMessage.class), any(MimeMessage.class));

        testSubject.send(message1, message2);
        verify(sender, only()).send(any(MimeMessage.class), any(MimeMessage.class));
    }

    @Test
    void sendMimeMessagePreparator() {
        MimeMessagePreparator preparator = mock(MimeMessagePreparator.class);
        testSubject.send(preparator);
        verify(sender, only()).send(any(OverrideRecipientsMimeMessagePreparator.class));
    }

    @Test
    void sendMimeMessagePreparators() {
        MimeMessagePreparator preparator1 = mock(MimeMessagePreparator.class);
        MimeMessagePreparator preparator2 = mock(MimeMessagePreparator.class);
        testSubject.send(preparator1, preparator2);
        verify(sender, only()).send(any(OverrideRecipientsMimeMessagePreparator.class),
                any(OverrideRecipientsMimeMessagePreparator.class));
    }

    @Test
    void testSendSimpleMailMessage() {
        doAnswer(a -> {
            SimpleMailMessage copied = a.getArgument(0);
            assertEquals("a@hamster.com", copied.getTo()[0]);
            return null;
        }).when(sender).send(any(SimpleMailMessage.class));

        SimpleMailMessage message = mock(SimpleMailMessage.class);
        testSubject.send(message);

        verify(sender, only()).send(any(SimpleMailMessage.class));
    }

    @Test
    void testSendSimpleMailMessages() {
        doAnswer(a -> {
            SimpleMailMessage copied1 = a.getArgument(0);
            assertEquals("a@hamster.com", copied1.getTo()[0]);

            SimpleMailMessage copied2 = a.getArgument(0);
            assertEquals("a@hamster.com", copied2.getTo()[0]);
            return null;
        }).when(sender).send(any(SimpleMailMessage.class), any(SimpleMailMessage.class));

        SimpleMailMessage message1 = mock(SimpleMailMessage.class);
        SimpleMailMessage message2 = mock(SimpleMailMessage.class);
        testSubject.send(message1, message2);

        verify(sender, only()).send(any(SimpleMailMessage.class), any(SimpleMailMessage.class));
    }
}