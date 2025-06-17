package org.happy.common.enums.gen;

import org.happy.common.core.domain.code.I18nCode;

public enum GenJavaType implements I18nCode<String> {
    STRING,
    INTEGER,
    LONG,
    DOUBLE,
    BIGDECIMAL,
    DATE,
    ;

    @Override
    public String getCode() {
        return name();
    }
}
