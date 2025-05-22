package org.happy.common.core.domain.code;

import org.behappy.common.i18n.MessageService;

public interface I18nItem {
    String getI18nKey();

    default String getTranslatedKey(MessageService messageService) {
        return messageService.getString(getI18nKey());
    }
}