package org.happy.common.enums.entity;

import org.happy.common.core.domain.code.I18nCode;

public enum JobStatus implements I18nCode<String> {
    NORMAL,
    PAUSE,
    ;

    @Override
    public String getCode() {
        return name();
    }
}
