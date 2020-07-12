package org.hamster.common.boot.core.mail;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.mail.Address;
import javax.mail.internet.MimeMessage;

import static javax.mail.Message.RecipientType.BCC;
import static javax.mail.Message.RecipientType.CC;
import static javax.mail.Message.RecipientType.TO;
import static org.apache.commons.lang3.StringUtils.join;

/**
 * Prints the email details in logging file rather than send out.
 *
 * @author Jack Yin
 * @since 1.0
 */
@Slf4j
@RequiredArgsConstructor
public class LogMailSender extends JavaMailSenderImpl {

    private static final String SEP = "; ";

    @SneakyThrows
    @Override
    public void send(MimeMessage mimeMessage) throws MailException {
        doPrint(findFrom(mimeMessage.getFrom()), mimeMessage.getRecipients(TO), mimeMessage.getRecipients(CC),
                mimeMessage.getRecipients(BCC), mimeMessage.getSubject(),
                "<Skip the content printing for MimeMessage>");
    }

    @Override
    public void send(MimeMessage... mimeMessages) throws MailException {
        for (MimeMessage message : mimeMessages) {
            send(message);
        }
    }

    @Override
    public void send(SimpleMailMessage simpleMailMessage) throws MailException {
        doPrint(simpleMailMessage.getFrom(), simpleMailMessage.getTo(), simpleMailMessage.getCc(),
                simpleMailMessage.getBcc(), simpleMailMessage.getSubject(), simpleMailMessage.getText());
    }

    @Override
    public void send(SimpleMailMessage... simpleMailMessages) throws MailException {
        for (SimpleMailMessage message : simpleMailMessages) {
            send(message);
        }
    }

    private void doPrint(String from, Object[] to, Object[] cc, Object[] bcc, String subject, String text) {
        String sb =
                "Mute the email sending and print them in log." + "\n    From: " + from + "\n    To  : " + join(to, SEP)
                        + "\n    Cc  : " + join(cc, SEP) + "\n    Bcc : " + join(bcc, SEP) + "\n    Subject : "
                        + subject + "\n    Text:\n      " + text;
        log.info(sb);
    }

    private String findFrom(Address[] from) {
        if (ArrayUtils.isEmpty(from)) {
            return "<Empty>";
        } else {
            return join(from, SEP);
        }
    }
}
