package org.happy.common.enums;

import org.happy.common.core.domain.code.I18nCode;

public enum SexEnum implements I18nCode<String> {
    MALE,
    FEMALE,
    UNKNOWN;

    @Override
    public String getCode() {
        return name();
    }
}
