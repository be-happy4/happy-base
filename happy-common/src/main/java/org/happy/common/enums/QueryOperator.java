package org.happy.common.enums;

import org.happy.common.core.domain.code.I18nCode;

public enum QueryOperator implements I18nCode<String> {
    EQ,
    NE,
    GT,
    LT,
    LIKE,
    BETWEEN,
    ;

    @Override
    public String getCode() {
        return name();
    }
}
