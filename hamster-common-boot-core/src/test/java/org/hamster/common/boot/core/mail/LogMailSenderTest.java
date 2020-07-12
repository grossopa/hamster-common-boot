package org.hamster.common.boot.core.mail;

import ch.qos.logback.classic.spi.ILoggingEvent;
import lombok.SneakyThrows;
import org.hamster.common.boot.test.logger.LoggerMatcher;
import org.hamster.common.boot.test.logger.LoggerMatcherBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mail.SimpleMailMessage;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;

import static javax.mail.Message.RecipientType.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link LogMailSender}
 *
 * @author Jack Yin
 * @since 1.0
 */
class LogMailSenderTest {

    LoggerMatcher<ILoggingEvent> loggerMatcher;
    LogMailSender testSubject = new LogMailSender();

    @BeforeEach
    void beforeEach() {
        loggerMatcher = LoggerMatcherBuilder.logback(LogMailSender.class).build(true);
    }

    @SneakyThrows
    private MimeMessage createMimeMessage(int index, boolean withFrom) {
        MimeMessage mimeMessage = mock(MimeMessage.class);
        if (withFrom) {
            when(mimeMessage.getFrom()).thenReturn(createAddresses("from@aaa.com"));
        }
        when(mimeMessage.getRecipients(eq(TO))).thenReturn(createAddresses("a@ham.com", "b@ham.com"));
        when(mimeMessage.getRecipients(eq(CC))).thenReturn(createAddresses("cc@ham.com", "dd@ham.com"));
        when(mimeMessage.getRecipients(eq(BCC))).thenReturn(createAddresses("bcc@ham.com", "bdd@ham.com"));
        when(mimeMessage.getSubject()).thenReturn("some subject " + index);
        return mimeMessage;
    }

    @SneakyThrows
    private InternetAddress[] createAddresses(String... addresses) {
        return Arrays.stream(addresses).map(address -> {
            try {
                return new InternetAddress(address);
            } catch (Exception e) {
                throw new AssertionError("wrong test data");
            }
        }).toArray(InternetAddress[]::new);
    }

    private SimpleMailMessage createSimpleMailMessage(int index) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("some.from@bbb.com");
        message.setTo("to@ccc.com", "to2@ccc.com");
        message.setSubject("some subject");
        message.setText("body 123 " + index);
        return message;
    }

    @AfterEach
    void afterEach() {
        loggerMatcher.terminate();
    }

    @Test
    void sendMimeMessageWithFrom() {
        testSubject.send(createMimeMessage(1, true));
        loggerMatcher.verify("Mute the email sending and print them in log.\n" + "    From: from@aaa.com\n"
                + "    To  : a@ham.com; b@ham.com\n" + "    Cc  : cc@ham.com; dd@ham.com\n"
                + "    Bcc : bcc@ham.com; bdd@ham.com\n" + "    Subject : some subject 1\n"
                + "    Text:\n      <Skip the content printing for MimeMessage>", 0);
    }

    @Test
    void sendMimeMessage() {
        testSubject.send(createMimeMessage(1, false));
        loggerMatcher.verify("Mute the email sending and print them in log.\n" + "    From: <Empty>\n"
                + "    To  : a@ham.com; b@ham.com\n" + "    Cc  : cc@ham.com; dd@ham.com\n"
                + "    Bcc : bcc@ham.com; bdd@ham.com\n" + "    Subject : some subject 1\n"
                + "    Text:\n      <Skip the content printing for MimeMessage>", 0);
    }

    @Test
    void sendMimeMessages() {
        testSubject.send(createMimeMessage(11, false), createMimeMessage(8, false));

        loggerMatcher.verify("Mute the email sending and print them in log.\n" + "    From: <Empty>\n"
                + "    To  : a@ham.com; b@ham.com\n" + "    Cc  : cc@ham.com; dd@ham.com\n"
                + "    Bcc : bcc@ham.com; bdd@ham.com\n" + "    Subject : some subject 11\n"
                + "    Text:\n      <Skip the content printing for MimeMessage>", 0);

        loggerMatcher.verify("Mute the email sending and print them in log.\n" + "    From: <Empty>\n"
                + "    To  : a@ham.com; b@ham.com\n" + "    Cc  : cc@ham.com; dd@ham.com\n"
                + "    Bcc : bcc@ham.com; bdd@ham.com\n" + "    Subject : some subject 8\n"
                + "    Text:\n      <Skip the content printing for MimeMessage>", 1);

    }

    @Test
    void sendSimpleMailMessage() {
        testSubject.send(createSimpleMailMessage(24));
        loggerMatcher.verify("Mute the email sending and print them in log.\n" + "    From: some.from@bbb.com\n"
                + "    To  : to@ccc.com; to2@ccc.com\n" + "    Cc  : null\n" + "    Bcc : null\n"
                + "    Subject : some subject\n" + "    Text:\n" + "      body 123 24", 0);
    }

    @Test
    void sendSimpleMailMessages() {
        testSubject.send(createSimpleMailMessage(24),createSimpleMailMessage(56));
        loggerMatcher.verify("Mute the email sending and print them in log.\n" + "    From: some.from@bbb.com\n"
                + "    To  : to@ccc.com; to2@ccc.com\n" + "    Cc  : null\n" + "    Bcc : null\n"
                + "    Subject : some subject\n" + "    Text:\n" + "      body 123 24", 0);

        loggerMatcher.verify("Mute the email sending and print them in log.\n" + "    From: some.from@bbb.com\n"
                + "    To  : to@ccc.com; to2@ccc.com\n" + "    Cc  : null\n" + "    Bcc : null\n"
                + "    Subject : some subject\n" + "    Text:\n" + "      body 123 56", 1);
    }

}