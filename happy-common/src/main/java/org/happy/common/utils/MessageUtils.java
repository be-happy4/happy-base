package org.happy.common.utils;

import org.happy.common.utils.spring.SpringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * I18n message utility class
 *
 * @author happy
 */
public class MessageUtils {
    /**
     * Get I18n message
     *
     * @param code i18n code
     * @param args i18n message parameters
     * @return I18n message
     */
    public static String message(String code, Object... args) {
        MessageSource messageSource = SpringUtils.getBean(MessageSource.class);
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }
}
