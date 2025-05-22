package org.happy.common.core.domain.code;


import org.happy.common.utils.MessageUtils;

public interface I18nItem {
    String getI18nKey();

    default String getTranslatedKey() {
        return MessageUtils.message(getI18nKey());
    }
}