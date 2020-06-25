package org.hamster.common.boot.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.mail.internet.MimeMessage;

/**
 * Overrides the receipts of the given {@link MimeMessage} to the configured value
 *
 * @author Jack Yin
 * @since 1.0
 */
@RequiredArgsConstructor
public class OverrideRecipientsMimeMessagePreparator implements MimeMessagePreparator {

    private final MimeMessagePreparator messagePreparator;
    private final OverrideRecipientsAction action;

    @Override
    public void prepare(MimeMessage mimeMessage) throws Exception {
        messagePreparator.prepare(mimeMessage);
        action.prepare(mimeMessage);
    }
}
