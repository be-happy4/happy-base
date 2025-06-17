package org.happy.common.enums.entity;

import org.happy.common.core.domain.code.I18nCode;

public enum NoticeStatus implements I18nCode<String> {
    NORMAL,
    CLOSED,
    ;

    @Override
    public String getCode() {
        return name();
    }
}
