package org.hamster.common.boot.core.mail;

import lombok.SneakyThrows;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.annotation.Nonnull;
import javax.mail.internet.MimeMessage;
import java.io.InputStream;

import static java.util.Arrays.stream;
import static java.util.Objects.requireNonNull;

/**
 * The override recipients wrapper of {@link JavaMailSender}, it will override the recipients to configured ones e.g. in
 * non-production environment the email should go to development / support team instead of users.
 *
 * @author Jack Yin
 * @since 1.0
 */
public class OverrideRecipientsMailSender implements JavaMailSender {
    private final JavaMailSender javaMailSender;
    private final OverrideRecipientsAction action;

    /**
     * Constructs an instance with javaMailSender and overriding recipients details.
     *
     * @param javaMailSender
     *         the decorated javaMailSender instance
     * @param overrideTo
     *         the to list to override
     * @param overrideCc
     *         the cc list to override
     * @param overrideBcc
     *         the bcc list to override
     */
    public OverrideRecipientsMailSender(JavaMailSender javaMailSender, String[] overrideTo, String[] overrideCc,
            String[] overrideBcc) {
        this.javaMailSender = requireNonNull(javaMailSender);
        this.action = new OverrideRecipientsAction(overrideTo, overrideCc, overrideBcc);
    }

    @Override
    @Nonnull
    public MimeMessage createMimeMessage() {
        return javaMailSender.createMimeMessage();
    }

    @Override
    @Nonnull
    public MimeMessage createMimeMessage(@Nonnull InputStream inputStream) throws MailException {
        return javaMailSender.createMimeMessage(inputStream);
    }

    @SneakyThrows
    @Override
    public void send(@Nonnull MimeMessage mimeMessage) throws MailException {
        MimeMessage copiedMessage = new MimeMessage(mimeMessage);
        action.prepare(copiedMessage);
        javaMailSender.send(copiedMessage);
    }

    @SneakyThrows
    @Override
    public void send(MimeMessage... mimeMessages) throws MailException {
        MimeMessage[] copiedMessages = new MimeMessage[mimeMessages.length];
        for (int i = 0; i < mimeMessages.length; i++) {
            copiedMessages[i] = new MimeMessage(mimeMessages[i]);
            action.prepare(copiedMessages[i]);
        }
        javaMailSender.send(copiedMessages);
    }

    @Override
    public void send(@Nonnull MimeMessagePreparator mimeMessagePreparator) throws MailException {
        javaMailSender.send(new OverrideRecipientsMimeMessagePreparator(mimeMessagePreparator, action));
    }

    @Override
    public void send(@Nonnull MimeMessagePreparator... mimeMessagePreparators) throws MailException {
        MimeMessagePreparator[] overridePreparators = stream(mimeMessagePreparators)
                .map(p -> new OverrideRecipientsMimeMessagePreparator(p, action)).toArray(MimeMessagePreparator[]::new);
        javaMailSender.send(overridePreparators);
    }

    @Override
    public void send(@Nonnull SimpleMailMessage simpleMailMessage) throws MailException {
        SimpleMailMessage copiedMailMessage = new SimpleMailMessage(simpleMailMessage);
        action.prepare(copiedMailMessage);
        javaMailSender.send(copiedMailMessage);
    }

    @Override
    public void send(@Nonnull SimpleMailMessage... simpleMailMessages) throws MailException {
        SimpleMailMessage[] copiedMessages = stream(simpleMailMessages).map(message -> {
            SimpleMailMessage copiedMessage = new SimpleMailMessage(message);
            action.prepare(copiedMessage);
            return copiedMessage;
        }).toArray(SimpleMailMessage[]::new);
        javaMailSender.send(copiedMessages);
    }
}
