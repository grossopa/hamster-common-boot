package org.hamster.common.boot.core.mail;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import static javax.mail.Message.RecipientType.BCC;
import static javax.mail.Message.RecipientType.CC;
import static javax.mail.Message.RecipientType.TO;
import static org.apache.commons.lang3.ArrayUtils.isEmpty;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;
import static org.apache.commons.lang3.StringUtils.join;

/**
 * Overrides the receipts of either given {@link MimeMessage} or {@link SimpleMailMessage}
 *
 * @author Jack Yin
 * @since 1.0
 */
@Slf4j
public class OverrideRecipientsAction {
    private static final String SEP = ", ";

    @Getter
    private final String[] overrideTo;
    @Getter
    private final String[] overrideCc;
    @Getter
    private final String[] overrideBcc;

    /**
     * Constructs an instance with override to list, override cc list and / or override bcc list. at least one must be
     * present.
     *
     * @param overrideTo
     *         the mail to list to be overridden
     * @param overrideCc
     *         the mail cc list to be overridden
     * @param overrideBcc
     *         the mail bcc list to be overridden
     */
    public OverrideRecipientsAction(String[] overrideTo, String[] overrideCc, String[] overrideBcc) {
        if (isEmpty(overrideTo) && isEmpty(overrideCc) && isEmpty(overrideBcc)) {
            throw new IllegalArgumentException(
                    "At least one of overrideTo, overrideCC or overrideBcc need to be present.");
        }
        this.overrideTo = defaultIfNull(overrideTo, new String[0]);
        this.overrideCc = defaultIfNull(overrideCc, new String[0]);
        this.overrideBcc = defaultIfNull(overrideBcc, new String[0]);
    }

    /**
     * Overrides the to, cc and bcc list of the given {@link MimeMessage} with the help of {@link MimeMessageHelper}.
     *
     * @param mimeMessage
     *         the message to enrich
     */
    public void prepare(MimeMessage mimeMessage) throws MessagingException {
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        log.info("Overriding the receipts, to: {} -> {}, cc: {} -> {}, bcc: {} -> {}",
                join(mimeMessage.getRecipients(TO), SEP), join(overrideTo, SEP),
                join(mimeMessage.getRecipients(CC), SEP), join(overrideCc, SEP),
                join(mimeMessage.getRecipients(BCC), SEP), join(overrideBcc, SEP));
        helper.setTo(overrideTo);
        helper.setCc(overrideCc);
        helper.setBcc(overrideBcc);
    }

    /**
     * Overrides the co, cc and bcc list of the given {@link SimpleMailMessage}.
     *
     * @param simpleMailMessage
     *         the message to enrich
     */
    public void prepare(SimpleMailMessage simpleMailMessage) {
        log.info("Overriding the receipts, to: {} -> {}, cc: {} -> {}, bcc: {} -> {}",
                join(simpleMailMessage.getTo(), SEP), join(overrideTo, SEP), join(simpleMailMessage.getCc(), SEP),
                join(overrideCc, SEP), join(simpleMailMessage.getBcc(), SEP), join(overrideBcc, SEP));
        simpleMailMessage.setTo(overrideTo);
        simpleMailMessage.setCc(overrideCc);
        simpleMailMessage.setBcc(overrideBcc);
    }
}
