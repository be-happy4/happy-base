package org.happy.common.enums.entity;

import org.happy.common.core.domain.code.I18nCode;

public enum NoticeType implements I18nCode<String> {
    NOTICE,
    ANNOUNCEMENT,
    ;

    @Override
    public String getCode() {
        return name();
    }
}
